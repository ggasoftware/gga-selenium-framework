/****************************************************************************
 * Copyright (C) 2014 GGA Software Services LLC
 *
 * This file may be distributed and/or modified under the terms of the
 * GNU General Public License version 3 as published by the Free Software
 * Foundation.
 *
 * This file is provided AS IS with NO WARRANTY OF ANY KIND, INCLUDING THE
 * WARRANTY OF DESIGN, MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see <http://www.gnu.org/licenses>.
 ***************************************************************************/
package com.ggasoftware.uitest.control;

import com.ggasoftware.uitest.control.base.annotations.functions.Functions;
import com.ggasoftware.uitest.control.base.apiInteract.GetElementModule;
import com.ggasoftware.uitest.control.base.logger.LogSettings;
import com.ggasoftware.uitest.control.interfaces.common.IButton;
import com.ggasoftware.uitest.control.interfaces.common.IText;
import com.ggasoftware.uitest.control.new_controls.base.BaseElement;
import com.ggasoftware.uitest.control.interfaces.base.IElement;
import com.ggasoftware.uitest.control.new_controls.common.Text;
import com.ggasoftware.uitest.utils.*;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

import static com.ggasoftware.uitest.control.base.asserter.TestNGAsserter.asserter;
import static com.ggasoftware.uitest.control.base.logger.enums.LogInfoTypes.BUSINESS;
import static com.ggasoftware.uitest.control.base.logger.enums.LogLevels.DEBUG;
import static com.ggasoftware.uitest.utils.LinqUtils.*;
import static com.ggasoftware.uitest.utils.ReflectionUtils.*;
import static com.ggasoftware.uitest.utils.ReporterNG.logTechnical;
import static com.ggasoftware.uitest.utils.ReporterNGExt.*;
import static com.ggasoftware.uitest.utils.TestBaseWebDriver.logFindElementLocator;
import static com.ggasoftware.uitest.utils.TestBaseWebDriver.takePassedScreenshot;
import static com.ggasoftware.uitest.utils.Timer.alwaysDoneAction;
import static com.ggasoftware.uitest.utils.Timer.getResultAction;
import static com.ggasoftware.uitest.utils.WebDriverWrapper.*;
import static java.lang.String.format;

/**
 * Base Element control implementation
 *
 * @author Alexeenko Yan
 * @author Belin Yury
 * @author Belousov Andrey
 * @author Shubin Konstantin
 * @author Zharov Alexandr
 */
public class Element<ParentPanel> extends BaseElement<ParentPanel> implements IElement<ParentPanel> {


    /**
     * Contains name of the element used for locating its parameters in properties file
     */
    protected final Properties properties = new Properties();

    {
        PropertyReader.getProperties(properties, this.getClass().getName());
        String panelLocator = getProperty("main");
        if (panelLocator != null) {
            this.locator = panelLocator;
            avatar = new GetElementModule(getByLocator(), this);
        }
    }

    /**
     * Searches for the property with the specified key in this property list.
     * If the key is not found in this property list, the default property list,
     * and its defaults, recursively, are then checked. The method returns
     * <code>null</code> if the property is not found.
     *
     * @param key the property key.
     * @return the value in this property list with the specified key value.
     */
    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    protected Element() {

    }

    /**
     * Initializes element with given locator. Locates own properties of the element by class name, takes given locator and tries
     * to initialize.
     *
     * @param name    - Element name
     * @param locator - start it with locator type "id=", "css=", "xpath=" and etc. Locator without type is assigned to xpath
     * @param panel   - Parent panel instance
     */
    public Element(String name, String locator, ParentPanel panel) {
        super(name, locator, panel);
    }
    public Element(String name, By byLocator) {
        super(name, byLocator);
    }
    public Element(By byLocator) {
        super(byLocator);
    }

    /**
     * Sets locator for the element
     *
     * @param elementLocator - Locator of the element. Start it with locator type "id=", "css=", "xpath=" and etc.
     */
    protected void setLocator(String elementLocator) {
        this.locator = elementLocator;
    }

    /**
     * Sets locator for the element
     *
     * @param parentPanel - Locator of the element. Start it with locator type "id=", "css=", "xpath=" and etc.
     */
    protected void setParent(ParentPanel parentPanel) {
        this.parent = parentPanel;
    }
    public boolean waitDisplayed() { return waitDisplayed(TIMEOUT); }
    public boolean waitDisplayed(int seconds) {
        return doJActionResult("Wait element appear during '%s' seconds", () -> {
            setWaitTimeout(seconds);
            boolean result = new Timer(seconds * 1000).wait(() -> avatar.getElement().isDisplayed());
            setWaitTimeout(TIMEOUT);
            return result;
        });
    }

    public boolean waitVanished() { return waitDisplayed(TIMEOUT); }
    public boolean waitVanished(int seconds)  {
        return doJActionResult("Wait element disappear during '%s' seconds", () -> {
            setWaitTimeout(100);
            boolean result = new Timer(seconds * 1000).wait(() -> !(avatar.getElement().isDisplayed()));
            setWaitTimeout(TIMEOUT);
            return result;
        });
    }

    public static <T extends IElement> T copy(T element, By newLocator) {
        try {
            T result = (T) element.getClass().newInstance();
            result.setAvatar(newLocator, element.getAvatar());
            return result;
        } catch (Exception ex) { asserter.exception("Can't copy element: " + element); return null; }
    }

