package com.epam.jditests.tests.common;

import com.epam.jditests.InitTests;
import com.epam.jditests.enums.Preconditions;
import com.epam.jditests.tests.common.utils.*;
import com.ggasoftware.jdiuitest.core.interfaces.common.ITextArea;
import com.ggasoftware.jdiuitest.core.utils.common.Timer;
import com.ggasoftware.jdiuitest.core.utils.linqinterfaces.JFuncT;
import com.ggasoftware.jdiuitest.web.selenium.elements.base.Element;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;

import java.io.IOException;
import java.lang.reflect.Method;

import static com.epam.jditests.CommonData.TEST_DATE;
import static com.epam.jditests.enums.Preconditions.DATES_PAGE_FILLED;
import static com.epam.jditests.pageobjects.EpamJDISite.dates;
import static com.epam.jditests.pageobjects.EpamJDISite.isInState;

public class DatePickerTests extends InitTests {
    JFuncT<Element> get = () -> dates.datepicker;
    private Preconditions _onPage = DATES_PAGE_FILLED;

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
