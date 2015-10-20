package com.epam.jditests.tests.common;

import static com.epam.jditests.enums.Preconditions.DATES_PAGE_FILLED;
import static com.epam.jditests.pageobjects.EpamJDISite.isInState;
import static com.epam.jditests.pageobjects.EpamJDISite.*;
import static com.epam.jditests.CommonData.TEST_DATE;

import java.io.IOException;
import java.lang.reflect.Method;

import com.ggasoftware.jdiuitests.core.utils.linqInterfaces.JFuncT;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import com.epam.jditests.InitTests;
import com.epam.jditests.enums.Preconditions;
import com.epam.jditests.tests.common.utils.AttributeTests;
import com.epam.jditests.tests.common.utils.ContainsTextTests;
import com.epam.jditests.tests.common.utils.InputTests;
import com.epam.jditests.tests.common.utils.MatchTextTests;
import com.epam.jditests.tests.common.utils.SimpleTextTests;
import com.ggasoftware.jdiuitests.core.utils.common.Timer;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.interfaces.base.IElement;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.interfaces.common.ITextArea;

public class DatePickerTests extends InitTests {
    private Preconditions _onPage = DATES_PAGE_FILLED;
    JFuncT<IElement> get = () -> dates.datepicker;

    public ITextArea input() {
        return (ITextArea) get.invoke();
    }

    @BeforeMethod
    public void before(final Method method) throws IOException {
        isInState(_onPage, method);
    }

    @AfterMethod
    public void after() {
        _onPage.open();
    }


    @Factory
    public Object[] factory() {
        return new Object[]{new SimpleTextTests(TEST_DATE, _onPage, get),
                new MatchTextTests(TEST_DATE, "([0-9]{2}[\\/]{1}){2}[0-9]{4}", _onPage, get),
                new ContainsTextTests(TEST_DATE, "1945", _onPage, get),
                new AttributeTests("testAttribute", "testValue", _onPage, get),
                new InputTests(Timer.nowTime("MM/dd/yyyy"), _onPage, get),
        };
    }
}
