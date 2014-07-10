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

import com.ggasoftware.uitest.autoit.UAutoItX;
import org.openqa.selenium.*;
import org.openqa.selenium.browserlaunchers.Sleeper;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Manage WebDriver instances.
 *
 * @author Belin Yury
 * @author Belousov Andrey
 * @author Shubin Konstantin
 * @author Zharov Alexandr
 * @author Zhukov Anatoliy
 */
public final class WebDriverWrapper {

    public static final ThreadLocal<WebDriver> threadDriver = new ThreadLocal<>();
    private WebDriverWrapper(){}

    public static int TIMEOUT = 30; //seconds

    public static WebDriver getDriver() {
        return threadDriver.get();
    }

    public static void setWebDriver(WebDriver webDriver) {
        threadDriver.set(webDriver);
    }

    /**
     * Set Default timeout for WebDriver
     *
     * @param timeout - seconds to wait web element
     */
    public static void setDefaultTimeout(int timeout) {
        TIMEOUT = timeout;
    }

    /**
     * initialization RemoteWebDriver
     *
     * @param remoteUrl - remote host
     * @param capabilities - desired capabilities
     */
    public static void initRemoteWebDriver(String remoteUrl, Capabilities capabilities) throws MalformedURLException {
        ReporterNGExt.logTechnical(String.format("Initialization Remote Web Driver at url '%s'", remoteUrl));
        setWebDriver(new RemoteWebDriver(new URL(remoteUrl), capabilities));
        setTimeout(TIMEOUT);
        getDriver().manage().window().maximize();
    }

    /**
     * initialization FirefoxDriver
     *
     * @param capabilities - desired capabilities
     */
    public static void initFirefoxDriver(Capabilities capabilities) {
        ReporterNGExt.logTechnical("Initialization Firefox Driver");
        setWebDriver(new FirefoxDriver(capabilities));
        setTimeout(TIMEOUT);
        getDriver().manage().window().maximize();
    }

    /**
     * initialization ChromeDriver
     *
     * @param capabilities - desired capabilities
     */
    public static void initChromeDriver(Capabilities capabilities) {
        ReporterNGExt.logTechnical("Initialization Chrome Driver");
        setWebDriver(new ChromeDriver(capabilities));
        setTimeout(TIMEOUT);
        getDriver().manage().window().maximize();
    }

    /**
     * initialization SafariDriver
     *
     * @param capabilities - desired capabilities
     */
    public static void initSafariDriver(Capabilities capabilities) {
        ReporterNGExt.logTechnical("Initialization Safari Driver");
        setWebDriver(new SafariDriver(capabilities));
        setTimeout(TIMEOUT);
        getDriver().manage().window().maximize();
    }

    /**
     * initialization InternetExplorerDriver
     *
     * @param capabilities - desired capabilities
     */
    public static void initInternetExplorerDriver(Capabilities capabilities) {
        ReporterNGExt.logTechnical("Initialization Internet Explorer Driver");
        setWebDriver(new InternetExplorerDriver(capabilities));
        setTimeout(TIMEOUT);
        getDriver().manage().window().maximize();
    }

    /**
     * initialization HtmlUnitDriver
     *
     * @param capabilities - desired capabilities
     */
    public static void initHtmlUnitDriver(Capabilities capabilities) {
        ReporterNGExt.logTechnical("Initialization Html Unit Driver");
        setWebDriver(new HtmlUnitDriver(capabilities));
        setTimeout(TIMEOUT);
        getDriver().manage().window().maximize();
    }

    /**
     * initialization FF with some profile
     * Use it if you want to use your profile for FF. It doesn't work remotely.
     * Before running create your profile. Use cmd >> firefox.exe -ProfileManager -no-remote
     * @param path - profile path
     */
    public static void initFFProfile(String path) {
        ReporterNGExt.logTechnical(String.format("Initialization Firefox Driver with Profile '%s'", path));
        File profileDir = new File(path);
        FirefoxProfile ffprofile = new FirefoxProfile(profileDir);
        ffprofile.setEnableNativeEvents(true);
        setWebDriver(new FirefoxDriver(ffprofile));
        getDriver().manage().window().maximize();
    }
    
