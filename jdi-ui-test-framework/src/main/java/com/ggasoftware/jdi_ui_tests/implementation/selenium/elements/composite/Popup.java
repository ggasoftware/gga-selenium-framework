package com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.composite;

import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.common.Text;
import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.interfaces.complex.IPopup;

import static com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.page_objects.annotations.functions.Functions.*;

/**
 * Created by Roman_Iovlev on 7/8/2015.
 */
public class Popup extends Text implements IPopup {

    @Override
    protected String getTextAction() { return getTextElement().getText(); }

    public void ok()       { getButton(OK_BUTTON).click();}
    public void cancel()   { getButton(CANCEL_BUTTON).click();}
    public void close()    { getButton(CLOSE_BUTTON).click();}

}
