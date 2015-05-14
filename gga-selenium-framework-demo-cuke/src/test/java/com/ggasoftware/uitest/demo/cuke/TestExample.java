package com.ggasoftware.uitest.demo.cuke;

import com.ggasoftware.uitest.demo.panel.TestPanel;
import com.ggasoftware.uitest.utils.ReporterNGExt;
import com.ggasoftware.uitest.utils.TestBaseWebDriver;
import com.ggasoftware.uitest.utils.WebDriverWrapper;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

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

    @Before
    public void setUp(Scenario scenario) throws MalformedURLException {
        System.setProperty("webdriver.chrome.driver", chromedriver);
        System.setProperty("webdriver.ie.driver", iedriver);
        setBrowserType(browser);
        initWebDriver();
        ReporterNGExt.logTechnical(String.format("    ===== Processing Test Pack %s =====", this.getClass().getSimpleName()));
        ReporterNGExt.logBusiness(String.format("Run Cucumber scenario: %s", scenario.getName()));
    }

    @Given("^Open default website$")
    public void Open_default_website() {
        WebDriverWrapper.open(website);
    }

    @After
    public void quit() {
        WebDriverWrapper.quit();
    }

    @Then("^I see default page$")
    public void i_see_default_page() {
        assertTrue(TestPanel.get().logo.isDisplayed(), "check logo", true);
        assertTrue(TestPanel.get().searchBtn.isDisplayed(), "check search button is visible");
    }

    @When("^I set text '([^\\\"]*)' at default page$")
    public void i_set_text_at_default_page(String value) {
        TestPanel.get().textField.setText(value);
    }

    @Then("^I did not see search button$")
    public void i_did_not_see_search_button() {
        assertFalse(TestPanel.get().searchBtn.isDisplayed(), "check search button is not visible");
    }

    @When("^I click '([^\\\"]*)' button$")
    public void i_click_search_button(String value) {
        switch (value) {
            case "Search":
                TestPanel.get().searchBtn.click();
                break;
            case "Search2":
                TestPanel.get().searchBtn2.click();
                break;
        }
    }

    @Then("^I see first link text '([^\\\"]*)' at results page$")
    public void i_see_first_link_text_at_results_page(String value) {
        assertEquals(TestPanel.get().resultsLinks.getWebElement(0).getText(), value, "check first link text");
    }

    @When("^I click at '([^\\\"]*)' results link$")
    public void i_click_at_results_link(int number) {
        TestPanel.get().resultsLinks.getWebElement(number-1).click();
    }

    @Then("^I see first link title '([^\\\"]*)' at new tab$")
    public void i_see_first_link_title_at_new_tab(String value) {
        WebDriverWrapper.waitWindowsCount(2);
        WebDriverWrapper.switchWindow();
        assertEquals(WebDriverWrapper.getDriver().getTitle(), value, "check first link title", true);
    }



}
