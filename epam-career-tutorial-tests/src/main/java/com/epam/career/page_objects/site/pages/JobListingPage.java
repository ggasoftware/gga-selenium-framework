package com.epam.career.page_objects.site.pages;

import com.epam.career.page_objects.site.sections.AddCVForm;
import com.epam.career.page_objects.site.sections.JobFilter;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.complex.table.Table;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.complex.table.interfaces.ITable;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.composite.Page;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.interfaces.base.IElement;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.interfaces.common.IButton;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

import static com.epam.career.page_objects.enums.JobListHeaders.*;

/**
 * Created by Roman_Iovlev on 10/22/2015.
 */
public class JobListingPage extends Page {
    @FindBy(className = "career-list-header")
    public JobFilter jobFilter;

    @FindBy(className = "search-result-list")
    public ITable jobsList = new Table(null,
            By.xpath("//*[@class='search-result-list']/li[%s]//div"),
            By.xpath("//*[@class='search-result-list']/li//div[%s]"))
            .setColumnHeaders(new String[]{JOB_NAME.toString(), JOB_CATEGORY.toString(), JOB_LOCATION.toString()});

    @FindBy(xpath = "//*[.='Apply Now']")
    public IButton applyNowButton;
    @FindBy(css = ".vac-sidebar .form-constructor")
    public AddCVForm addCVForm;

    @FindBy(id = "captcha-input")
    public IElement captcha;

}
