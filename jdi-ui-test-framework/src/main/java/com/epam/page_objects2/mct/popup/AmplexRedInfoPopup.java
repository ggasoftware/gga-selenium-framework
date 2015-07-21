package com.epam.page_objects2.mct.popup;

import com.epam.page_objects2.entities.Experiment;
import com.epam.ui_test_framework.elements.composite.PopupForm;
import com.epam.ui_test_framework.elements.interfaces.common.IButton;
import com.epam.ui_test_framework.elements.interfaces.common.IInput;
import com.epam.ui_test_framework.elements.page_objects.annotations.functions.*;
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

    @FindBy(xpath = "//button[contains(text(),'OK')]") @OkButton
    private IButton okButton;
    @FindBy(xpath = "//button[contains(text(),'Cancel')]") @CancelButton
    private IButton cancelButton;
}
