package com.ggasoftware.jdiuitests.implementation.selenium.elements.actions;


import com.ggasoftware.jdiuitests.core.logger.base.LogSettings;
import com.ggasoftware.jdiuitests.core.utils.common.Timer;
import com.ggasoftware.jdiuitests.core.utils.linqInterfaces.JAction;
import com.ggasoftware.jdiuitests.core.utils.linqInterfaces.JFuncT;
import com.ggasoftware.jdiuitests.core.utils.linqInterfaces.JFuncTT;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.BaseElement;

import static com.ggasoftware.jdiuitests.core.reporting.PerformanceStatistic.addStatistic;
import static com.ggasoftware.jdiuitests.core.settings.JDISettings.asserter;
import static com.ggasoftware.jdiuitests.core.settings.JDISettings.logger;
import static com.ggasoftware.jdiuitests.core.utils.common.Timer.alwaysDoneAction;
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
        element.logAction(actionName, logSettings);
        Timer timer = new Timer();
        alwaysDoneAction(jAction::invoke);
        logger.info(actionName + " done");
        addStatistic(timer.timePassedInMSec());
    }

    public <TResult> TResult resultScenario(String actionName, JFuncT<TResult> jAction, JFuncTT<TResult, String> logResult, LogSettings logSettings) {
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
