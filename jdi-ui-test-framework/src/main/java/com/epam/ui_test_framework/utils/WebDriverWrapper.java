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
package com.epam.ui_test_framework.utils;

import org.openqa.selenium.*;
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

import java.io.File;
import java.net.URL;
import java.util.Set;

import static com.epam.ui_test_framework.settings.FrameworkSettings.logger;
import static com.epam.ui_test_framework.settings.FrameworkSettings.driverFactory;
import static com.epam.ui_test_framework.utils.usefulUtils.TryCatchUtil.tryGetResult;
import static java.lang.String.format;
import static java.util.Arrays.asList;

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
    /**
     * initialization RemoteWebDriver
     *
     * @param remoteUrl - remote host
     * @param capabilities - desired capabilities
     */
    public static void initRemoteWebDriver(String remoteUrl, Capabilities capabilities) {
        logger.debug(format("Initialization Remote Web Driver at url '%s'", remoteUrl));
        driverFactory.registerDriver(new RemoteWebDriver(tryGetResult(() -> new URL(remoteUrl)), capabilities));
        // driverFactory.getDriver().manage().window().maximize();
    }

    /**
     * initialization FirefoxDriver
     *
     * @param capabilities - desired capabilities
     */
    public static void initFirefoxDriver(Capabilities capabilities) {
        logger.debug("Initialization Firefox Driver");
        driverFactory.registerDriver(new FirefoxDriver(capabilities));
        // driverFactory.getDriver().manage().window().maximize();
    }

    /**
     * initialization ChromeDriver
     *
     * @param capabilities - desired capabilities
     */
    public static void initChromeDriver(Capabilities capabilities) {
        logger.debug("Initialization Chrome Driver");
        driverFactory.registerDriver(new ChromeDriver(capabilities));
        // driverFactory.getDriver().manage().window().maximize();
    }

    /**
     * initialization SafariDriver
     *
     * @param capabilities - desired capabilities
     */
    public static void initSafariDriver(Capabilities capabilities) {
        logger.debug("Initialization Safari Driver");
        driverFactory.registerDriver(new SafariDriver(capabilities));
        // driverFactory.getDriver().manage().window().maximize();
    }

    /**
     * initialization InternetExplorerDriver
     *
     * @param capabilities - desired capabilities
     */
    public static void initInternetExplorerDriver(Capabilities capabilities) {
        logger.debug("Initialization Internet Explorer Driver");
        driverFactory.registerDriver(new InternetExplorerDriver(capabilities));
        // driverFactory.getDriver().manage().window().maximize();
    }

    /**
     * initialization HtmlUnitDriver
     *
     * @param capabilities - desired capabilities
     */
    public static void initHtmlUnitDriver(Capabilities capabilities) {
        logger.debug("Initialization Html Unit Driver");
        driverFactory.registerDriver(new HtmlUnitDriver(capabilities));
        // driverFactory.getDriver().manage().window().maximize();
    }

    /**
     * initialization FF with some profile
     * Use it if you want to use your profile for FF. It doesn't work remotely.
     * Before running create your profile. Use cmd >> firefox.exe -ProfileManager -no-remote
     * @param path - profile path
     */
    public static void initFFProfile(String path) {
        logger.debug(format("Initialization Firefox Driver with Profile '%s'", path));
        File profileDir = new File(path);
        FirefoxProfile ffprofile = new FirefoxProfile(profileDir);
        ffprofile.setEnableNativeEvents(true);
        driverFactory.registerDriver(new FirefoxDriver(ffprofile));
        // driverFactory.getDriver().manage().window().maximize();
    }

     /**
     * initialization FirefoxDriver
     */
    public static void initFirefoxDriver() {
        logger.debug("Initialization Firefox Driver");
        FirefoxProfile profile = new FirefoxProfile();
        profile.setAcceptUntrustedCertificates(true);
        profile.setAssumeUntrustedCertificateIssuer(true);
        profile.setEnableNativeEvents(true);
        profile.setPreference("javascript.enabled", true);
        profile.setPreference("dom.max_script_run_time", 0);
        profile.setPreference("dom.max_chrome_script_run_time", 0);
        driverFactory.registerDriver(new FirefoxDriver(profile));
        // driverFactory.getDriver().manage().window().maximize();
    }

     /**
     * initialization InternetExplorerDriver
     */
    public static void initInternetExplorerDriver() {
        logger.debug("Initialization Internet Explorer Driver");
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setPlatform(Platform.WINDOWS);
        capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
        capabilities.setCapability(CapabilityType.HAS_NATIVE_EVENTS, true);
        capabilities.setJavascriptEnabled(true);
        capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
        capabilities.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);
        capabilities.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);
        driverFactory.registerDriver(new InternetExplorerDriver(capabilities));
        // driverFactory.getDriver().manage().window().maximize();
    }

     /**
     * initialization ChromeDriver
     *
     */
    public static void initChromeDriver() {
        logger.debug("Initialization Chrome Driver");
        ChromeOptions options = new ChromeOptions();
        options.addArguments(asList("--start-maximized", "--test-type", "--ignore-certificate-errors", "--disable-popup-blocking", "--allow-running-insecure-content", "--disable-translate", "--always-authorize-plugins"));
        driverFactory.registerDriver(new ChromeDriver(options));
    }

     /**
     * initialization SafariDriver
     */
    public static void initSafariDriver() {
        logger.debug("Initialization Safari Driver");
        driverFactory.registerDriver(new SafariDriver());
        // driverFactory.getDriver().manage().window().maximize();
    }

    /**
     * Remove all cookies from browser
     */
    public static void deleteAllCookies() {
        logger.debug("Remove all cookies");
        driverFactory.getDriver().manage().deleteAllCookies();
    }

    /**
     * Delete the named cookie from the current domain. This is equivalent to setting the named
     * cookie's expiry date to some time in the past.
     *
     * @param cookieName The name of the cookie to delete
     */
    public static void deleteCookieNamed(String cookieName){
        logger.debug(format("Remove cookie: %s", cookieName));
        getCookieNamed(cookieName);
        driverFactory.getDriver().manage().deleteCookieNamed(cookieName);
        getCookieNamed(cookieName);
    }

    /**
     * Log cookie.
     *
     * @param cookieName The name of the cookie to log.
     */
    public static void getCookieNamed(String cookieName){
        logger.debug(format("getCookieNamed: %s", cookieName));
        if((driverFactory.getDriver().manage().getCookieNamed(cookieName))!=null) {
            logger.debug(format(" cookie: %s%s", cookieName, driverFactory.getDriver().manage().getCookieNamed(cookieName)));
        }
        else{
            logger.debug(format(" cookie: [ %s ] = null !!! )", cookieName));
        }
    }


    /**
     * Open web page
     *
     * @param page - target for navigate
     */
    public static void open(String page) {
        logger.debug(format("Navigate to page: %s", page));
        driverFactory.getDriver().get(page);
    }

    /**
     * Quit from WebDriver (Closes all browser windows and safely ends the session).
     */
    public static void quit() {
        if (driverFactory.getDriver() != null) {
            logger.debug("WebDriver Quit");
            driverFactory.getDriver().quit();
        }
    }


    /**
     *  Close the WebDriver (Close the browser window that the driver has focus of).
     */
    public static void close() {
        if (driverFactory.getDriver() != null) {
            logger.debug("WebDriver Close");
            driverFactory.getDriver().close();
        }
    }

    /**
     * Execute JavaScript code.
     *
     * @param script - target for execution
     * @return Execution results
     */
    public static Object executeScript(String script) {
        logger.debug(format("Execute Script - %s", script));
        return ((JavascriptExecutor) driverFactory.getDriver()).executeScript(script);
    }

    /**
     * Switch to frame.
     *
     * @param frame - target frame
     */
    public static void switchToFrame(String frame) {
        logger.debug(format("Switch To Frame - %s", frame));
        driverFactory.getDriver().switchTo().frame(frame);
    }

    /**
     * Switch to window.
     *
     * @param window - target window
     */
    public static void switchToWindow(String window) {
        logger.debug(format("Switch To Window - %s", window));
        driverFactory.getDriver().switchTo().window(window);
    }

    /**
     * Switch to active element.
     *
     * @return active WebElement
     */
    public static WebElement switchToActive() {
        logger.debug("Switch To Active");
        return driverFactory.getDriver().switchTo().activeElement();
    }

    /**
     * Switch to the next window.
     */
    public static void switchWindow() {
        logger.debug("Switch Window");
        Set<String> handles = driverFactory.getDriver().getWindowHandles();
        if (handles.size() > 1 ){
            String current = driverFactory.getDriver().getWindowHandle();
            handles.remove(current);
        }
        else{
            logger.debug("SwitchWindow: only one windows is available");
        }
        String newTab = handles.iterator().next();
        driverFactory.getDriver().switchTo().window(newTab);
    }

    /**
     * Switch to the default window.
     */
    public static void switchToDefault() {
        logger.debug("Switch To Default");
        driverFactory.getDriver().switchTo().defaultContent();
    }

    /**
     * Switch to the window with title.
     *
     * @param title - Expected active window title.
     * @return True if window is switched, otherwise False
     */
    public static boolean switchToWindowUsingTitle(String title) {
        logger.debug(format("Switch To Window Using Title '%s'", title));
        Set<String> availableWindows = driverFactory.getDriver().getWindowHandles();
        if (!availableWindows.isEmpty() && availableWindows.contains(title)) {
            driverFactory.getDriver().switchTo().window(title);
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
        logger.debug("Get Window Handle");
        return driverFactory.getDriver().getWindowHandle();
    }

    /**
     * Get windows handles.
     *
     * @return Window handles.
     */
    public static Set<String> getWindowHandles() {
        logger.debug("Get Windows Handles");
        return driverFactory.getDriver().getWindowHandles();
    }

    /**
     * Get window title.
     *
     * @return Window title.
     */
    public static String getWindowTitle() {
        logger.debug("Get Window Title");
        return driverFactory.getDriver().getTitle();
    }

    /**
     * Click "go Back" button on the current page
     */
    public static void goBack() {
        logger.debug("Go Back");
        driverFactory.getDriver().navigate().back();
    }

    /**
     * Click "go Forward" button on the current page
     */
    public static void goForward() {
        logger.debug("Go Forward");
        driverFactory.getDriver().navigate().forward();
    }

    /**
     * Click "Refresh" button on the current page
     */
    public static void refresh() {
        logger.debug("Refresh");
        driverFactory.getDriver().navigate().refresh();
    }

    /**
     * Scroll page to top by JS
     */
    public static void scrollPageToTop() {
        logger.debug("Scroll Page To Top");
        WebDriverWrapper.executeScript("window.scrollTo(0, 0);");
    }
    /**
     * Scroll page down by JS
     */
    public static void scrollPageDown(int iHeight) {
        logger.debug("Scroll Page To Top");
        WebDriverWrapper.executeScript("window.scrollTo(0, " + iHeight + ");");
    }
    /**
     * Scroll page up by JS
     */
    public static void scrollPageUp(int iHeight) {
        logger.debug("Scroll Page To Top");
        WebDriverWrapper.executeScript("window.scrollTo(0, " + iHeight + ");");
    }
    /**
     * Scroll page to end by JS
     */
    public static void scrollPageToEnd() {
        logger.debug("Scroll Page To End");
        WebDriverWrapper.executeScript("window.scrollTo(0, document.body.clientHeight )");
    }

}
