package com.ggasoftware.jdi_ui_tests.utils.linqInterfaces;

/**
 * Created by roman.i on 01.10.2014.
 */
public interface JFuncTTTT<T1, T2, T3, TResult> {
    TResult invoke(T1 val1, T2 val2, T3 val3) throws RuntimeException;
}
