package com.ggasoftware.jdiuitests.implementation.selenium.elements;

import com.ggasoftware.jdiuitests.core.logger.base.LogSettings;
import com.ggasoftware.jdiuitests.core.utils.common.Timer;
import com.ggasoftware.jdiuitests.core.utils.linqInterfaces.JActionT;
import com.ggasoftware.jdiuitests.core.utils.linqInterfaces.JActionTT;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.actions.ActionInvoker;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.actions.ActionScenrios;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.actions.ElementsActions;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.apiInteract.GetElementModule;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.base.Clickable;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.base.Element;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.common.*;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.complex.*;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.complex.table.Table;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.complex.table.interfaces.ITable;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.interfaces.base.IBaseElement;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.interfaces.base.IClickable;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.interfaces.base.IComposite;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.interfaces.base.IElement;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.interfaces.common.*;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.interfaces.complex.*;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.pageobjects.annotations.AnnotationsUtil;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.pageobjects.annotations.GetElement;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.pageobjects.annotations.functions.Functions;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import java.lang.reflect.Field;
import java.text.MessageFormat;

import static com.ggasoftware.jdiuitests.core.settings.JDISettings.*;
import static com.ggasoftware.jdiuitests.core.utils.common.ReflectionUtils.isInterface;
import static com.ggasoftware.jdiuitests.implementation.selenium.elements.CascadeInit.InitElements;
import static com.ggasoftware.jdiuitests.implementation.selenium.elements.CascadeInit.firstInstance;
import static java.lang.String.format;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

/**
 * Created by Roman_Iovlev on 6/10/2015.
 */
public abstract class BaseElement implements IBaseElement {
    public static boolean createFreeInstance = false;
    public static ActionScenrios actionScenrios = new ActionScenrios();
    public static JActionTT<String, JActionT<String>> doActionRule = (text, action) -> {
        if (text == null) return;
        action.invoke(text);
    };
    public static JActionTT<String, JActionT<String>> setValueEmptyAction = (text, action) -> {
        if (text == null || text.equals("")) return;
        action.invoke(text.equals("#CLEAR#") ? "" : text);
    };
    public Functions function = Functions.NONE;
    public GetElementModule avatar;
    public ActionInvoker invoker = new ActionInvoker(this);
    protected String parentTypeName = "";
    protected GetElement getElement = new GetElement(this);
    protected ElementsActions actions = new ElementsActions(this);
    private String name;
    private String varName;
    private String typeName;

    public BaseElement() {
        this(null);
    }

    public BaseElement(By byLocator) {
        avatar = new GetElementModule(byLocator, this);
        if (isInterface(getClass(), IComposite.class) && !createFreeInstance && firstInstance)
            InitElements(this);
        MapInterfaceToElement.updateInterfacesMap(seleniumDefaultMap());
    }

    public static void setActionScenarios(ActionScenrios actionScenrios) {
        BaseElement.actionScenrios = actionScenrios;
    }

    public static void setValueRule(String text, JActionT<String> action) {
        doActionRule.invoke(text, action);
    }

    private static Object[][] seleniumDefaultMap() {
        return new Object[][]{
                {IElement.class, Element.class},
                {IButton.class, Button.class},
                {IClickable.class, Clickable.class},
                {IComboBox.class, ComboBox.class},
                {ILink.class, Link.class},
                {ISelector.class, Selector.class},
                {IText.class, Text.class},
                {IImage.class, Image.class},
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
        };
    }

    public String getName() {
        return name != null ? name : getTypeName();
    }

    public void setName(Field field) {
        this.name = AnnotationsUtil.getElementName(field);
        this.varName = field.getName();
    }

    public String getVarName() {
        return varName != null ? varName : getName();
    }

    public void setName(String name) {
        this.name = name;
    }

    protected Timer timer() {
        return avatar.timer();
    }

    protected JavascriptExecutor jsExecutor() {
        return (JavascriptExecutor) getDriver();
    }

    public boolean haveLocator() {
        return avatar.haveLocator();
    }

    public WebDriver getDriver() {
        return avatar.getDriver();
    }

    public By getLocator() {
        return avatar.byLocator;
    }

    public GetElementModule getAvatar() {
        return avatar;
    }

    public BaseElement setAvatar(GetElementModule avatar) {
        this.avatar = avatar;
        return this;
    }

    public BaseElement setAvatar(By byLocator, GetElementModule avatar) {
        this.avatar = new GetElementModule(byLocator, avatar.context, this);
        this.avatar.localElementSearchCriteria = avatar.localElementSearchCriteria;
        return this;
    }
    public void setWaitTimeout(long mSeconds) {
        logger.debug("Set wait timeout to " + mSeconds);
        getDriver().manage().timeouts().implicitlyWait(mSeconds, MILLISECONDS);
    }

    public void restoreWaitTimeout() {
        setWaitTimeout(timeouts.waitElementSec);
    }

    protected String getTypeName() {
        return (typeName != null) ? typeName : getClass().getSimpleName();
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    protected String getParentName() {
        return parentTypeName;
    }

    protected void setParentName(String parrentName) {
        parentTypeName = parrentName;
    }

    public void logAction(String actionName, LogSettings logSettings) {
        logger.toLog(format(shortLogMessagesFormat
                ? "%s for %s"
                : "Perform action '%s' with Element (%s)", actionName, this.toString()), logSettings);
    }

    public void logAction(String actionName) {
        logAction(actionName, new LogSettings());
    }

    @Override
    public String toString() {
        return MessageFormat.format(shortLogMessagesFormat
                        ? "{1} ''{0}'' ({2}.{3}; {4})"
                        : "Name: ''{0}'', Type: ''{1}'' In: ''{2}'', {4}",
                getName(), getTypeName(), getParentName(), getVarName(), avatar);
    }
}
