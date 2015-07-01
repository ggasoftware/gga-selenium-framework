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
package com.ggasoftware.uitest.control.simple;

import com.ggasoftware.uitest.control.BaseElement;
import com.ggasoftware.uitest.control.interfaces.IElement;
import com.ggasoftware.uitest.utils.common.Timer;
import com.ggasoftware.uitest.utils.settings.HighlightSettings;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.*;

import java.util.List;

import static com.ggasoftware.uitest.utils.settings.FrameworkSettings.*;
import static com.ggasoftware.uitest.utils.common.LinqUtils.where;
import static com.ggasoftware.uitest.utils.ReporterNG.logTechnical;
import static com.ggasoftware.uitest.utils.WebDriverWrapper.*;
import static java.lang.String.format;
import static org.openqa.selenium.Keys.CONTROL;

/**
 * Base Element control implementation
 *
 * @author Alexeenko Yan
 * @author Belin Yury
 * @author Belousov Andrey
 * @author Shubin Konstantin
 * @author Zharov Alexandr
 */
public class Element<ParentPanel> extends BaseElement<ParentPanel> implements IElement {

    public Element() { }

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

    /**
     * Initializes element with given locator. Locates own properties of the element by class name, takes given locator and tries
     * to initialize.
     *
     * @param name    - Element name
     * @param byLocator - Selenium By
     */
    public Element(String name, By byLocator) {
        super(name, byLocator);
    }

    public void highlight() {
        highlight(highlightSettings);
    }

    public void highlight(HighlightSettings highlightSettings) {
        if (highlightSettings == null)
            highlightSettings = new HighlightSettings();
        String orig = getWebElement().getAttribute("style");
        setAttribute("style", format("border: 3px solid %s; background-color: %s;", highlightSettings.FrameColor,
                highlightSettings.BgColor));
        try { Thread.sleep(highlightSettings.TimeoutInSec * 1000); } catch (Exception ignore) {}
        setAttribute("style", orig);
    }

    public void setAttribute(String attributeName, String value) {
        jsExecutor().executeScript("arguments[0].setAttribute(arguments[1], arguments[2])",
                getWebElement(), attributeName, value);
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
     * Find webelement from web page. We use locator for this. Where locator -
     * start it with locator type "id=", "css=", "xpath=" and etc. Locator
     * without type is assigned to xpath
     *
     * @return WebElement
     */
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
        if (logFindElementLocator)
            logTechnical(format("Get Web Element '%s'", getLocator()));
        SearchContext currentContext = getDriver();
        if (context != null)
            for (By by : context) {
                List<WebElement> elements = currentContext.findElements(by);
                if (elements.size() != 1)
                    asserter.exception(format("Instead of 1 element found '%s' elements", elements.size()));
                currentContext = elements.get(0);
            }
        final SearchContext finalCurrentContext = currentContext;
        List<WebElement> result = new Timer(timeouInSec * 1000).getByCondition(
                () -> finalCurrentContext.findElements(getByLocator()),
                els -> where(els, el -> el != null && el.isDisplayed()).size() > 0);
        setTimeout(TIMEOUT);
        if (result == null) {
            asserter.exception(format(failedToFindElementMessage, getByLocator(), TIMEOUT));
            return null;
        }
        if (result.size() > 1)
            asserter.exception(format(findToMuchElementsMessage, result.size(), getByLocator(), TIMEOUT));
        return result.get(0);
    }

    //  Common functions

    /**
     * Click on the Element(WebElement)
     *
     * @return Parent instance
     */
    public ParentPanel click() {
        return doJAction("click", () -> getWebElement().click());
    }

    /**
     * A convenience method that performs click at the location of the source element
     *
     * @param xOffset - horizontal move offset.
     * @param yOffset - vertical move offset.
     * @return Parent instance
     */
    public ParentPanel clickBy(int xOffset, int yOffset) {
        return doJAction(
            format("click element:  horizontal move offset- %dpx; vertical move offset- %dpx", xOffset, yOffset),
            () -> new Actions(getDriver())
                .moveToElement(getWebElement(), xOffset, yOffset)
                .click().build().perform() );
    }
    
