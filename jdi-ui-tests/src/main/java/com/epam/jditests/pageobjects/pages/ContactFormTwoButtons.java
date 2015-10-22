package com.epam.jditests.pageobjects.pages;

import com.epam.jditests.entities.Contact;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.composite.Form;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.interfaces.common.IButton;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.interfaces.common.ITextArea;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.interfaces.common.ITextField;
import org.openqa.selenium.support.FindBy;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Dmitry_Lebedev1 on 10/16/2015.
 */
public class ContactFormTwoButtons extends Form<Contact> {

    @FindBy(id = "Name")
    public ITextField name;

    @FindBy(id = "LastName")
    public ITextField lastName;

    @FindBy(id = "Description")
    public ITextArea description;

    @FindBy(xpath = "//*[text()='Submit']")
    public IButton submit;

    @FindBy(xpath = "//*[text()='Calculate']")
    public IButton calculate;

    public List<String> getFormValue() {
        return Arrays.asList(new String[]{
                name.getWebElement().getAttribute("value"),
                lastName.getWebElement().getAttribute("value"),
                description.getWebElement().getAttribute("value")});
    }
    public void fillForm(Contact contact) {
        name.getWebElement().clear();
        lastName.getWebElement().clear();
        description.getWebElement().clear();
        name.getWebElement().sendKeys(contact.name);
        lastName.getWebElement().sendKeys(contact.lastName);
        description.getWebElement().sendKeys(contact.description);
    }
}
