package com.epam.page_objects2.mct.popup;

import com.epam.ui_test_framework.elements.composite.Popup;
import com.epam.ui_test_framework.elements.interfaces.common.IButton;
import com.epam.ui_test_framework.elements.interfaces.common.IText;
import com.epam.ui_test_framework.elements.page_objects.annotations.functions.CancelButton;
import com.epam.ui_test_framework.elements.page_objects.annotations.functions.OkButton;
import com.epam.ui_test_framework.elements.page_objects.annotations.functions.PopupText;
import org.openqa.selenium.support.FindBy;

/**
 * Created by Nataliia_Garkusha on 15-Jun-15.
 */
public class MCTInfo extends Popup {
    @FindBy(css = ".mct-dialog-text") @PopupText
    private IText infoText;

    @FindBy(xpath = "//button[contains(text(),'OK')]") @OkButton
    private IButton okButton;
    @FindBy(xpath = "//button[contains(text(),'Cancel')]") @CancelButton
    private IButton cancelButton;

    public String getExperimentId(){
        String info = waitText(" with id #");
        return info.substring(info.indexOf("#") + 1, info.indexOf("was"));
    }

}
