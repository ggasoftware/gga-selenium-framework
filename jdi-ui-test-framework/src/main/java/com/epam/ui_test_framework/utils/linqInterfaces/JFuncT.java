package com.epam.ui_test_framework.utils.linqInterfaces;

/**
 * Created by 12345 on 30.09.2014.
 */
public interface JFuncT<TResult> {
    TResult invoke() throws Exception;
}
