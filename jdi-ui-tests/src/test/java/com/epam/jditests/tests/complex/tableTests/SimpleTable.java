package com.epam.jditests.tests.complex.tableTests;

import com.epam.jditests.InitTests;
import com.ggasoftware.jdiuitests.core.utils.map.MapArray;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.complex.table.Table;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.complex.table.TableSettings;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.complex.table.interfaces.ICell;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import static com.epam.jditests.enums.Preconditions.NO_HEADERS_TABLE_PAGE;
import static com.epam.jditests.pageobjects.EpamJDISite.isInState;
import static com.ggasoftware.jdiuitests.core.utils.common.LinqUtils.foreach;
import static com.ggasoftware.jdiuitests.implementation.selenium.elements.complex.table.Column.column;
import static com.ggasoftware.jdiuitests.implementation.selenium.elements.complex.table.Row.row;
import static com.ggasoftware.jdiuitests.implementation.testng.asserter.Assert.areEquals;
import static com.ggasoftware.jdiuitests.implementation.testng.asserter.Assert.isTrue;
import static java.lang.String.format;
import static java.util.Arrays.asList;

/**
 * Created by Natalia_Grebenshchik on 10/21/2015.
 */
public class SimpleTable extends InitTests {

    @BeforeMethod
    protected void before(Method method) throws IOException {
        isInState(NO_HEADERS_TABLE_PAGE);
    }

    @Test(enabled = false)
    public void testClickOnCell() {
       /* ICell cell = sorting().cell(column(2), row(2));

        cell.click();

        areEquals(actionsLog.getText(0), "TestNG, JUnit Custom", String.format("Expected log after cell click is %s, but was %s"));
        isTrue(cell.isSelected(), "Clicked cell is not selected");*/
    }

    @Test(enabled = false)
    public void verifyTableWithRowAndColumnHeaders() {

        List<String> expColumnHeaders = Arrays.asList("Selenium Custom", "JavaScript, Appium, WinAPI, Sikuli"),
                expRowHeaders = Arrays.asList("Test Runner", "Asserter", "Logger", "Reporter", "BDD/DSL");

        Table table = new Table(By.xpath("./tbody/tr[1]/td"),
                By.xpath("./tbody/tr[position() > 1]/td[1]"),
                By.xpath("./tbody/tr"),
                By.xpath("./tbody/tr/td"), null,
                new TableSettings(true, true),2,2);

        table.avatar.byLocator = By.xpath("*//table");

        isTrue((table.columns().count() == 2 && table.rows().count() == 5),
                format("Expected column/row count is %d/%d, but was %d/%d", 2, 5, table.columns().count(), table.rows().count()));

        areEquals(asList(table.columns().headers()), expColumnHeaders,
                format("Expected columns header list is \n%s \nbut was \n%s", expColumnHeaders, asList(table.columns().headers())));
        areEquals(asList(table.rows().headers()), expRowHeaders,
                format("Expected row header list is \n%s \nbut was \n%s", expRowHeaders, asList(table.rows().headers())));

        areEquals(table.cell(column(1), row(1)).getText(), "TestNG, JUnit Custom",
                format("Expected first cell content is '%s', but was '%s'", "TestNG, JUnit Custom", table.cell(column(1), row(1)).getText()));

    }

    @Test(enabled = false)
    public void verifyTableWithRowHeaders() {

        List<String> expColumnHeaders = Arrays.asList("1", "2"),
                expRowHeaders = Arrays.asList("Drivers", "Test Runner", "Asserter", "Logger", "Reporter", "BDD/DSL");
        String firstColumnContent = "Selenium Custom";

       Table table = new Table(By.xpath("./tbody/tr[1]/td"),
               By.xpath("./tbody/tr[position() > 0]/td[1]"),
               By.xpath("./tbody/tr"),
               By.xpath("./tbody/tr/td"), null,
               new TableSettings(false, true),2,1);

        table.avatar.byLocator = By.xpath("*//table");

        isTrue((table.columns().count() == 2 && table.rows().count() == 6),
                format("Expected column/row count is %d/%d, but was %d/%d", 2, 6, table.columns().count(), table.rows().count()));

        areEquals(asList(table.columns().headers()), expColumnHeaders,
                format("Expected columns header list is \n%s \nbut was \n%s", expColumnHeaders, asList(table.columns().headers())));
        areEquals(asList(table.rows().headers()), expRowHeaders,
                format("Expected row header list is \n%s \nbut was \n%s", expRowHeaders, asList(table.rows().headers())));

        areEquals(table.cell(column(1), row(1)).getText(), firstColumnContent,
                format("Expected first cell content is '%s', but was '%s'", firstColumnContent, table.cell(column(1), row(1)).getText()));

    }

    @Test(enabled = false)
    public void verifyTableWithColumnHeaders() {

        List<String> expColumnHeaders = Arrays.asList("Drivers", "Selenium Custom", "JavaScript, Appium, WinAPI, Sikuli"),
                expRowHeaders = Arrays.asList("1", "2", "3", "4", "5");
        String firstColumnContent = "Test Runner";

        Table table = new Table(By.xpath("./tbody/tr[1]/td"),
                By.xpath("./tbody/tr[position() > 1]/td[1]"),
                By.xpath("./tbody/tr"),
                By.xpath("./tbody/tr/td"), null,
                new TableSettings(true, false),1,2);

        table.avatar.byLocator = By.xpath("*//table");

        isTrue((table.columns().count() == 3 && table.rows().count() == 5),
                format("Expected column/row count is %d/%d, but was %d/%d", 3, 5, table.columns().count(), table.rows().count()));

        areEquals(asList(table.columns().headers()), expColumnHeaders,
                format("Expected columns header list is \n%s \nbut was \n%s", expColumnHeaders, asList(table.columns().headers())));
        areEquals(asList(table.rows().headers()), expRowHeaders,
                format("Expected row header list is \n%s \nbut was \n%s", expRowHeaders, asList(table.rows().headers())));

        areEquals(table.cell(column(1), row(1)).getText(), firstColumnContent,
                format("Expected first cell content is '%s', but was '%s'", firstColumnContent, table.cell(column(1), row(1)).getText()));

    }

    @Test(enabled = false)
    public void verifyTableWithNoHeaders() {

        List<String>    expColumnHeaders = Arrays.asList("1", "2", "3"),
                expRowHeaders = Arrays.asList("1", "2", "3", "4", "5", "6");
        String firstColumnContent = "Drivers";

        Table table = new Table(By.xpath("./tbody/tr[1]/td"),
                By.xpath("./tbody/tr[position() > 0]/td[1]"),
                By.xpath("./tbody/tr"),
                By.xpath("./tbody/tr/td"), null,
                new TableSettings(false, false),1,1);

        table.avatar.byLocator = By.xpath("*//table");

        isTrue((table.columns().count() == 3 && table.rows().count() == 6),
                format("Expected column/row count is %d/%d, but was %d/%d", 3, 6, table.columns().count(), table.rows().count()));

        areEquals(asList(table.columns().headers()), expColumnHeaders,
                format("Expected columns header list is \n%s \nbut was \n%s", expColumnHeaders, asList(table.columns().headers())));
        areEquals(asList(table.rows().headers()), expRowHeaders,
                format("Expected row header list is \n%s \nbut was \n%s", expRowHeaders, asList(table.rows().headers())));

        areEquals(table.cell(column(1), row(1)).getText(), firstColumnContent,
                format("Expected first cell content is '%s', but was '%s'", firstColumnContent, table.cell(column(1), row(1)).getText()));

    }

}
