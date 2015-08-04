package com.epam.jdi_ui_tests.elements.base;

import com.epam.jdi_ui_tests.elements.BaseElement;
import com.epam.jdi_ui_tests.elements.interfaces.base.IHaveValue;
import com.epam.jdi_ui_tests.utils.linqInterfaces.JFuncT;

import static com.epam.jdi_ui_tests.settings.JDISettings.asserter;
import static java.lang.String.format;

/**
 * Created by Roman_Iovlev on 7/6/2015.
 */
public class HaveValue extends Element implements IHaveValue {
    private JFuncT<String> getValueFunc;
    public HaveValue(BaseElement element) {
        this.getValueFunc = () -> {
            String noElementMessage = format("Get value not implemented for '%s'", element);
            asserter.exception(noElementMessage);
            return noElementMessage;
        };
    }
    public HaveValue(JFuncT<String> getValueFunc) {
        this.getValueFunc = getValueFunc;
    }
    public final String getValue() { return doJActionResult("Get value", getValueFunc::invoke); }

}
