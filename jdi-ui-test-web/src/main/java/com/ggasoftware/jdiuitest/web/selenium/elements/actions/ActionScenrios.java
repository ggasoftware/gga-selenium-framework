package com.ggasoftware.jdiuitest.web.selenium.elements.actions;


import com.ggasoftware.jdiuitest.core.logger.base.LogSettings;
import com.ggasoftware.jdiuitest.core.reporting.PerformanceStatistic;
import com.ggasoftware.jdiuitest.core.settings.JDISettings;
import com.ggasoftware.jdiuitest.core.utils.common.Timer;
import com.ggasoftware.jdiuitest.core.utils.linqinterfaces.JAction;
import com.ggasoftware.jdiuitest.core.utils.linqinterfaces.JFuncT;
import com.ggasoftware.jdiuitest.core.utils.linqinterfaces.JFuncTT;
import com.ggasoftware.jdiuitest.web.selenium.elements.BaseElement;

import static com.ggasoftware.jdiuitest.core.settings.JDISettings.timeouts;
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

    public void actionScenario(String actionName, JAction jAction, LogSettings logSettings) {
        element.logAction(actionName, logSettings);
        Timer timer = new Timer();
        new Timer(timeouts.currentTimeoutSec).wait(() -> {
            jAction.invoke();
            return true;
        });
        JDISettings.logger.info(actionName + " done");
        PerformanceStatistic.addStatistic(timer.timePassedInMSec());
    }

    public <TResult> TResult resultScenario(String actionName, JFuncT<TResult> jAction, JFuncTT<TResult, String> logResult, LogSettings logSettings) {
        element.logAction(actionName);
        Timer timer = new Timer();
        TResult result = new Timer(timeouts.currentTimeoutSec)
                .getResultByCondition(jAction::invoke, res -> true);
        String stringResult = (logResult == null)
                ? result.toString()
                : JDISettings.asserter.silent(() -> logResult.invoke(result));
        Long timePassed = timer.timePassedInMSec();
        PerformanceStatistic.addStatistic(timer.timePassedInMSec());
        JDISettings.logger.toLog(format("Get result '%s' in %s seconds", stringResult,
                format("%.2f", (double) timePassed / 1000)), logSettings);
        return result;
    }
}
