package com.ggasoftware.jdi_ui_tests.core.elements.interfaces.complex;

import com.ggasoftware.jdi_ui_tests.core.elements.template.composite.Page;
import com.ggasoftware.jdi_ui_tests.core.elements.interfaces.base.IComposite;
import com.ggasoftware.jdi_ui_tests.core.elements.page_objects.annotations.JDIAction;

/**
 * Created by Roman_Iovlev on 8/14/2015.
 */
public interface IPage extends IComposite {
    /** Check that current page title equals/matches/contains expected title */
    @JDIAction
    Page.StringCheckType title();
}
