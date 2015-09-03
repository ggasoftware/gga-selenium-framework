package com.ggasoftware.jdi_ui_tests.implementation.junit.asserter;

import com.ggasoftware.jdi_ui_tests.core.asserter.BaseChecker;
import org.junit.Assert;

/**
 * Created by Roman_Iovlev on 6/9/2015.
 */
public class Check extends BaseChecker {
    public Check() { this(""); }
    public Check(String checkMessage) {
        super(checkMessage);
        setThrowFail(Assert::fail);
    }
}
