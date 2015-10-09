package com.epam.jdi_tests.tests.complex;

import com.epam.jdi_tests.InitTests;
import com.epam.jdi_tests.enums.Nature;
import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.interfaces.complex.ICheckList;
import org.openqa.selenium.By;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static com.epam.jdi_tests.enums.Nature.*;
import static com.epam.jdi_tests.enums.Preconditions.METALS_AND_COLORS_PAGE;
import static com.epam.jdi_tests.page_objects.EpamJDISite.*;
import static com.epam.jdi_tests.tests.complex.CommonActionsData.checkAction;
import static com.ggasoftware.jdi_ui_tests.core.settings.JDISettings.getDriver;
import static com.ggasoftware.jdi_ui_tests.core.utils.common.LinqUtils.first;
import static com.ggasoftware.jdi_ui_tests.implementation.testng.asserter.Assert.*;
import static com.ggasoftware.jdi_ui_tests.implementation.testng.asserter.ScreenAssert.listEquals;
import static java.util.Arrays.asList;

/**
 * Created by Roman_Iovlev on 9/15/2015.
 */
public class ChecklistTests extends InitTests {
    private ICheckList<Nature> nature() { return metalsColorsPage.nature; }

    @BeforeMethod
    public void before(Method method) throws IOException {
        isInState(METALS_AND_COLORS_PAGE);
    }
    private void checkAllChecked() {
        assertTrue(first(getDriver().findElements(By.cssSelector("#elements-checklist input")),
                el -> el.getAttribute("checked") != null) != null);
    }
    @Test
    public void selectStringTest() {
        nature().select("Fire");
        checkAction("Fire: condition changed to true");
    }
    @Test
    public void selectIndexTest() {
        nature().select(4);
        checkAction("Fire: condition changed to true");
    }
    @Test
    public void selectEnumTest() {
        nature().select(FIRE);
        checkAction("Fire: condition changed to true");
    }
    @Test
    public void select2StringTest() {
        nature().select("Water", "Fire");
        checkAction("Fire: condition changed to true");
        assertContains(() -> (String) actionsLog.getTextList().get(1), "Water: condition changed to true");

    }
    @Test
    public void select2IndexTest() {
        nature().select(1, 4);
        checkAction("Fire: condition changed to true");
        assertContains(() -> (String) actionsLog.getTextList().get(1), "Water: condition changed to true");
    }
    @Test
    public void select2EnumTest() {
        nature().select(WATER, FIRE);
        checkAction("Fire: condition changed to true");
        assertContains(() -> (String) actionsLog.getTextList().get(1), "Water: condition changed to true");
    }

    @Test
    public void checkStringTest() {
        nature().check("Fire");
        checkAction("Fire: condition changed to true");
    }
    @Test
    public void checkIndexTest() {
        nature().check(4);
        checkAction("Fire: condition changed to true");
    }
    @Test
    public void checkEnumTest() {
        nature().check(FIRE);
        checkAction("Fire: condition changed to true");
    }
    @Test
    public void check2StringTest() {
        nature().check("Water", "Fire");
        checkAction("Fire: condition changed to true");
        assertContains(() -> (String) actionsLog.getTextList().get(1), "Water: condition changed to true");

    }
    @Test
    public void check2IndexTest() {
        nature().check(1, 4);
        checkAction("Fire: condition changed to true");
        assertContains(() -> (String) actionsLog.getTextList().get(1), "Water: condition changed to true");
    }
    @Test
    public void check2EnumTest() {
        nature().check(WATER, FIRE);
        checkAction("Fire: condition changed to true");
        assertContains(() -> (String) actionsLog.getTextList().get(1), "Water: condition changed to true");
    }

    @Test
    public void selectAllTest() {
        nature().selectAll();
        List<String> log = actionsLog.getTextList();
        assertContains(log.get(3), "Water: condition changed to true");
        assertContains(log.get(2), "Earth: condition changed to true");
        assertContains(log.get(1), "Wind: condition changed to true");
        assertContains(log.get(0), "Fire: condition changed to true");
        checkAllChecked();
    }
    @Test
    public void checkAllTest() {
        nature().checkAll();
        List<String> log = actionsLog.getTextList();
        assertContains(log.get(3), "Water: condition changed to true");
        assertContains(log.get(2), "Earth: condition changed to true");
        assertContains(log.get(1), "Wind: condition changed to true");
        assertContains(log.get(0), "Fire: condition changed to true");
        checkAllChecked();
    }
    @Test
    public void clearAllTest() {
        nature().checkAll();
        checkAllChecked();
        nature().clear();
        checkAllChecked(); // isDisplayed not defined
    }
    @Test
    public void uncheckAllTest() {
        nature().checkAll();
        checkAllChecked();
        nature().uncheckAll();
        checkAllChecked(); // isDisplayed not defined
    }

    private static final List<String> natureOptions = asList("Water", "Earth", "Wind", "Fire");
    @Test
    public void getOptionsTest() {
        listEquals(nature().getOptions(), natureOptions);
    }
    @Test
    public void getNamesTest() {
        listEquals(nature().getNames(), natureOptions);
    }
    @Test
    public void getValuesTest() {
        listEquals(nature().getValues(), natureOptions);
    }
    private static final String allValues = "Water, Earth, Wind, Fire";
    @Test
    public void getOptionsAsTextTest() {
        areEquals(nature().getOptionsAsText(), allValues);
    }
    @Test
    public void setValueTest() {
        nature().setValue("Fire");
        checkAction("Fire: condition changed to true");
    }
    @Test
    public void getNameTest() {
        areEquals(nature().getName(), "Nature");
    }

    @Test
    public void areSelectedTest() { listEquals(nature().areSelected(), new ArrayList<>());// isDisplayed not defined
    }
    @Test
    public void areDeselectedTest() { listEquals(nature().areDeselected(), natureOptions);// isDisplayed not defined
    }
    @Test
    public void getValueTest() {
        areEquals(nature().getValue(), "");// isDisplayed not defined
    }
}
