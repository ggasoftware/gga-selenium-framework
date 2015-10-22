package com.epam.jditests.pageobjects.pages;

import com.epam.jditests.enums.ColumnHeaders;
import com.epam.jditests.pageobjects.composite.DynamicTable;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.common.Button;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.complex.DropList;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.composite.Page;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

import static com.epam.jditests.pageobjects.EpamJDISite.dynamicTablePage;

/**
 * Created by Natalia_Grebenshchik on 10/14/2015.
 */
public class DynamicTablePage extends Page{
    @FindBy(xpath = "*//table[@class='uui-table stripe tbl-scroll' or @class='uui-table stripe tbl-scroll table-delete']")
    public DynamicTable dynamicTable;

    @FindBy(xpath = "*//button[contains(@class,'btn dropdown-toggle selectpicker btn-default')]")
    public Button dropDownButton;

    public ColumnDropList<ColumnHeaders> tableColumnDD = new ColumnDropList<ColumnHeaders>(
            By.xpath("*//ul[contains(@class,'dropdown-menu inner selectpicker')]"),
            By.xpath("*//ul[contains(@class,'dropdown-menu inner selectpicker')]/li")) {
        @Override
        public void selectByName(String name) {
            dynamicTablePage.dropDownButton.click();
            dynamicTablePage.tableColumnDD.select(name);
            dynamicTablePage.dropDownButton.click();
        }
    };

    @FindBy(xpath = "*//button[text()='Reestablish']")
    public Button ReestablishBtn;
    @FindBy(xpath = "*//button[text()='Apply']")
    public Button applyBtn;

    public abstract class ColumnDropList<ColumnHeaders extends Enum> extends DropList<ColumnHeaders>{
        public ColumnDropList(By xpath, By xpath1) {
            super(xpath, xpath1);
        }

        public abstract void selectByName(String name);
    }

}
