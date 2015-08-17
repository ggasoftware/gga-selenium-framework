package com.ggasoftware.jdi_ui_tests.core.elements.complex.table;

import com.ggasoftware.jdi_ui_tests.core.elements.base.ASelectElement;
import com.ggasoftware.jdi_ui_tests.core.elements.common.AText;
import com.ggasoftware.jdi_ui_tests.core.elements.interfaces.complex.ITable;
import com.ggasoftware.jdi_ui_tests.utils.linqInterfaces.JFuncT;
import com.ggasoftware.jdi_ui_tests.utils.map.MapArray;
import com.ggasoftware.jdi_ui_tests.utils.pairs.Pair;
import com.ggasoftware.jdi_ui_tests.utils.common.*;

import java.util.*;

import static com.ggasoftware.jdi_ui_tests.core.settings.JDISettings.*;
import static com.ggasoftware.jdi_ui_tests.utils.common.LinqUtils.*;
import static com.ggasoftware.jdi_ui_tests.utils.common.PrintUtils.print;
import static com.ggasoftware.jdi_ui_tests.utils.common.Timer.waitCondition;
import static java.lang.String.format;
import static java.util.Arrays.asList;

/**
 * Created by Roman_Iovlev on 6/2/2015.
 */
public abstract class Table<T extends ASelectElement> extends AText implements ITable<T> {
    protected List<ICell<T>> _allCells = new ArrayList<>();
    public List<ICell<T>> getCells() {
        for(String columnName : columns().headers())
            for(String rowName : rows().headers())
                _allCells.add(cell(new Column(columnName), new Row(rowName)));
        return _allCells;
    }

    protected Columns<T> _columns;
    public ITableLine columns() { return _columns; }
    protected Columns<T> getColumns() { return _columns; }
    public MapArray<String, ICell<T>> column(int colNum) { return getRows().getColumn(colNum); }
    public MapArray<String, ICell<T>> column(String colName) { return getRows().getColumn(colName); }

    protected MapArray<String, ICell<T>> column(Column column) { return column.get(this::column, this::column); }

    public void setColumns(Columns<T> value) { _columns.update(value); }

    protected Rows<T> _rows;
    public ITableLine rows() { return _rows; }
    public Rows<T> getRows() { return _rows; }
    public MapArray<String, ICell<T>> row(int rowNum) { return getColumns().getRow(rowNum); }
    public MapArray<String, ICell<T>> row(String rowName) { return getColumns().getRow(rowName); }

    private MapArray<String, ICell<T>> row(Row row) { return row.get(this::row, this::row); }
    public void setRows(Rows<T> value) { _rows.update(value); }

    public void setColumnHeaders(String[] value) { getColumns().setHeaders(value); }
    public void setRowHeaders(String[] value) { getRows().setHeaders(value); }
    public void setColCount(int value) { getColumns().setCount(value); }
    public void setRowCount(int value) { getRows().setCount(value); }

    protected abstract String[] getFooterAction();

    protected String[] _footer;
    public void setFooter(String[] value) { _footer = value; }
    public String[] header() { return columns().headers(); }
    public String[] footer() {
        if (_footer != null)
            return _footer;
        _footer = doJActionResult("Get Footer", this::getFooterAction);
        if (_footer == null || _footer.length == 0)
            return null;
        getColumns().setCount(_footer.length);
        return _footer;
    }

    public ICell<T> cell(Column column, Row row) {
        int colIndex = column.get(this::getColumnIndex, num -> num + getColumns().startIndex - 1);
        int rowIndex = row.get(this::getRowIndex, num -> num + getRows().startIndex - 1);
        return addCell(colIndex, rowIndex,
                column.get(name -> asList(columns().headers()).indexOf(name) + 1, num -> num),
                row.get(name -> asList(rows().headers()).indexOf(name) + 1, num -> num),
                column.get(name -> name, num -> ""),
                row.get(name -> name, num -> ""));
    }

    private List<ICell<T>> matches(Collection<ICell<T>> list, String regex) {
        return new ArrayList<>(where(list, cell -> cell.getValue().matches(regex)));
    }

