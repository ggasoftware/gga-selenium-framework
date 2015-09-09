package com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.complex.table;

import com.ggasoftware.jdi_ui_tests.core.utils.common.StringUtils;
import com.ggasoftware.jdi_ui_tests.core.utils.map.MapArray;
import com.ggasoftware.jdi_ui_tests.core.utils.pairs.Pair;
import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.common.Text;
import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.complex.table.interfaces.ICell;
import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.complex.table.interfaces.ITable;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.ggasoftware.jdi_ui_tests.core.settings.JDISettings.asserter;
import static com.ggasoftware.jdi_ui_tests.core.settings.JDISettings.timeouts;
import static com.ggasoftware.jdi_ui_tests.core.utils.common.LinqUtils.*;
import static com.ggasoftware.jdi_ui_tests.core.utils.common.PrintUtils.print;
import static com.ggasoftware.jdi_ui_tests.core.utils.common.Timer.waitCondition;
import static java.util.Arrays.asList;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * Created by Roman_Iovlev on 6/2/2015.
 */
public class Table extends Text implements ITable {
    public Table() {
        this(null);
        //GetFooterFunc = t => t.FindElements(By.xpath("//tfoot/tr/td")).Select(el => el.Text).ToArray();
    }
    public Table(By tableLocator) {
        super(tableLocator);
        _columns.table = this;
        _rows.table = this;
    }
    public Table(By tableLocator, By cellLocatorTemplate) {
        this(tableLocator);
        this.cellLocatorTemplate = cellLocatorTemplate;
    }
    public Table(By tableLocator, Class<?>... columnsTemplate) {
        this(tableLocator);
        this.columnsTemplate = columnsTemplate;
    }

    private By cellLocatorTemplate;
    private Class<?>[] columnsTemplate;

    // ------------------------------------------ //

    private List<ICell> _allCells = new ArrayList<>();
    public List<ICell> getCells() {
        MapArray<String, MapArray<String, ICell>> rows = rows().get();
        for(String columnName : columns().headers())
            for(String rowName : rows().headers())
                _allCells.add(rows.get(rowName).get(columnName));
        return _allCells;
    }

    public void clean() { _allCells = new ArrayList<>(); }
    public void clear() { clean(); }
    private Columns _columns = new Columns();
    public Columns columns() { return _columns; }
    public MapArray<String, ICell> column(int colNum) { return rows().getColumn(colNum); }
    public MapArray<String, ICell> column(String colName) { return rows().getColumn(colName); }

    private MapArray<String, ICell> column(Column column) { return column.get(this::column, this::column); }

    public void setColumns(Columns value) { _columns.update(value); }

    private Rows _rows = new Rows();
    public Rows rows() { return _rows; }
    public MapArray<String, ICell> row(int rowNum) { return columns().getRow(rowNum); }
    public MapArray<String, ICell> row(String rowName) { return columns().getRow(rowName); }

    private MapArray<String, ICell> row(Row row) { return row.get(this::row, this::row); }
    public void setRows(Rows value) { _rows.update(value); }

