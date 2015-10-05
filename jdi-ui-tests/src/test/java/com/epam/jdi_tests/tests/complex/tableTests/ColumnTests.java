package com.epam.jdi_tests.tests.complex.tableTests;

import com.epam.jdi_tests.InitTests;
import com.ggasoftware.jdi_ui_tests.core.utils.map.MapArray;
import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.complex.table.Columns;
import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.complex.table.interfaces.ITable;
import com.ggasoftware.jdi_ui_tests.implementation.testng.asserter.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.lang.reflect.Method;

import static com.epam.jdi_tests.enums.Preconditions.SUPPORT_PAGE;
import static com.epam.jdi_tests.page_objects.EpamJDISite.isInState;
import static com.epam.jdi_tests.page_objects.EpamJDISite.supportPage;

/**
 * Created by Natalia_Grebenshchikova on 10/5/2015.
 */
public class ColumnTests extends InitTableTests{

    @Test
    public void findAllColumns(){
        Columns columns = support().columns();
        Assert.areEquals(columns.count(), 3, String.format("Expected to find 3 columns, but was %d", columns.count()));
    }

    @Test
    public void getColumnsTest() {
        MapArray<String, MapArray<String, String>> columns = support().columns().getAsText();
        Assert.areEquals(columns,
                "Type:" +
                        "1:Drivers, " +
                        "2:Test Runner, " +
                        "3:Asserter, " +
                        "4:Logger, " +
                        "5:Reporter, " +
                        "6:BDD/DSL, " +
                        "Now:" +
                        "1:Selenium Custom, " +
                        "2:TestNG, JUnit Custom, " +
                        "3:TestNG, JUnit, Custom, " +
                        "4:Log4J, TestNG log, Custom, " +
                        "5:Jenkins, Allure, Custom, " +
                        "6:Custom, " +
                        "Plans:" +
                        "1:JavaScript, Appium, WinAPI, Sikuli, " +
                        "2:MSTest, NUnit, Epam, " +
                        "3:MSTest, NUnit, Epam, " +
                        "4:Epam, XML/Json logging, Hyper logging, " +
                        "5:EPAM Report portal, Serenity, TimCity, Hudson, " +
                        "6:Cucumber, Jbehave, Thucydides, SpecFlow");
    }
}
