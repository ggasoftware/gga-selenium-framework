package com.epam.ui_test_framework.elements;

import com.epam.ui_test_framework.elements.apiInteract.ContextType;
import com.epam.ui_test_framework.elements.apiInteract.GetElementModule;
import com.epam.ui_test_framework.elements.base.Clickable;
import com.epam.ui_test_framework.elements.base.Element;
import com.epam.ui_test_framework.elements.base.ElementsGroup;
import com.epam.ui_test_framework.elements.base.TextList;
import com.epam.ui_test_framework.elements.complex.*;
import com.epam.ui_test_framework.elements.complex.table.Table;
import com.epam.ui_test_framework.elements.interfaces.base.*;
import com.epam.ui_test_framework.elements.interfaces.complex.*;
import com.epam.ui_test_framework.elements.interfaces.common.*;
import com.epam.ui_test_framework.elements.page_objects.annotations.Frame;
import com.epam.ui_test_framework.elements.page_objects.annotations.JFindBy;
import com.epam.ui_test_framework.elements.common.*;
import com.epam.ui_test_framework.logger.LogSettings;
import com.epam.ui_test_framework.utils.interfaces.IScenario;
import com.epam.ui_test_framework.utils.interfaces.IScenarioWithResult;
import com.epam.ui_test_framework.utils.linqInterfaces.*;
import com.epam.ui_test_framework.utils.map.MapArray;
import com.epam.ui_test_framework.utils.pairs.Pairs;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import java.lang.reflect.Field;

import static com.epam.ui_test_framework.elements.base.Element.highlight;
import static com.epam.ui_test_framework.elements.page_objects.annotations.AnnotationsUtil.*;
import static com.epam.ui_test_framework.utils.common.LinqUtils.foreach;
import static com.epam.ui_test_framework.utils.common.LinqUtils.select;
import static com.epam.ui_test_framework.utils.common.ReflectionUtils.*;
import static com.epam.ui_test_framework.utils.common.StringUtils.LineBreak;
import static com.epam.ui_test_framework.utils.common.Timer.alwaysDoneAction;
import static com.epam.ui_test_framework.utils.common.Timer.getResultAction;
import static com.epam.ui_test_framework.utils.common.Timer.sleep;
import static com.epam.ui_test_framework.utils.settings.FrameworkSettings.*;
import static com.epam.ui_test_framework.utils.usefulUtils.TryCatchUtil.ignoreException;
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
    public String function = "";

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

    public static IScenario invocationScenario = (element, actionName, jAction) -> {
        ignoreException(() -> sleep(100));
        element.logAction(actionName);
        alwaysDoneAction(jAction::invoke);
    };
    public static IScenarioWithResult invocationScenarioWithResult = new IScenarioWithResult() {
        @Override
        public <TResult> TResult invoke(BaseElement element, String actionName, JFuncT<TResult> jAction, JFuncTT<TResult, String> logResult, LogSettings logSettings) {
            sleep(100);
            element.logAction(actionName);
            TResult result = getResultAction(jAction::invoke);
            String stringResult = (logResult == null)
                    ? result.toString()
                    : asserter.silentException(() -> logResult.invoke(result));
            logger.toLog(stringResult, logSettings);
            return result;
        }
    };

    protected void logAction(String actionName) {
        logger.info(format("Perform action '%s' with element (%s)", actionName, this.toString()));
    }

    protected final <TResult> TResult doJActionResult(String actionName, JFuncT<TResult> viAction) {
        return doJActionResult(actionName, viAction, null, new LogSettings());
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

    private void processDemoMode() {
        if (isDemoMode)
            if (isClass(getClass(), Element.class))
                highlight((Element)this, highlightSettings);
    }

    // Page Objects init

    public static <T> T InitElements(T parent) {
        asserter.silentException(() -> foreach(getFields(parent, IBaseElement.class), f -> setElement(parent, f)));
        return parent;
    }

    public static void setElement(Object parent, Field field) throws Exception {
        try {
            Class<?> type = field.getType();
            BaseElement instance = createChildFromField(parent, field, type);
            instance.setName(getElementName(field));
            instance.function = getFunction(field);
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
                        {IRadioButtons.class, RadioButtons.class},
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
    public static MapArray<Class, Class> getInterfaceMap() {
        return map;
    }

}
