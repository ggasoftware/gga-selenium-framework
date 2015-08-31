package com.ggasoftware.uitest.utils.linqInterfaces;

/**
 * Created by 12345 on 30.09.2014.
 */
public interface JFuncTR<TResult> {
    TResult invoke() throws RuntimeException;
}
