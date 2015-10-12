package com.epam.jdi_tests;

import com.epam.jdi_tests.page_objects.EpamJDISite;
import com.ggasoftware.jdi_ui_tests.core.settings.JDISettings;
import com.ggasoftware.jdi_ui_tests.implementation.testng.testRunner.TestNGBase;
import org.testng.annotations.BeforeSuite;

import static com.epam.jdi_tests.entities.User.DEFAULT_USER;
import static com.epam.jdi_tests.page_objects.EpamJDISite.homePage;
import static com.epam.jdi_tests.page_objects.EpamJDISite.login;
import static com.ggasoftware.jdi_ui_tests.core.settings.JDISettings.initJDIFromProperties;
import static com.ggasoftware.jdi_ui_tests.core.settings.JDISettings.logger;
import static com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.composite.Site.Init;


/**
 * Created by Roman_Iovlev on 7/13/2015.
 */
public class InitTests extends TestNGBase {

    @BeforeSuite(alwaysRun = true)
    public static void setUp() throws Exception {
//        JDISettings.driverFactory.driversPath = "jdi-ui-tests\\src\\main\\resources";
        initJDIFromProperties();
        //Assert.noScreenOnFail();
        Init(EpamJDISite.class);
        homePage.open();
        login.submit(DEFAULT_USER);
        logger.init("Run Tests");
    }
}
