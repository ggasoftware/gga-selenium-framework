package com.epam.ui_test_framework.asserter;

import com.epam.ui_test_framework.utils.linqInterfaces.JAction;
import com.epam.ui_test_framework.utils.linqInterfaces.JFuncT;
import org.testng.Assert;

import static com.epam.ui_test_framework.logger.enums.LogInfoTypes.FRAMEWORK;
import static com.epam.ui_test_framework.settings.FrameworkSettings.logger;

/**
 * Created by Roman_Iovlev on 6/9/2015.
 */
public class TestNGAsserter extends Assert implements IAsserter {
    public Exception exception(String message) {
        logger.error(FRAMEWORK, message);
        assertTrue(false, message);
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
