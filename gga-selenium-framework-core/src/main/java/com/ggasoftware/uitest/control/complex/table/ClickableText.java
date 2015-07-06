package com.ggasoftware.uitest.control.complex.table;

import com.ggasoftware.uitest.control.base.Element;
import com.ggasoftware.uitest.control.interfaces.IClickable;
import com.ggasoftware.uitest.control.interfaces.IText;
import com.ggasoftware.uitest.utils.linqInterfaces.JFuncT;
import org.openqa.selenium.By;

/**
 * Created by Roman_Iovlev on 6/2/2015.
 */
public class ClickableText<P> extends Element<P> implements IClickable<P>, IText, IHaveValue {
    public JFuncT<String> getValueAction =  () -> getWebElement().getText();
    public String getValue() {
        return doJActionResult("Get Value", getValueAction);
    }

    public ClickableText() {}
    public ClickableText(String name, By locator) { super(name, locator); }
}
