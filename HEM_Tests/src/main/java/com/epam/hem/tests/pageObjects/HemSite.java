package com.epam.hem.tests.pageObjects;

import com.epam.hem.tests.pageObjects.pages.HomePage;
import com.epam.hem.tests.pageObjects.pages.ProjectsPage;
import com.epam.hem.tests.pageObjects.sections.common.LoginForm;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.composite.Site;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.pageobjects.annotations.JPage;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.pageobjects.annotations.JSite;
import org.openqa.selenium.support.FindBy;

/**
 * Created by Pavel_Shcherbakov1 on 2015-10-08
 */

@JSite(domain = "http://ecse0010016b.epam.com:8080/hem")
public class HemSite extends Site {
    @JPage(url = "/", title = "Home Page")
    public static HomePage homePage;
    @JPage(url = "/#/projects", title = "Projects Page")
    public static ProjectsPage projectsPage;
    @FindBy(id = "fm1")
    public static LoginForm login;
}
