package com.ggasoftware.jdi_ui_tests.implementations.asserter;

import com.ggasoftware.jdi_ui_tests.core.asserter.IAsserter;
import com.ggasoftware.jdi_ui_tests.utils.linqInterfaces.*;
import com.ggasoftware.jdi_ui_tests.utils.usefulUtils.ScreenshotMaker;
import org.junit.Assert;

import static com.ggasoftware.jdi_ui_tests.core.logger.enums.LogInfoTypes.FRAMEWORK;
import static com.ggasoftware.jdi_ui_tests.core.settings.JDISettings.logger;
import static com.ggasoftware.jdi_ui_tests.utils.common.StringUtils.LineBreak;

/**
 * Created by Roman_Iovlev on 6/9/2015.
 */
public class JUnitAsserter extends Assert implements IAsserter {
    private boolean doScreenshots;

    public JUnitAsserter() { this(false); }
    public JUnitAsserter(boolean doScreenshots) {
        this.doScreenshots = doScreenshots;
    }

    public RuntimeException exception(String message) {
        if (doScreenshots) {
            String resultMessage = ScreenshotMaker.doScreenshotGetMessage();
            message = LineBreak + resultMessage + LineBreak + message;
        }
        logger.error(FRAMEWORK, message);
        return new RuntimeException(message);
    }
    public void silent(JActionEx action) {
        try { action.invoke();
        } catch (Exception ex) { throw exception(ex.getMessage()); }
    }
    public <TResult> TResult silent(JFuncExT<TResult> func) {
        try { return func.invoke();
        } catch (Exception ex) { throw exception(ex.getMessage()); }
    }
    public void areEquals(Object obj1, Object obj2) {
        assertEquals(obj1, obj2);
    }
    public void matches(String str, String regEx) {
        assertTrue(str.matches(regEx));
    }
    public void contains(String str, String str2) {
        assertTrue(str.contains(str2));
    }

}
