package com.ggasoftware.jdiuitest.web.testng.asserter;

import com.ggasoftware.jdiuitest.core.asserter.BaseChecker;
import com.ggasoftware.jdiuitest.core.utils.linqinterfaces.JActionT;
import com.ggasoftware.jdiuitest.web.selenium.driver.ScreenshotMaker;
import org.testng.Assert;

/**
 * Created by Roman_Iovlev on 6/9/2015.
 */
public class Check extends BaseChecker {
    public Check() {
        super();
    }
    protected String doScreenshotGetMessage() { return ScreenshotMaker.doScreenshotGetMessage(); }

    public Check(String checkMessage) {
        super(checkMessage);
    }

    @Override
    protected JActionT<String> throwFail() {
        return Assert::fail;
    }
}
