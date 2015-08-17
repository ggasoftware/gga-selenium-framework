package com.ggasoftware.jdi_ui_tests.implementations.elements.selenium.base;

import com.ggasoftware.jdi_ui_tests.core.elements.base.AElementHandler;
import com.ggasoftware.jdi_ui_tests.core.elements.interfaces.base.IBaseElement;
import com.ggasoftware.jdi_ui_tests.utils.common.WebDriverByUtils;
import com.ggasoftware.jdi_ui_tests.utils.linqInterfaces.JFuncTT;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

import static com.ggasoftware.jdi_ui_tests.core.settings.DriverFactory.*;
import static com.ggasoftware.jdi_ui_tests.implementations.elements.selenium.base.ContextType.Locator;
import static com.ggasoftware.jdi_ui_tests.utils.common.WebDriverByUtils.fillByTemplate;
import static com.ggasoftware.jdi_ui_tests.utils.usefulUtils.TryCatchUtil.tryGetResult;
import static java.lang.String.format;

/**
 * Created by Roman_Iovlev on 7/3/2015.
 */
public class WebElementsHandler extends AElementHandler<WebDriver, WebElement, By> {
    private List<ByContext> context = new ArrayList<>();

    public WebElementsHandler(IBaseElement element) {
        this.element = element;
        driverName = getCurrentDriverName();
    }
    public WebElementsHandler(By byLocator, IBaseElement element) {
        this(element);
        this.locator = byLocator;
    }
    public WebElementsHandler(By byLocator, List<ByContext> context, IBaseElement element) {
        this(element);
        this.locator = byLocator;
        this.context = context;
    }
    public WebElementsHandler getFilledTemplate(String name, IBaseElement resultElement) {
        return new WebElementsHandler(fillByTemplate(getLocator(), name), getContextCopy(), resultElement);
    }

    public List<ByContext> getContextCopy() {
        List<ByContext> result = new ArrayList<>();
        context.forEach(result::add);
        return result;
    }

    protected List<WebElement> searchElements() {
        if (context == null || context.size() == 0)
            return getDriver().findElements(locator);
        return getSearchContext(correctXPaths(context)).findElements(correctXPaths(locator));
    }
    protected JFuncTT<WebElement, Boolean> defaultSearchCriteria() { return WebElement::isDisplayed; }

    private SearchContext getSearchContext(List<ByContext> context) {
        SearchContext searchContext = getDriver().switchTo().defaultContent();
        for (ByContext locator : context) {
            WebElement element = searchContext.findElement(locator.byLocator);
            if (locator.type == Locator)
                searchContext = element;
            else {
                getDriver().switchTo().frame(element);
                searchContext = getDriver();
            }
        }
        return searchContext;
    }

    private List<ByContext> correctXPaths(List<ByContext> context) {
        if (context.size() == 1) return context;
        for (ByContext pair : context.subList(1, context.size())) {
            By byValue = pair.byLocator;
            if (byValue.toString().contains("By.xpath: //"))
                pair.byLocator = tryGetResult(() -> WebDriverByUtils.getByFunc(byValue).invoke(WebDriverByUtils.getByLocator(byValue)
                        .replaceFirst("/", "./")));
        }
        return context;
    }
    private By correctXPaths(By byValue) {
        return (byValue.toString().contains("By.xpath: //"))
                ? tryGetResult(() -> WebDriverByUtils.getByFunc(byValue).invoke(WebDriverByUtils.getByLocator(byValue)
                .replaceFirst("/", "./")))
                : byValue;
    }

    public void clearCookies() { getDriver().manage().deleteAllCookies(); }

    @Override
    public String toString() {
        return format("Locator: '%s'", locator) +
                ((context.size() > 0) ? format(", Context: '%s'", context) : "");
    }
}
