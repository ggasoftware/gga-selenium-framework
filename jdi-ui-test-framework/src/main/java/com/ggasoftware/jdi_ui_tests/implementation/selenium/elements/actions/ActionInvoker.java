package com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.actions;

import com.ggasoftware.jdi_ui_tests.core.logger.base.LogSettings;
import com.ggasoftware.jdi_ui_tests.core.utils.linqInterfaces.JAction;
import com.ggasoftware.jdi_ui_tests.core.utils.linqInterfaces.JFuncT;
import com.ggasoftware.jdi_ui_tests.core.utils.linqInterfaces.JFuncTT;
import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.BaseElement;
import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.base.Element;

import static com.ggasoftware.jdi_ui_tests.core.settings.JDISettings.*;
import static com.ggasoftware.jdi_ui_tests.core.utils.common.ReflectionUtils.isClass;
import static com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.BaseElement.actionScenrios;
import static java.lang.String.format;

/**
 * Created by Roman_Iovlev on 9/3/2015.
 */
public class ActionInvoker {
    private BaseElement element;
    public ActionInvoker(BaseElement element) { newTest(); this.element = element; }

    public final <TResult> TResult doJActionResult(String actionName, JFuncT<TResult> action) {
        return doJActionResult(actionName, action, null, new LogSettings());
    }
    public final <TResult> TResult doJActionResult(String actionName, JFuncT<TResult> action, JFuncTT<TResult, String> logResult) {
        return doJActionResult(actionName, action, logResult, new LogSettings());
    }
    public final <TResult> TResult doJActionResult(String actionName, JFuncT<TResult> action, LogSettings logSettings) {
        return doJActionResult(actionName, action, null, logSettings);
    }

    public final <TResult> TResult doJActionResult(String actionName, JFuncT<TResult> action,
                                                      JFuncTT<TResult, String> logResult, LogSettings logSettings) {
        try {
            processDemoMode();
            return actionScenrios.setElement(element).resultScenario(actionName, action, logResult, logSettings);
        }
        catch (Throwable ex) {
            throw asserter.exception(format("Failed to do '%s' action. Exception: %s", actionName, ex));
        }
    }

    public final void doJAction(String actionName, JAction action) {
        doJAction(actionName, action, new LogSettings());
    }

    public final void doJAction(String actionName, JAction action, LogSettings logSettings) {
        try {
            processDemoMode();
            actionScenrios.setElement(element).actionScenario(actionName, action, logSettings);
        }
        catch (Throwable ex) {
            throw asserter.exception(format("Failed to do '%s' action. Exception: %s", actionName, ex));
        }
    }

    public void processDemoMode() {
        if (isDemoMode)
            if (isClass(element.getClass(), Element.class))
                ((Element)element).highlight(highlightSettings);
    }
}
