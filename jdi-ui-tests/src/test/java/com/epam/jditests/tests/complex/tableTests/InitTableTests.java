package com.epam.jditests.tests.complex.tableTests;

import com.epam.jditests.InitTests;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.complex.table.interfaces.ITable;
import org.testng.annotations.BeforeMethod;

import java.io.IOException;
import java.lang.reflect.Method;

import static com.epam.jditests.enums.Preconditions.SUPPORT_PAGE;
import static com.epam.jditests.pageobjects.EpamJDISite.isInState;
import static com.epam.jditests.pageobjects.EpamJDISite.supportPage;

/**
 * Created by Natalia_Grebenshchik on 10/5/2015.
 */
public class InitTableTests extends InitTests {
    protected ITable support() {
        return supportPage.supportTable;
    }

    @BeforeMethod
    protected void before(Method method) throws IOException {
        isInState(SUPPORT_PAGE, method);
        support().clean();
    }
}
