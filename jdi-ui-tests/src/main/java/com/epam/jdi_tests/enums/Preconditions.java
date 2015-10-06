package com.epam.jdi_tests.enums;

import static com.epam.jdi_tests.dataproviders.DatePickerDP.currentDate;
import static com.epam.jdi_tests.entities.User.DEFAULT_USER;
import static com.epam.jdi_tests.page_objects.EpamJDISite.contactFormPage;
import static com.epam.jdi_tests.page_objects.EpamJDISite.dates;
import static com.ggasoftware.jdi_ui_tests.core.settings.JDISettings.domain;
import static com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.composite.Page.getUrl;
import static com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.composite.Page.openUrl;

import com.ggasoftware.jdi_ui_tests.core.utils.linqInterfaces.JAction;
import com.ggasoftware.jdi_ui_tests.core.utils.linqInterfaces.JFuncT;

/**
 * Created by 12345 on 03.06.2015.
 */
public enum Preconditions {
    HOME_PAGE("index.htm"),
    CONTACT_PAGE("page1.htm"),
    CONTACT_PAGE_WITH_FILLED_FIELDS(() -> checkUrl("page1.htm"), () -> {
        openUri("page1.htm");
        contactFormPage.name.newInput(DEFAULT_USER.name);
        contactFormPage.lastName.newInput(DEFAULT_USER.lastName);
        contactFormPage.description.newInput(DEFAULT_USER.description);
    }),
    METALS_AND_COLORS_PAGE("page2.htm"),
//    DATES_PAGE("page4.htm");
    SUPPORT_PAGE("page3.htm"),
    DATES_PAGE(() -> checkUrl("page4.html"), () -> {
    	openUri("page4.htm");
		((org.openqa.selenium.JavascriptExecutor) com.ggasoftware.jdi_ui_tests.core.settings.JDISettings.getDriver())
		.executeScript("arguments[0].value = arguments[1]", dates._datepicker.getWebElement(), currentDate());
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
