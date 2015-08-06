package com.ggasoftware.uitest.control.new_controls.base;

import com.ggasoftware.uitest.control.*;
import com.ggasoftware.uitest.control.base.annotations.*;
import com.ggasoftware.uitest.control.base.annotations.functions.Functions;
import com.ggasoftware.uitest.control.base.apiInteract.*;
import com.ggasoftware.uitest.control.base.interfaces.*;
import com.ggasoftware.uitest.control.base.logger.LogSettings;
import com.ggasoftware.uitest.control.base.map.MapArray;
import com.ggasoftware.uitest.control.base.pairs.Pairs;
import com.ggasoftware.uitest.control.interfaces.base.*;
import com.ggasoftware.uitest.control.interfaces.common.*;
import com.ggasoftware.uitest.control.interfaces.complex.*;
import com.ggasoftware.uitest.control.new_controls.common.*;
import com.ggasoftware.uitest.control.new_controls.complex.*;
import com.ggasoftware.uitest.control.new_controls.complex.table.Table;
import com.ggasoftware.uitest.control.new_controls.composite.Page;
import com.ggasoftware.uitest.utils.Timer;
import com.ggasoftware.uitest.utils.linqInterfaces.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import java.lang.reflect.Field;

import static com.ggasoftware.uitest.control.base.annotations.AnnotationsUtil.*;
import static com.ggasoftware.uitest.control.base.annotations.functions.Functions.NONE;
import static com.ggasoftware.uitest.control.base.asserter.TestNGAsserter.asserter;
import static com.ggasoftware.uitest.control.base.logger.TestNGLog4JLogger.logger;
import static com.ggasoftware.uitest.utils.LinqUtils.foreach;
import static com.ggasoftware.uitest.utils.ReflectionUtils.*;
import static com.ggasoftware.uitest.utils.StringUtils.LineBreak;
import static com.ggasoftware.uitest.utils.TestBaseWebDriver.applicationVersion;
import static com.ggasoftware.uitest.utils.TestBaseWebDriver.simpleClassName;
import static com.ggasoftware.uitest.utils.Timer.*;
import static java.lang.String.format;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

/**
 * Created by Roman_Iovlev on 6/10/2015.
 */
public abstract class BaseElement<P> implements IBaseElement {
    public BaseElement() { this(null); }
    public BaseElement(By byLocator) {
        name = getTypeName();
        avatar = new GetElementModule(byLocator, this);
    }
    public BaseElement(String name, String locator, P panel) {
        this.name = name;
        this.locator = locator;
        this.parent = panel;
        avatar = new GetElementModule(getByLocator(), this);
    }
    public BaseElement(String name, By byLocator) {
        this.name = name;
        avatar = new GetElementModule(byLocator, this);
        this.locator = byLocator.toString();
    }

    /**
     * Locator of the element if applicable
     */
    protected String locator;

    protected String name;
    public String getName() { return name != null ? name : getTypeName(); }
    public void setName(String name) { this.name = name; }
    public Functions function = NONE;

    public boolean haveLocator() { return avatar.haveLocator(); }
    public WebDriver getDriver() { return avatar.getDriver(); }
    public By getLocator() { return avatar.byLocator; }

    public void setAvatar(GetElementModule avatar) { this.avatar = avatar; }

    public void setAvatar(By byLocator, GetElementModule avatar) {
        this.avatar = new GetElementModule(byLocator, avatar.context, this);
        this.avatar.localElementSearchCriteria = avatar.localElementSearchCriteria;
    }
    public GetElementModule getAvatar() {return avatar; }

    protected void setWaitTimeout(long mSeconds) {
        logger.debug("Set wait timeout to " + mSeconds);
        getDriver().manage().timeouts().implicitlyWait(mSeconds, MILLISECONDS);
    }
    protected GetElementModule avatar;

    /**
     * Parent panel which contains current element
     */
    protected P parent;

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

    protected String parentTypeName = "";
    protected String getTypeName() { return getClass().getSimpleName(); }
    protected String getParentName() { return parentTypeName; }
    protected void setParentName(String parrentName) { parentTypeName = parrentName; }

    protected JavascriptExecutor jsExecutor() { return (JavascriptExecutor) getDriver(); }

    @Override
    public String toString() {
        return format("Name: '%s', Type: '%s' In: '%s', %s",
                getName(), getTypeName(), getParentName(), avatar);
    }

