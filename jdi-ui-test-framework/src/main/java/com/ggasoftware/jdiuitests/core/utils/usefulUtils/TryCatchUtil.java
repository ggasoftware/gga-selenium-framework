package com.ggasoftware.jdiuitests.core.utils.usefulUtils;

import com.ggasoftware.jdiuitests.core.utils.linqInterfaces.JFuncTEx;

import static com.ggasoftware.jdiuitests.core.settings.JDISettings.exceptionThrown;

/**
 * Created by Roman_Iovlev on 6/9/2015.
 */
public class TryCatchUtil {
    public static <T> T tryGetResult(JFuncTEx<T> waitCase) {
        try {
            return waitCase.invoke();
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public static void throwRuntimeException(Throwable ignore) {
        if (exceptionThrown)
            throw (ignore instanceof RuntimeException) ? (RuntimeException) ignore : new RuntimeException(ignore);
    }
}
