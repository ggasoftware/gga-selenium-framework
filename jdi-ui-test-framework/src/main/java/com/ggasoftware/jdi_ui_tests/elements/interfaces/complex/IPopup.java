package com.ggasoftware.jdi_ui_tests.elements.interfaces.complex;

import com.ggasoftware.jdi_ui_tests.elements.interfaces.base.IComposite;
import com.ggasoftware.jdi_ui_tests.elements.interfaces.base.IHaveValue;
import com.ggasoftware.jdi_ui_tests.elements.page_objects.annotations.JDIAction;

/**
 * Created by Roman_Iovlev on 7/8/2015.
 */
public interface IPopup extends IHaveValue, IComposite {
    /** Click on Button marked with annotation @OkButton or named "okButton" */
    @JDIAction
    void ok();
    /** Click on Button marked with annotation @CancelButton or named "cancelButton" */
    @JDIAction
    void cancel();
    /** Click on Button marked with annotation @CloseButton or named "closeButton" */
    @JDIAction
    void close();
}
