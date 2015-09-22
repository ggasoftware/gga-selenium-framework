package com.epam.jdi_tests.tests.common;

import com.epam.jdi_tests.InitTests;
import com.epam.jdi_tests.enums.Preconditions;
import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.interfaces.common.ITextArea;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.lang.reflect.Method;

import static com.epam.jdi_tests.entities.User.DEFAULT_USER;
import static com.epam.jdi_tests.enums.Preconditions.CONTACT_PAGE_WITH_FILLED_FIELDS;
import static com.epam.jdi_tests.enums.Preconditions.SUPPORT_PAGE;
import static com.epam.jdi_tests.page_objects.EpamJDISite.contactFormPage;
import static com.epam.jdi_tests.page_objects.EpamJDISite.isInState;
import static com.epam.jdi_tests.tests.complex.CommonActionsData.*;
import static com.ggasoftware.jdi_ui_tests.implementation.testng.asserter.Assert.isTrue;

/**
 * Created by Roman_Iovlev on 9/15/2015.
 */
public class TextAreaTests extends InitTests {
    private ITextArea element() { return contactFormPage.description; }
    private Preconditions onPage = CONTACT_PAGE_WITH_FILLED_FIELDS;
    private String text = DEFAULT_USER.description;
    private String containsText = "escr";
    private String regExText = "Descr.*";

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
