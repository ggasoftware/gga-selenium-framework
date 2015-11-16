package com.ggasoftware.jdiuitest.core.interfaces.complex;

import com.ggasoftware.jdiuitest.core.interfaces.base.IComposite;
import com.ggasoftware.jdiuitest.core.interfaces.common.IText;
import com.ggasoftware.jdiuitest.core.annotations.JDIAction;

/**
 * Created by Roman_Iovlev on 7/8/2015.
 */
public interface IPopup extends IText, IComposite {
    /**
     * @return Click on Button marked with annotation @OkButton or named "okButton"
     */
    @JDIAction
    void ok();

    /**
     * @return Click on Button marked with annotation @CancelButton or named "cancelButton"
     */
    @JDIAction
    void cancel();

    /**
     * @return Click on Button marked with annotation @CloseButton or named "closeButton"
     */
    @JDIAction
    void close();
}