    public WebElement getWebElement() {
        return doJActionResult("Get web element " + this.toString(), avatar::getElement,
                new LogSettings(DEBUG, BUSINESS));
    }
    public WebElement getWebElement(int seconds) {
        setTimeout(seconds);
        WebElement result = doJActionResult("Get web element " + this.toString(), avatar::getElement,
                new LogSettings(DEBUG, BUSINESS));
        setTimeout(TIMEOUT);
        return result;
    }
    protected Button getButton(Functions funcName) {
        List<Field> fields = getFields(this, IButton.class);
        if (fields.size() == 1)
            return (Button) getFieldValue(fields.get(0), this);
        Collection<Button> buttons = select(fields, f -> (Button) getFieldValue(f, this));
        Button button = first(buttons, b -> b.function.equals(funcName));
        if (button == null) {
            asserter.exception(format("Can't find button '%s' for element '%s'", funcName, toString()));
            return null;
        }
        return button;
    }

    protected Text getTextElement() {
        Field textField = first(getClass().getDeclaredFields(), f -> (f.getType() == Text.class) || (f.getType() == IText.class));
        if (textField!= null) return (Text) getFieldValue(textField, this);
        asserter.exception(format("Can't find Text element '%s'", toString()));
        return null;
    }
    //  Common functions


    protected void clickActionM() { getWebElement().click(); }
    public ParentPanel click() { doJAction("Click on element", this::clickActionM); return parent; }

    /**
     * A convenience method that performs click at the location of the source element
     *
     * @param xOffset - horizontal move offset.
     * @param yOffset - vertical move offset.
     * @return Parent instance
     */
    public ParentPanel clickBy(int xOffset, int yOffset) {
        doJAction(format("click element:  horizontal move offset- %dpx; vertical move offset- %dpx", xOffset, yOffset), () -> {
            Actions builder = new Actions(getDriver());
            Action click = builder.moveToElement(getWebElement(), xOffset, yOffset).click().build();
            click.perform();
        });
        return parent;
    }

    /**
     * Replace each substring of this string "$VALUE" to [value] in [str]
     *
     * @param str   - input string for replacement
     * @param value -The replacement sequence of char values
     * @return The resulting string
     */
    protected String insertValue(String str, String value) {
        return str.replace("$VALUE", value);
    }
    protected String insertValues(String str, String[] values) {
        int i = 0;
        String result = str;
        for (String value : values)
            result = result.replace("$VALUE" + i++, value);
        return result;
    }
    /**
     * Click on the Element(WebElement) by JS
     *
     * @return Parent instance
     */
    public ParentPanel clickJS() {
        doJAction("clickJS", () -> jsExecutor().executeScript("arguments[0].click();", getWebElement()));
        return parent;
    }

    /**
     * Serialize Form by JS
     *
     * @return Serialize result
     */
    public String serializeForm() {
        return doJActionResult("serializeForm", () ->
                (String) jsExecutor().executeScript("return $(arguments[0]).serialize();", getWebElement()));
    }

    /**
     * Mouse Over on the Element(WebElement) by JS
     *
     * @return Parent instance
     */
    public ParentPanel mouseOverJS() {
        doJAction("mouseOverJS", () -> {
            String script = "var evt = document.createEvent('MouseEvents');" +
                    "evt.initMouseEvent('mouseover',true, true, window, 500, 100, 0, 0, 0, false, false, false, false, 0, null);" +
                    "arguments[0].dispatchEvent(evt);";
            jsExecutor().executeScript(script, getWebElement());
        });
        return parent;
    }

    /**
     * Click on the Element(WebElement) with pressed CTRL key
     *
     * @return Parent instance
     */
    public ParentPanel ctrlClick() {
        doJAction("ctrlClick", () ->
                new Actions(getDriver()).keyDown(Keys.CONTROL)
                        .moveToElement(getWebElement())
                        .click()
                        .keyUp(Keys.CONTROL)
                        .perform());
        return parent;
    }

    /**
     * Focus window and Click on the Element(WebElement)
     *
     * @return Parent instance
     */
    public ParentPanel focusWindowAndClick() {
        logAction(this, getParentClassName(), "focusWindowAndClick");
        getDriver().switchTo().window("");
        getWebElement().click();
        return parent;
    }

    /**
     * Performs a double-click at the current mouse location.
     *
     * @return Parent instance
     */
    public ParentPanel doubleClick() {
        getWebElement().getSize(); //for scroll to object
        logAction(this, getParentClassName(), "doubleClick");
        alwaysDoneAction(() -> {
            Actions builder = new Actions(getDriver());
            builder.doubleClick();
        });
        return parent;
    }

    /**
     * Performs a right-click at the current mouse location.
     *
     * @return Parent instance
     */
    public ParentPanel rightClick() {
        getWebElement().getSize(); //for scroll to object
        logAction(this, getParentClassName(), "rightClick");
        Actions builder = new Actions(getDriver());
        builder.contextClick(getWebElement()).perform();
        return parent;
    }

    /**
     * Clicks in the middle of the given element. Equivalent to:
     * Actions.moveToElement(onElement).click()
     *
     * @return Parent instance
     */
    public ParentPanel clickAction() {
        getWebElement().getSize(); //for scroll to object
        logAction(this, getParentClassName(), "clickAction");
        alwaysDoneAction(() -> {
            Actions builder = new Actions(getDriver());
            builder.click(getWebElement()).perform();
        });
        return parent;
    }

    public String getSimpleClassName() { return getClass().getSimpleName(); }

