package com.epam.ui_test_framework.elements.complex.table;

import com.epam.ui_test_framework.elements.apiInteract.ContextType;
import com.epam.ui_test_framework.elements.base.HaveValue;
import com.epam.ui_test_framework.elements.interfaces.base.IBaseElement;
import com.epam.ui_test_framework.elements.interfaces.base.IClickable;
import com.epam.ui_test_framework.elements.interfaces.base.IHaveValue;
import com.epam.ui_test_framework.elements.interfaces.common.IText;
import com.epam.ui_test_framework.elements.interfaces.complex.ITable;
import com.epam.ui_test_framework.elements.common.Button;
import com.epam.ui_test_framework.elements.common.Text;
import com.epam.ui_test_framework.utils.linqInterfaces.JFuncT;
import com.epam.ui_test_framework.utils.map.MapArray;
import com.epam.ui_test_framework.utils.pairs.Pair;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.epam.ui_test_framework.elements.apiInteract.ContextType.Locator;
import static com.epam.ui_test_framework.utils.common.LinqUtils.*;
import static com.epam.ui_test_framework.utils.common.PrintUtils.print;
import static com.epam.ui_test_framework.utils.common.StringUtils.LineBreak;
import static com.epam.ui_test_framework.utils.common.Timer.waitCondition;
import static com.epam.ui_test_framework.utils.common.WebDriverByUtils.fillByTemplateSilent;
import static com.epam.ui_test_framework.settings.FrameworkSettings.asserter;
import static com.epam.ui_test_framework.settings.FrameworkSettings.timeouts;
import static java.lang.String.format;
import static java.util.Arrays.asList;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * Created by Roman_Iovlev on 6/2/2015.
 */
public class Table<T extends IClickable & IText> extends Text implements ITable<T> {
    public Table() {
        this(null);
        //GetFooterFunc = t => t.FindElements(By.xpath("//tfoot/tr/td")).Select(el => el.Text).ToArray();
    }
    public Table(By tableLocator) {
        super(tableLocator);
        columns().table = this;
        rows().table = this;
        clazz = Button.class;
    }
    public Table(By tableLocator, By cellLocatorTemplate) {
        this(tableLocator);
        _cellLocatorTemplate = cellLocatorTemplate;
    }

    // ------------------------------------------ //

    private List<Cell<T>> _allCells = new ArrayList<>();
    public List<Cell<T>> getCells() {
        for(String columnName : columns().headers())
            for(String rowName : rows().headers())
                _allCells.add(cell(new Column(columnName), new Row(rowName)));
        return _allCells;
    }

    private Columns<T> _columns = new Columns<>();
    public Columns<T> columns() { return _columns; }
    public MapArray<String, Cell<T>> column(int colNum) { return rows().getColumn(colNum); }
    public MapArray<String, Cell<T>> column(String colName) { return rows().getColumn(colName); }

    private MapArray<String, Cell<T>> column(Column column) { return column.get(this::column, this::column); }

    public void setColumns(Columns<T> value) { _columns.update(value); }

    private Rows<T> _rows = new Rows<>();
    public Rows<T> rows() { return _rows; }
    public MapArray<String, Cell<T>> row(int rowNum) { return columns().getRow(rowNum); }
    public MapArray<String, Cell<T>> row(String rowName) { return columns().getRow(rowName); }

    private MapArray<String, Cell<T>> row(Row row) { return row.get(this::row, this::row); }
    public void setRows(Rows<T> value) { _rows.update(value); }

