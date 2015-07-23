package com.ggasoftware.uitest.control.interfaces.complex;

import com.ggasoftware.uitest.control.base.annotations.JDIAction;
import com.ggasoftware.uitest.control.base.map.MapArray;
import com.ggasoftware.uitest.control.interfaces.base.IClickableText;
import com.ggasoftware.uitest.control.interfaces.common.IText;
import com.ggasoftware.uitest.control.new_controls.complex.table.*;

import java.util.List;

/**
 * Created by roman.i on 20.10.2014.
 */

public interface ITable<T extends IClickableText, P> extends IText<P> {
    @JDIAction
    Cell<T,P> cell(Column column, Row row);
    @JDIAction
    List<Cell<T,P>> cells(String value);
    @JDIAction
    List<Cell<T,P>> cellsMatch(String regex);
    @JDIAction
    Cell<T,P> cell(String value);
    @JDIAction
    Cell<T,P> cellMatch(String regex);

    @JDIAction
    MapArray<String, MapArray<String, Cell<T,P>>> rows(String... colNameValues);
    @JDIAction
    MapArray<String, MapArray<String, Cell<T,P>>> columns(String... rowNameValues);

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
    Cell<T,P> cell(String value, Row row);
    @JDIAction
    Cell<T,P> cell(String value, Column column);
    @JDIAction
    List<Cell<T,P>> cellsMatch(String regex, Column column);
    @JDIAction
    List<Cell<T,P>> cellsMatch(String regex, Row row);

    @JDIAction
    MapArray<String, Cell<T,P>> column(String value, Row row);
    @JDIAction
    MapArray<String, Cell<T,P>> row(String value, Column column);


    Columns<T,P> columns();
    @JDIAction
    MapArray<String, Cell<T,P>> column(int colNum);
    @JDIAction
    MapArray<String, Cell<T,P>> column(String colName);

    Rows<T,P> rows();
    @JDIAction
    MapArray<String, Cell<T,P>> row(int rowNum);
    @JDIAction
    MapArray<String, Cell<T,P>> row(String rowName);

    @JDIAction
    String[] footer();
}
