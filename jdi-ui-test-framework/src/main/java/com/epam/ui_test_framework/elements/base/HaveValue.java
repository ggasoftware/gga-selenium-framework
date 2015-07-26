package com.epam.ui_test_framework.elements.base;

import com.epam.ui_test_framework.elements.BaseElement;
import com.epam.ui_test_framework.elements.interfaces.base.IHaveValue;
import com.epam.ui_test_framework.utils.linqInterfaces.JFuncT;

import static com.epam.ui_test_framework.settings.FrameworkSettings.asserter;
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
