package com.ggasoftware.jdi_ui_tests.implementation.junit.asserter;

import com.ggasoftware.jdi_ui_tests.core.asserter.BaseChecker;
import com.ggasoftware.jdi_ui_tests.core.utils.linqInterfaces.JActionT;
import org.junit.Assert;

/**
 * Created by Roman_Iovlev on 6/9/2015.
 */
public class Check extends BaseChecker {
    @Override
    protected JActionT<String> throwFail() { return Assert::fail; }
    public Check() { super(); }
    public Check(String checkMessage) {
        super(checkMessage);
    }
}
