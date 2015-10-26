package com.epam.jditests.tests.complex.tableTests;

import com.epam.jditests.InitTests;
import com.ggasoftware.jdiuitests.core.utils.map.MapArray;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.base.SelectElement;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.complex.table.interfaces.ICell;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.complex.table.interfaces.ITable;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;

import static com.epam.jditests.enums.Preconditions.SORTING_TABLE_PAGE;
import static com.epam.jditests.pageobjects.EpamJDISite.*;
import static com.ggasoftware.jdiuitests.implementation.selenium.elements.complex.table.Column.column;
import static com.ggasoftware.jdiuitests.implementation.selenium.elements.complex.table.Row.row;
import static com.ggasoftware.jdiuitests.implementation.testng.asserter.Assert.*;

/**
 * Created by Natalia_Grebenshchik on 10/15/2015.
 */
public class SortingTableTests extends InitTests{

    protected ITable sorting() {
        return sortingTablePage.sortingTable;
    }

    @BeforeMethod
    protected void before(Method method) throws IOException {
        isInState(SORTING_TABLE_PAGE, method);
        sortingTablePage.refresh();
        sorting().clean();
    }

    @Test
    public void verifyTableContentAfterSorting(){
        sorting().header("Now").click();

        String actualColumnContent = sorting().rows().getColumnAsText("Now").toString();
        String expectedColumnContent = "1:AJAX, 2:Back- and Mid-Office Related, 3:Corporate, 4:Custom, 5:Entity Framework";

        areEquals(actualColumnContent, expectedColumnContent,
                String.format("Expected column content after soring is \n%s\nbut was \n%s", expectedColumnContent, actualColumnContent));
    }

    @Test
    public void verifyTableContentAfterSortingByDoubleClick(){
        sorting().header("Now").doubleClick();

        String actualColumnContent = sorting().rows().getColumnAsText("Now").toString();
        String expectedColumnContent = "1:TestNG, JUnit, Custom, 2:TestNG, JUnit Custom, 3:Selenium Custom, 4:Log4J, TestNG log, Custom, 5:Jenkins, Allure, Custom";

        areEquals(actualColumnContent, expectedColumnContent,
                String.format("Expected column content after soring is \n%s\nbut was \n%s", expectedColumnContent, actualColumnContent));
    }

    @Test
    public void verifyRowsCount(){

        assertEquals(sorting().rows().count(),5, String.format("Expected row count in 1-st page is 5, but found %d", sorting().rows().count()));

        sortingTablePage.tablePagination.next();
        sortingTablePage.tablePagination.next();

        assertEquals(sorting().rows().count(),1, String.format("Expected row count in 3-nd page is 1, but found %d", sorting().rows().count()));

        sortingTablePage.tablePagination.previous();

        assertEquals(sorting().rows().count(),5, String.format("Expected row count in 2-st page is 5, but found %d", sorting().rows().count()));
    }

    @Test
    public void verifyRowsAdded(){
        sortingTablePage.rowsNumberInPageDD.select("10");
        assertEquals(sorting().rows().count(),10, String.format("Expected row count is 10, but found %d", sorting().rows().count()));

        sortingTablePage.rowsNumberInPageDD.select("20");
        assertEquals(sorting().rows().count(),11, String.format("Expected row count is 11, but found %d", sorting().rows().count()));
    }

    @Test
    public void verifyRowsRemoved(){
        sortingTablePage.rowsNumberInPageDD.select("20");
        sortingTablePage.rowsNumberInPageDD.select("10");
        assertEquals(sorting().rows().count(),10, String.format("Expected row count is 10, but found %d", sorting().rows().count()));

        sortingTablePage.search.input("li");
        assertEquals(sorting().rows().count(),3, String.format("Expected row count is 3, but found %d", sorting().rows().count()));

    }

}
