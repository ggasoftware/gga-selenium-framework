package com.epam.hem.tests;

import com.epam.hem.tests.pageObjects.HemSite;
import com.ggasoftware.jdiuitests.core.settings.JDISettings;
import com.ggasoftware.jdiuitests.implementation.testng.testRunner.TestNGBase;
import org.testng.annotations.BeforeSuite;

import static com.epam.hem.tests.entities.User.DEFAULT_USER;
import static com.epam.hem.tests.pageObjects.HemSite.homePage;
import static com.epam.hem.tests.pageObjects.HemSite.login;
import static com.ggasoftware.jdiuitests.core.settings.JDISettings.initJDIFromProperties;
import static com.ggasoftware.jdiuitests.core.settings.JDISettings.logger;
import static com.ggasoftware.jdiuitests.implementation.selenium.elements.composite.Site.Init;

/**
 * Created by Pavel_Shcherbakov1 on 2015-10-08
 */
public class InitTests extends TestNGBase {
    @BeforeSuite(alwaysRun = true)
    public static void setUp() throws Exception {
        JDISettings.driverFactory.driversPath = "C:\\Selenium";
        initJDIFromProperties();
        Init(HemSite.class);
        homePage.open();
        login.login(DEFAULT_USER);
        logger.init("Run Tests");
    }
}
