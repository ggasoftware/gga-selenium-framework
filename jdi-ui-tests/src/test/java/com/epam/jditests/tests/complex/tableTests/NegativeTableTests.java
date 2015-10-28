package com.epam.jditests.tests.complex.tableTests;

import com.epam.jditests.InitTests;
import com.epam.jditests.dataproviders.TableDP;
import com.epam.jditests.pageobjects.pages.SupportPage;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.complex.table.Table;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.complex.table.TableSettings;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.complex.table.interfaces.ITable;
import com.ggasoftware.jdiuitests.implementation.testng.asserter.Assert;
import org.openqa.selenium.By;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.epam.jditests.enums.Preconditions.SIMPLE_PAGE;
import static com.epam.jditests.enums.Preconditions.SUPPORT_PAGE;
import static com.epam.jditests.pageobjects.EpamJDISite.isInState;
import static com.epam.jditests.pageobjects.EpamJDISite.supportPage;
import static com.ggasoftware.jdiuitests.implementation.selenium.elements.complex.table.Column.column;
import static com.ggasoftware.jdiuitests.implementation.selenium.elements.complex.table.Row.row;
import static com.ggasoftware.jdiuitests.implementation.testng.asserter.Assert.areEquals;
import static java.lang.String.format;

/**
 * Created by Natalia_Grebenshchik on 10/28/2015.
 */
public class NegativeTableTests extends InitTests {

    private Table tableNoHeaders() {
        Table table = new Table(By.xpath("./tbody/tr[1]/td"),
                By.xpath("./tbody/tr[position() > 0]/td[1]"),
                By.xpath("./tbody/tr"),
                By.xpath("./tbody/tr/td"), null,
                new TableSettings(false, false), 1, 1);

        table.avatar.byLocator = By.xpath("*//table");

        return table;
    }

    private ITable tableWithHeaders() {
        return supportPage.supportTable;
    }

    final String _assertionException = "java.lang.AssertionError: Failed to do 'Get web element' action. Exception: java.lang.RuntimeException: java.lang.AssertionError: Can't get Web Elements";
    final String _nullPointerException = "java.lang.NullPointerException";

    @Test(dataProvider = "cellIndexes", dataProviderClass = TableDP.class)
    public void verifyIllegalCellIndex(int columnIndex, int rowIndex) {
        isInState(SIMPLE_PAGE);

        try {
            tableNoHeaders().cell(column(columnIndex), row(rowIndex)).getWebElement();
        } catch (Error | Exception e) {
            areEquals(e.toString(), _assertionException,
                    format("Expected exception is \n'%s'\n but was \b'%s'", _assertionException, e.toString()));
            return;
        }

        throw new RuntimeException(format("Web element for cell %d/%d was found", columnIndex, rowIndex));
    }

    @Test(dataProvider = "indexes", dataProviderClass = TableDP.class)
    public void verifyIllegalRowIndex(int rowIndex) {
        isInState(SIMPLE_PAGE);
        String expectedException =
                format("java.lang.AssertionError: Can't Get Column '%d'. [num] > ColumnsCount(3).", rowIndex);

        try {
            tableNoHeaders().row(rowIndex);
        } catch (Error | Exception e) {
            areEquals(e.toString(), expectedException,
                    format("Expected exception is \n'%s'\n but was \b'%s'", expectedException, e.toString()));
            return;
        }

        throw new RuntimeException(format("Web element for row %d was found", rowIndex));
    }

    @Test(dataProvider = "indexes", dataProviderClass = TableDP.class)
    public void verifyIllegalColumnIndex(int columnIndex) {
        isInState(SIMPLE_PAGE);
        String expectedException =
                format("java.lang.AssertionError: Can't Get Row '%d'. [num] > RowsCount(6).", columnIndex);

        try {
            tableNoHeaders().column(columnIndex);
        } catch (Error | Exception e) {
            areEquals(e.toString(), expectedException,
                    format("Expected exception is \n'%s'\n but was \b'%s'", expectedException, e.toString()));
            return;
        }

        throw new RuntimeException(format("Web element for column %d was found", columnIndex));
    }

    @Test
    public void clickIllegalColumnHeaders_ByName() {

        isInState(SUPPORT_PAGE);

        try {
            tableWithHeaders().header("Now_illegal").getWebElement();
        } catch (Error | Exception e) {
            areEquals(e.toString(), _nullPointerException,
                    format("Expected exception is \n'%s'\n but was \b'%s'", _nullPointerException, e.toString()));
            return;
        }

        throw new RuntimeException("Web element for header 'Now_illegal' was found");
    }

    @Test
    public void clickIllegalColumnHeaders_ByNumber() {

        isInState(SUPPORT_PAGE);
        ((Table) tableWithHeaders()).setTableSettings(new TableSettings(true, true));

        try {
            tableWithHeaders().rows().header("Now_illegal").getWebElement();
        } catch (Error | Exception e) {
            areEquals(e.toString(), _nullPointerException,
                    format("Expected exception is \n'%s'\n but was \b'%s'", _nullPointerException, e.toString()));
            return;
        }

        throw new RuntimeException("Web element for header 'Now_illegal' was found");
    }

}