    public void setColumnHeaders(String[] value) { columns().setHeaders(value); }
    public void setRowHeaders(String[] value) { rows().setHeaders(value); }
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
        _footer = doJActionResult("Get Footer", this::getFooterAction);
        if (_footer == null || _footer.length == 0)
            return null;
        columns().setCount(_footer.length);
        return _footer;
    }

    public Cell<T> cell(Column column, Row row) {
        int colIndex = column.get(this::getColumnIndex, num -> num + columns().startIndex - 1);
        int rowIndex = row.get(this::getRowIndex, num -> num + rows().startIndex - 1);
        return addCell(colIndex, rowIndex,
                column.get(name -> asList(columns().headers()).indexOf(name) + 1, num -> num),
                row.get(name -> asList(rows().headers()).indexOf(name) + 1, num -> num),
                column.get(name -> name, num -> ""),
                row.get(name -> name, num -> ""));
    }

    private List<Cell<T>> matches(Collection<Cell<T>> list, String regex) {
        return new ArrayList<>(where(list, cell -> cell.getValue().matches(regex)));
    }

    public List<Cell<T>> cells(String value) {
        return new ArrayList<>(where(getCells(), cell -> cell.getValue().equals(value)));
    }

    public List<Cell<T>> cellsMatch(String regex) {
        return matches(getCells(), regex);
    }

    public Cell<T> cell(String value) {
        for (int colIndex = 1; colIndex <= columns().count(); colIndex++)
            for (int rowIndex = 1; rowIndex <= rows().count(); rowIndex++) {
                Cell<T> cell = cell(new Column(colIndex), new Row(rowIndex));
                if (cell.getValue().equals(value)) return cell;
            }
        return null;
    }

    public Cell<T> cellMatch(String regex) {
        for (int colIndex = 1; colIndex <= columns().count(); colIndex++)
            for (int rowIndex = 1; rowIndex <= rows().count(); rowIndex++) {
                Cell<T> cell = cell(new Column(colIndex), new Row(rowIndex));
                if (cell.getValue().matches(regex)) return cell;
            }
        return null;
    }

    /**
     * Finds Rows in table matches specified criteria
     * @param colNameValues - list of search criteria in format <columnName>=<columnValue>
     * (e.g. rows("Name=Roman", "Profession=QA") )
     * @return List of Rows (dictionary: rowName:row)
     * Each Row is dictionary: columnName:cell)
     */
    public MapArray<String, MapArray<String, Cell<T>>> rows(String... colNameValues) {
        MapArray<String, MapArray<String, Cell<T>>> result = new MapArray<>();
        for (Pair<String, MapArray<String, Cell<T>>> row : rows().get()) {
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

    /**
     * Finds Columns in table matches specified criteria
     * @param rowNameValues - list of search criteria in format <rowName>=<rowValue>
     * (e.g. columns("Name=Roman", "Profession=QA") )
     * @return List of Columns (dictionary: columnName:column)
     * Each Column is dictionary: rowName:cell)
     */
    public MapArray<String, MapArray<String, Cell<T>>> columns(String... rowNameValues) {
        MapArray<String, MapArray<String, Cell<T>>> result = new MapArray<>();
        for (Pair<String, MapArray<String, Cell<T>>> column : columns().get()) {
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
        return waitCondition(() -> column(value, row) != null);
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
        return waitCondition(() -> rows().count() > count);
    }

    public Cell<T> cell(String value, Row row) {
        int rowIndex = (row.haveName())
            ? asList(rows().headers()).indexOf(row.getName()) + 1
            : row.getNum();

        for (int colIndex = 1; colIndex <= columns().count(); colIndex++) {
            Cell<T> cell = cell(new Column(colIndex), new Row(rowIndex));
            if (cell.getValue().equals(value)) return cell;
        }
        return null;
    }

    public Cell<T> cell(String value, Column column) {
        int colIndex = column.get(
                name -> asList(columns().headers()).indexOf(name) + 1,
                num -> num);
        for (int rowIndex = 1; rowIndex <= rows().count(); rowIndex++) {
            Cell<T> cell = cell(new Column(colIndex), new Row(rowIndex));
            if (cell.getValue().equals(value)) return cell;
        }
        return null;
    }

    public List<Cell<T>> cellsMatch(String regex, Column column) {
        MapArray<String, Cell<T>> columnLine = column(column);
        return matches(columnLine.values(), regex);
    }

    public List<Cell<T>> cellsMatch(String regex, Row row) {
        MapArray<String, Cell<T>> rowLine = row(row);
        return matches(rowLine.values(), regex);
    }

    public MapArray<String, Cell<T>> column(String value, Row row) {
        Cell<T> columnCell = cell(value, row);
        return columnCell != null ? columns().getRow(columnCell.columnNum) : null;
    }
    public MapArray<String, Cell<T>> row(String value, Column column) {
        Cell<T> rowCell = cell(value, column);
        return rowCell != null ? rows().getColumn(rowCell.rowNum) : null;
    }

    private int getColumnIndex(String name) {
        int nameIndex = -1;
        String[] headers = columns().headers();
        if (headers != null && asList(headers).contains(name))
            nameIndex = asList(headers).indexOf(name);
        else asserter.exception("Can't Get Column: '" + name + "'. " + ((headers == null)
                ? "ColumnHeaders is Null"
                : ("Available ColumnHeaders: " + print(headers, ", ", "'{0}'") + ")")));
        return nameIndex + columns().startIndex;
    }

    private int getRowIndex(String name) {
        int nameIndex = -1;
        String[] headers = rows().headers();
        if (headers != null && asList(headers).contains(name))
            nameIndex = asList(headers).indexOf(name);
        else asserter.exception("Can't Get Row: '" + name + "'. " + ((headers == null)
                ? "RowHeaders is Null"
                : ("Available RowHeaders: " + print(headers, ", ", "'{0}'") + ")")));
        return nameIndex + rows().startIndex;
    }
    @Override
    protected IHaveValue haveValue() { return new HaveValue(() ->
        "||X|" + print(columns().headers(), "|") + "||" + LineBreak +
                print(new ArrayList<>(select(rows().headers(),
                        rowName -> "||" + rowName + "||" +
                                print(new ArrayList<>(select(where(getCells(),
                                                cell -> cell.rowName.equals(rowName)),
                                        Cell::getValue)), "|") + "||")), LineBreak)
    ); }

    private Cell<T> addCell(int colIndex, int rowIndex, int colNum, int rowNum, String colName, String rowName) {
        Cell<T> cell = first(_allCells, c -> c.columnNum == colNum && c.rowNum == rowNum);
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

    private Class<Button> clazz;

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

    public <TChild extends IBaseElement> TChild createCellInstance(Class<TChild> childClass, By newLocator) {
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