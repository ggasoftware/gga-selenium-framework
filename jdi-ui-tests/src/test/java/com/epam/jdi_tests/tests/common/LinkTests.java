package com.epam.jdi_tests.tests.common;

import com.epam.jdi_tests.InitTests;
import com.epam.jdi_tests.enums.Preconditions;
import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.interfaces.common.ILink;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.lang.reflect.Method;

import static com.epam.jdi_tests.enums.Preconditions.HOME_PAGE;
import static com.epam.jdi_tests.enums.Preconditions.SUPPORT_PAGE;
import static com.epam.jdi_tests.page_objects.EpamJDISite.*;
import static com.epam.jdi_tests.tests.complex.CommonActionsData.*;
import static com.ggasoftware.jdi_ui_tests.implementation.testng.asserter.Assert.isTrue;

/**
 * Created by Roman_Iovlev on 9/15/2015.
 */
public class LinkTests extends InitTests {
    private ILink link() { return footer.about; }
    private Preconditions onPage = HOME_PAGE;
    private String text = "About";
    private String containsText = "bout";
    private String regExText = "Abou.";

    @BeforeMethod
    public void before(Method method) throws IOException {
        isInState(onPage, method);
    }
    @Test
    public void textWaitTestWithButton() {
        link().click();
        supportPage.checkOpened();
    }
    @Test
    public void getTextTest() {
        checText(link()::getText, text);
    }
    @Test
    public void getValueTest() {
        checText(link()::getValue, text);
    }
    @Test
    public void waitTextTest() {
        checText(() -> link().waitText(containsText), text);
    }

    @Test
    public void waitTextMatchTest() {
        checText(() -> link().waitMatchText(regExText), text);
    }
    @Test
    public void wait3TextTest() {
        isInState(SUPPORT_PAGE);
        runParallel(onPage::open);
        checText(() -> link().waitText(containsText), text);
        isTrue(timer.timePassedInMSec() > waitTimeOut);
    }
    @Test
    public void wait3TextMatchTest() {
        isInState(SUPPORT_PAGE);
        runParallel(onPage::open);
        checText(() -> link().waitMatchText(regExText), text);
        isTrue(timer.timePassedInMSec() > waitTimeOut);
    }

}
