package com.ggasoftware.jdi_ui_tests.utils.usefulUtils;

import com.ggasoftware.jdi_ui_tests.utils.linqInterfaces.JActionEx;
import com.ggasoftware.jdi_ui_tests.utils.linqInterfaces.JFuncTEx;

/**
 * Created by Roman_Iovlev on 6/9/2015.
 */
public class TryCatchUtil {
    public static <T> T tryGetResult(JFuncTEx<T> waitCase)
    {
        try { return waitCase.invoke(); }
        catch(Exception ex) { return null; }
    }
    public static void ignoreException(JActionEx action) {
        try { action.invoke();
        } catch (Exception ignore) { }
    }
}
