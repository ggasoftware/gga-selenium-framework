package com.epam.jditests.enums;

import com.ggasoftware.jdiuitests.core.utils.linqInterfaces.JAction;
import com.ggasoftware.jdiuitests.core.utils.linqInterfaces.JFuncT;
import org.openqa.selenium.WebElement;

import static com.epam.jditests.CommonData.TEST_DATE;
import static com.epam.jditests.entities.User.DEFAULT_USER;
import static com.epam.jditests.page_objects.EpamJDISite.contactFormPage;
import static com.epam.jditests.page_objects.EpamJDISite.dates;
import static com.ggasoftware.jdiuitests.core.settings.JDISettings.domain;
import static com.ggasoftware.jdiuitests.core.settings.JDISettings.getDriver;
import static com.ggasoftware.jdiuitests.implementation.selenium.elements.composite.Page.getUrl;
import static com.ggasoftware.jdiuitests.implementation.selenium.elements.composite.Page.openUrl;

/**
 * Created by 12345 on 03.06.2015.
 */
public enum Preconditions {
    HOME_PAGE("index.htm"),
    CONTACT_PAGE("page1.htm"),
    CONTACT_PAGE_FILLED(() -> checkUrl("page1.htm"), () -> {
        openUri("page1.htm");
        contactFormPage.name.newInput(DEFAULT_USER.name);
        contactFormPage.lastName.newInput(DEFAULT_USER.lastName);
        contactFormPage.description.newInput(DEFAULT_USER.description);
    }),
    METALS_AND_COLORS_PAGE("page2.htm"),
    DATES_PAGE("page4.htm"),
    SUPPORT_PAGE("page3.htm"),
    DATES_PAGE_FILLED(() -> checkUrl("page4.html"), () -> {
        openUri("page4.htm");
        WebElement datePicker = getDriver().findElement(dates.datepicker.getLocator());
        datePicker.clear();
        datePicker.sendKeys(TEST_DATE);
    });
    public String _htmlPageName;
    public JFuncT<Boolean> checkAction;
    public JAction moveToAction;

    Preconditions(final JFuncT<Boolean> checkAction, final JAction moveToAction) {
        this.checkAction = checkAction;
        this.moveToAction = moveToAction;
    }
    Preconditions(final String uri) {
        this(() -> false/*checkUrl(uri)*/, () -> openUri(uri));
        _htmlPageName = uri;
    }
    private static boolean checkUrl(final String uri) {
        return getUrl().matches(".*/" + uri + "(\\?.*)?");
    }
    public static void openUri(final String uri) {
        openUrl(domain.replaceAll("/*$", "") + "/" + uri.replaceAll("^/*", ""));
    }
    public void open() {
        moveToAction.invoke();
    }
}
