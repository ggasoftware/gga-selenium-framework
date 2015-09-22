package com.epam.jdi_tests.tests.complex;

import com.epam.jdi_tests.InitTests;
import com.epam.jdi_tests.enums.Colors;
import com.epam.jdi_tests.enums.Preconditions;
import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.interfaces.complex.IDropDown;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;

import static com.epam.jdi_tests.enums.Colors.Blue;
import static com.epam.jdi_tests.enums.Colors.Red;
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
public class DropdownExpandedTests extends InitTests {
    private IDropDown<Colors> colors() { return metalsColorsPage.colors; }
    private Preconditions onPage = METALS_AND_COLORS_PAGE;

    @BeforeMethod
    public void before(Method method) throws IOException {
        isInState(onPage, method);
        colors().expand();
    }

    @Test
    public void selectStringTest() {
        colors().select("Red");
        checkAction("Colors: value changed to Red");
        colors().select("Blue");
        checkAction("Colors: value changed to Blue");
    }
    @Test
    public void selectIndexTest() {
        colors().select(2);
        checkAction("Colors: value changed to Red");
        colors().select(4);
        checkAction("Colors: value changed to Blue");
    }
    @Test
    public void selectEnumTest() {
        colors().select(Red);
        checkAction("Colors: value changed to Red");
        colors().select(Blue);
        checkAction("Colors: value changed to Blue");
    }

    private static final List<String> oddOptions = asList("Colors", "Red", "Green", "Blue", "Yellow");
    @Test
    public void getOptionsTest() {
        listEquals(colors().getOptions(), oddOptions);
    }
    @Test
    public void getNamesTest() {
        listEquals(colors().getNames(), oddOptions);
    }
    @Test
    public void getValuesTest() {
        listEquals(colors().getValues(), oddOptions);
    }
    @Test
    public void getOptionsAsTextTest() {
        areEquals(colors().getOptionsAsText(), "Colors, Red, Green, Blue, Yellow");
    }
    @Test
    public void setValueTest() {
        colors().setValue("Red");
        checkAction("Colors: value changed to Red");
        colors().setValue("Blue");
        checkAction("Colors: value changed to Blue");
    }
    @Test
    public void getNameTest() {
        areEquals(colors().getName(), "Colors");
    }

    // Fails
    @Test
    public void getSelectedTest() {
        checkActionThrowError(() -> colors().getSelected());
    }
    @Test
    public void getSelectedIndexTest() {
        checkActionThrowError(() -> colors().getSelectedIndex());
    }
    @Test
    public void isSelectedTest() {
        checkActionThrowError(() -> colors().isSelected("Red"));
    }
    @Test
    public void isSelectedIndexTest() {
        checkActionThrowError(() -> colors().isSelected(2));
    }
    @Test
    public void isSelectedEnumTest() {
        checkActionThrowError(() -> colors().isSelected(Red));
    }
    @Test
    public void waitSelectedTest() {
        checkActionThrowError(() -> colors().waitSelected("Red"));
    }
    @Test
    public void waitSelectedIndexTest() {
        checkActionThrowError(() -> colors().waitSelected(2));
    }
    @Test
    public void waitSelectedEnumTest() {
        checkActionThrowError(() -> colors().waitSelected(Red));
    }
    @Test
    public void getValueTest() {
        checkActionThrowError(() -> colors().getValue());
    }
}
