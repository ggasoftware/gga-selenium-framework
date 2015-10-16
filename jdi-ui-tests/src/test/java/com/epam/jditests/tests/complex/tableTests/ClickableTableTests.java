package com.epam.jditests.tests.complex.tableTests;

import com.epam.jditests.InitTests;
import com.epam.jditests.dataproviders.TableDP;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.base.SelectElement;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.common.Button;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.common.Link;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.common.Text;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.complex.Dropdown;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.complex.TextList;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.interfaces.common.ITextArea;
import com.ggasoftware.jdiuitests.implementation.testng.asserter.Assert;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.complex.table.Column;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.complex.table.Row;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.complex.table.interfaces.ICell;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.complex.table.interfaces.ITable;
import com.sun.java.util.jar.pack.*;
import org.apache.tools.ant.types.resources.selectors.InstanceOf;
import org.openqa.selenium.By;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;

import static com.epam.jditests.enums.Preconditions.CLICKABLE_TABLE_PAGE;
import static com.epam.jditests.pageobjects.EpamJDISite.*;
import static com.ggasoftware.jdiuitests.implementation.selenium.elements.complex.table.Column.column;
import static com.ggasoftware.jdiuitests.implementation.selenium.elements.complex.table.Row.row;
import static com.ggasoftware.jdiuitests.implementation.testng.asserter.Assert.areEquals;
import static com.ggasoftware.jdiuitests.implementation.testng.asserter.Assert.isTrue;

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

    @Test (enabled = false, dataProvider = "tableCellsContent", dataProviderClass = TableDP.class)
    public void clickCellsButton(int columnIndex, int rowIndex, Class className, String logMessage) throws Exception {

        ICell cell = clickable().cell(column(3), row(2));

        Object d = className.newInstance();
        
      //  Button.class b = cell.get(Link.class);
      //  b.click();
    }


}
