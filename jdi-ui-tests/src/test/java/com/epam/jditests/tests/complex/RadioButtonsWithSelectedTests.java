package com.epam.jditests.tests.complex;

import com.epam.jditests.InitTests;
import com.epam.jditests.enums.Odds;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.complex.Selector;
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
import static com.ggasoftware.jdiuitests.implementation.testng.asserter.Assert.*;
import static java.util.Arrays.asList;

/**
 * Created by Roman_Iovlev on 9/15/2015.
 */
public class RadioButtonsWithSelectedTests extends InitTests {
    private static final List<String> oddOptions = asList("1", "3", "5", "7");

    private Selector<Odds> odds() {
        return metalsColorsPage.summary.oddsRWithSelected;
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
        areEquals(odds().getName(), "Odds RWith Selected");
    }

    @Test
    public void getSelectedTest() {
        odds().select(SEVEN);
        areEquals(odds().getSelected(), "7");
    }

    @Test
    public void getSelectedIndexTest() {
        odds().select(SEVEN);
        areEquals(odds().getSelectedIndex(), 4);
    }

    @Test
    public void isSelectedTest() {
        odds().select(SEVEN);
        isTrue(odds().isSelected("7"));
    }

    @Test
    public void isSelectedEnumTest() {
        odds().select(SEVEN);
        isTrue(odds().isSelected(SEVEN));
    }

    @Test
    public void waitSelectedTest() {
        odds().select(SEVEN);
        isTrue(odds().waitSelected("7"));
    }

    @Test
    public void waitSelectedEnumTest() {
        odds().select(SEVEN);
        isTrue(odds().waitSelected(SEVEN));
    }

    @Test
    public void wait3SelectedTest() {
        runParallel(() -> odds().select(SEVEN));
        isTrue(odds().waitSelected("7"));
        isTrue(getTimePassed() > waitTimeOut);
        isTrue(odds().isSelected(SEVEN));
    }

    @Test
    public void wait3SelectedEnumTest() {
        runParallel(() -> odds().select(SEVEN));
        isTrue(odds().waitSelected(SEVEN));
        isTrue(getTimePassed() > waitTimeOut);
        isTrue(odds().isSelected(SEVEN));
    }

    @Test
    public void getValueTest() {
        odds().select(SEVEN);
        areEquals(odds().getValue(), "7");
    }
}
