package com.ggasoftware.uitest.control.base.asserter;


import com.ggasoftware.uitest.control.base.usefulUtils.ScreenshotMaker;

import static com.ggasoftware.uitest.control.base.usefulUtils.TryCatchUtil.tryGetResult;
import static com.ggasoftware.uitest.utils.StringUtils.LineBreak;

/**
 * Created by Roman_Iovlev on 6/9/2015.
 */
public class TestNGScreenshotAsserter extends TestNGAsserter implements IAsserter {
    @Override
    public Exception exception(String message) {
        String screenshotPath = tryGetResult(ScreenshotMaker::takeScreen);
        message = LineBreak + "Add screenshot to: " + screenshotPath + LineBreak + message;
        return super.exception(message);
    }
}
