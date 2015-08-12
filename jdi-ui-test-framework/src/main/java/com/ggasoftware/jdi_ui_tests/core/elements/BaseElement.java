package com.ggasoftware.jdi_ui_tests.core.elements;

import com.ggasoftware.jdi_ui_tests.core.drivers.JLocationInfo;
import com.ggasoftware.jdi_ui_tests.core.elements.base.Element;
import com.ggasoftware.jdi_ui_tests.core.elements.complex.table.Table;
import com.ggasoftware.jdi_ui_tests.core.elements.page_objects.annotations.functions.Functions;
import com.ggasoftware.jdi_ui_tests.logger.base.LogSettings;
import com.ggasoftware.jdi_ui_tests.utils.common.Timer;
import com.ggasoftware.jdi_ui_tests.utils.linqInterfaces.*;
import com.ggasoftware.jdi_ui_tests.utils.map.MapArray;
import com.ggasoftware.jdi_ui_tests.selenium.elements.base.SlmLocationInfo;
import com.ggasoftware.jdi_ui_tests.core.elements.base.Clickable;
import com.ggasoftware.jdi_ui_tests.core.elements.common.*;
import com.ggasoftware.jdi_ui_tests.core.elements.complex.*;
import com.ggasoftware.jdi_ui_tests.core.elements.interfaces.base.*;
import com.ggasoftware.jdi_ui_tests.core.elements.interfaces.common.*;
import com.ggasoftware.jdi_ui_tests.core.elements.interfaces.complex.*;
import com.ggasoftware.jdi_ui_tests.settings.JDISettings;
import com.ggasoftware.jdi_ui_tests.utils.common.ReflectionUtils;
import com.ggasoftware.jdi_ui_tests.utils.common.StringUtils;
import org.openqa.selenium.*;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;

import static com.ggasoftware.jdi_ui_tests.core.elements.CascadeInit.InitElements;
import static com.ggasoftware.jdi_ui_tests.core.elements.page_objects.annotations.functions.Functions.NONE;
import static com.ggasoftware.jdi_ui_tests.settings.JDISettings.*;
import static com.ggasoftware.jdi_ui_tests.utils.common.LinqUtils.*;
import static com.ggasoftware.jdi_ui_tests.utils.common.ReflectionUtils.getFieldValue;
import static java.lang.String.format;

/**
 * Created by Roman_Iovlev on 6/10/2015.
 */
public abstract class BaseElement<TDriver, TElement, TLocator, TContext> implements IBaseElement<TDriver, TLocator> {
    public BaseElement() { this(null); }

    public BaseElement(TLocator locator) {
        locationInfo.init(locator, this);
        actionScenrios = new ActionScenrios(this);
        if (!createFreeInstance)
            InitElements(this);
    }

    public static boolean createFreeInstance = false;

    private String name;
    protected JLocationInfo<TDriver, TElement, TLocator, TContext> locationInfo;
    public String getName() { return name != null ? name : getTypeName(); }
    public void setName(String name) { this.name = name; }
    public Functions function = NONE;
    protected String parentTypeName = "";
    protected Timer timer() { return locationInfo.timer(); }
    protected JavascriptExecutor jsExecutor() { return (JavascriptExecutor) getDriver(); }

    public boolean haveLocator() { return locationInfo.haveLocator(); }
    public TDriver getDriver() { return locationInfo.getDriver(); }
    public TLocator getLocator() { return locationInfo.getLocator(); }

    public JLocationInfo<TDriver, TElement, TLocator, TContext> getLocationInfo() {return locationInfo; }
    public void setLocationInfo(SlmLocationInfo locationInfo) { this.locationInfo = locationInfo; }
    public void setAvatar(TLocator locator, JLocationInfo<TDriver, TElement, TLocator, TContext> locationInfo) {
        this.locationInfo.init(locator, locationInfo.getContext(), this);
        this.locationInfo.localElementSearchCriteria = locationInfo.localElementSearchCriteria;
    }

    private String typeName;
    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
    protected String getTypeName() { return (typeName != null) ? typeName : getClass().getSimpleName(); }
    protected String getParentName() { return parentTypeName; }
    protected void setParentName(String parrentName) { parentTypeName = parrentName; }

    @Override
    public String toString() {
        return format("Name: '%s', Type: '%s' In: '%s', %s",
                getName(), getTypeName(), getParentName(), locationInfo);
    }

    private ActionScenrios actionScenrios;
    public BaseElement setActionScenarios(ActionScenrios actionScenrios) {
        this.actionScenrios = actionScenrios;
        return this;
    }

    protected void logAction(String actionName, LogSettings logSettings) {
        JDISettings.logger.toLog(format("Perform action '%s' with element (%s)", actionName, this.toString()), logSettings);
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
            return actionScenrios.resultScenario(actionName, action, logResult, logSettings);
        }
        catch (Exception ex) {
            asserter.exception(format("Failed to do '%s' action. Exception: %s", actionName, ex));
            return null;
        }
    }

    protected final void doJAction(String actionName, JAction action) {
        doJAction(actionName, action, new LogSettings());
    }

    protected final void doJAction(String actionName, JAction action, LogSettings logSettings) {
        try {
            driverFactory.processDemoMode(this);
            actionScenrios.actionScenario(actionName, action, logSettings);
        }
        catch (Exception ex) {
            asserter.exception(format("Failed to do '%s' action. Exception: %s", actionName, ex));
        }
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
                        {ITextField.class, TextField.class},
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
        } catch (Exception ex) { asserter.exception("Error in getInterfaceTypeMap" + StringUtils.LineBreak + ex.getMessage()); }
        return null;
    }

    protected Button getButton(String buttonName) {
        List<Field> fields = ReflectionUtils.getFields(this, IButton.class);
        if (fields.size() == 1)
            return (Button) getFieldValue(fields.get(0), this);
        Collection<Button> buttons = select(fields, f -> (Button) getFieldValue(f, this));
        Button button = first(buttons, b -> namesEqual(b.getName(), buttonName.toLowerCase().contains("button") ? buttonName : buttonName + "button"));
        if (button == null) {
            asserter.exception(format("Can't find button '%s' for element '%s'", buttonName, toString()));
            return null;
        }
        return button;
    }

    protected boolean namesEqual(String name1, String name2) {
        return name1.toLowerCase().replace(" ", "").equals(name2.toLowerCase().replace(" ", ""));
    }

    protected Button getButton(Functions funcName) {
        List<Field> fields = ReflectionUtils.getFields(this, IButton.class);
        if (fields.size() == 1)
            return (Button) getFieldValue(fields.get(0), this);
        Collection<Button> buttons = select(fields, f -> (Button) getFieldValue(f, this));
        Button button = first(buttons, b -> b.function.equals(funcName));
        if (button == null) {
            asserter.exception(format("Can't find button '%s' for element '%s'", funcName, toString()));
            return null;
        }
        return button;
    }

    protected Text getTextElement() {
        Field textField = first(getClass().getDeclaredFields(), f -> (f.getType() == Text.class) || (f.getType() == IText.class));
        if (textField!= null) return (Text) getFieldValue(textField, this);
        asserter.exception(format("Can't find Text element '%s'", toString()));
        return null;
    }
}
