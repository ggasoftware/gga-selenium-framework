package com.ggasoftware.jdi_ui_tests.core.elements.base;

/**
 * Created by Roman_Iovlev on 6/10/2015.
 */
public interface IDriver<TDriver> {
    TDriver get() throws Exception;
    void close();
}
