package com.epam.jditests.pageobjects.pages;

import com.epam.jditests.entities.User;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.composite.Form;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.interfaces.base.IClickable;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.interfaces.common.IButton;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.interfaces.common.ITextField;
import org.openqa.selenium.support.FindBy;

/**
 * Created by Maksim_Palchevskii on 9/9/2015.
 */
public class Login extends Form<User> {
    @FindBy(id = "Login")
    private ITextField login;
    @FindBy(id = "Password")
    private ITextField password;
    @FindBy(css = ".btn-login")
    private IButton loginButton;
    @FindBy(css = ".profile-photo")
    private IClickable profile;

    @Override
    public void submit(User user) {
        profile.click();
        super.submit(user);
    }
}
