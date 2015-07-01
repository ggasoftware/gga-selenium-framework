package com.ggasoftware.uitest.control.complex.table;

import com.ggasoftware.uitest.control.simple.Element;
import com.ggasoftware.uitest.utils.linqInterfaces.JFuncT;
import com.ggasoftware.uitest.utils.map.KeyValue;
import com.ggasoftware.uitest.utils.map.MapArray;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.ggasoftware.uitest.utils.WebDriverWrapper.TIMEOUT;
import static com.ggasoftware.uitest.utils.WebDriverWrapper.getDriver;
import static com.ggasoftware.uitest.utils.common.LinqUtils.*;
import static com.ggasoftware.uitest.utils.common.PrintUtils.print;
import static com.ggasoftware.uitest.utils.common.StringUtils.LineBreak;
import static com.ggasoftware.uitest.utils.common.Timer.getResultAction;
import static com.ggasoftware.uitest.utils.common.WebDriverByUtils.fillByTemplate;
import static com.ggasoftware.uitest.utils.settings.FrameworkSettings.asserter;
import static java.util.Arrays.asList;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * Created by Roman_Iovlev on 6/2/2015.
 */
public class Table<P, T extends ClickableText> extends Element<P> implements ITable<P, T> {
    // ------------------------------------------ //

    public Table() {
        columns().table = this;
        rows().table = this;
        clazz = ClickableText.class;
        //GetFooterFunc = t => t.FindElements(By.xpath("//tfoot/tr/td")).Select(el => el.Text).ToArray();
    }
    public Table(By tableLocator) {
        this();
        setLocator(tableLocator);
    }
    public Table(By tableLocator, By cellLocatorTemplate) {
        this(tableLocator);
        _cellLocatorTemplate = cellLocatorTemplate;
    }

    // ------------------------------------------ //

    private List<Cell<P, T>> _allCells = new ArrayList<>();
    public List<Cell<P, T>> getCells() {
        for(String columnName : columns().headers())
            for(String rowName : rows().headers())
                _allCells.add(cell(columnName, rowName));
        return _allCells;
    }

    private Columns<P, T> _columns = new Columns<P, T>();
    public Columns<P, T> columns() { return _columns; }
    public MapArray<String, Cell<P, T>> getColumn(int colNum) { return rows().getColumn(colNum); }
    public MapArray<String, Cell<P, T>> getColumn(String colName) { return rows().getColumn(colName); }
    public void setColumns(Columns<P, T> value) { _columns.update(value); }
    public String[] getHeaders(JFuncT<String[]> getHeadersAction) {
        return getResultAction(getHeadersAction::invoke); }

    private Rows<P, T> _rows = new Rows<>();
    public Rows<P, T> rows() { return _rows; }
    public MapArray<String, Cell<P, T>> getRow(int rowNum) { return columns().getRow(rowNum); }
    public MapArray<String, Cell<P, T>> getRow(String rowName) { return columns().getRow(rowName); }
    public void setRows(Rows<P, T> value) { _rows.update(value); }

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
    public String[] getFooter() {
        if (_footer != null)
            return _footer;
        _footer = doJActionResult("Get Footer", this::getFooterAction);
        if (_footer == null || _footer.length == 0)
            return null;
        columns().setCount(_footer.length);
        return _footer;
    }

    public boolean isEmpty() {
        return isEmpty(1000);
    }
    public boolean isEmpty(long timeout) {
        getDriver().manage().timeouts().implicitlyWait(timeout, MILLISECONDS);
        int rowsCount = rows().count();
        getDriver().manage().timeouts().implicitlyWait(TIMEOUT, SECONDS);
        return rowsCount == 0;
    }


    // ------------------------------------------ //

    public Cell<P, T> cell(int colNum, int rowNum) {
        int colIndex = colNum + columns().startIndex - 1;
        int rowIndex = rowNum + rows().startIndex - 1;
        return addCell(colIndex, rowIndex, colNum, rowNum, "", "");
    }

    public Cell<P, T> cell(String colName, int rowNum) {
        int colIndex = getColumnIndex(colName);
        int rowIndex = rowNum + rows().startIndex - 1;
        return addCell(colIndex, rowIndex, asList(columns().headers()).indexOf(colName) + 1, rowNum, colName, "");
    }

