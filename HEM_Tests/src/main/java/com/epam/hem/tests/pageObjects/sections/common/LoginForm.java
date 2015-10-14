package com.epam.hem.tests.pageObjects.sections.common;

import com.epam.hem.tests.entities.User;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.common.Button;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.common.TextField;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.composite.Form;
import org.openqa.selenium.support.FindBy;

/**
 * Created by Pavel_Shcherbakov1 on 2015-10-08
 */
public class LoginForm extends Form<User> {

    @FindBy(css = "[name=username]")
    private TextField userName;
    @FindBy(css = "[name=password]")
    private TextField password;
    @FindBy(css = "[name=submit]")
    private Button loginButton;
}
