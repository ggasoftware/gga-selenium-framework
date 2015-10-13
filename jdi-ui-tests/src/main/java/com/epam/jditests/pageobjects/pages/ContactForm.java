package com.epam.jditests.pageobjects.pages;

import org.openqa.selenium.support.FindBy;

import com.ggasoftware.jdiuitests.implementation.selenium.elements.composite.Page;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.interfaces.common.IButton;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.interfaces.common.ITextArea;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.interfaces.common.ITextField;

/**
 * Created by Maksim_Palchevskii on 8/17/2015.
 */
public class ContactForm extends Page {
    @FindBy(id = "Name")
    public ITextField name;
    @FindBy(id = "LastName")
    public ITextField lastName;
    @FindBy(id = "Description")
    public ITextArea description;
    @FindBy(xpath = ".//main//button")
    public IButton contactSubmit;
}
