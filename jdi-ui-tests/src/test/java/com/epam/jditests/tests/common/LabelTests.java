package com.epam.jditests.tests.common;

import com.epam.jditests.InitTests;
import com.epam.jditests.enums.Preconditions;
import com.epam.jditests.tests.common.utils.AttributeTests;
import com.epam.jditests.tests.common.utils.ContainsTextTests;
import com.epam.jditests.tests.common.utils.MatchTextTests;
import com.epam.jditests.tests.common.utils.SimpleTextTests;
import com.ggasoftware.jdiuitests.core.utils.linqinterfaces.JFuncT;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.interfaces.base.IClickable;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.interfaces.base.IElement;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

import static com.epam.jditests.enums.Preconditions.METALS_AND_COLORS_PAGE;
import static com.epam.jditests.pageobjects.EpamJDISite.isInState;
import static com.epam.jditests.pageobjects.EpamJDISite.metalsColorsPage;
import static com.epam.jditests.tests.complex.CommonActionsData.checkCalculate;

@Test(testName = "Label")
public class LabelTests extends InitTests {
    public static final String TEXT = "CALCULATE";
    JFuncT<IElement> get = () -> metalsColorsPage.calculate;
    private Preconditions _onPage = METALS_AND_COLORS_PAGE;

    @BeforeMethod
    public void before(final Method method) {
        isInState(_onPage, method);
    }

    @Test
    public void textWaitTestWithButton() {
        ((IClickable) get.invoke()).click();
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