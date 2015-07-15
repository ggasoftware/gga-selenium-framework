package com.epam.page_objects.mct.popup;

import com.epam.ui_test_framework.elements.composite.Popup;
import com.epam.ui_test_framework.elements.interfaces.common.IButton;
import com.epam.ui_test_framework.elements.interfaces.common.IText;
import com.epam.ui_test_framework.elements.page_objects.annotations.Function;
import org.openqa.selenium.support.FindBy;

/**
 * Created by Nataliia_Garkusha on 15-Jun-15.
 */
public class MCTInfo extends Popup {
    @FindBy(css = ".mct-dialog-text") @Function(value = "text")
    private IText infoText;

    @FindBy(xpath = "//button[contains(text(),'OK')]") @Function(value = "ok")
    private IButton okButton;
    @FindBy(xpath = "//button[contains(text(),'Cancel')]") @Function(value = "cancel")
    private IButton cancelButton;

    public String getExperimentId(){
        String info = waitText(" with id #");
        return info.substring(info.indexOf("#") + 1, info.indexOf("was"));
    }

}
