package com.epam.jditests.pageobjects.pages;

import com.ggasoftware.jdiuitest.core.interfaces.common.IFileInput;
import com.ggasoftware.jdiuitest.web.robot.RFileInput;
import com.ggasoftware.jdiuitest.web.selenium.elements.common.DatePicker;
import com.ggasoftware.jdiuitest.web.selenium.elements.composite.WebPage;
import org.openqa.selenium.support.FindBy;

/**
 * Created by Maksim_Palchevskii on 8/17/2015.
 */
public class DatesPage extends WebPage {
    @FindBy(css = "#datepicker input")
    public DatePicker datepicker;
    @FindBy(css = "[data-provides=fileinput]")
    public IFileInput imageInput;
    @FindBy(css = "[data-provides=fileinput]")
    public RFileInput rImageInput;

}
