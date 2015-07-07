package com.ggasoftware.uitest.control;

import com.ggasoftware.uitest.control.apiInteract.ContextType;
import com.ggasoftware.uitest.control.apiInteract.GetElementModule;
import com.ggasoftware.uitest.control.interfaces.IBaseElement;
import com.ggasoftware.uitest.control.base.Element;
import com.ggasoftware.uitest.utils.common.StringUtils;
import com.ggasoftware.uitest.utils.common.WebDriverByUtils;
import com.ggasoftware.uitest.utils.interfaces.IScenario;
import com.ggasoftware.uitest.utils.interfaces.IScenarioWithResult;
import com.ggasoftware.uitest.utils.linqInterfaces.JAction;
import com.ggasoftware.uitest.utils.linqInterfaces.JFuncT;
import com.ggasoftware.uitest.utils.linqInterfaces.JFuncTT;
import com.ggasoftware.uitest.utils.map.MapArray;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import static com.ggasoftware.uitest.control.apiInteract.ContextType.Locator;
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
    public BaseElement() { }
    public BaseElement(By byLocator) {
        avatar = new GetElementModule(byLocator);
    }

    private String name;
    public String getName() { return name != null ? name : getTypeName(); }
    public void setName(String name) { this.name = name; }
    public boolean haveLocator() { return avatar.haveLocator(); }
    public WebDriver getDriver() { return avatar.getDriver(); }
    public By getLocator() { return avatar.getLocator(); }
    public static <TChild extends IBaseElement> TChild createChild(BaseElement parent, Class<TChild> childClass, By newLocator) {
        TChild element;
        try { element = childClass.newInstance(); }
        catch (Exception ignore) { asserter.exception(
            format("Can't create child for parent '%s' with type '%s' and new locator '%s'",
                parent.toString(), childClass.getName(), newLocator)); return null; }
        MapArray<ContextType, By> newContext = parent.avatar.getContext().clone();
        newContext.add(Locator, parent.getLocator());
        element.setAvatar(new GetElementModule(newLocator, newContext));
        return element;
    }

    public void setAvatar(GetElementModule avatar) { this.avatar = avatar; }

    protected void setWaitTimeout(long mSeconds) {
        logger.debug("Set wait timeout to " + mSeconds);
        getDriver().manage().timeouts().implicitlyWait(mSeconds, MILLISECONDS);
    }
    protected GetElementModule avatar;

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
                    : asserter.silentException(() -> logResult.invoke(result));
            logger.info(stringResult);
            return result;
        }
    };

    protected void logAction(String actionName) {
        logger.info(format("Perform action '%s' with element (%s)", actionName, this.toString()));
    }

    protected final <TResult> TResult doJActionResult(String actionName, JFuncT<TResult> viAction) {
        return doJActionResult(actionName, viAction, null);
    }
    protected final <TResult> TResult doJActionResult(String actionName, JFuncT<TResult> viAction,
                                                        JFuncTT<TResult, String> logResult) {
        try {
            processDemoMode();
            return invocationScenarioWithResult.invoke(this, actionName, viAction, logResult);
        }
        catch (Exception ex) {
            asserter.exception(format("Failed to do '%s' action. Exception: %s", actionName, ex));
            return null;
        }
    }

    protected final void doJAction(String actionName, JAction viAction) {
        try {
            processDemoMode();
            invocationScenario.invoke(this, actionName, viAction);
        }
        catch (Exception ex) {
            asserter.exception(format("Failed to do '%s' action. Exception: %s", actionName, ex));
        }
    }

    private void processDemoMode() {
        if (isDemoMode)
            if (isClass(getClass(), Element.class))
                ((Element)this).highlight(highlightSettings);
    }
}
