package com.ggasoftware.uitest.control.base.apiInteract;

import com.ggasoftware.uitest.control.base.pairs.Pair;
import com.ggasoftware.uitest.control.base.pairs.Pairs;
import com.ggasoftware.uitest.control.interfaces.base.IBaseElement;
import com.ggasoftware.uitest.utils.Timer;
import com.ggasoftware.uitest.utils.WebDriverWrapper;
import com.ggasoftware.uitest.utils.linqInterfaces.JFuncTT;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

import static com.ggasoftware.uitest.control.base.asserter.TestNGAsserter.asserter;
import static com.ggasoftware.uitest.control.base.logger.TestNGLog4JLogger.logger;
import static com.ggasoftware.uitest.control.base.usefulUtils.TryCatchUtil.tryGetResult;
import static com.ggasoftware.uitest.utils.LinqUtils.where;
import static com.ggasoftware.uitest.utils.TestBaseWebDriver.defaultSearchCriteria;
import static com.ggasoftware.uitest.utils.TestBaseWebDriver.selectFirstElementIfMultiplefound;
import static com.ggasoftware.uitest.utils.Timer.getByCondition;
import static com.ggasoftware.uitest.utils.WebDriverByUtils.getByFunc;
import static com.ggasoftware.uitest.utils.WebDriverByUtils.getByLocator;
import static com.ggasoftware.uitest.utils.WebDriverWrapper.TIMEOUT;
import static java.lang.String.format;

/**
 * Created by Roman_Iovlev on 7/3/2015.
 */
public class GetElementModule {
    public By byLocator;
    public boolean haveLocator() { return byLocator != null; }
    private IBaseElement element;

    public Pairs<ContextType, By> context = new Pairs<>();
    public String printContext() { return context.toString(); }

    public GetElementModule(IBaseElement element) {
        this.element = element;
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

    public WebDriver getDriver() { return tryGetResult(WebDriverWrapper::getDriver); }

    public WebElement getElement() {
        logger.info("Get Web element: " + element);
        WebElement element = getByCondition(this::getElementAction, el -> el != null);
        logger.debug("One element found");
        return element;
    }

    public List<WebElement> getElements() {
        logger.info("Get Web elements: " + element);
        List<WebElement> elements = getElementsAction();
        logger.debug(format("Found %s elements", elements.size()));
        return elements;
    }

    private List<WebElement> getElementsAction() {
        return new Timer(TIMEOUT * 1000).getResultByCondition(
                this::searchElements,
                els -> where(els, getSearchCriteria()::invoke).size() > 0);
    }
    public JFuncTT<WebElement, Boolean> localElementSearchCriteria = null;
    private JFuncTT<WebElement, Boolean> getSearchCriteria() {
        return localElementSearchCriteria != null ? localElementSearchCriteria : defaultSearchCriteria;
    }

    private WebElement getElementAction() {
        int timeout = TIMEOUT;
        List<WebElement> result = getElementsAction();
        if (result == null) {
            asserter.exception(format(failedToFindElementMessage, element, timeout));
            return null;
        }
        if (!selectFirstElementIfMultiplefound && result.size() > 1) {
            asserter.exception(format(findToMuchElementsMessage, result.size(), element, timeout));
            return null;
        }
        return result.get(0);
    }

    private static final String failedToFindElementMessage = "Can't find element '%s' during %s seconds";
    private static final String findToMuchElementsMessage = "Find %s elements instead of one for element '%s' during %s seconds";

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
                pair.value = tryGetResult(() -> getByFunc(byValue).invoke(getByLocator(byValue)
                        .replaceFirst("/", "./")));
        }
        return context;
    }
    private By correctXPaths(By byValue) {
        return (byValue.toString().contains("By.xpath: //"))
                ? tryGetResult(() -> getByFunc(byValue).invoke(getByLocator(byValue)
                .replaceFirst("/", "./")))
                : byValue;
    }

    public void clearCookies() { getDriver().manage().deleteAllCookies(); }

    @Override
    public String toString() {
        return format("Locator: '%s'", byLocator) +
                ((context.size() > 0)
                        ? format(", Context: '%s'", context)
                        : "");
    }
}