    public List<ICell<T>> cells(String value) {
        return new ArrayList<>(where(getCells(), cell -> cell.getValue().equals(value)));
    }

    public List<ICell<T>> cellsMatch(String regex) {
        return matches(getCells(), regex);
    }

    public ICell<T> cell(String value) {
        for (int colIndex = 1; colIndex <= columns().count(); colIndex++)
            for (int rowIndex = 1; rowIndex <= rows().count(); rowIndex++) {
                ICell<T> cell = cell(new Column(colIndex), new Row(rowIndex));
                if (cell.getValue().equals(value)) return cell;
            }
        return null;
    }

    public ICell<T> cellMatch(String regex) {
        for (int colIndex = 1; colIndex <= columns().count(); colIndex++)
            for (int rowIndex = 1; rowIndex <= rows().count(); rowIndex++) {
                ICell<T> cell = cell(new Column(colIndex), new Row(rowIndex));
                if (cell.getValue().matches(regex)) return cell;
            }
        return null;
    }

    public MapArray<String, MapArray<String, ICell<T>>> rows(String... colNameValues) {
        MapArray<String, MapArray<String, ICell<T>>> result = new MapArray<>();
        for (Pair<String, MapArray<String, ICell<T>>> row : getRows().get()) {
            boolean matches = true;
            for (String colNameValue : colNameValues) {
                if (!colNameValue.matches("[^=]+=[^=]*"))
                    throw asserter.exception("Wrong search criteria for Cells: " + colNameValue);
                String[] splitted = colNameValue.split("=");
                String colName = splitted[0];
                String colValue = splitted[1];
                if (!row.value.get(colName).getValue().equals(colValue)) {
                    matches = false;
                    break;
                }
            }
            if (matches) result.add(row);
        }
        return result;
    }

    public MapArray<String, MapArray<String, ICell<T>>> columns(String... rowNameValues) {
        MapArray<String, MapArray<String, ICell<T>>> result = new MapArray<>();
        for (Pair<String, MapArray<String, ICell<T>>> column : getColumns().get()) {
            boolean matches = true;
            for (String rowNameValue : rowNameValues) {
                if (!rowNameValue.matches("[^=]+=[^=]*"))
                    throw asserter.exception("Wrong search criteria for Cells: " + rowNameValue);
                String[] splitted = rowNameValue.split("=");
                String rowName = splitted[0];
                String rowValue = splitted[1];
                if (!column.value.get(rowName).getValue().equals(rowValue)) {
                    matches = false;
                    break;
                }
            }
            if (matches) result.add(column);
        }
        return result;
    }
    public boolean waitValue(String value, Row row) {
        return timer().wait(() -> column(value, row) != null);
    }

    public boolean waitValue(String value, Column column) {
        return timer().wait(() -> row(value, column) != null);
    }

    public boolean isEmpty() {
        setWaitTimeout(0);
        int rowsCount = rows().count();
        setWaitTimeout(timeouts.waitElementSec);
        return rowsCount == 0;
    }
    public boolean waitHaveRows() {
        return waitRows(1);
    }
    public boolean waitRows(int count) {
        return waitCondition(() -> rows().count() >= count);
    }

    public ICell<T> cell(String value, Row row) {
        int rowIndex = (row.haveName())
            ? asList(rows().headers()).indexOf(row.getName()) + 1
            : row.getNum();

        for (int colIndex = 1; colIndex <= columns().count(); colIndex++) {
            ICell<T> cell = cell(new Column(colIndex), new Row(rowIndex));
            if (cell.getValue().equals(value)) return cell;
        }
        return null;
    }

    public ICell<T> cell(String value, Column column) {
        int colIndex = column.get(
                name -> asList(columns().headers()).indexOf(name) + 1,
                num -> num);
        for (int rowIndex = 1; rowIndex <= rows().count(); rowIndex++) {
            ICell<T> cell = cell(new Column(colIndex), new Row(rowIndex));
            if (cell.getValue().equals(value)) return cell;
        }
        return null;
    }

