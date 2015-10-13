package com.epam.jditests;

import com.epam.jditests.page_objects.EpamJDISite;
import com.ggasoftware.jdiuitests.core.settings.JDISettings;
import com.ggasoftware.jdiuitests.implementation.testng.testRunner.TestNGBase;
import org.testng.annotations.BeforeSuite;

import static com.epam.jditests.entities.User.DEFAULT_USER;
import static com.epam.jditests.page_objects.EpamJDISite.homePage;
import static com.epam.jditests.page_objects.EpamJDISite.login;
import static com.ggasoftware.jdiuitests.core.settings.JDISettings.initJDIFromProperties;
import static com.ggasoftware.jdiuitests.core.settings.JDISettings.logger;
import static com.ggasoftware.jdiuitests.implementation.selenium.elements.composite.Site.Init;


/**
 * Created by Roman_Iovlev on 7/13/2015.
 */
public class InitTests extends TestNGBase {

    @BeforeSuite(alwaysRun = true)
    public static void setUp() throws Exception {
        JDISettings.driverFactory.driversPath = "jdi-ui-tests\\src\\main\\resources";
        initJDIFromProperties();
        //Assert.noScreenOnFail();
        Init(EpamJDISite.class);
        homePage.open();
        login.submit(DEFAULT_USER);
        logger.init("Run Tests");
    }
}
