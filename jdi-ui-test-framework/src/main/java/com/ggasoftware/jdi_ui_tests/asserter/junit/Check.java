package com.ggasoftware.jdi_ui_tests.asserter.junit;

import com.ggasoftware.jdi_ui_tests.asserter.BaseChecker;
import org.junit.Assert;

/**
 * Created by Roman_Iovlev on 6/9/2015.
 */
public class Check extends BaseChecker {
    public Check() { }
    public Check(String checkMessage) {
        super(checkMessage);
        setThrowFail(Assert::fail);
    }
}
