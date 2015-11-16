package com.ggasoftware.jdiuitest.web.selenium.preconditions;

import java.lang.reflect.Method;

import static com.ggasoftware.jdiuitest.core.settings.JDIData.testName;
import static com.ggasoftware.jdiuitest.core.settings.JDISettings.asserter;
import static com.ggasoftware.jdiuitest.core.settings.JDISettings.logger;
import static java.lang.String.format;

/**
 * Created by Roman_Iovlev on 10/27/2015.
 */
public class PreconditionsState {
    public static boolean alwaysMoveToCondition;

    public static void isInState(IPreconditions condition, Method method) {
        try {
            logger.test("Move to condition: " + condition);
            testName = method.getName();
            if (alwaysMoveToCondition && condition.checkAction().invoke())
                return;
            condition.moveToAction().invoke();
            asserter.isTrue(condition.checkAction()::invoke);
            logger.test(condition + " condition achieved");
        } catch (Exception ex) {
            throw asserter.exception(format("Can't reach state: %s. Exception: %s", condition, ex.getMessage()));
        }
    }

}
