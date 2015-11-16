package com.epam.jditests.tests.complex;

import com.epam.jditests.InitTests;
import com.epam.jditests.enums.Odds;
import com.ggasoftware.jdiuitest.web.selenium.elements.complex.Selector;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;

import static com.epam.jditests.enums.Odds.SEVEN;
import static com.epam.jditests.enums.Preconditions.METALS_AND_COLORS_PAGE;
import static com.epam.jditests.pageobjects.EpamJDISite.isInState;
import static com.epam.jditests.pageobjects.EpamJDISite.metalsColorsPage;
import static com.epam.jditests.tests.complex.CommonActionsData.*;
import static com.ggasoftware.jdiuitest.web.testng.asserter.Assert.areEquals;
import static com.ggasoftware.jdiuitest.web.testng.asserter.Assert.listEquals;
import static java.util.Arrays.asList;

/**
 * Created by Roman_Iovlev on 9/15/2015.
 */
public class SelectorTests extends InitTests {
    private static final List<String> oddOptions = asList("1", "3", "5", "7");

    private Selector<Odds> odds() {
        return metalsColorsPage.summary.odds;
    }

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
        areEquals(odds().getName(), "Odds");
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
