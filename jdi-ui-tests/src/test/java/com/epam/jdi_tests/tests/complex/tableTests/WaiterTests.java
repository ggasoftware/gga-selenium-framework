package com.epam.jdi_tests.tests.complex.tableTests;

import com.ggasoftware.jdi_ui_tests.implementation.testng.asserter.Assert;
import org.testng.annotations.Test;

import static com.epam.jdi_tests.enums.Preconditions.HOME_PAGE;
import static com.epam.jdi_tests.enums.Preconditions.SUPPORT_PAGE;
import static com.epam.jdi_tests.page_objects.EpamJDISite.isInState;
import static com.epam.jdi_tests.tests.complex.CommonActionsData.*;
import static com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.complex.table.Column.column;
import static com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.complex.table.Row.row;
import static com.ggasoftware.jdi_ui_tests.implementation.testng.asserter.Assert.isFalse;
import static com.ggasoftware.jdi_ui_tests.implementation.testng.asserter.Assert.isTrue;

/**
 * Created by Natalia_Grebenshchik on 10/5/2015.
 */
public class WaiterTests extends  InitTableTests{

    @Test
    public void waitExpectedRowsValue(){
        boolean rowValueExists = support().waitValue("Cucumber, Jbehave, Thucydides, SpecFlow", row(6));

        Assert.isTrue(rowValueExists,"waitValue method did not find \"Cucumber, Jbehave, Thucydides, SpecFlow\" in Row 6 ");
    }
    @Test
    public void waitUnexpectedRowsValue(){
        boolean rowValueExists = support().waitValue("Cucumber, Jbehave, Thucydides, SpecFlow Unexepected", row(6));

        Assert.isTrue(!rowValueExists,"waitValue method found unexpected \"Cucumber, Jbehave, Thucydides, SpecFlow Unexepected\" in Row 6 ");
    }

    @Test
    public void waitExpectedColumnsValue(){

        boolean columnValueExists = support().waitValue("Custom", column(2));

        Assert.isTrue(columnValueExists,"waitValue method did not find \"Cucumber, Jbehave, Thucydides, SpecFlow\" in Column 3 ");
    }
    @Test
    public void waitUnexpectedColumnsValue(){
        boolean rowValueExists = support().waitValue("Cucumber, Jbehave, Thucydides, SpecFlow Unexepected", column(3));

        Assert.isTrue(!rowValueExists,"waitValue method found unexpected \"Cucumber, Jbehave, Thucydides, SpecFlow Unexepected\" in Column 3 ");
    }

    @Test
    public void checkAreAnyRowsInTable() {
        boolean rowsPresent = support().isEmpty();

        Assert.isTrue(!rowsPresent, "isEmpty method did not found any rows in table");
    }

    @Test
    public void cellWaitText() {
        support().clean();
        isInState(HOME_PAGE);
        runParallel(SUPPORT_PAGE::open);
        checkText(() -> support().cell(column(2),row(2)).waitText("TestNG, JUnit Custom"), "TestNG, JUnit Custom");

        isTrue(timer.timePassedInMSec() > waitTimeOut);
    }
    @Test
    public void cellWaitMatchText() {
        support().clean();
        isInState(HOME_PAGE);
        runParallel(SUPPORT_PAGE::open);

        checkText(() -> support().cell(column(2), row(2)).waitMatchText("[a-zA-Z, ]*JUnit[a-zA-Z ]*"), "TestNG, JUnit Custom");
    }
    @Test
    public void waitHaveRows() {
        support().clean();
        isInState(HOME_PAGE);
        runParallel(SUPPORT_PAGE::open);

        isTrue(() -> support().waitHaveRows());
    }
    @Test
    public void waitRowsEqualNumberOfRows() {
        support().clean();
        isInState(HOME_PAGE);
        runParallel(SUPPORT_PAGE::open);

        isTrue(support().waitRows(6));
    }
    @Test
    public void waitRowsMoreNumberOfRows() {

        long tableWaitTomeOut;
        support().clean();
        isInState(HOME_PAGE);
        runParallel(SUPPORT_PAGE::open);

        tableWaitTomeOut = timer.timePassedInMSec();
        isFalse(support().waitRows(7));
        tableWaitTomeOut -= timer.timePassedInMSec();

        isTrue(-tableWaitTomeOut < 5200, "Time out for waiting table rows more than 5200 ms");
    }
}
