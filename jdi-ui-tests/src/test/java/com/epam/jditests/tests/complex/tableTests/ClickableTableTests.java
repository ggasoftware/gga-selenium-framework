package com.epam.jditests.tests.complex.tableTests;

import com.epam.jditests.InitTests;
import com.epam.jditests.dataproviders.TableDP;
import com.epam.jditests.enums.CellValues;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.base.SelectElement;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.common.DatePicker;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.complex.Dropdown;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.interfaces.base.IClickable;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.complex.table.interfaces.ICell;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.complex.table.interfaces.ITable;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.lang.reflect.Method;

import static com.epam.jditests.enums.Preconditions.CLICKABLE_TABLE_PAGE;
import static com.epam.jditests.pageobjects.EpamJDISite.*;
import static com.ggasoftware.jdiuitests.implementation.selenium.elements.complex.table.Column.column;
import static com.ggasoftware.jdiuitests.implementation.selenium.elements.complex.table.Row.row;
import static com.ggasoftware.jdiuitests.implementation.testng.asserter.Assert.*;

/**
 * Created by Natalia_Grebenshchik on 10/15/2015.
 */
public class ClickableTableTests extends InitTests{

    protected ITable clickable() {
        return clickableTablePage.clickableTable;
    }

    @BeforeMethod
    protected void before(Method method) throws IOException {
        isInState(CLICKABLE_TABLE_PAGE, method);
        clickable().useCache();
    }

    @Test (enabled = false)
    public void testClickOnCell(){
        ICell cell = clickable().cell(column(2), row(2));

        cell.click();

        areEquals(actionsLog.getText(0), "TestNG, JUnit Custom", String.format("Expected log after cell click is %s, but was %s"));
        isTrue(cell.isSelected(), "Clicked cell is not selected");
    }

    @Test (enabled = false)
    public void testClickOnColumnHeader(){
        SelectElement header = clickable().header("Now");

        header.click();

        areEquals(actionsLog.getText(0), "Now", String.format("Expected log after cell click is %s, but was %s"));
        isTrue(clickable().cell(column("Now"), row(2)).isSelected(),"Cell is not selected after clicking on its header");
    }

    @Test (enabled = false)
    public void testClickOnRowHeader(){
        SelectElement header = clickable().rows().header("1");

        header.click();

        areEquals(actionsLog.getText(0), "Now", String.format("Expected log after cell click is %s, but was %s"));
        isTrue(clickable().cell(column(2), row(2)).isSelected(),"Cell is not selected after clicking on its header");
    }

    @Test (enabled = false, dataProvider = "tableCellsClickableContent", dataProviderClass = TableDP.class)
    public void clickCellsClickableObject(int columnIndex, int rowIndex, Class className, String logMessage) throws Exception {
        ICell cell;
        Object cellsObject;

        cell = clickable().cell(column(columnIndex), row(rowIndex));
        cellsObject = cell.get(className);

        ((IClickable)cellsObject).click();

        matches(actionsLog.getText(0), logMessage, String.format("Expected log after clicking is %s, but was %s", actionsLog.getText(0), logMessage));
    }

    @Test (enabled = false)
    public void selectCellsDropdown() throws Exception {
        ICell cell = clickable().cell(column(3), row(2));
        Dropdown<CellValues> dropdown = cell.get(Dropdown.class);

        dropdown.select(CellValues.value2);

        matches(actionsLog.getText(0), "ExpectedLogMessage", String.format("Expected log after clicking is %s, but was %s"));
        assertEquals(dropdown.getText(),CellValues.value2);
    }

    @Test (enabled = false)
    public void selectCellsDatePicker() throws Exception{
        ICell cell = clickable().cell(column(3), row(2));
        DatePicker datePicker = cell.get(DatePicker.class);

        datePicker.setValue("12/10/2015");

        matches(actionsLog.getText(0), "ExpectedLogMessage", String.format("Expected log after clicking is %s, but was %s"));
        assertEquals(datePicker.getText(),"12/10/2015");

    }
}
