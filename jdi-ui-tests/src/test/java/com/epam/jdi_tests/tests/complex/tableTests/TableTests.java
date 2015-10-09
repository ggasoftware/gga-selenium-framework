package com.epam.jdi_tests.tests.complex.tableTests;

import com.epam.jdi_tests.dataproviders.TableDP;
import com.epam.jdi_tests.page_objects.pages.SupportPage;
import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.complex.table.Table;
import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.complex.table.interfaces.ITable;
import com.ggasoftware.jdi_ui_tests.implementation.testng.asserter.Assert;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.complex.table.Column.column;
import static com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.complex.table.Row.row;
/**
 * Created by Roman_Iovlev on 9/15/2015.
 */
public class TableTests extends InitTableTests {

    @Test(dataProvider = "hasHeadersSelector", dataProviderClass = TableDP.class)
    public void verifyHasHeadersVariablesByColumnCount(  boolean hasRowHeaders, boolean hasColumnHeaders,
                                            int rowsCount, int columnsCount,
                                            List<String> rowsHeaders, List<String> columnHeaders,
                                            String firstColumnContent){

        ITable supportTable = new SupportPage().supportTable;

        supportTable.rows().hasHeader = hasRowHeaders;
        supportTable.columns().hasHeader = hasColumnHeaders;

        Assert.isTrue((supportTable.columns().count()== columnsCount && supportTable.rows().count()==rowsCount),
                String.format("Expected column/row count is %d/%d, but was %d/%d", columnsCount,rowsCount, supportTable.columns().count(), supportTable.rows().count()));
    }
    @Test(dataProvider = "hasHeadersSelector", dataProviderClass = TableDP.class)
    public void verifyHasHeadersVariablesByHeaders(  boolean hasRowHeaders, boolean hasColumnHeaders,
                                            int rowsCount, int columnsCount,
                                            List<String> rowsHeaders, List<String> columnHeaders,
                                            String firstColumnContent){

        ITable supportTable = new SupportPage().supportTable;

        supportTable.rows().hasHeader = hasRowHeaders;
        supportTable.columns().hasHeader = hasColumnHeaders;

        Assert.isTrue((Arrays.asList(supportTable.columns().headers()).equals(columnHeaders) && Arrays.asList(supportTable.rows().headers()).equals(rowsHeaders)),
                String.format("Expected column/row headers is \n%s\n%s, \nbut was \n%s\n%s", columnHeaders,rowsHeaders,
                        Arrays.asList(supportTable.columns().headers()), Arrays.asList(supportTable.rows().headers())));
    }
   @Test(dataProvider = "hasHeadersSelector", dataProviderClass = TableDP.class)
    public void verifyHasHeadersVariablesByFirstColumn(  boolean hasRowHeaders, boolean hasColumnHeaders,
                                            int rowsCount, int columnsCount,
                                            List<String> rowsHeaders, List<String> columnHeaders,
                                            String firstColumnContent){

        ITable supportTable = new SupportPage().supportTable;

        supportTable.rows().hasHeader = hasRowHeaders;
        supportTable.columns().hasHeader = hasColumnHeaders;

        Assert.areEquals(supportTable.cell(column(1), row(1)).getText(),firstColumnContent,
                String.format("Expected first column is '%s', but was '%s'", firstColumnContent, supportTable.cell(column(1), row(1)).getText()));
    }


    @Test(dataProvider = "setColumnsCount", dataProviderClass = TableDP.class)
    public void verifySetColumnsCountByColumnsCount(int providedColumnCount,int expNewColumnCount,
                                               List<String> expNewColumnsHeaders, String firstColumnContent){
        Table supportTable = (Table)new SupportPage().supportTable;

        supportTable.setColCount(providedColumnCount);

        Assert.areEquals(supportTable.columns().count(),expNewColumnCount,
                String.format("Expected column count is %d, but was %d",expNewColumnCount,supportTable.columns().count()));

    }
    @Test(dataProvider = "setColumnsCount", dataProviderClass = TableDP.class)
    public void verifySetColumnsCountByColumnsHeader(int providedColumnCount,int expNewColumnCount,
                                               List<String> expNewColumnsHeaders, String firstColumnContent){
        Table supportTable = (Table)new SupportPage().supportTable;

        supportTable.setColCount(providedColumnCount);

        Assert.areEquals(Arrays.asList(supportTable.columns().headers()),expNewColumnsHeaders,
                String.format("Expected columns headers are \n%s, \nbut was \n%s", expNewColumnsHeaders, Arrays.asList(supportTable.columns().headers())));

    }
    @Test(dataProvider = "setColumnsCount", dataProviderClass = TableDP.class)
    public void verifySetColumnsCountByFirstColumnContent(int providedColumnCount,int expNewColumnCount,
                                               List<String> expNewColumnsHeaders, String firstColumnContent){
        Table supportTable = (Table)new SupportPage().supportTable;

        supportTable.setColCount(providedColumnCount);

        Assert.areEquals(supportTable.cell(column(1), row(1)).getText(),firstColumnContent,
                String.format("Expected first column is '%s', but was '%s'", firstColumnContent, supportTable.cell(column(1), row(1)).getText()));

    }

    @Test(dataProvider = "setRowsCount", dataProviderClass = TableDP.class)
    public void verifySetRowsCountByColumnsCount(int providedRowsCount,int expNewRowsCount,
                                               List<String> expNewRowsHeaders, String firstColumnContent){
        Table supportTable = (Table)new SupportPage().supportTable;

        supportTable.setRowCount(providedRowsCount);

        Assert.areEquals(supportTable.rows().count(),expNewRowsCount,
                String.format("Expected rows count is %d, but was %d",expNewRowsCount,supportTable.rows().count()));

    }
    @Test(dataProvider = "setRowsCount", dataProviderClass = TableDP.class)
    public void verifySetRowsCountByRowHeaders(int providedRowsCount,int expNewRowsCount,
                                               List<String> expNewRowsHeaders, String firstColumnContent){
        Table supportTable = (Table)new SupportPage().supportTable;

        supportTable.setRowCount(providedRowsCount);

        Assert.areEquals(Arrays.asList(supportTable.rows().headers()),expNewRowsHeaders,
                String.format("Expected rows headers are \n%s, \nbut were \n%s",expNewRowsHeaders,Arrays.asList(supportTable.rows().headers())));

    }
    @Test(dataProvider = "setColumnsCount", dataProviderClass = TableDP.class)
    public void verifySetRowsCountByFirstColumnContent(int providedColumnCount,int expNewColumnCount,
                                                          List<String> expNewColumnsHeaders, String firstColumnContent){
        Table supportTable = (Table)new SupportPage().supportTable;

        supportTable.setColCount(providedColumnCount);

        Assert.areEquals(supportTable.cell(column(1), row(1)).getText(),firstColumnContent,
                String.format("Expected first column is '%s', but was '%s'", firstColumnContent, supportTable.cell(column(1), row(1)).getText()));

    }


    @Test(dataProvider = "setColumnHeaders", dataProviderClass = TableDP.class)
    public void verifySetColumnHeadersByColumnCount (List<String> providedColumnList, String firstColumnContent){
        Table supportTable = (Table)new SupportPage().supportTable;

        supportTable.setColumnHeaders((String [])providedColumnList.toArray());

        Assert.areEquals(supportTable.columns().count(),providedColumnList.size(),
                String.format("Expected column count is %d, but was %d",providedColumnList.size(),supportTable.columns().count()));
    }

}
