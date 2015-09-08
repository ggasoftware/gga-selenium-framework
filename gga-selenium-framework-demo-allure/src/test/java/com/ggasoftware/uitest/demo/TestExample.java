package com.ggasoftware.uitest.demo;

import com.ggasoftware.uitest.demo.panel.TestPanel;
import com.ggasoftware.uitest.utils.*;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.yandex.qatools.allure.annotations.Step;


import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Properties;

/**
 * Test class
 *
 * @author Belousov Andrey
 */
public class TestExample extends TestBaseWebDriver {

    public static Properties testproperties;
    public static String browser;
    public static String website;

    @BeforeTest
    public void setUp() throws MalformedURLException {

        testproperties = new Properties();
        try {
            testproperties.load(ClassLoader.getSystemResourceAsStream("test.properties"));
        } catch (IOException ignored) {
        }

        System.setProperty("webdriver.chrome.driver", testproperties.getProperty("chromedriver"));
        System.setProperty("webdriver.ie.driver", testproperties.getProperty("iedriver"));

        website = System.getProperty("website", testproperties.getProperty("website"));
        browser = System.getProperty("browser", testproperties.getProperty("browser"));

        setBrowserType(browser);
        takePassedScreenshot(false);
        useAllure(true);
        useReportPortal(true);
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

    @Step
    public void SetTestToField(){
        TestPanel.get().textField.setText("Selenium");
    }

    @Test
    public void Google_Search() {

        SetTestToField();
        assertFalse(TestPanel.get().searchBtn.isDisplayed(), "check search button is not visible");
        TestPanel.get().textField.setText("Selenium")
                .searchBtn2.click();
        assertEquals(TestPanel.get().resultsLinks.getWebElement(0).getText(), "Selenium 5 - Web Browser Automation", "check first link text");
        TestPanel.get().resultsLinks.getElement(0).click();
        WebDriverWrapper.waitWindowsCount(2);
        WebDriverWrapper.switchWindow();
        assertEquals(WebDriverWrapper.getDriver().getTitle(), "Selenium - Web Browser Automation", "check first link title", true);

    }

}
