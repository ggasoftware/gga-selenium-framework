package com.ggasoftware.jdi_ui_tests.core.asserter;

/**
 * Created by Roman_Iovlev on 9/18/2015.
 */
public class JDIException extends RuntimeException {
    public JDIException(Exception ex) {
        super(ex.getMessage());
    }
    public JDIException(AssertionError ex) {
        super(ex.getMessage());
    }
    public JDIException(String msg) {
        super(msg);
    }
}
