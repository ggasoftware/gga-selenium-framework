package com.epam.career.page_objects.site.pages;

import com.epam.career.page_objects.site.sections.JobFilter;
import com.ggasoftware.jdiuitest.web.selenium.elements.composite.WebPage;
import org.openqa.selenium.support.FindBy;

/**
 * Created by Roman_Iovlev on 10/22/2015.
 */
public class CareerPage extends WebPage {
    @FindBy(className = "job-search")
    public JobFilter jobFilter;
}
