package com.ggasoftware.jdiuitests.core.utils.linqinterfaces;

/**
 * Created by 12345 on 30.09.2014.
 */
public interface JFuncT<TResult> {
    TResult invoke() throws RuntimeException;
}
