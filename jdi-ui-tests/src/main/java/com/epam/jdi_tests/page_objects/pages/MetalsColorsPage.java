package com.epam.jdi_tests.page_objects.pages;

import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.common.CheckBox;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

import com.epam.jdi_tests.enums.Colors;
import com.epam.jdi_tests.enums.Metals;
import com.epam.jdi_tests.enums.Nature;
import com.epam.jdi_tests.page_objects.sections.Summary;
import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.complex.ComboBox;
import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.complex.Dropdown;
import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.composite.Page;
import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.interfaces.common.IButton;
import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.interfaces.common.ICheckBox;
import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.interfaces.common.ILabel;
import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.interfaces.common.IText;
import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.interfaces.complex.ICheckList;
import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.interfaces.complex.IComboBox;
import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.interfaces.complex.IDropDown;

/**
 * Created by Maksim_Palchevskii on 8/17/2015.
 */
public class MetalsColorsPage extends Page {

	@FindBy(id = "summary-block")
	public Summary summary;

	@FindBy(id = "calculate-button")
	public ILabel calculate;

	@FindBy(id = "calculate-button")
	public IButton calculateButton;

	public IDropDown<Colors> colors = new Dropdown<>(By.cssSelector(".colors .filter-option"),
			By.cssSelector(".colors li span"));

	@FindBy(css = ".summ-res")
	public IText calculateText;

	@FindBy(css = "#elements-checklist label")
	public ICheckList<Nature> nature;

	@FindBy(xpath = "//*[@id='elements-checklist']//*[label[text()='%s']]/input")
	public ICheckList<Nature> natureTemplate;


	@FindBy(css = "#elements-checklist input")
	public ICheckBox cbWater = new CheckBox() {
	@Override
	protected boolean isCheckedAction() { return getWebElement().getAttribute("checked") != null; }
	};

    public IComboBox<Metals> comboBox = new ComboBox<>(By.cssSelector(".metals .filter-option"), By.cssSelector(".metals li span"));
}
