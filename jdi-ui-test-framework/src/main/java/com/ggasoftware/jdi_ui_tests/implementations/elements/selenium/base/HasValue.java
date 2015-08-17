package com.ggasoftware.jdi_ui_tests.implementations.elements.selenium.base;

import com.ggasoftware.jdi_ui_tests.core.elements.base.ABase;
import com.ggasoftware.jdi_ui_tests.core.elements.interfaces.base.IHasValue;
import com.ggasoftware.jdi_ui_tests.utils.linqInterfaces.JFuncT;

import static com.ggasoftware.jdi_ui_tests.core.settings.JDISettings.asserter;
import static java.lang.String.format;

/**
 * Created by Roman_Iovlev on 7/6/2015.
 */
public class HasValue extends WebBaseElement implements IHasValue {
    private JFuncT<String> getValueFunc;
    public HasValue(ABase element) {
        this.getValueFunc = () -> {
            String noElementMessage = format("Get value not implemented for '%s'", element);
            throw asserter.exception(noElementMessage);
        };
    }
    public HasValue(JFuncT<String> getValueFunc) {
        this.getValueFunc = getValueFunc;
    }
    public final String getValue() { return doJActionResult("Get value", getValueFunc::invoke); }

}
