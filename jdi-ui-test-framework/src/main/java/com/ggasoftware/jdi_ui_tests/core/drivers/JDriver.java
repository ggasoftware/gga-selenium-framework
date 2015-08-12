package com.ggasoftware.jdi_ui_tests.core.drivers;

/**
 * Created by Roman_Iovlev on 6/10/2015.
 */
public interface JDriver<TDriver> {
    TDriver get() throws Exception;
    void close();
}
