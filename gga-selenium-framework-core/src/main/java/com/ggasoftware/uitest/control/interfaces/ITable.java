package com.ggasoftware.uitest.control.interfaces;

import com.ggasoftware.uitest.control.complex.table.Cell;
import com.ggasoftware.uitest.control.complex.table.Columns;
import com.ggasoftware.uitest.control.complex.table.Rows;
import com.ggasoftware.uitest.control.interfaces.IClickable;
import com.ggasoftware.uitest.control.interfaces.IClickableText;
import com.ggasoftware.uitest.control.interfaces.IHaveValue;
import com.ggasoftware.uitest.utils.JDIAction;
import com.ggasoftware.uitest.utils.map.MapArray;

import java.util.List;

/**
 * Created by roman.i on 20.10.2014.
 */

public interface ITable<T extends IClickableText> extends IHaveValue {
    @JDIAction
    Cell<T> cell(int colNum, int rowNum);
    @JDIAction
    Cell<T> cell(String colName, int rowNum);
    @JDIAction
    Cell<T> cell(int colNum, String rowName);
    @JDIAction
    Cell<T> cell(String colName, String rowName);
    @JDIAction
    List<Cell<T>> findCellsValues(String value);
    @JDIAction
    List<Cell<T>> matchCellsValues(String regex);
    @JDIAction
    Cell<T> findFirstCellWithValue(String value);

    @JDIAction
    Cell<T> findCellInColumn(int colIndex, String value);
    @JDIAction
    MapArray<String, Cell<T>> matchCellsInColumn(int colIndex, String regex);
    @JDIAction
    Cell<T> findCellInColumn(String colName, String value);
    @JDIAction
    MapArray<String, Cell<T>> matchCellsInColumn(String colname, String regex);
    @JDIAction
    MapArray<String, MapArray<String, Cell<T>>> findRows(String... colNameValues);
    @JDIAction
    MapArray<String, MapArray<String, Cell<T>>> findColumns(String... rowNameValues);

    @JDIAction
    Cell<T> findCellInRow(int rowIndex, String value);
    @JDIAction
    MapArray<String, Cell<T>> matchCellsInRow(int rowIndex, String regex);
    @JDIAction
    Cell<T> findCellInRow(String rowName, String value);
    @JDIAction
    MapArray<String, Cell<T>> matchCellsInRow(String rowName, String pattern);

    @JDIAction
    MapArray<String, Cell<T>> findColumnByRowValue(int rowIndex, String value);
    @JDIAction
    MapArray<String, Cell<T>> findColumnByRowValue(String rowName, String value);
    @JDIAction
    MapArray<String, Cell<T>> findRowByColumnValue(int colIndex, String value);
    @JDIAction
    MapArray<String, Cell<T>> findRowByColumnValue(String colName, String value);

    @JDIAction
    Columns<T> columns();
    @JDIAction
    MapArray<String, Cell<T>> getColumn(int colNum);
    @JDIAction
    MapArray<String, Cell<T>> getColumn(String colName);
    @JDIAction
    Rows<T> rows();
    @JDIAction
    MapArray<String, Cell<T>> getRow(int rowNum);
    @JDIAction
    MapArray<String, Cell<T>> getRow(String rowName);
    @JDIAction
    String[] getFooter();
}
