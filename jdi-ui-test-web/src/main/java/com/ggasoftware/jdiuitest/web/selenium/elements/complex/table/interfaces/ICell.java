package com.ggasoftware.jdiuitest.web.selenium.elements.complex.table.interfaces;

import com.ggasoftware.jdiuitest.web.selenium.elements.base.SelectElement;
import com.ggasoftware.jdiuitest.web.selenium.elements.BaseElement;
import com.ggasoftware.jdiuitest.core.interfaces.base.ISelect;

/**
 * Created by Roman_Iovlev on 7/28/2015.
 */
public interface ICell extends ISelect {
    SelectElement get();

    <T extends BaseElement> T get(Class<T> clazz) throws Exception;

    <T extends BaseElement> T get(T element);

    int columnNum();

    int rowNum();

    String columnName();

    String rowName();
}
