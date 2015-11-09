package com.ggasoftware.uitest.control.new_controls.complex;

import com.ggasoftware.uitest.control.base.logger.LogSettings;
import com.ggasoftware.uitest.control.base.map.MapArray;
import com.ggasoftware.uitest.control.interfaces.complex.ITextList;
import com.ggasoftware.uitest.control.new_controls.base.BaseElement;
import com.ggasoftware.uitest.utils.PrintUtils;
import com.ggasoftware.uitest.utils.Timer;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

import static com.ggasoftware.uitest.control.base.asserter.testNG.Assert.exception;
import static com.ggasoftware.uitest.control.base.logger.enums.LogInfoTypes.BUSINESS;
import static com.ggasoftware.uitest.control.base.logger.enums.LogLevels.DEBUG;
import static com.ggasoftware.uitest.utils.EnumUtils.getEnumValue;
import static com.ggasoftware.uitest.utils.LinqUtils.*;
import static com.ggasoftware.uitest.utils.PrintUtils.print;
import static com.ggasoftware.uitest.utils.Timer.waitCondition;
import static com.ggasoftware.uitest.utils.WebDriverWrapper.TIMEOUT;


/**
 * Created by Roman_Iovlev on 7/3/2015.
 */
public class TextList<TEnum extends Enum, P> extends BaseElement<P> implements ITextList<TEnum> {
    public TextList() {
    }

    public TextList(By byLocator) {
        super(byLocator);
    }

    public List<WebElement> getWebElements() {
        return getWebElements(TIMEOUT);
    }

    public List<WebElement> getWebElements(int timeouInSec) {
        setWaitTimeout(timeouInSec);
        List<WebElement> element = doJActionResult("Get web elements " + this.toString(), avatar::getElements,
                els -> format("Got %s element(s)", els.size()), new LogSettings(DEBUG, BUSINESS));
        setWaitTimeout(TIMEOUT);
        return element;
    }

    public boolean isDisplayed() {
        return waitDisplayed(0);
    }

    public boolean waitDisplayed() {
        return waitDisplayed(TIMEOUT);
    }

    public boolean waitDisplayed(int seconds) {
        setWaitTimeout(seconds);
        boolean result = new Timer(seconds * 1000).wait(() -> where(getWebElements(), WebElement::isDisplayed).size() > 0);
        setWaitTimeout(TIMEOUT);
        return result;
    }

    public boolean waitVanished() {
        return waitDisplayed(TIMEOUT);
    }

    public boolean waitVanished(int seconds) {
        setWaitTimeout(100);
        boolean result = new Timer(seconds * 1000).wait(() -> where(getWebElements(), WebElement::isDisplayed).size() == 0);
        setWaitTimeout(TIMEOUT);
        return result;
    }

    public WebElement getElement(String name) {
        return first(getWebElements(), el -> el.getText().equals(name));
    }

    public WebElement getElement(int index) {
        return getWebElements().get(index);
    }

    public WebElement getElement(TEnum enumName) {
        return getElement(getEnumValue(enumName));
    }

    protected MapArray<String, WebElement> getElementsAction() {
        try {
            return new MapArray<>(getWebElements(), WebElement::getText, value -> value);
        } catch (Exception | AssertionError ex) {
            throw exception(ex.getMessage());
        }
    }

    protected List<String> getLabelsAction() {
        return (List<String>) getElementsAction().keys();
    }

    public final MapArray<String, WebElement> getElements() {
        return doJActionResult("Get elements", this::getElementsAction);
    }

    public final List<String> getLabels() {
        return doJActionResult("Get names", this::getLabelsAction);
    }

    protected String getTextAction(WebElement element) {
        return element.getText();
    }

    public final String getText(String name) {
        return doJActionResult(format("Get text for element '%s' with name '%s'", this.toString(), name),
                () -> getTextAction(getElement(name)));
    }

    public final String getText(int index) {
        return doJActionResult(format("Get text for element '%s' with index '%s'", this.toString(), index),
                () -> getTextAction(getElement(index)));
    }

    public final String getText(TEnum enumName) {
        return getText(getEnumValue(enumName));
    }

    public final int count() {
        return getElements().size();
    }

    protected String getValueAction() {
        return print(select(getWebElements(), WebElement::getText));
    }

    public final String getValue() {
        return doJActionResult("Get value", this::getValueAction);
    }

    public final List<String> waitText(String str) {
        if (waitCondition(() -> select(getWebElements(), WebElement::getText).contains(str)))
            return getLabels();
        else {
            throw exception("Wait Text Failed");
        }
    }

    public List<String> getTextList() {
        return doJActionResult("Get list of texts", () -> (List<String>) select(getWebElements(), WebElement::getText),
                PrintUtils::print);
    }

    public String getFirstText() {
        List<String> results = getTextList();
        return (results != null && results.size() > 0)
                ? results.get(0)
                : null;
    }

    public String getLastText() {
        List<String> results = getTextList();
        return (results != null && results.size() > 0)
                ? results.get(results.size() - 1)
                : null;
    }
}
