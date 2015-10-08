package com.epam.jdi_tests;

import static com.epam.jdi_tests.entities.User.DEFAULT_USER;
import static com.epam.jdi_tests.page_objects.EpamJDISite.homePage;
import static com.epam.jdi_tests.page_objects.EpamJDISite.login;
import static com.ggasoftware.jdi_ui_tests.core.settings.JDISettings.initJDIFromProperties;
import static com.ggasoftware.jdi_ui_tests.core.settings.JDISettings.logger;
import static com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.composite.Site.Init;

import org.testng.annotations.BeforeSuite;

import com.epam.jdi_tests.enums.Preconditions;
import com.epam.jdi_tests.page_objects.EpamJDISite;
import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.interfaces.common.IText;
import com.ggasoftware.jdi_ui_tests.implementation.testng.asserter.Assert;
import com.ggasoftware.jdi_ui_tests.implementation.testng.testRunner.TestNGBase;


/**
 * Created by Roman_Iovlev on 7/13/2015.
 */
public class InitTests extends TestNGBase {
	
	public Preconditions _onPage;
	
    @BeforeSuite(alwaysRun = true)
    public static void setUp() throws Exception {
        initJDIFromProperties();
        //Assert.noScreenOnFail();
        Init(EpamJDISite.class);
        homePage.open();
        login.submit(DEFAULT_USER);
        logger.init("Run Tests");
    }
     
    public IText textElement() throws Exception{
        //test comments
    	throw new Exception();
    }
}
