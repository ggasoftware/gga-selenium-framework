package com.ggasoftware.uitest.control.base.asserter;

import com.ggasoftware.uitest.utils.linqInterfaces.*;
import org.junit.Assert;

import static com.ggasoftware.uitest.control.base.logger.TestNGLog4JLogger.logger;
import static com.ggasoftware.uitest.control.base.logger.enums.LogInfoTypes.FRAMEWORK;


/**
 * Created by Roman_Iovlev on 6/9/2015.
 */
public class JUnitAsserter extends Assert implements IAsserter {

    public Exception exception(String message) {
        logger.error(FRAMEWORK, message);
        assertTrue(message, false);
        return new Exception(message);
    }
    public void silentException(JAction action) {
        try { action.invoke();
        } catch (Exception ex) { exception(ex.getMessage()); }
    }
    public <TResult> TResult silentException(JFuncT<TResult> func) {
        try { return func.invoke();
        } catch (Exception ex) { exception(ex.getMessage()); }
        return null;
    }


}
