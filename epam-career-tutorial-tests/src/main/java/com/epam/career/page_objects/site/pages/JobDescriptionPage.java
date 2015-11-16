package com.epam.career.page_objects.site.pages;

import com.epam.career.page_objects.site.sections.AddCVForm;
import com.ggasoftware.jdiuitest.web.selenium.elements.composite.WebPage;
import com.ggasoftware.jdiuitest.core.interfaces.base.IElement;
import com.ggasoftware.jdiuitest.core.interfaces.common.IButton;
import org.openqa.selenium.support.FindBy;

/**
 * Created by Roman_Iovlev on 10/22/2015.
 */
public class JobDescriptionPage extends WebPage {
    @FindBy(xpath = "//*[.='Apply Now']")
    public IButton applyNowButton;

    @FindBy(css = ".vac-sidebar .form-constructor")
    public AddCVForm addCVForm;

    @FindBy(id = "captcha-input")
    public IElement captcha;

}