    public void setColumnHeaders(String[] value) { columns().setHeaders(value); }
    public void setRowHeaders(String[] value) {
        rows().setHeaders(value); }
    public void setColCount(int value) { columns().setCount(value); }
    public void setRowCount(int value) { rows().setCount(value); }

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
        _footer = invoker.doJActionResult("Get Footer", this::getFooterAction);
        if (_footer == null || _footer.length == 0)
            return null;
        columns().setCount(_footer.length);
        return _footer;
    }

    public ICell cell(Column column, Row row) {
        int colIndex = column.get(this::getColumnIndex, num -> num + columns().startIndex - 1);
        int rowIndex = row.get(this::getRowIndex, num -> num + rows().startIndex - 1);
        return addCell(colIndex, rowIndex,
                column.get(name -> asList(columns().headers()).indexOf(name) + 1, num -> num),
                row.get(name -> asList(rows().headers()).indexOf(name) + 1, num -> num),
                column.get(name -> name, num -> ""),
                row.get(name -> name, num -> ""));
    }
    public ICell cell(WebElement webElement, Column column, Row row) {
        return addCell(webElement,
                column.get(name -> asList(columns().headers()).indexOf(name) + 1, num -> num),
                row.get(name -> asList(rows().headers()).indexOf(name) + 1, num -> num),
                column.get(name -> name, num -> ""),
                row.get(name -> name, num -> ""));
    }

    private List<ICell> matches(Collection<ICell> list, String regex) {
        return new ArrayList<>(where(list, cell -> cell.getValue().matches(regex)));
    }

    public List<ICell> cells(String value) {
        return new ArrayList<>(where(getCells(), cell -> cell.getValue().equals(value)));
    }

    public List<ICell> cellsMatch(String regex) {
        return matches(getCells(), regex);
    }

    public ICell cell(String value) {
        ICell result;
        for (Pair<String, MapArray<String,ICell>> row : rows().get()) {
            result = row.value.first((cName, cValue) -> cValue.getText().equals(value));
            if (result != null)
                return result;
        }
        return null;
    }

    public ICell cellMatch(String regex) {
        ICell result;
        for (Pair<String, MapArray<String,ICell>> row : rows().get()) {
            result = row.value.first((cName, cValue) -> cValue.getText().matches(regex));
            if (result != null)
                return result;
        }
        return null;
    }

    public MapArray<String, MapArray<String, ICell>> rows(String... colNameValues) {
        MapArray<String, MapArray<String, ICell>> result = new MapArray<>();
        for (Pair<String, MapArray<String, ICell>> row : rows().get()) {
            boolean matches = true;
            for (String colNameValue : colNameValues) {
                if (!colNameValue.matches("[^=]+=[^=]*"))
                    throw asserter.exception("Wrong searchCriteria for Cells: " + colNameValue);
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

    public MapArray<String, MapArray<String, ICell>> columns(String... rowNameValues) {
        MapArray<String, MapArray<String, ICell>> result = new MapArray<>();
        for (Pair<String, MapArray<String, ICell>> column : columns().get()) {
            boolean matches = true;
            for (String rowNameValue : rowNameValues) {
                if (!rowNameValue.matches("[^=]+=[^=]*"))
                    throw asserter.exception("Wrong searchCritaria for Cells: " + rowNameValue);
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

    public ICell cell(String value, Row row) {
        int rowNum = (row.haveName())
            ? asList(rows().headers()).indexOf(row.getName()) + 1
            : row.getNum();
        return columns().getRow(rowNum).first((name, cell) -> cell.getValue().equals(value));
    }

    public ICell cell(String value, Column column) {
        int colIndex = column.get(
                name -> asList(columns().headers()).indexOf(name) + 1,
                num -> num);
        return rows().getColumn(colIndex).first((name, cell) -> cell.getValue().equals(value));
    }

    public List<ICell> cellsMatch(String regex, Column column) {
        MapArray<String, ICell> columnLine = column(column);
        return matches(columnLine.values(), regex);
    }

    public List<ICell> cellsMatch(String regex, Row row) {
        MapArray<String, ICell> rowLine = row(row);
        return matches(rowLine.values(), regex);
    }

    public MapArray<String, ICell> column(String value, Row row) {
        ICell columnCell = cell(value, row);
        return columnCell != null ? columns().getRow(columnCell.columnNum()) : null;
    }
    public MapArray<String, ICell> row(String value, Column column) {
        ICell rowCell = cell(value, column);
        return rowCell != null ? rows().getColumn(rowCell.rowNum()) : null;
    }

    private int getColumnIndex(String name) {
        int nameIndex;
        String[] headers = columns().headers();
        if (headers != null && asList(headers).contains(name))
            nameIndex = asList(headers).indexOf(name);
        else
            throw asserter.exception("Can't Get Column: '" + name + "'. " + ((headers == null)
                ? "ColumnHeaders is Null"
                : ("Available ColumnHeaders: " + print(headers, ", ", "'{0}'") + ")")));
        return nameIndex + columns().startIndex;
    }

    private int getRowIndex(String name) {
        int nameIndex;
        String[] headers = rows().headers();
        if (headers != null && asList(headers).contains(name))
            nameIndex = asList(headers).indexOf(name);
        else
            throw asserter.exception("Can't Get Row: '" + name + "'. " + ((headers == null)
                ? "RowHeaders is Null"
                : ("Available RowHeaders: " + print(headers, ", ", "'{0}'") + ")")));
        return nameIndex + rows().startIndex;
    }
    @Override
    protected String getValueAction() { return
        "||X|" + print(columns().headers(), "|") + "||" + StringUtils.LineBreak +
                print(new ArrayList<>(select(rows().headers(),
                        rowName -> "||" + rowName + "||" +
                                print(new ArrayList<>(select(where(getCells(),
                                                cell -> cell.rowName().equals(rowName)),
                                        ICell::getValue)), "|") + "||")), StringUtils.LineBreak);
    }

    private Cell addCell(int colIndex, int rowIndex, int colNum, int rowNum, String colName, String rowName) {
        Cell cell = (Cell) first(_allCells, c -> c.columnNum() == colNum && c.rowNum() == rowNum);
        if (cell != null)
            return cell.updateData(colName, rowName);
        cell = new Cell(colIndex, rowIndex, colNum, rowNum, colName, rowName, cellLocatorTemplate, columnsTemplate, this);
        _allCells.add(cell);
        return cell;
    }
    private Cell addCell(WebElement webElement, int colNum, int rowNum, String colName, String rowName) {
        Cell cell = (Cell) first(_allCells, c -> c.columnNum() == colNum && c.rowNum() == rowNum);
        if (cell != null) {
            cell.setWebElement(webElement);
            return cell.updateData(colName, rowName);
        }
        cell = new Cell(webElement, colNum, rowNum, colName, rowName, cellLocatorTemplate, columnsTemplate, this);
        _allCells.add(cell);
        return cell;
    }
}