package com.ggasoftware.jdiuitests.implementation.selenium.elements.complex;

import com.ggasoftware.jdiuitests.core.utils.common.LinqUtils;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.BaseElement;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.GetElementType;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.base.Clickable;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.base.Element;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.interfaces.base.IVisible;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

import static com.ggasoftware.jdiuitests.core.settings.JDISettings.exception;
import static com.ggasoftware.jdiuitests.core.utils.common.EnumUtils.getEnumValue;
import static com.ggasoftware.jdiuitests.core.utils.common.Timer.waitCondition;
import static com.ggasoftware.jdiuitests.core.utils.common.WebDriverByUtils.fillByTemplate;

/**
 * Created by Roman_Iovlev on 7/9/2015.
 */
abstract class BaseSelector<TEnum extends Enum> extends BaseElement implements IVisible {
    protected boolean isSelector = false;
    private GetElementType allLabels = new GetElementType();

    public BaseSelector() {
        super();
    }

    public BaseSelector(By optionsNamesLocator) {
        super(optionsNamesLocator);
    }

    public BaseSelector(By optionsNamesLocator, By allLabelsLocator) {
        super(optionsNamesLocator);
        this.allLabels = new GetElementType(allLabelsLocator);
    }

    protected TextList<TEnum> allLabels() {
        return allLabels.get(new TextList<>(), getAvatar());
    }

    protected void selectAction(String name) {
        if (!haveLocator() && allLabels() == null)
            throw exception("Can't find option '%s'. No optionsNamesLocator and allLabelsLocator found", name);
        if (getLocator().toString().contains("%s")) {
            new Clickable(fillByTemplate(getLocator(), name)).click();
            return;
        }
        if (allLabels() != null) {
            selectFromList(allLabels().getWebElements(), name);
            return;
        }
        List<WebElement> els = getAvatar().searchAll().getElements();
        if (els.size() == 1)
            getSelector().selectByVisibleText(name);
        else
            selectFromList(els, name);
    }

    private void selectFromList(List<WebElement> els, String name) {
        WebElement element = LinqUtils.first(els, el -> el.getText().equals(name));
        if (element == null)
            throw exception("Can't find option '%s'. Please fix allLabelsLocator", name);
        element.click();
    }

    protected void selectAction(int index) {
        if (!haveLocator() && allLabels() == null)
            throw exception("Can't find option '%s'. No optionsNamesLocator and allLabelsLocator found", index);
        if (allLabels() != null) {
            selectFromList(allLabels().getWebElements(), index);
            return;
        }
        if (getLocator().toString().contains("%s")) {
            new Clickable(fillByTemplate(getLocator(), index)).click();
            return;
        }
        List<WebElement> els = getAvatar().searchAll().getElements();
        if (els.size() == 1)
            getSelector().selectByIndex(index);
        else
            selectFromList(els, index);
    }

    private void selectFromList(List<WebElement> els, int index) {
        if (index <= 0)
            throw exception("Can't get option with index '%s'. Index should be 1 or more", index);
        if (els == null)
            throw exception("Can't find option with index '%s'. Please fix allLabelsLocator", index);
        if (els.size() < index)
            throw exception("Can't find option with index '%s'. Find only '%s' options", index, els.size());
        els.get(index - 1).click();
    }

    protected abstract boolean isSelectedAction(String name);

    protected abstract boolean isSelectedAction(int index);

    protected boolean isSelectedAction(WebElement el) {
        if (isSelector)
            return el.isSelected();
        String attr = el.getAttribute("checked");
        return attr != null && attr.equals("true");
    }

    public final boolean waitSelected(String name) {
        return actions.isSelected(name, n -> waitCondition(() -> isSelectedAction(n)));
    }

    public final boolean waitSelected(TEnum name) {
        return waitSelected(getEnumValue(name));
    }

    public final boolean isSelected(String name) {
        return actions.isSelected(name, this::isSelectedAction);
    }

    public final boolean isSelected(TEnum name) {
        return isSelected(getEnumValue(name));
    }

