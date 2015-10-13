package com.ggasoftware.jdiuitests.implementation.selenium.elements.complex.table.interfaces;

import com.ggasoftware.jdiuitests.implementation.selenium.elements.BaseElement;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.base.SelectElement;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.interfaces.base.ISelect;

/**
 * Created by Roman_Iovlev on 7/28/2015.
 */
public interface ICell extends ISelect {
    SelectElement get();
    <T extends BaseElement> T get(Class<?> clazz) throws Exception;
    <T extends BaseElement> T get(T element);
    int columnNum() ;
    int rowNum();
    String columnName();
    String rowName();
}
