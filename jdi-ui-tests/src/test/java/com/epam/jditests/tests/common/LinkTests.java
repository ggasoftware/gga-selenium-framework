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
import com.ggasoftware.jdiuitests.implementation.selenium.elements.interfaces.common.ILink;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

import static com.epam.jditests.enums.Preconditions.HOME_PAGE;
import static com.epam.jditests.enums.Preconditions.SUPPORT_PAGE;
import static com.epam.jditests.pageobjects.EpamJDISite.*;
import static com.epam.jditests.tests.complex.CommonActionsData.*;
import static com.ggasoftware.jdiuitests.implementation.testng.asserter.Assert.isTrue;

public class LinkTests extends InitTests {
    public final static String TEXT = "About";
    public final static String HREF = "http://ecse00100176.epam.com/page3.htm";
    JFuncT<IElement> get = () -> footer.about;
    private Preconditions _onPage = HOME_PAGE;

    @BeforeMethod
    public void before(final Method method) {
        isInState(_onPage, method);
    }

    @Test
    public void textWaitTestWithButton() {
        ((IClickable) get.invoke()).click();
        supportPage.checkOpened();
    }

    // reference
    @Test
    public void waitReferenceTest() {
        isInState(SUPPORT_PAGE);
        runParallel(_onPage::open);
        checkText(() -> ((ILink) get.invoke()).waitReference("page3.htm"), HREF);
        isTrue(timer.timePassedInMSec() > waitTimeOut);
    }

    @Test
    public void waitMatchReferenceTest() throws Exception {
        isInState(SUPPORT_PAGE);
        runParallel(_onPage::open);
        checkText(() -> ((ILink) get.invoke()).waitMatchReference(".*htm"), HREF);
        isTrue(timer.timePassedInMSec() > waitTimeOut);
    }
    // !reference

    @Factory
    public Object[] factory() {
        return new Object[]{new SimpleTextTests(TEXT, _onPage, get),
                new MatchTextTests(TEXT, "Abou.*", _onPage, get),
                new ContainsTextTests(TEXT, "About", _onPage, get),
                new AttributeTests("testAttribute", "testValue", _onPage, get)};
    }
}