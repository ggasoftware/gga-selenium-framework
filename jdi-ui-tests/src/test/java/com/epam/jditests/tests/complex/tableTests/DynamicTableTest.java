package com.epam.jditests.tests.complex.tableTests;

import com.epam.jditests.InitTests;
import com.epam.jditests.dataproviders.TableDP;
import com.ggasoftware.jdiuitests.core.utils.map.MapArray;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.base.SelectElement;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.complex.table.interfaces.ITable;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;

import static com.epam.jditests.enums.Preconditions.DYNAMIC_TABLE_PAGE;
import static com.epam.jditests.pageobjects.EpamJDISite.dynamicTablePage;
import static com.epam.jditests.pageobjects.EpamJDISite.isInState;
import static com.ggasoftware.jdiuitests.implementation.selenium.elements.complex.table.Column.column;
import static com.ggasoftware.jdiuitests.implementation.selenium.elements.complex.table.Row.row;
import static com.ggasoftware.jdiuitests.implementation.testng.asserter.Assert.areEquals;

/**
 * Created by Natalia_Grebenshchik on 10/16/2015.
 */
public class DynamicTableTest extends InitTests{

    protected ITable dynamic() {
        return dynamicTablePage.dynamicTable;
    }

    @BeforeMethod
    protected void before(Method method) throws IOException {
        isInState(DYNAMIC_TABLE_PAGE, method);
        dynamic().useCache();
    }

    @Test (enabled = false, dataProvider = "addColumn", dataProviderClass = TableDP.class)
    public void addNewColumn (int columnPosition, int expectedColumnCount, String[] headers, String firstCell){
        dynamicTablePage.selectColumnsDD.select(columnPosition);
        dynamicTablePage.selectColumnsBtn.click();

        areEquals(dynamic().columns().count(), expectedColumnCount,
                String.format("Expected column count is %d, but was %d", expectedColumnCount, dynamic().columns().count()));
        areEquals(dynamic().headers(), headers);
        areEquals(dynamic().cell(column(1),row(1)).getValue(), firstCell,
                String.format("Expected first cell content id %s, but was %s",firstCell, dynamic().cell(column(1),row(1)).getValue()));
    }

    @Test (enabled = false, dataProvider = "deleteColumn", dataProviderClass = TableDP.class)
    public void deleteColumn (int columnPosition, int expectedColumnCount, String[] headers, String firstCell){
        dynamicTablePage.selectColumnsDD.uncheck(columnPosition);
        dynamicTablePage.selectColumnsBtn.click();

        areEquals(dynamic().columns().count(), expectedColumnCount,
                String.format("Expected column count is %d, but was %d", expectedColumnCount, dynamic().columns().count()));
        areEquals(dynamic().headers(), headers);
        areEquals(dynamic().cell(column(1),row(1)).getValue(), firstCell,
                String.format("Expected first cell content id %s, but was %s",firstCell, dynamic().cell(column(1),row(1)).getValue()));
    }

    @Test (enabled = false, dataProvider = "addRow", dataProviderClass = TableDP.class)
    public void addNewRow (int rowPosition, int expectedRowCount, List<String> headers, String firstCell){
        dynamicTablePage.selectRowsDD.check(rowPosition);
        dynamicTablePage.selectRowsBtn.click();

        areEquals(dynamic().rows().count(), expectedRowCount,
                String.format("Expected column count is %d, but was %d", expectedRowCount, dynamic().rows().count()));
        areEquals(dynamic().rows().headers(), headers);
        areEquals(dynamic().cell(column(1),row(1)).getValue(), firstCell,
                String.format("Expected first cell content id %s, but was %s",firstCell, dynamic().cell(column(1),row(1)).getValue()));
    }

    @Test (enabled = false, dataProvider = "deleteRow", dataProviderClass = TableDP.class)
    public void deleteRow (int rowPosition, int expectedRowCount, List<String> headers, String firstCell){
        dynamicTablePage.selectRowsDD.uncheck(rowPosition);
        dynamicTablePage.selectRowsBtn.click();

        areEquals(dynamic().rows().count(), expectedRowCount,
                String.format("Expected column count is %d, but was %d", expectedRowCount, dynamic().rows().count()));
        areEquals(dynamic().rows().headers(), headers);
        areEquals(dynamic().cell(column(1),row(1)).getValue(), firstCell,
                String.format("Expected first cell content id %s, but was %s",firstCell, dynamic().cell(column(1),row(1)).getValue()));
    }

    @Test (enabled = false)
    public void verifyTableContentAfterSorting(){
        SelectElement header = dynamic().header("2");
        header.click();

        MapArray<String, MapArray<String, String>> tableContent = dynamic().rows().getAsText();

        areEquals(tableContent, "");
    }

}
