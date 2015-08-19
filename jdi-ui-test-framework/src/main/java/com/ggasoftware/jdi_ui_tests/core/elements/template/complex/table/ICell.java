package com.ggasoftware.jdi_ui_tests.core.elements.template.complex.table;

import com.ggasoftware.jdi_ui_tests.core.elements.template.base.SelectElement;
import com.ggasoftware.jdi_ui_tests.core.elements.interfaces.base.ISelect;

/**
 * Created by Roman_Iovlev on 7/28/2015.
 */
public interface ICell<T extends SelectElement> extends ISelect {
    T get();
    public int columnNum() ;
    public int rowNum();
    public String columnName();
    public String rowName();
}
