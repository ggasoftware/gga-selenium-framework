package com.ggasoftware.jdiuitest.core.utils.linqinterfaces;

/**
 * Created by 12345 on 30.09.2014.
 */
public interface JActionT<T> {
    void invoke(T val) throws RuntimeException;
}