    protected List<String> getOptionsAction() {
        return LinqUtils.select(getElements(), WebElement::getText);
    }

    protected abstract String getValueAction();

    protected void setValueAction(String value) {
        selectAction(value);
    }

    public final String getValue() {
        return actions.getValue(this::getValueAction);
    }

    public final void setValue(String value) {
        actions.setValue(value, this::setValueAction);
    }

    public final List<String> getOptions() {
        return getOptionsAction();
    }

    protected Select getSelector() {
        {
            isSelector = true;
            return new Select(new Element(getLocator()).getWebElement());
        }
    }

    protected List<WebElement> getElements() {
        if (!haveLocator() && allLabels() == null)
            throw exception("Can't check is element displayed or not. No optionsNamesLocator and allLabelsLocator found");
        if (allLabels() != null)
            return allLabels().getWebElements();
        if (getLocator().toString().contains("%s"))
            throw exception("Can't check is element displayed or not. Please specify allLabelsLocator or correct optionsNamesLocator (should not contain '%s')");
        List<WebElement> els = getAvatar().searchAll().getElements();
        if (els.size() == 1)
            els = getSelector().getAllSelectedOptions();
        return els;
    }

    protected boolean isDisplayedAction(String name) {
        if (!haveLocator() && allLabels() == null)
            throw exception("Can't check is option '%s' displayed. No optionsNamesLocator and allLabelsLocator found", name);
        if (getLocator().toString().contains("%s"))
            return new Clickable(fillByTemplate(getLocator(), name)).isDisplayed();
        if (allLabels() != null)
            return isDisplayedInList(allLabels().getWebElements(), name);
        List<WebElement> els;
        try {
            els = getAvatar().searchAll().getElements();
        } catch (Exception|Error ex) { return false; }
        return isDisplayedInList(els.size() == 1 ? getSelector().getOptions() : els, name);
    }

    private boolean isDisplayedInList(List<WebElement> els, String name) {
        WebElement element = LinqUtils.first(els, el -> el.getText().equals(name));
        return element != null && element.isDisplayed();
    }

    protected boolean isDisplayedAction(int index) {
        if (!haveLocator() && allLabels() == null)
            throw exception("Can't check is option '%s' displayed. No optionsNamesLocator and allLabelsLocator found", index);
        if (getLocator().toString().contains("%s"))
            return new Clickable(fillByTemplate(getLocator(), index)).isDisplayed();
        if (allLabels() != null)
            return isDisplayedInList(allLabels().getWebElements(), index);
        List<WebElement> els = getAvatar().searchAll().getElements();
        return isDisplayedInList(els.size() == 1 ? getSelector().getOptions() : els, index);
    }

    private boolean isDisplayedInList(List<WebElement> els, int index) {
        if (index <= 0)
            throw exception("Can't get option with index '%s'. Index should be 1 or more", index);
        if (els == null)
            throw exception("Can't find option with index '%s'. Please fix allLabelsLocator", index);
        if (els.size() < index)
            throw exception("Can't find option with index '%s'. Find '%s' options", index, els.size());
        return els.get(index - 1).isDisplayed();
    }

    protected boolean isDisplayedAction() {
        List<WebElement> els = actions.findImmediately(this::getElements, null);
        return els != null && els.size() > 0 && els.get(0).isDisplayed();
    }

    protected boolean waitDisplayedAction() {
        return timer().wait(() -> {
            List<WebElement> els = getElements();
            return els != null && els.size() > 0 && els.get(0).isDisplayed();
        });
    }

    protected boolean waitVanishedAction() {
        return timer().wait(() -> !isDisplayedAction());
    }

    public boolean isDisplayed() {
        return actions.isDisplayed(this::isDisplayedAction);
    }

    public boolean isHidden() {
        return actions.isDisplayed(() -> !isDisplayedAction());
    }

    public boolean waitDisplayed() {
        return actions.waitDisplayed(this::waitDisplayedAction);
    }

    public boolean waitVanished() {
        return actions.waitVanished(() -> timer().wait(() -> !isDisplayedAction()));
    }
}