    public static IScenario invocationScenario = (element, actionName, jAction) -> {
        sleep(100);
        element.defaultLogAction(actionName);
        alwaysDoneAction(jAction::invoke);
    };
    public static IScenarioWithResult invocationScenarioWithResult = new IScenarioWithResult() {
        @Override
        public <TResult> TResult invoke(BaseElement element, String actionName, JFuncT<TResult> jAction, JFuncTT<TResult, String> logResult, LogSettings logSettings) {
            sleep(100);
            element.defaultLogAction(actionName);
            Timer timer = new Timer();
            timer.timePassedInMSec();
            TResult result = getResultAction(jAction::invoke);
            String stringResult = (logResult == null)
                    ? result.toString()
                    : asserter.silentException(() -> logResult.invoke(result));
            logger.toLog(stringResult, logSettings);
            return result;
        }
    };

    protected void defaultLogAction(String actionName) {
        logger.info(format("Perform action '%s' with element (%s)", actionName, this.toString()));
    }

    protected final <TResult> TResult doJActionResult(String actionName, JFuncT<TResult> viAction) {
        return doJActionResult(actionName, viAction, null, new LogSettings());
    }
    private void test() {
        Boolean b = doJActionResult("", () -> true);
    }
    protected final <TResult> TResult doJActionResult(String actionName, JFuncT<TResult> viAction, JFuncTT<TResult, String> logResult) {
        return doJActionResult(actionName, viAction, logResult, new LogSettings());
    }
    protected final <TResult> TResult doJActionResult(String actionName, JFuncT<TResult> viAction, LogSettings logSettings) {
        return doJActionResult(actionName, viAction, null, logSettings);
    }
    protected final <TResult> TResult doJActionResult(String actionName, JFuncT<TResult> viAction,
                                                      JFuncTT<TResult, String> logResult, LogSettings logSettings) {
        try {
            processDemoMode();
            return invocationScenarioWithResult.invoke(this, actionName, viAction, logResult, logSettings);
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

    private void processDemoMode() { }

    // Page Objects init

    public static <T> T InitElements(T parent) {
        fillParentPage(parent);
        asserter.silentException(() -> foreach(getFields(parent, IBaseElement.class),
                f -> setElement(parent, f)));
        return parent;
    }

    public static void setElement(Object parent, Field field) throws Exception {
        try {
            Class<?> type = field.getType();
            BaseElement instance;
            if (isClass(type, Page.class)) {
                instance = (BaseElement) type.newInstance();
                instance.fillPage(field, parent);
            }
            else {
                instance = createChildFromField(parent, field, type);
                instance.function = getFunction(field);
            }
            instance.setName(getElementName(field));
            instance.setParentName(parent.getClass().getSimpleName());
            field.set(parent, instance);
            if (isInterface(field, IComposite.class))
                InitElements(instance);
        } catch (Exception ex) {
            throw asserter.exception(format("Error in setElement for field '%s' with parent '%s'", field.getName(), parent.getClass().getSimpleName()) + LineBreak + ex.getMessage()); }
    }

    public static BaseElement createChildFromField(Object parent, Field field, Class<?> type) {
        BaseElement instance = (BaseElement) getFieldValue(field, parent);
        if (instance == null)
            try { instance = getElementInstance(type, field.getName(), getNewLocator(field)); }
            catch (Exception ignore) { asserter.exception(
                    format("Can't create child for parent '%s' with type '%s'",
                            parent.getClass().getSimpleName(), field.getType().getName())); return null; }
        else if (instance.getLocator() == null)
            instance.avatar.byLocator = getNewLocator(field);
        instance.avatar.context = (isBaseElement(parent))
                ? ((BaseElement) parent).avatar.context.copy()
                : new Pairs<>();
        if (type != null) {
            By frameBy = getFrame(type.getDeclaredAnnotation(Frame.class));
            if (frameBy != null)
                instance.avatar.context.add(ContextType.Frame, frameBy);
        }
        if (isBaseElement(parent)) {
            By parentLocator = ((BaseElement) parent).getLocator();
            if (parentLocator != null)
                instance.avatar.context.add(ContextType.Locator, parentLocator);
        }
        return instance;
    }

    public static void fillParentPage(Object parent) {
        Class<?> parentType = parent.getClass();
        if (isClass(parentType, Page.class) &&
                parentType.isAnnotationPresent(JPage.class))
            fillPageFromAnnotaiton((Page) parent,
                    parentType.getAnnotation(JPage.class), null);
    }

    public void fillPage(Field field, Object parent) throws Exception {
        if (field.isAnnotationPresent(JPage.class))
            fillPageFromAnnotaiton((Page) this, field.getAnnotation(JPage.class), parent);
    }

    private static boolean isBaseElement(Object obj) {
        return isClass(obj.getClass(), BaseElement.class);
    }

    private static BaseElement getElementInstance(Class<?> type, String fieldName, By newLocator) throws Exception {
        try {
            if (!type.isInterface()) {
                BaseElement instance = (BaseElement) type.newInstance();
                instance.avatar.byLocator = newLocator;
                return instance;
            }
            Class classType = getInterfacesMap().first(clType -> clType == type);
            if (classType != null)
                return (BaseElement) classType.getDeclaredConstructor(By.class).newInstance(newLocator);
            throw asserter.exception("Unknown interface: " + type +
                    ". Add relation interface -> class in VIElement.InterfaceTypeMap");
        } catch (Exception ex) {
            throw asserter.exception(format("Error in getElementInstance for field '%s' with type '%s'", fieldName, type.getName()) +
                    LineBreak + ex.getMessage()); }
    }

    private static By getNewLocator(Field field) {
        try {
            By byLocator = null;
            String locatorGroup = applicationVersion;
            if (locatorGroup != null) {
                JFindBy jFindBy = field.getAnnotation(JFindBy.class);
                if (jFindBy != null && locatorGroup.equals(jFindBy.group()))
                    byLocator = getFindByLocator(jFindBy);
            }
            return (byLocator != null)
                    ? byLocator
                    : getFindByLocator(field.getAnnotation(FindBy.class));
        } catch (Exception ex) {
            asserter.exception(format("Error in get locator for type '%s'", field.getType().getName()) +
                    LineBreak + ex.getMessage()); return null; }
    }

    public static JActionTT<String, JActionT<String>> setValueRule = (text, action) -> {
        if (text == null) return;
        action.invoke(text);
    };
    public static void setValueRule(String text, JActionT<String> action)  {
        asserter.silentException(() -> setValueRule.invoke(text, action));
    }
    public static JActionTT<String, JActionT<String>> setValueEmptyAction = (text, action) -> {
        if (text == null || text.equals("")) return;
        action.invoke(text.equals("#CLEAR#") ? "" : text);
    };

    private static MapArray<Class, Class> map;
    private static MapArray<Class, Class> getInterfacesMap() {
        try {
            if (map == null)
                map = new MapArray<>(new Object[][]{
                        {IElement.class, Element.class},
                        {IButton.class, Button.class},
                        {IClickable.class, Clickable.class},
                        {IClickableText.class, Button.class},
                        {IComboBox.class, ComboBox.class},
                        {ILink.class, Link.class},
                        {ISelector.class, Selector.class},
                        {IText.class, Text.class},
                        {ITextArea.class, TextArea.class},
                        {IInput.class, Input.class},
                        {ILabel.class, Label.class},
                        {IDropDown.class, Dropdown.class},
                        {IDropList.class, DropList.class},
                        {IGroup.class, ElementsGroup.class},
                        {ITable.class, Table.class},
                        {ICheckBox.class, CheckBox.class},
                        {IRadioButtons.class, RadioButton.class},
                        {ICheckList.class, CheckList.class},
                        {ITextList.class, TextList.class},
                        {ITabs.class, Tabs.class},
                        {IFileInput.class, FileInput.class},
                        {IDatePicker.class, DatePicker.class},
                });
            return map;
        } catch (Exception ex) { asserter.exception("Error in getInterfaceTypeMap" + LineBreak + ex.getMessage()); }
        return null;
    }

    /**
     * Gets element's By locator
     *
     * @return By Locator of the element
     */
    public By getByLocator() {
        String locator_body = locator.replaceAll("[\\w\\s]*=(.*)", "$1").trim();
        String type = locator.replaceAll("([\\w\\s]*)=.*", "$1").trim();
        switch (type) {
            case "css":
                return By.cssSelector(locator_body);
            case "id":
                return By.id(locator_body);
            case "link":
                return By.linkText(locator_body);
            case "xpath":
                return By.xpath(locator_body);
            case "text":
                return By.xpath(format("//*[contains(text(), '%s')]", locator_body));
            case "name":
                return By.name(locator_body);
            default:
                return By.xpath(locator);
        }
    }
}
