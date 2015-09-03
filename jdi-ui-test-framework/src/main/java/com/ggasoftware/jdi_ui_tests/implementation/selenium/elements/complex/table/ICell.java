package com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.complex.table;

import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.base.SelectElement;
import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.interfaces.base.ISelect;

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
