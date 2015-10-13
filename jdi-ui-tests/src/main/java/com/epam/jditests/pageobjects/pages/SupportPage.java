package com.epam.jditests.pageobjects.pages;

import com.ggasoftware.jdiuitests.implementation.selenium.elements.complex.table.interfaces.ITable;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.composite.Page;
import org.openqa.selenium.support.FindBy;

/**
 * Created by Maksim_Palchevskii on 8/17/2015.
 */
public class SupportPage extends Page {
    @FindBy(className = "uui-table")
    public ITable supportTable;



}
