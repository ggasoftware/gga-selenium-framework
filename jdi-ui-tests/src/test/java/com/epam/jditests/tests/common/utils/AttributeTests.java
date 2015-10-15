package com.epam.jditests.tests.common.utils;

import com.epam.jditests.enums.Preconditions;
import com.ggasoftware.jdiuitests.core.utils.linqInterfaces.JFuncT;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.interfaces.base.IElement;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

import static com.epam.jditests.pageobjects.EpamJDISite.isInState;
import static com.epam.jditests.tests.complex.CommonActionsData.checkText;

public class AttributeTests {
    private String _value;
    private Preconditions _onPage;
    private String _attributeName;
    private JFuncT<IElement> _element;

    public AttributeTests(String attributeName, String value, Preconditions onPage, JFuncT<IElement> element) {
        _value = value;
        _attributeName = attributeName;
        _onPage = onPage;
        _element = element;
    }

    @BeforeMethod
    public void before(final Method method) {
        isInState(_onPage, method);
    }

    @Test
    public void setAttributeTest() {
        _element.invoke().setAttribute(_attributeName, _value);
        checkText(() -> _element.invoke().getWebElement().getAttribute(_attributeName), _value);
    }

    // TODO
//	@Test
//	public void getAttributeTest() throws Exception {
//		
//	}
}
