package com.epam.jdi_tests.page_objects.sections;

import com.ggasoftware.jdiuitests.implementation.selenium.elements.composite.Section;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.interfaces.common.ILink;
import org.openqa.selenium.support.FindBy;

/**
 * Created by Fail on 15.09.2015.
 */
public class Footer extends Section {

    @FindBy(partialLinkText = "About")
    public ILink about;

}