    /**
     * Mouse Over on the the given element.
     *
     * @return Parent instance
     */
    public ParentPanel mouseOver() {
        getWebElement().getSize(); //for scroll to object
        logAction(this, getParentClassName(), "mouseOver");
        Actions builder = new Actions(getDriver());
        builder.moveToElement(getWebElement()).build().perform();
        return parent;
    }

    /**
     * Focus on the Element(WebElement)
     *
     * @return Parent instance
     */
    public ParentPanel focus() {
        Dimension size = getWebElement().getSize(); //for scroll to object
        logAction(this, getParentClassName(), "focus");
        Actions builder = new Actions(getDriver());
        org.openqa.selenium.interactions.Action focus = builder.moveToElement(getWebElement(), size.width / 2, size.height / 2).build();
        focus.perform();
        return parent;
    }

    /**
     * Click on the Element(WebElement) until expectedElement is NOT DISPLAYED
     *
     * @param expectedElement - expected Element
     * @param tryCount - number of count for click
     * @return Parent instance
     */
    public ParentPanel clickWhileObjectNotDisplayed(Element expectedElement, int tryCount) {
        logAction(this, getParentClassName(), format("clickWhileObjectNotDisplayed: element locator '%s', element name '%s'", expectedElement.locator, expectedElement.getName()));
        int i = 0;
        while (!(expectedElement.isDisplayed())) {
            if (isDisplayed()) {
                getWebElement().click();
            } else {
                break;
            }
            i++;
            if (i >= tryCount) {
                break;
            }
        }
        return parent;
    }

    /**
     * Click on the Element(WebElement) until expectedElement exists
     *
     * @param expectedElement - expected Element
     * @param tryCount - number of count for click
     * @return Parent instance
     */
    public ParentPanel clickWhileObjectNotExist(Element expectedElement, int tryCount) {
        logAction(this, getParentClassName(),
                format("clickWhileObjectNotExist: element locator '%s', element name '%s'", expectedElement.locator, expectedElement.getName()));
        int i = 0;
        while (!(expectedElement.isExists())) {
            getWebElement().click();
            i++;
            if (i >= tryCount) {
                break;
            }
        }
        return parent;
    }

    /**
     * Click on the Element(WebElement) until expectedElement is displayed
     *
     * @param expectedElement - expected Element
     * @param tryCount - number of count for click
     * @return Parent instance
     */
    public ParentPanel clickWhileObjectIsDisplayed(Element expectedElement, int tryCount) {
        logAction(this, getParentClassName(),
                format("clickWhileObjectExist: element locator '%s', element name '%s'", expectedElement.locator, expectedElement.getName()));
        int i = 0;
        while ((expectedElement.isDisplayed())) {
            getWebElement().click();
            i++;
            if (i >= tryCount) {
                break;
            }
        }
        return parent;
    }

    /**
     * Use Clickable elements instead of Element type
     * Use this method to simulate typing into an element, which may set its value.
     *
     * @param keysToSend - CharSequence to send
     * @return Parent instance
     */
    public ParentPanel sendKeys(CharSequence... keysToSend) {
        doJAction(format("sendKeys: %s", keysToSend), () -> {
            getDriver().switchTo().activeElement();
            getWebElement().sendKeys(keysToSend);
        });
        return parent;
    }

    /**
     * Use this method to simulate typing into an element with secure log, which may set its value.
     *
     * @param keysToSend - CharSequence to send
     * @return Parent instance
     */
    public ParentPanel sendKeysSecure(CharSequence... keysToSend) {
        logAction(this, getParentClassName(), "sendKeysSecure");
        alwaysDoneAction(() -> {
            getDriver().switchTo().activeElement();
            getWebElement().sendKeys(keysToSend);
        });
        return parent;
    }

    /**
     * Use this method to simulate send keys to the element.
     *
     * @param sendKeys - e.g. sendKeys(Keys.ARROW_DOWN)
     * @return Parent instance
     */
    protected ParentPanel sendKeys(Keys sendKeys) {
        logAction(this, getParentClassName(), format("sendKeys - %s", sendKeys));
        alwaysDoneAction(() -> {
            getDriver().switchTo().activeElement();
            getWebElement().sendKeys(sendKeys);
        });
        return parent;
    }

    /**
     * Is this element exists (on the web page) or not?
     *
     * @return true if we can find Element on the web page, otherwise false
     */
    public boolean isExists() {
        if (logFindElementLocator) {
            logTechnical(format("Find Elements '%s'", locator));
        }
        return !getDriver().findElements(avatar.byLocator).isEmpty();
    }

    /**
     * Is this element exists (on the web page) or not?
     *
     * @param seconds to wait until element become existed.
     * @return true if we can find Element on the web page, otherwise false
     */
    public boolean isExists(int seconds) {
        setTimeout(seconds);
        boolean found = isExists();
        setTimeout(TIMEOUT);
        return found;
    }

    /**
     * Is this element vanished from the web page
     *
     * @return true if element not exists or not displayed at the page
     */
    public boolean isVanished() {
        setTimeout(0);
        boolean result = !isExists(0) || !(getWebElement().isDisplayed());;
        setTimeout(TIMEOUT);
        return result;
    }

    /**
     * Is this element displayed or not? This method avoids the problem of having to parse an
     * element's "style" attribute.
     *
     * @return Whether or not the element is displayed
     */
    public boolean isDisplayed() {
        return isDisplayed(TIMEOUT);
    }


