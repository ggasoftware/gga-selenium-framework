package com.ggasoftware.jdi_ui_tests.asserter;

import com.ggasoftware.jdi_ui_tests.utils.linqInterfaces.JFuncTEx;

/**
 * Created by Roman_Iovlev on 6/9/2015.
 */
public interface IAsserter {
    RuntimeException exception(String message);
    <TResult> TResult silent(JFuncTEx<TResult> func);
    void areEquals(Object obj1, Object obj2);
    void matches(String str, String regEx);
    void contains(String str1, String str2);
}
