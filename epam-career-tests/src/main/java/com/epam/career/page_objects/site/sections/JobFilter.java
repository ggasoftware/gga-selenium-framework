package com.epam.career.page_objects.site.sections;

import com.epam.career.page_objects.entities.JobSearchFilter;
import com.epam.career.page_objects.enums.JobCategories;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.complex.Dropdown;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.composite.Form;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.interfaces.common.IButton;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.interfaces.common.ITextField;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.interfaces.complex.IDropDown;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

/**
 * Created by Roman_Iovlev on 10/22/2015.
 */
public class JobFilter extends Form<JobSearchFilter> {
    @FindBy(className = "job-search-input")
    public ITextField keywords;
    @FindBy(id = "select-box-department-select-container")
    public IDropDown<JobCategories> category = new Dropdown<>(By.id("select-box-department-select-container"), By.cssSelector(".options .option"));
    /*@FindBy(className = "career-location-box")
    public IDropDown<Locations> city;*/

    @FindBy(className = "job-search-button")
    public IButton selectButton;
}
