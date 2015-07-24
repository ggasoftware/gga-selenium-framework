package com.epam.ui_test_framework.asserter;

import com.epam.ui_test_framework.utils.usefulUtils.ScreenshotMaker;

import static com.epam.ui_test_framework.logger.enums.LogInfoTypes.FRAMEWORK;
import static com.epam.ui_test_framework.settings.FrameworkSettings.logger;
import static com.epam.ui_test_framework.utils.common.StringUtils.LineBreak;
import static com.epam.ui_test_framework.utils.usefulUtils.TryCatchUtil.tryGetResult;

/**
 * Created by Roman_Iovlev on 6/9/2015.
 */
public class JUnitScreenshotAsserter extends JUnitAsserter implements IAsserter {
    @Override
    public Exception exception(String message) {
        String screenshotPath = tryGetResult(ScreenshotMaker::takeScreen);
        message = LineBreak + "Add screenshot to: " + screenshotPath + LineBreak + message;
        return super.exception(message);
    }
}
