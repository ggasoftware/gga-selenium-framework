package com.ggasoftware.uitest.utils.linqInterfaces;

/**
 * Created by 12345 on 30.09.2014.
 */
public interface JFuncTTR<T1, TResult> {
    TResult invoke(T1 val1) throws RuntimeException;
}
