package com.epam.ui_test_framework.elements.complex.table;

import com.epam.ui_test_framework.elements.apiInteract.ContextType;
import com.epam.ui_test_framework.elements.base.HaveValue;
import com.epam.ui_test_framework.elements.base.SelectElement;
import com.epam.ui_test_framework.elements.interfaces.base.*;
import com.epam.ui_test_framework.elements.interfaces.complex.ITable;
import com.epam.ui_test_framework.elements.common.*;
import com.epam.ui_test_framework.utils.linqInterfaces.JFuncT;
import com.epam.ui_test_framework.utils.map.MapArray;
import com.epam.ui_test_framework.utils.pairs.Pair;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.*;

import static com.epam.ui_test_framework.elements.apiInteract.ContextType.Locator;
import static com.epam.ui_test_framework.utils.common.LinqUtils.*;
import static com.epam.ui_test_framework.utils.common.PrintUtils.print;
import static com.epam.ui_test_framework.utils.common.StringUtils.LineBreak;
import static com.epam.ui_test_framework.utils.common.Timer.waitCondition;
import static com.epam.ui_test_framework.utils.common.WebDriverByUtils.fillByTemplateSilent;
import static com.epam.ui_test_framework.settings.FrameworkSettings.*;
import static java.lang.String.format;
import static java.util.Arrays.asList;
import static java.util.concurrent.TimeUnit.*;

/**
 * Created by Roman_Iovlev on 6/2/2015.
 */
public class Table<T extends SelectElement> extends Text implements ITable<T> {
    public Table() {
        this(null);
        //GetFooterFunc = t => t.FindElements(By.xpath("//tfoot/tr/td")).Select(el => el.Text).ToArray();
    }
    public Table(By tableLocator) {
        super(tableLocator);
        _columns.table = this;
        _rows.table = this;
        clazz = SelectElement.class;
    }
    public Table(By tableLocator, By cellLocatorTemplate) {
        this(tableLocator);
        _cellLocatorTemplate = cellLocatorTemplate;
    }

    // ------------------------------------------ //

    private List<ICell<T>> _allCells = new ArrayList<>();
    public List<ICell<T>> getCells() {
        for(String columnName : columns().headers())
            for(String rowName : rows().headers())
                _allCells.add(cell(new Column(columnName), new Row(rowName)));
        return _allCells;
    }

    private Columns<T> _columns = new Columns<>();
    public ITableLine columns() { return _columns; }
    private Columns<T> getColumns() { return _columns; }
    public MapArray<String, ICell<T>> column(int colNum) { return getRows().getColumn(colNum); }
    public MapArray<String, ICell<T>> column(String colName) { return getRows().getColumn(colName); }

    private MapArray<String, ICell<T>> column(Column column) { return column.get(this::column, this::column); }

    public void setColumns(Columns<T> value) { _columns.update(value); }

    private Rows<T> _rows = new Rows<>();
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

    protected String[] getFooterAction() {
        return select(getWebElement().findElements(By.xpath("//tfoot/tr/td[1]")), WebElement::getText)
                .toArray(new String[1]);
    }
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
        return (ICell<T>) addCell(colIndex, rowIndex,
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
                    asserter.exception("Wrong searchCriteria for Cells: " + colNameValue);
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
                    asserter.exception("Wrong searchCritaria for Cells: " + rowNameValue);
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
        return waitCondition(() -> row(value, column) != null);
    }

    public boolean isEmpty() {
        getDriver().manage().timeouts().implicitlyWait(0, MILLISECONDS);
        int rowsCount = rows().count();
        getDriver().manage().timeouts().implicitlyWait(timeouts.waitElementSec, SECONDS);
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
        int nameIndex = -1;
        String[] headers = columns().headers();
        if (headers != null && asList(headers).contains(name))
            nameIndex = asList(headers).indexOf(name);
        else asserter.exception("Can't Get Column: '" + name + "'. " + ((headers == null)
                ? "ColumnHeaders is Null"
                : ("Available ColumnHeaders: " + print(headers, ", ", "'{0}'") + ")")));
        return nameIndex + getColumns().startIndex;
    }

    private int getRowIndex(String name) {
        int nameIndex = -1;
        String[] headers = rows().headers();
        if (headers != null && asList(headers).contains(name))
            nameIndex = asList(headers).indexOf(name);
        else asserter.exception("Can't Get Row: '" + name + "'. " + ((headers == null)
                ? "RowHeaders is Null"
                : ("Available RowHeaders: " + print(headers, ", ", "'{0}'") + ")")));
        return nameIndex + getRows().startIndex;
    }
    @Override
    protected IHaveValue haveValue() { return new HaveValue(() ->
        "||X|" + print(columns().headers(), "|") + "||" + LineBreak +
                print(new ArrayList<>(select(rows().headers(),
                        rowName -> "||" + rowName + "||" +
                                print(new ArrayList<>(select(where(getCells(),
                                                cell -> cell.rowName().equals(rowName)),
                                        ICell::getValue)), "|") + "||")), LineBreak)
    ); }

    private Cell<T> addCell(int colIndex, int rowIndex, int colNum, int rowNum, String colName, String rowName) {
        Cell<T> cell = (Cell<T>) first(_allCells, c -> c.columnNum() == colNum && c.rowNum() == rowNum);
        if (cell != null)
            return cell.updateData(colName, rowName);
        cell = createCell(colIndex, rowIndex, colNum, rowNum, colName, rowName);
        _allCells.add((ICell<T>)cell);
        return cell;
    }

    private Cell<T> createCell(int colIndex, int rowIndex, int colNum, int rowNum, String colName, String rowName) {
        T cell = createCell(colIndex, rowIndex);
        return new Cell<>(cell, colNum, rowNum, colName, rowName);
    }

    private Class<SelectElement> clazz;

    private By _cellLocatorTemplate = By.xpath(".//tr[%s]/td[%s]");

    public JFuncT<T> cellCreateTemplate;

    private T createCell(int colIndex, int rowIndex) {
        T cell;
        try {
            if (cellCreateTemplate != null) {
                cell = cellCreateTemplate.invoke();
                cell.getAvatar().byLocator = fillByTemplateSilent(cell.getLocator(), rowIndex, colIndex);
                cell.getAvatar().context.add(Locator, getLocator());
            } else
                cell = (T) createCellInstance(clazz, fillByTemplateSilent(_cellLocatorTemplate, rowIndex, colIndex));
        } catch (Exception ex) { asserter.exception("Can't init Cell"); return null; }
        if (cell == null)
            asserter.exception("Can't init Cell");
        return cell;
    }

    public <TChild extends SelectElement> TChild createCellInstance(Class<TChild> childClass, By newLocator) {
        TChild element;
        try { element = childClass.newInstance(); }
        catch (Exception ignore) { asserter.exception(
                format("Can't create child for parent '%s' with type '%s' and new locator '%s'",
                        toString(), childClass.getName(), newLocator)); return null; }
        element.getAvatar().byLocator = newLocator;
        element.getAvatar().context.add(ContextType.Locator, getLocator());
        return element;
    }
}