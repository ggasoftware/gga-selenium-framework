package com.ggasoftware.uitest.control.simple;

import com.ggasoftware.uitest.control.base.Element;
import com.ggasoftware.uitest.control.interfaces.IText;
import org.openqa.selenium.By;

/**
 * Created by Roman_Iovlev on 7/6/2015.
 */
public class Text extends Element implements IText {
    public Text() { }
    public Text(By byLocator) { super(byLocator); }

    protected String getTextAction() { return getWebElement().getText(); }
    protected String getValueAction() { return getText(); }

    public final String getValue() { return doJActionResult("Get value", this::getValueAction); }
    public final String getText() { return doJActionResult("Get text", this::getTextAction); }
}
