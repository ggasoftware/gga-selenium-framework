package com.ggasoftware.jdiuitests.core.asserter;

import com.ggasoftware.jdiuitests.core.utils.linqinterfaces.JFuncTEx;

/**
 * Created by Roman_Iovlev on 6/9/2015.
 */
public interface IAsserter {
    RuntimeException exception(String message, Object... args);

    <TResult> TResult silent(JFuncTEx<TResult> func);

    <T> void areEquals(T obj1, T obj2);

    void matches(String str, String regEx);

    void contains(String str1, String str2);
}
