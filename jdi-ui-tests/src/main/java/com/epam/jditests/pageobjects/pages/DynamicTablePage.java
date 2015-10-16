package com.epam.jditests.pageobjects.pages;

import com.ggasoftware.jdiuitests.implementation.selenium.elements.common.Button;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.common.Text;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.common.TextField;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.complex.DropList;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.complex.Dropdown;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.complex.table.interfaces.ITable;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.composite.Page;
import org.openqa.selenium.support.FindBy;

/**
 * Created by Natalia_Grebenshchik on 10/14/2015.
 */
public class DynamicTablePage extends Page{
    @FindBy(className = "uui-dynamic-table")
    public ITable dynamicTable;

    @FindBy(name = "select-columns-dd")
    public DropList selectColumnsDD;
    @FindBy(name = "apply-selected-columns-btn")
    public Button selectColumnsBtn;

    @FindBy(name = "select-rows-dd")
    public DropList selectRowsDD;
    @FindBy(name = "apply-selected-rows-btn")
    public Button selectRowsBtn;
}
