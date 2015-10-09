package com.epam.jdi_tests.tests.complex;

import com.epam.jdi_tests.InitTests;

/**
 * Created by Roman_Iovlev on 9/15/2015.
 */
public class ComboBoxTests extends InitTests {
    // TODO failed
    /*
    private IComboBox<Metals> metals() { return metalsColorsPage.comboBox; }

    @BeforeMethod
    public void before(Method method) throws IOException {
        isInState(METALS_AND_COLORS_PAGE, method);
    }

    @Test
    public void selectStringTest() {
        metals().select("Gold");
        checkAction("Col: value changed to Gold");
    }
    @Test
    public void selectIndexTest() {
        metals().select(3);
        checkAction("Col: value changed to Silver");
    }
    @Test
    public void selectEnumTest() {
        metals().select(Gold);
        checkAction("Col: value changed to Gold");
    }

    private static final List<String> oddOptions = asList("Col", "Gold", "Silver", "Bronze", "Selen");
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
    @Test
    public void getNameTest() {
        areEquals(metals().getName(), "Col");
    }

    // Fails
    @Test
    public void getSelectedTest() {
        areEquals(metals().getSelected(), "Col");
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
    */
}
