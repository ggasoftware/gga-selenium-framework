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
package com.ggasoftware.uitest.utils;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.util.ArrayList;

/**
 * Utility for use simple asserts.
 *
 * @author Belousov Andrey
 * @author Zharov Alexandr
 * @author Zhukov Anatoliy
 */
public class TestBaseWebDriver extends TestBase{

    public static WebDriver driver;
    public static String browserType = "chrome";
    public static boolean grid = false;
    public static String gridHub = "http://localhost:4444/wd/hub";
    public static String screenshotDirectory = "target/surefire-reports/html/screenshots";
    public static boolean simpleClassName = true;
    public static boolean takePassedScreenshot = false;
    public static boolean logFindElementLocator = true;

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
     * @param takeScreenshot - true for taking screenshorts at passed assection.
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
        if (grid){
            initWebDriverGrid();
        }else {
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
    private void initScreenshotDirectory() {
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
        ReporterNGExt.logAssertEquals(ReporterNGExt.BUSINESS_LEVEL, value, expectedValue, message, takePassedScreenshot);
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
        ReporterNGExt.logAssertNotEquals(ReporterNGExt.BUSINESS_LEVEL, value, notExpectedValue, message, takePassedScreenshot);
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
    }

    /**
     * assertArrayListEquals by ReporterNGExt
     *
     * @param value - actual Array List of String
     * @param expectedValue - expected Array List of String
     * @param message - log message text
     */
    public void assertArrayListEquals(ArrayList<String> value, ArrayList<String> expectedValue, String message) {
        ReporterNGExt.logAssertArrayListEquals(ReporterNGExt.BUSINESS_LEVEL, value, expectedValue, message, takePassedScreenshot);
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
        ReporterNGExt.logAssertEquals(ReporterNGExt.BUSINESS_LEVEL, value, expectedValue, message, takePassedScreenshot);
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
    }

    /**
     * assertTrue by ReporterNGExt
     *
     * @param what - expression
     * @param message - log message text
     */
    @Override
    public void assertTrue(boolean what, String message) {
        ReporterNGExt.logAssertTrue(ReporterNGExt.BUSINESS_LEVEL, what, message, takePassedScreenshot);
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
    }

    /**
     * assertFalse by ReporterNGExt
     *
     * @param what - expression
     * @param message - log message text
     */
    @Override
    public void assertFalse(boolean what, String message) {
        ReporterNGExt.logAssertFalse(ReporterNGExt.BUSINESS_LEVEL, what, message, takePassedScreenshot);
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
        ReporterNGExt.logAssertContains(ReporterNGExt.BUSINESS_LEVEL, toSearchIn, whatToSearch, message, takePassedScreenshot);
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
    }

    /**
     * assertNotContains by ReporterNGExt
     *
     * @param toSearchIn - text search in
     * @param whatToSearch - text to search
     * @param message - log message text
     */
    public void assertNotContains(String toSearchIn, String whatToSearch, String message) {
        ReporterNGExt.logAssertNotContains(ReporterNGExt.BUSINESS_LEVEL, toSearchIn, whatToSearch, message, takePassedScreenshot);
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
    }

    /**
     * assertNull by ReporterNGExt
     *
     * @param what - analyzed object
     * @param message - log message text
     */
    @Override
    public void assertNull(Object what, String message) {
        ReporterNGExt.logAssertNull(ReporterNGExt.BUSINESS_LEVEL, what, message, takePassedScreenshot);
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
    }

    /**
     * assertNotNull by ReporterNGExt
     *
     * @param what - analyzed object
     * @param message - log message text
     */
    @Override
    public void assertNotNull(Object what, String message) {
        ReporterNGExt.logAssertNotNull(ReporterNGExt.BUSINESS_LEVEL, what, message, takePassedScreenshot);
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
    }

    /**
     * assertEmpty by ReporterNGExt
     *
     * @param what - analyzed text
     * @param message - log message text
     */
    @Override
    public void assertEmpty(String what, String message) {
        ReporterNGExt.logAssertEmpty(ReporterNGExt.BUSINESS_LEVEL, what, message, takePassedScreenshot);
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
        ReporterNGExt.logAssertNotIntersect(ReporterNGExt.BUSINESS_LEVEL, firstArray, secondArray, message, takePassedScreenshot);
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
        ReporterNGExt.logAssertMatch(ReporterNGExt.BUSINESS_LEVEL, value, regExp, message, takePassedScreenshot);
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
    }

    /**
     * Use it for taking screenshots.
     *
     * @param message - Screenshort message.
     */
    protected void logBusinessScreenshot(String message) {
        ReporterNGExt.logBusiness(ScreenShotMaker.takeScreenshotRemote(message));
    }

}
