package com.epam.career.page_objects.site.sections;

import com.epam.career.page_objects.entities.Attendee;
import com.ggasoftware.jdiuitests.implementation.robot.elements.common.RFileInput;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.complex.Dropdown;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.composite.Form;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.interfaces.common.IButton;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.interfaces.common.ITextArea;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.interfaces.common.ITextField;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.interfaces.complex.IDropDown;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

/**
 * Created by Roman_Iovlev on 10/23/2015.
 */
public class AddCVForm extends Form<Attendee> {
    @FindBy(css = "[placeholder='First Name']")
    private ITextField name;
    @FindBy(css = "[placeholder='Last Name']")
    private ITextField lastName;
    @FindBy(css = "[placeholder='Email']")
    private ITextField email;
    @FindBy(css = ".country-selection")
    private IDropDown country = new Dropdown<>(By.cssSelector(".country-wrapper .arrow"),
            By.xpath("//*[contains(@id,'select-box-applicantCountry')]//li"));
    @FindBy(css = ".city-selection")
    private IDropDown city = new Dropdown<>(By.cssSelector(".city-wrapper .arrow"),
            By.xpath("//*[contains(@id,'select-box-applicantCity')]//li"));
    @FindBy(css = ".file-upload")
    private RFileInput cv;
    @FindBy(css = ".comment-input")
    private ITextArea comment;

    @FindBy(xpath = "//*[.='Submit']")
    private IButton submit;
    @FindBy(xpath = "//*[.='Cancel']")
    private IButton cancel;


}
