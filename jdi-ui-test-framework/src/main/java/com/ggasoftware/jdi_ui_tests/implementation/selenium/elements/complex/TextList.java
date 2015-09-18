package com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.complex;

import com.ggasoftware.jdi_ui_tests.core.logger.base.LogSettings;
import com.ggasoftware.jdi_ui_tests.core.logger.enums.LogInfoTypes;
import com.ggasoftware.jdi_ui_tests.core.logger.enums.LogLevels;
import com.ggasoftware.jdi_ui_tests.core.utils.common.PrintUtils;
import com.ggasoftware.jdi_ui_tests.core.utils.common.Timer;
import com.ggasoftware.jdi_ui_tests.core.utils.map.MapArray;
import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.BaseElement;
import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.interfaces.complex.ITextList;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

import static com.ggasoftware.jdi_ui_tests.core.settings.JDISettings.asserter;
import static com.ggasoftware.jdi_ui_tests.core.utils.common.EnumUtils.getEnumValue;
import static com.ggasoftware.jdi_ui_tests.core.utils.common.LinqUtils.*;
import static com.ggasoftware.jdi_ui_tests.core.utils.common.PrintUtils.print;
import static java.lang.String.format;

/**
 * Created by Roman_Iovlev on 7/3/2015.
 */
public class TextList<TEnum extends Enum> extends BaseElement implements ITextList<TEnum> {
    public TextList() { }
    public TextList(By byLocator) { super(byLocator); }

    public List<WebElement> getWebElements() {
        return invoker.doJActionResult("Get web elements " + this.toString(), avatar::getElements,
                els -> format("Got %s Element(s)", els.size()), new LogSettings(LogLevels.DEBUG, LogInfoTypes.BUSINESS));
    }

    public boolean waitVanishedAction()  {
        return actions.findImmediately(() ->
            timer().wait(() -> {
                List<WebElement> elements = getWebElements();
                if (elements == null || elements.size() == 0)
                    return true;
                for (WebElement el : getWebElements())
                    if (el.isDisplayed()) return false;
                return true;
            }));
    }
    protected boolean isDisplayedAction() {
        return actions.findImmediately(() -> where(getWebElements(), WebElement::isDisplayed).size() > 0);
    }
    public boolean waitDisplayedAction() {
        return timer().wait(() -> any(getWebElements(), el -> !el.isDisplayed()));
    }

    public boolean isDisplayed() {return actions.isDisplayed(this::isDisplayedAction); }
    public boolean isHidden() {
        return actions.isDisplayed(() -> !isDisplayedAction());
    }
    public boolean waitDisplayed() {
        return actions.waitDisplayed(this::waitDisplayedAction);
    }
    public boolean waitVanished() {
        return actions.waitVanished(this::waitVanishedAction);
    }

    public WebElement getElement(String name)  {
        return first(getWebElements(), el -> el.getText().equals(name));
    }
    public WebElement getElement(int index) {
        return getWebElements().get(index);
    }
    public WebElement getElement(TEnum enumName) {
        return getElement(getEnumValue(enumName));
    }

    protected MapArray<String, WebElement> getElementsAction() {
        try { return new MapArray<>(getWebElements(), WebElement::getText, value -> value);
        } catch (Throwable ex) { throw asserter.exception(ex.getMessage()); }
    }
    protected List<String> getLabelsAction() {
        return (List<String>) getElementsAction().keys();
    }

    public final MapArray<String, WebElement> getElements() {
        return invoker.doJActionResult("Get elements", this::getElementsAction);
    }
    public final List<String> getLabels() {
        return invoker.doJActionResult("Get names", this::getLabelsAction);
    }
    protected String getTextAction(WebElement element) { return element.getText(); }

    public final String getText(String name) {
        return invoker.doJActionResult(String.format("Get text for Element '%s' with name '%s'", this.toString(), name),
                () -> getTextAction(getElement(name)));
    }

    public final String getText(int index) {
        return invoker.doJActionResult(String.format("Get text for Element '%s' with index '%s'", this.toString(), index),
                () -> getTextAction(getElement(index)));
    }
    public final String getText(TEnum enumName) {
        return getText(getEnumValue(enumName));
    }
    public final int count() { return getElements().size(); }

    protected String getValueAction() { return print(select(getWebElements(), WebElement::getText)); }
    public final String getValue() { return invoker.doJActionResult("Get value", this::getValueAction); }

    public final List<String> waitText(String str) {
        if (Timer.waitCondition(() -> select(getWebElements(), WebElement::getText).contains(str)))
            return getLabels();
        else { throw asserter.exception("Wait Text Failed"); }
    }

    public List<String> getTextList() {
        return invoker.doJActionResult("Get list of texts", () -> select(getWebElements(), WebElement::getText),
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
