package com.moderna;

import com.epam.page_objects.mct.custom.ModernaCombobox;
import com.epam.page_objects.mct.custom.ModernaFileInput;
import com.epam.ui_test_framework.elements.interfaces.complex.IComboBox;
import com.epam.ui_test_framework.elements.interfaces.simple.IFileInput;
import com.epam.ui_test_framework.utils.common.Timer;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.epam.page_objects.mct.login.Portal.modernaUrl;
import static com.epam.ui_test_framework.elements.BaseElement.getInterfaceMap;
import static com.epam.ui_test_framework.utils.common.StringUtils.LineBreak;
import static com.epam.ui_test_framework.utils.settings.FrameworkSettings.logger;
import static com.epam.ui_test_framework.utils.settings.FrameworkSettings.seleniumFactory;
import static com.epam.ui_test_framework.utils.usefulUtils.WebDriverUtils.killAllRunWebDrivers;
import static java.lang.System.setProperty;

/**
 * Created by Roman_Iovlev on 7/13/2015.
 */
public class InitTests {

    public static Timer timer;

    @BeforeSuite(alwaysRun = true)
    public static void setUp() throws Exception {
        logger.init("Init testrun");
        killAllRunWebDrivers();
        setProperty("webdriver.chrome.driver", "C:/Jenkins_folder/chromedriver.exe");
        seleniumFactory.registerDriver(new ChromeDriver());
        seleniumFactory.getDriver().manage().window().maximize();
        seleniumFactory.getDriver().navigate().to(modernaUrl);
        getInterfaceMap().addOrReplace(new Object[][]{
                {IFileInput.class,  ModernaFileInput.class},
                {IComboBox.class,   ModernaCombobox.class}
        });
        timer = new Timer();
        logger.init("Run Tests");
    }

    @AfterSuite(alwaysRun = true)
    public static void tearDown() throws Exception {
        logger.info("Testrun fiinished. " + LineBreak + "Tests time spent: " + new SimpleDateFormat("HH:mm:ss.S").format(new Date(21 * 3600000 + timer.timePassedInMSec())));
        killAllRunWebDrivers();
    }
}
