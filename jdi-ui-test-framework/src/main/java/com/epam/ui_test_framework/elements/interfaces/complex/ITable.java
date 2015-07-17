package com.epam.ui_test_framework.elements.interfaces.complex;

import com.epam.ui_test_framework.elements.complex.table.*;
import com.epam.ui_test_framework.elements.interfaces.base.IClickableText;
import com.epam.ui_test_framework.elements.interfaces.base.IHaveValue;
import com.epam.ui_test_framework.elements.interfaces.common.IText;
import com.epam.ui_test_framework.utils.JDIAction;
import com.epam.ui_test_framework.utils.map.MapArray;

import java.util.List;

import static com.epam.ui_test_framework.utils.settings.FrameworkSettings.timeouts;

/**
 * Created by roman.i on 20.10.2014.
 */

public interface ITable<T extends IClickableText> extends IText {
    @JDIAction
    Cell<T> cell(Column column, Row row);
    @JDIAction
    List<Cell<T>> cells(String value);
    @JDIAction
    List<Cell<T>> cellsMatch(String regex);
    @JDIAction
    Cell<T> cell(String value);
    @JDIAction
    Cell<T> cellMatch(String regex);

    @JDIAction
    MapArray<String, MapArray<String, Cell<T>>> rows(String... colNameValues);
    @JDIAction
    MapArray<String, MapArray<String, Cell<T>>> columns(String... rowNameValues);

    @JDIAction
    boolean waitValue(String value, Row row);
    @JDIAction
    boolean waitValue(String value, Column column);

    @JDIAction
    boolean isEmpty();
    @JDIAction
    boolean waitHaveRows();
    @JDIAction
    boolean waitRows(int count);

    @JDIAction
    Cell<T> cell(String value, Row row);
    @JDIAction
    Cell<T> cell(String value, Column column);
    @JDIAction
    List<Cell<T>> cellsMatch(String regex, Column column);
    @JDIAction
    List<Cell<T>> cellsMatch(String regex, Row row);

    @JDIAction
    MapArray<String, Cell<T>> column(String value, Row row);
    @JDIAction
    MapArray<String, Cell<T>> row(String value, Column column);


    Columns<T> columns();
    @JDIAction
    MapArray<String, Cell<T>> column(int colNum);
    @JDIAction
    MapArray<String, Cell<T>> column(String colName);

    Rows<T> rows();
    @JDIAction
    MapArray<String, Cell<T>> row(int rowNum);
    @JDIAction
    MapArray<String, Cell<T>> row(String rowName);

    @JDIAction
    String[] footer();
}
