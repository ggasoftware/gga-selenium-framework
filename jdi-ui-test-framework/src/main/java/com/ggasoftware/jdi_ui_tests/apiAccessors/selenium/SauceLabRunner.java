package com.ggasoftware.jdi_ui_tests.apiAccessors.selenium;

import com.saucelabs.common.*;
import com.saucelabs.junit.SauceOnDemandTestWatcher;
import org.junit.Rule;
import org.openqa.selenium.remote.*;

import java.net.MalformedURLException;
import java.net.URL;

import static java.lang.System.getenv;
import static org.openqa.selenium.remote.CapabilityType.PLATFORM;

/**
 * Created by Roman_Iovlev on 8/4/2015.
 */
public class SauceLabRunner implements SauceOnDemandSessionIdProvider {
    public static SauceOnDemandAuthentication authentication =
            new SauceOnDemandAuthentication(getenv("SAUCE_USER_NAME"), getenv("SAUCE_API_KEY"));

    @Rule
    public SauceOnDemandTestWatcher resultReportingTestWatcher = new SauceOnDemandTestWatcher(
            this, authentication);

    private static String sessionId;
    public String getSessionId() {
        return sessionId;
    }
    public static void setSauceSessionID(RemoteWebDriver remoteDriver) {
        sessionId = remoteDriver.getSessionId().toString();
    }

    public static DesiredCapabilities getSauceDesiredCapabilities(DriverTypes driverType) {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setBrowserName(getenv("SELENIUM_BROWSER"));
        capabilities.setVersion(getenv("SELENIUM_VERSION"));
        capabilities.setCapability(PLATFORM, getenv("SELENIUM_PLATFORM"));
        return capabilities;
    }

    public static URL getSauceUrl() throws MalformedURLException {
        return new URL("http://"
                + authentication.getUsername() + ":"
                + authentication.getAccessKey()
                + "@ondemand.saucelabs.com:80/wd/hub");
    }
}
