package com.epam.page_objects.mct.popup;

import com.epam.page_objects.entities.Experiment;
import com.epam.ui_test_framework.elements.composite.PopupForm;
import com.epam.ui_test_framework.elements.interfaces.simple.IButton;
import com.epam.ui_test_framework.elements.interfaces.simple.IInput;
import com.epam.ui_test_framework.elements.page_objects.annotations.Function;
import org.openqa.selenium.support.FindBy;

/**
 * Created by Nataliia_Garkusha on 13-Jun-15.
 */
public class AmplexRedInfoPopup extends PopupForm<Experiment> {

    @FindBy(xpath = "//*[label[contains (text(), 'Assay Name:')]]//input")
    private IInput assayName;
    @FindBy(xpath = "//*[label[contains (text(), 'Catalog Number:')]]//input")
    private IInput catalogNumber;
    @FindBy(xpath = "//*[label[contains (text(), 'Lot Number:')]]//input")
    private IInput lotNumber;

    @FindBy(xpath = "//button[contains(text(),'OK')]") @Function(value = "ok")
    private IButton okButton;
    @FindBy(xpath = "//button[contains(text(),'Cancel')]") @Function(value = "cancel")
    private IButton cancelButton;
}
