package com.epam.jditests.tests.complex;

import com.epam.jditests.InitTests;
import com.epam.jditests.enums.Colors;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.interfaces.complex.IDropDown;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;

import static com.epam.jditests.enums.Colors.Blue;
import static com.epam.jditests.enums.Colors.Colors;
import static com.epam.jditests.enums.Preconditions.METALS_AND_COLORS_PAGE;
import static com.epam.jditests.pageobjects.EpamJDISite.isInState;
import static com.epam.jditests.pageobjects.EpamJDISite.metalsColorsPage;
import static com.epam.jditests.tests.complex.CommonActionsData.*;
import static com.ggasoftware.jdiuitests.implementation.testng.asserter.Assert.areEquals;
import static com.ggasoftware.jdiuitests.implementation.testng.asserter.Assert.listEquals;
import static java.util.Arrays.asList;

/**
 * Created by Roman_Iovlev on 9/15/2015.
 */
public class DropdownTests extends InitTests {
    private static final List<String> oddOptions = asList("Colors", "Red", "Green", "Blue", "Yellow");

    private IDropDown<Colors> colors() {
        return metalsColorsPage.colors;
    }

    @BeforeMethod
    public void before(Method method) throws IOException {
        isInState(METALS_AND_COLORS_PAGE, method);
    }

    @Test
    public void selectStringTest() {
        colors().select("Blue");
        checkAction("Colors: value changed to Blue");
    }

    @Test
    public void selectIndexTest() {
        colors().select(4);
        checkAction("Colors: value changed to Blue");
    }

    @Test
    public void selectEnumTest() {
        colors().select(Blue);
        checkAction("Colors: value changed to Blue");
    }

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
        areEquals(colors().getSelected(), "Colors");
    }

    @Test
    public void getSelectedIndexTest() {
        checkActionThrowError(() -> colors().getSelectedIndex(), noElementsMessage); // isDisplayed not defined
    }

    @Test
    public void isSelectedTest() {
        areEquals(colors().isSelected("Colors"), true);
    }

    @Test
    public void isSelectedEnumTest() {
        areEquals(colors().isSelected(Colors), true);
    }

    @Test
    public void waitSelectedTest() {
        areEquals(colors().waitSelected("Colors"), true);
    }

    @Test
    public void waitSelectedEnumTest() {
        areEquals(colors().waitSelected(Colors), true);
    }

    @Test
    public void getValueTest() {
        areEquals(colors().getValue(), "Colors");
    }
}
