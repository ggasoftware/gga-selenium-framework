package com.epam.ui_test_framework.elements.composite;

import com.epam.ui_test_framework.elements.interfaces.complex.IPopup;
import com.epam.ui_test_framework.elements.common.Button;
import org.openqa.selenium.By;

/**
 * Created by Roman_Iovlev on 7/8/2015.
 */
public class PopupForm<T> extends Form<T> implements IPopup {
    public PopupForm() { }
    public PopupForm(By byLocator) { super(byLocator); }

    @Override
    public void submit(T entity) {
        fill(entity);
        getButton("ok").click();
    }
    protected void buttonAction(Button button) {
        button.click();
    }
    public void ok()       { buttonAction(getButton("Ok"));}
    public void cancel()   { buttonAction(getButton("Cancel"));}
    public void close()         { buttonAction(getButton("Close"));}

    protected String getValueAction() { return ""; }
    public final String getValue() { return doJActionResult("Get value", this::getValueAction); }
}
