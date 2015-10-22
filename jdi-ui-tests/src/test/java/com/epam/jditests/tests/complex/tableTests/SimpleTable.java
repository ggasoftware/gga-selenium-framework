package com.epam.jditests.tests.complex.tableTests;

import com.epam.jditests.InitTests;
import com.epam.jditests.dataproviders.TableDP;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.complex.table.Table;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.complex.table.TableSettings;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.complex.table.interfaces.ICell;
import com.ggasoftware.jdiuitests.implementation.testng.asserter.Assert;
import org.openqa.selenium.By;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;

import static com.epam.jditests.enums.Preconditions.DYNAMIC_TABLE_PAGE;
import static com.epam.jditests.enums.Preconditions.NO_HEADERS_TABLE_PAGE;
import static com.epam.jditests.pageobjects.EpamJDISite.actionsLog;
import static com.epam.jditests.pageobjects.EpamJDISite.dynamicTablePage;
import static com.epam.jditests.pageobjects.EpamJDISite.isInState;
import static com.ggasoftware.jdiuitests.implementation.selenium.elements.complex.table.Column.column;
import static com.ggasoftware.jdiuitests.implementation.selenium.elements.complex.table.Row.row;
import static com.ggasoftware.jdiuitests.implementation.testng.asserter.Assert.areEquals;
import static com.ggasoftware.jdiuitests.implementation.testng.asserter.Assert.isTrue;
import static java.lang.String.format;
import static java.util.Arrays.asList;

/**
 * Created by Natalia_Grebenshchik on 10/21/2015.
 */
public class SimpleTable extends InitTests{

    private Table initTable (Table table){
        table.avatar.byLocator = By.xpath("*//table");
        return table;
    }

    @BeforeMethod
    protected void before(Method method) throws IOException {
        isInState(NO_HEADERS_TABLE_PAGE);
    }

    @Test(enabled = false)
    public void testClickOnCell(){
       /* ICell cell = sorting().cell(column(2), row(2));

        cell.click();

        areEquals(actionsLog.getText(0), "TestNG, JUnit Custom", String.format("Expected log after cell click is %s, but was %s"));
        isTrue(cell.isSelected(), "Clicked cell is not selected");*/
    }

    @Test(enabled = false, dataProvider = "hasHeadersSelector", dataProviderClass = TableDP.class)
    public void verifyHasHeadersVariablesByColumnRowCount(  boolean hasColumnHeaders, boolean hasRowHeaders,
                                                         int columnsCount, int rowsCount,
                                                         List<String> columnHeaders, List<String> rowsHeaders,
                                                         String firstColumnContent){
        Table table = initTable(new Table(new TableSettings(hasColumnHeaders, hasRowHeaders)));

        Assert.isTrue((table.columns().count() == columnsCount && table.rows().count() == rowsCount),
                format("Expected column/row count is %d/%d, but was %d/%d", columnsCount, rowsCount, table.columns().count(), table.rows().count()));
    }
}
