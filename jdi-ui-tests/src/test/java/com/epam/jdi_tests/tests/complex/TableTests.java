package com.epam.jdi_tests.tests.complex;

import com.epam.jdi_tests.InitTests;
import com.ggasoftware.jdi_ui_tests.core.utils.common.Timer;
import com.ggasoftware.jdi_ui_tests.core.utils.map.MapArray;
import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.complex.table.interfaces.ITable;
import com.ggasoftware.jdi_ui_tests.implementation.testng.asserter.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.lang.reflect.Method;

import static com.epam.jdi_tests.enums.Preconditions.SUPPORT_PAGE;
import static com.epam.jdi_tests.page_objects.EpamJDISite.isInState;
import static com.epam.jdi_tests.page_objects.EpamJDISite.supportPage;
import static com.ggasoftware.jdi_ui_tests.core.settings.JDISettings.logger;

/**
 * Created by Roman_Iovlev on 9/15/2015.
 */
public class TableTests extends InitTests {
    private ITable support() {
        return supportPage.supportTable;
    }

    @BeforeMethod
    public void before(Method method) throws IOException {
        isInState(SUPPORT_PAGE, method);
    }

    @Test
    public void getRowsCacheTest() {
        Timer timer = new Timer();
        support().useCache();
        MapArray<String, MapArray<String, String>> rows = support().rows().getAsText();
        logger.info("[TIME]: " + timer.timePassedInMSec()+"");
        Assert.areEquals(rows,
                "Drivers:" +
                        "Type:Drivers, " +
                        "Now:Selenium Custom, " +
                        "Plans:JavaScript, Appium, WinAPI, Sikuli, " +
                        "Test Runner:" +
                        "Type:Test Runner, " +
                        "Now:TestNG, JUnit Custom, " +
                        "Plans:MSTest, NUnit, Epam, " +
                        "Asserter:" +
                        "Type:Asserter, " +
                        "Now:TestNG, JUnit, Custom, " +
                        "Plans:MSTest, NUnit, Epam, " +
                        "Logger:" +
                        "Type:Logger, " +
                        "Now:Log4J, TestNG log, Custom, " +
                        "Plans:Epam, XML/Json logging, Hyper logging, " +
                        "Reporter:" +
                        "Type:Reporter, " +
                        "Now:Jenkins, Allure, Custom, " +
                        "Plans:EPAM Report portal, Serenity, TimCity, Hudson, " +
                        "BDD/DSL:" +
                        "Type:BDD/DSL, " +
                        "Now:Custom, " +
                        "Plans:Cucumber, Jbehave, Thucydides, SpecFlow");
    }
    @Test
    public void getRowsTest() {
        Timer timer = new Timer();
        MapArray<String, MapArray<String, String>> rows = support().rows().getAsText();
        logger.info("[TIME]: " + timer.timePassedInMSec()+"");
        Assert.areEquals(rows,
                "Drivers:" +
                        "Type:Drivers, " +
                        "Now:Selenium Custom, " +
                        "Plans:JavaScript, Appium, WinAPI, Sikuli, " +
                "Test Runner:" +
                        "Type:Test Runner, " +
                        "Now:TestNG, JUnit Custom, " +
                        "Plans:MSTest, NUnit, Epam, " +
                "Asserter:" +
                        "Type:Asserter, " +
                        "Now:TestNG, JUnit, Custom, " +
                        "Plans:MSTest, NUnit, Epam, " +
                "Logger:" +
                        "Type:Logger, " +
                        "Now:Log4J, TestNG log, Custom, " +
                        "Plans:Epam, XML/Json logging, Hyper logging, " +
                "Reporter:" +
                        "Type:Reporter, " +
                        "Now:Jenkins, Allure, Custom, " +
                        "Plans:EPAM Report portal, Serenity, TimCity, Hudson, " +
                "BDD/DSL:" +
                        "Type:BDD/DSL, " +
                        "Now:Custom, " +
                        "Plans:Cucumber, Jbehave, Thucydides, SpecFlow");
    }
    /*
    @Test
    public void getColumnsTest() {
        MapArray<String, MapArray<String, String>> columns = support().columns().getAsText();
        Assert.areEquals(columns,
                "Type:" +
                        "Drivers:Drivers, " +
                        "Test Runner:Test Runner, " +
                        "Asserter:Asserter, " +
                        "Logger:Logger, " +
                        "Reporter:Reporter, " +
                        "BDD/DSL:BDD/DSL, " +
                "Now:" +
                        "Drivers:Selenium Custom, " +
                        "Test Runner:TestNG, JUnit Custom, " +
                        "Asserter:TestNG, JUnit, Custom, " +
                        "Logger:Log4J, TestNG log, Custom, " +
                        "Reporter:Jenkins, Allure, Custom, " +
                        "BDD/DSL:Custom, " +
                "Plans:" +
                        "Drivers:JavaScript, Appium, WinAPI, Sikuli, " +
                        "Test Runner:MSTest, NUnit, Epam, " +
                        "Asserter:MSTest, NUnit, Epam, " +
                        "Logger:Epam, XML/Json logging, Hyper logging, " +
                        "Reporter:EPAM Report portal, Serenity, TimCity, Hudson, " +
                        "BDD/DSL:Cucumber, Jbehave, Thucydides, SpecFlow");
    }*/

}
