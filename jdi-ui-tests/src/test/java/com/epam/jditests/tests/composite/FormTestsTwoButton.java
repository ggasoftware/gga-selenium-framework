package com.epam.jditests.tests.composite;

import com.epam.jditests.InitTests;
import com.epam.jditests.pageobjects.pages.ContactFormTwoButtons;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

import static com.epam.jditests.entities.Contact.DEFAULT_CONTACT;
import static com.epam.jditests.enums.Buttons.SUBMIT;
import static com.epam.jditests.enums.Preconditions.CONTACT_PAGE;
import static com.epam.jditests.pageobjects.EpamJDISite.contactFormPage;
import static com.epam.jditests.pageobjects.EpamJDISite.isInState;
import static com.epam.jditests.tests.complex.CommonActionsData.checkResult;

/**
 * Created by Dmitry_Lebedev1 on 10/15/2015.
 */
public class FormTestsTwoButton extends InitTests {
    public ContactFormTwoButtons form() {
        return contactFormPage.contactFormTwoButtons;
    }

    @BeforeMethod
    public void before(final Method method) {
        isInState(CONTACT_PAGE, method);
    }

    @Test
    public void submitTest() {
        form().submit(DEFAULT_CONTACT);
        checkResult(DEFAULT_CONTACT.toString());
    }

    @Test
    public void submitSpecButtonStringTest() {
        form().submit(DEFAULT_CONTACT, "submit");
        checkResult(DEFAULT_CONTACT.toString());
    }

    @Test
    public void submitSpecButtonEnumTest() throws Exception {
        form().submit(DEFAULT_CONTACT, SUBMIT);
        checkResult(DEFAULT_CONTACT.toString());
    }

    @Test
    public void submitStringTest() throws Exception {
        form().submit(DEFAULT_CONTACT.name);
        checkResult(String.format("Summary: 3\nName: %s", DEFAULT_CONTACT.name));
    }
}