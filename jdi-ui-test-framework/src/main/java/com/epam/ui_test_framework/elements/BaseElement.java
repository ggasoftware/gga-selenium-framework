package com.epam.ui_test_framework.elements;

import com.epam.ui_test_framework.elements.apiInteract.*;
import com.epam.ui_test_framework.elements.base.*;
import com.epam.ui_test_framework.elements.complex.*;
import com.epam.ui_test_framework.elements.complex.table.Table;
import com.epam.ui_test_framework.elements.composite.*;
import com.epam.ui_test_framework.elements.interfaces.base.*;
import com.epam.ui_test_framework.elements.interfaces.complex.*;
import com.epam.ui_test_framework.elements.interfaces.common.*;
import com.epam.ui_test_framework.elements.page_objects.annotations.*;
import com.epam.ui_test_framework.elements.common.*;
import com.epam.ui_test_framework.elements.page_objects.annotations.functions.Functions;
import com.epam.ui_test_framework.logger.base.LogSettings;
import com.epam.ui_test_framework.utils.common.Timer;
import com.epam.ui_test_framework.utils.interfaces.*;
import com.epam.ui_test_framework.utils.linqInterfaces.*;
import com.epam.ui_test_framework.utils.map.MapArray;
import com.epam.ui_test_framework.utils.pairs.Pairs;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;

import java.lang.reflect.Field;

import static com.epam.ui_test_framework.elements.page_objects.annotations.AnnotationsUtil.*;
import static com.epam.ui_test_framework.elements.page_objects.annotations.functions.Functions.NONE;
import static com.epam.ui_test_framework.reporting.PerformanceStatistic.addStatistic;
import static com.epam.ui_test_framework.settings.FrameworkData.applicationVersion;
import static com.epam.ui_test_framework.utils.common.LinqUtils.foreach;
import static com.epam.ui_test_framework.utils.common.ReflectionUtils.*;
import static com.epam.ui_test_framework.utils.common.StringUtils.LineBreak;
import static com.epam.ui_test_framework.utils.common.Timer.*;
import static com.epam.ui_test_framework.settings.FrameworkSettings.*;
import static java.lang.String.format;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

/**
 * Created by Roman_Iovlev on 6/10/2015.
 */
public abstract class BaseElement implements IBaseElement {
    public BaseElement() { this(null); }
    public BaseElement(By byLocator) {
        avatar = new GetElementModule(byLocator, this);
    }

    private String name;
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

    public static IScenario invocationScenario = (element, actionName, jAction, logSettings) -> {
        element.logAction(actionName, logSettings);
        Timer timer = new Timer();
        alwaysDoneAction(jAction::invoke);
        addStatistic(timer.timePassedInMSec());
    };
    protected Timer timer() { return avatar.timer(); }
    public static IScenarioWithResult invocationScenarioWithResult = new IScenarioWithResult() {
        @Override
        public <TResult> TResult invoke(BaseElement element, String actionName, JFuncT<TResult> jAction, JFuncTT<TResult, String> logResult, LogSettings logSettings) {
            element.logAction(actionName);
            Timer timer = new Timer();
            TResult result = getResultAction(jAction::invoke);
            String stringResult = (logResult == null)
                    ? result.toString()
                    : asserter.silent(() -> logResult.invoke(result));
            Long timePassed = timer.timePassedInMSec();
            addStatistic(timer.timePassedInMSec());
            logger.toLog(format("Get result '%s' in %s seconds", stringResult,
                    format("%.2f", (double) timePassed / 1000)), logSettings);
            return result;
        }
    };

    protected void logAction(String actionName, LogSettings logSettings) {
        logger.toLog(format("Perform action '%s' with element (%s)", actionName, this.toString()), logSettings);
    }
    protected void logAction(String actionName) { logAction(actionName, new LogSettings());
    }
    protected final <TResult> TResult doJActionResult(String actionName, JFuncT<TResult> action) {
        return doJActionResult(actionName, action, null, new LogSettings());
    }
    protected final <TResult> TResult doJActionResult(String actionName, JFuncT<TResult> action, JFuncTT<TResult, String> logResult) {
        return doJActionResult(actionName, action, logResult, new LogSettings());
    }
    protected final <TResult> TResult doJActionResult(String actionName, JFuncT<TResult> action, LogSettings logSettings) {
        return doJActionResult(actionName, action, null, logSettings);
    }
    protected final <TResult> TResult doJActionResult(String actionName, JFuncT<TResult> action,
                                                      JFuncTT<TResult, String> logResult, LogSettings logSettings) {
        try {
            driverFactory.processDemoMode(this);
            return invocationScenarioWithResult.invoke(this, actionName, action, logResult, logSettings);
        }
        catch (Exception ex) {
            asserter.exception(format("Failed to do '%s' action. Exception: %s", actionName, ex));
            return null;
        }
    }

    protected final void doJAction(String actionName, JAction action) {
        doJAction(actionName, action);
    }

    protected final void doJAction(String actionName, JAction action, LogSettings logSettings) {
        try {
            driverFactory.processDemoMode(this);
            invocationScenario.invoke(this, actionName, action, logSettings);
        }
        catch (Exception ex) {
            asserter.exception(format("Failed to do '%s' action. Exception: %s", actionName, ex));
        }
    }

    // Page Objects init

    public static <T> T InitElements(T parent) {
        fillParentPage(parent);
        asserter.silent(() -> foreach(getFields(parent, IBaseElement.class),
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

    public static void setValueRule(String text, JActionT<String> action)  {
        asserter.silent(() -> setValueRule.invoke(text, action));
    }
    public static JActionTT<String, JActionT<String>> setValueRule = (text, action) -> {
        if (text == null) return;
        action.invoke(text);
    };
    public static JActionTT<String, JActionT<String>> setValueEmptyAction = (text, action) -> {
        if (text == null || text.equals("")) return;
        action.invoke(text.equals("#CLEAR#") ? "" : text);
    };

    private static MapArray<Class, Class> map;
    public static MapArray<Class, Class> getInterfacesMap() {
        try {
            if (map == null)
                map = new MapArray<>(new Object[][]{
                        {IElement.class, Element.class},
                        {IButton.class, Button.class},
                        {IClickable.class, Clickable.class},
                        {IComboBox.class, ComboBox.class},
                        {ILink.class, Link.class},
                        {ISelector.class, Selector.class},
                        {IText.class, Text.class},
                        {ITextArea.class, TextArea.class},
                        {ITextfield.class, TextField.class},
                        {ILabel.class, Label.class},
                        {IDropDown.class, Dropdown.class},
                        {IDropList.class, DropList.class},
                        {IGroup.class, ElementsGroup.class},
                        {ITable.class, Table.class},
                        {ICheckBox.class, CheckBox.class},
                        {IRadioButtons.class, RadioButtons.class},
                        {ICheckList.class, CheckList.class},
                        {ITextList.class, TextList.class},
                        {ITabs.class, Tabs.class},
                        {IMenu.class, Menu.class},
                        {IFileInput.class, FileInput.class},
                        {IDatePicker.class, DatePicker.class},
                });
            return map;
        } catch (Exception ex) { asserter.exception("Error in getInterfaceTypeMap" + LineBreak + ex.getMessage()); }
        return null;
    }

}
