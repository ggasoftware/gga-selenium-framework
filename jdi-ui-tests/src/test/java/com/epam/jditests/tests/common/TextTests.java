package com.epam.jditests.tests.common;

import com.epam.jditests.InitTests;
import com.epam.jditests.enums.Preconditions;
import com.epam.jditests.tests.common.utils.*;
import com.ggasoftware.jdiuitests.core.utils.linqInterfaces.JFuncT;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.interfaces.base.IElement;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.interfaces.common.IText;
import org.testng.annotations.Factory;

import static com.epam.jditests.enums.Preconditions.HOME_PAGE;
import static com.epam.jditests.pageobjects.EpamJDISite.homePage;

public class TextTests extends InitTests {
    private Preconditions _onPage = null;

    public static final String TEXT = ("Lorem ipsum dolor sit amet, consectetur adipisicing elit,"
            + " sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."
            + " Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris"
            + " nisi ut aliquip ex ea commodo consequat Duis aute irure dolor in"
            + " reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.").toUpperCase();

    public TextTests() {
        _onPage = HOME_PAGE;
    }

    JFuncT<IElement> get = () -> homePage.text;

    @Factory
    public Object[] factory() {
        return new Object[]{
                new SimpleTextTests(TEXT, _onPage, get),
                new MatchTextTests(TEXT, ".* IPSUM DOLOR SIT AMET.*", _onPage, get),
                new ContainsTextTests(TEXT, "ENIM AD MINIM VENIAM, QUIS NOSTRUD", _onPage, get),
                new AttributeTests("testAttribute", "testValue", _onPage, get)
        };
    }
}