package com.ggasoftware.uitest.control.apiInteract;

import com.ggasoftware.uitest.utils.common.WebDriverByUtils;
import com.ggasoftware.uitest.utils.map.KeyValue;
import com.ggasoftware.uitest.utils.map.MapArray;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

import static com.ggasoftware.uitest.control.apiInteract.ContextType.Locator;
import static com.ggasoftware.uitest.utils.common.WebDriverByUtils.getByFunc;
import static com.ggasoftware.uitest.utils.common.WebDriverByUtils.getByLocator;
import static com.ggasoftware.uitest.utils.settings.FrameworkSettings.avatar;
import static java.lang.String.format;

/**
 * Created by Roman_Iovlev on 7/3/2015.
 */
public class GetElementModule2 {
    private By byLocator;
    public boolean haveLocator() { return byLocator != null; }
    private String driverName = "";

    private MapArray<ContextType, By> context = new MapArray<>();
    public void addToContext(By byLocator) { addToContext(Locator, byLocator); }
    public void addToContext(ContextType type, By byLocator) { context.add(type, byLocator); }
    public String printContext() { return context.toString(); }

    public GetElementModule2() {
        driverName = avatar.currentDriverName;
    }
    public GetElementModule2(By byLocator) {
        this();
        this.byLocator = byLocator;
    }

    private WebDriver getDriver() throws Exception {
        return avatar.useDriver(driverName);
    }

    public WebElement getElement() throws Exception {
        return getContext().findElement(byLocator);
    }

    public WebElement getElement(String value) throws Exception {
        By resultLocator = WebDriverByUtils.fillByTemplate(byLocator, value);
        return getContext().findElement(resultLocator);
    }
    public List<WebElement> getElements() throws Exception {
        return getContext().findElements(byLocator);
    }

    private SearchContext getContext() throws Exception {
        boolean isFirst = true;
        if (context == null || context.size() == 0)
            return getDriver();
        SearchContext searchContext = getDriver().switchTo().defaultContent();
        for (KeyValue<ContextType, By> locator : context) {
            if (!isFirst && locator.value.toString().contains("By.xpath: //"))
                locator.value = getByFunc(locator.value).invoke(getByLocator(locator.value)
                        .replaceFirst("/", "./"));
            WebElement element = searchContext.findElement(locator.value);
            if (locator.key == ContextType.Locator)
                searchContext = element;
            else {
                getDriver().switchTo().frame(element);
                searchContext = getDriver();
            }
            isFirst = false;
        }
        return searchContext;
    }

    @Override
    public String toString() {
        return format("Locator: '%s'", byLocator) +
                ((context.size() > 0)
                        ? format(", Context: '%s'", context)
                        : "");
    }
}
