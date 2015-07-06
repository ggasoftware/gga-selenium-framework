package com.ggasoftware.uitest.control;

import com.ggasoftware.uitest.control.apiInteract.GetElementModule;
import com.ggasoftware.uitest.control.interfaces.IBaseElement;
import com.ggasoftware.uitest.control.base.Element;
import com.ggasoftware.uitest.utils.interfaces.IScenario;
import com.ggasoftware.uitest.utils.interfaces.IScenarioWithResult;
import com.ggasoftware.uitest.utils.linqInterfaces.JAction;
import com.ggasoftware.uitest.utils.linqInterfaces.JFuncT;
import com.ggasoftware.uitest.utils.linqInterfaces.JFuncTT;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static com.ggasoftware.uitest.utils.common.ReflectionUtils.isClass;
import static com.ggasoftware.uitest.utils.common.Timer.alwaysDoneAction;
import static com.ggasoftware.uitest.utils.settings.FrameworkSettings.*;
import static com.ggasoftware.uitest.utils.settings.FrameworkSettings.highlightSettings;
import static com.ggasoftware.uitest.utils.common.Timer.getResultAction;
import static java.lang.String.format;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

/**
 * Created by Roman_Iovlev on 6/10/2015.
 */
public abstract class BaseElement implements IBaseElement {
    private String name;
    public String getName() { return name; }
    public boolean haveLocator() { return avatar.haveLocator(); }
    public WebDriver getDriver() { return avatar.getDriver(); }
    public By getLocator() { return avatar.getLocator(); }

    public void setElementSearchCriteria(JFuncTT<WebElement, Boolean> criteria) { avatar.setElementSearchCriteria(criteria); }

    protected void setWaitTimeout(long mSeconds) {
        logger.debug("Set wait timeout to " + mSeconds);
        getDriver().manage().timeouts().implicitlyWait(mSeconds, MILLISECONDS);
    }
    protected GetElementModule avatar;

    public BaseElement() { }
    public BaseElement(By byLocator) {
        this(null, byLocator); }
    public BaseElement(String name, By byLocator) {
        this.name = name != null ? name : getTypeName();
        avatar = new GetElementModule(byLocator);
    }

    protected String parentTypeName = "";
    protected String getTypeName() { return getClass().getSimpleName(); }
    protected String getParentName() { return parentTypeName; }
    protected void setParentName(String parrentName) { parentTypeName = parrentName; }

    protected JavascriptExecutor jsExecutor() { return (JavascriptExecutor) getDriver(); }

    @Override
    public String toString() {
        return format("Name: '%s', Type: '%s' In: '%s', Locator: '%s'",
                getName(), getTypeName(), getParentName(), avatar);
    }

    public static IScenario invocationScenario = (element, actionName, jAction) -> {
        element.logAction(actionName);
        alwaysDoneAction(jAction::invoke);
    };
    public static IScenarioWithResult invocationScenarioWithResult = new IScenarioWithResult() {
        @Override
        public <TResult> TResult invoke(BaseElement element, String actionName, JFuncT<TResult> jAction, JFuncTT<TResult, String> logResult) {
            element.logAction(actionName);
            TResult result = getResultAction(jAction::invoke);
            String stringResult = (logResult == null)
                    ? result.toString()
                    : logResult.invoke(result);
            logger.info(stringResult);
            return result;
        }
    };

    protected void logAction(String actionName) {
        logger.info(format("Perform action '%s' with element (%s)", actionName, this.toString()));
    }

    protected final <TResult> TResult doJActionResult(String actionName, JFuncT<TResult> viAction) throws Exception {
        return doJActionResult(actionName, viAction, null);
    }
    protected final <TResult> TResult doJActionResult(String actionName, JFuncT<TResult> viAction,
                                                        JFuncTT<TResult, String> logResult) throws Exception {
        try {
            processDemoMode();
            return invocationScenarioWithResult.invoke(this, actionName, viAction, logResult);
        }
        catch (Exception ex) {
            throw asserter.exception(format("Failed to do '%s' action. Exception: %s", actionName, ex));
        }
    }

    protected final void doJAction(String actionName, JAction viAction) throws Exception {
        try {
            processDemoMode();
            invocationScenario.invoke(this, actionName, viAction);
        }
        catch (Exception ex) {
            throw asserter.exception(format("Failed to do '%s' action. Exception: %s", actionName, ex));
        }
    }

    private void processDemoMode() throws Exception {
        if (isDemoMode)
            if (isClass(getClass(), Element.class))
                ((Element)this).highlight(highlightSettings);
    }
}
