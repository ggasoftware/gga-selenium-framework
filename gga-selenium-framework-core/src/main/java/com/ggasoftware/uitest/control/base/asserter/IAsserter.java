package com.ggasoftware.uitest.control.base.asserter;


import com.ggasoftware.uitest.utils.linqInterfaces.*;

/**
 * Created by Roman_Iovlev on 6/9/2015.
 */
public interface IAsserter {
    Exception exception(String message);
    void silentException(JAction action);
    <TResult> TResult silentException(JFuncT<TResult> func);
}
