package com.ggasoftware.jdi_ui_tests.core.elements.template.base;

import com.ggasoftware.jdi_ui_tests.core.elements.base.ActionScenrios;
import com.ggasoftware.jdi_ui_tests.core.elements.base.BaseGetElements;
import com.ggasoftware.jdi_ui_tests.core.elements.interfaces.base.IBaseElement;
import com.ggasoftware.jdi_ui_tests.core.elements.interfaces.base.IClickable;
import com.ggasoftware.jdi_ui_tests.core.elements.interfaces.base.IElement;
import com.ggasoftware.jdi_ui_tests.core.elements.interfaces.base.ISelector;
import com.ggasoftware.jdi_ui_tests.core.elements.interfaces.common.*;
import com.ggasoftware.jdi_ui_tests.core.elements.interfaces.complex.*;
import com.ggasoftware.jdi_ui_tests.core.elements.page_objects.annotations.functions.Functions;
import com.ggasoftware.jdi_ui_tests.core.elements.template.common.*;
import com.ggasoftware.jdi_ui_tests.core.elements.template.complex.*;
import com.ggasoftware.jdi_ui_tests.core.elements.template.complex.table.Table;
import com.ggasoftware.jdi_ui_tests.core.logger.LogSettings;
import com.ggasoftware.jdi_ui_tests.utils.common.Timer;
import com.ggasoftware.jdi_ui_tests.utils.linqInterfaces.*;

import java.lang.reflect.Field;

import static com.ggasoftware.jdi_ui_tests.core.elements.base.CascadeInit.InitElements;
import static com.ggasoftware.jdi_ui_tests.core.elements.base.MapInterfaceToElement.updateInterfacesMap;
import static com.ggasoftware.jdi_ui_tests.core.elements.page_objects.annotations.functions.Functions.NONE;
import static com.ggasoftware.jdi_ui_tests.core.settings.JDISettings.*;
import static com.ggasoftware.jdi_ui_tests.utils.common.ReflectionUtils.isClass;
import static java.lang.String.format;

/**
 * Created by Roman_Iovlev on 6/10/2015.
 */
public abstract class BaseElement extends BaseGetElements implements IBaseElement {
    public BaseElement() { this(null); }
    public BaseElement(TLocator locator) {
        elementHandler = new ElementHandler(locator, this);
        if (!createFreeInstance)
            InitElements(this);
        InitElements(this);
        updateInterfacesMap(new Object[][]{
                {IElement.class,    Element.class},
                {IButton.class,     Button.class},
                {IClickable.class,  Clickable.class},
                {IComboBox.class,   ComboBox.class},
                {ILink.class,       Link.class},
                {ISelector.class,   Selector.class},
                {IText.class,       Text.class},
                {ITextArea.class,   TextArea.class},
                {ITextField.class,  TextField.class},
                {ILabel.class,      Label.class},
                {IDropDown.class,   Dropdown.class},
                {IDropList.class,   DropList.class},
                {IGroup.class,      ElementsGroup.class},
                {ITable.class,      Table.class},
                {ICheckBox.class,   CheckBox.class},
                {IRadioButtons.class, RadioButtons.class},
                {ICheckList.class,  CheckList.class},
                {ITextList.class,   TextList.class},
                {ITabs.class,       Tabs.class},
                {IMenu.class,       Menu.class},
                {IFileInput.class,  FileInput.class},
                {IDatePicker.class, DatePicker.class},
        });
    }

    protected ElementHandler elementHandler;
    public <T extends IBaseElement> T copyFromTemplate(T element, String name);
    protected void setTimeoutAction(long mSeconds);
    public void setLocatorFromField(Field field, Object parent, Class<?> type);

    public static boolean createFreeInstance = false;

    private String name;
    public String getName() { return name != null ? name : getTypeName(); }
    public void setName(String name) { this.name = name; }

    protected Timer timer() { return timeouts.timer(); }

    public Functions function = NONE;
    protected String parentTypeName = "";
    protected void setWaitTimeout(long mSeconds) {
        logger.debug("Set wait timeout to " + mSeconds);
        setTimeoutAction(mSeconds);
    }

    private String typeName;
    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
    protected String getTypeName() { return (typeName != null) ? typeName : getClass().getSimpleName(); }

    protected String getParentName() { return parentTypeName; }
    public void setParentName(String parrentName) { parentTypeName = parrentName; }

    private ActionScenrios actionScenrios;
    public void setActionScenarios(ActionScenrios actionScenrios) {
        this.actionScenrios = actionScenrios.setElement(this);
    }

    public void processDemoMode() {
        if (isDemoMode)
            if (isClass(getClass(), Element.class))
                ((Element)this).highlight(highlightSettings);
    }

    public void logAction(String actionName, LogSettings logSettings) {
        logger.toLog(format("Perform action '%s' with element (%s)", actionName, this.toString()), logSettings);
    }
    public void logAction(String actionName) { logAction(actionName, new LogSettings());
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
            processDemoMode();
            return actionScenrios.resultScenario(actionName, action, logResult, logSettings);
        }
        catch (Exception ex) {
            throw asserter.exception(format("Failed to do '%s' action. Exception: %s", actionName, ex));
        }
    }

    protected final void doJAction(String actionName, JAction action) {
        doJAction(actionName, action, new LogSettings());
    }

    protected final void doJAction(String actionName, JAction action, LogSettings logSettings) {
        try {
            processDemoMode();
            actionScenrios.actionScenario(actionName, action, logSettings);
        }
        catch (Exception ex) {
            throw asserter.exception(format("Failed to do '%s' action. Exception: %s", actionName, ex));
        }
    }

    @Override
    public String toString() {
        return format("Name: '%s', Type: '%s' In: '%s', %s",
                getName(), getTypeName(), getParentName(), printLocator());
    }
}