    public Cell<P, T> cell(int colNum, String rowName) {
        int colIndex = colNum + columns().startIndex - 1;
        int rowIndex = getRowIndex(rowName);
        return addCell(colIndex, rowIndex, colNum, asList(rows().headers()).indexOf(rowName) + 1, "", rowName);
    }

    public Cell<P, T> cell(String colName, String rowName)  {
        int colIndex = getColumnIndex(colName);
        int rowIndex = getRowIndex(rowName);
        return addCell(colIndex, rowIndex, asList(columns().headers()).indexOf(colName) + 1,
                asList(rows().headers()).indexOf(rowName) + 1, colName, rowName);
    }

    // ------------------------------------------ //

    private List<Cell<P, T>> matches(Collection<Cell<P, T>> list, String pattern) {
        return new ArrayList<>(where(list, cell -> cell.getValue().matches(pattern)));
    }

    public List<Cell<P, T>> findCellsValues(String value) {
        return new ArrayList<>(where(getCells(), cell -> cell.getValue().equals(value)));
    }

    public List<Cell<P, T>> matchCellsValues(String pattern) {
        return matches(getCells(), pattern);
    }

    public Cell<P, T> findFirstCellWithValue(String value) {
        for (int colIndex = 1; colIndex <= columns().count(); colIndex++)
            for (int rowIndex = 1; rowIndex <= rows().count(); rowIndex++) {
                Cell<P, T> cell = cell(colIndex, rowIndex);
                if (cell.getValue().equals(value)) return cell;
            }
        return null;
    }

    public Cell<P, T> findFirstCellMatchesValue(String pattern) {
        for (int colIndex = 1; colIndex <= columns().count(); colIndex++)
            for (int rowIndex = 1; rowIndex <= rows().count(); rowIndex++) {
                Cell<P, T> cell = cell(colIndex, rowIndex);
                if (cell.getValue().matches(pattern)) return cell;
            }
        return null;
    }

