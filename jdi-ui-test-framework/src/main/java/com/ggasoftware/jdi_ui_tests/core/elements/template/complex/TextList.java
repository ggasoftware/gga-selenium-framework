package com.ggasoftware.jdi_ui_tests.core.elements.template.complex;

import com.ggasoftware.jdi_ui_tests.core.elements.template.base.BaseElement;
import com.ggasoftware.jdi_ui_tests.core.elements.interfaces.complex.ITextList;
import com.ggasoftware.jdi_ui_tests.utils.common.PrintUtils;
import com.ggasoftware.jdi_ui_tests.utils.common.Timer;

import java.util.List;

import static com.ggasoftware.jdi_ui_tests.core.settings.JDISettings.asserter;
import static com.ggasoftware.jdi_ui_tests.core.settings.JDISettings.timeouts;
import static com.ggasoftware.jdi_ui_tests.utils.common.EnumUtils.getEnumValue;
import static com.ggasoftware.jdi_ui_tests.utils.common.PrintUtils.print;

/**
 * Created by Roman_Iovlev on 7/3/2015.
 */
public class TextList<TEnum extends Enum> extends BaseElement implements ITextList<TEnum> {
    // Actions
    protected int displayedElementsCount();
    protected boolean noElementsDisplayedCondition();
    protected List<String> getLabelsAction();
    protected String getTextAction(String name);
    protected String getTextAction(int index);
    protected List<String> getElementsTextAction();
    protected String getValueAction() { return print(getElementsTextAction()); }

    // Methods
    public boolean waitVisible() { return timer().wait(() -> displayedElementsCount() > 0); }
    public boolean waitInvisible()  {
        setWaitTimeout(timeouts.retryMSec);
        boolean result = timer().wait(this::noElementsDisplayedCondition);
        setWaitTimeout(timeouts.waitElementSec);
        return result;
    }
    public final List<String> getLabels() { return doJActionResult("Get labels", this::getLabelsAction); }

    public final String getText(String name) {
        return doJActionResult(String.format("Get text for element '%s' with name '%s'", this.toString(), name),
            () -> getTextAction(name));
    }

    public final String getText(int index) {
        return doJActionResult(String.format("Get text for element '%s' with index '%s'", this.toString(), index),
                () -> getTextAction(index));
    }
    public final String getText(TEnum enumName) {
        return getText(getEnumValue(enumName));
    }
    public final int count() { return doJActionResult("Get text lines count", () -> getLabelsAction().size()); }
    public final List<String> waitText(String str) {
        if (Timer.waitCondition(() -> getElementsTextAction().contains(str)))
            return getLabels();
        else
            throw asserter.exception("Wait Text Failed");
    }
    public String getLastText() {
        List<String> results = doJActionResult("Get list of texts", this::getElementsTextAction,
                PrintUtils::print);
        return (results != null && results.size() > 0)
            ? results.get(results.size() - 1)
            : null;
    }
    public final String getValue() { return doJActionResult("Get value", this::getValueAction); }
}
