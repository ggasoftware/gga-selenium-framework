package com.ggasoftware.jdi_ui_tests.core.asserter;

import com.ggasoftware.jdi_ui_tests.utils.linqInterfaces.*;

/**
 * Created by Roman_Iovlev on 6/9/2015.
 */
public interface IAsserter {
    RuntimeException exception(String message);
    void silent(JActionEx action);
    <TResult> TResult silent(JFuncExT<TResult> func);
    void areEquals(Object obj1, Object obj2);
    void matches(String str, String regEx);
    void contains(String str1, String str2);
}
