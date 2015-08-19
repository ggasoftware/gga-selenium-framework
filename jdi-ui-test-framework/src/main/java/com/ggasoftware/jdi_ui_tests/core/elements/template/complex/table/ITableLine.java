package com.ggasoftware.jdi_ui_tests.core.elements.template.complex.table;

import com.ggasoftware.jdi_ui_tests.core.elements.template.base.SelectElement;
import com.ggasoftware.jdi_ui_tests.core.elements.page_objects.annotations.JDIAction;
import com.ggasoftware.jdi_ui_tests.utils.map.MapArray;

/**
 * Created by Roman_Iovlev on 7/28/2015.
 */
public interface ITableLine<T extends SelectElement> {
    /** Get Columns/Rows count*/
    @JDIAction
    int count();
    /** Get Columns/Rows headers*/
    @JDIAction
    String[] headers();
    /** Get Columns/Rows in following format <br>
     *  For rows: rowName>columnName>cell <br>
     *  For columns: columnName>rowName>cell <br>
     *  e.g. myTable.columns().getElement().getElement("Name").getElement("5")
     *  or   myTable.columns().getElement().count()
     *  But better to use specified commands like
     *      cell("Name, "5")
     *      myTable.columns().count()
     *  */
    @JDIAction
    MapArray<String, MapArray<String, ICell<T>>> get();
}
