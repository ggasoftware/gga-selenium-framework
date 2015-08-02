package com.epam.ui_test_framework.elements;

import com.epam.ui_test_framework.elements.apiInteract.*;
import com.epam.ui_test_framework.elements.base.*;
import com.epam.ui_test_framework.elements.complex.*;
import com.epam.ui_test_framework.elements.complex.table.Table;
import com.epam.ui_test_framework.elements.interfaces.base.*;
import com.epam.ui_test_framework.elements.interfaces.complex.*;
import com.epam.ui_test_framework.elements.interfaces.common.*;
import com.epam.ui_test_framework.elements.common.*;
import com.epam.ui_test_framework.elements.page_objects.annotations.functions.Functions;
import com.epam.ui_test_framework.logger.base.LogSettings;
import com.epam.ui_test_framework.utils.common.Timer;
import com.epam.ui_test_framework.utils.interfaces.*;
import com.epam.ui_test_framework.utils.linqInterfaces.*;
import com.epam.ui_test_framework.utils.map.MapArray;
import org.openqa.selenium.*;

import static com.epam.ui_test_framework.elements.CascadeInit.InitElements;
import static com.epam.ui_test_framework.elements.page_objects.annotations.functions.Functions.NONE;
import static com.epam.ui_test_framework.reporting.PerformanceStatistic.addStatistic;
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
    public static boolean createFreeInstance = false;
    public BaseElement(By byLocator) {
        avatar = new GetElementModule(byLocator, this);
        if (!createFreeInstance)
            InitElements(this);
    }

    private String name;
    public String getName() { return name != null ? name : getTypeName(); }
    public void setName(String name) { this.name = name; }
    public Functions function = NONE;
    protected GetElementModule avatar;
    protected String parentTypeName = "";
    protected Timer timer() { return avatar.timer(); }
    protected JavascriptExecutor jsExecutor() { return (JavascriptExecutor) getDriver(); }

    public boolean haveLocator() { return avatar.haveLocator(); }
    public WebDriver getDriver() { return avatar.getDriver(); }
    public By getLocator() { return avatar.byLocator; }

    public GetElementModule getAvatar() {return avatar; }
    public void setAvatar(GetElementModule avatar) { this.avatar = avatar; }
    public void setAvatar(By byLocator, GetElementModule avatar) {
        this.avatar = new GetElementModule(byLocator, avatar.context, this);
        this.avatar.localElementSearchCriteria = avatar.localElementSearchCriteria;
    }

    protected void setWaitTimeout(long mSeconds) {
        logger.debug("Set wait timeout to " + mSeconds);
        getDriver().manage().timeouts().implicitlyWait(mSeconds, MILLISECONDS);
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
                getName(), getTypeName(), getParentName(), avatar);
    }

    public static IScenario invocationScenario = (element, actionName, jAction, logSettings) -> {
        element.logAction(actionName, logSettings);
        Timer timer = new Timer();
        alwaysDoneAction(jAction::invoke);
        logger.info(actionName + " done");
        addStatistic(timer.timePassedInMSec());
    };
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
        doJAction(actionName, action, new LogSettings());
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
        } catch (Exception ex) { asserter.exception("Error in getInterfaceTypeMap" + LineBreak + ex.getMessage()); }
        return null;
    }
}
