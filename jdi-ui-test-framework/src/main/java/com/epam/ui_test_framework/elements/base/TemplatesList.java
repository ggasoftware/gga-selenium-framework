package com.epam.ui_test_framework.elements.base;

import com.epam.ui_test_framework.elements.BaseElement;
import com.epam.ui_test_framework.elements.interfaces.base.IElement;
import com.epam.ui_test_framework.elements.interfaces.base.IVisible;
import com.epam.ui_test_framework.utils.common.EnumUtils;
import com.epam.ui_test_framework.utils.common.Timer;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.stream.Collectors;

import static com.epam.ui_test_framework.elements.base.Element.copy;
import static com.epam.ui_test_framework.utils.common.EnumUtils.getEnumValue;
import static com.epam.ui_test_framework.utils.common.LinqUtils.*;
import static com.epam.ui_test_framework.utils.common.WebDriverByUtils.fillByTemplateSilent;
import static com.epam.ui_test_framework.utils.settings.FrameworkSettings.asserter;
import static com.epam.ui_test_framework.utils.settings.FrameworkSettings.timeouts;
import static java.lang.String.format;

/**
 * Created by Roman_Iovlev on 7/3/2015.
 */
public class TemplatesList<TType extends IElement, TEnum extends Enum> extends BaseElement implements IVisible{
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

    public void setListOfElements(List<String> elementsNames) { this.elementsNames = elementsNames; }
    public void setListOfElements(TEnum enumMember) { this.elementsNames =
            (List<String>) select(enumMember.getClass().getEnumConstants(), EnumUtils::getEnumValue); }
    protected List<String> elementsNames;
    private TType templateElement;

    public boolean isDisplayed() { return waitDisplayed(0); }
    public boolean waitDisplayed() { return waitDisplayed(timeouts.waitElementSec); }
    public boolean waitDisplayed(int seconds) {
        setWaitTimeout(seconds);
        boolean result = new Timer(seconds*1000).wait(() -> where(getElementsList(), IVisible::isDisplayed).size() > 0);
        setWaitTimeout(timeouts.waitElementSec);
        return result;
    }

    public boolean waitVanished() { return waitDisplayed(timeouts.waitElementSec); }
    public boolean waitVanished(int seconds)  {
        setWaitTimeout(timeouts.retryMSec);
        boolean result = new Timer(seconds*1000).wait(() -> where(getElementsList(), IVisible::isDisplayed).size() == 0);
        setWaitTimeout(timeouts.waitElementSec);
        return result;
    }
    public WebElement getWebElement(String name) { return getElement(name).getWebElement(); }
    public WebElement getWebElement(TEnum enumName) { return getElement(enumName).getWebElement(); }

    public TType getElement(String name) {
        return copy(templateElement, fillByTemplateSilent(getLocator(), name));
    }
    public TType getElement(TEnum enumName) {
        return getElement(getEnumValue(enumName));
    }

    public List<WebElement> getWebElements(TEnum enumName) {
        return (List<WebElement>) select(getElementsList(), IElement::getWebElement); }
    protected List<TType> getElementsListAction() {
        try { return elementsNames.stream().map(this::getElement).collect(Collectors.toList());
        } catch (Exception ex) { asserter.exception(ex.getMessage()); return null; }
    }
    public final List<TType> getElementsList() {
        if (elementsNames == null)
            asserter.exception(format("Please specify elements names for list element '%s'", toString()));
        return doJActionResult("Get elements", this::getElementsListAction);
    }
    public int count() {
        return getElementsList().size();
    }
}
