package com.epam.ui_test_framework.elements.common;

import com.epam.ui_test_framework.elements.base.Element;
import com.epam.ui_test_framework.elements.interfaces.simple.IText;
import org.openqa.selenium.By;

import static com.epam.ui_test_framework.utils.common.Timer.getByCondition;

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
    public final String waitText(String text) { return doJActionResult("Wait text",
            () -> getByCondition(this::getTextAction, t -> t.contains(text)));
    }
    public final String waitMatchText(String regEx) { return doJActionResult("Wait text",
            () -> getByCondition(this::getTextAction, t -> t.matches(regEx)));
    }
}