     /**
     * initialization FirefoxDriver
     */
    public static void initFirefoxDriver() {
        ReporterNGExt.logTechnical("Initialization Firefox Driver");
        FirefoxProfile profile = new FirefoxProfile();
        profile.setAcceptUntrustedCertificates(true);
        profile.setAssumeUntrustedCertificateIssuer(true);
        profile.setEnableNativeEvents(true);
        profile.setPreference("javascript.enabled", true);
        profile.setPreference("dom.max_script_run_time", 0);
        profile.setPreference("dom.max_chrome_script_run_time", 0);
        setWebDriver(new FirefoxDriver(profile));
        setTimeout(TIMEOUT);
        getDriver().manage().window().maximize();
    }
    
     /**
     * initialization InternetExplorerDriver
     */
    public static void initInternetExplorerDriver() {
        ReporterNGExt.logTechnical("Initialization Internet Explorer Driver");
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setPlatform(Platform.WINDOWS);
        capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
        capabilities.setCapability(CapabilityType.HAS_NATIVE_EVENTS, true);
        capabilities.setJavascriptEnabled(true);
        capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
        capabilities.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);
        capabilities.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);
        setWebDriver(new InternetExplorerDriver(capabilities));
        setTimeout(TIMEOUT);
        getDriver().manage().window().maximize();
    }
    
     /**
     * initialization ChromeDriver
     *
     */
    public static void initChromeDriver() {
        ReporterNGExt.logTechnical("Initialization Chrome Driver");
        ChromeOptions options = new ChromeOptions();
        options.addArguments(Arrays.asList("--start-maximized", "--test-type", "--ignore-certificate-errors", "--disable-popup-blocking", "--allow-running-insecure-content", "--disable-translate", "--always-authorize-plugins"));
        setWebDriver(new ChromeDriver(options));
        setTimeout(TIMEOUT);
    }
    
     /**
     * initialization SafariDriver
     */
    public static void initSafariDriver() {
        ReporterNGExt.logTechnical("Initialization Safari Driver");
        setWebDriver(new SafariDriver());
        setTimeout(TIMEOUT);
        getDriver().manage().window().maximize();
    }

    /**
     * Set Wait and Script timeouts for WebDriver
     *
     * @param timeout - seconds to wait element and running script
     */
    public static void setTimeout(int timeout) {
        getDriver().manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);
        getDriver().manage().timeouts().setScriptTimeout(timeout, TimeUnit.SECONDS);
    }

    /**
     * Set Wait and Script timeouts for WebDriver
     *
     * @param timeout - milliseconds to wait element and running script
     */
    public static void setTimeoutMls(int timeout) {
    	getDriver().manage().timeouts().implicitlyWait(timeout, TimeUnit.MILLISECONDS);
    	getDriver().manage().timeouts().setScriptTimeout(timeout, TimeUnit.MILLISECONDS);
    }

    /**
     * Remove all cookies from browser
     */
    public static void deleteAllCookies() {
        ReporterNGExt.logTechnical("Remove all cookies");
        getDriver().manage().deleteAllCookies();
    }

    /**
     * Delete the named cookie from the current domain. This is equivalent to setting the named
     * cookie's expiry date to some time in the past.
     *
     * @param cookieName The name of the cookie to delete
     */
    public static void deleteCookieNamed(String cookieName){
        ReporterNGExt.logTechnical(String.format("Remove cookie: %s", cookieName));
        getCookieNamed(cookieName);
        getDriver().manage().deleteCookieNamed(cookieName);
        getCookieNamed(cookieName);
    }

    /**
     * Log cookie.
     *
     * @param cookieName The name of the cookie to log.
     */
    public static void getCookieNamed(String cookieName){
        ReporterNGExt.logTechnical(String.format("getCookieNamed: %s", cookieName));
        if((getDriver().manage().getCookieNamed(cookieName))!=null) {
            ReporterNGExt.logTechnical(String.format(" cookie: %s%s", cookieName, getDriver().manage().getCookieNamed(cookieName)));
        }
        else{
            ReporterNGExt.logTechnical(String.format(" cookie: [ %s ] = null !!! )", cookieName));
        }
    }


    /**
     * Open web page
     *
     * @param page - target for navigate
     */
    public static void open(String page) {
        ReporterNGExt.logTechnical(String.format("Navigate to page: %s", page));
        getDriver().get(page);
    }

    /**
     * Quit from WebDriver (Closes all browser windows and safely ends the session).
     */
    public static void quit() {
        if (getDriver() != null) {
            ReporterNGExt.logTechnical("WebDriver Quit");
            getDriver().quit();
        }
    }


    /**
     *  Close the WebDriver (Close the browser window that the driver has focus of).
     */
    public static void close() {
        if (getDriver() != null) {
            ReporterNGExt.logTechnical("WebDriver Close");
            getDriver().close();
        }
    }

    /**
     * Execute JavaScript code.
     *
     * @param script - target for execution
     * @return Execution results
     */
    public static Object executeScript(String script) {
        ReporterNGExt.logTechnical(String.format("Execute Script - %s", script));
        return ((JavascriptExecutor) getDriver()).executeScript(script);
    }

    /**
     * Switch to frame.
     *
     * @param frame - target frame
     */
    public static void switchToFrame(String frame) {
        ReporterNGExt.logTechnical(String.format("Switch To Frame - %s", frame));
        getDriver().switchTo().frame(frame);
    }

    /**
     * Switch to window.
     *
     * @param window - target window
     */
    public static void switchToWindow(String window) {
        ReporterNGExt.logTechnical(String.format("Switch To Window - %s", window));
        getDriver().switchTo().window(window);
    }

    /**
     * Switch to active element.
     *
     * @return active WebElement
     */
    public static WebElement switchToActive() {
        ReporterNGExt.logTechnical("Switch To Active");
        return getDriver().switchTo().activeElement();
    }

    /**
     * Switch to the next window.
     */
    public static void switchWindow() {
        ReporterNGExt.logTechnical("Switch Window");
        Set<String> handles = getDriver().getWindowHandles();
        if (handles.size() > 1 ){
            String current = getDriver().getWindowHandle();
            handles.remove(current);
        }
        else{
            ReporterNGExt.logTechnical("SwitchWindow: only one windows is available");
        }
        String newTab = handles.iterator().next();
        getDriver().switchTo().window(newTab);
    }

    /**
     * Close active and switch to the next window.
     */
    public static void switchCloseWindow() {
        ReporterNGExt.logTechnical("Switch And Close Window");
        new WebDriverWait(getDriver(), TIMEOUT) {
        }.until(new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver wDriver) {
                return (wDriver.getWindowHandles().size() > 1);
            }
        });
        Set<String> handles = getDriver().getWindowHandles();
        if (handles.size() == 1 ){
            ReporterNGExt.logTechnical("switchCloseWindow: only one windows is available");
            return;
        }
        getDriver().close();
        Sleeper.sleepTight(500);
        handles = getDriver().getWindowHandles();
        getDriver().switchTo().window(handles.iterator().next());
    }

    public static boolean waitWindowsCount(final int numberOfWindows){
        ReporterNGExt.logTechnical("Wait for Open Windows Count");
        new WebDriverWait(getDriver(), TIMEOUT) {
        }.until(new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver wDriver) {
                return (wDriver.getWindowHandles().size() == numberOfWindows);
            }
        });
        return false;
    }

    /**
     * Wait until all windows not empty.
     *
     * @param numberOfWindows - number of existing windows
     */
    public static void waitAllWindowsFullLoaded(final int numberOfWindows) {
        ReporterNGExt.logTechnical("Wait for All Windows Full Loaded");
    	new WebDriverWait(getDriver(), TIMEOUT) {
    	     }.until(new ExpectedCondition<Boolean>() { 
    	         @Override 
    	         public Boolean apply(WebDriver wDriver) {
    	             return (wDriver.getWindowHandles().size() == numberOfWindows);
    	         } 
    	     });
    	     
        new WebDriverWait(getDriver(), TIMEOUT) {
        }.until(new ExpectedCondition<Boolean>() { 
            @Override 
            public Boolean apply(WebDriver wDriver) {
            	boolean fullLoaded = true;
            	for (String window : wDriver.getWindowHandles()) {
					if (window.isEmpty()) {
						fullLoaded = false;
						break;
					}
				}
                return (fullLoaded); 
            } 
        });
        Sleeper.sleepTight(1000);
    }

    /**
     * Switch to the default window.
     */
    public static void switchToDefault() {
        ReporterNGExt.logTechnical("Switch To Default");
        getDriver().switchTo().defaultContent();
    }

    /**
     * Switch to the window with title.
     *
     * @param title - Expected active window title.
     * @return True if window is switched, otherwise False
     */
    public static boolean switchToWindowUsingTitle(String title) {
        ReporterNGExt.logTechnical(String.format("Switch To Window Using Title '%s'", title));
        Set<String> availableWindows = getDriver().getWindowHandles();
        if (!availableWindows.isEmpty() && availableWindows.contains(title)) {
            getDriver().switchTo().window(title);
            return true;
        }
        return false;
    }

    /**
     * Get active window handle.
     *
     * @return Active window handle.
     */
    public static String getWindowHandle() {
        ReporterNGExt.logTechnical("Get Window Handle");
        return getDriver().getWindowHandle();
    }

    /**
     * Get windows handles.
     *
     * @return Window handles.
     */
    public static Set<String> getWindowHandles() {
        ReporterNGExt.logTechnical("Get Windows Handles");
        return getDriver().getWindowHandles();
    }

    /**
     * Get window title.
     *
     * @return Window title.
     */
    public static String getWindowTitle() {
        ReporterNGExt.logTechnical("Get Window Title");
        return getDriver().getTitle();
    }

    /**
     * Click "go Back" button on the current page
     */
    public static void goBack() {
        ReporterNGExt.logTechnical("Go Back");
        getDriver().navigate().back();
    }

    /**
     * Click "go Forward" button on the current page
     */
    public static void goForward() {
        ReporterNGExt.logTechnical("Go Forward");
        getDriver().navigate().forward();
    }

    /**
     * Click "Refresh" button on the current page
     */
    public static void refresh() {
        ReporterNGExt.logTechnical("Refresh");
        getDriver().navigate().refresh();
    }

    /**
     * Sleep by seconds.
     *
     * @param seconds to sleeping.
     */
    public static void sleep(int seconds) {
        ReporterNGExt.logTechnical(String.format("Sleeping %d seconds", seconds));
        Sleeper.sleepTightInSeconds(seconds);
    }

    /**
     * Scroll page to top by JS
     */
    public static void scrollPageToTop() {
        ReporterNGExt.logTechnical("Scroll Page To Top");
        WebDriverWrapper.executeScript("window.scrollTo(0, 0);");
    }
    /**
     * Scroll page down by JS
     */
    public static void scrollPageDown(int iHeight) {
        ReporterNGExt.logTechnical("Scroll Page To Top");
        WebDriverWrapper.executeScript("window.scrollTo(0, " + iHeight + ");");
    }
    /**
     * Scroll page up by JS
     */
    public static void scrollPageUp(int iHeight) {
        ReporterNGExt.logTechnical("Scroll Page To Top");
        WebDriverWrapper.executeScript("window.scrollTo(0, " + iHeight + ");");
    }
    /**
     * Scroll page to end by JS
     */
    public static void scrollPageToEnd() {
        ReporterNGExt.logTechnical("Scroll Page To End");
        WebDriverWrapper.executeScript("window.scrollTo(0, document.body.clientHeight )");
    }

    /**
     * Wait until windows has title.
     *
     * @param title - Expected window title.
     */
    public static void waitForTitle(String title) {
        waitForTitle(title, TIMEOUT);
    }
    /**
     * Wait until windows has title.
     *
     * @param timeoutSec to wait until windows has title.
     * @param title - Expected window title.
     */
    public static void waitForTitle(String title, int timeoutSec) {
        ReporterNGExt.logAction(getDriver(), "", String.format("waitForTitle: %s", title));
        long start = System.currentTimeMillis()/1000;
        WebDriverWait wait = (WebDriverWait) new WebDriverWait(getDriver(), timeoutSec)
                .ignoring(StaleElementReferenceException.class);
        try {
            wait.until(ExpectedConditions.titleIs(title));
        }catch (TimeoutException ignored){
            ReporterNGExt.logTechnical(String.format("waitForTitle: [ %s ] during: [ %d ] sec ", title, System.currentTimeMillis() / 1000 - start));
        }
        ReporterNGExt.logAssertEquals(ReporterNGExt.BUSINESS_LEVEL, getDriver().getTitle(), title, "waitForTitle", TestBaseWebDriver.takePassedScreenshot);
    }
    /**
     * Wait until windows title contains text.
     *
     * @param title - Expected window title contains text.
     */
    public static void waitForTitleContains(String title) {
        waitForTitleContains(title, TIMEOUT);
    }
    /**
     * Wait until windows title contains text.
     *
     * @param timeoutSec to wait until windows title contains text.
     * @param title - Expected window title contains text.
     */
    public static void waitForTitleContains(String title, int timeoutSec) {
        ReporterNGExt.logAction(getDriver(), "", String.format("waitForTitleContains: %s", title));
        long start = System.currentTimeMillis()/1000;
        WebDriverWait wait = (WebDriverWait) new WebDriverWait(getDriver(), timeoutSec)
                .ignoring(StaleElementReferenceException.class);
        try{
            wait.until(ExpectedConditions.titleContains(title));
        }catch (TimeoutException ignored){
            ReporterNGExt.logTechnical(String.format("waitForTitleContains: [ %s ] during: [ %d ] sec ", title, System.currentTimeMillis() / 1000 - start));
        }
        ReporterNGExt.logAssertContains(ReporterNGExt.BUSINESS_LEVEL, getDriver().getTitle(), title, "waitForTitleContains", TestBaseWebDriver.takePassedScreenshot);
    }
    /**
     * Wait until windows title not contains text.
     *
     * @param title - Expected window title not contains text.
     */
    public static void waitForTitleNotContains(String title) {
        waitForTitleNotContains(title, TIMEOUT);
    }
    /**
     * Wait until windows title not contains text.
     *
     * @param timeoutSec to wait until windows title not contains text.
     * @param title - Expected window title not contains text.
     */
    public static void waitForTitleNotContains(String title, int timeoutSec) {
        ReporterNGExt.logAction(getDriver(), "", String.format("waitForTitleNotContains: %s", title));
        long start = System.currentTimeMillis()/1000;
        WebDriverWait wait = (WebDriverWait) new WebDriverWait(getDriver(), timeoutSec)
                .ignoring(StaleElementReferenceException.class);
        try{
            wait.until(ExpectedConditions.not(ExpectedConditions.titleContains(title)));
        }catch (TimeoutException ignored){
            ReporterNGExt.logTechnical(String.format("waitForTitleNotContains: [ %s ] during: [ %d ] sec ", title, System.currentTimeMillis() / 1000 - start));
        }
        ReporterNGExt.logAssertNotContains(ReporterNGExt.BUSINESS_LEVEL, getDriver().getTitle(), title, "waitForTitleNotContains", TestBaseWebDriver.takePassedScreenshot);
    }

    /**
     * Wait until title is changed text
     *
     * @param title before change
     */
    public static void waitForTitleChanged(final String title) {
        waitForTitleChanged(title, TIMEOUT);
    }

    /**
     * Wait until element is changed text
     *
     * @param title before change
     * @param timeoutSec seconds to wait until element is changed text
     */
    public static void waitForTitleChanged(final String title, int timeoutSec) {
        boolean isChanged;
        ReporterNGExt.logAction(getDriver(), "", String.format("waitForTitleChanged: %s", title));
        long start = System.currentTimeMillis() / 1000;
        WebDriverWait wait = (WebDriverWait) new WebDriverWait(getDriver(), timeoutSec)
                .ignoring(StaleElementReferenceException.class);
        try{
            isChanged = wait.until(ExpectedConditions.not(ExpectedConditions.titleIs(title)));
        }catch (TimeoutException e){
            ReporterNGExt.logTechnical(String.format("waitForTitleChanged: [ %s ] during: [ %d ] sec ", title, System.currentTimeMillis() / 1000 - start));
            isChanged = false;
        }
        ReporterNGExt.logAssertTrue(ReporterNGExt.BUSINESS_LEVEL, isChanged, String.format("waitForTitleChanged: title '%s' should be changed", title), TestBaseWebDriver.takePassedScreenshot);
    }

    /**
     * Wait until any element with text presents at web page.
     *
     * @param text - element text to be presents..
     */
    public static void waitForTextToBePresent(String text) {
        waitForTextToBePresent(text, TIMEOUT);
    }
    /**
     * Wait until any element with text presents at web page.
     *
     * @param text - element text to be presents.
     * @param timeoutSec to wait until presents.
     */
    public static void waitForTextToBePresent(String text, int timeoutSec) {
        ReporterNGExt.logAction(getDriver(), "", String.format("waitForTextToBePresent: %s", text));
        boolean isPresent;
        long start = System.currentTimeMillis()/1000;
        WebDriverWait wait = (WebDriverWait) new WebDriverWait(getDriver(), timeoutSec)
                .ignoring(StaleElementReferenceException.class);
        setTimeout(timeoutSec);
        try {
            isPresent = wait.until(ExpectedConditions.textToBePresentInElementLocated(By.xpath("//*"), text));
        }catch (TimeoutException ignored){
            ReporterNGExt.logTechnical(String.format("waitForTextToBePresent: [ %s ] during: [ %d ] sec ", text, System.currentTimeMillis() / 1000 - start));
            isPresent = false;
        }
        setTimeout(TIMEOUT);
        ReporterNGExt.logAssertTrue(ReporterNGExt.BUSINESS_LEVEL, isPresent, String.format("waitForTextToBePresent: element with text '%s' should be exists", text), TestBaseWebDriver.takePassedScreenshot);
    }
    /**
     * Wait until any element with text not presents at web page.
     *
     * @param text - element text to not be presents.
     */
    public static void waitForTextToNotBePresent(String text) {
        waitForTextToNotBePresent(text, TIMEOUT);
    }
    /**
     * Wait until any element with text not presents at web page.
     *
     * @param text - element text to not be presents.
     * @param timeoutSec to wait until not presents.
     */
    public static void waitForTextToNotBePresent(String text, int timeoutSec) {
        ReporterNGExt.logAction(getDriver(), "", String.format("waitForTextToNotBePresent: %s", text));
        boolean isNotPresent;
        long start = System.currentTimeMillis()/1000;
        WebDriverWait wait = (WebDriverWait) new WebDriverWait(getDriver(), timeoutSec)
                .ignoring(StaleElementReferenceException.class);
        setTimeout(timeoutSec);
        try {
            isNotPresent = wait.until(ExpectedConditions.not(ExpectedConditions.textToBePresentInElementLocated(By.xpath("//*"), text)));
        }catch (TimeoutException ignored){
            ReporterNGExt.logTechnical(String.format("waitForTextToNotBePresent: [ %s ] during: [ %d ] sec ", text, System.currentTimeMillis() / 1000 - start));
            isNotPresent = false;
        }
        setTimeout(TIMEOUT);
        ReporterNGExt.logAssertTrue(ReporterNGExt.BUSINESS_LEVEL, isNotPresent, String.format("waitForTextToNotBePresent: element with text '%s' should not be exists", text), TestBaseWebDriver.takePassedScreenshot);
    }
    /**
     * Wait until link presents at web page.
     *
     * @param linkText - link to be presents.
     */
    public static void waitForLinkToBePresent(String linkText) {
        waitForLinkToBePresent(linkText, TIMEOUT);
    }
    /**
     * Wait until link presents at web page.
     *
     * @param linkText - linkText to be presents.
     * @param timeoutSec to wait until presents.
     */
    public static void waitForLinkToBePresent(String linkText, int timeoutSec) {
        ReporterNGExt.logAction(getDriver(), "", String.format("waitForLinkToBePresent: %s", linkText));
        boolean isPresent;
        long start = System.currentTimeMillis()/1000;
        WebDriverWait wait = (WebDriverWait) new WebDriverWait(getDriver(), timeoutSec)
                .ignoring(StaleElementReferenceException.class);
        setTimeout(timeoutSec);
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(By.linkText(linkText)));
            isPresent = true;
        }catch (TimeoutException ignored){
            ReporterNGExt.logTechnical(String.format("waitForLinkToBePresent: [ %s ] during: [ %d ] sec ", linkText, System.currentTimeMillis() / 1000 - start));
            isPresent = false;
        }
        setTimeout(TIMEOUT);
        ReporterNGExt.logAssertTrue(ReporterNGExt.BUSINESS_LEVEL, isPresent, String.format("waitForLinkToBePresent: link with text '%s' should be exists", linkText), TestBaseWebDriver.takePassedScreenshot);
    }
    /**
     * Wait until link not presents at web page.
     *
     * @param linkText - link to not be presents.
     */
    public static void waitForLinkToNotBePresent(String linkText) {
        waitForLinkToNotBePresent(linkText, TIMEOUT);
    }
    /**
     * Wait until link not presents at web page.
     *
     * @param linkText - linkText to not be presents.
     * @param timeoutSec to wait until not presents.
     */
    public static void waitForLinkToNotBePresent(String linkText, int timeoutSec) {
        ReporterNGExt.logAction(getDriver(), "", String.format("waitForLinkToNotBePresent: %s", linkText));
        boolean isNotPresent;
        long start = System.currentTimeMillis()/1000;
        WebDriverWait wait = (WebDriverWait) new WebDriverWait(getDriver(), timeoutSec)
                .ignoring(StaleElementReferenceException.class);
        setTimeout(timeoutSec);
        try {
            isNotPresent = wait.until(ExpectedConditions.invisibilityOfElementLocated(By.linkText(linkText)));
        }catch (TimeoutException ignored){
            ReporterNGExt.logTechnical(String.format("waitForLinkToNotBePresent: [ %s ] during: [ %d ] sec ", linkText, System.currentTimeMillis() / 1000 - start));
            isNotPresent = false;
        }
        setTimeout(TIMEOUT);
        ReporterNGExt.logAssertTrue(ReporterNGExt.BUSINESS_LEVEL, isNotPresent, String.format("waitForLinkToNotBePresent: link with text '%s' should not be exists", linkText), TestBaseWebDriver.takePassedScreenshot);
    }
    /**
     * Wait until native window is not exists.
     *
     * @param title of native window
     */
    public static void waitForNativeWindow(final String title) {
        waitForNativeWindow(title, TIMEOUT);
    }
    /**
     * Wait until native window is not exists.
     *
     * @param title of native window
     * @param timeoutSec to wait until native window is not exists.
     */
    public static void waitForNativeWindow(final String title, int timeoutSec) {
        ReporterNGExt.logAction(getDriver(), "", String.format("waitForNativeWindow: %s", title));
        boolean isPresent;
        long start = System.currentTimeMillis()/1000;
        WebDriverWait wait = new WebDriverWait(getDriver(), timeoutSec);
        try {
            isPresent = wait.until(
                    new ExpectedCondition<Boolean>() {
                        @Override
                        public Boolean apply(WebDriver driver) {
                            UAutoItX UIMethods = new UAutoItX();
                            return UIMethods.WinExists(title, "") > 0;
                        }
                    }
            );
        } catch (TimeoutException e) {
            ReporterNGExt.logTechnical(String.format("waitForNativeWindow: [ %s ] during: [ %d ] sec ", title, System.currentTimeMillis() / 1000 - start));
            isPresent = false;
        }
        ReporterNGExt.logAssertTrue(ReporterNGExt.BUSINESS_LEVEL, isPresent, String.format("waitForNativeWindow: native window '%s' should be exists", title), TestBaseWebDriver.takePassedScreenshot);
    }


}
