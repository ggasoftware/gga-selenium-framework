package com.ggasoftware.jdiuitests.implementation.selenium.elements.apiInteract;

import com.ggasoftware.jdiuitests.core.utils.common.Timer;
import com.ggasoftware.jdiuitests.core.utils.common.WebDriverByUtils;
import com.ggasoftware.jdiuitests.core.utils.linqInterfaces.JFuncTT;
import com.ggasoftware.jdiuitests.core.utils.pairs.Pair;
import com.ggasoftware.jdiuitests.core.utils.pairs.Pairs;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.interfaces.base.IBaseElement;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

import static com.ggasoftware.jdiuitests.core.settings.JDISettings.*;
import static com.ggasoftware.jdiuitests.core.utils.common.LinqUtils.select;
import static com.ggasoftware.jdiuitests.core.utils.common.LinqUtils.where;
import static com.ggasoftware.jdiuitests.core.utils.common.PrintUtils.print;
import static com.ggasoftware.jdiuitests.core.utils.common.WebDriverByUtils.*;
import static java.lang.String.format;

/**
 * Created by Roman_Iovlev on 7/3/2015.
 */
public class GetElementModule {
    public By byLocator;
    public boolean haveLocator() { return byLocator != null; }
    private String driverName = "";
    private IBaseElement element;

    public Pairs<ContextType, By> context = new Pairs<>();
    public String printContext() { return context.toString(); }

    public GetElementModule(IBaseElement element) {
        this.element = element;
        driverName = driverFactory.currentDriverName;
    }
    public GetElementModule(By byLocator, IBaseElement element) {
        this(element);
        this.byLocator = byLocator;
    }
    public GetElementModule(By byLocator, Pairs<ContextType, By> context, IBaseElement element) {
        this(element);
        this.byLocator = byLocator;
        this.context = context;
    }

    public WebDriver getDriver() { return driverFactory.getDriver(driverName); }

    public WebElement getElement() {
        logger.debug("Get Web Element: " + element);
        WebElement element = Timer.getByCondition(this::getElementAction, el -> el != null);
        logger.debug("One Element found");
        return element;
    }

    public List<WebElement> getElements() {
        logger.debug("Get Web elements: " + element);
        List<WebElement> elements = getElementsAction();
        logger.debug("Found %s elements", elements.size());
        return elements;
    }

    public Timer timer() { return new Timer(timeouts.currentTimeoutSec * 1000); }
    private List<WebElement> getElementsAction() {
        List<WebElement> result = timer().getResultByCondition(
                this::searchElements,
                els -> where(els, getSearchCriteria()::invoke).size() > 0);
        timeouts.dropTimeouts();
        if (result == null)
            throw exception("Can't get Web Elements");
        return where(result, getSearchCriteria()::invoke);
    }
    public JFuncTT<WebElement, Boolean> localElementSearchCriteria = null;
    private JFuncTT<WebElement, Boolean> getSearchCriteria() {
        return localElementSearchCriteria != null ? localElementSearchCriteria : driverFactory.elementSearchCriteria;
    }
    public GetElementModule searchAll() { localElementSearchCriteria = el -> el!=null; return this; }

    private WebElement getElementAction() {
        int timeout = timeouts.currentTimeoutSec;
        List<WebElement> result = getElementsAction();
        if (result == null)
            throw exception(failedToFindElementMessage, element, timeout);
        if (result.size() > 1)
            throw exception(findToMuchElementsMessage, result.size(), element, timeout);
        return result.get(0);
    }

    private static final String failedToFindElementMessage = "Can't find Element '%s' during %s seconds";
    private static final String findToMuchElementsMessage = "Find %s elements instead of one for Element '%s' during %s seconds";

    private List<WebElement> searchElements() {
        if (context == null || context.size() == 0)
            return getDriver().findElements(byLocator);
        return getSearchContext(correctXPaths(context)).findElements(correctXPaths(byLocator));
    }
    private SearchContext getSearchContext(Pairs<ContextType, By> context) {
        SearchContext searchContext = getDriver().switchTo().defaultContent();
        for (Pair<ContextType, By> locator : context) {
            WebElement element = searchContext.findElement(locator.value);
            if (locator.key == ContextType.Locator)
                searchContext = element;
            else {
                getDriver().switchTo().frame(element);
                searchContext = getDriver();
            }
        }
        return searchContext;
    }

    private Pairs<ContextType, By> correctXPaths(Pairs<ContextType, By> context) {
        if (context.size() == 1) return context;
        for (Pair<ContextType, By> pair : context.subList(1)) {
            By byValue = pair.value;
            if (byValue.toString().contains("By.xpath: //"))
                pair.value = getByFunc(byValue).invoke(WebDriverByUtils.getByLocator(byValue)
                        .replaceFirst("/", "./"));
        }
        return context;
    }
    private By correctXPaths(By byValue) {
        return (byValue.toString().contains("By.xpath: //"))
                ? getByFunc(byValue).invoke(WebDriverByUtils.getByLocator(byValue)
                .replaceFirst("/", "./"))
                : byValue;
    }

    public void clearCookies() { getDriver().manage().deleteAllCookies(); }

    @Override
    public String toString() {
        return shortLogMessagesFormat
            ? printFullLocator()
            : format("Locator: '%s'", byLocator) +
                ((context.size() > 0)
                        ? format(", Context: '%s'", context)
                        : "");
    }

    private String printFullLocator() {
        if (byLocator == null)
             return "No Locators";
        List<String> result = new ArrayList<>();
        if (context.size() != 0)
            result = select(context, el -> printShortBy(el.value));
        result.add(printShortBy(byLocator));
        return print(result);
    }

    private String printShortBy(By by) {
        return String.format("%s='%s'", getByName(by), getByLocator(by));
    }
}
