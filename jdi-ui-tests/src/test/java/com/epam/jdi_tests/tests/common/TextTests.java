package com.epam.jdi_tests.tests.common;

import com.epam.jdi_tests.InitTests;
import com.epam.jdi_tests.enums.Preconditions;
import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.interfaces.common.IText;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.lang.reflect.Method;

import static com.epam.jdi_tests.enums.Preconditions.HOME_PAGE;
import static com.epam.jdi_tests.enums.Preconditions.SUPPORT_PAGE;
import static com.epam.jdi_tests.page_objects.EpamJDISite.homePage;
import static com.epam.jdi_tests.page_objects.EpamJDISite.isInState;
import static com.epam.jdi_tests.tests.complex.CommonActionsData.*;
import static com.ggasoftware.jdi_ui_tests.implementation.testng.asserter.Assert.isTrue;

/**
 * Created by Roman_Iovlev on 9/15/2015.
 */
public class TextTests extends InitTests {
    private IText element() { return homePage.text; }
    private Preconditions onPage = HOME_PAGE;
    private String text = "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.".toUpperCase();
    private String containsText = "Lorem ipsum dolor sit amet".toUpperCase();
    private String regExText = ".* ipsum dolor sit amet.*".toUpperCase();

    @BeforeMethod
    public void before(Method method) throws IOException {
        isInState(onPage, method);
    }
    @Test
    public void getTextTest() {
        checText(element()::getText, text);
    }
    @Test
    public void getValueTest() {
        checText(element()::getValue, text);
    }
    @Test
    public void waitTextTest() {
        checText(() -> element().waitText(containsText), text);
    }

    @Test
    public void waitTextMatchTest() {
        checText(() -> element().waitMatchText(regExText), text);
    }
    @Test
    public void wait3TextTest() {
        isInState(SUPPORT_PAGE);
        runParallel(onPage::open);
        checText(() -> element().waitText(containsText), text);
        isTrue(timer.timePassedInMSec() > waitTimeOut);
    }
    @Test
    public void wait3TextMatchTest() {
        isInState(SUPPORT_PAGE);
        runParallel(onPage::open);
        checText(() -> element().waitMatchText(regExText), text);
        isTrue(timer.timePassedInMSec() > waitTimeOut);
    }
}

