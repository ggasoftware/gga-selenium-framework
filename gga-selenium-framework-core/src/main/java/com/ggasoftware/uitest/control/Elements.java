/****************************************************************************
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
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static com.ggasoftware.uitest.utils.WebDriverWrapper.*;

/**
 * Element Group control implementation
 *
 * @author Alexeenko Yan
 * @author Belin Yury
 * @author Belousov Andrey
 * @author Shubin Konstantin
 * @author Zharov Alexandr
 */
public class Elements<ParentPanel> {

    /**
     * Name of the Group Element for Report
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
     * Contains name of the element used for locating its parameters in
     * properties file
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
     * Parent panel which contains current element
     */
    protected ParentPanel parent;

    //constructors

    /**
     * Common constructor without any parameters. Locates own properties of the
     * element by class name and tries to use it to initialize.
     */
    public Elements() {
    }

    /**
     * Initializes element's with given locator. Locates own properties of the
     * element by class name, takes given locator and tries to initialize.
     *
     * @param name    - Element name
     * @param locator - start it with locator type "id=", "css=", "xpath=" and
     *                etc. Locator without type is assigned to xpath
     * @param panel   - Parent panel instance
     */
    public Elements(String name, String locator, ParentPanel panel) {
        this.name = name;
        this.locator = locator;
        this.bylocator = getByLocator();
        this.parent = panel;
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
     * @return By Locator of the elements
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
                return By.xpath(String.format("//*[contains(text(), '%s')]", locator_body));
            case "name":
                return By.name(locator_body);
            default:
                return By.xpath(locator);
        }
    }

    /**
     * Find webelement from web page. We use locator for this. Where locator -
     * start it with locator type "id=", "css=", "xpath=" and etc. Locator
     * without type is assigned to xpath
     *
     * @return List of WebElements
     */
    public List<WebElement> getWebElements() {
        if (TestBaseWebDriver.logFindElementLocator) {
            ReporterNGExt.logTechnical(String.format("Get Web Elements '%s'", locator));
        }
        return getDriver().findElements(bylocator);
    }

    /**
     * Find webelement from web page. We use locator for this. Where locator -
     * start it with locator type "id=", "css=", "xpath=" and etc. Locator
     * without type is assigned to xpath
     *
     * @param seconds to wait until elements found.
     * @return List of WebElements
     */

    public List<WebElement> getWebElements(int seconds) {
        setTimeout(seconds);
        List<WebElement> webElementList = getWebElements();
        setTimeout(TIMEOUT);
        return webElementList;
    }

    /**
     * Is at least one of elements exists (on the web page) or not?
     *
     * @return true if we can find at least one of elements on the web page, otherwise false
     */
    public boolean isExists() {
        return !getWebElements().isEmpty();
    }

    /**
     * Is at least one of elements exists (on the web page) or not?
     *
     * @param seconds to wait until elements become existed.
     * @return true if we can find at least one of elements on the web page, otherwise false
     */
    public boolean isExists(int seconds) {
        return !getWebElements(seconds).isEmpty();
    }

    /**
     * Get Element from Elements by index using last xpath tag for numerate
     *
     * @param elementIndex index of element
     * @return Element
     */
    public Element getElement(int elementIndex) {
        return new Element<>(String.format("Element #%s", elementIndex), String.format("%s[%d]", getXPath().replace("//", "/descendant::"), elementIndex + 1), parent);
    }

    /**
     * Get Element from Elements by index using xpath tag for numerate
     *
     * @param elementIndex index of element
     * @param tag - xpath tag for numerate
     * @return Element
     */
    public Element getElement(int elementIndex, String tag) {
        String xpath = getXPath();
        StringBuilder b = new StringBuilder(getXPath());
        b.replace(xpath.lastIndexOf(tag), xpath.lastIndexOf(tag)+1+String.valueOf(elementIndex+1).length(), String.format("%s[%d]", tag, elementIndex+1));
        return new Element<>(String.format("Element #%s", elementIndex), b.toString(), parent);
    }

    /**
     * Get First Visible Element from Elements
     *
     * @return Element
     */
    public Element getVisibleElement() {
        ReporterNGExt.logAction(this, getParentClassName(), "get first visible element");
        int elementIndex = 0;
        for (WebElement webEl : getWebElements()) {
            if (webEl.isDisplayed()) {
                return new Element<>(String.format("Element #%s", elementIndex), String.format("%s[%d]", getXPath().replace("//", "/descendant::"), elementIndex + 1), parent);
            }
            elementIndex++;
        }
        throw new NoSuchElementException("No visible elements available.");
    }

    //  Common functions

    /**
     * Gets count of WebElements
     *
     * @return count of WebElements
     */
    public int getWebElementsCount() {
        return (Integer) ReporterNGExt.logGetter(this, getParentClassName(), "count", getWebElements().size());
    }

    /**
     * Gets count of WebElements
     *
     * @param seconds to wait until elements found.
     * @return count of WebElements
     */
    public int getWebElementsCount(int seconds) {
        return (Integer) ReporterNGExt.logGetter(this, getParentClassName(), "count", getWebElements(seconds).size());
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
     * Get Parent Class Name
     *
     * @return Parent Canonical Class Name
     */
    protected String getParentClassName() {
        if (parent == null) {
            return "";
        }
        if (TestBaseWebDriver.simpleClassName) {
            return parent.getClass().getSimpleName();
        }
        return parent.getClass().getCanonicalName();
    }

    /**
     * Click on the WebElement by index
     *
     * @param elementIndex - index of the element in the List of WebElements
     * @return Parent instance
     */
    public ParentPanel clickBy(int elementIndex) {
        ReporterNGExt.logAction(this, getParentClassName(), String.format("click by element with index %d", elementIndex));
        getWebElements().get(elementIndex).click();
        return parent;
    }

    /**
     * Click on the WebElement by text
     *
     * @param elementText - text of the element in the List of WebElements
     * @return Parent instance
     */
    public ParentPanel clickByText(String elementText) {
        ReporterNGExt.logAction(this, getParentClassName(), String.format("click by element with text '%s'", elementText));
        getWebElements().get(getIndexByText(elementText)).click();
        return parent;
    }

    /**
     * Click on the WebElement by index until expectedElement is NOT DISPLAYED
     *
     * @param expectedElement - expected Element
     * @param elementIndex    - index of WebElement
     * @param tryCount        - number ot click attempts
     * @return Parent instance
     */
    public ParentPanel clickByWhileObjectNotDisplayed(int elementIndex, Element expectedElement, int tryCount) {
        ReporterNGExt.logAction(this, getParentClassName(), String.format("Click by element with index '%d' while expectedElement NOT displayed: element locator '%s', element name '%s'", elementIndex, expectedElement.locator, expectedElement.name));
        int i = 0;
        do {
            getWebElements().get(elementIndex).click();
            i++;
            if (i >= tryCount) {
                break;
            }
        }
        while (!(expectedElement.isDisplayed()));
        return parent;
    }

    /**
     * Focus on the WebElement by index
     *
     * @param elementIndex    - index of WebElement
     * @return Parent instance
     */
    public ParentPanel focus(int elementIndex) {
        Dimension size = getWebElements().get(elementIndex).getSize(); //for scroll to object
        ReporterNGExt.logAction(this, getParentClassName(), "Focus");
        Actions builder = new Actions(WebDriverWrapper.getDriver());
        org.openqa.selenium.interactions.Action focus = builder.moveToElement(getWebElements().get(elementIndex), size.width / 2, size.height / 2).build();
        focus.perform();
        return parent;
    }

    /**
     * Mouse Over on the the element by index.
     *
     * @param elementIndex    - index of WebElement
     * @return Parent instance
     */
    public ParentPanel mouseOver(int elementIndex) {
        getWebElements().get(elementIndex).getSize(); //for scroll to object
        ReporterNGExt.logAction(this, getParentClassName(), "mouseOver");
        Actions builder = new Actions(WebDriverWrapper.getDriver());
        builder.moveToElement(getWebElements().get(elementIndex)).build().perform();
        return parent;
    }

    /**
     * Click on the WebElement by JS
     *
     * @param elementIndex    - index of WebElement
     * @return Parent instance
     */
    public ParentPanel clickByJS(int elementIndex) {
        ReporterNGExt.logAction(this, getParentClassName(), "clickJS");
        JavascriptExecutor executor = (JavascriptExecutor) WebDriverWrapper.getDriver();
        executor.executeScript("arguments[0].click();", getWebElements().get(elementIndex));
        return parent;
    }

    /**
     * Click on the Element by index until expectedElements is NOT DISPLAYED
     *
     * @param expectedElements - expected Elements
     * @param elementIndex     - index of WebElement
     * @return Parent instance
     */
    public ParentPanel clickByWhileObjectNotDisplayed(int elementIndex, Elements expectedElements, int tryCount) {
        ReporterNGExt.logAction(this, getParentClassName(), String.format("Click by element with index '%d' while expectedElement NOT displayed: element locator '%s', element name '%s'", elementIndex, expectedElements.getName(), expectedElements.getLocator()));
        int i = 0;
        do {
            getWebElements().get(elementIndex).click();
            Sleeper.sleepTight(1000);
            i++;
            if (i >= tryCount) {
                break;
            }
        }
        while (!(expectedElements.isVisibleWebElementAvailable()));
        return parent;
    }


    /**
     * Get WebElement by index
     *
     * @param elementIndex - index of WebElement
     * @return WebElement
     */
    public WebElement getWebElement(int elementIndex) {
        ReporterNGExt.logAction(this, getParentClassName(), String.format("get by element with index '%d'", elementIndex));
        return getWebElements().get(elementIndex);
    }

    /**
     * Get First visible WebElement
     *
     * @return WebElement
     */
    public WebElement getVisibleWebElement() {
        ReporterNGExt.logAction(this, getParentClassName(), "get first visible element");
        for (WebElement webEl : getWebElements()) {
            if (webEl.isDisplayed()) {
                return webEl;
            }
        }
        throw new NoSuchElementException("No visible elements available.");
    }

    /**
     * Is First visible WebElement available
     *
     * @return Whether or not first visible WebElement available(in the Elements)
     */
    public boolean isVisibleWebElementAvailable() {
        ReporterNGExt.logAction(this, getParentClassName(), "is first visible element available");
        for (WebElement webEl : getWebElements()) {
            if (webEl.isDisplayed()) {
                return true;
            }
        }
        return false;
    }


    /**
     * Get WebElement by text attribute
     *
     * @param sText - text() attribute of the WebElement in the List of WebElements
     * @return WebElement
     */
    public WebElement getWebElementByText(String sText) {
        ReporterNGExt.logAction(this, getParentClassName(), String.format("get WebElement of element with text '%s'", sText));
        for (WebElement webEl : getWebElements()) {
            if (sText.equals(webEl.getText())) {
                return webEl;
            }
        }
        throw new NoSuchElementException(String.format("Cannot find element with text '%s'. ", sText));
    }

    /**
     * Get WebElement by text attribute contains
     *
     * @param sText - text() attribute of the WebElement in the List of WebElements
     * @return WebElement
     */
    public WebElement getWebElementByTextContains(String sText) {
        ReporterNGExt.logAction(this, getParentClassName(), String.format("get WebElement with text contains '%s'", sText));
        for (WebElement webEl : getWebElements()) {
            if (webEl.getText().contains(sText)) {
                return webEl;
            }
        }
        throw new NoSuchElementException(String.format("Cannot find element with text contains '%s'. ", sText));
    }

    /**
     * Get WebElement index by text of current attribute
     *
     * @param sText      - value of attribute of the element in the List of WebElements
     * @param sAttribute - type of attribute of the element in the List of WebElements
     * @return index
     */
    public int getIndexByAttribute(String sText, String sAttribute) {
        ReporterNGExt.logAction(this, getParentClassName(), String.format("get index of element with text '%s' by attribute '%s'", sText, sAttribute));
        int i = 0;
        for (WebElement webEl : getWebElements()) {
            if (sText.equals(webEl.getAttribute(sAttribute))) {
                return i;
            }
            i++;
        }
        throw new NoSuchElementException(String.format("Cannot find element with text '%s' by attribute '%s'. ", sText, sAttribute));
    }

    /**
     * Get WebElement index by text attribute
     *
     * @param sText - text() attribute of the element in the List of WebElements
     * @return index
     */
    public int getIndexByText(String sText) {
        ReporterNGExt.logAction(this, getParentClassName(), String.format("get index of element with text '%s'", sText));
        int i = 0;
        for (WebElement webEl : getWebElements()) {
            if (sText.equals(webEl.getText())) {
                return i;
            }
            i++;
        }
        throw new NoSuchElementException(String.format("Cannot find element with text '%s'. ", sText));
    }

    /**
     * Get WebElement index by text attribute contains
     *
     * @param sText - text() attribute of the element in the List of WebElements
     * @return index
     */
    public int getIndexByTextContains(String sText) {
        ReporterNGExt.logAction(this, getParentClassName(), String.format("get index of element with text contains '%s'", sText));
        int i = 0;
        for (WebElement webEl : getWebElements()) {
            if (webEl.getText().contains(sText)) {
                return i;
            }
            i++;
        }
        throw new NoSuchElementException(String.format("Cannot find element with text contains '%s'. ", sText));
    }

    /**
     * Get WebElement index by text attribute starts with
     *
     * @param sText - text() attribute of the element in the List of WebElements
     * @return index of WebElement
     */
    public int getIndexByTextStartsWith(String sText) {
        ReporterNGExt.logAction(this, getParentClassName(), String.format("get index of element with text starts with '%s'", sText));
        int i = 0;
        for (WebElement webEl : getWebElements()) {
            if (webEl.getText().startsWith(sText)) {
                return i;
            }
            i++;
        }
        throw new NoSuchElementException(String.format("Cannot find element with text starts with '%s'. ", sText));

    }

    /**
     * Get text of WebElement by index
     * <p/>
     * * @param elementIndex - index of the element in the List of WebElements
     *
     * @return text of WebElement
     */
    public String getText(int elementIndex) {
        ReporterNGExt.logAction(this, getParentClassName(), String.format("get text of element with index '%d'", elementIndex));
        return getWebElements().get(elementIndex).getText();
    }

    /**
     * Get List of WebElement text
     *
     * @return ArrayList
     */
    public ArrayList<String> getTextList() {
        ArrayList<String> list = new ArrayList<>();
        for (WebElement webEl : getWebElements()) {
            list.add(webEl.getText());
        }
        return list;
    }

    /**
     * Get List of WebElement attributes
     *
     * @param attributeName - name of attribute for getting value
     * @return List of WebElement attributes
     */
    public ArrayList<String> getAttribureList(String attributeName) {
        ArrayList<String> list = new ArrayList<>();
        for (WebElement webEl : getWebElements()) {
            list.add(webEl.getAttribute(attributeName));
        }
        return list;
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
                return String.format("//*[@id=\"%s\"]", sLocator);
            case "link":
                return String.format("//*[@link=\"%s\"]", sLocator);
            case "xpath":
                return String.format("%s", sLocator);
            case "text":
                return String.format("//*[contains(text(), '%s')]", sLocator);
            case "name":
                return String.format("//*[@name=\"%s\"]", sLocator);
            default:
                return "";
        }
    }

    /**
     * Replace each substring of this string "$VALUE" to [value] in [str]
     *
     * @param str   - input string for replacement
     * @param value -The replacement sequence of char values
     * @return The resulting string
     */
    public String insertValue(String str, String value) {
        return str.replace("$VALUE", value);
    }

    /**
     * Get Control Name
     *
     * @return Get Element's Group Name
     */
    public String getName() {
        return name;
    }

    /**
     * Wait until first element is visible.
     *
     * @param timeoutSec seconds to wait until element become visible
     * @param checkCondition log assert for expected conditions.
     * @return Parent instance
     */
    public ParentPanel waitForFirstVisibleElement(final int timeoutSec, final boolean checkCondition) {
        ReporterNGExt.logAction(this, getParentClassName(), String.format("waitForFirstVisibleElement: %s", locator));
        boolean isVisible;
        long start = System.currentTimeMillis() / 1000;
        WebDriverWait wait = (WebDriverWait) new WebDriverWait(getDriver(), timeoutSec)
                .ignoring(StaleElementReferenceException.class);
        setTimeout(1);
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(bylocator));
            isVisible = true;
        } catch (TimeoutException e) {
            ReporterNGExt.logTechnical(String.format("waitForFirstVisibleElement: [ %s ] during: [ %d ] sec ", locator, System.currentTimeMillis() / 1000 - start));
            isVisible = false;
        }
        setTimeout(TIMEOUT);
        if (checkCondition){
            ReporterNGExt.logAssertTrue(ReporterNGExt.BUSINESS_LEVEL, isVisible, String.format("waitForFirstVisibleElement - first element of '%s' should be visible", name), TestBaseWebDriver.takePassedScreenshot);
        }
        return parent;
    }

    /**
     * Wait until first element is visible
     *
     * @return Parent instance
     */
    public ParentPanel waitForFirstVisibleElement() {
        return waitForFirstVisibleElement(TIMEOUT, false);
    }
    /**
     * Wait until first element is visible
     * @param timeoutSec seconds to wait until all elements exist
     * @return Parent instance
     */
    public ParentPanel waitForFirstVisibleElement(final int timeoutSec) {
        return waitForFirstVisibleElement(timeoutSec, CHECKCONDITION);
    }

    /**
     * Wait until all elements exist.
     *
     * @param timeoutSec seconds to wait until all elements exist
     * @param checkCondition log assert for expected conditions.
     * @return Parent instance
     */
    public ParentPanel waitForAllElementsExist(final int timeoutSec, final boolean checkCondition) {
        ReporterNGExt.logAction(this, getParentClassName(), String.format("waitForAllElementsExist: %s", locator));
        boolean exist;
        long start = System.currentTimeMillis() / 1000;
        WebDriverWait wait = (WebDriverWait) new WebDriverWait(getDriver(), timeoutSec)
                .ignoring(StaleElementReferenceException.class);
        setTimeout(1);
        try {
            wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(bylocator));
            exist = true;
        } catch (TimeoutException e) {
            ReporterNGExt.logTechnical(String.format("waitForAllElementsExist: [ %s ] during: [ %d ] sec ", locator, System.currentTimeMillis() / 1000 - start));
            exist = false;
        }
        setTimeout(TIMEOUT);
        if (checkCondition){
            ReporterNGExt.logAssertTrue(ReporterNGExt.BUSINESS_LEVEL, exist, String.format("waitForAllElementsExist - all elements of '%s' should exist", name), TestBaseWebDriver.takePassedScreenshot);
        }
        return parent;
    }

    /**
     * Wait until all elements exist
     *
     * @return Parent instance
     */
    public ParentPanel waitForAllElementsExist() {
        return waitForAllElementsExist(TIMEOUT, false);
    }
    /**
     * Wait until all elements exist
     * @param timeoutSec seconds to wait until all elements become Not Visible
     * @return Parent instance
     */
    public ParentPanel waitForAllElementsExist(final int timeoutSec) {
        return waitForAllElementsExist(timeoutSec, CHECKCONDITION);
    }

    /**
     * Wait until all elements is invisible
     *
     * @param timeoutSec seconds to wait until all elements become Not Visible
     * @param checkCondition log assert for expected conditions.
     * @return Parent instance
     */
    public ParentPanel waitForAllElementsNotVisible(final int timeoutSec, final boolean checkCondition) {
        ReporterNGExt.logAction(this, getParentClassName(), String.format("waitForAllElementsNotVisible: %s", locator));
        boolean isNotVisible;
        long start = System.currentTimeMillis() / 1000;
        WebDriverWait wait = (WebDriverWait) new WebDriverWait(getDriver(), timeoutSec)
                .ignoring(StaleElementReferenceException.class);
        setTimeout(1);
        try {
            wait.until(ExpectedConditions.not(ExpectedConditions.visibilityOfAllElements(getWebElements())));
            isNotVisible = true;
        } catch (TimeoutException e){
            ReporterNGExt.logTechnical(String.format("waitForAllElementsNotVisible: [ %s ] during: [ %d ] sec ", locator, System.currentTimeMillis() / 1000 - start));
            isNotVisible = false;
        } catch (NoSuchElementException elementException) {
            isNotVisible = false;
        }
        setTimeout(TIMEOUT);
        if (checkCondition){
            ReporterNGExt.logAssertTrue(ReporterNGExt.BUSINESS_LEVEL, isNotVisible, String.format("waitForAllElementsNotVisible - all element of '%s' should be not visible", name), TestBaseWebDriver.takePassedScreenshot);
        }
        return parent;
    }

    /**
     * Wait until all elements is invisible
     *
     * @return Parent instance
     */
    public ParentPanel waitForAllElementsNotVisible() {
        return waitForAllElementsNotVisible(TIMEOUT, false);
    }
    /**
     * Wait until all elements is invisible
     * @param timeoutSec seconds to wait until all elements become Visible
     * @return Parent instance
     */
    public ParentPanel waitForAllElementsNotVisible(final int timeoutSec) {
        return waitForAllElementsNotVisible(timeoutSec, CHECKCONDITION);
    }

    /**
     * Wait until all elements is visible
     *
     * @param timeoutSec seconds to wait until all elements become Visible
     * @param checkCondition log assert for expected conditions.
     * @return Parent instance
     */
    public ParentPanel waitForAllElementsVisible(final int timeoutSec, final boolean checkCondition) {
        ReporterNGExt.logAction(this, getParentClassName(), String.format("waitForAllElementsVisible: %s", locator));
        boolean isVisible;
        long start = System.currentTimeMillis() / 1000;
        WebDriverWait wait = (WebDriverWait) new WebDriverWait(getDriver(), timeoutSec)
                .ignoring(StaleElementReferenceException.class);
        setTimeout(1);
        try {
            wait.until(ExpectedConditions.visibilityOfAllElements(getWebElements()));
            isVisible = true;
        } catch (TimeoutException e) {
            ReporterNGExt.logTechnical(String.format("waitForAllElementsVisible: [ %s ] during: [ %d ] sec ", locator, System.currentTimeMillis() / 1000 - start));
            isVisible = false;
        }
        setTimeout(TIMEOUT);
        if (checkCondition){
            ReporterNGExt.logAssertTrue(ReporterNGExt.BUSINESS_LEVEL, isVisible, String.format("waitForAllElementsVisible - all elements of '%s' should be visible", name), TestBaseWebDriver.takePassedScreenshot);
        }
        return parent;
    }

    /**
     * Wait until all elements is visible
     *
     * @return Parent instance
     */
    public ParentPanel waitForAllElementsVisible() {
        return waitForAllElementsVisible(TIMEOUT, false);
    }

    /**
     * Wait until all elements is visible
     * @param timeoutSec seconds to wait until elements is changed list of text
     * @return Parent instance
     */
    public ParentPanel waitForAllElementsVisible(final int timeoutSec) {
        return waitForAllElementsVisible(timeoutSec, CHECKCONDITION);
    }

    /**
     * Wait until elements is changed list of text
     *
     * @param list expected list of text
     * @param timeoutSec seconds to wait until elements is changed list of text
     * @param checkCondition log assert for expected conditions.
     * @return Parent instance
     */
    public ParentPanel waitForElementsTextChanged(final List<String> list, final int timeoutSec, final boolean checkCondition) {
        boolean isChanged;
        ReporterNGExt.logAction(this, getParentClassName(), String.format("waitForElementsTextChanged: %s", locator));
        long start = System.currentTimeMillis() / 1000;
        WebDriverWait wait = (WebDriverWait) new WebDriverWait(getDriver(), timeoutSec)
                .ignoring(StaleElementReferenceException.class);
        try {
            isChanged = wait.until(
                    new ExpectedCondition<Boolean>() {
                        @Override
                        public Boolean apply(WebDriver driver) {
                            return !getTextList().containsAll(list);
                        }
                    }
            );
        } catch (TimeoutException e) {
            ReporterNGExt.logTechnical(String.format("waitForElementsTextChanged: [ %s ] during: [ %d ] sec ", locator, System.currentTimeMillis() / 1000 - start));
            isChanged = false;
        }
        if (checkCondition){
            ReporterNGExt.logAssertTrue(ReporterNGExt.BUSINESS_LEVEL, isChanged, String.format("waitForElementsTextChanged - '%s' should be changed", name), TestBaseWebDriver.takePassedScreenshot);
        }
        return parent;
    }

    /**
     * Wait until elements is changed list of text
     *
     * @param list expected list of text
     * @return Parent instance
     */
    public ParentPanel waitForElementsTextChanged(final List<String> list) {
        return waitForElementsTextChanged(list, TIMEOUT, false);
    }

    /**
     * Wait until elements is changed list of text
     *
     * @param list expected list of text
     * @param timeoutSec seconds to wait until elements count is changed
     * @return Parent instance
     */
    public ParentPanel waitForElementsTextChanged(final List<String> list, final int timeoutSec) {
        return waitForElementsTextChanged(list, timeoutSec, CHECKCONDITION);
    }

    /**
     * Wait until number of elements is changed
     *
     * @param count      - source number of element
     * @param timeoutSec seconds to wait until elements count is changed
     * @param checkCondition log assert for expected conditions.
     * @return Parent instance
     */
    public ParentPanel waitForNumberOfElementsChanged(final int count, final int timeoutSec, final boolean checkCondition) {
        boolean isChanged;
        ReporterNGExt.logAction(this, getParentClassName(), String.format("waitNumberOfElementsChanged[%d]: %s", count, locator));
        long start = System.currentTimeMillis() / 1000;
        WebDriverWait wait = (WebDriverWait) new WebDriverWait(getDriver(), timeoutSec)
                .ignoring(StaleElementReferenceException.class);
        try {
            getWebElementsCount();
            isChanged = wait.until(
                    new ExpectedCondition<Boolean>() {
                        @Override
                        public Boolean apply(WebDriver driver) {
                            return getDriver().findElements(bylocator).size() != count;
                        }
                    }
            );
        } catch (TimeoutException e) {
            ReporterNGExt.logTechnical(String.format("waitNumberOfElementsChanged: [ %s ] during: [ %d ] sec ", locator, System.currentTimeMillis() / 1000 - start));
            isChanged = false;
        }
        if (checkCondition){
            ReporterNGExt.logAssertTrue(ReporterNGExt.BUSINESS_LEVEL, isChanged, String.format("waitNumberOfElementsChanged - '%s' elements count '%d' should be changed", name, count), TestBaseWebDriver.takePassedScreenshot);
        }
        return parent;
    }

    /**
     * Wait until number of elements is changed
     *
     * @param count      - source number of element
     * @return Parent instance
     */
    public ParentPanel waitForNumberOfElementsChanged(final int count) {
        return waitForNumberOfElementsChanged(count, TIMEOUT, false);
    }

    /**
     * Wait until number of elements is changed
     *
     * @param count      - source number of element
     * @param timeoutSec seconds to wait until elements count is changed
     * @return Parent instance
     */
    public ParentPanel waitForNumberOfElementsChanged(final int count, final int timeoutSec) {
        return waitForNumberOfElementsChanged(count, timeoutSec, CHECKCONDITION);
    }

}
