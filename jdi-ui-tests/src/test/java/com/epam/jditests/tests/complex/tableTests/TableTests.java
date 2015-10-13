package com.epam.jditests.tests.complex.tableTests;

import com.epam.jditests.dataproviders.TableDP;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.complex.table.Table;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.complex.table.TableSettings;
import com.ggasoftware.jdiuitests.implementation.testng.asserter.Assert;
import org.openqa.selenium.By;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;

import static com.epam.jditests.enums.Preconditions.SUPPORT_PAGE;
import static com.epam.jditests.page_objects.EpamJDISite.isInState;
import static com.ggasoftware.jdiuitests.core.utils.common.LinqUtils.toStringArray;
import static com.ggasoftware.jdiuitests.implementation.selenium.elements.complex.table.Column.column;
import static com.ggasoftware.jdiuitests.implementation.selenium.elements.complex.table.Row.row;
import static com.ggasoftware.jdiuitests.implementation.testng.asserter.Assert.areEquals;
import static java.lang.String.format;
import static java.util.Arrays.asList;

/**
 * Created by Roman_Iovlev on 9/15/2015.
 */
/**
 * Created by Roman_Iovlev on 9/15/2015.
 */

public class TableTests extends InitTableTests {
    private Table initTable(Table table) {
        table.avatar.byLocator = By.className("uui-table");
        return table;
    }
    @BeforeMethod
    public void before(Method method) throws IOException {
        isInState(SUPPORT_PAGE, method);
    }

    @Test(dataProvider = "hasHeadersSelector", dataProviderClass = TableDP.class)
    public void verifyHasHeadersVariablesByColumnCount(  boolean hasColumnHeaders, boolean hasRowHeaders,
                                                         int columnsCount, int rowsCount,
                                                         List<String> columnHeaders, List<String> rowsHeaders,
                                                         String firstColumnContent){
        Table table = initTable(new Table(new TableSettings(hasColumnHeaders, hasRowHeaders)));

        Assert.isTrue((table.columns().count() == columnsCount && table.rows().count() == rowsCount),
                format("Expected column/row count is %d/%d, but was %d/%d", columnsCount, rowsCount, table.columns().count(), table.rows().count()));
    }
    @Test(dataProvider = "hasHeadersSelector", dataProviderClass = TableDP.class)
    public void verifyHasHeadersVariablesByHeaders(  boolean hasColumnHeaders, boolean hasRowHeaders,
                                                     int columnsCount, int rowsCount,
                                                     List<String> columnHeaders, List<String> rowsHeaders,
                                                     String firstColumnContent){

        Table table = initTable(new Table(new TableSettings(hasColumnHeaders, hasRowHeaders)));

        Assert.isTrue((asList(table.columns().headers()).equals(columnHeaders) && asList(table.rows().headers()).equals(rowsHeaders)),
                format("Expected column/row headers is \n%s\n%s, \nbut was \n%s\n%s", columnHeaders, rowsHeaders,
                        asList(table.columns().headers()), asList(table.rows().headers())));
    }
    @Test(dataProvider = "hasHeadersSelector", dataProviderClass = TableDP.class)
    public void verifyHasHeadersVariablesByFirstCell( boolean hasColumnHeaders, boolean hasRowHeaders,
                                                      int columnsCount, int rowsCount,
                                                      List<String> columnHeaders, List<String> rowsHeaders,
                                                      String firstColumnContent){

        Table table = initTable(new Table(new TableSettings(hasColumnHeaders, hasRowHeaders)));

        Assert.areEquals(table.cell(column(1), row(1)).getText(),firstColumnContent,
                format("Expected first column is '%s', but was '%s'", firstColumnContent, table.cell(column(1), row(1)).getText()));
    }


