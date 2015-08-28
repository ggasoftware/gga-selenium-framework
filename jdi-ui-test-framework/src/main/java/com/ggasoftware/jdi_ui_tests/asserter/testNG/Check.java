package com.ggasoftware.jdi_ui_tests.asserter.testNG;

import com.ggasoftware.jdi_ui_tests.asserter.BaseChecker;
import com.ggasoftware.jdi_ui_tests.asserter.DoScreen;
import org.testng.Assert;

import static com.ggasoftware.jdi_ui_tests.asserter.DoScreen.NO_SCREEN;

/**
 * Created by Roman_Iovlev on 6/9/2015.
 */
public class Check extends BaseChecker {
    public Check() { }
    public Check(DoScreen doScreenshot) { this(null, doScreenshot); }
    public Check(String checkMessage) { this(checkMessage, NO_SCREEN); }
    public Check(String checkMessage, DoScreen doScreenshot) {
        super(checkMessage, doScreenshot);
        setFailMethod(Assert::fail);
    }
}
