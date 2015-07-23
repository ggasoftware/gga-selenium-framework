package com.ggasoftware.uitest.control.new_controls.composite;

import com.ggasoftware.uitest.control.Button;
import com.ggasoftware.uitest.control.base.map.MapArray;
import com.ggasoftware.uitest.control.interfaces.complex.IPopup;
import org.openqa.selenium.By;

import static com.ggasoftware.uitest.control.base.annotations.functions.Functions.*;

/**
 * Created by Roman_Iovlev on 7/8/2015.
 */
public class PopupForm<T, P> extends Form<T, P> implements IPopup {
    public PopupForm() { }
    public PopupForm(By byLocator) { super(byLocator); }

    @Override
    public void submit(MapArray<String, String> objStrings) {
        fill(objStrings);
        ok();
    }
    protected void buttonAction(Button button) { button.click(); }
    public void ok()       { buttonAction(getButton(OK_BUTTON));}
    public void cancel()   { buttonAction(getButton(CANCEL_BUTTON));}
    public void close()         { buttonAction(getButton(CLOSE_BUTTON));}
}
