package com.epam.jditests.pageobjects.pages;

import com.epam.jditests.enums.Colors;
import com.epam.jditests.enums.Metals;
import com.epam.jditests.enums.Nature;
import com.epam.jditests.pageobjects.sections.Summary;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.base.Element;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.common.Button;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.common.CheckBox;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.complex.ComboBox;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.complex.Dropdown;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.composite.Page;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.interfaces.common.IButton;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.interfaces.common.ICheckBox;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.interfaces.common.ILabel;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.interfaces.common.IText;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.interfaces.complex.ICheckList;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.interfaces.complex.IComboBox;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.interfaces.complex.IDropDown;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

/**
 * Created by Maksim_Palchevskii on 8/17/2015.
 */
public class MetalsColorsPage extends Page {

	@FindBy(id = "summary-block")
	public Summary summary;

	@FindBy(id = "calculate-button")
	public ILabel calculate;

	@FindBy(id = "calculate-button")
	public IButton calculateButton = new Button() {
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
	public ICheckBox cbWater = new CheckBox() {
	@Override
	protected boolean isCheckedAction() { return new Element(By.xpath("//*[@id='elements-checklist']//*[*[text()='Water']]/input"))
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
