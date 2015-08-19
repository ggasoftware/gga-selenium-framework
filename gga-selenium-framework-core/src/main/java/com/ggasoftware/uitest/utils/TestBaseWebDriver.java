/**
 * *************************************************************************
 * Copyright (C) 2014 GGA Software Services LLC
 * <p/>
 * This file may be distributed and/or modified under the terms of the
 * GNU General Public License version 3 as published by the Free Software
 * Foundation.
 * <p/>
 * This file is provided AS IS with NO WARRANTY OF ANY KIND, INCLUDING THE
 * WARRANTY OF DESIGN, MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE.
 * <p/>
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see <http://www.gnu.org/licenses>.
 * *************************************************************************
 */
package com.ggasoftware.uitest.utils;

import com.ggasoftware.uitest.utils.linqInterfaces.JFuncTT;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * Utility for use simple asserts.
 *
 * @author Belousov Andrey
 * @author Zharov Alexandr
 * @author Zhukov Anatoliy
 */
public class TestBaseWebDriver extends TestBase {

    public static WebDriver driver;
    public static String browserType = "chrome";
    public static boolean grid = false;
    public static String gridHub = "http://localhost:4444/wd/hub";
    public static final String screenshotDirectory = "target/surefire-reports/html/screenshots";
    public static boolean simpleClassName = true;
    public static boolean takePassedScreenshot = false;
    public static boolean logFindElementLocator = true;
    public static boolean allure = false;
    public static boolean reportportal = false;
    public static String testName = "Undefined Test Name";
    public static String applicationVersion;
    public static JFuncTT<WebElement, Boolean> defaultSearchCriteria = WebElement::isDisplayed;
    public static boolean selectFirstElementIfMultipleFound = false;

    /**
     * Set Browser for WebDriver.
     *
     * @param browser - browser to initialization.
     */
    public static void setBrowserType(String browser) {
        browserType = browser;
    }

    /**
     * Use Selenium Grid.
     *
     * @param useGrid - true for using selenium grid.
     */
    public static void useGrid(boolean useGrid) {
        grid = useGrid;
    }

    /**
     * Set Selenium Grid Hub.
     *
     * @param hub - selenium grid hub url.
     */
    public static void setGridHub(String hub) {
        gridHub = hub;
    }

    /**
     * Take screenshots at passed assertion.
     *
     * @param takeScreenshot - true for taking screenshots at passed assertion.
     */
    public static void takePassedScreenshot(boolean takeScreenshot) {
        takePassedScreenshot = takeScreenshot;
    }

    /**
     * Use simple or canonical class name.
     *
     * @param simple - true for using simple class name .
     */
    public static void useSimpleClassName(boolean simple) {
        simpleClassName = simple;
    }

    /**
     * Use allure framework.
     *
     * @param useAllure - true for using allure framework.
     */
    public static void useAllure(boolean useAllure) {
        allure = useAllure;
    }

    /**
     * Use Reportportal framework.
     *
     * @param useReportPortal - true for using ReportPortal framework.
     */
    public static void useReportPortal(boolean useReportPortal) {
        reportportal = useReportPortal;
    }


    /**
     * Logging locator of element at findElement method.
     *
     * @param loggingLocator - true for logging locator.
     */
    public static void logFindElementLocator(boolean loggingLocator) {
        logFindElementLocator = loggingLocator;
    }

    /**
     * WebDriver initialization.
     */
    public void initWebDriver() {
        if (grid) {
            initWebDriverGrid();
        } else {
            switch (browserType) {
                case "safari":
                    WebDriverWrapper.initSafariDriver();
                    break;
                case "chrome":
                    WebDriverWrapper.initChromeDriver();
                    break;
                case "firefox":
                    WebDriverWrapper.initFirefoxDriver();
                    break;
                case "ie":
                case "iexplorer":
                case "internetexplorer":
                    WebDriverWrapper.initInternetExplorerDriver();
                    break;
            }
            driver = WebDriverWrapper.getDriver();
        }
        initScreenshotDirectory();
    }

