package com.epam.career.tests;

import com.epam.career.TestsBase;
import com.epam.career.page_objects.dataProviders.AttendeeProvider;
import com.epam.career.page_objects.entities.Attendee;
import com.ggasoftware.jdiuitests.implementation.testng.asserter.Check;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.lang.reflect.Method;

import static com.epam.career.page_objects.enums.HeaderMenu.CAREERS;
import static com.epam.career.page_objects.enums.Preconditions.HOME_PAGE;
import static com.epam.career.page_objects.site.EpamSite.*;
import static com.ggasoftware.jdiuitests.core.utils.preconditions.PreconditionsState.isInState;


public class CareerTests extends TestsBase {
    @BeforeMethod
    public void before(Method method) throws IOException {
        isInState(HOME_PAGE, method);
    }

    @Test(dataProvider = "attendees", dataProviderClass = AttendeeProvider.class)
    public void sendCVTest(Attendee attendee) {
        headerMenu.select(CAREERS);
        careerPage.checkOpened();
        careerPage.jobFilter.search(attendee.filter);

        jobListingPage.checkOpened();
        new Check("Table is not empty").isFalse(jobListingPage.jobsList::isEmpty);
        jobListingPage.getJobRowByName("Senior QA Automation Engineer");

        jobDescriptionPage.addCVForm.submit(attendee);
        new Check("Captcha").contains(() -> jobDescriptionPage.captcha.getAttribute("class"), "form-field-error");
    }

}
