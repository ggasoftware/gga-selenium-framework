package com.epam.jdi_tests.tests.complex.tableTests;

import com.epam.jdi_tests.InitTests;
import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.complex.table.interfaces.ITable;
import com.sun.org.apache.xml.internal.security.Init;
import org.testng.annotations.BeforeMethod;

import java.io.IOException;
import java.lang.reflect.Method;

import static com.epam.jdi_tests.enums.Preconditions.SUPPORT_PAGE;
import static com.epam.jdi_tests.page_objects.EpamJDISite.isInState;
import static com.epam.jdi_tests.page_objects.EpamJDISite.supportPage;

/**
 * Created by Natalia_Grebenshchik on 10/5/2015.
 */
public class InitTableTests extends InitTests{
    protected ITable support() {
        return supportPage.supportTable;
    }

    @BeforeMethod
    protected void before(Method method) throws IOException {
        isInState(SUPPORT_PAGE, method);
        support().useCache();
    }
}
