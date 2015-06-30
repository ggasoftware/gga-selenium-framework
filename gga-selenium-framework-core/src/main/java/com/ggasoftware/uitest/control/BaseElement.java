package com.ggasoftware.uitest.control;

import com.ggasoftware.uitest.control.interfaces.IBaseElement;
import com.ggasoftware.uitest.control.interfaces.IElement;
import com.ggasoftware.uitest.utils.PropertyReader;
import com.ggasoftware.uitest.utils.interfaces.IScenario;
import com.ggasoftware.uitest.utils.linqInterfaces.JAction;
import com.ggasoftware.uitest.utils.linqInterfaces.JFuncT;
import com.ggasoftware.uitest.utils.linqInterfaces.JFuncTT;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static com.ggasoftware.uitest.utils.settings.FrameworkSettings.*;
import static com.ggasoftware.uitest.utils.settings.FrameworkSettings.highlightSettings;
import static com.ggasoftware.uitest.utils.common.ReflectionUtils.isInterface;
import static com.ggasoftware.uitest.utils.ReporterNGExt.logAction;
import static com.ggasoftware.uitest.utils.TestBaseWebDriver.simpleClassName;
import static com.ggasoftware.uitest.utils.common.Timer.getResultAction;
import static com.ggasoftware.uitest.utils.WebDriverWrapper.getDriver;
import static java.lang.String.format;

/**
 * Created by Roman_Iovlev on 6/10/2015.
 */
public abstract class BaseElement<ParentPanel>  implements IBaseElement {

    /**
     * Name of the element for Report
     */
    private String name;

    /**
     * Locator of the element if applicable
     */
    private String locator;

    /**
     * Locator of the element if applicable
     */
    private By bylocator;

    public String getName() { return name; }

    public By getByLocator() { return bylocator; }
    /**
     * Contains name of the element used for locating its parameters in properties file
     */
    private final Properties properties = new Properties();

    {
        PropertyReader.getProperties(properties, this.getClass().getName());
        String panelLocator = getProperty("main");
        if (panelLocator != null) {
            this.locator = panelLocator;
            this.bylocator = getByLocator();
        }
    }

    protected List<By> context;

    public void setContext(By context) {
        this.context = new ArrayList<>();
        this.context.add(context);
    }
    public void setContext(List<By> context) {
        this.context = context;
    }
    /**
     * Searches for the property with the specified key in this property list.
     * If the key is not found in this property list, the default property list,
     * and its defaults, recursively, are then checked. The method returns
     * <code>null</code> if the property is not found.
     *
     * @param key the property key.
     * @return the value in this property list with the specified key value.
     */
    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    /**
     * Parent panel which contains current element
     */
    protected ParentPanel parent;
    public BaseElement() { }

    /**
     * Initializes element with given locator. Locates own properties of the element by class name, takes given locator and tries
     * to initialize.
     *
     * @param name    - Element name
     * @param locator - start it with locator type "id=", "css=", "xpath=" and etc. Locator without type is assigned to xpath
     * @param panel   - Parent panel instance
     */
    public BaseElement(String name, String locator, ParentPanel panel) {
        this.name = name;
        this.locator = locator;
        this.bylocator = getByLocator();
        this.parent = panel;
        this.parentTypeName = panel.getClass().getSimpleName();
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

    public String getDefaultLogMessage(String text) throws Exception {
        return text +  format(" (Name: '%s', Type: '%s' In: '%s', LocatorAttribute: '%s')",
                getName(), getTypeName(), getParentName(), bylocator);
    }

    public static IScenario invocationScenario = new IScenario() {
        @Override
        public <TResult> TResult invoke(
                BaseElement element, String actionName, JFuncT<TResult> jAction) {
            // TODO replace logAction with
            // logger.info(element.getDefaultLogMessage(actionName));
            logAction(element, element.getParentClassName(), actionName);
            return getResultAction(jAction::invoke);
        }
    };

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
        if (simpleClassName) {
            return parent.getClass().getSimpleName();
        }
        return parent.getClass().getCanonicalName();
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
