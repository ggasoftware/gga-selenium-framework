package com.ggasoftware.jdi_ui_tests.utils.linqInterfaces;

/**
 * Created by 12345 on 30.09.2014.
 */
public interface JActionT<T> {
    void invoke(T val) throws RuntimeException;
}
