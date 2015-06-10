package com.ggasoftware.uitest.utils;

import com.ggasoftware.uitest.utils.linqInterfaces.JAction;

/**
 * Created by Roman_Iovlev on 6/9/2015.
 */
public class TryCatchUtil {
    public static void ignoreExceptions(JAction action) {
        try { action.invoke();
        } catch (Exception ignore) { }
    }
}