    /**
     * WebDriver Grid initialization.
     */
    public void initWebDriverGrid() {

        Capabilities capabilities = null;
        switch (browserType) {
            case "safari":
                capabilities = DesiredCapabilities.internetExplorer();
                break;
            case "chrome":
                capabilities = DesiredCapabilities.chrome();
                break;
            case "firefox":
                capabilities = DesiredCapabilities.firefox();
                break;
            case "ie":
            case "iexplorer":
            case "internetexplorer":
                capabilities = DesiredCapabilities.internetExplorer();
                break;
        }
        try {
            WebDriverWrapper.initRemoteWebDriver(gridHub, capabilities);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        driver = WebDriverWrapper.getDriver();

    }

    /**
     * Directory for Screenshot initialization.
     */
    public void initScreenshotDirectory() {
        MakeDir.makeDir(screenshotDirectory);
        ScreenShotMaker.setPath(screenshotDirectory);
    }

    /**
     * assertEquals by ReporterNGExt
     *
     * @param value - actual object
     * @param expectedValue - expected object
     * @param message - log message text
     */
    @Override
    public void assertEquals(Object value, Object expectedValue, String message) {
        assertEquals(value, expectedValue, message, takePassedScreenshot);
    }

    /**
     * assertEquals by ReporterNGExt
     *
     * @param value - actual object
     * @param expectedValue - expected object
     * @param message - log message text
     * @param takeScreenshot - Set True to take screenshot if assert passed
     */
    public void assertEquals(Object value, Object expectedValue, String message, boolean takeScreenshot) {
        ReporterNGExt.logAssertEquals(ReporterNGExt.BUSINESS_LEVEL, value, expectedValue, message, takeScreenshot);
        if (allure) {
            Assert.assertEquals(value, expectedValue, message);
        }
    }

    /**
     * assertNotEquals by ReporterNGExt
     *
     * @param value - actual object
     * @param notExpectedValue - not expected object
     * @param message - log message text
     */
    @Override
    public void assertNotEquals(Object value, Object notExpectedValue, String message) {
        assertNotEquals(value, notExpectedValue, message, takePassedScreenshot);
        if (allure) {
            Assert.assertNotEquals(value, notExpectedValue, message);
        }
    }

    /**
     * assertNotEquals by ReporterNGExt
     *
     * @param value - actual object
     * @param notExpectedValue - not expected object
     * @param message - log message text
     * @param takeScreenshot - Set True to take screenshot if assert passed
     */
    public void assertNotEquals(Object value, Object notExpectedValue, String message, boolean takeScreenshot) {
        ReporterNGExt.logAssertNotEquals(ReporterNGExt.BUSINESS_LEVEL, value, notExpectedValue, message, takeScreenshot);
        if (allure) {
            Assert.assertNotEquals(value, notExpectedValue, message);
        }
    }

    /**
     * assertArrayListEquals by ReporterNGExt
     *
     * @param value - actual Array List of String
     * @param expectedValue - expected Array List of String
     * @param message - log message text
     */
    public void assertArrayListEquals(ArrayList<String> value, ArrayList<String> expectedValue, String message) {
        assertArrayListEquals(value, expectedValue, message, takePassedScreenshot);
    }

    /**
     * assertArrayListEquals by ReporterNGExt
     *
     * @param value - actual Array List of String
     * @param expectedValue - expected Array List of String
     * @param message - log message text
     * @param takeScreenshot - Set True to take screenshot if assert passed
     */
    public void assertArrayListEquals(ArrayList<String> value, ArrayList<String> expectedValue, String message, boolean takeScreenshot) {
        ReporterNGExt.logAssertArrayListEquals(ReporterNGExt.BUSINESS_LEVEL, value, expectedValue, message, takeScreenshot);
        if (allure) {
            Assert.assertEquals(value, expectedValue, message);
        }
    }

    /**
     * assertEquals by ReporterNGExt
     *
     * @param value - actual text array
     * @param expectedValue - expected text array
     * @param message - log message text
     */
    @Override
    public void assertEquals(String[] value, String[] expectedValue, String message) {
        assertEquals(value, expectedValue, message, takePassedScreenshot);
    }

    /**
     * assertEquals by ReporterNGExt
     *
     * @param value - actual text array
     * @param expectedValue - expected text array
     * @param message - log message text
     * @param takeScreenshot - Set True to take screenshot if assert passed
     */
    public void assertEquals(String[] value, String[] expectedValue, String message, boolean takeScreenshot) {
        ReporterNGExt.logAssertEquals(ReporterNGExt.BUSINESS_LEVEL, value, expectedValue, message, takeScreenshot);
        if (allure) {
            Assert.assertEquals(value, expectedValue, message);
        }
    }

    /**
     * assertTrue by ReporterNGExt
     *
     * @param what - expression
     * @param message - log message text
     */
    @Override
    public void assertTrue(boolean what, String message) {
        assertTrue(what, message, takePassedScreenshot);
        if (allure) {
            Assert.assertTrue(what, message);
        }
    }

    /**
     * assertTrue by ReporterNGExt
     *
     * @param what - expression
     * @param message - log message text
     * @param takeScreenshot - Set True to take screenshot if assert passed
     */
    public void assertTrue(boolean what, String message, boolean takeScreenshot) {
        ReporterNGExt.logAssertTrue(ReporterNGExt.BUSINESS_LEVEL, what, message, takeScreenshot);
        if (allure) {
            Assert.assertTrue(what, message);
        }
    }

    /**
     * assertFalse by ReporterNGExt
     *
     * @param what - expression
     * @param message - log message text
     */
    @Override
    public void assertFalse(boolean what, String message) {
        assertFalse(what, message, takePassedScreenshot);
    }

    /**
     * assertFalse by ReporterNGExt
     *
     * @param what - expression
     * @param message - log message text
     * @param takeScreenshot - Set True to take screenshot if assert passed
     */
    public void assertFalse(boolean what, String message, boolean takeScreenshot) {
        ReporterNGExt.logAssertFalse(ReporterNGExt.BUSINESS_LEVEL, what, message, takeScreenshot);
        if (allure) {
            Assert.assertFalse(what, message);
        }
    }

    /**
     * logAssertContains by ReporterNGExt
     *
     * @param toSearchIn - text search in
     * @param whatToSearch - text to search
     * @param message - log message text
     */
    @Override
    public void assertContains(String toSearchIn, String whatToSearch, String message) {
        assertContains(toSearchIn, whatToSearch, message, takePassedScreenshot);
    }

    /**
     * logAssertContains by ReporterNGExt
     *
     * @param toSearchIn - text search in
     * @param whatToSearch - text to search
     * @param message - log message text
     * @param takeScreenshot - Set True to take screenshot if assert passed
     */
    public void assertContains(String toSearchIn, String whatToSearch, String message, boolean takeScreenshot) {
        ReporterNGExt.logAssertContains(ReporterNGExt.BUSINESS_LEVEL, toSearchIn, whatToSearch, message, takeScreenshot);
        if (allure) {
            if (!toSearchIn.contains(whatToSearch)) {
                Assert.fail(String.format("assertContains: %s: <br> Expected: %s.<br> Actual: %s", message, toSearchIn, whatToSearch));
            }
        }
    }

    /**
     * assertNotContains by ReporterNGExt
     *
     * @param toSearchIn - text search in
     * @param whatToSearch - text to search
     * @param message - log message text
     */
    public void assertNotContains(String toSearchIn, String whatToSearch, String message) {
        assertNotContains(toSearchIn, whatToSearch, message, takePassedScreenshot);
    }

    /**
     * assertNotContains by ReporterNGExt
     *
     * @param toSearchIn - text search in
     * @param whatToSearch - text to search
     * @param message - log message text
     * @param takeScreenshot - Set True to take screenshot if assert passed
     */
    public void assertNotContains(String toSearchIn, String whatToSearch, String message, boolean takeScreenshot) {
        ReporterNGExt.logAssertNotContains(ReporterNGExt.BUSINESS_LEVEL, toSearchIn, whatToSearch, message, takeScreenshot);
        if (allure) {
            if (toSearchIn.contains(whatToSearch)) {
                Assert.fail(String.format("assertNotContains: %s: <br> Expected: %s.<br> Actual: %s", message, toSearchIn, whatToSearch));
            }
        }
    }

    /**
     * assertNull by ReporterNGExt
     *
     * @param what - analyzed object
     * @param message - log message text
     */
    @Override
    public void assertNull(Object what, String message) {
        assertNull(what, message, takePassedScreenshot);
    }

    /**
     * assertNull by ReporterNGExt
     *
     * @param what - analyzed object
     * @param message - log message text
     * @param takeScreenshot - Set True to take screenshot if assert passed
     */
    public void assertNull(Object what, String message, boolean takeScreenshot) {
        ReporterNGExt.logAssertNull(ReporterNGExt.BUSINESS_LEVEL, what, message, takeScreenshot);
        if (allure) {
            Assert.assertNull(what, message);
        }
    }

    /**
     * assertNotNull by ReporterNGExt
     *
     * @param what - analyzed object
     * @param message - log message text
     */
    @Override
    public void assertNotNull(Object what, String message) {
        assertNotNull(what, message, takePassedScreenshot);
    }

    /**
     * assertNotNull by ReporterNGExt
     *
     * @param what - analyzed object
     * @param message - log message text
     * @param takeScreenshot - Set True to take screenshot if assert passed
     */
    public void assertNotNull(Object what, String message, boolean takeScreenshot) {
        ReporterNGExt.logAssertNotNull(ReporterNGExt.BUSINESS_LEVEL, what, message, takeScreenshot);
        if (allure) {
            Assert.assertNotNull(what, message);
        }
    }

    /**
     * assertEmpty by ReporterNGExt
     *
     * @param what - analyzed text
     * @param message - log message text
     */
    @Override
    public void assertEmpty(String what, String message) {
        assertEmpty(what, message, takePassedScreenshot);
    }

    /**
     * assertEmpty by ReporterNGExt
     *
     * @param what - analyzed text
     * @param message - log message text
     * @param takeScreenshot - Set True to take screenshot if assert passed
     */
    public void assertEmpty(String what, String message, boolean takeScreenshot) {
        ReporterNGExt.logAssertEmpty(ReporterNGExt.BUSINESS_LEVEL, what, message, takeScreenshot);
        if (allure) {
            Assert.assertEquals(what, "", message);
        }
    }

    /**
     * assertNotIntersect by ReporterNGExt
     *
     * @param firstArray - first text array 
     * @param secondArray - second text array
     * @param message - log message text
     */
    @Override
    public void assertNotIntersect(String[] firstArray, String[] secondArray, String message) {
        assertNotIntersect(firstArray, secondArray, message, takePassedScreenshot);
    }

    /**
     * assertNotIntersect by ReporterNGExt
     *
     * @param firstArray - first text array 
     * @param secondArray - second text array
     * @param message - log message text
     * @param takeScreenshot - Set True to take screenshot if assert passed
     */
    public void assertNotIntersect(String[] firstArray, String[] secondArray, String message, boolean takeScreenshot) {
        ReporterNGExt.logAssertNotIntersect(ReporterNGExt.BUSINESS_LEVEL, firstArray, secondArray, message, takeScreenshot);
        if (allure) {
            ArrayList<String> secondArrayList = new ArrayList<>();
            ArrayList<String> firstArrayList = new ArrayList<>();
            Collections.addAll(secondArrayList, secondArray);
            Collections.addAll(firstArrayList, firstArray);
            if (firstArrayList.removeAll(secondArrayList)) {
                Collections.addAll(firstArrayList, firstArray);
                Assert.fail(String.format("assertNotIntersect: %s: <br> Expected: %s.<br> Actual: %s", message, firstArrayList.toString(), Arrays.toString(secondArray)));
            }
        }
    }

    /**
     * assertMatch by ReporterNGExt
     *
     * @param value - analyzed text
     * @param regExp - regular expression
     * @param message - log message text
     */
    @Override
    public void assertMatch(String value, String regExp, String message) {
        assertMatch(value, regExp, message, takePassedScreenshot);
    }

    /**
     * assertMatch by ReporterNGExt
     *
     * @param value - analyzed text
     * @param regExp - regular expression
     * @param message - log message text
     * @param takeScreenshot - Set True to take screenshot if assert passed
     */
    public void assertMatch(String value, String regExp, String message, boolean takeScreenshot) {
        ReporterNGExt.logAssertMatch(ReporterNGExt.BUSINESS_LEVEL, value, regExp, message, takeScreenshot);
        if (allure) {
            if (!value.matches(regExp)) {
                Assert.fail(String.format("assertMatch: %s: <br> Expected: %s.<br> Actual: %s", message, regExp, value));
            }
        }
    }

    /**
     * Use it for taking screenshots.
     *
     * @param message - Screenshot message.
     */
    protected void logBusinessScreenshot(String message) {
        ReporterNGExt.logBusiness(ScreenShotMaker.takeScreenshotRemote(message));
    }

}
