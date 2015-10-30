package com.epam.jditests.pageobjects;

import com.epam.jditests.enums.Preconditions;
import com.epam.jditests.pageobjects.pages.*;
import com.epam.jditests.pageobjects.sections.Footer;
import com.epam.jditests.pageobjects.sections.Header;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.complex.TextList;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.composite.Page;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.composite.Site;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.pageobjects.annotations.JPage;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.pageobjects.annotations.JSite;
import org.openqa.selenium.support.FindBy;
import ru.yandex.qatools.allure.annotations.Step;

import java.lang.reflect.Method;

import static com.ggasoftware.jdiuitests.core.settings.JDIData.testName;
import static com.ggasoftware.jdiuitests.core.settings.JDISettings.asserter;
import static com.ggasoftware.jdiuitests.core.settings.JDISettings.logger;
import static java.lang.String.format;

/**
 * Created by Maksim_Palchevskii on 9/10/2015.
 */

@JSite(domain = "http://ecse00100176.epam.com")
public class EpamJDISite extends Site {
    @JPage(url = "/index.htm", title = "Index Page")
    public static HomePage homePage;
    @JPage(url = "/page1.htm", title = "Contact Foem")
    public static ContactPage contactFormPage;
    @JPage(url = "/page2.htm", title = "Metal and Colors")
    public static MetalsColorsPage metalsColorsPage;
    @JPage(url = "/page3.htm", title = "Support")
    public static SupportPage supportPage;
    @JPage(url = "/page3.htm", title = "Support")
    public static SortingTablePage sortingTablePage;
    @JPage (url = "/page7.htm", title = "Table sort")
    public static DynamicTablePage dynamicTablePage;
    @JPage(url = "/page5.htm", title = "Table Scroll")
    public static Page simplePage;
    @JPage(url = "/page6.htm", title = "Simple Page")
    public static DatesPage dates;
    @FindBy(css = ".uui-profile-menu")
    public static Login login;
    @FindBy(css = ".uui-header")
    public static Header header;
    @FindBy(css = ".footer-content")
    public static Footer footer;
    @FindBy(css = ".logs li")
    public static TextList actionsLog;

    @Step
    public static void isInState(final Preconditions condition, final Method method) {
        testName = method.getName();
        isInState(condition);
    }
    @Step
    public static void isInState(final Preconditions condition) {
        try {
            logger.test("Move to condition: " + condition);
            if (condition.checkAction.invoke())
                return;
            condition.moveToAction.invoke();
            logger.test(format("Condition '%s' achieved", condition));
        } catch (final Exception ex) { throw asserter.exception(format("Can't reach state: %s. Exception: %s", condition, ex.getMessage())); }
    }
}