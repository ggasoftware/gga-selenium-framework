package com.ggasoftware.jdi_ui_tests.elements.base;

import com.ggasoftware.jdi_ui_tests.elements.BaseElement;
import com.ggasoftware.jdi_ui_tests.elements.interfaces.base.IHasValue;
import com.ggasoftware.jdi_ui_tests.utils.linqInterfaces.JFuncT;
import com.ggasoftware.jdi_ui_tests.settings.JDISettings;

import static java.lang.String.format;

/**
 * Created by Roman_Iovlev on 7/6/2015.
 */
public class HasValue extends Element implements IHasValue {
    private JFuncT<String> getValueFunc;
    public HasValue(BaseElement element) {
        this.getValueFunc = () -> {
            String noElementMessage = format("Get value not implemented for '%s'", element);
            JDISettings.asserter.exception(noElementMessage);
            return noElementMessage;
        };
    }
    public HasValue(JFuncT<String> getValueFunc) {
        this.getValueFunc = getValueFunc;
    }
    public final String getValue() { return doJActionResult("Get value", getValueFunc::invoke); }

}