    /**
     * Is this element displayed or not? This method avoids the problem of having to parse an
     * element's "style" attribute.
     *
     * @param seconds to wait until element become visible or undiscovered.
     * @return Whether or not the element is displayed
     */
    public boolean isDisplayed(int seconds) {
        setTimeout(seconds);
        boolean result = isExists(seconds) && getWebElement().isDisplayed();
        setTimeout(TIMEOUT);
        return result;
    }

    /**
     * Is the element currently enabled or not? This will generally return true for everything but
     * disabled input elements.
     *
     * @return True if the element is enabled, false otherwise.
     */
    public boolean isEnabled() {
        return isEnabled(TIMEOUT);
    }

    /**
     * Is the element currently enabled or not? This will generally return true for everything but
     * disabled input elements.
     *
     * @param seconds to wait until element become enable or undiscovered.
     * @return True if the element is enabled, false otherwise.
     */
    public boolean isEnabled(int seconds) {
        setTimeout(seconds);
        boolean result = isExists(seconds) && getWebElement().isEnabled();
        setTimeout(TIMEOUT);
        return result;
    }

    /**
     * Get the visible (i.e. not hidden by CSS) innerText of this element, including sub-elements,
     * without any leading or trailing whitespace.
     *
     * @return The innerText of this element.
     */
    public String getText() {
        String result = getResultAction(() -> getWebElement().getText());
        logAction(this, getParentClassName(), format("got ext : %s", result));
        return result;
    }

    /**
     * Get the visible (i.e. not hidden by CSS) innerText of this element, excluding sub-elements,
     * without any leading or trailing whitespace.
     *
     * @return The innerText of this element.
     */
    public String getElementText() {
        int l = 0;
        for(WebElement webElement:getChild()){
            l=+webElement.getText().length();
        }
        return (String) logGetter(this, getParentClassName(), "element text", getWebElement().getText().substring(l));
    }

    /**
     * !!! Use waitAttribute(String name, String value) instead
     * Get the value of a the given attribute of the element. Will return the current value, even if
     * this has been modified after the page has been loaded.
     *
     * @param sName The name of the attribute.
     * @return The attribute's current value or null if the value is not set.
     */
    public String getAttribute(String sName) {
        return (String) logGetter(this, getParentClassName(), sName, getWebElement().getAttribute(sName));
    }
    public boolean waitAttribute(String name, String value) {
        return doJActionResult(format("Wait attribute %s='%s'", name, value),
                () -> getWebElement().getAttribute(name).equals(value));
    }
    public boolean waitAttributeChanged(String name, String value) {
        return doJActionResult(format("Wait attribute %s='%s' changed", name, value),
                () -> !getWebElement().getAttribute(name).equals(value));
    }
    /**
     * Set the value of a the given attribute of the element by JS.
     *
     * @param attribute The name of the attribute.
     * @param value value The value of the attribute.
     * @return Parent instance
     *
     */
    public ParentPanel setAttributeJS(String attribute, String value) {
        ((JavascriptExecutor) getDriver()).executeScript(format("arguments[0].setAttribute('%s',arguments[1]);", attribute),
                getWebElement(),
                value);
        return parent;
    }

    /**
     * Get the value of a given CSS property. This is probably not going to return what you expect it
     * to unless you've already had a look at the element using something like firebug. Seriously,
     * even then you'll be lucky for this to work cross-browser. Colour values should be returned as
     * hex strings, so, for example if the "background-color" property is set as "green" in the HTML
     * source, the returned value will be "#008000"
     *
     * @param name - css name
     * @return The current, computed value of the property.
     */
    public String getCssValue(String name) {
        return (String) logGetter(this, getParentClassName(), name, getWebElement().getCssValue(name));
    }

    /**
     * Where on the page is the top left-hand corner of the rendered element?
     * We can use also:
     * - getLocation().x or getLocation().getX
     * - getLocation().y or getLocation().getY
     *
     * @return A point, containing the location of the top left-hand corner of the element
     */
    public Point getLocation() {
        return (Point) logGetter(this, getParentClassName(), "Location", getWebElement().getLocation());
    }

    /**
     * What is the width and height of the rendered element?
     * We can use also:
     * - getWebElement().getSize().width or getWebElement().getSize().getWidth()
     * - getWebElement().getSize().height or getWebElement().getSize().getHeight()
     *
     * @return The size of the element on the page.
     */
    public Dimension getSize() {
        return (Dimension) logGetter(this, getParentClassName(), "Dimension", getWebElement().getSize());
    }

    /**
     * Get the tag name of this element. <b>Not</b> the value of the name attribute: will return
     * <code>"input"</code> for the element <code>&lt;input name="foo" /&gt;</code>.
     *
     * @return The tag name of this element.
     */
    public String getTagName() {
        return (String) logGetter(this, getParentClassName(), "TagName", getWebElement().getTagName());
    }

    /**
     * Has child element with specified tag
     *
     * @param tagName Tag name
     * @return Has child or not
     */
    public boolean hasChildByTag(String tagName) {
        return (Boolean) logGetter(this, getParentClassName(), "HasChildByTag", !getWebElement().findElements(By.tagName(tagName)).isEmpty());
    }

    /**
     * Has child element
     *
     * @return Has child or not
     */
    public boolean hasChild() {
        return (Boolean) logGetter(this, getParentClassName(), "HasChild", !getWebElement().findElements(By.xpath(".//*")).isEmpty());
    }

