package com.epam.jditests.pageobjects.pages;

import com.ggasoftware.jdiuitests.implementation.selenium.elements.complex.table.interfaces.ITable;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.composite.Page;
import org.openqa.selenium.support.FindBy;

/**
 * Created by Natalia_Grebenshchik on 10/14/2015.
 */
public class ClickableTablePage extends Page {

    @FindBy(className = "uui-dynamic-table")
    public ITable clickableTable;

}
