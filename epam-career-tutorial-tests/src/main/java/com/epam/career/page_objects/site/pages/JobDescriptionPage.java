package com.epam.career.page_objects.site.pages;

import com.epam.career.page_objects.site.sections.AddCVForm;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.composite.Page;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.interfaces.base.IElement;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.interfaces.common.IButton;
import org.openqa.selenium.support.FindBy;

/**
 * Created by Roman_Iovlev on 10/22/2015.
 */
public class JobDescriptionPage extends Page {
    @FindBy(xpath = "//*[.='Apply Now']")
    public IButton applyNowButton;

    @FindBy(css = ".vac-sidebar .form-constructor")
    public AddCVForm addCVForm;

    @FindBy(id = "captcha-input")
    public IElement captcha;

}