    /**
     * Get child elements
     *
     * @return Has child or not
     */
    public List<WebElement> getChild() {
        if (logFindElementLocator) {
            logTechnical(format("Get Child Web Elements '%s'", locator));
        }
        return getWebElement().findElements(By.xpath(".//*"));
    }

    /**
     * Get full xpath string
     *
     * @return Xpath of the element
     */
    public String getXPath() {
        String sLocator = locator.replaceAll("\\w*=(.*)", "$1").trim();
        String sType = locator.replaceAll("(\\w*)=.*", "$1").trim();
        switch (sType) {
            case "css":
                return "";
            case "id":
                return format("//*[@id=\"%s\"]", sLocator);
            case "link":
                return format("//*[@link=\"%s\"]", sLocator);
            case "xpath":
                return format("%s", sLocator);
            case "text":
                return format("//*[contains(text(), '%s')]", sLocator);
            case "name":
                return format("//*[@name=\"%s\"]", sLocator);
            default:
                return locator;
        }
    }

    /**
     * Select some area (mouse from point(x1, y1) to point(x2, y2) in element)
     *
     * @param x1 - x1 coordinate of element
     * @param y1 - y1 coordinate of element
     * @param x2 - x2 coordinate of element
     * @param y2 - y2 coordinate of element
     * @return Parent instance
     */
    public ParentPanel selectArea(int x1, int y1, int x2, int y2) {
        logAction(this, getParentClassName(), format("Select area: from %d,%d;to %d,%d", x1, y1, x2, y2));
        WebElement element = getWebElement();
        new Actions(getDriver()).moveToElement(element, x1, y1)
                .clickAndHold()
                .moveToElement(element, x2, y2)
                .release()
                .build()
                .perform();
        return parent;
    }

    /**
     * A convenience method that performs click-and-hold at the location of the source element, moves by a given offset, then releases the mouse.
     *
     * @param xOffset - horizontal move offset.
     * @param yOffset - vertical move offset.
     * @return Parent instance
     */
    public ParentPanel dragAndDropBy(int xOffset, int yOffset) {
        logAction(this, getParentClassName(), format("Drag and drop element: horizontal move offset - %dpx; vertical move offset - %dpx", xOffset, yOffset));

        Actions builder = new Actions(getDriver());

        Action dragAndDropBy = builder.dragAndDropBy(getWebElement(), xOffset, yOffset).build();
        dragAndDropBy.perform();
        return parent;
    }

