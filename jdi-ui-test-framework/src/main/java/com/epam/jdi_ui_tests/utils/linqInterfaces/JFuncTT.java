package com.epam.jdi_ui_tests.utils.linqInterfaces;

/**
 * Created by 12345 on 30.09.2014.
 */
public interface JFuncTT<T1, TResult> {
    TResult invoke(T1 val1) throws Exception;
}
