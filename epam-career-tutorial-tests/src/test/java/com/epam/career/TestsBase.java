package com.epam.career;

import com.epam.career.page_objects.site.EpamSite;
import com.ggasoftware.jdiuitest.web.testng.testRunner.TestNGBase;
import org.testng.annotations.BeforeSuite;

import static com.epam.career.page_objects.site.EpamSite.homePage;
import static com.ggasoftware.jdiuitest.core.settings.JDISettings.*;
import static com.ggasoftware.jdiuitest.web.selenium.elements.composite.Site.Init;

/**
 * Created by Roman_Iovlev on 7/13/2015.
 */
public abstract class TestsBase extends TestNGBase {
    @BeforeSuite(alwaysRun = true)
    public static void setUp() throws Exception {
        driverFactory.setDriverPath("C:\\Selenium");
        initJDIFromProperties();
        Init(EpamSite.class);
        homePage.open();
        logger.init("Run Tests");
    }
}
