package com.ggasoftware.jdi_ui_tests.selenium;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Roman_Iovlev on 8/10/2015.
 */
public class RemoteRunner {
    public static String remoteUrl;

    public static RemoteWebDriver getRemoteDriver(DriverTypes driverType) throws MalformedURLException {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setBrowserName(driverType.name);
        return new RemoteWebDriver(new URL(remoteUrl), capabilities);
    }
}
