package com.epam.jdi_tests.tests.complex.tableTests;

import com.epam.jdi_tests.InitTests;
import com.epam.jdi_tests.dataproviders.TableDP;
import com.ggasoftware.jdi_ui_tests.core.utils.map.MapArray;
import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.complex.table.interfaces.ITable;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;

import static com.epam.jdi_tests.enums.Preconditions.SUPPORT_PAGE;
import static com.epam.jdi_tests.page_objects.EpamJDISite.isInState;
import static com.epam.jdi_tests.page_objects.EpamJDISite.supportPage;
import static com.ggasoftware.jdi_ui_tests.core.utils.common.LinqUtils.toStringArray;
import static com.ggasoftware.jdi_ui_tests.implementation.testng.asserter.Assert.areEquals;
import static com.ggasoftware.jdi_ui_tests.implementation.testng.asserter.Assert.arrayEquals;

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

    @Test(dataProvider = "hasHeadersSelector", dataProviderClass = TableDP.class)
    public void verifyHasHeadersVariables(ITable table, int rowsCount, int columnsCount,
                                            List<String> rowsHeaders, List<String> columnHeaders){
        arrayEquals(table.columns().headers(), toStringArray(columnHeaders));
        arrayEquals(table.rows().headers(), toStringArray(rowsHeaders));
        areEquals(table.columns().count(), columnsCount);
        areEquals(table.rows().count(), rowsCount);
    }

    @Test
    public void getRowsTest() {
        MapArray<String, MapArray<String, String>> rows = support().rows().getAsText();
        areEquals(rows,
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
    public void getColumnsTest() {
        MapArray<String, MapArray<String, String>> columns = support().columns().getAsText();
        areEquals(columns,
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
