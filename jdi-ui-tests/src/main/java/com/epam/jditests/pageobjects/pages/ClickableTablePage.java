package com.epam.jditests.pageobjects.pages;

import com.ggasoftware.jdiuitest.web.selenium.elements.complex.table.interfaces.ITable;
import com.ggasoftware.jdiuitest.web.selenium.elements.composite.WebPage;
import org.openqa.selenium.support.FindBy;

/**
 * Created by Natalia_Grebenshchik on 10/14/2015.
 */
public class ClickableTablePage extends WebPage {

    @FindBy(className = "uui-dynamic-table")
    public ITable clickableTable;

}
