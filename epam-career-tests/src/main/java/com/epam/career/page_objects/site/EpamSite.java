package com.epam.career.page_objects.site;

import com.epam.career.page_objects.enums.HeaderMenu;
import com.epam.career.page_objects.enums.Preconditions;
import com.epam.career.page_objects.site.pages.CareerPage;
import com.epam.career.page_objects.site.pages.HomePage;
import com.epam.career.page_objects.site.pages.JobListingPage;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.complex.Menu;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.composite.Site;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.pageobjects.annotations.JPage;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.pageobjects.annotations.JSite;
import com.ggasoftware.jdiuitests.implementation.testng.asserter.Check;
import org.openqa.selenium.support.FindBy;
import ru.yandex.qatools.allure.annotations.Step;

import java.lang.reflect.Method;

import static com.ggasoftware.jdiuitests.core.settings.JDIData.testName;
import static com.ggasoftware.jdiuitests.core.settings.JDISettings.asserter;
import static com.ggasoftware.jdiuitests.core.settings.JDISettings.logger;
import static com.ggasoftware.jdiuitests.implementation.selenium.elements.composite.CheckPageTypes.CONTAIN;
import static java.lang.String.format;

/**
 * Created by Roman_Iovlev on 8/30/2015.
 */
@JSite(domain = "http://test-env-clgn.elasticbeanstalk.com/#/")
public class EpamSite extends Site {
    @JPage(url = "/", title = "EPAM | Software Product Development Services")
    public static HomePage homePage;
    @JPage(url = "/careers", title = "Careers")
    public static CareerPage careerPage;
    @JPage(url = "/careers/job-listings", title = "Job Listings", urlCheckType = CONTAIN, titleCheckType = CONTAIN)
    public static JobListingPage jobListingPage;

    @FindBy(css = ".tile-menu>li>a")
    public static Menu<HeaderMenu> header;


    @Step
    public static void isInState(Preconditions condition, Method method) {
        try {
            logger.test("Move to condition: " + condition);
            testName = method.getName();
            if (condition.checkAction.invoke())
                return;
            condition.moveToAction.invoke();
            new Check(condition + " condition achieved").isTrue(condition.checkAction::invoke);
        } catch (Exception ex) { throw asserter.exception(format("Can't reach state: %s. Exception: %s", condition, ex.getMessage())); }
    }
}
