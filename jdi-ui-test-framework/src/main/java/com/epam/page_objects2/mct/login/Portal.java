package com.epam.page_objects2.mct.login;

import com.epam.page_objects2.enums.Preconditions;
import com.epam.ui_test_framework.elements.base.Element;
import com.epam.ui_test_framework.elements.interfaces.common.IButton;
import com.epam.ui_test_framework.elements.interfaces.common.ILabel;
import org.openqa.selenium.support.FindBy;
import ru.yandex.qatools.allure.annotations.Step;

import static com.epam.page_objects2.entities.User.DefaultUser;
import static com.epam.page_objects2.mct.login.LoginPage.loginPage;
import static com.epam.ui_test_framework.utils.settings.FrameworkSettings.asserter;
import static com.epam.ui_test_framework.utils.settings.FrameworkSettings.logger;
import static java.lang.String.format;


/**
 * Entering to Portal Panel
 *
 * Created by nlokhanov on 27-Jan-15.
 */
public class Portal extends Element {

    public static Portal portal = InitElements(new Portal());

    public static String modernaUrl = "https://portal-auto-test.modernatx.com/";

    @FindBy(css = ".app-user-info")         public  IButton loginInfo;
    @FindBy(css = ".app-logo")              private ILabel homePageLogo;
    @FindBy(css = "a[href='logoutUser']")   private IButton logutLink;


    @Step
    public void logout() {
        loginInfo.click();
        logutLink.click();
    }

    @Step
    public void isInState(Preconditions condition) {
        try {
            logger.test("Move to condition: " + condition);
            if (condition.checkAction.invoke())
                return;
            String url = getDriver().getCurrentUrl();
            if (url.contains("auth/login"))
               loginPage.submit(DefaultUser);
            condition.moveToAction.invoke();
            logger.test(format("Condition '%s' achived", condition));
        } catch (Exception ex) { asserter.exception(format("Can't reach state: %s. Exception: %s", condition, ex.getMessage())); }
    }
}
