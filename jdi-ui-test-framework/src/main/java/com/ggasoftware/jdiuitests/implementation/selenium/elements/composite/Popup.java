package com.ggasoftware.jdiuitests.implementation.selenium.elements.composite;

import com.ggasoftware.jdiuitests.implementation.selenium.elements.common.Text;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.interfaces.complex.IPopup;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.pageobjects.annotations.functions.Functions;

/**
 * Created by Roman_Iovlev on 7/8/2015.
 */
public class Popup extends Text implements IPopup {

    @Override
    protected String getTextAction() {
        return getElement.getTextElement().getText();
    }

    public void ok() {
        getElement.getButton(Functions.OK_BUTTON).click();
    }

    public void cancel() {
        getElement.getButton(Functions.CANCEL_BUTTON).click();
    }

    public void close() {
        getElement.getButton(Functions.CLOSE_BUTTON).click();
    }

}
