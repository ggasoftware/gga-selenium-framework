package com.ggasoftware.uitest.utils.asserter;

import com.ggasoftware.uitest.utils.linqInterfaces.*;

/**
 * Created by Roman_Iovlev on 6/9/2015.
 */
public interface IAsserter {
    Exception exception(String message);
    void silent(JAction action);
    <TResult> TResult silent(JFuncT<TResult> func);
    void areEquals(Object obj1, Object obj2);
    void matches(String str, String regEx);
    void contains(String str1, String str2);
}
