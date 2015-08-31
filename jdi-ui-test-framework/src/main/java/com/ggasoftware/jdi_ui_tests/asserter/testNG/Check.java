package com.ggasoftware.jdi_ui_tests.asserter.testNG;

import com.ggasoftware.jdi_ui_tests.asserter.BaseChecker;
import org.testng.Assert;

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
