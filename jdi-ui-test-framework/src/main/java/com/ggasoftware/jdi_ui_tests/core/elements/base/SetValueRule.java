package com.ggasoftware.jdi_ui_tests.core.elements.base;

import com.ggasoftware.jdi_ui_tests.utils.linqInterfaces.JActionT;
import com.ggasoftware.jdi_ui_tests.utils.linqInterfaces.JActionTT;

/**
 * Created by Roman_Iovlev on 8/17/2015.
 */
public class SetValueRule {
    public static void setValueRule(String text, JActionT<String> action)  {
        setValueRule.invoke(text, action);
    }
    public static JActionTT<String, JActionT<String>> setValueRule = (text, action) -> {
        if (text == null) return;
        action.invoke(text);
    };
    public static JActionTT<String, JActionT<String>> setValueEmptyAction = (text, action) -> {
        if (text == null || text.equals("")) return;
        action.invoke(text.equals("#CLEAR#") ? "" : text);
    };
}
