/*
 * Copyright 2004-2016 EPAM Systems
 *
 * This file is part of JDI project.
 *
 * JDI is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * JDI is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; 
 * without even the implied warranty ofMERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. 
 * See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with JDI. If not, see <http://www.gnu.org/licenses/>.
 */
 
package com.ggasoftware.jdiuitest.web.selenium.elements.apiInteract;

import com.ggasoftware.jdiuitest.core.interfaces.base.IBaseElement;
import com.ggasoftware.jdiuitest.core.settings.JDISettings;
import com.ggasoftware.jdiuitest.core.utils.common.LinqUtils;
import com.ggasoftware.jdiuitest.core.utils.common.PrintUtils;
import com.ggasoftware.jdiuitest.core.utils.common.Timer;
import com.ggasoftware.jdiuitest.core.utils.linqinterfaces.JFuncTT;
import com.ggasoftware.jdiuitest.core.utils.pairs.Pair;
import com.ggasoftware.jdiuitest.core.utils.pairs.Pairs;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

import static com.ggasoftware.jdiuitest.core.utils.common.LinqUtils.where;
import static com.ggasoftware.jdiuitest.web.selenium.driver.WebDriverByUtils.*;
import static com.ggasoftware.jdiuitest.web.selenium.elements.WebSettings.driverFactory;
import static com.ggasoftware.jdiuitest.web.selenium.elements.WebSettings.getDriverFactory;
import static java.lang.String.format;

/**
 * Created by Roman_Iovlev on 7/3/2015.
 */
public class GetElementModule {
    private static final String failedToFindElementMessage = "Can't find Element '%s' during %s seconds";
    private static final String findToMuchElementsMessage = "Find %s elements instead of one for Element '%s' during %s seconds";
    public By byLocator;
    public Pairs<ContextType, By> context = new Pairs<>();
    public JFuncTT<WebElement, Boolean> localElementSearchCriteria = null;
    public WebElement rootElement;
    private String driverName = "";
    private IBaseElement element;

    public GetElementModule(IBaseElement element) {
        this.element = element;
        driverName = driverFactory.currentDriverName();
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

    public GetElementModule(By byLocator, WebElement rootElement, IBaseElement element) {
        this(element);
        this.byLocator = byLocator;
        this.rootElement = rootElement;
    }

    public boolean haveLocator() {
        return byLocator != null;
    }

    public String printContext() {
        return context.toString();
    }

    public WebDriver getDriver() {
        return (WebDriver) driverFactory.getDriver(driverName);
    }

    public WebElement getElement() {
        JDISettings.logger.debug("Get Web Element: " + element);
        WebElement element = timer().getResultByCondition(this::getElementAction, el -> el != null);
        JDISettings.logger.debug("One Element found");
        return element;
    }

    public List<WebElement> getElements() {
        JDISettings.logger.debug("Get Web elements: " + element);
        List<WebElement> elements = getElementsAction();
        JDISettings.logger.debug("Found %s elements", elements.size());
        return elements;
    }

    public Timer timer() {
        return new Timer(JDISettings.timeouts.currentTimeoutSec * 1000);
    }

    private List<WebElement> getElementsAction() {
        List<WebElement> result = timer().getResultByCondition(
                this::searchElements,
                els -> where(els, getSearchCriteria()::invoke).size() > 0);
        JDISettings.timeouts.dropTimeouts();
        if (result == null)
            throw JDISettings.exception("Can't get Web Elements");
        return LinqUtils.where(result, getSearchCriteria()::invoke);
    }

    private JFuncTT<WebElement, Boolean> getSearchCriteria() {
        return localElementSearchCriteria != null ? localElementSearchCriteria : getDriverFactory().elementSearchCriteria;
    }

    public GetElementModule searchAll() {
        localElementSearchCriteria = el -> el != null;
        return this;
    }

    private WebElement getElementAction() {
        int timeout = JDISettings.timeouts.currentTimeoutSec;
        List<WebElement> result = getElementsAction();
        switch (result.size()) {
            case 0:
                throw JDISettings.exception(failedToFindElementMessage, element, timeout);
            case 1:
                return result.get(0);
            default:
                throw JDISettings.exception(findToMuchElementsMessage, result.size(), element, timeout);
        }
    }

    private List<WebElement> searchElements() {
        if (this.context == null || this.context.isEmpty())
            return getDriver().findElements(byLocator);
        SearchContext context = (rootElement != null)
                ? rootElement
                : getSearchContext(correctXPaths(this.context));
        return context.findElements(correctXPaths(byLocator));
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
                pair.value = getByFunc(byValue).invoke(getByLocator(byValue)
                        .replaceFirst("/", "./"));
        }
        return context;
    }

    private By correctXPaths(By byValue) {
        return (byValue.toString().contains("By.xpath: //"))
                ? getByFunc(byValue).invoke(getByLocator(byValue)
                .replaceFirst("/", "./"))
                : byValue;
    }

    public void clearCookies() {
        getDriver().manage().deleteAllCookies();
    }

    @Override
    public String toString() {
        return JDISettings.shortLogMessagesFormat
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
            result = LinqUtils.select(context, el -> printShortBy(el.value));
        result.add(printShortBy(byLocator));
        return PrintUtils.print(result);
    }

    private String printShortBy(By by) {
        return String.format("%s='%s'", getByName(by), getByLocator(by));
    }
}
