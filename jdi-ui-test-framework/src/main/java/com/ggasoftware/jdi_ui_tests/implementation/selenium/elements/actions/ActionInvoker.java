package com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.actions;

import com.ggasoftware.jdi_ui_tests.core.logger.base.LogSettings;
import com.ggasoftware.jdi_ui_tests.core.utils.linqInterfaces.JAction;
import com.ggasoftware.jdi_ui_tests.core.utils.linqInterfaces.JFuncT;
import com.ggasoftware.jdi_ui_tests.core.utils.linqInterfaces.JFuncTT;
import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.BaseElement;
import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.base.Element;
import com.ggasoftware.jdi_ui_tests.core.settings.JDISettings;

import static com.ggasoftware.jdi_ui_tests.core.utils.common.ReflectionUtils.isClass;

/**
 * Created by Roman_Iovlev on 9/3/2015.
 */
public class ActionInvoker {
    private BaseElement element;
    public ActionInvoker(BaseElement element) { JDISettings.newTest(); this.element = element; }

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
            return BaseElement.actionScenrios.setElement(element).resultScenario(actionName, action, logResult, logSettings);
        }
        catch (Exception|Error ex) {
            throw JDISettings.exception("Failed to do '%s' action. Exception: %s", actionName, ex);
        }
    }

    public final void doJAction(String actionName, JAction action) {
        doJAction(actionName, action, new LogSettings());
    }

    public final void doJAction(String actionName, JAction action, LogSettings logSettings) {
        try {
            processDemoMode();
            BaseElement.actionScenrios.setElement(element).actionScenario(actionName, action, logSettings);
        }
        catch (Exception|Error ex) {
            throw JDISettings.exception("Failed to do '%s' action. Exception: %s", actionName, ex);
        }
    }

    public void processDemoMode() {
        if (JDISettings.isDemoMode)
            if (isClass(element.getClass(), Element.class))
                ((Element)element).highlight(JDISettings.highlightSettings);
    }
}
