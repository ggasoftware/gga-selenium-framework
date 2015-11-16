package com.ggasoftware.jdiuitest.core.interfaces.settings;

import com.ggasoftware.jdiuitest.core.utils.linqinterfaces.JFuncT;
import com.ggasoftware.jdiuitest.core.utils.linqinterfaces.JFuncTEx;

/**
 * Created by Roman_Iovlev on 6/9/2015.
 */
public interface IAsserter {
    RuntimeException exception(String message, Object... args);
    <TResult> TResult silent(JFuncTEx<TResult> func);
    <T> void areEquals(T actual, T expected);
    void matches(String actual, String regEx);
    void contains(String actual, String expected);
    void isTrue(Boolean actual);
    void isTrue(JFuncT<Boolean> actual);
}