    /**
     * Click on the Element(WebElement) by JS
     *
     * @return Parent instance
     */
    public ParentPanel clickJS() {
        return doJAction("clickJS",
                () -> jsExecutor().executeScript("arguments[0].click();", getWebElement()));
    }

    /**
     * Serialize Form by JS
     *
     * @return Serialize result
     */
    public String serializeForm() {
        return doJActionResult("serializeForm",
                () -> (String) jsExecutor().executeScript("return $(arguments[0]).serialize();", getWebElement()));
    }

    /**
     * Mouse Over on the Element(WebElement) by JS
     *
     * @return Parent instance
     */
    public ParentPanel mouseOverJS() {
        return doJAction("mouseOverJS", () -> {
            String script = "var evt = document.createEvent('MouseEvents');" +
                    "evt.initMouseEvent('mouseover',true, true, window, 500, 100, 0, 0, 0, false, false, false, false, 0, null);" +
                    "arguments[0].dispatchEvent(evt);";
            jsExecutor().executeScript(script, getWebElement());
        });
    }

    /**
     * Click on the Element(WebElement) with pressed CTRL key
     *
     * @return Parent instance
     */
    public ParentPanel ctrlClick() {
        return doJAction("ctrlClick",
            () -> new Actions(getDriver()).keyDown(CONTROL)
                .moveToElement(getWebElement())
                .click().keyUp(CONTROL).perform());
    }

    /**
     * Focus window and Click on the Element(WebElement)
     *
     * @return Parent instance
     */
    public ParentPanel focusWindowAndClick() {
        return doJAction("focusWindowAndClick", () -> {
            getDriver().switchTo().window("");
            getWebElement().click();
        });
    }

    /**
     * Performs a double-click at the current mouse location.
     *
     * @return Parent instance
     */
    public ParentPanel doubleClick() {
        return doJAction("doubleClick", () -> {
                getWebElement().getSize(); //for scroll to object
                Actions builder = new Actions(getDriver());
                builder.doubleClick();
            });
    }


    /**
     * Performs a right-click at the current mouse location.
     *
     * @return Parent instance
     */
    public ParentPanel rightClick() {
        return doJAction("rightClick", () -> {
            getWebElement().getSize(); //for scroll to object
            Actions builder = new Actions(getDriver());
            builder.contextClick(getWebElement()).perform();
        });
    }

    /**
     * Clicks in the middle of the given element. Equivalent to:
     * Actions.moveToElement(onElement).click()
     *
     * @return Parent instance
     */
    public ParentPanel clickAction() {
        return doJAction("clickAction", () -> {
            getWebElement().getSize(); //for scroll to object
            Actions builder = new Actions(getDriver());
            builder.click(getWebElement()).perform();
        });
    }

    /**
     * Mouse Over on the the given element.
     *
     * @return Parent instance
     */
    public ParentPanel mouseOver() {
        return doJAction("mouseOver", () -> {
            getWebElement().getSize(); //for scroll to object
            Actions builder = new Actions(getDriver());
            builder.moveToElement(getWebElement()).build().perform();
        });
    }

    /**
     * Focus on the Element(WebElement)
     *
     * @return Parent instance
     */
    public ParentPanel focus() {
        return doJAction("focus", () -> {
            Dimension size = getWebElement().getSize(); //for scroll to object
            Actions builder = new Actions(getDriver());
            org.openqa.selenium.interactions.Action focus 
                    = builder.moveToElement(getWebElement(), size.width / 2, size.height / 2).build();
            focus.perform();
        });
    }

