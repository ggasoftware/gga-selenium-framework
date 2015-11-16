package com.epam.jditests.tests.common.utils;

import com.epam.jditests.enums.Preconditions;
import com.ggasoftware.jdiuitest.core.interfaces.common.IText;
import com.ggasoftware.jdiuitest.core.utils.linqinterfaces.JFuncT;
import com.ggasoftware.jdiuitest.web.selenium.elements.base.Element;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

import static com.epam.jditests.pageobjects.EpamJDISite.isInState;
import static com.epam.jditests.tests.complex.CommonActionsData.checkText;

public class SimpleTextTests {

    private String _expected;
    private Preconditions _onPage;
    private JFuncT<Element> _element;

    public SimpleTextTests(String expected, Preconditions onPage, JFuncT<Element> element) {
        _expected = expected;
        _onPage = onPage;
        _element = element;
    }

    @BeforeMethod
    public void before(final Method method) {
        isInState(_onPage, method);
    }

    @Test
    public void getTextTest() throws Exception {
        checkText(((IText) _element.invoke())::getText, _expected);
    }

    @Test
    public void getValueTest() throws Exception {
        checkText(((IText) _element.invoke())::getValue, _expected);
    }
}
