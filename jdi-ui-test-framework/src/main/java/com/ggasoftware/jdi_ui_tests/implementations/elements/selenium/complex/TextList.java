package com.ggasoftware.jdi_ui_tests.implementations.elements.selenium.complex;

import com.ggasoftware.jdi_ui_tests.core.elements.base.ABase;
import com.ggasoftware.jdi_ui_tests.core.elements.interfaces.complex.ITextList;
import com.ggasoftware.jdi_ui_tests.implementations.logger.base.LogSettings;
import com.ggasoftware.jdi_ui_tests.implementations.logger.enums.LogInfoTypes;
import com.ggasoftware.jdi_ui_tests.implementations.logger.enums.LogLevels;
import com.ggasoftware.jdi_ui_tests.core.settings.JDISettings;
import com.ggasoftware.jdi_ui_tests.utils.common.PrintUtils;
import com.ggasoftware.jdi_ui_tests.utils.map.MapArray;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

import static com.ggasoftware.jdi_ui_tests.core.settings.JDISettings.asserter;
import static com.ggasoftware.jdi_ui_tests.utils.common.EnumUtils.getEnumValue;
import static com.ggasoftware.jdi_ui_tests.utils.common.LinqUtils.*;
import static com.ggasoftware.jdi_ui_tests.utils.common.PrintUtils.print;
import static com.ggasoftware.jdi_ui_tests.utils.common.Timer.waitCondition;
import static java.lang.String.format;

/**
 * Created by Roman_Iovlev on 7/3/2015.
 */
public class TextList<TEnum extends Enum> extends ABase implements ITextList<TEnum> {
    public TextList() { }
    public TextList(By byLocator) { super(byLocator); }

    public List<WebElement> getWebElements() {
        return doJActionResult("Get web elements " + this.toString(), locationInfo::getElements,
                els -> format("Got %s element(s)", els.size()), new LogSettings(LogLevels.DEBUG, LogInfoTypes.BUSINESS));
    }

    public boolean waitVisible() {
        return timer().wait(() -> where(getWebElements(), WebElement::isDisplayed).size() > 0);
    }

    public boolean waitInvisible()  {
        setWaitTimeout(JDISettings.timeouts.retryMSec);
        boolean result = timer().wait(() -> {
                    List<WebElement> elements = getWebElements();
                    if (elements == null || elements.size() == 0)
                        return true;
                    for (WebElement el : getWebElements())
                        if (el.isDisplayed()) return false;
                    return true;
                });
        setWaitTimeout(JDISettings.timeouts.waitElementSec);
        return result;
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
        } catch (Exception ex) { throw asserter.exception(ex.getMessage()); }
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
    protected String getTextAction(WebElement element) { return element.getText(); }

    public final String getText(String name) {
        return doJActionResult(String.format("Get text for element '%s' with name '%s'", this.toString(), name),
            () -> getTextAction(getElement(name)));
    }

    public final String getText(int index) {
        return doJActionResult(String.format("Get text for element '%s' with index '%s'", this.toString(), index),
                () -> getTextAction(getElement(index)));
    }
    public final String getText(TEnum enumName) {
        return getText(getEnumValue(enumName));
    }
    public final int count() { return getElements().size(); }

    protected String getValueAction() { return print(select(getWebElements(), WebElement::getText)); }
    public final String getValue() { return doJActionResult("Get value", this::getValueAction); }

    public final List<String> waitText(String str) {
        if (!waitCondition(() -> select(getWebElements(), WebElement::getText).contains(str)))
            throw asserter.exception("Wait Text Failed");
        return getLabels();
    }

    public String getLastText() {
        List<String> results = doJActionResult("Get list of texts", () -> (List<String>) select(getWebElements(), WebElement::getText),
                PrintUtils::print);
        return (results != null && results.size() > 0)
            ? results.get(results.size() - 1)
            : null;
    }
}
