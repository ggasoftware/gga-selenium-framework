package com.epam.jditests.pageobjects.pages;

import com.ggasoftware.jdiuitests.implementation.selenium.elements.complex.table.Table;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.complex.table.TableSettings;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.complex.table.interfaces.ITable;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.composite.Page;
import org.openqa.selenium.support.FindBy;

/**
 * Created by Maksim_Palchevskii on 8/17/2015.
 */
public class SupportPage extends Page {
    @FindBy(css = ".uui-table")
    public ITable supportTable;
    @FindBy(css = ".uui-table")
    public ITable tableWithBothHeaders = new Table(new TableSettings(true, true));
    @FindBy(css = ".uui-table")
    public ITable tableWithRowsHeader = new Table(new TableSettings(false, true));
    @FindBy(css = ".uui-table")
    public ITable tableWithColumnHeader = new Table(new TableSettings(true, false));
    @FindBy(css = ".uui-table")
    public ITable tableWithoutHeaders = new Table(new TableSettings(false, false));

}
