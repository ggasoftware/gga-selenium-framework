package com.epam.ui_test_framework.asserter;

import com.epam.ui_test_framework.utils.linqInterfaces.JAction;
import com.epam.ui_test_framework.utils.linqInterfaces.JFuncT;

/**
 * Created by Roman_Iovlev on 6/9/2015.
 */
public interface IAsserter {
    Exception exception(String message);
    void silentException(JAction action);
    <TResult> TResult silentException(JFuncT<TResult> func);
    void areEquals(Object obj1, Object obj2);
    void matches(String str, String regEx);
    void contains(String str1, String str2);
}