    public List<ICell<T>> cellsMatch(String regex, Column column) {
        MapArray<String, ICell<T>> columnLine = column(column);
        return matches(columnLine.values(), regex);
    }

    public List<ICell<T>> cellsMatch(String regex, Row row) {
        MapArray<String, ICell<T>> rowLine = row(row);
        return matches(rowLine.values(), regex);
    }

    public MapArray<String, ICell<T>> column(String value, Row row) {
        ICell<T> columnCell = cell(value, row);
        return columnCell != null ? getColumns().getRow(columnCell.columnNum()) : null;
    }
    public MapArray<String, ICell<T>> row(String value, Column column) {
        ICell<T> rowCell = cell(value, column);
        return rowCell != null ? getRows().getColumn(rowCell.rowNum()) : null;
    }

    private int getColumnIndex(String name) {
        String[] headers = columns().headers();
        if (headers == null || !asList(headers).contains(name))
            throw asserter.exception("Can't Get Column: '" + name + "'. " + ((headers == null)
                    ? "ColumnHeaders is Null"
                    : ("Available ColumnHeaders: " + print(headers, ", ", "'{0}'") + ")")));
        int nameIndex = asList(headers).indexOf(name);
        return nameIndex + getColumns().startIndex;
    }

    private int getRowIndex(String name) {
        String[] headers = rows().headers();
        if (headers == null || !asList(headers).contains(name))
            throw asserter.exception("Can't Get Row: '" + name + "'. " + ((headers == null)
                    ? "RowHeaders is Null"
                    : ("Available RowHeaders: " + print(headers, ", ", "'{0}'") + ")")));
        int nameIndex = asList(headers).indexOf(name);
        return nameIndex + getRows().startIndex;
    }


    @Override
    protected String getValueAction() {
            return "||X|" + print(columns().headers(), "|") + "||" + StringUtils.LineBreak +
                    print(new ArrayList<>(select(rows().headers(),
                            rowName -> "||" + rowName + "||" +
                                    print(new ArrayList<>(select(where(getCells(),
                                                    cell -> cell.rowName().equals(rowName)),
                                            ICell::getValue)), "|") + "||")), StringUtils.LineBreak);
    }

    private Cell<T> addCell(int colIndex, int rowIndex, int colNum, int rowNum, String colName, String rowName) {
        Cell<T> cell = (Cell<T>) first(_allCells, c -> c.columnNum() == colNum && c.rowNum() == rowNum);
        if (cell != null)
            return cell.updateData(colName, rowName);
        cell = createCell(colIndex, rowIndex, colNum, rowNum, colName, rowName);
        _allCells.add(cell);
        return cell;
    }

    private Cell<T> createCell(int colIndex, int rowIndex, int colNum, int rowNum, String colName, String rowName) {
        T cell = createCell(colIndex, rowIndex);
        return new Cell<>(cell, colNum, rowNum, colName, rowName);
    }

    private Class<ASelectElement> clazz;

    public JFuncT<T> cellCreateTemplate;
    protected abstract void fillCellLocationContext(ASelectElement cell, int colIndex, int rowIndex);

    private T createCell(int colIndex, int rowIndex) {
        T cell;
        try {
            if (cellCreateTemplate != null) {
                cell = cellCreateTemplate.invoke();
                fillCellLocationContext(cell, colIndex, rowIndex);
            } else
                cell = (T) createCellInstance(clazz, colIndex, rowIndex);
        } catch (Exception ex) { throw asserter.exception("Can't init Cell"); }
        if (cell == null)
            throw asserter.exception("Can't init Cell");
        return cell;
    }
    protected abstract String printNewLocator(int colIndex, int rowIndex);

    public  ASelectElement createCellInstance(Class<ASelectElement> childClass, int colIndex, int rowIndex) {
        ASelectElement element;
        try { element = childClass.newInstance(); }
        catch (Exception ignore) { throw asserter.exception(
                format("Can't create child for parent '%s' with type '%s' and new locator '%s'",
                        toString(), childClass.getName(), printNewLocator(colIndex, rowIndex))); }
        fillCellLocationContext(element, colIndex, rowIndex);
        return element;
    }
}