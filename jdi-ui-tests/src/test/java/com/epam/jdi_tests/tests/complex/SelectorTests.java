package com.epam.jdi_tests.tests.complex;

import com.epam.jdi_tests.InitTests;
import com.epam.jdi_tests.enums.Odds;
import com.epam.jdi_tests.enums.Preconditions;
import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.complex.Selector;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;

import static com.epam.jdi_tests.enums.Odds.SEVEN;
import static com.epam.jdi_tests.enums.Odds.THREE;
import static com.epam.jdi_tests.enums.Preconditions.METALS_AND_COLORS_PAGE;
import static com.epam.jdi_tests.page_objects.EpamJDISite.isInState;
import static com.epam.jdi_tests.page_objects.EpamJDISite.metalsColorsPage;
import static com.epam.jdi_tests.tests.complex.CommonActionsData.checkAction;
import static com.epam.jdi_tests.tests.complex.CommonActionsData.checkActionThrowError;
import static com.ggasoftware.jdi_ui_tests.implementation.testng.asserter.Assert.areEquals;
import static com.ggasoftware.jdi_ui_tests.implementation.testng.asserter.Assert.listEquals;
import static java.util.Arrays.asList;

/**
 * Created by Roman_Iovlev on 9/15/2015.
 */
public class SelectorTests extends InitTests {
    private Selector<Odds> odds() { return metalsColorsPage.summary.odds; }
    private Preconditions onPage = METALS_AND_COLORS_PAGE;

    @BeforeMethod
    public void before(Method method) throws IOException {
        isInState(onPage, method);
    }

    @Test
    public void selectStringTest() {
        odds().select("7");
        checkAction("Summary (Odd): value changed to 7");
        odds().select("3");
        checkAction("Summary (Odd): value changed to 3");
    }
    @Test
    public void selectIndexTest() {
        odds().select(4);
        checkAction("Summary (Odd): value changed to 7");
        odds().select(2);
        checkAction("Summary (Odd): value changed to 3");
    }
    @Test
    public void selectEnumTest() {
        odds().select(SEVEN);
        checkAction("Summary (Odd): value changed to 7");
        odds().select(THREE);
        checkAction("Summary (Odd): value changed to 3");
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
        odds().setValue("3");
        checkAction("Summary (Odd): value changed to 3");
    }
    @Test
    public void getNameTest() {
        areEquals(odds().getName(), "Odds");
    }

    // Fails
    @Test
    public void getSelectedTest() {
        checkActionThrowError(() -> odds().getSelected());
    }
    @Test
    public void getSelectedIndexTest() {
        checkActionThrowError(() -> odds().getSelectedIndex());
    }
    @Test
    public void isSelectedTest() {
        checkActionThrowError(() -> odds().isSelected("7"));
    }
    @Test
    public void isSelectedIndexTest() {
        checkActionThrowError(() -> odds().isSelected(4));
    }
    @Test
    public void isSelectedEnumTest() {
        checkActionThrowError(() -> odds().isSelected(SEVEN));
    }
    @Test
    public void waitSelectedTest() {
        checkActionThrowError(() -> odds().waitSelected("7"));
    }
    @Test
    public void waitSelectedIndexTest() {
        checkActionThrowError(() -> odds().waitSelected(4));
    }
    @Test
    public void waitSelectedEnumTest() {
        checkActionThrowError(() -> odds().waitSelected(SEVEN));
    }
    @Test
    public void getValueTest() {
        checkActionThrowError(() -> odds().getValue());
    }
}
