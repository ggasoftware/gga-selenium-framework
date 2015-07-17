package com.epam.ui_test_framework.elements.composite;

import com.epam.ui_test_framework.elements.interfaces.complex.IPopup;
import com.epam.ui_test_framework.elements.common.Button;
import com.epam.ui_test_framework.elements.common.Text;
import org.openqa.selenium.By;

import java.lang.reflect.Field;

import static com.epam.ui_test_framework.utils.common.LinqUtils.first;
import static com.epam.ui_test_framework.utils.common.ReflectionUtils.getFieldValue;
import static com.epam.ui_test_framework.utils.settings.FrameworkSettings.asserter;
import static java.lang.String.format;

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

    public void ok()       { buttonAction(getButton("Ok"));}
    public void cancel()   { buttonAction(getButton("Cancel"));}
    public void close()    { buttonAction(getButton("Close"));}

}
