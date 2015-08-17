package com.ggasoftware.jdi_ui_tests.core.elements.interfaces.complex;

import com.ggasoftware.jdi_ui_tests.core.elements.interfaces.base.IBaseElement;
import com.ggasoftware.jdi_ui_tests.core.elements.interfaces.base.IHasValue;
import com.ggasoftware.jdi_ui_tests.core.elements.interfaces.base.IVisible;
import com.ggasoftware.jdi_ui_tests.core.elements.page_objects.annotations.JDIAction;

import java.util.List;

/**
 * Created by Roman_Iovlev on 6/10/2015.
 */
public interface ITextList<TEnum extends Enum> extends IBaseElement, IHasValue, IVisible {
    @JDIAction
    String getText(String name);
    @JDIAction
    String getText(int index);
    @JDIAction
    String getText(TEnum enumName);
    @JDIAction
    int count();
    @JDIAction
    List<String> getLabels();
    @JDIAction
    List<String> waitText(String str);
    @JDIAction
    String getLastText();
}
