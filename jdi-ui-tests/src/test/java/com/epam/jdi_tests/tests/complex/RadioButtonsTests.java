package com.epam.jdi_tests.tests.complex;

import com.epam.jdi_tests.InitTests;
import com.epam.jdi_tests.enums.Odds;
import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.complex.Selector;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;

import static com.epam.jdi_tests.enums.Odds.SEVEN;
import static com.epam.jdi_tests.enums.Preconditions.METALS_AND_COLORS_PAGE;
import static com.epam.jdi_tests.page_objects.EpamJDISite.isInState;
import static com.epam.jdi_tests.page_objects.EpamJDISite.metalsColorsPage;
import static com.epam.jdi_tests.tests.complex.CommonActionsData.checkAction;
import static com.epam.jdi_tests.tests.complex.CommonActionsData.checkActionThrowError;
import static com.epam.jdi_tests.tests.complex.CommonActionsData.noElementsMessage;
import static com.ggasoftware.jdi_ui_tests.implementation.testng.asserter.Assert.areEquals;
import static com.ggasoftware.jdi_ui_tests.implementation.testng.asserter.Assert.listEquals;
import static java.util.Arrays.asList;

/**
 * Created by Roman_Iovlev on 9/15/2015.
 */
public class RadioButtonsTests extends InitTests {
    private Selector<Odds> odds() { return metalsColorsPage.summary.oddsR; }

    @BeforeMethod
    public void before(Method method) throws IOException {
        isInState(METALS_AND_COLORS_PAGE, method);
    }

    @Test
    public void selectStringTest() {
        odds().select("7");
        checkAction("Summary (Odd): value changed to 7");
    }
    @Test
    public void selectIndexTest() {
        odds().select(4);
        checkAction("Summary (Odd): value changed to 7");
    }
    @Test
    public void selectEnumTest() {
        odds().select(SEVEN);
        checkAction("Summary (Odd): value changed to 7");
    }

    private static final List<String> oddOptions = asList("1", "3", "5", "7");
    @Test
    public void getOptionsTest() {
        listEquals(odds().getOptions(), oddOptions);
    }
    @Test
    public void getNamesTest() {
        listEquals(odds().getNames(), oddOptions);
    }
    @Test
    public void getValuesTest() {
        listEquals(odds().getValues(), oddOptions);
    }
    @Test
    public void getOptionsAsTextTest() {
        areEquals(odds().getOptionsAsText(), "1, 3, 5, 7");
    }
    @Test
    public void setValueTest() {
        odds().setValue("7");
        checkAction("Summary (Odd): value changed to 7");
    }
    @Test
    public void getNameTest() {
        areEquals(odds().getName(), "OddsR");
    }

    // Fails
    @Test
    public void getSelectedTest() {
        checkActionThrowError(() -> odds().getSelected(), noElementsMessage); // isDisplayed not defined
    }
    @Test
    public void getSelectedIndexTest() {
        checkActionThrowError(() -> odds().getSelectedIndex(), noElementsMessage); // isDisplayed not defined
    }
    @Test
    public void isSelectedTest() {
        checkActionThrowError(() -> odds().isSelected("7"), noElementsMessage); // isDisplayed not defined
    }
    @Test
    public void isSelectedEnumTest() {
        checkActionThrowError(() -> odds().isSelected(SEVEN), noElementsMessage); // isDisplayed not defined
    }
    @Test
    public void waitSelectedTest() {
        checkActionThrowError(() -> odds().waitSelected("7"), noElementsMessage); // isDisplayed not defined
    }
    @Test
    public void waitSelectedEnumTest() {
        checkActionThrowError(() -> odds().waitSelected(SEVEN), noElementsMessage); // isDisplayed not defined
    }
    @Test
    public void getValueTest() {
        checkActionThrowError(() -> odds().getValue(), noElementsMessage); // isDisplayed not defined
    }

}
