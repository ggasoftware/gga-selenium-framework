package com.ggasoftware.uitest.asserter;

import org.testng.Assert;

import static com.ggasoftware.uitest.utils.FrameworkSettings.logger;

/**
 * Created by Roman_Iovlev on 6/9/2015.
 */
public class TestNGAsserter extends Assert implements IAsserter {
    public Exception exception(String message) {
        logger.error(message);
        assertTrue(false, message);
        return new Exception(message);
    }


}
