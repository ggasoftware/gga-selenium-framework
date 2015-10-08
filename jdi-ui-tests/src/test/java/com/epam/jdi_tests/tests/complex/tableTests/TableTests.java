package com.epam.jdi_tests.tests.complex.tableTests;

import com.epam.jdi_tests.dataproviders.DatePickerDP;
import com.epam.jdi_tests.dataproviders.TableDP;
import com.epam.jdi_tests.page_objects.pages.SupportPage;
import com.ggasoftware.jdi_ui_tests.core.asserter.Verify;
import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.complex.table.interfaces.ITable;
import com.ggasoftware.jdi_ui_tests.implementation.testng.asserter.Assert;
import com.ggasoftware.jdi_ui_tests.implementation.testng.asserter.ScreenAssert;
import org.junit.rules.ExpectedException;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static com.ggasoftware.jdi_ui_tests.core.asserter.Verify.getFails;
import static com.ggasoftware.jdi_ui_tests.core.utils.common.PrintUtils.print;
import static com.ggasoftware.jdi_ui_tests.implementation.testng.asserter.Assert.exception;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

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

}
