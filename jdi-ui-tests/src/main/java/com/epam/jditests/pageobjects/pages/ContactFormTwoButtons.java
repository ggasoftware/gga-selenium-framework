package com.epam.jditests.pageobjects.pages;

import com.epam.jditests.entities.Contact;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.composite.Form;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.interfaces.common.IButton;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.interfaces.common.ITextArea;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.interfaces.common.ITextField;
import org.openqa.selenium.support.FindBy;

/**
 * Created by Dmitry_Lebedev1 on 10/16/2015.
 */
public class ContactFormTwoButtons extends Form<Contact> {

    @FindBy(id = "Name")
    public ITextField name;

    @FindBy(id = "LastName")
    public ITextField lastName;

    @FindBy(xpath = "//*[text()='Submit']")
    public IButton submit;

    @FindBy(id = "Description")
    public ITextArea description;

    @FindBy(xpath = "//*[text()=\"Calculate\"]")
    public IButton calculate;
}
