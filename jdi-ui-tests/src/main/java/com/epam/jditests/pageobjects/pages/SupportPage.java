package com.epam.jditests.pageobjects.pages;

import com.ggasoftware.jdiuitest.web.selenium.elements.complex.table.Table;
import com.ggasoftware.jdiuitest.web.selenium.elements.complex.table.interfaces.ITable;
import com.ggasoftware.jdiuitest.web.selenium.elements.composite.WebPage;
import org.openqa.selenium.support.FindBy;

/**
 * Created by Maksim_Palchevskii on 8/17/2015.
 */
public class SupportPage extends WebPage {
    @FindBy(css = ".uui-table")
    public ITable supportTable;
    @FindBy(css = ".uui-table")
    public ITable tableWithHeaders = new Table().hasHeaders();
    @FindBy(css = ".uui-table")
    public ITable tableWithoutHeaders = new Table().hasNoHeaders();

}
