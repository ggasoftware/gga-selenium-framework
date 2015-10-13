package com.epam.jditests.tests.complex.tableTests;

import com.ggasoftware.jdiuitests.core.utils.map.MapArray;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.complex.table.Columns;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.complex.table.interfaces.ICell;
import com.ggasoftware.jdiuitests.implementation.testng.asserter.Assert;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static com.ggasoftware.jdiuitests.implementation.selenium.elements.complex.table.Column.column;
import static com.ggasoftware.jdiuitests.implementation.selenium.elements.complex.table.Row.row;
/**
 * Created by Natalia_Grebenshchikova on 10/5/2015.
 */
public class ColumnTests extends InitTableTests {
    @Test
    public void findAllColumns(){
        Columns columns = support().columns();
        Assert.areEquals(columns.count(), 3, String.format("Expected to find 3 columns, but was %d", columns.count()));
    }

    @Test
    public void getCellsColumnByColumnNumber(){
        List<String> expectedColumnsValue = Arrays.asList("Selenium Custom", "TestNG, JUnit Custom", "TestNG, JUnit, Custom", "Log4J, TestNG log, Custom", "Jenkins, Allure, Custom", "Custom");

        MapArray<String, ICell> cellColumns = support().column(2);

        Assert.areEquals(cellColumns.count(), 6, String.format("Expected number of cell expected to be 6, but was ", cellColumns.count()));

        for (int i=0; i<6; i++)
            Assert.areEquals(cellColumns.value(i).getValue(),
                    expectedColumnsValue.get(i),
                    String.format("Expected content for row %d is '%s', but was %s", i + 1, expectedColumnsValue.get(i), cellColumns.value(i).getValue()));

    }
    @Test
    public void getCellsColumnByColumnName(){
        List<String> expectedColumnsValue = Arrays.asList("Selenium Custom", "TestNG, JUnit Custom", "TestNG, JUnit, Custom", "Log4J, TestNG log, Custom", "Jenkins, Allure, Custom", "Custom");

        MapArray<String, ICell> cellColumns = support().column("Now");

        Assert.areEquals(cellColumns.count(), 6, String.format("Nnumber of cell expected to be 6, but was ", cellColumns.count()));

        for (int i=0; i<6; i++)
            Assert.areEquals(cellColumns.value(i).getValue(),
                    expectedColumnsValue.get(i),
                    String.format("Expected content for row %d is '%s', but was %s", i + 1, expectedColumnsValue.get(i), cellColumns.value(i).getValue()));

    }

    @Test
    public void verifyColumnsCount(){
        int actualColNumber = support().columns().count();

        Assert.areEquals(actualColNumber, 3, String.format("Expected number of columns 3, but was %d", actualColNumber));
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

    @Test
    public void getColumnByRowAndValue(){
        MapArray<String, ICell> column = support().column("Logger", row(4));

        Assert.areEquals("Type", column.get(0).value.columnName(), String.format("Expected column name is 'Type', but was '%s'", column.get(0).value.columnName()));
    }

    @Test
    public void getColumnAsTest(){

        String expectedColumnValue = "1:Selenium Custom, 2:TestNG, JUnit Custom, 3:TestNG, JUnit, Custom, 4:Log4J, TestNG log, Custom, 5:Jenkins, Allure, Custom, 6:Custom";
        MapArray<String, String> columnAsText = support().rows().getColumnAsText(2);

        Assert.areEquals(columnAsText, expectedColumnValue, String.format("Expectde column is %s, but was %s", expectedColumnValue, columnAsText));
    }
    @Test
    public void cellsToColumn(){
        MapArray<String, ICell> cellsToColumn = support().rows().cellsToColumn(Arrays.asList(support().cell(column(1), row(1)),support().cell(column(2), row(2))));

        Assert.areEquals(cellsToColumn.key(0),"1",String.format("Expected first cell row id '1', but was %s", cellsToColumn.key(0)));
        Assert.areEquals(cellsToColumn.value(0).getValue(),"Drivers",String.format("Expected first cell value id 'Drivers', but was %s", cellsToColumn.value(0).getValue()));
        Assert.areEquals(cellsToColumn.key(1), "2",String.format("Expected second cell row id '2', but was %s", cellsToColumn.key(1)));
        Assert.areEquals(cellsToColumn.value(1).getValue(),"TestNG, JUnit Custom",String.format("Expected first cell value id 'TestNG, JUnit Custom', but was %s", cellsToColumn.value(1).getValue()));
    }

}
