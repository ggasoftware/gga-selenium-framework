package com.ggasoftware.jdiuitest.core.interfaces.complex;

import com.ggasoftware.jdiuitest.core.annotations.JDIAction;
import com.ggasoftware.jdiuitest.core.interfaces.base.IComposite;
import com.ggasoftware.jdiuitest.core.interfaces.common.IText;

/**
 * Created by Roman_Iovlev on 7/8/2015.
 */
public interface IPopup extends IText, IComposite {
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
