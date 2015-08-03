package com.epam.jdi_ui_tests.elements.complex.table;

import com.epam.jdi_ui_tests.elements.base.SelectElement;
import com.epam.jdi_ui_tests.elements.interfaces.base.ISelect;

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
