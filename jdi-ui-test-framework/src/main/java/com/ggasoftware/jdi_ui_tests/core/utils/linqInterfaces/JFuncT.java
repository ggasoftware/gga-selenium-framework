package com.ggasoftware.jdi_ui_tests.core.utils.linqInterfaces;

/**
 * Created by 12345 on 30.09.2014.
 */
public interface JFuncT<TResult> {
    TResult invoke() throws RuntimeException;
}
