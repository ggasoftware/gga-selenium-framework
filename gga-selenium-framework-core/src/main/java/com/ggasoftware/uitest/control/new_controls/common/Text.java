package com.ggasoftware.uitest.control.new_controls.common;

import com.ggasoftware.uitest.control.Element;
import com.ggasoftware.uitest.control.interfaces.common.IText;
import org.openqa.selenium.By;

import static com.ggasoftware.uitest.utils.Timer.getByCondition;
import static java.lang.String.format;

/**
 * Created by Roman_Iovlev on 7/6/2015.
 */
public class Text<P> extends Element<P> implements IText<P> {
    public Text() { }
    public Text(By byLocator) { super(byLocator); }
    public Text(String name, String locator, P parentPanel) {
        super(name, locator, parentPanel);
    }

    protected String getTextAction() { return getWebElement().getText(); }
    protected String getValueAction() { return getTextAction(); }

    public final String getValue() { return doJActionResult("Get value", this::getValueAction); }
    public final String getText() { return doJActionResult("Get text", this::getTextAction); }
    public final String waitText(String text) { return doJActionResult(format("Wait text contains '%s'", text),
            () -> getByCondition(this::getTextAction, t -> t.contains(text)));
    }
    public final String waitMatchText(String regEx) { return doJActionResult(format("Wait text match regex '%s'", regEx),
            () -> getByCondition(this::getTextAction, t -> t.matches(regEx)));
    }
}
