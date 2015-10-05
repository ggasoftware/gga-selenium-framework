package com.epam.jdi_tests.tests.complex.tableTests;

import com.epam.jdi_tests.InitTests;
import com.ggasoftware.jdi_ui_tests.core.utils.common.Timer;
import com.ggasoftware.jdi_ui_tests.core.utils.map.MapArray;
import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.complex.table.Rows;
import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.complex.table.interfaces.ICell;
import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.complex.table.interfaces.ITable;
import com.ggasoftware.jdi_ui_tests.implementation.testng.asserter.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;

import static com.epam.jdi_tests.enums.Preconditions.SUPPORT_PAGE;
import static com.epam.jdi_tests.page_objects.EpamJDISite.isInState;
import static com.epam.jdi_tests.page_objects.EpamJDISite.supportPage;
import static com.ggasoftware.jdi_ui_tests.core.settings.JDISettings.logger;

/**
 * Created by Natalia_Grebenshikova on 10/05/2015.
 */
public class RowTests extends InitTableTests {

    @Test
    public void findAllRows(){
        Rows rows = support().rows();
        Assert.areEquals(rows.count(), 6, String.format("Expectde to find 6 rows, but was found %d", rows.count()));
    }
    @Test
    public void findAllRowsWithRepeatableWord(){
        MapArray<String, MapArray<String, ICell>> rows = support().rows("Now=Custom");

        Assert.areEquals(rows.size(),1,String.format("Number of found element expectde to 1, but was %d", rows.size()));
        Assert.areEquals(rows.value(0).value(0).columnNum(),2,"Wrong element`s position, expected 2, but was "+rows.value(0).value(0).columnNum());
        Assert.areEquals(rows.value(0).value(0).rowNum(),6,"Wrong element`s position, expected 6, but was "+rows.value(0).value(0).rowNum());
    }
    @Test
    public void findAllRowsWithSameValue(){
        MapArray<String, MapArray<String, ICell>> rows = support().rows("Plans=MSTest, NUnit, Epam");

        Assert.areEquals(rows.size(),2,String.format("Number of found element expectde to 2, but was %d", rows.size()));

        Assert.areEquals(rows.value(0).value(0).columnNum(),3,"Wrong element`s position, expected 3, but was "+rows.value(0).value(0).columnNum());
        Assert.areEquals(rows.value(0).value(0).rowNum(),2,"Wrong element`s position, expected 2, but was "+rows.value(0).value(0).rowNum());

        Assert.areEquals(rows.value(1).value(0).columnNum(),3,"Wrong element`s position, expected 3, but was "+rows.value(0).value(0).columnNum());
        Assert.areEquals(rows.value(1).value(0).rowNum(),3,"Wrong element`s position, expected 3, but was "+rows.value(0).value(0).rowNum());
    }

    @Test
    public void getRowsCacheTest() {
        List<ICell> cells = support().getCells();
        Timer timer = new Timer();
        support().useCache();
        MapArray<String, MapArray<String, String>> rows = support().rows().getAsText();
        logger.info("[TIME]: " + timer.timePassedInMSec()+"");
        Assert.areEquals(rows,
                "1:" +
                        "Type:Drivers, " +
                        "Now:Selenium Custom, " +
                        "Plans:JavaScript, Appium, WinAPI, Sikuli, " +
                        "2:" +
                        "Type:Test Runner, " +
                        "Now:TestNG, JUnit Custom, " +
                        "Plans:MSTest, NUnit, Epam, " +
                        "3:" +
                        "Type:Asserter, " +
                        "Now:TestNG, JUnit, Custom, " +
                        "Plans:MSTest, NUnit, Epam, " +
                        "4:" +
                        "Type:Logger, " +
                        "Now:Log4J, TestNG log, Custom, " +
                        "Plans:Epam, XML/Json logging, Hyper logging, " +
                        "5:" +
                        "Type:Reporter, " +
                        "Now:Jenkins, Allure, Custom, " +
                        "Plans:EPAM Report portal, Serenity, TimCity, Hudson, " +
                        "6:" +
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
                "1:" +
                        "Type:Drivers, " +
                        "Now:Selenium Custom, " +
                        "Plans:JavaScript, Appium, WinAPI, Sikuli, " +
                        "2:" +
                        "Type:Test Runner, " +
                        "Now:TestNG, JUnit Custom, " +
                        "Plans:MSTest, NUnit, Epam, " +
                        "3:" +
                        "Type:Asserter, " +
                        "Now:TestNG, JUnit, Custom, " +
                        "Plans:MSTest, NUnit, Epam, " +
                        "4:" +
                        "Type:Logger, " +
                        "Now:Log4J, TestNG log, Custom, " +
                        "Plans:Epam, XML/Json logging, Hyper logging, " +
                        "5:" +
                        "Type:Reporter, " +
                        "Now:Jenkins, Allure, Custom, " +
                        "Plans:EPAM Report portal, Serenity, TimCity, Hudson, " +
                        "6:" +
                        "Type:BDD/DSL, " +
                        "Now:Custom, " +
                        "Plans:Cucumber, Jbehave, Thucydides, SpecFlow");
    }

}
