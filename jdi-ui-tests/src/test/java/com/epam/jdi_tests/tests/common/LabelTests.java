package com.epam.jdi_tests.tests.common;

import com.epam.jdi_tests.InitTests;
import com.epam.jdi_tests.enums.Preconditions;
import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.interfaces.common.ILabel;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.lang.reflect.Method;

import static com.epam.jdi_tests.enums.Preconditions.METALS_AND_COLORS_PAGE;
import static com.epam.jdi_tests.enums.Preconditions.SUPPORT_PAGE;
import static com.epam.jdi_tests.page_objects.EpamJDISite.isInState;
import static com.epam.jdi_tests.page_objects.EpamJDISite.metalsColorsPage;
import static com.epam.jdi_tests.tests.complex.CommonActionsData.*;
import static com.ggasoftware.jdi_ui_tests.implementation.testng.asserter.Assert.isTrue;

/**
 * Created by Roman_Iovlev on 9/15/2015.
 */
public class LabelTests extends InitTests {
    private ILabel label() { return metalsColorsPage.calculate; }
    private Preconditions onPage = METALS_AND_COLORS_PAGE;
    private String text = "CALCULATE";
    private String containsText = "CUL";
    private String regExText = "C.*C.LATE";

    @BeforeMethod
    public void before(Method method) throws IOException {
        isInState(onPage, method);
    }

    @Test
    public void textWaitTestWithButton() {
        label().click();
        checkCalculate("Summary: 3");
    }
    @Test
    public void getTextTest() {
        checText(label()::getText, text);
    }
    @Test
    public void getValueTest() {
        checText(label()::getValue, text);
    }
    @Test
    public void waitTextTest() {
        checText(() -> label().waitText(containsText), text);
    }

    @Test
    public void waitTextMatchTest() {
        checText(() -> label().waitMatchText(regExText), text);
    }
    @Test
    public void wait3TextTest() {
        isInState(SUPPORT_PAGE);
        runParallel(onPage::open);
        checText(() -> label().waitText(containsText), text);
        isTrue(timer.timePassedInMSec() > waitTimeOut);
    }
    @Test
    public void wait3TextMatchTest() {
        isInState(SUPPORT_PAGE);
        runParallel(onPage::open);
        checText(() -> label().waitMatchText(regExText), text);
        isTrue(timer.timePassedInMSec() > waitTimeOut);
    }
}