    /**
     * Wait until element exists.
     *
     * @param timeoutSec seconds to wait until element exists.
     * @param checkCondition log assert for expected conditions.
     * @return Parent instance
     */
    public ParentPanel waitForExists(int timeoutSec, boolean checkCondition) {
        boolean isExists;
        logAction(this, getParentClassName(), format("waitForExists: %s", locator));
        long start = System.currentTimeMillis() / 1000;
        WebDriverWait wait = (WebDriverWait) new WebDriverWait(getDriver(), timeoutSec)
                .ignoring(StaleElementReferenceException.class);
        setTimeout(1);
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(avatar.byLocator));
            isExists = true;
        } catch (TimeoutException e) {
            logTechnical(format("waitForExists: [ %s ] during: [ %d ] sec ", locator, System.currentTimeMillis() / 1000 - start));
            isExists = false;
        }
        setTimeout(TIMEOUT);
        if (checkCondition) {
            logAssertTrue(BUSINESS_LEVEL, isExists,
                    format("waitForExists - '%s' should exist", getName()), takePassedScreenshot);
        }
        return parent;
    }

    /**
     * Wait until element exists.
     *
     * @param timeoutSec seconds to wait until element exists.
     * @return Parent instance
     */
    public ParentPanel waitForExists(int timeoutSec) {
        return waitForExists(timeoutSec, CHECKCONDITION);
    }
    /**
     * Wait until element exists.
     *
     * @return Parent instance
     */
    public ParentPanel waitForExists() {
        return waitForExists(TIMEOUT);
    }
    /**
     * Wait until element exists.
     *
     * @param checkCondition log assert for expected conditions.
     * @return Parent instance
     */
    public ParentPanel waitForExists(boolean checkCondition) {
        return waitForExists(TIMEOUT, checkCondition);
    }

    /**
     * Wait until element is displayed.
     *
     * @param timeoutSec seconds to wait until element is displayed.
     * @param checkCondition log assert for expected conditions.
     * @return Parent instance
     */
    private ParentPanel waitForDisplayed(int timeoutSec, boolean checkCondition) {
        boolean isDisplayed;
        logAction(this, getParentClassName(), format("waitForDisplayed: %s", locator));
        long start = System.currentTimeMillis() / 1000;
        WebDriverWait wait = (WebDriverWait) new WebDriverWait(getDriver(), timeoutSec)
                .ignoring(StaleElementReferenceException.class);
        setTimeout(1);
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(avatar.byLocator));
            isDisplayed = true;
        } catch (TimeoutException e) {
            logTechnical(format("waitForDisplayed: [ %s ] during: [ %d ] sec ", locator, System.currentTimeMillis() / 1000 - start));
            isDisplayed = false;
        }
        setTimeout(TIMEOUT);
        if (checkCondition) {
            logAssertTrue(BUSINESS_LEVEL, isDisplayed,
                    format("waitForDisplayed - '%s' should be displayed", getName()), takePassedScreenshot);
        }
        return parent;
    }
    /**
     * Wait until element is displayed.
     *
     * @param timeoutSec seconds to wait until element is displayed.
     * @return Parent instance
     */
    public ParentPanel waitForDisplayed(int timeoutSec) {
        return waitForDisplayed(timeoutSec, CHECKCONDITION);
    }
    /**
     * Wait until element is displayed.
     *
     * @return Parent instance
     */
    public ParentPanel waitForDisplayed() {
        return waitForDisplayed(TIMEOUT);
    }
    /**
     * Wait until element is displayed.
     *
     * @param checkCondition log assert for expected conditions.
     * @return Parent instance
     */
    public ParentPanel waitForDisplayed(boolean checkCondition) {
        return waitForDisplayed(TIMEOUT, checkCondition);
    }

    /**
     * Wait until element is vanished.
     *
     * @return Parent instance
     */
    public ParentPanel waitForElementToVanish() {
        return waitForElementToVanish(TIMEOUT, CHECKCONDITION);
    }

    /**
     * Wait until element is vanished.
     * @param timeoutSec -  the maximum time to wait in seconds (until element become invisible or disappeared)
     *
     * @return Parent instance
     */
    public ParentPanel waitForElementToVanish(int timeoutSec) {
        return waitForElementToVanish(timeoutSec, CHECKCONDITION);
    }

    /**
     * Wait until element is vanished.
     * @param checkCondition log assert for expected conditions.
     *
     * @return Parent instance
     */
    public ParentPanel waitForElementToVanish(boolean checkCondition) {
        return waitForElementToVanish(TIMEOUT, CHECKCONDITION);
    }

    /**
     * Wait until element is vanished.
     *
     * @param timeoutSec -  the maximum time to wait in seconds (until element become invisible or disappeared)
     * @param checkCondition log assert for expected conditions.
     * @return Parent instance
     */
    public ParentPanel waitForElementToVanish(int timeoutSec, boolean checkCondition) {
        boolean isVanished;
        logAction(this, getParentClassName(), format("waitForElementToVanish: %s", locator));
        long start = System.currentTimeMillis() / 1000;
        WebDriverWait wait = (WebDriverWait) new WebDriverWait(getDriver(), timeoutSec)
                .ignoring(StaleElementReferenceException.class, NoSuchElementException.class);
        setTimeout(1);
        try {
            isVanished = wait.until(ExpectedConditions.invisibilityOfElementLocated(avatar.byLocator));
        } catch (TimeoutException e) {
            logTechnical(format("waitForElementToVanish: [ %s ] during: [ %d ] sec ", locator, System.currentTimeMillis() / 1000 - start));
            isVanished = false;
        }
        setTimeout(TIMEOUT);
        if (checkCondition){
            logAssertTrue(BUSINESS_LEVEL, isVanished,
                    format("waitForElementToVanish - '%s' should be vanished", getName()), takePassedScreenshot);
        }
        return parent;
    }

    /**
     * Wait until element has a text.
     *
     * @param text Text of Element
     * @return Parent instance
     */
    public ParentPanel waitForText(final String text) {
        return waitForText(text, TIMEOUT, CHECKCONDITION);
    }

    /**
     * Wait until element has a text.
     *
     * @param text Text of Element
     * @param timeoutSec seconds to wait until element has a text
     * @return Parent instance
     */
    public ParentPanel waitForText(final String text, final int timeoutSec) {
        return waitForText(text, timeoutSec, CHECKCONDITION);
    }

    /**
     * Wait until element has a text.
     *
     * @param text Text of Element
     * @param timeoutSec seconds to wait until element has a text
     * @param checkCondition log assert for expected conditions.
     * @return Parent instance
     */
    public ParentPanel waitForText(final String text, final int timeoutSec, final boolean checkCondition) {
        boolean isPresent;
        logAction(this, getParentClassName(), format("waitForText[%s]: %s", text, locator));
        long start = System.currentTimeMillis() / 1000;
        WebDriverWait wait = (WebDriverWait) new WebDriverWait(getDriver(), timeoutSec)
                .ignoring(StaleElementReferenceException.class);
        try {
            wait.until(ExpectedConditions.textToBePresentInElementLocated(avatar.byLocator, text));
            getText();
            isPresent = wait.until(
                    new ExpectedCondition<Boolean>() {
                        @Override
                        public Boolean apply(WebDriver driver) {
                            return getDriver().findElement(avatar.byLocator).getText().equals(text);
                        }
                    }
            );
        } catch (TimeoutException e) {
            logTechnical(format("waitForText: [ %s ] during: [ %d ] sec ", locator, System.currentTimeMillis() / 1000 - start));
            isPresent = false;
        }
        if (checkCondition){
            logAssertTrue(BUSINESS_LEVEL, isPresent,
                    format("waitForText - '%s' should has a text '%s'", getName(), text), takePassedScreenshot);
        }
        return parent;
    }

    /**
     * Wait until element contains a text.
     *
     * @param text Text of Element
     * @return Parent instance
     */
    public ParentPanel waitForTextContains(final String text) {
        return waitForTextContains(text, TIMEOUT, CHECKCONDITION);
    }
    /**
     * Wait until element contains a text.
     *
     * @param text Text of Element
     * @param timeoutSec seconds to wait until element contains a text
     * @return Parent instance
     */
    public ParentPanel waitForTextContains(final String text, final int timeoutSec) {
        return waitForTextContains(text, timeoutSec, CHECKCONDITION);
    }
    /**
     * Wait until element contains a text.
     *
     * @param text Text of Element
     * @param timeoutSec seconds to wait until element contains a text
     * @param checkCondition log assert for expected conditions.
     * @return Parent instance
     */
    public ParentPanel waitForTextContains(final String text, final int timeoutSec, final boolean checkCondition) {
        boolean isPresent;
        logAction(this, getParentClassName(), format("waitForTextContains[%s]: %s", text, locator));
        long start = System.currentTimeMillis() / 1000;
        final WebDriverWait wait = (WebDriverWait) new WebDriverWait(getDriver(), timeoutSec)
                .ignoring(StaleElementReferenceException.class);
        try {
            isPresent = wait.until(ExpectedConditions.textToBePresentInElementLocated(avatar.byLocator, text));
        } catch (TimeoutException e) {
            logTechnical(format("waitForTextContains: [ %s ] during: [ %d ] sec ", locator, System.currentTimeMillis() / 1000 - start));
            isPresent = false;
        }
        if (checkCondition){
            logAssertTrue(BUSINESS_LEVEL, isPresent,
                    format("waitForTextContains - '%s' should has a text contains '%s'", getName(), text), takePassedScreenshot);
        }
        return parent;
    }

    /**
     * Wait until element is changed text.
     *
     * @param text before change
     * @return Parent instance
     */
    public ParentPanel waitForTextChanged(final String text) {
        return waitForTextChanged(text, TIMEOUT, CHECKCONDITION);
    }

    /**
     * Wait until element is changed text.
     *
     * @param text before change
     * @param timeoutSec seconds to wait until element is changed text
     * @return Parent instance
     */
    public ParentPanel waitForTextChanged(final String text, final int timeoutSec) {
        return waitForTextChanged(text, timeoutSec, CHECKCONDITION);
    }

    /**
     * Wait until element is changed text.
     *
     * @param text before change
     * @param timeoutSec seconds to wait until element is changed text
     * @param checkCondition log assert for expected conditions.
     * @return Parent instance
     */
    public ParentPanel waitForTextChanged(final String text, final int timeoutSec, final boolean checkCondition) {
        boolean isChanged;
        logAction(this, getParentClassName(), format("waitForTextChanged[%s]: %s", text, locator));
        long start = System.currentTimeMillis() / 1000;
        WebDriverWait wait = (WebDriverWait) new WebDriverWait(getDriver(), timeoutSec)
                .ignoring(StaleElementReferenceException.class);
        try {
            isChanged = wait.until(ExpectedConditions.not(ExpectedConditions.textToBePresentInElementLocated(avatar.byLocator, text)));
        } catch (TimeoutException e) {
            logTechnical(format("waitForTextChanged: [ %s ] during: [ %d ] sec ", locator, System.currentTimeMillis() / 1000 - start));
            isChanged = false;
        }
        if (checkCondition){
            logAssertTrue(BUSINESS_LEVEL, isChanged,
                    format("waitForTextChanged - '%s' text '%s' should be changed", getName(), text), takePassedScreenshot);
        }
        return parent;
    }

    /**
     * Wait until element has a 'value' attribute.
     *
     * @param value 'Value' Attribute of Element
     * @return Parent instance
     */
    public ParentPanel waitForValue(final String value) {
        return waitForText(value, TIMEOUT, CHECKCONDITION);
    }

    /**
     * Wait until element has a 'value' attribute.
     *
     * @param value 'Value' Attribute of Element
     * @param timeoutSec seconds to wait until element has a 'value' attribute
     * @return Parent instance
     */
    public ParentPanel waitForValue(final String value, int timeoutSec) {
        return waitForText(value, timeoutSec, CHECKCONDITION);
    }

    /**
     * Wait until element has a 'value' attribute.
     *
     * @param value 'Value' Attribute of Element
     * @param timeoutSec seconds to wait until element has a 'value' attribute
     * @param checkCondition log assert for expected conditions.
     * @return Parent instance
     */
    public ParentPanel waitForValue(final String value, final int timeoutSec, final boolean checkCondition) {
        boolean isPresent;
        logAction(this, getParentClassName(), format("waitForValueAttribute[%s]: %s", value, locator));
        long start = System.currentTimeMillis() / 1000;
        WebDriverWait wait = (WebDriverWait) new WebDriverWait(getDriver(), timeoutSec)
                .ignoring(StaleElementReferenceException.class);
        try {
            isPresent = wait.until(ExpectedConditions.textToBePresentInElementValue(avatar.byLocator, value));
        } catch (TimeoutException e) {
            logTechnical(format("waitForValueAttribute: [ %s ] during: [ %d ] sec ", locator, System.currentTimeMillis() / 1000 - start));
            isPresent = false;
        }
        if (checkCondition){
            logAssertTrue(BUSINESS_LEVEL, isPresent,
                    format("waitForValueAttribute - '%s' should has a value attribute '%s'", getName(), value), takePassedScreenshot);
        }
        return parent;
    }

    /**
     * !!! Use waitAttribute(String name, String value)*
     * Wait until element is changed the attribute.
     *
     * @param attribute  for watching
     * @param value      of attribute before change
     * @param timeoutSec seconds to wait until element is changed attribute
     * @return Parent instance
     */
    public ParentPanel waitForAttributeChanged(final String attribute, final String value, final int timeoutSec) {
        return waitForAttributeChanged(attribute, value, timeoutSec, true);
    }

    /**
     * !!! Use waitAttribute(String name, String value)
     * Wait until element is changed the attribute.
     *
     * @param attribute  for watching
     * @param value      of attribute before change
     * @param timeoutSec seconds to wait until element is changed attribute
     * @param checkCondition log assert for expected conditions.
     * @return Parent instance
     */
    public ParentPanel waitForAttributeChanged(final String attribute, final String value, final int timeoutSec, final boolean checkCondition) {
        setTimeout(timeoutSec);
        boolean result = waitAttribute(attribute, value);
        logAssertTrue(BUSINESS_LEVEL, result,
                format("waitForAttributeChanged - '%s' attribute '%s' value '%s' should be changed", getName(), attribute, value), takePassedScreenshot);
        setTimeout(TIMEOUT);

        if (checkCondition){
            logAssertTrue(ReporterNGExt.BUSINESS_LEVEL, result,
                format("waitForAttributeChanged - '%s' attribute '%s' value '%s' should be changed", name, attribute, value),
                    takePassedScreenshot);
        }
        return parent;
    }

    /**
     * Firstly :Wait until element exists, then: Wait until element is vanished.
     *
     * @param checkCondition log assert for expected conditions.
     * @return Parent instance
     */
    public ParentPanel waitForExistsThenVanish(final boolean checkCondition) {
        logAction(this, getParentClassName(), format("waitForExistsThenVanish:%s", locator));
        waitForExists(checkCondition);
        waitForElementToVanish(checkCondition);
        return parent;
    }

    /**
     * Firstly :Wait until element exists, then: Wait until element is vanished.
     *
     * @return Parent instance
     */
    public ParentPanel waitForExistsThenVanish() {
        logAction(this, getParentClassName(), format("waitForExistsThenVanish:%s", locator));
        waitForExists();
        waitForElementToVanish();
        return parent;
    }

    /**
     * Get visible WebElement with Element locator.
     *
     * @return WebElement
     */
    public WebElement getVisibleWebElement() {
        Elements elements = new Elements<>(getName(), locator, parent);
        return elements.getVisibleWebElement();
    }

    /**
     * Wait until element is clickable and click at it.
     *
     * @param timeoutSec seconds to wait until element become clickable.
     * @return Parent instance
     */
    public ParentPanel waitForClickableAndClick(final int timeoutSec) {
        boolean isClicked;
        logAction(this, getParentClassName(), format("waitForClickable: %s", locator));
        long start = System.currentTimeMillis() / 1000;
        WebDriverWait wait = (WebDriverWait) new WebDriverWait(getDriver(), timeoutSec)
                .ignoring(StaleElementReferenceException.class);
        try {
            wait.until(ExpectedConditions.elementToBeClickable(avatar.byLocator));
            isClicked = wait.until(
                    new ExpectedCondition<Boolean>() {
                        @Override
                        public Boolean apply(WebDriver driver) {
                            try {
                                setTimeout(timeoutSec);
                                getWebElement().click();
                                setTimeout(TIMEOUT);
                                return true;
                            } catch (Exception e) {
                                return false;
                            }
                        }
                    }
            );
        } catch (TimeoutException e) {
            logTechnical(format("waitForClickable: [ %s ] during: [ %d ] sec ", locator, System.currentTimeMillis() / 1000 - start));
            isClicked = false;
        }
        logAssertTrue(BUSINESS_LEVEL, isClicked,
                format("waitForClickableAndClick: '%s' was clickable and click at it", getName()), takePassedScreenshot);
        return parent;
    }

    /**
     * Wait until Expected Condition.
     *
     * @param condition - Expected Condition
     * @return Parent instance
     */
    public ParentPanel waitForExpectedConditions(final ExpectedCondition<Boolean> condition) {
        return waitForExpectedConditions(condition, TIMEOUT, CHECKCONDITION);
    }

    /**
     * Wait until Expected Condition.
     *
     * @param condition - Expected Condition
     * @param timeoutSec - the maximum time to wait in seconds
     * @return Parent instance
     */
    public ParentPanel waitForExpectedConditions(final ExpectedCondition<Boolean> condition, final int timeoutSec) {
        return waitForExpectedConditions(condition, timeoutSec, CHECKCONDITION);
    }

    /**
     * Wait until Expected Condition.
     *
     * @param condition - Expected Condition
     * @param timeoutSec - the maximum time to wait in seconds
     * @param checkCondition log assert for expected conditions.
     * @return Parent instance
     */
    public ParentPanel waitForExpectedConditions(final ExpectedCondition<Boolean> condition, final int timeoutSec, final boolean checkCondition) {
        boolean isTrue;
        logAction(this, getParentClassName(), format("waitForExpectedCondition[%s}: %s", condition, locator));
        long start = System.currentTimeMillis() / 1000;
        WebDriverWait wait = (WebDriverWait) new WebDriverWait(getDriver(), timeoutSec)
                .ignoring(StaleElementReferenceException.class);
        setTimeout(1);
        try {
            wait.until(condition);
            isTrue = false;
        } catch (TimeoutException e) {
            logTechnical(format("waitForExpectedCondition: [ %s ] during: [ %d ] sec ", locator, System.currentTimeMillis() / 1000 - start));
            isTrue = true;
        }
        setTimeout(TIMEOUT);
        if (checkCondition){
            logAssertFalse(BUSINESS_LEVEL, isTrue,
                    format("waitForExpectedCondition - '%s'", condition), takePassedScreenshot);
        }
        return parent;
    }

}
