package com.epam.jditests.pageobjects.sections;

import com.ggasoftware.jdiuitest.web.selenium.elements.common.Link;
import com.ggasoftware.jdiuitest.web.selenium.elements.composite.Section;
import org.openqa.selenium.support.FindBy;

/**
 * Created by Fail on 15.09.2015.
 */
public class Footer extends Section {

    @FindBy(partialLinkText = "About")
    public Link about;

}
