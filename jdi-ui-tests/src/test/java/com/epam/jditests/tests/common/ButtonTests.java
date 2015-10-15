package com.epam.jditests.tests.common;

import com.epam.jditests.InitTests;
import com.epam.jditests.enums.Preconditions;
import com.epam.jditests.tests.common.utils.*;
import com.ggasoftware.jdiuitests.core.utils.linqInterfaces.JFuncT;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.interfaces.base.IClickable;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.interfaces.base.IElement;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.interfaces.common.IButton;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

import static com.epam.jditests.enums.Preconditions.METALS_AND_COLORS_PAGE;
import static com.epam.jditests.pageobjects.EpamJDISite.homePage;
import static com.epam.jditests.pageobjects.EpamJDISite.isInState;
import static com.epam.jditests.pageobjects.EpamJDISite.metalsColorsPage;
import static com.epam.jditests.tests.complex.CommonActionsData.checkCalculate;

public class ButtonTests extends InitTests {

    public static final String TEXT = "CALCULATE";
    private Preconditions _onPage = METALS_AND_COLORS_PAGE;
    JFuncT<IElement> get = () -> metalsColorsPage.calculateButton;

    @BeforeMethod
    public void before(final Method method) {
        isInState(_onPage, method);
    }

    @Test
    public void textWaitTestWithButton() {
        ((IClickable)get.invoke()).click();
        checkCalculate("Summary: 3");
    }

    @Factory
    public Object[] factory() {
        return new Object[]{
                new SimpleTextTests(TEXT, _onPage, get),
                new MatchTextTests(TEXT, ".*LCU.*", _onPage, get),
                new ContainsTextTests(TEXT, "CUL", _onPage, get),
                new AttributeTests("testAttribute", "testValue", _onPage, get)
        };
    }
}
