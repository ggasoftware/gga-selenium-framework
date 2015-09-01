package com.ggasoftware.uitest.utils.linqInterfaces;

/**
 * Created by 12345 on 30.09.2014.
 */
public interface JActionTT<T1, T2> {
    void invoke(T1 val1, T2 val2) throws RuntimeException;
}
