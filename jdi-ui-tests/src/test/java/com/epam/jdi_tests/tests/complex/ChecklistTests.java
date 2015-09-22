package com.epam.jdi_tests.tests.complex;

import com.epam.jdi_tests.InitTests;
import com.epam.jdi_tests.enums.Nature;
import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.interfaces.complex.ICheckList;
import org.openqa.selenium.By;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;

import static com.epam.jdi_tests.enums.Nature.*;
import static com.epam.jdi_tests.page_objects.EpamJDISite.actionsLog;
import static com.epam.jdi_tests.page_objects.EpamJDISite.metalsColorsPage;
import static com.epam.jdi_tests.tests.complex.CommonActionsData.checkAction;
import static com.epam.jdi_tests.tests.complex.CommonActionsData.checkActionThrowError;
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
        metalsColorsPage.open();
        /*nature().select("Fire");
        for(WebElement el : getDriver().findElements(By.cssSelector("#elements-checklist p"))) {
            if (el.findElement(By.tagName("input")).getAttribute("checked") != null)
                el.findElement(By.tagName("label")).click();
        }*/
    }
    private void checkAllChecked() {
        assertTrue(first(getDriver().findElements(By.cssSelector("#elements-checklist input")),
            el -> el.getAttribute("checked") != null) != null);
    }

    @Test
    public void selectStringTest() {
        nature().select("Fire");
        checkAction("Fire: condition changed to true");
        nature().select("Earth");
        checkAction("Earth: condition changed to true");
    }
    @Test
    public void selectIndexTest() {
        nature().select(4);
        checkAction("Fire: condition changed to true");
        nature().select(2);
        checkAction("Earth: condition changed to true");
    }
    @Test
    public void selectEnumTest() {
        nature().select(FIRE);
        checkAction("Fire: condition changed to true");
        nature().select(EARTH);
        checkAction("Earth: condition changed to true");
    }
    @Test
    public void select2StringTest() {
        nature().select("Water", "Fire");
        checkAction("Fire: condition changed to true");
        contains(() -> (String) actionsLog.getTextList().get(1), "Water: condition changed to true");

    }
    @Test
    public void select2IndexTest() {
        nature().select(1, 4);
        checkAction("Fire: condition changed to true");
        contains(() -> (String) actionsLog.getTextList().get(1), "Water: condition changed to true");
    }
    @Test
    public void select2EnumTest() {
        nature().select(WATER, FIRE);
        checkAction("Fire: condition changed to true");
        contains(() -> (String) actionsLog.getTextList().get(1), "Water: condition changed to true");
    }


    @Test
    public void checkStringTest() {
        nature().check("Fire");
        checkAction("Fire: condition changed to true");
        nature().check("Earth");
        checkAction("Earth: condition changed to true");
    }
    @Test
    public void checkIndexTest() {
        nature().check(4);
        checkAction("Fire: condition changed to true");
        nature().check(2);
        checkAction("Earth: condition changed to true");
    }
    @Test
    public void checkEnumTest() {
        nature().check(FIRE);
        checkAction("Fire: condition changed to true");
        nature().check(EARTH);
        checkAction("Earth: condition changed to true");
    }
    @Test
    public void check2StringTest() {
        nature().check("Water", "Fire");
        checkAction("Fire: condition changed to true");
        contains(() -> (String) actionsLog.getTextList().get(1), "Water: condition changed to true");

    }
    @Test
    public void check2IndexTest() {
        nature().check(1, 4);
        checkAction("Fire: condition changed to true");
        contains(() -> (String) actionsLog.getTextList().get(1), "Water: condition changed to true");
    }
    @Test
    public void check2EnumTest() {
        nature().check(WATER, FIRE);
        checkAction("Fire: condition changed to true");
        contains(() -> (String)actionsLog.getTextList().get(1), "Water: condition changed to true");
    }

    @Test
    public void selectAllTest() {
        nature().selectAll();
        List<String> log = actionsLog.getTextList();
        contains(log.get(3), "Water: condition changed to true");
        contains(log.get(2), "Earth: condition changed to true");
        contains(log.get(1), "Wind: condition changed to true");
        contains(log.get(0), "Fire: condition changed to true");
        checkAllChecked();
    }
    @Test
    public void checkAllTest() {
        nature().checkAll();
        List<String> log = actionsLog.getTextList();
        contains(log.get(3), "Water: condition changed to true");
        contains(log.get(2), "Earth: condition changed to true");
        contains(log.get(1), "Wind: condition changed to true");
        contains(log.get(0), "Fire: condition changed to true");
        checkAllChecked();
    }
    @Test
    public void clearAllTest() {
        nature().clear();
        List<String> log = actionsLog.getTextList();
        contains(log.get(3), "Water: condition changed to false");
        contains(log.get(2), "Earth: condition changed to false");
        contains(log.get(1), "Wind: condition changed to false");
        contains(log.get(0), "Fire: condition changed to false");
        checkAllChecked();
    }
    @Test
    public void uncheckAllTest() {
        nature().uncheckAll();
        List<String> log = actionsLog.getTextList();
        contains(log.get(3), "Water: condition changed to false");
        contains(log.get(2), "Earth: condition changed to false");
        contains(log.get(1), "Wind: condition changed to false");
        contains(log.get(0), "Fire: condition changed to false");
        checkAllChecked();
    }

    private static final List<String> oddOptions = asList("Water", "Earth", "Wind", "Fire");
    @Test
    public void getOptionsTest() {
        listEquals(nature().getOptions(), oddOptions);
    }
    @Test
    public void getNamesTest() {
        listEquals(nature().getNames(), oddOptions);
    }
    @Test
    public void getValuesTest() {
        listEquals(nature().getValues(), oddOptions);
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
        nature().setValue("Earth");
        checkAction("Earth: condition changed to true");
    }
    @Test
    public void getNameTest() {
        areEquals(nature().getName(), "Nature");
    }

    // Fails
    @Test
    public void areSelectedTest() {
        checkActionThrowError(() -> nature().areSelected());
    }
    @Test
    public void areDeselectedTest() {
        checkActionThrowError(() -> nature().areDeselected());
    }
    @Test
    public void clearTest() {
        checkActionThrowError(() -> nature().clear());
    }
    @Test
    public void getValueTest() {
        checkActionThrowError(() -> nature().getValue());
    }
}
