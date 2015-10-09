package com.epam.jdi_tests.page_objects.pages;

import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.complex.table.Table;
import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.complex.table.interfaces.ITable;
import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.composite.Page;
import org.openqa.selenium.support.FindBy;

/**
 * Created by Maksim_Palchevskii on 8/17/2015.
 */
public class SupportPage extends Page {
    @FindBy(className = "uui-table")
    public ITable supportTable;



}
