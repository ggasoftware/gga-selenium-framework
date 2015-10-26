package com.epam.career.tests;

import com.epam.career.TestsBase;
import com.epam.career.page_objects.dataProviders.AttendeeProvider;
import com.epam.career.page_objects.entities.Attendee;
import com.epam.career.page_objects.site.pages.JobListingPage;
import com.ggasoftware.jdiuitests.core.utils.map.MapArray;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.complex.table.interfaces.ICell;
import com.ggasoftware.jdiuitests.implementation.testng.asserter.Check;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.lang.reflect.Method;

import static com.epam.career.page_objects.enums.HeaderMenu.CAREERS;
import static com.epam.career.page_objects.enums.JobListHeaders.JOB_NAME;
import static com.epam.career.page_objects.enums.Preconditions.HOME_PAGE;
import static com.epam.career.page_objects.site.EpamSite.*;
import static com.ggasoftware.jdiuitests.implementation.selenium.elements.complex.table.Column.column;


public class CareerTests extends TestsBase {
    @BeforeMethod
    public void before(Method method) throws IOException {
        isInState(HOME_PAGE, method);
    }

    @Test(dataProvider = "attendees", dataProviderClass = AttendeeProvider.class)
    public void sendCVTest(Attendee attendee){
        header.select(CAREERS);
        careerPage.checkOpened();
        careerPage.jobFilter.search(attendee.filter);
        {
            JobListingPage _ = jobListingPage;
            _.checkOpened();
            new Check("Table is not empty").isFalse(_.jobsList::isEmpty);
            MapArray<String, ICell> row = _.jobsList.row("Senior QA Automation Engineer", column(JOB_NAME.toString()));
            row.get(0).value.select();
            _.applyNowButton.click();
            _.addCVForm.submit(attendee);
            new Check("Captcha").contains(() -> _.captcha.getAttribute("class"), "form-field-error");
        }
    }

}
