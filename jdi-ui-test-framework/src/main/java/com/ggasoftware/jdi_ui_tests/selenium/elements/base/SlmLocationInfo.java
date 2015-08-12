package com.ggasoftware.jdi_ui_tests.selenium.elements.base;

import com.ggasoftware.jdi_ui_tests.core.drivers.DriverFactory;
import com.ggasoftware.jdi_ui_tests.core.drivers.JLocationInfo;
import com.ggasoftware.jdi_ui_tests.core.elements.interfaces.base.IBaseElement;
import com.ggasoftware.jdi_ui_tests.settings.JDISettings;
import com.ggasoftware.jdi_ui_tests.utils.common.WebDriverByUtils;
import com.ggasoftware.jdi_ui_tests.utils.linqInterfaces.JFuncTT;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

import static com.ggasoftware.jdi_ui_tests.core.drivers.DriverFactory.getCurrentDriverName;
import static com.ggasoftware.jdi_ui_tests.selenium.ContextType.Locator;
import static com.ggasoftware.jdi_ui_tests.settings.JDISettings.asserter;
import static com.ggasoftware.jdi_ui_tests.settings.JDISettings.timeouts;
import static com.ggasoftware.jdi_ui_tests.utils.common.LinqUtils.where;
import static com.ggasoftware.jdi_ui_tests.utils.usefulUtils.TryCatchUtil.tryGetResult;
import static java.lang.String.format;

/**
 * Created by Roman_Iovlev on 7/3/2015.
 */
public class SlmLocationInfo implements JLocationInfo<WebDriver, WebElement, By, ByContext> {
    public By byLocator;
    private String driverName = "";
    private IBaseElement element;

    public List<ByContext> context = new ArrayList<>();
    public List<ByContext> getContext() { return context; }
    public String printContext() { return context.toString(); }
    public void init(IBaseElement<WebDriver, By> element) {
        init(null, null, element);
    }
    public void init(By byLocator, IBaseElement<WebDriver, By> element) {
        init(byLocator, null, element);
    }

    public void init(By byLocator, List<ByContext> context, IBaseElement<WebDriver, By> element) {
        driverName = getCurrentDriverName();
        this.element = element;
        this.byLocator = byLocator;
        this.context = context;
    }

    public boolean haveLocator() { return byLocator != null; }
    public WebDriver getDriver() {
        return (WebDriver) DriverFactory.getDriver(driverName); }

    public By getLocator() { return byLocator; }

    public List<WebElement> getElementsAction() {
        List<WebElement> webElements = timer().getResultByCondition(
                this::searchElements,
                els -> where(els, this::getSearchCriteria).size() > 0);
        timeouts.dropTimeouts();
        return webElements;
    }
    public JFuncTT<WebElement, Boolean> localElementSearchCriteria = null;
    private boolean getSearchCriteria(WebElement element) {
        try {
            if (localElementSearchCriteria != null)
                return localElementSearchCriteria.invoke(element);
            if (JDISettings.elementSearchCriteria != null)
                return JDISettings.elementSearchCriteria.invoke(new SlmElement(element));
        } catch (Exception ex) { asserter.exception("can't fet search criteria"); }
        return false;
    }

    private List<WebElement> searchElements() {
        if (context == null || context.size() == 0)
            return getDriver().findElements(byLocator);
        return getSearchContext(correctXPaths(context)).findElements(correctXPaths(byLocator));
    }
    private SearchContext getSearchContext(List<ByContext> context) {
        SearchContext searchContext = getDriver().switchTo().defaultContent();
        for (ByContext ctx : context) {
            WebElement element = searchContext.findElement(ctx.byLocator);
            if (ctx.type == Locator)
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
        for (ByContext ctx : context.subList(1, context.size() - 1)) {
            By byValue = ctx.byLocator;
            if (byValue.toString().contains("By.xpath: //"))
                ctx.byLocator = tryGetResult(() -> WebDriverByUtils.getByFunc(byValue).invoke(WebDriverByUtils.getByLocator(byValue)
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

    public String printElement() { return this.toString(); }

    @Override
    public String toString() {
        return format("Locator: '%s'", byLocator) +
                ((context.size() > 0)
                        ? format(", Context: '%s'", context)
                        : "");
    }
}
