package com.epam.career.page_objects.site.sections;

import com.epam.career.page_objects.entities.JobSearchFilter;
import com.epam.career.page_objects.enums.JobCategories;
import com.epam.career.page_objects.enums.Locations;
import com.ggasoftware.jdiuitest.core.interfaces.common.IButton;
import com.ggasoftware.jdiuitest.core.interfaces.common.ITextField;
import com.ggasoftware.jdiuitest.core.interfaces.complex.IDropDown;
import com.ggasoftware.jdiuitest.web.selenium.elements.complex.Dropdown;
import com.ggasoftware.jdiuitest.web.selenium.elements.composite.Form;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

/**
 * Created by Roman_Iovlev on 10/22/2015.
 */
public class JobFilter extends Form<JobSearchFilter> {
    @FindBy(className = "job-search-input")
    public ITextField keywords;
    public IDropDown<JobCategories> category = new Dropdown<>(By.id("select-box-department-select-container"), By.className("option"));
    @FindBy(className = "career-location-box")
    public IDropDown<Locations> city;

    @FindBy(className = "job-search-button")
    public IButton selectButton;
}
