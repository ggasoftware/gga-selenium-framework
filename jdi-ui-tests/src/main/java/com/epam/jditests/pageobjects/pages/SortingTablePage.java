package com.epam.jditests.pageobjects.pages;

import com.ggasoftware.jdiuitests.implementation.selenium.elements.common.TextField;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.complex.table.interfaces.ITable;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.composite.Page;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.composite.Pagination;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.interfaces.complex.IPagination;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.interfaces.complex.ISelector;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

/**
 * Created by Natalia_Grebenshchik on 10/14/2015.
 */
public class SortingTablePage extends Page{

    @FindBy(id = "DataTables_Table_0")
    public ITable sortingTable;

    public Pagination tablePagination = new Pagination( By.id("DataTables_Table_0_wrapper"),
                                                        By.linkText("Next"),
                                                        By.linkText("Previous"));

    @FindBy(name = "DataTables_Table_0_length")
    public ISelector rowsNumberInPageDD;

    @FindBy(xpath = ".//input[@type='search']")
    public TextField search;
}
