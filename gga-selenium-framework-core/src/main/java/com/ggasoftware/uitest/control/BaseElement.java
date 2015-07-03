package com.ggasoftware.uitest.control;

import com.ggasoftware.uitest.control.apiInteract.GetElementModule;
import com.ggasoftware.uitest.control.interfaces.IBaseElement;
import com.ggasoftware.uitest.control.interfaces.IElement;
import com.ggasoftware.uitest.utils.interfaces.IScenario;
import com.ggasoftware.uitest.utils.linqInterfaces.JAction;
import com.ggasoftware.uitest.utils.linqInterfaces.JFuncT;
import com.ggasoftware.uitest.utils.linqInterfaces.JFuncTT;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;

import static com.ggasoftware.uitest.utils.settings.FrameworkSettings.*;
import static com.ggasoftware.uitest.utils.settings.FrameworkSettings.highlightSettings;
import static com.ggasoftware.uitest.utils.common.ReflectionUtils.isInterface;
import static com.ggasoftware.uitest.utils.common.Timer.getResultAction;
import static com.ggasoftware.uitest.utils.WebDriverWrapper.getDriver;
import static java.lang.String.format;

/**
 * Created by Roman_Iovlev on 6/10/2015.
 */
public abstract class BaseElement implements IBaseElement {
    private String name;
    public String getName() { return name; }
    private GetElementModule avatar;

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

    public String getElementInfo()  {
        return format(" (Name: '%s', Type: '%s' In: '%s', Locator: '%s')",
                getName(), getTypeName(), getParentName(), avatar);
    }

    public static IScenario invocationScenario = new IScenario() {
        @Override
        public <TResult> TResult invoke(BaseElement element, String actionName, JFuncT<TResult> jAction) {
            element.logAction(actionName);
            return getResultAction(jAction::invoke);
        }
    };

    public void logAction(String actionName) {
        logger.info(format("Perform action '%s' with element '%s'", actionName, getElementInfo()));
    }

    protected final <TResult> TResult doJActionResult(String actionName, JFuncT<TResult> viAction) throws Exception {
        return doJActionResult(actionName, viAction, null);
    }
    protected final <TResult> TResult doJActionResult(String actionName, JFuncT<TResult> viAction,
                                                      JFuncTT<TResult, String> logResult) throws Exception {
        try {
            if (isDemoMode)
                if (isInterface(getClass(), IElement.class))
                    ((IElement)this).highlight(highlightSettings);
            TResult result = invocationScenario.invoke(this, actionName, viAction);
            if (logResult != null)
                logger.info(logResult.invoke(result));
            return result;
        }
        catch (Exception ex) {
            throw asserter.exception(format("Failed to do '%s' action. Exception: %s", actionName, ex));
        }
    }

    protected final void doJAction(String actionName, JAction viAction) throws Exception {
        try {
            if (isDemoMode)
                if (isInterface(getClass(), IElement.class))
                    ((IElement)this).highlight(highlightSettings);
            invocationScenario.invoke(this, actionName, () -> {
                viAction.invoke();
                return null;
            });
        }
        catch (Exception ex) {
            throw asserter.exception(format("Failed to do '%s' action. Exception: %s", actionName, ex));
        }
    }
    public boolean haveLocator() { return avatar.haveLocator(); }
}
