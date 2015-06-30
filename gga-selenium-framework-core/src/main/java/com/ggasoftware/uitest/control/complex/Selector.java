package com.ggasoftware.uitest.control.complex;

import com.ggasoftware.uitest.control.complex.table.ClickableText;
import com.ggasoftware.uitest.control.interfaces.ISelector;
import com.ggasoftware.uitest.control.simple.Elements;
import com.ggasoftware.uitest.utils.common.LinqUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.lang.reflect.Field;
import java.util.List;

import static com.ggasoftware.uitest.utils.common.LinqUtils.first;
import static com.ggasoftware.uitest.utils.common.PrintUtils.print;
import static com.ggasoftware.uitest.utils.common.Timer.getByConditionAction;
import static com.ggasoftware.uitest.utils.common.Timer.tryGetResult;
import static com.ggasoftware.uitest.utils.common.WebDriverByUtils.fillByTemplate;
import static com.ggasoftware.uitest.utils.settings.FrameworkSettings.asserter;

/**
 * Created by roman.i on 03.10.2014.
 */

public class Selector<P> extends ClickableText<P> implements ISelector {
    public Selector() { super(); }
    public Selector(String name, By byLocator) { super(name, byLocator); }
    public Selector(String name, By byLocator, By optionsNamesLocator) {
        super(name, byLocator);
        optionsNames = new Elements<P>(name + " option", optionsNamesLocator);
    }

    private Elements<P> optionsNames;

    protected ClickableText getElementByTemplate(String name) {
        By byLocator = null;
        try { byLocator = fillByTemplate(getByLocator(), name); }
        catch (Exception ex) { asserter.exception(ex.getMessage()); }
        return new ClickableText(name, byLocator);
    }

    protected void selectAction(String name) {
        getElementByTemplate(name).click();
    }
    protected void selectByIndexAction(int index) {
        getElementByTemplate(getAllLabels().get(index)).click();
    }
    protected String getElementTextAction(String name) {
        return getElementByTemplate(name).getText();
    }
    protected String getValueAction() {
        return getElementByTemplate(isSelected()).getText();
    }
    protected String isSelectedAction() {
        ClickableText firstElement = first(getAllElementsAction(), cl -> cl.getWebElement().isSelected());
        return (firstElement != null) ? firstElement.getName() : null;
    }
    protected List<String> getAllLabelsAction() {
        return (List<String>) LinqUtils.select(LinqUtils.select(optionsNames.getWebElements(), WebElement::getText), this::getElementTextAction);
    }
    protected final List<ClickableText> getAllElementsAction() {
        return (List<ClickableText>)LinqUtils.select(getAllLabels(), this::getElementByTemplate);
    }
    public final void select(String valueName) { selectAction(valueName); }
    public final void select(Enum valueName) { selectAction(getEnumValue(valueName)); }
    public final void select(int index) { selectByIndexAction(index); }
    protected String getEnumValue(Enum enumWithValue) {
        Field field;
        try { field = enumWithValue.getClass().getField("value");
        } catch (Exception ex) { return enumWithValue.toString(); }
        return tryGetResult(() -> (String) field.get(enumWithValue));
    }

    public final String getValue()  {
        return getValueAction();
    }
    public final String getText(String valueName)  {
        return getElementTextAction(valueName);
    }
    public final List<String> getAllLabels()  {
        return getAllLabelsAction();
    }
    public final String isSelected()  {
        return isSelectedAction();
    }
    public final boolean isSelected(Enum e) {
        return isSelected().equals(e + "");
    }
    public final boolean isSelected(String value) {
        return isSelected().equals(value);
    }
    public final String getAllLabelsAsText() {
        String allLabels = null;
        try { allLabels = print(getAllLabels()); }
        catch (Exception ex) { asserter.exception(ex.getMessage()); }
        return allLabels;
    }
    public final String waitValue(String text) {
        return getByConditionAction(this::getValue, t -> t.equals(text));
    }
    public void selectNew() { select(1);}

}
