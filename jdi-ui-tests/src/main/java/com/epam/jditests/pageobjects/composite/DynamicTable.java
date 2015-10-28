package com.epam.jditests.pageobjects.composite;

import com.ggasoftware.jdiuitests.implementation.selenium.elements.complex.table.Table;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.complex.table.TableSettings;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.complex.table.interfaces.ITable;
import org.openqa.selenium.By;

/**
 * Created by Natalia_Grebenshchik on 10/21/2015.
 */
public class DynamicTable extends Table implements ITable{

    public DynamicTable() {
        super(
                By.xpath(".//thead//th"),
                By.xpath(".//table//td[1]"),
                By.xpath(".//table//tr"),
                By.xpath(".//table//tr/td"),
                By.xpath(".//tfoot//th"),
                new TableSettings(true, true),1,1);

        this.cellLocatorTemplate = By.xpath(".//table//tr[{1}]/td[{0}]");
    }

}
