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
    public BaseElement(By byLocator) { }
    public BaseElement(String name, By byLocator) {
        avatar = new GetElementModule(byLocator);
    }


    /**
     * Initializes element with given locator. Locates own properties of the element by class name, takes given locator and tries
     * to initialize.
     *
     * @param name    - Element name
     * @param byLocator - Selenium By
     */
    public BaseElement(String name, By byLocator) {
        this.name = name;
        this.bylocator = byLocator;
        this.locator = byLocator.toString();
    }
    /**
     * Gets element's locator
     *
     * @return Locator of the element
     */
    public String getLocator() {
        return locator;
    }

    protected String parentTypeName = "";
    protected String getTypeName() { return this.getClass().getSimpleName(); }
    protected String getParentName() { return parentTypeName; }
    protected void setParentName(String parrentName) { parentTypeName = parrentName; }

    protected JavascriptExecutor jsExecutor() { return (JavascriptExecutor) getDriver(); }

    public String getDefaultLogMessage(String text)  {
        return text +  format(" (Name: '%s', Type: '%s' In: '%s', LocatorAttribute: '%s')",
                getName(), getTypeName(), getParentName(), bylocator);
    }

    public static IScenario invocationScenario = new IScenario() {
        @Override
        public <TResult> TResult invoke(
                BaseElement element, String actionName, JFuncT<TResult> jAction) {
            // TODO replace logAction with
            // logger.info(element.getDefaultLogMessage(actionName));
            element.logAction(actionName);
            return getResultAction(jAction::invoke);
        }
    };

    public void logAction(String actionName) {
        logger.info("Perform action '%s' with element '%s'", actionName, getDefaultLogMessage(""));
    }

    protected final <TResult> TResult doJActionResult(String actionName, JFuncT<TResult> viAction) {
        return doJActionResult(actionName, viAction, null);
    }
    protected final <TResult> TResult doJActionResult(String actionName, JFuncT<TResult> viAction,
                                                      JFuncTT<TResult, String> logResult)
    {
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
            asserter.exception(format("Failed to do '%s' action. Exception: %s", actionName, ex));
            return null;
        }
    }

    protected final ParentPanel doJAction(String actionName, JAction viAction)
    {
        try {
            if (isDemoMode)
                if (isInterface(getClass(), IElement.class))
                    ((IElement)this).highlight(highlightSettings);
            invocationScenario.invoke(this, actionName, () -> {
                viAction.invoke();
                return null;
            });
            // TODO remove
            // return parent;
            return parent;
        }
        catch (Exception ex) {
            asserter.exception(format("Failed to do '%s' action. Exception: %s", actionName, ex));
            return null;
        }
    }
    /**
     * Sets locator for the element
     *
     * @param elementLocator - Locator of the element. Start it with locator type "id=", "css=", "xpath=" and etc.
     */
    public void setLocator(String elementLocator) {
        this.locator = elementLocator;
    }
    public void setLocator(By byLocator) {
        this.bylocator = byLocator;
    }
    public boolean haveLocator() { return bylocator != null; }
    /**
     * Sets locator for the element
     *
     * @param parentPanel - Locator of the element. Start it with locator type "id=", "css=", "xpath=" and etc.
     */
    protected void setParent(ParentPanel parentPanel) {
        this.parent = parentPanel;
    }

    /**
     * Get simple element class name
     *
     * @return class name string
     */
    protected String getSimpleClassName() {
        return this.getClass().getSimpleName();
    }

    /**
     * Get Parent Class Name
     *
     * @return Parent Canonical Class Name
     */
    protected String getParentClassName() {
        if (parent == null) {
            return "";
        }
        return parent.getClass().getSimpleName();
    }

    /**
     * Get full xpath string
     *
     * @return Xpath of the element
     */
    public String getXPath() {
        String sLocator = getLocator().replaceAll("\\w*=(.*)", "$1").trim();
        String sType = getLocator().replaceAll("(\\w*)=.*", "$1").trim();
        switch (sType) {
            case "css":
                return "";
            case "id":
                return format("//*[@id=\"%s\"]", sLocator);
            case "link":
                return format("//*[@link=\"%s\"]", sLocator);
            case "xpath":
                return format("%s", sLocator);
            case "text":
                return format("//*[contains(text(), '%s')]", sLocator);
            case "name":
                return format("//*[@name=\"%s\"]", sLocator);
            default:
                return "";
        }
    }
}
