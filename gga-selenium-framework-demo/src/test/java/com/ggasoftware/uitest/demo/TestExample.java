package com.ggasoftware.uitest.demo;

import com.ggasoftware.uitest.demo.panel.TestPanel;
import com.ggasoftware.uitest.utils.*;
import org.apache.log4j.LogManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.net.MalformedURLException;

/**
 * Test class
 *
 * @author Belousov Andrey
 */
public class TestExample extends TestBaseWebDriver {

    public static final String browser = "chrome";
    public static final String website = "https://www.google.com";
    public static final String chromedriver = "c:\\Selenium\\Drivers\\chromedriver.exe";
    public static final String iedriver = "c:\\Selenium\\Drivers\\IEDriverServer.exe";


    @BeforeTest
    public void setUp() throws MalformedURLException {

        System.setProperty("webdriver.chrome.driver", chromedriver);
        System.setProperty("webdriver.ie.driver", iedriver);
        setBrowserType(browser);
        takePassedScreenshot(true);
        initWebDriver();
        ReporterNGExt.logTechnical(String.format("    ===== Processing Test Pack %s =====", this.getClass().getSimpleName()));
        WebDriverWrapper.open(website);

    }

    @AfterTest
    public void quit() {
        WebDriverWrapper.quit();
    }


    @Test
    public void Google_Page() {

        assertTrue(TestPanel.get().logo.isDisplayed(), "check logo", true);
        assertTrue(TestPanel.get().searchBtn.isDisplayed(), "check search button is visible");
    }

    @Test
    public void Google_Search() {

        TestPanel.get().textField.setText("Selenium");
        assertFalse(TestPanel.get().searchBtn.isDisplayed(), "check search button is not visible");
        TestPanel.get().textField.setText("Selenium")
                .searchBtn2.click();
        assertEquals(TestPanel.get().resultsLinks.getWebElement(0).getText(), "Selenium - Web Browser Automation", "check first link text");
        TestPanel.get().resultsLinks.getElement(0).click();
        WebDriverWrapper.waitWindowsCount(2);
        WebDriverWrapper.switchWindow();
        assertEquals(WebDriverWrapper.getDriver().getTitle(), "Selenium - Web Browser Automation", "check first link title", true);

    }

}
