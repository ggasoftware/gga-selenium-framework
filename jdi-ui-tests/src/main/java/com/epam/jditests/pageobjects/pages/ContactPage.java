package com.epam.jditests.pageobjects.pages;

import com.ggasoftware.jdiuitests.implementation.selenium.elements.composite.Page;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.interfaces.common.*;
import org.openqa.selenium.support.FindBy;

/**
 * Created by Maksim_Palchevskii on 8/17/2015.
 */
public class ContactPage extends Page {
    @FindBy(id = "Name")
    public ITextField name;
    @FindBy(id = "LastName")
    public ITextField lastName;
    @FindBy(id = "Description")
    public ITextArea description;
    @FindBy(xpath = "//*[text()='Submit']")
    public IButton contactSubmit;
    @FindBy(css = ".epam-logo img")
    public IImage logoImage;
    @FindBy(css = ".results")
    public IText result;
    @FindBy(css = "main form")
    public ContactForm feedForm;
}
