package com.ggasoftware.uitest.control.base.usefulUtils;


import com.ggasoftware.uitest.utils.linqInterfaces.*;

/**
 * Created by Roman_Iovlev on 6/9/2015.
 */
public class TryCatchUtil {
    public static <T> T tryGetResult(JFuncTEx<T> waitCase)
    {
        try { return waitCase.invoke(); }
        catch(Exception|AssertionError ex) { return null; }
    }
    public static void ignoreException(JActionEx action) {
        try { action.invoke();
        } catch (Exception ignore) { }
    }
}
