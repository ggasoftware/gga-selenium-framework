package com.ggasoftware.uitest.control.new_controls.composite;

import com.ggasoftware.uitest.control.Button;
import com.ggasoftware.uitest.control.base.annotations.functions.Functions;
import com.ggasoftware.uitest.control.interfaces.complex.IPopup;
import com.ggasoftware.uitest.control.new_controls.common.Text;
import org.openqa.selenium.By;

import static com.ggasoftware.uitest.control.base.annotations.functions.Functions.*;

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

    public void ok()       { buttonAction(getButton(OK_BUTTON));}
    public void cancel()   { buttonAction(getButton(CANCEL_BUTTON));}
    public void close()    { buttonAction(getButton(CLOSE_BUTTON));}

}
