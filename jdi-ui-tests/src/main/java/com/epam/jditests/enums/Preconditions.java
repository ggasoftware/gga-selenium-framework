package com.epam.jditests.enums;

import com.ggasoftware.jdiuitest.core.utils.linqinterfaces.JAction;
import com.ggasoftware.jdiuitest.core.utils.linqinterfaces.JFuncT;
import com.ggasoftware.jdiuitest.web.selenium.preconditions.IPreconditions;
import org.openqa.selenium.WebElement;

import static com.epam.jditests.CommonData.TEST_DATE;
import static com.epam.jditests.entities.User.DEFAULT_USER;
import static com.epam.jditests.pageobjects.EpamJDISite.contactFormPage;
import static com.epam.jditests.pageobjects.EpamJDISite.dates;
import static com.ggasoftware.jdiuitest.web.selenium.elements.WebSettings.getDriver;
import static com.ggasoftware.jdiuitest.web.selenium.preconditions.PreconditionsState.alwaysMoveToCondition;

/**
 * Created by 12345 on 03.06.2015.
 */
public enum Preconditions implements IPreconditions {
    HOME_PAGE("index.htm"),
    CONTACT_PAGE("page1.htm"),
    CONTACT_PAGE_FILLED(() -> false/*checkUrl("page1.htm")*/, () -> {
        IPreconditions.openUri("page1.htm");
        contactFormPage.name.newInput(DEFAULT_USER.name);
        contactFormPage.lastName.newInput(DEFAULT_USER.lastName);
        contactFormPage.description.newInput(DEFAULT_USER.description);
    }),
    METALS_AND_COLORS_PAGE("page2.htm"),
    DATES_PAGE("page4.htm"),
    SUPPORT_PAGE("page3.htm"),
    SORTING_TABLE_PAGE("page7.htm"),
    DYNAMIC_TABLE_PAGE("page5.htm"),
    SIMPLE_PAGE("page6.htm"),
    DATES_PAGE_FILLED(() -> false/*checkUrl("page4.html")*/, () -> {
        IPreconditions.openUri("page4.htm");
        WebElement datePicker = getDriver().findElement(dates.datepicker.getLocator());
        datePicker.clear();
        datePicker.sendKeys(TEST_DATE);
    });

    public String _htmlPageName;

    public JFuncT<Boolean> checkAction;
    public JAction moveToAction;
    public JFuncT<Boolean> checkAction() { return checkAction; }
    public JAction moveToAction() { return moveToAction; }

    Preconditions(JFuncT<Boolean> checkAction, JAction moveToAction) {
        this.checkAction = checkAction;
        this.moveToAction = moveToAction;
        alwaysMoveToCondition = true;
    }

    Preconditions(String uri) {
        this(() -> false/*checkUrl(uri)*/, () -> IPreconditions.openUri(uri));
        _htmlPageName = uri;
    }
}
