package com.epam.career.page_objects.site.pages;

import com.epam.career.page_objects.site.sections.JobFilter;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.composite.Page;
import org.openqa.selenium.support.FindBy;

/**
 * Created by Roman_Iovlev on 10/22/2015.
 */
public class CareerPage extends Page {
    @FindBy(className = "career-list-header")
    public JobFilter jobFilter;
}
