package com.ggasoftware.jdiuitests.core.utils.linqinterfaces;

/**
 * Created by 12345 on 30.09.2014.
 */
public interface JFuncTT<T1, TResult> {
    TResult invoke(T1 val1) throws RuntimeException;
}
