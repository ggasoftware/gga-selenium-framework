package com.epam.jdi_tests.tests.common;

import com.epam.jdi_tests.InitTests;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.lang.reflect.Method;

import static com.epam.jdi_tests.enums.Preconditions.HOME_PAGE;
import static com.epam.jdi_tests.page_objects.EpamJDISite.isInState;
import static com.ggasoftware.jdi_ui_tests.core.settings.JDIData.testName;

/**
 * Created by Roman_Iovlev on 9/15/2015.
 */
public class ImageTests extends InitTests {
    @BeforeMethod
    public void before(Method method) throws IOException {
        testName = method.getName();
        isInState(HOME_PAGE, method);
    }

    @Test
    public void test() {

    }
}
