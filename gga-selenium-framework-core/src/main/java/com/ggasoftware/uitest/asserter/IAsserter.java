package com.ggasoftware.uitest.asserter;

import com.ggasoftware.uitest.utils.linqInterfaces.JAction;
import com.ggasoftware.uitest.utils.linqInterfaces.JFuncT;

/**
 * Created by Roman_Iovlev on 6/9/2015.
 */
public interface IAsserter {
    Exception exception(String message);
    void silentException(JAction action);
    <TResult> TResult silentException(JFuncT<TResult> func);
}
