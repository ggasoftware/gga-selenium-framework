package com.epam.jditests.pageobjects.pages;

import com.ggasoftware.jdiuitest.web.selenium.elements.complex.table.interfaces.ITable;
import com.ggasoftware.jdiuitest.web.selenium.elements.composite.WebPage;
import org.openqa.selenium.support.FindBy;

/**
 * Created by Natalia_Grebenshchik on 10/22/2015.
 */
public class SimpleTablePage extends WebPage {
    @FindBy(xpath = "*//table")
    public ITable simpleTable;
}
