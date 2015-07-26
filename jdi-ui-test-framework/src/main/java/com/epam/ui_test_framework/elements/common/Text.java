package com.epam.ui_test_framework.elements.common;

import com.epam.ui_test_framework.elements.base.Element;
import com.epam.ui_test_framework.elements.base.HaveValue;
import com.epam.ui_test_framework.elements.interfaces.base.IHaveValue;
import com.epam.ui_test_framework.elements.interfaces.common.IText;
import org.openqa.selenium.By;

import static com.epam.ui_test_framework.utils.common.Timer.getByCondition;
import static java.lang.String.format;

/**
 * Created by Roman_Iovlev on 7/6/2015.
 */
public class Text extends Element implements IText {
    public Text() { }
    public Text(By byLocator) { super(byLocator); }

    protected IHaveValue haveValue() { return new HaveValue(this::getTextAction); }
    public final String getValue() { return haveValue().getValue(); }

    protected String getTextAction() { return getWebElement().getText(); }
    public final String getText() { return doJActionResult("Get text", this::getTextAction); }
    public final String waitText(String text) { return doJActionResult(format("Wait text contains '%s'", text),
            () -> getByCondition(this::getTextAction, t -> t.contains(text)));
    }
    public final String waitMatchText(String regEx) { return doJActionResult(format("Wait text match regex '%s'", regEx),
            () -> getByCondition(this::getTextAction, t -> t.matches(regEx)));
    }
}
