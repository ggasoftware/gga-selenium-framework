package com.ggasoftware.uitest.control.base.asserter;

/**
 * Created by Roman_Iovlev on 6/9/2015.
 */
public interface IAsserter {
    RuntimeException exception(String message);

    void areEquals(Object obj1, Object obj2);

    void matches(String str, String regEx);

    void contains(String str1, String str2);
}
