package com.ggasoftware.uitest.control.simple;

import com.ggasoftware.uitest.control.base.HaveValue;
import com.ggasoftware.uitest.control.interfaces.IText;
import org.openqa.selenium.By;

/**
 * Created by Roman_Iovlev on 7/6/2015.
 */
public class Text extends HaveValue implements IText {
    public Text() { }
    public Text(By byLocator) { super(byLocator); }
    public Text(String name, By byLocator) { super(name, byLocator); }

    protected String getTextAction() throws Exception { return getWebElement().getText(); }
    public final String getText() throws Exception { return doJActionResult("Get text", this::getTextAction); }

    @Override
    protected String getValueAction() throws Exception {
        return getText();
    }
}
