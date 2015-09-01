package com.ggasoftware.uitest.control.new_controls.base;

import com.ggasoftware.uitest.control.Element;
import com.ggasoftware.uitest.control.interfaces.base.IElement;
import com.ggasoftware.uitest.control.interfaces.base.IVisible;
import com.ggasoftware.uitest.utils.EnumUtils;
import com.ggasoftware.uitest.utils.Timer;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.stream.Collectors;

import static com.ggasoftware.uitest.control.base.asserter.testNG.Assert.exception;
import static com.ggasoftware.uitest.utils.EnumUtils.getEnumValue;
import static com.ggasoftware.uitest.utils.LinqUtils.select;
import static com.ggasoftware.uitest.utils.LinqUtils.where;
import static com.ggasoftware.uitest.utils.WebDriverByUtils.fillByTemplateSilent;
import static com.ggasoftware.uitest.utils.WebDriverWrapper.TIMEOUT;
import static java.lang.String.format;

/**
 * Created by Roman_Iovlev on 7/3/2015.
 */
public abstract class TemplatesList<TType extends IElement, TEnum extends Enum, P> extends Element<P> implements IVisible {
    public TemplatesList() { }
    public TemplatesList(By byLocator, TType templateElement) {
        super(byLocator);
        this.templateElement = templateElement;
    }
    public TemplatesList(By byLocator, TType templateElement, TEnum enumMember) {
        super(byLocator);
        this.templateElement = templateElement;
        elementsNames = (List<String>) select(enumMember.getClass().getEnumConstants(), EnumUtils::getEnumValue);
    }
    public TemplatesList(String name, String locator, P parentPanel) {
        super(name, locator, parentPanel);
    }

    public void setListOfElements(List<String> elementsNames) { this.elementsNames = elementsNames; }
    public void setListOfElements(TEnum enumMember) { this.elementsNames =
            (List<String>) select(enumMember.getClass().getEnumConstants(), EnumUtils::getEnumValue); }
    protected List<String> elementsNames;
    private TType templateElement;
    protected TType getTemplateElement() {
        if (templateElement == null)
            templateElement = getDefaultElement(getByLocator());
        return templateElement;
    }
    protected abstract TType getDefaultElement(By locator);

    @Override
    public WebElement getWebElement() { return getTemplateElement().getWebElement(); }

    public boolean isDisplayed() { return waitDisplayed(0); }
    public boolean waitDisplayed() { return waitDisplayed(TIMEOUT); }
    public boolean waitDisplayed(int seconds) {
        setWaitTimeout(seconds);
        boolean result = new Timer(seconds*1000).wait(() -> where(getElementsList(), IVisible::isDisplayed).size() > 0);
        setWaitTimeout(TIMEOUT);
        return result;
    }

    public boolean waitVanished() { return waitDisplayed(TIMEOUT); }
    public boolean waitVanished(int seconds)  {
        setWaitTimeout(100);
        boolean result = new Timer(seconds*1000).wait(() -> where(getElementsList(), IVisible::isDisplayed).size() == 0);
        setWaitTimeout(TIMEOUT);
        return result;
    }
    public WebElement getWebElement(String name) { return getElement(name).getWebElement(); }
    public WebElement getWebElement(TEnum enumName) { return getElement(enumName).getWebElement(); }

    public TType getElement(String name) {
        return copy(getTemplateElement(), fillByTemplateSilent(getLocator(), name));
    }
    public TType getElement(TEnum enumName) {
        return getElement(getEnumValue(enumName));
    }

    public List<WebElement> getWebElements(TEnum enumName) {
        return (List<WebElement>) select(getElementsList(), IElement::getWebElement); }
    protected List<TType> getElementsListAction() {
        try { return elementsNames.stream().map(this::getElement).collect(Collectors.toList());
        } catch (Exception ex) { throw exception(ex.getMessage()); }
    }
    public final List<TType> getElementsList() {
        if (elementsNames == null)
            exception(format("Please specify elements names for list element '%s'", toString()));
        return doJActionResult("Get elements", this::getElementsListAction);
    }
    public int count() {
        return getElementsList().size();
    }
}
