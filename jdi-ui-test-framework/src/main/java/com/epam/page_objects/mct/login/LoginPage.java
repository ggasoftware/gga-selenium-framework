package com.epam.page_objects.mct.login;

import com.epam.ui_test_framework.elements.composite.Form;
import com.epam.ui_test_framework.elements.page_objects.annotations.Function;
import com.epam.ui_test_framework.elements.page_objects.annotations.Name;
import com.epam.ui_test_framework.elements.common.Button;
import com.epam.ui_test_framework.elements.common.Input;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends Form {

    public static LoginPage loginPage = InitElements(new LoginPage());

    @FindBy(css = "input[name=login]") @Name(value = "login")
    private Input userName;
    @FindBy(css = "input[name=password]")
    private Input password;
    @FindBy(css = "button[type=submit]") @Function(value = "submit")
    private Button submitButton;

}