    /**
     * Finds Rows in table matches specified criterias
     * @param colNameValues - list of search criterias in format <columnName>=<columnValue>
     * (e.g. findRows("Name=Roman", "Profession=Tester") )
     * @return List of Rows (dictionary: rowName:row)
     * Each Row is dictionary: columnName:cell)
     */
    public MapArray<String, MapArray<String, Cell<P, T>>> findRows(String... colNameValues) {
        MapArray<String, MapArray<String, Cell<P, T>>> result = new MapArray<>();
        for (KeyValue<String, MapArray<String, Cell<P, T>>> row : rows().get()) {
            boolean matches = true;
            for (String colNameValue : colNameValues) {
                if (!colNameValue.matches("[^=]+=[^=]*"))
                    asserter.exception("Wrong searchCritaria for Cells: " + colNameValue);
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
     * Finds Columns in table matches specified criterias
     * @param rowNameValues - list of search criterias in format <rowName>=<rowValue>
     * (e.g. findColumns("Name=Roman", "Profession=Tester") )
     * @return List of Columns (dictionary: columnName:column)
     * Each Column is dictionary: rowName:cell)
     */
    public MapArray<String, MapArray<String, Cell<P, T>>> findColumns(String... rowNameValues) {
        MapArray<String, MapArray<String, Cell<P, T>>> result = new MapArray<>();
        for (KeyValue<String, MapArray<String, Cell<P, T>>> column : columns().get()) {
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

    public Cell<P, T> findCellInColumn(int colIndex, String value) {
        for (int rowIndex = 1; rowIndex <= rows().count(); rowIndex++) {
            Cell<P, T> cell = cell(colIndex, rowIndex);
            if (cell.getValue().equals(value)) return cell;
        }
        return null;
    }

    public Cell<P, T> findCellInColumn(String colName, String value) {
        int colIndex = asList(columns().headers()).indexOf(colName) + 1;
        for (int rowIndex = 1; rowIndex <= rows().count(); rowIndex++) {
            Cell<P, T> cell = cell(colIndex, rowIndex);
            if (cell.getValue().equals(value)) return cell;
        }
        return null;
    }

    public MapArray<String, Cell<P, T>> matchCellsInColumn(int colIndex, String pattern) {
        return _rows.cellsToColumn(matches(getColumn(colIndex).values(), pattern));
    }

    public MapArray<String, Cell<P, T>> matchCellsInColumn(String colName, String pattern) {
        return _rows.cellsToColumn(matches(getColumn(colName).values(), pattern));
    }

    //Row filters
    public MapArray<String, Cell<P, T>> matchCellsInRow(int rowIndex, String pattern) {
        return _columns.cellsToRow(matches(getRow(rowIndex).values(), pattern));
    }

    public MapArray<String, Cell<P, T>> matchCellsInRow(String rowName, String pattern) {
        return _columns.cellsToRow(matches(getRow(rowName).values(), pattern));
    }

    public Cell<P, T> findCellInRow(int rowIndex, String value) {
        for (int colIndex = 1; colIndex <= columns().count(); colIndex++) {
            Cell<P, T> cell = cell(colIndex, rowIndex);
            if (cell.getValue().equals(value)) return cell;
        }
        return null;
    }

    public Cell<P, T> findCellInRow(String rowName, String value) {
        int rowIndex = asList(rows().headers()).indexOf(rowName) + 1;
        for (int colIndex = 1; colIndex <= columns().count(); colIndex++) {
            Cell<P, T> cell = cell(colIndex, rowIndex);
            if (cell.getValue().equals(value)) return cell;
        }
        return null;
    }

    public MapArray<String, Cell<P, T>> findColumnByRowValue(int rowIndex, String value) {
        Cell<P, T> columnCell = findCellInRow(rowIndex, value);
        return columnCell != null ? columns().getRow(columnCell.columnNum) : null;
    }

    public MapArray<String, Cell<P, T>> findColumnByRowValue(String rowName, String value) {
        Cell<P, T> columnCell = findCellInRow(rowName, value);
        return columnCell != null ? columns().getRow(columnCell.columnNum) : null;
    }

    public MapArray<String, Cell<P, T>> findRowByColumnValue(int colIndex, String value) {
        Cell<P, T> rowCell = findCellInColumn(colIndex, value);
        return rowCell != null ? rows().getColumn(rowCell.rowNum) : null;
    }

    public MapArray<String, Cell<P, T>> findRowByColumnValue(String colName, String value) {
        Cell<P, T> rowCell = findCellInColumn(colName, value);
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

    public String getValue() {
        return "||X|" + print(columns().headers(), "|") + "||" + LineBreak +
                print(new ArrayList<>(select(rows().headers(),
                        rowName -> "||" + rowName + "||" +
                                print(new ArrayList<>(select(where(getCells(),
                                                cell -> cell.rowName.equals(rowName)),
                                        Cell::getValue)), "|") + "||")), LineBreak);
    }

    private Cell<P, T> addCell(int colIndex, int rowIndex, int colNum, int rowNum, String colName, String rowName) {
        Cell<P, T> cell = first(_allCells, c -> c.columnNum == colNum && c.rowNum == rowNum);
        if (cell != null)
            return cell.updateData(colName, rowName);
        cell = createCell(colIndex, rowIndex, colNum, rowNum, colName, rowName);
        _allCells.add(cell);
        return cell;
    }

    private Cell<P, T> createCell(int colIndex, int rowIndex, int colNum, int rowNum, String colName, String rowName) {
        T cell = createCell(colIndex, rowIndex);
        //PageObjectsInit.initSubElements(cell);
        return new Cell<P, T>(cell, colNum, rowNum, colName, rowName, getByLocator());
    }

    private Class<ClickableText> clazz;

    private By _cellLocatorTemplate;
    private By getCellLocator(T cell) {
        if (_cellLocatorTemplate == null)
            _cellLocatorTemplate = (cell.haveLocator())
                    ? cell.getByLocator()
                    : By.xpath(".//tr[%s]/td[%s]");
        return _cellLocatorTemplate;
    }

    private T createCell(int colIndex, int rowIndex) {
        T cell = null;
        try { cell = (T) clazz.newInstance(); }
        catch (Exception ignore) { }
        if (cell == null)
            asserter.exception("Can't init Cell");
        else {
            By locator = null;
            try { locator = fillByTemplate(getCellLocator(cell), rowIndex, colIndex); }
            catch (Exception ex) { asserter.exception(ex.getMessage()); }
            cell.setLocator(locator);
        }
        return cell;
    }
}