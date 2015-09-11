package com.ggasoftware.jdi_ui_tests.implementation.selenium.elements;

import com.ggasoftware.jdi_ui_tests.core.logger.base.LogSettings;
import com.ggasoftware.jdi_ui_tests.core.settings.JDISettings;
import com.ggasoftware.jdi_ui_tests.core.utils.common.Timer;
import com.ggasoftware.jdi_ui_tests.core.utils.linqInterfaces.JActionT;
import com.ggasoftware.jdi_ui_tests.core.utils.linqInterfaces.JActionTT;
import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.actions.ActionInvoker;
import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.actions.ActionScenrios;
import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.actions.ElementsActions;
import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.apiInteract.GetElementModule;
import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.base.Clickable;
import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.base.Element;
import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.common.*;
import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.complex.*;
import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.complex.table.interfaces.ITable;
import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.complex.table.Table;
import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.interfaces.base.IBaseElement;
import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.interfaces.base.IClickable;
import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.interfaces.base.IElement;
import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.interfaces.base.ISelector;
import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.interfaces.common.*;
import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.interfaces.complex.*;
import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.page_objects.annotations.GetElement;
import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.page_objects.annotations.functions.Functions;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import java.text.MessageFormat;

import static com.ggasoftware.jdi_ui_tests.core.settings.JDISettings.logger;
import static com.ggasoftware.jdi_ui_tests.core.settings.JDISettings.shortLogMessagesFormat;
import static com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.CascadeInit.InitElements;
import static com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.CascadeInit.firstInstance;
import static com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.MapInterfaceToElement.updateInterfacesMap;
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
        if (!createFreeInstance && firstInstance)
            InitElements(this);
        updateInterfacesMap(seleniumDefaultMap());
    }

    private String name;
    public String getName() { return name != null ? name : getTypeName(); }
    public void setName(String name) { this.name = name; }
    public Functions function = Functions.NONE;
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
    protected GetElement getElement = new GetElement(this);
    protected ElementsActions actions = new ElementsActions(this);
    public ActionInvoker invoker = new ActionInvoker(this);

    protected void setWaitTimeout(long mSeconds) {
        JDISettings.logger.debug("Set wait timeout to " + mSeconds);
        getDriver().manage().timeouts().implicitlyWait(mSeconds, MILLISECONDS);
    }
    private String typeName;
    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
    protected String getTypeName() { return (typeName != null) ? typeName : getClass().getSimpleName(); }
    protected String getParentName() { return parentTypeName; }
    protected void setParentName(String parrentName) { parentTypeName = parrentName; }

    public void logAction(String actionName, LogSettings logSettings) {
        logger.toLog(format(shortLogMessagesFormat
                ? "%s with %s"
                : "Perform action '%s' with webElement (%s)", actionName, this.toString()), logSettings);
    }
    public void logAction(String actionName) { logAction(actionName, new LogSettings());
    }
    public static ActionScenrios actionScenrios = new ActionScenrios();
    public static void setActionScenarios(ActionScenrios actionScenrios) {
        BaseElement.actionScenrios = actionScenrios;
    }

    public static void setValueRule(String text, JActionT<String> action)  {
        doActionRule.invoke(text, action);
    }
    public static JActionTT<String, JActionT<String>> doActionRule = (text, action) -> {
        if (text == null) return;
        action.invoke(text);
    };
    public static JActionTT<String, JActionT<String>> setValueEmptyAction = (text, action) -> {
        if (text == null || text.equals("")) return;
        action.invoke(text.equals("#CLEAR#") ? "" : text);
    };

    private static Object[][] seleniumDefaultMap(){
        return new Object[][]{
                {IElement.class,    Element.class},
                {IButton.class,     Button.class},
                {IClickable.class,  Clickable.class},
                {IComboBox.class,   ComboBox.class},
                {ILink.class,       Link.class},
                {ISelector.class,   Selector.class},
                {IText.class,       Text.class},
                {IImage.class,      Image.class},
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
        };
    }

    @Override
    public String toString() {
        return MessageFormat.format(shortLogMessagesFormat
                ? "{1} {2).{0} {3}"
                : "Name: '{0}', Type: '{1}' In: '{2}', {3}",
                getName(), getTypeName(), getParentName(), avatar);
    }
}
