package com.epam.jditests.tests.complex;

import com.epam.jditests.InitTests;
import com.epam.jditests.enums.Metals;
import com.ggasoftware.jdiuitest.core.interfaces.complex.IComboBox;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.util.List;

import static com.epam.jditests.enums.Metals.Gold;
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
public class ComboBoxTests extends InitTests {
    private static final List<String> oddOptions = asList("Col", "Gold", "Silver", "Bronze", "Selen");

    private IComboBox<Metals> metals() {
        return metalsColorsPage.comboBox;
    }

    @BeforeMethod
    public void before(Method method) {
        isInState(METALS_AND_COLORS_PAGE, method);
    }

    @Test
    public void selectStringTest() {
        metals().select("Gold");
        checkAction("Metals: value changed to Gold");
    }

    @Test
    public void selectIndexTest() {
        metals().select(3);
        checkAction("Metals: value changed to Silver");
    }

    @Test
    public void selectEnumTest() {
        metals().select(Gold);
        checkAction("Metals: value changed to Gold");
    }

    @Test
    public void getOptionsTest() {
        listEquals(metals().getOptions(), oddOptions);
    }

    @Test
    public void getNamesTest() {
        listEquals(metals().getNames(), oddOptions);
    }

    @Test
    public void getValuesTest() {
        listEquals(metals().getValues(), oddOptions);
    }

    @Test
    public void getOptionsAsTextTest() {
        areEquals(metals().getOptionsAsText(), "Col, Gold, Silver, Bronze, Selen");
    }

    @Test
    public void setValueTest() {
        metals().setValue("Blue");
        checkAction("Colors: value changed to Blue");
    }

    // Fails
    @Test
    public void getSelectedTest() {
        metals().select("Gold");
        areEquals(metals().getSelected(), "Gold");
    }

    @Test
    public void getSelectedIndexTest() {
        checkActionThrowError(() -> metals().getSelectedIndex(), noElementsMessage); // isDisplayed not defined
    }

    @Test
    public void isSelectedTest() {
        areEquals(metals().isSelected("Col"), true);
    }

    @Test
    public void isSelectedEnumTest() {
        areEquals(metals().isSelected(Metals.Col), true);
    }

    @Test
    public void waitSelectedTest() {
        areEquals(metals().waitSelected("Col"), true);
    }

    @Test
    public void waitSelectedEnumTest() {
        areEquals(metals().waitSelected(Metals.Col), true);
    }

    @Test
    public void getValueTest() {
        areEquals(metals().getValue(), "Col");
    }
}
