package com.ggasoftware.jdi_ui_tests.core.elements;

import com.ggasoftware.jdi_ui_tests.logger.base.LogSettings;
import com.ggasoftware.jdi_ui_tests.utils.common.Timer;
import com.ggasoftware.jdi_ui_tests.utils.linqInterfaces.JAction;
import com.ggasoftware.jdi_ui_tests.utils.linqInterfaces.JFuncT;
import com.ggasoftware.jdi_ui_tests.utils.linqInterfaces.JFuncTT;

import static com.ggasoftware.jdi_ui_tests.reporting.PerformanceStatistic.*;
import static com.ggasoftware.jdi_ui_tests.settings.JDISettings.*;
import static com.ggasoftware.jdi_ui_tests.utils.common.Timer.*;
import static java.lang.String.format;

/**
 * Created by Roman_Iovlev on 8/10/2015.
 */
public class ActionScenrios {
    private BaseElement element;
    public ActionScenrios setElement(BaseElement element) {
        this.element = element;
        return this;
    }

    public void actionScenario(String actionName , JAction jAction, LogSettings logSettings) {
        sleep(100);
        element.logAction(actionName, logSettings);
        Timer timer = new Timer();
        alwaysDoneAction(jAction::invoke);
        logger.info(actionName + " done");
        addStatistic(timer.timePassedInMSec());
    }

    public <TResult> TResult resultScenario(String actionName, JFuncT<TResult> jAction, JFuncTT<TResult, String> logResult, LogSettings logSettings) {
        sleep(100);
        element.logAction(actionName);
        Timer timer = new Timer();
        TResult result = Timer.getResultAction(jAction::invoke);
        String stringResult = (logResult == null)
                ? result.toString()
                : asserter.silent(() -> logResult.invoke(result));
        Long timePassed = timer.timePassedInMSec();
        addStatistic(timer.timePassedInMSec());
        logger.toLog(format("Get result '%s' in %s seconds", stringResult,
                format("%.2f", (double) timePassed / 1000)), logSettings);
        return result;
    }

}
