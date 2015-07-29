package com.epam.ui_test_framework.elements.complex.table;

import com.epam.ui_test_framework.elements.base.SelectElement;
import com.epam.ui_test_framework.elements.interfaces.base.ISelect;

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
