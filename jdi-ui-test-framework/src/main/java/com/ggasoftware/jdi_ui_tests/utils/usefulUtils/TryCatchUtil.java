package com.ggasoftware.jdi_ui_tests.utils.usefulUtils;

import com.ggasoftware.jdi_ui_tests.utils.linqInterfaces.JAction;
import com.ggasoftware.jdi_ui_tests.utils.linqInterfaces.JFuncT;

/**
 * Created by Roman_Iovlev on 6/9/2015.
 */
public class TryCatchUtil {
    public static <T> T tryGetResult(JFuncT<T> waitCase)
    {
        try { return waitCase.invoke(); }
        catch(Exception ex) { return null; }
    }
    public static void ignoreException(JAction action) {
        try { action.invoke();
        } catch (Exception ignore) { }
    }
}
