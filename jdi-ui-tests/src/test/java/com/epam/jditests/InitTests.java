package com.epam.jditests;

import com.epam.jditests.pageobjects.EpamJDISite;
import com.ggasoftware.jdiuitest.web.testng.testRunner.TestNGBase;
import org.testng.annotations.BeforeSuite;

import static com.epam.jditests.entities.User.DEFAULT_USER;
import static com.epam.jditests.pageobjects.EpamJDISite.homePage;
import static com.epam.jditests.pageobjects.EpamJDISite.login;
import static com.ggasoftware.jdiuitest.web.selenium.elements.WebSettings.*;
import static com.ggasoftware.jdiuitest.web.selenium.elements.composite.Site.Init;


/**
 * Created by Roman_Iovlev on 7/13/2015.
 */
public class InitTests extends TestNGBase {

    @BeforeSuite(alwaysRun = true)
    public static void setUp() throws Exception {
        getDriverFactory().setDriverPath("C:\\Selenium");
        initJDIFromProperties();
        //Assert.noScreenOnFail();
        Init(EpamJDISite.class);
        homePage.open();
        login.submit(DEFAULT_USER);
        logger.init("Run Tests");
    }
}
