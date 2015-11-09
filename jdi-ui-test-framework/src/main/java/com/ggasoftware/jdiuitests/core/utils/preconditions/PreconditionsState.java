package com.ggasoftware.jdiuitests.core.utils.preconditions;

import com.ggasoftware.jdiuitests.implementation.testng.asserter.Check;

import java.lang.reflect.Method;

import static com.ggasoftware.jdiuitests.core.settings.JDIData.testName;
import static com.ggasoftware.jdiuitests.core.settings.JDISettings.asserter;
import static com.ggasoftware.jdiuitests.core.settings.JDISettings.logger;
import static java.lang.String.format;

/**
 * Created by Roman_Iovlev on 10/27/2015.
 */
public class PreconditionsState {

    public static void isInState(IPreconditions condition, Method method) {
        try {
            logger.test("Move to condition: " + condition);
            testName = method.getName();
            if (condition.checkAction().invoke())
                return;
            condition.moveToAction().invoke();
            new Check(condition + " condition achieved").isTrue(condition.checkAction()::invoke);
        } catch (Exception ex) {
            throw asserter.exception(format("Can't reach state: %s. Exception: %s", condition, ex.getMessage()));
        }
    }

}
