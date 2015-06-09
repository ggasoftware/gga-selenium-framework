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

import com.ggasoftware.uitest.utils.*;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Properties;

import static com.ggasoftware.uitest.utils.LinqUtils.where;
import static com.ggasoftware.uitest.utils.ReporterNG.logTechnical;
import static com.ggasoftware.uitest.utils.ReporterNGExt.logAction;
import static com.ggasoftware.uitest.utils.ReporterNGExt.logGetter;
import static com.ggasoftware.uitest.utils.TestBaseWebDriver.logFindElementLocator;
import static com.ggasoftware.uitest.utils.TestBaseWebDriver.simpleClassName;
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
public class Element<ParentPanel> {

    /**
     * Name of the element for Report
     */
    protected String name;

    /**
     * Locator of the element if applicable
     */
    protected String locator;
    /**
     * Locator of the element if applicable
     */
    protected By bylocator;

    /**
     * Contains name of the element used for locating its parameters in properties file
     */
    protected final Properties properties = new Properties();

    {
        PropertyReader.getProperties(properties, this.getClass().getName());
        String panelLocator = getProperty("main");
        if (panelLocator != null) {
            this.locator = panelLocator;
            this.bylocator = getByLocator();
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


    /**
     * Parent panel which contains current element
     */
    protected ParentPanel parent;


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
        this.name = name;
        this.locator = locator;
        this.bylocator = getByLocator();
        this.parent = panel;
    }

    public Element(String name, By byLocator) {
        this.name = name;
        this.bylocator = byLocator;
        this.locator = byLocator.toString();
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

    /**
     * Replace each substring of this string "$VALUE0..N" to [value] in [str]
     *
     * @param str    - input string for replacement
     * @param values -The replacement sequence of char values
     * @return The resulting string
     */
    protected String insertValues(String str, String[] values) {
        int i = 0;
        String s = str;
        for (String value : values) {
            s = s.replace("$VALUE" + (i++), value);
        }
        return s;
    }

    /**
     * Return new Element instance
     *
     * @param locator - start it with locator type "id=", "css=", "xpath=" and etc. Locator without type is assigned to xpath
     * @return New Element which has the same name, parent, and new locator
     */
    protected Element<ParentPanel> getElement(String locator) {
        return new Element<>(getName(), locator, parent);
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

    /**
     * Gets element's locator
     *
     * @return Locator of the element
     */
    public String getLocator() {
        return locator;
    }

    /**
     * Gets element's By locator
     *
     * @return By Locator of the element
     */
    public By getByLocator() {
        String locator_body = locator.replaceAll("[\\w\\s]*=(.*)", "$1").trim();
        String type = locator.replaceAll("([\\w\\s]*)=.*", "$1").trim();
        switch (type) {
            case "css":
                return By.cssSelector(locator_body);
            case "id":
                return By.id(locator_body);
            case "link":
                return By.linkText(locator_body);
            case "xpath":
                return By.xpath(locator_body);
            case "text":
                return By.xpath(format("//*[contains(text(), '%s')]", locator_body));
            case "name":
                return By.name(locator_body);
            default:
                return By.xpath(locator);
        }
    }

    /**
     * Get simple element class name
     *
     * @return class name string
     */
    protected String getSimpleClassName() {
        return this.getClass().getSimpleName();
    }

    /**
     * Get Parent Class Name
     *
     * @return Parent Canonical Class Name
     */
    protected String getParentClassName() {
        if (parent == null) {
            return "";
        }
        if (simpleClassName) {
            return parent.getClass().getSimpleName();
        }
        return parent.getClass().getCanonicalName();
    }

    /**
     * Find webelement from web page. We use locator for this. Where locator -
     * start it with locator type "id=", "css=", "xpath=" and etc. Locator
     * without type is assigned to xpath
     *
     * @return WebElement
     */
    private TestBaseWebDriver checker = new TestBaseWebDriver();
    private static final String failedToFindElementMessage = "Can't find element by locator '%s' during %s seconds";
    private static final String findToMuchElementsMessage = "Find %s elements instead of one by locator '%s' during %s seconds";

    public WebElement getWebElement() {
        return getWebElement(TIMEOUT);
    }

    /**
     * Find webelement from web page. We use locator for this. Where locator -
     * start it with locator type "id=", "css=", "xpath=" and etc. Locator
     * without type is assigned to xpath
     *
     * @param timeouInSec to wait until element found.
     * @return WebElement
     */
    public WebElement getWebElement(int timeouInSec) {
        setTimeout(timeouInSec);
        if (logFindElementLocator) {
            logTechnical(format("Get Web Element '%s'", locator));
        }
        List<WebElement> result = new Timer(timeouInSec * 1000).getByCondition(
                () -> getDriver().findElements(bylocator),
                els -> where(els, WebElement::isDisplayed).size() > 0);
        setTimeout(TIMEOUT);
        if (result == null) {
            checker.assertTrue(false, format(failedToFindElementMessage, bylocator, TIMEOUT));
            return null;
        }
        if (result.size() > 1) {
            checker.assertTrue(false, format(findToMuchElementsMessage, result.size(), bylocator, TIMEOUT));
            return null;
        }
        return result.get(0);
    }

    //  Common functions

    /**
     * Click on the Element(WebElement)
     *
     * @return Parent instance
     */
    public ParentPanel click() {
        logAction(this, getParentClassName(), "click");
        alwaysDoneAction(() -> getWebElement().click());
        return parent;
    }

    /**
     * A convenience method that performs click at the location of the source element
     *
     * @param xOffset - horizontal move offset.
     * @param yOffset - vertical move offset.
     * @return Parent instance
     */
    public ParentPanel clickBy(int xOffset, int yOffset) {
        logAction(this, getParentClassName(), format("click element:  horizontal move offset- %dpx; vertical move offset- %dpx", xOffset, yOffset));
        alwaysDoneAction(() -> {
            Actions builder = new Actions(getDriver());
            Action click = builder.moveToElement(getWebElement(), xOffset, yOffset).click().build();
            click.perform();
        });
        return parent;
    }

    private JavascriptExecutor jsExecutor() { return (JavascriptExecutor) getDriver(); }
    
    /**
     * Click on the Element(WebElement) by JS
     *
     * @return Parent instance
     */
    public ParentPanel clickJS() {
        logAction(this, getParentClassName(), "clickJS");
        alwaysDoneAction(() -> jsExecutor().executeScript("arguments[0].click();", getWebElement()));
        return parent;
    }

    /**
     * Serialize Form by JS
     *
     * @return Serialize result
     */
    public String serializeForm() {
        logAction(this, getParentClassName(), "serializeForm");
        return (String) jsExecutor().executeScript("return $(arguments[0]).serialize();", getWebElement());
    }

    /**
     * Mouse Over on the Element(WebElement) by JS
     *
     * @return Parent instance
     */
    public ParentPanel mouseOverJS() {
        logAction(this, getParentClassName(), "mouseOverJS");
        String script = "var evt = document.createEvent('MouseEvents');" +
                "evt.initMouseEvent('mouseover',true, true, window, 500, 100, 0, 0, 0, false, false, false, false, 0, null);" +
                "arguments[0].dispatchEvent(evt);";
        jsExecutor().executeScript(script, getWebElement());
        return parent;
    }

    /**
     * Click on the Element(WebElement) with pressed CTRL key
     *
     * @return Parent instance
     */
    public ParentPanel ctrlClick() {
        logAction(this, getParentClassName(), "ctrlClick");
        Actions builder = new Actions(getDriver());
        builder.keyDown(Keys.CONTROL)
                .moveToElement(getWebElement())
                .click()
                .keyUp(Keys.CONTROL)
                .perform();
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
        logAction(this, getParentClassName(), format("clickWhileObjectNotDisplayed: element locator '%s', element name '%s'", expectedElement.locator, expectedElement.name));
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
        logAction(this, getParentClassName(), format("clickWhileObjectNotExist: element locator '%s', element name '%s'", expectedElement.locator, expectedElement.name));
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
        logAction(this, getParentClassName(), format("clickWhileObjectExist: element locator '%s', element name '%s'", expectedElement.locator, expectedElement.name));
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
     * Use this method to simulate typing into an element, which may set its value.
     *
     * @param keysToSend - CharSequence to send
     * @return Parent instance
     */
    public ParentPanel sendKeys(CharSequence... keysToSend) {
        logAction(this, getParentClassName(), format("sendKeys - %s", keysToSend));
        alwaysDoneAction(() -> {
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
        return !getDriver().findElements(bylocator).isEmpty();
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
        return !isExists(0) || !(getWebElement(0).isDisplayed());
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
        return isExists(seconds) && getWebElement(seconds).isDisplayed();
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
        return isExists(seconds) && getWebElement(seconds).isEnabled();
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
     * Get the value of a the given attribute of the element. Will return the current value, even if
     * this has been modified after the page has been loaded.
     *
     * @param sName The name of the attribute.
     * @return The attribute's current value or null if the value is not set.
     */
    public String getAttribute(String sName) {
        return (String) logGetter(this, getParentClassName(), sName, getWebElement().getAttribute(sName));
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
     * Get Control Name
     *
     * @return Control Name
     */
    public String getName() {
        return name;
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
            wait.until(ExpectedConditions.presenceOfElementLocated(bylocator));
            isExists = true;
        } catch (TimeoutException e) {
            logTechnical(format("waitForExists: [ %s ] during: [ %d ] sec ", locator, System.currentTimeMillis() / 1000 - start));
            isExists = false;
        }
        setTimeout(TIMEOUT);
        if (checkCondition) {
            ReporterNGExt.logAssertTrue(ReporterNGExt.BUSINESS_LEVEL, isExists, format("waitForExists - '%s' should exist", name), TestBaseWebDriver.takePassedScreenshot);
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
            wait.until(ExpectedConditions.visibilityOfElementLocated(bylocator));
            isDisplayed = true;
        } catch (TimeoutException e) {
            logTechnical(format("waitForDisplayed: [ %s ] during: [ %d ] sec ", locator, System.currentTimeMillis() / 1000 - start));
            isDisplayed = false;
        }
        setTimeout(TIMEOUT);
        if (checkCondition) {
            ReporterNGExt.logAssertTrue(ReporterNGExt.BUSINESS_LEVEL, isDisplayed, format("waitForDisplayed - '%s' should be displayed", name), TestBaseWebDriver.takePassedScreenshot);
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
            isVanished = wait.until(ExpectedConditions.invisibilityOfElementLocated(bylocator));
        } catch (TimeoutException e) {
            logTechnical(format("waitForElementToVanish: [ %s ] during: [ %d ] sec ", locator, System.currentTimeMillis() / 1000 - start));
            isVanished = false;
        }
        setTimeout(TIMEOUT);
        if (checkCondition){
            ReporterNGExt.logAssertTrue(ReporterNGExt.BUSINESS_LEVEL, isVanished, format("waitForElementToVanish - '%s' should be vanished", name), TestBaseWebDriver.takePassedScreenshot);
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
            wait.until(ExpectedConditions.textToBePresentInElementLocated(bylocator, text));
            getText();
            isPresent = wait.until(
                    new ExpectedCondition<Boolean>() {
                        @Override
                        public Boolean apply(WebDriver driver) {
                            return getDriver().findElement(bylocator).getText().equals(text);
                        }
                    }
            );
        } catch (TimeoutException e) {
            logTechnical(format("waitForText: [ %s ] during: [ %d ] sec ", locator, System.currentTimeMillis() / 1000 - start));
            isPresent = false;
        }
        if (checkCondition){
            ReporterNGExt.logAssertTrue(ReporterNGExt.BUSINESS_LEVEL, isPresent, format("waitForText - '%s' should has a text '%s'", name, text), TestBaseWebDriver.takePassedScreenshot);
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
            isPresent = wait.until(ExpectedConditions.textToBePresentInElementLocated(bylocator, text));
        } catch (TimeoutException e) {
            logTechnical(format("waitForTextContains: [ %s ] during: [ %d ] sec ", locator, System.currentTimeMillis() / 1000 - start));
            isPresent = false;
        }
        if (checkCondition){
            ReporterNGExt.logAssertTrue(ReporterNGExt.BUSINESS_LEVEL, isPresent, format("waitForTextContains - '%s' should has a text contains '%s'", name, text), TestBaseWebDriver.takePassedScreenshot);
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
            isChanged = wait.until(ExpectedConditions.not(ExpectedConditions.textToBePresentInElementLocated(bylocator, text)));
        } catch (TimeoutException e) {
            logTechnical(format("waitForTextChanged: [ %s ] during: [ %d ] sec ", locator, System.currentTimeMillis() / 1000 - start));
            isChanged = false;
        }
        if (checkCondition){
            ReporterNGExt.logAssertTrue(ReporterNGExt.BUSINESS_LEVEL, isChanged, format("waitForTextChanged - '%s' text '%s' should be changed", name, text), TestBaseWebDriver.takePassedScreenshot);
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
            isPresent = wait.until(ExpectedConditions.textToBePresentInElementValue(bylocator, value));
        } catch (TimeoutException e) {
            logTechnical(format("waitForValueAttribute: [ %s ] during: [ %d ] sec ", locator, System.currentTimeMillis() / 1000 - start));
            isPresent = false;
        }
        if (checkCondition){
            ReporterNGExt.logAssertTrue(ReporterNGExt.BUSINESS_LEVEL, isPresent, format("waitForValueAttribute - '%s' should has a value attribute '%s'", name, value), TestBaseWebDriver.takePassedScreenshot);
        }
        return parent;
    }

    /**
     * Wait until element is changed the attribute.
     *
     * @param attribute  for watching
     * @param value      of attribute before change
     * @param timeoutSec seconds to wait until element is changed attribute
     * @return Parent instance
     */
    public ParentPanel waitForAttributeChanged(final String attribute, final String value, final int timeoutSec) {
        return waitForAttributeChanged(attribute, value, timeoutSec, CHECKCONDITION);
    }

    /**
     * Wait until element is changed the attribute.
     *
     * @param attribute  for watching
     * @param value      of attribute before change
     * @param timeoutSec seconds to wait until element is changed attribute
     * @param checkCondition log assert for expected conditions.
     * @return Parent instance
     */
    public ParentPanel waitForAttributeChanged(final String attribute, final String value, final int timeoutSec, final boolean checkCondition) {
        boolean isChanged;
        logAction(this, getParentClassName(), format("waitForAttributeChanged[%s]: %s", value, locator));
        long start = System.currentTimeMillis() / 1000;
        WebDriverWait wait = (WebDriverWait) new WebDriverWait(getDriver(), timeoutSec)
                .ignoring(StaleElementReferenceException.class);
        try {
            getAttribute(attribute);
            isChanged = wait.until(
                    new ExpectedCondition<Boolean>() {
                        @Override
                        public Boolean apply(WebDriver driver) {
                            return !getDriver().findElement(bylocator).getAttribute(attribute).equals(value);
                        }
                    }
            );
        } catch (TimeoutException e) {
            logTechnical(format("waitForAttributeChanged: [ %s ] during: [ %d ] sec ", locator, System.currentTimeMillis() / 1000 - start));
            isChanged = false;
        }
        if (checkCondition){
            ReporterNGExt.logAssertTrue(ReporterNGExt.BUSINESS_LEVEL, isChanged, format("waitForAttributeChanged - '%s' attribute '%s' value '%s' should be changed", name, attribute, value), TestBaseWebDriver.takePassedScreenshot);
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
        Elements elements = new Elements<>(name, locator, parent);
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
            wait.until(ExpectedConditions.elementToBeClickable(bylocator));
            isClicked = wait.until(
                    new ExpectedCondition<Boolean>() {
                        @Override
                        public Boolean apply(WebDriver driver) {
                            try {
                                getWebElement(timeoutSec).click();
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
        ReporterNGExt.logAssertTrue(ReporterNGExt.BUSINESS_LEVEL, isClicked, format("waitForClickableAndClick: '%s' was clickable and click at it", name), TestBaseWebDriver.takePassedScreenshot);
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
            ReporterNGExt.logAssertFalse(ReporterNGExt.BUSINESS_LEVEL, isTrue, format("waitForExpectedCondition - '%s'", condition), TestBaseWebDriver.takePassedScreenshot);
        }
        return parent;
    }

}