    @Test(dataProvider = "setColumnsCount", dataProviderClass = TableDP.class)
    public void verifySetColumnsCountByColumnsCount(int newColumnCount,List<String> expNewColumnsHeaders, String firstColumnContent){
        Table table = initTable(new Table(new TableSettings(newColumnCount, 6)));

        areEquals(table.columns().count(), newColumnCount,
                format("Expected column count is %d, but was %d", newColumnCount, table.columns().count()));
    }
    @Test(dataProvider = "setColumnsCount", dataProviderClass = TableDP.class)
    public void verifySetColumnsCountByColumnsHeader(int newColumnCount,List<String> expNewColumnsHeaders, String firstColumnContent){
        Table table = initTable(new Table(new TableSettings(newColumnCount, 6)));

        areEquals(asList(table.columns().headers()), expNewColumnsHeaders,
                format("Expected columns headers are \n%s, \nbut was \n%s", expNewColumnsHeaders, asList(table.columns().headers())));

    }
    @Test(dataProvider = "setColumnsCount", dataProviderClass = TableDP.class)
    public void verifySetColumnsCountByFirstCellContent(int newColumnCount,List<String> expNewColumnsHeaders, String firstColumnContent){
        Table table = initTable(new Table(new TableSettings(newColumnCount, 6)));

        Assert.areEquals(table.cell(column(1), row(1)).getText(),firstColumnContent,
                format("Expected first column is '%s', but was '%s'", firstColumnContent, table.cell(column(1), row(1)).getText()));
    }

    @Test(dataProvider = "setRowsCount", dataProviderClass = TableDP.class)
    public void verifySetRowsCountByRowCount(int newRowCount, List<String> expNewRowsHeaders, String firstColumnContent){
        Table table = initTable(new Table(new TableSettings(3, newRowCount)));

        Assert.areEquals(table.rows().count(),newRowCount,
                format("Expected rows count is %d, but was %d", newRowCount, table.rows().count()));

    }
    @Test(dataProvider = "setRowsCount", dataProviderClass = TableDP.class)
    public void verifySetRowsCountByRowHeaders(int newRowCount, List<String> expNewRowsHeaders, String firstColumnContent){
        Table table = initTable(new Table(new TableSettings(3, newRowCount)));

        areEquals(asList(table.rows().headers()), expNewRowsHeaders,
                format("Expected rows headers are \n%s, \nbut were \n%s", expNewRowsHeaders, asList(table.rows().headers())));

    }
    @Test(dataProvider = "setColumnsCount", dataProviderClass = TableDP.class)
    public void verifySetRowsCountByFirstCellContent(int newRowCount, List<String> expNewRowsHeaders, String firstColumnContent){
        Table table = initTable(new Table(new TableSettings(3, newRowCount)));

        Assert.areEquals(table.cell(column(1), row(1)).getText(),firstColumnContent,
                format("Expected first column is '%s', but was '%s'", firstColumnContent, table.cell(column(1), row(1)).getText()));

    }


    @Test(dataProvider = "setColumnHeaders", dataProviderClass = TableDP.class)
    public void verifySetColumnHeadersByColumnRowCount (List<String> providedColumnList, List<String> providedRowList, String firstColumnContent){
        Table table = initTable(new Table(new TableSettings(toStringArray(providedColumnList),toStringArray(providedRowList))));

        areEquals(table.columns().count(), providedColumnList.size(),format("Expected column count is %d, but was %d", providedColumnList.size(), table.columns().count()));
        areEquals(table.rows().count(), providedRowList.size(),format("Expected row count is %d, but was %d", providedRowList.size(), table.rows().count()));
    }
    @Test(dataProvider = "setColumnHeaders", dataProviderClass = TableDP.class)
    public void verifySetColumnHeadersByHeaders (List<String> providedColumnList, List<String> providedRowList, String firstColumnContent){
        Table table = initTable(new Table(new TableSettings(toStringArray(providedColumnList),toStringArray(providedRowList))));

        areEquals(asList(table.columns().headers()),providedColumnList,format("Expected column list is \n%s, \nbut was \n%s", providedColumnList, asList(table.columns().headers())));
        areEquals(asList(table.rows().headers()), providedRowList, format("Expected row list is \n%s, \nbut was \n%s", providedRowList, asList(table.rows().headers())));
    }
    @Test(dataProvider = "setColumnHeaders", dataProviderClass = TableDP.class)
    public void verifySetColumnHeadersByFirstCellContent (List<String> providedColumnList, List<String> providedRowList, String firstColumnContent){
        Table table = initTable(new Table(new TableSettings(toStringArray(providedColumnList),toStringArray(providedRowList))));

        areEquals(table.cell(column(providedColumnList.get(0)), row(providedRowList.get(0))).getText(), firstColumnContent,
                format("Expected first column ocntent is %s, but was %s", firstColumnContent, table.cell(column(providedColumnList.get(0)), row(providedRowList.get(0))).getText()));
    }
}
