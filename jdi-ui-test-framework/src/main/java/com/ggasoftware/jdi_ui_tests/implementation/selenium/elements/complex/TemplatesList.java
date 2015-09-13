package com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.complex;

import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.BaseElement;
import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.base.Element;
import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.interfaces.base.IElement;
import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.interfaces.base.IVisible;
import com.ggasoftware.jdi_ui_tests.core.utils.common.EnumUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.stream.Collectors;

import static com.ggasoftware.jdi_ui_tests.core.utils.common.WebDriverByUtils.fillByTemplate;
import static com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.base.Element.copy;
import static com.ggasoftware.jdi_ui_tests.core.settings.JDISettings.asserter;
import static com.ggasoftware.jdi_ui_tests.core.settings.JDISettings.timeouts;
import static com.ggasoftware.jdi_ui_tests.core.utils.common.EnumUtils.getEnumValue;
import static com.ggasoftware.jdi_ui_tests.core.utils.common.LinqUtils.first;
import static com.ggasoftware.jdi_ui_tests.core.utils.common.LinqUtils.select;
import static java.lang.String.format;

/**
 * Created by Roman_Iovlev on 7/3/2015.
 */
abstract class TemplatesList<TType extends Element, TEnum extends Enum> extends BaseElement implements IVisible {
    public TemplatesList() { }
    public TemplatesList(By byLocator, TType templateElement) {
        super(byLocator);
        this.templateElement = templateElement;
    }
    public TemplatesList(By byLocator, TType templateElement, TEnum enumMember) {
        super(byLocator);
        this.templateElement = templateElement;
        elementsNames = select(enumMember.getClass().getEnumConstants(), EnumUtils::getEnumValue);
    }

    public void setListOfElements(List<String> elementsNames) { this.elementsNames = elementsNames; }
    public void setListOfElements(TEnum enumMember) { this.elementsNames =
            select(enumMember.getClass().getEnumConstants(), EnumUtils::getEnumValue); }
    protected List<String> elementsNames;
    private TType templateElement;
    protected TType getTemplateElement() {
        if (templateElement == null)
            templateElement = getDefaultElement(getLocator());
        return templateElement;
    }
    protected abstract TType getDefaultElement(By locator);

    public boolean waitDisplayed() {
        return timer().wait(() -> first(getElementsList(), el -> el.getWebElement().isDisplayed()) != null);
    }

    public boolean waitVanished()  {
        setWaitTimeout(timeouts.retryMSec);
        boolean result = timer().wait(() -> {
                for (TType el : getElementsList())
                    try { if (el.getWebElement().isDisplayed()) return false;
                    } catch (Exception|AssertionError ignore) { }
                return true;
            });
        setWaitTimeout(timeouts.waitElementSec);
        return result;
    }

    public WebElement getWebElement(String name) { return getElement(name).getWebElement(); }
    public WebElement getWebElement(TEnum enumName) { return getElement(enumName).getWebElement(); }

    public TType getElement(String name) {
        return copy(getTemplateElement() , fillByTemplate(getLocator(), name));
    }
    public TType getElement(TEnum enumName) {
        return getElement(getEnumValue(enumName));
    }

    public List<WebElement> getWebElements(TEnum enumName) {
        return select(getElementsList(), IElement::getWebElement); }
    protected List<TType> getElementsListAction() {
        try { return elementsNames.stream().map(this::getElement).collect(Collectors.toList());
        } catch (Exception|AssertionError ex) { throw asserter.exception(ex.getMessage()); }
    }
    public final List<TType> getElementsList() {
        if (elementsNames == null || elementsNames.size() == 0)
            throw asserter.exception(format("Please specify elements names for list Element '%s'", toString()));
        return invoker.doJActionResult("Get elements", this::getElementsListAction);
    }
    public int count() {
        return getElementsList().size();
    }
}
