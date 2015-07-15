package com.epam.ui_test_framework.elements.composite;

import com.epam.ui_test_framework.elements.interfaces.complex.IPopup;
import com.epam.ui_test_framework.elements.common.Button;
import com.epam.ui_test_framework.elements.common.Text;
import org.openqa.selenium.By;

/**
 * Created by Roman_Iovlev on 7/8/2015.
 */
public class Popup extends Text implements IPopup {
    public Popup() { }
    public Popup(By byLocator) { super(byLocator); }

    @Override
    protected String getTextAction() { return getTextElement().getText(); }
    protected void buttonAction(Button button) {
        button.click();
    }

    public void clickOk()       { buttonAction(getButton("Ok"));}
    public void clickCancel()   { buttonAction(getButton("Cancel"));}
    public void close()         { buttonAction(getButton("Close"));}
}
