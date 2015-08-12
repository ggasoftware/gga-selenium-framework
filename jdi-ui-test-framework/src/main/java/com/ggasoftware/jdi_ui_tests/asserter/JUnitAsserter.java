package com.ggasoftware.jdi_ui_tests.asserter;

import com.ggasoftware.jdi_ui_tests.utils.linqInterfaces.JAction;
import com.ggasoftware.jdi_ui_tests.utils.linqInterfaces.JFuncT;
import com.ggasoftware.jdi_ui_tests.utils.usefulUtils.ScreenshotMaker;
import org.junit.Assert;

import static com.ggasoftware.jdi_ui_tests.logger.enums.LogInfoTypes.FRAMEWORK;
import static com.ggasoftware.jdi_ui_tests.settings.JDISettings.logger;
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

    public Exception exception(String message) {
        if (doScreenshots) {
            String resultMessage = ScreenshotMaker.doScreenshotGetMessage();
            message = LineBreak + resultMessage + LineBreak + message;
        }
        logger.error(FRAMEWORK, message);
        return new Exception(message);
    }
    public void silent(JAction action) {
        try { action.invoke();
        } catch (Exception ex) { exception(ex.getMessage()); }
    }
    public <TResult> TResult silent(JFuncT<TResult> func) {
        try { return func.invoke();
        } catch (Exception ex) { exception(ex.getMessage()); }
        return null;
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
