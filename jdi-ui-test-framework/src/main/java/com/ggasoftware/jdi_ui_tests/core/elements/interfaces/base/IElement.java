package com.ggasoftware.jdi_ui_tests.core.elements.interfaces.base;

import com.ggasoftware.jdi_ui_tests.core.elements.base.ABaseElement;
import com.ggasoftware.jdi_ui_tests.core.elements.page_objects.annotations.JDIAction;
import com.ggasoftware.jdi_ui_tests.core.settings.HighlightSettings;
import com.ggasoftware.jdi_ui_tests.utils.linqInterfaces.JFuncTT;

/**
 * Created by Roman_Iovlev on 6/10/2015.
 */
public interface IElement extends IBaseElement, IVisible {
    /** Waits while attribute gets expected value. Return false if this not happens */
    @JDIAction
    boolean waitAttribute(String name, String value);
    /** Sets attribute value for element */
    @JDIAction
    void setAttribute(String attributeName, String value);
    /** Highlights element on page */
    void highlight();
    void highlight(HighlightSettings highlightSettings);
    /** Waits while condition with Element happens and returns wait result */
    @JDIAction
    Boolean wait(JFuncTT<ABaseElement, Boolean> resultFunc);
    /** Waits while condition with Element happens and returns result using resultFunc */
    @JDIAction
    <T> T wait(JFuncTT<ABaseElement, T> resultFunc, JFuncTT<T, Boolean> condition);
    /** Waits while condition with Element happens during specified timeout and returns wait result */
    @JDIAction
    Boolean wait(JFuncTT<ABaseElement, Boolean> resultFunc, int timeoutSec);
    /** Waits while condition with Element happens during specified timeout and returns result using resultFunc */
    @JDIAction
    <T> T wait(JFuncTT<ABaseElement, T> resultFunc, JFuncTT<T, Boolean> condition, int timeoutSec);
}
