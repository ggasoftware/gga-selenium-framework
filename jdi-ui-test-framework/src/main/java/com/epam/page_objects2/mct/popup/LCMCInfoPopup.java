package com.epam.page_objects2.mct.popup;

import com.epam.page_objects2.entities.Experiment;
import com.epam.ui_test_framework.elements.complex.Dropdown;
import com.epam.ui_test_framework.elements.composite.PopupForm;
import com.epam.ui_test_framework.elements.interfaces.common.IButton;
import com.epam.ui_test_framework.elements.interfaces.complex.IDropDown;
import com.epam.ui_test_framework.elements.interfaces.common.IInput;
import com.epam.ui_test_framework.elements.page_objects.annotations.functions.*;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

/**
 * Created by Nataliia_Garkusha on 10-Jun-15.
 */
public class LCMCInfoPopup extends PopupForm<Experiment> {
    @FindBy(xpath = "//input[@name='containerBarcode']")
    private IInput barCode;
    @FindBy(xpath = "//input[@name='proteinExpressionResult']")
    private IInput proteinExprRes;
    private IDropDown result = new Dropdown<>(
            By.xpath("//*[@class='k-input' and text()='Select...']"),
            By.xpath("//ul[@aria-hidden='false']/li[text()='%s']"));

    @FindBy(xpath = "//button[contains(text(),'OK')]") @OkButton
    private IButton okButton;
    @FindBy(xpath = "//button[contains(text(),'Cancel')]") @CancelButton
    private IButton cancelButton;


}