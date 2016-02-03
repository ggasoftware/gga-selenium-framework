package com.epam.cucmber.stepdefs;

import com.epam.jdi.uitests.web.selenium.elements.composite.Site;
import com.epam.jdi.uitests.web.selenium.elements.composite.WebPage;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

import static com.epam.cucmber.stepdefs.Utils.getClassField;

/**
 * Created by Dmitry_Lebedev1 on 1/18/2016.
 */
public class PageFrameworkStepdefs {
    @Given("^I'm open \"(.*?)\"$")
    public void i_m_open(String pageName) {
        ((WebPage) Utils.getClassField(Site.currentSite, pageName)).open();
    }

    @Then("^I'm on \"(.*?)\"$")
    public void i_m_on(String pageName) throws Throwable {
        ((WebPage) Utils.getClassField(Site.currentSite, pageName)).checkOpened();
    }

    @And("^I'm refresh page$")
    public void iMRefreshPage() throws Throwable {
        WebPage.currentPage.refresh();
    }

    @And("^I'm go back$")
    public void iMGoBack() throws Throwable {
        WebPage.currentPage.back();
    }

    @And("^I'm go forward$")
    public void iMGoForward() throws Throwable {
        WebPage.currentPage.forward();
    }

    @And("^Check page url match$")
    public void checkPageUrlMatch() throws Throwable {
        WebPage.currentPage.url().match();
    }

    @And("^Check page url contains$")
    public void checkPageUrlContains() throws Throwable {
        WebPage.currentPage.url().contains();
    }
}
