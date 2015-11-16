package com.epam.jditests.pageobjects.pages;

import com.epam.jditests.enums.Colors;
import com.epam.jditests.enums.Metals;
import com.epam.jditests.enums.Nature;
import com.epam.jditests.pageobjects.sections.Summary;
import com.ggasoftware.jdiuitest.core.interfaces.common.ILabel;
import com.ggasoftware.jdiuitest.core.interfaces.common.IText;
import com.ggasoftware.jdiuitest.core.interfaces.complex.ICheckList;
import com.ggasoftware.jdiuitest.core.interfaces.complex.IComboBox;
import com.ggasoftware.jdiuitest.core.interfaces.complex.IDropDown;
import com.ggasoftware.jdiuitest.web.selenium.elements.base.Element;
import com.ggasoftware.jdiuitest.web.selenium.elements.common.Button;
import com.ggasoftware.jdiuitest.web.selenium.elements.common.CheckBox;
import com.ggasoftware.jdiuitest.web.selenium.elements.common.Label;
import com.ggasoftware.jdiuitest.web.selenium.elements.complex.ComboBox;
import com.ggasoftware.jdiuitest.web.selenium.elements.complex.Dropdown;
import com.ggasoftware.jdiuitest.web.selenium.elements.composite.WebPage;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

/**
 * Created by Maksim_Palchevskii on 8/17/2015.
 */
public class MetalsColorsPage extends WebPage {

    @FindBy(id = "summary-block")
    public Summary summary;

    @FindBy(id = "calculate-button")
    public Label calculate;

    @FindBy(id = "calculate-button")
    public Button calculateButton = new Button() {
        @Override
        protected String getTextAction() {
            return getWebElement().getText();
        }
    };
    @FindBy(id = "calculate-button")
    public ILabel calculateLabel;

    public IDropDown<Colors> colors = new Dropdown<>(By.cssSelector(".colors .filter-option"),
            By.cssSelector(".colors li span"));

    @FindBy(css = ".summ-res")
    public IText calculateText;

    @FindBy(css = "#elements-checklist label")
    public ICheckList<Nature> nature;

    @FindBy(xpath = "//*[@id='elements-checklist']//*[label[text()='%s']]/label")
    public ICheckList<Nature> natureTemplate;


    @FindBy(xpath = "//*[@id='elements-checklist']//*[text()='Water']")
    public CheckBox cbWater = new CheckBox() {
        @Override
        protected boolean isCheckedAction() {
            return new Element(By.xpath("//*[@id='elements-checklist']//*[*[text()='Water']]/input"))
                    .getInvisibleElement().getAttribute("checked") != null;
        }
    };

    public IComboBox<Metals> comboBox =
            new ComboBox<Metals>(By.cssSelector(".metals .caret"), By.cssSelector(".metals li span"), By.cssSelector(".metals .filter-option")) /*{
        /		@Override
				protected String getTextAction() {
					return getWebElement().getText();
				}
			}*/;
}
