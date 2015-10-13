package com.epam.jditests.tests.common;

import com.epam.jditests.InitTests;
import com.epam.jditests.enums.Preconditions;
import com.ggasoftware.jdiuitests.core.utils.linqInterfaces.JFuncT;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.interfaces.common.IText;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

import static com.epam.jditests.enums.Preconditions.HOME_PAGE;
import static com.epam.jditests.pageobjects.EpamJDISite.homePage;
import static com.epam.jditests.pageobjects.EpamJDISite.isInState;
import static com.epam.jditests.tests.complex.CommonActionsData.checkText;

/**
 * Created by Roman_Iovlev on 10/13/2015.
 */
public class SimpleTests extends InitTests {

    private JFuncT<IText> text = () -> homePage.text;
    public String expected = ("Lorem ipsum dolor sit amet, consectetur adipisicing elit,"
            + " sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."
            + " Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris"
            + " nisi ut aliquip ex ea commodo consequat Duis aute irure dolor in"
            + " reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.").toUpperCase();
    public Preconditions onPage = HOME_PAGE;
    public SimpleTests() { }
    public SimpleTests(JFuncT<IText> text, Preconditions onPage, String expected) {
        this.text = text;
        this.expected = expected;
        this.onPage = onPage;
    }

    @BeforeMethod
    public void before(final Method method) {
        isInState(onPage, method);
    }

    @Test
    public void getTextTest() throws Exception {
        checkText(text.invoke()::getText, expected);
    }
}
