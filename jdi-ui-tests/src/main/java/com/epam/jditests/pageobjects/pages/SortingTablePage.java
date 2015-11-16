package com.epam.jditests.pageobjects.pages;

import com.ggasoftware.jdiuitest.web.selenium.elements.common.TextField;
import com.ggasoftware.jdiuitest.web.selenium.elements.complex.table.interfaces.ITable;
import com.ggasoftware.jdiuitest.web.selenium.elements.composite.WebPage;
import com.ggasoftware.jdiuitest.web.selenium.elements.composite.Pagination;
import com.ggasoftware.jdiuitest.core.interfaces.complex.ISelector;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

/**
 * Created by Natalia_Grebenshchik on 10/14/2015.
 */
public class SortingTablePage extends WebPage {

    @FindBy(id = "DataTables_Table_0")
    public ITable sortingTable;

    public Pagination tablePagination = new Pagination(By.id("DataTables_Table_0_wrapper"),
            By.linkText("Next"),
            By.linkText("Previous"));

    @FindBy(name = "DataTables_Table_0_length")
    public ISelector rowsNumberInPageDD;

    @FindBy(xpath = ".//input[@type='search']")
    public TextField search;
}
