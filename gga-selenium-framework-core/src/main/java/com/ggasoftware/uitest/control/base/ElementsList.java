package com.ggasoftware.uitest.control.base;

import com.ggasoftware.uitest.control.BaseElement;
import com.ggasoftware.uitest.control.interfaces.IList;
import com.ggasoftware.uitest.utils.common.Timer;
import com.ggasoftware.uitest.utils.map.MapArray;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.lang.reflect.Field;
import java.util.List;

import static com.ggasoftware.uitest.utils.TryCatchUtil.tryGetResult;
import static com.ggasoftware.uitest.utils.common.LinqUtils.*;
import static com.ggasoftware.uitest.utils.common.PrintUtils.print;
import static com.ggasoftware.uitest.utils.settings.FrameworkSettings.timeouts;
import static java.lang.String.format;

/**
 * Created by Roman_Iovlev on 7/3/2015.
 */
public class ElementsList<TEnum extends Enum> extends BaseElement implements IList<TEnum> {
    public ElementsList() { }
    public ElementsList(By byLocator) { super(byLocator); }
    public ElementsList(String name, By byLocator) { super(name, byLocator); }

    public List<WebElement> getWebElements() throws Exception {
        return getWebElements(timeouts.waitElementSec);
    }

    public List<WebElement> getWebElements(int timeouInSec) throws Exception {
        timeouts.currentTimoutSec = timeouInSec;
        List<WebElement> element = doJActionResult("Get web elements " + this.toString(), avatar::getElements);
        timeouts.currentTimoutSec = timeouts.waitElementSec;
        return element;
    }

    public boolean isDisplayed() { return waitDisplayed(0); }
    public boolean waitDisplayed() { return waitDisplayed(timeouts.waitElementSec); }
    public boolean waitDisplayed(int seconds) {
        setWaitTimeout(seconds);
        boolean result = new Timer(seconds*1000).wait(() -> getWebElements().size() > 0);
        setWaitTimeout(timeouts.waitElementSec);
        return result;
    }

    public boolean waitVanished() { return waitDisplayed(timeouts.waitElementSec); }
    public boolean waitVanished(int seconds)  {
        setWaitTimeout(timeouts.retryMSec);
        boolean result = new Timer(seconds*1000).wait(() -> getWebElements().size() == 0);
        setWaitTimeout(timeouts.waitElementSec);
        return result;
    }

    public WebElement getElement(String name) throws Exception {
        return first(getWebElements(), el -> el.getText().equals(name));
    }
    public WebElement getElement(int index) throws Exception {
        return getWebElements().get(index);
    }
    public WebElement getElement(TEnum enumName) throws Exception {
        return getElement(getEnumValue(enumName));
    }

    protected String getEnumValue(TEnum enumWithValue) {
        Field field;
        try { field = enumWithValue.getClass().getField("value");
            if (field.getType() != String.class)
                throw new Exception("Can't get Value from enum");
        } catch (Exception ex) { return enumWithValue.toString(); }
        return tryGetResult(() -> (String) field.get(enumWithValue));
    }

    public MapArray<String, WebElement> getElements() throws Exception {
        return new MapArray<>(getWebElements(), WebElement::getText, value -> value);
    }

    protected String getTextAction(WebElement element) throws Exception { return element.getText(); }

    public final String getText(String name) throws Exception {
        return doJActionResult(format("Get text for element '%s' with name '%s'", this.toString(), name),
            () -> getTextAction(getElement(name)));
    }

    public String getText(int index) throws Exception {
        return doJActionResult(format("Get text for element '%s' with index '%s'", this.toString(), index),
                () -> getTextAction(getElement(index)));
    }
    public String getText(TEnum enumName) throws Exception {
        return getText(getEnumValue(enumName));
    }
    public int count() throws Exception {
        return getElements().size();
    }

    protected String getValueAction() throws Exception { return print(select(getWebElements(), WebElement::getText)); }
    public final String getValue() throws Exception { return doJActionResult("Get value", this::getValueAction); }
}
