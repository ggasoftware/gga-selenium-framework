package com.epam.jditests.tests.common;

import com.epam.jditests.InitTests;
import com.epam.jditests.enums.Preconditions;
import com.epam.jditests.tests.common.utils.*;
import com.ggasoftware.jdiuitests.core.utils.linqinterfaces.JFuncT;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.interfaces.base.IElement;
import org.testng.annotations.Factory;

import static com.epam.jditests.entities.User.DEFAULT_USER;
import static com.epam.jditests.enums.Preconditions.CONTACT_PAGE_FILLED;
import static com.epam.jditests.pageobjects.EpamJDISite.contactFormPage;

public class TextFieldTests extends InitTests {

    public static final String TEXT = DEFAULT_USER.name;
    JFuncT<IElement> get = () -> contactFormPage.name;
    private Preconditions _onPage = CONTACT_PAGE_FILLED;

    @Factory
    public Object[] factory() {
        return new Object[]{
                new SimpleTextTests(TEXT, _onPage, get),
                new MatchTextTests(TEXT, ".am.", _onPage, get),
                new ContainsTextTests(TEXT, "ame", _onPage, get),
                new AttributeTests("testAttribute", "testValue", _onPage, get),
                new InputTests("inputText", _onPage, get),
                new InputTests("1234567890", _onPage, get),
                new InputTests("!@#^&*()_", _onPage, get)
        };
    }
}
