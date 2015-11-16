package com.ggasoftware.jdiuitest.web.selenium.elements.composite;

import com.ggasoftware.jdiuitest.web.selenium.elements.common.Text;
import com.ggasoftware.jdiuitest.core.interfaces.complex.IPopup;
import com.ggasoftware.jdiuitest.core.annotations.functions.Functions;

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
