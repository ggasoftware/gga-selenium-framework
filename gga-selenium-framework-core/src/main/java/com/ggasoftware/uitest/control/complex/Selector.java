package com.ggasoftware.uitest.control.complex;

import com.ggasoftware.uitest.control.BaseElement;
import com.ggasoftware.uitest.control.complex.table.ClickableText;
import com.ggasoftware.uitest.control.interfaces.ISelector;
import com.ggasoftware.uitest.control.base.ElementsList;
import com.ggasoftware.uitest.utils.common.LinqUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.Collection;
import java.util.List;

import static com.ggasoftware.uitest.utils.TryCatchUtil.ignoreException;
import static com.ggasoftware.uitest.utils.common.LinqUtils.first;
import static com.ggasoftware.uitest.utils.common.PrintUtils.print;
import static com.ggasoftware.uitest.utils.common.Timer.getByConditionAction;
import static com.ggasoftware.uitest.utils.common.WebDriverByUtils.fillByTemplate;
import static com.ggasoftware.uitest.utils.settings.FrameworkSettings.asserter;
import static java.lang.String.format;

/**
 * Created by roman.i on 03.10.2014.
 */

public class Selector<T extends Enum> extends ElementsList implements ISelector {
    public Selector() { super(); }
    public Selector(By optionsNamesLocator) {
        super(optionsNamesLocator); }
    public Selector(By optionsNamesLocator, By allOptionsNamesLocator) {
        super(optionsNamesLocator);
        allOptionsNames = new ElementsList(allOptionsNamesLocator);
    }

    private ElementsList allOptionsNames;

    protected ClickableText getElement(String name)  {
        return new ClickableText(name, ignoreException(() -> fillByTemplate(getLocator(), name)));
    }


    private TElement newElement(Class<TElement> clazz, String... args) {
        TElement element = null;
        try { element = clazz.getDeclaredConstructor(new Class[]{String.class, By.class})
                .newInstance(getName() + " with option " + print(args), avatar.fillLocatorTemplate(args)); }
        catch (Exception ignore) { }
        if (element == null)
            asserter.exception(format("Can't create new element '%s' with args '%s'", this, print(args)));
        return element;
    }

    protected WebElement getWebElement(String name)  {
        return getElement(name).getWebElement();
    }
    protected void selectAction(String name) {
        getElement(name).click();
    }
    protected void selectByIndexAction(int index) {
        getElement(getAllLabels().get(index)).click();
    }
    protected String getElementTextAction(String name) {
        return getElement(name).getText();
    }
    protected String getValueAction() {
        return getElement(isSelected()).getText();
    }
    protected boolean isSelectedAction(String value) {
        return getElement(value).getWebElement().isSelected();
    }
    protected List<String> getAllLabelsAction()  {
        if (allOptionsNames == null)
            asserter.exception("Please specify get all labels locator for element " + getName());
        List<WebElement> elements = allOptionsNames.getWebElements();
        return (List<String>) LinqUtils.select(elements, WebElement::getText);
    }
    protected final List<ClickableText> getAllElementsAction() {
        return (List<ClickableText>) LinqUtils.select(getAllLabels(), this::getElement);
    }
    public final void select(String valueName) { selectAction(valueName); }
    public final void select(T valueName) { selectAction(getEnumValue(valueName)); }
    public final void select(int index) { selectByIndexAction(index); }

    public final String getValue() {
        return getValueAction();
    }
    public final String getText(String valueName) {
        return getElementTextAction(valueName);
    }
    public final List<String> getAllLabels() {
        return getAllLabelsAction();
    }
    public final String isSelected() {
        Collection<String> elementNames = LinqUtils.select(getAllElementsAction(), BaseElement::getName);
        return first(elementNames, this::isSelected);
    }
    public final boolean isSelected(T e) {
        return isSelectedAction(getEnumValue(e));
    }
    public final boolean isSelected(String value) {
        return isSelectedAction(value);
    }
    public final String getAllLabelsAsText() {
        return print(getAllLabels());
    }
    public final String waitValue(String text) {
        return getByConditionAction(this::getValue, t -> t.equals(text));
    }

}
