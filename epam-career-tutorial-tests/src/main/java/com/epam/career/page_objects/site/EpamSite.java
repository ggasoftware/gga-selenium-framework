package com.epam.career.page_objects.site;

import com.epam.career.page_objects.enums.HeaderMenu;
import com.epam.career.page_objects.site.pages.CareerPage;
import com.epam.career.page_objects.site.pages.HomePage;
import com.epam.career.page_objects.site.pages.JobDescriptionPage;
import com.epam.career.page_objects.site.pages.JobListingPage;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.complex.Menu;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.composite.Site;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.pageobjects.annotations.JPage;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.pageobjects.annotations.JSite;
import org.openqa.selenium.support.FindBy;

import static com.ggasoftware.jdiuitests.implementation.selenium.elements.composite.CheckPageTypes.CONTAIN;
import static com.ggasoftware.jdiuitests.implementation.selenium.elements.composite.CheckPageTypes.MATCH;

/**
 * Created by Roman_Iovlev on 8/30/2015.
 */
@JSite(domain = "http://test-env-clgn.elasticbeanstalk.com/#/")
public class EpamSite extends Site {
    @JPage(url = "/", title = "EPAM | Software Product Development Services")
    public static HomePage homePage;
    @JPage(url = "/careers", title = "Careers")
    public static CareerPage careerPage;
    @JPage(url = "/careers/job-listings", title = "Job Listings", urlCheckType = CONTAIN, titleCheckType = CONTAIN)
    public static JobListingPage jobListingPage;
    @JPage(url = ".*/careers/job-listings/job\\.\\d*#apply", urlCheckType = MATCH)
    public static JobDescriptionPage jobDescriptionPage;

    @FindBy(css = ".tile-menu>li>a")
    public static Menu<HeaderMenu> headerMenu;

}
