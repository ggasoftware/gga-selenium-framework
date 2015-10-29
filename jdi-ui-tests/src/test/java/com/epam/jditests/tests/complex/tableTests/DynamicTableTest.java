package com.epam.jditests.tests.complex.tableTests;

import com.epam.jditests.InitTests;
import com.epam.jditests.enums.ColumnHeaders;
import com.epam.jditests.pageobjects.composite.DynamicTable;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.base.Element;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.common.CheckBox;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.common.Link;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.complex.table.interfaces.ICell;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.interfaces.common.ICheckBox;
import com.ggasoftware.jdiuitests.implementation.testng.asserter.Check;
import org.openqa.selenium.By;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.epam.jditests.enums.Preconditions.DYNAMIC_TABLE_PAGE;
import static com.epam.jditests.pageobjects.EpamJDISite.*;
import static com.ggasoftware.jdiuitests.core.utils.common.LinqUtils.foreach;
import static com.ggasoftware.jdiuitests.implementation.selenium.elements.complex.table.Column.column;
import static com.ggasoftware.jdiuitests.implementation.selenium.elements.complex.table.Row.row;
import static com.ggasoftware.jdiuitests.implementation.testng.asserter.Assert.areEquals;
import static com.ggasoftware.jdiuitests.implementation.testng.asserter.Assert.isTrue;
import static com.ggasoftware.jdiuitests.implementation.testng.asserter.Assert.matches;

/**
 * Created by Natalia_Grebenshchik on 10/16/2015.
 */
public class DynamicTableTest extends InitTests{

    protected DynamicTable dynamic() {
        return dynamicTablePage.dynamicTable;
    }

    @BeforeMethod
    protected void before(Method method) throws IOException {
        isInState(DYNAMIC_TABLE_PAGE, method);
        dynamicTablePage.refresh();
        dynamic().clean();
    }

    @Test
    public void addNewColumn (){
        List<String> expectedColumnHeaders = new ArrayList<>();

        dynamicTablePage.tableColumnDD.selectByName(ColumnHeaders.col3.value);
        dynamicTablePage.applyBtn.click();

        areEquals(dynamic().columns().count(), 3,
                String.format("Expected column count is %d, but was %d", 3, dynamic().columns().count()));

        foreach(ColumnHeaders.values(), val -> expectedColumnHeaders.add(val.value));
        areEquals(Arrays.asList(dynamic().headers()), expectedColumnHeaders,
                String.format("Expected column header is %s, but was %s", Arrays.asList(dynamic().headers()),expectedColumnHeaders));

        areEquals(dynamic().cell(column(1),row(1)).getValue(), "Select\nSee More\n.NET Technologies",
                String.format("Expected first cell content id %s, but was %s","Select\nSee More\n.NET Technologies", dynamic().cell(column(1),row(1)).getValue()));
    }

    @Test
    public void deleteColumn (){

        dynamicTablePage.tableColumnDD.selectByName(ColumnHeaders.col1.value);
        dynamicTablePage.applyBtn.click();

        areEquals(dynamic().columns().count(), 1,
                String.format("Expected column count is %d, but was %d", 1, dynamic().columns().count()));

        areEquals(dynamic().headers()[0], ColumnHeaders.col2.value,
                String.format("Expected column header is %s, but was %s",dynamic().headers()[0],ColumnHeaders.col2.value));

        areEquals(dynamic().cell(column(1),row(1)).getValue(), "Select\nSee More\nInternet Technologies",
                String.format("Expected first cell content id %s, but was %s","Select\nSee More\nInternet Technologies", dynamic().cell(column(1),row(1)).getValue()));
    }

    @Test
    public void clickCellsLinkObject(){

        ICell cell = dynamic().cell(column(2), row(2));
        Link cellObject = cell.get(new Link(By.xpath(".//tr[{0}]/td[{1}]//a")));
        cellObject.click();

        areEquals(cellObject.getText(),"See More",
                String.format("Expected link test is 'See More', but was '%s'", cellObject.getText()));
        matches(actionsLog.getText(0), "([0-9]{2}:){2}[0-9]{2} :See More link clicked");
    }

    @Test
    public void checkCellsCheckBoxObject(){

        ICell cell = dynamic().cell(column(2), row(2));
        CellCheckBox cellObject = cell.get(new CellCheckBox((By.xpath(".//tr[{0}]/td[{1}]//label"))));
        cellObject.click();

        isTrue(cellObject.isChecked(), "CheckBox is not checked");
        matches(actionsLog.getText(0), "([0-9]{2}:){2}[0-9]{2} Select: condition changed to true");
        areEquals(actionsLog.count(),1,"There are more rows in log, then expected");
    }

    @Test
    public void scrolledTableRowCount () {

        areEquals(dynamic().rows().count(), 8,
                String.format("Expected column count is 8, but was %d", dynamic().columns().count()));

    }

    @Test
    public void navigateToCellAfterScroll() {
        ICell cell = dynamic().cell(column(2), row(8));

        areEquals(cell.columnNum(),2, String.format("Expected column index id 2, but was %d", cell.columnNum()));
        areEquals(cell.rowNum(), 8, String.format("Expected column index id 8, but was %d", cell.rowNum()));
        areEquals(cell.getText(),"Select\nSee More\nAdobe Day CRX",
                String.format("Expected call value is 'Select\nSee More\nAdobe Day CRX', but was '%s'", cell.getText()));

        cell.get(new Link(By.xpath(".//table//tr[{0}]/td[{1}]//a"))).click();

        matches(actionsLog.getText(0), "([0-9]{2}:){2}[0-9]{2} :See More link clicked");
    }

    @Test
    public void assertFooterContent() {
        List<String>
                expectedFooterContent = Arrays.asList("1 column", "2 column", "3 column"),
                actualFooterContent = Arrays.asList(dynamic().footer());

        areEquals(actualFooterContent, expectedFooterContent,
                String.format("Expected footer is %s, but was %s", expectedFooterContent, actualFooterContent));
    }

    @Test
    public void assertRowHeaderContent(){
        List<String>
                expectedRowHeader = Arrays.asList(  "Microsoft Technologies", "Mobile", "UXD", "Version Control Systems",
                                                    "JavaScript Components and Frameworks", "Software Construction", "Life Sciences", "Content management"),
                actualRowHeader = Arrays.asList(dynamic().rows().headers());

        areEquals(actualRowHeader, expectedRowHeader,
                String.format("Expected row headers are %s, but were %s", expectedRowHeader, actualRowHeader));
    }


}
class CellCheckBox extends CheckBox {

    public CellCheckBox(By xpath) {
        super(xpath);
    }

    @Override
    protected boolean isCheckedAction(){
        return  Boolean.valueOf(new Element(((CellCheckBox) this).
                getWebElement().
                findElement(By.xpath("../input"))).
                getInvisibleElement().getAttribute("checked"));
    }
}