    /**
     * Click on the Element(WebElement) until expectedElement is NOT DISPLAYED
     *
     * @param expectedElement - expected Element
     * @return Parent instance
     */
    public ParentPanel clickWhileObjectNotDisplayed(Element expectedElement, int tryCount) {
        logAction(format("clickWhileObjectNotDisplayed: element locator '%s', element name '%s'",
                expectedElement.getLocator(), expectedElement.getName()));
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
     * @return Parent instance
     */
    public ParentPanel clickWhileObjectNotExist(Element expectedElement, int tryCount) {
        logAction(format("clickWhileObjectNotExist: element locator '%s', element name '%s'",
                expectedElement.getLocator(), expectedElement.getName()));
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
     * @return Parent instance
     */
    public ParentPanel clickWhileObjectIsDisplayed(Element expectedElement, int tryCount) {
        logAction(format("clickWhileObjectExist: element locator '%s', element name '%s'",
                expectedElement.getLocator(), expectedElement.getName()));
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
        return doJAction(format("sendKeys - %s", keysToSend), () -> {
            getDriver().switchTo().activeElement();
            getWebElement().sendKeys(keysToSend);
        });
    }

    /**
     * Use this method to simulate typing into an element with secure log, which may set its value.
     *
     * @param keysToSend - CharSequence to send
     * @return Parent instance
     */
    public ParentPanel sendKeysSecure(CharSequence... keysToSend) {
        return doJAction("sendKeysSecure", () -> {
            getDriver().switchTo().activeElement();
            getWebElement().sendKeys(keysToSend);
        });
    }

    /**
     * Use this method to simulate send keys to the element.
     *
     * @param sendKeys - e.g. sendKeys(Keys.ARROW_DOWN)
     * @return Parent instance
     */
    protected ParentPanel sendKeys(Keys sendKeys) {
        return doJAction(format("sendKeys - %s", sendKeys), () -> {
            getDriver().switchTo().activeElement();
            getWebElement().sendKeys(sendKeys);
        });
    }

    /**
     * Is this element exists (on the web page) or not?
     *
     * @return true if we can find Element on the web page, otherwise false
     */
    public boolean isExists() {
        if (logFindElementLocator) {
            logTechnical(format("Find Elements '%s'", getLocator()));
        }
        return !getDriver().findElements(getByLocator()).isEmpty();
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
        return doJActionResult("Get Text",
            () -> getWebElement().getText(),
            text -> format("got ext : %s", text));
    }

    /**
     * Get the value of a the given attribute of the element. Will return the current value, even if
     * this has been modified after the page has been loaded.
     *
     * @param attributeName The name of the attribute.
     * @return The attribute's current value or null if the value is not set.
     */
    public String getAttribute(String attributeName) {
        return doJActionResult(
            format("%s '%s' Get Attribute '%s'", getTypeName(), getName(), attributeName),
            () -> getWebElement().getAttribute(attributeName));
    }

    /**
     * Set the value of a the given attribute of the element by JS.
     *
     * @param attribute The name of the attribute.
     * @return Parent instance
     * @value value The value of the attribute.
     */
    public ParentPanel setAttributeJS(String attribute, String value) {
        jsExecutor().executeScript(format("arguments[0].setAttribute('%s',arguments[1]);", attribute),
            getWebElement(), value);
        return parent;
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
        logAction(format("Select area: from %d,%d;to %d,%d", x1, y1, x2, y2));
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
        return doJAction(format("Drag and drop element: horizontal move offset - %dpx; vertical move offset - %dpx",
                xOffset, yOffset), () ->
                new Actions(getDriver()).dragAndDropBy(getWebElement(), xOffset, yOffset)
                .build().perform());
    }

    /**
     * Get visible WebElement with Element locator.
     *
     * @return WebElement
     */
    public WebElement getVisibleWebElement() {
        Elements elements = new Elements<>(getName(), getLocator(), parent);
        return elements.getVisibleWebElement();
    }

}
