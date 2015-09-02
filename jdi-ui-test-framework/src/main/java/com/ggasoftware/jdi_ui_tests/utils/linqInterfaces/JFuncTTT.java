package com.ggasoftware.jdi_ui_tests.utils.linqInterfaces;

/**
 * Created by 12345 on 30.09.2014.
 */
public interface JFuncTTT<T1, T2, TResult> {
    TResult invoke(T1 val1, T2 val2) throws RuntimeException;
}
