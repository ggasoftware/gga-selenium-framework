package com.ggasoftware.uitest.control.complex.table;

import com.ggasoftware.uitest.control.apiInteract.ContextType;
import com.ggasoftware.uitest.control.apiInteract.GetElementModule;
import com.ggasoftware.uitest.control.interfaces.IClickableText;
import com.ggasoftware.uitest.control.interfaces.ITable;
import com.ggasoftware.uitest.control.simple.Button;
import com.ggasoftware.uitest.control.simple.Text;
import com.ggasoftware.uitest.utils.linqInterfaces.JFuncT;
import com.ggasoftware.uitest.utils.map.KeyValue;
import com.ggasoftware.uitest.utils.map.MapArray;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.ggasoftware.uitest.control.apiInteract.ContextType.Locator;
import static com.ggasoftware.uitest.utils.common.LinqUtils.*;
import static com.ggasoftware.uitest.utils.common.PrintUtils.print;
import static com.ggasoftware.uitest.utils.common.StringUtils.LineBreak;
import static com.ggasoftware.uitest.utils.common.Timer.getResultAction;
import static com.ggasoftware.uitest.utils.common.WebDriverByUtils.fillByTemplateSilent;
import static com.ggasoftware.uitest.utils.settings.FrameworkSettings.asserter;
import static com.ggasoftware.uitest.utils.settings.FrameworkSettings.timeouts;
import static java.util.Arrays.asList;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * Created by Roman_Iovlev on 6/2/2015.
 */
public class Table<T extends IClickableText> extends Text implements ITable<T> {
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
                _allCells.add(cell(columnName, rowName));
        return _allCells;
    }

    private Columns<T> _columns = new Columns<T>();
    public Columns<T> columns() { return _columns; }
    public MapArray<String, Cell<T>> getColumn(int colNum) { return rows().getColumn(colNum); }
    public MapArray<String, Cell<T>> getColumn(String colName) { return rows().getColumn(colName); }
    public void setColumns(Columns<T> value) { _columns.update(value); }
    public String[] getHeaders(JFuncT<String[]> getHeadersAction) {
        return getResultAction(getHeadersAction::invoke); }

    private Rows<T> _rows = new Rows<>();
    public Rows<T> rows() { return _rows; }
    public MapArray<String, Cell<T>> getRow(int rowNum) { return columns().getRow(rowNum); }
    public MapArray<String, Cell<T>> getRow(String rowName) { return columns().getRow(rowName); }
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
        getDriver().manage().timeouts().implicitlyWait(timeouts.waitElementSec, SECONDS);
        return rowsCount == 0;
    }


    // ------------------------------------------ //

    public Cell<T> cell(int colNum, int rowNum) {
        int colIndex = colNum + columns().startIndex - 1;
        int rowIndex = rowNum + rows().startIndex - 1;
        return addCell(colIndex, rowIndex, colNum, rowNum, "", "");
    }

    public Cell<T> cell(String colName, int rowNum) {
        int colIndex = getColumnIndex(colName);
        int rowIndex = rowNum + rows().startIndex - 1;
        return addCell(colIndex, rowIndex, asList(columns().headers()).indexOf(colName) + 1, rowNum, colName, "");
    }

    public Cell<T> cell(int colNum, String rowName) {
        int colIndex = colNum + columns().startIndex - 1;
        int rowIndex = getRowIndex(rowName);
        return addCell(colIndex, rowIndex, colNum, asList(rows().headers()).indexOf(rowName) + 1, "", rowName);
    }

    public Cell<T> cell(String colName, String rowName)  {
        int colIndex = getColumnIndex(colName);
        int rowIndex = getRowIndex(rowName);
        return addCell(colIndex, rowIndex, asList(columns().headers()).indexOf(colName) + 1,
                asList(rows().headers()).indexOf(rowName) + 1, colName, rowName);
    }

    // ------------------------------------------ //

    private List<Cell<T>> matches(Collection<Cell<T>> list, String pattern) {
        return new ArrayList<>(where(list, cell -> cell.getValue().matches(pattern)));
    }

    public List<Cell<T>> findCellsValues(String value) {
        return new ArrayList<>(where(getCells(), cell -> cell.getValue().equals(value)));
    }

    public List<Cell<T>> matchCellsValues(String pattern) {
        return matches(getCells(), pattern);
    }

    public Cell<T> findFirstCellWithValue(String value) {
        for (int colIndex = 1; colIndex <= columns().count(); colIndex++)
            for (int rowIndex = 1; rowIndex <= rows().count(); rowIndex++) {
                Cell<T> cell = cell(colIndex, rowIndex);
                if (cell.getValue().equals(value)) return cell;
            }
        return null;
    }

    public Cell<T> findFirstCellMatchesValue(String pattern) {
        for (int colIndex = 1; colIndex <= columns().count(); colIndex++)
            for (int rowIndex = 1; rowIndex <= rows().count(); rowIndex++) {
                Cell<T> cell = cell(colIndex, rowIndex);
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
    public MapArray<String, MapArray<String, Cell<T>>> findRows(String... colNameValues) {
        MapArray<String, MapArray<String, Cell<T>>> result = new MapArray<>();
        for (KeyValue<String, MapArray<String, Cell<T>>> row : rows().get()) {
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
     * Finds Columns in table matches specified criterias
     * @param rowNameValues - list of search criterias in format <rowName>=<rowValue>
     * (e.g. findColumns("Name=Roman", "Profession=Tester") )
     * @return List of Columns (dictionary: columnName:column)
     * Each Column is dictionary: rowName:cell)
     */
    public MapArray<String, MapArray<String, Cell<T>>> findColumns(String... rowNameValues) {
        MapArray<String, MapArray<String, Cell<T>>> result = new MapArray<>();
        for (KeyValue<String, MapArray<String, Cell<T>>> column : columns().get()) {
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

    public Cell<T> findCellInColumn(int colIndex, String value) {
        for (int rowIndex = 1; rowIndex <= rows().count(); rowIndex++) {
            Cell<T> cell = cell(colIndex, rowIndex);
            if (cell.getValue().equals(value)) return cell;
        }
        return null;
    }

    public Cell<T> findCellInColumn(String colName, String value) {
        int colIndex = asList(columns().headers()).indexOf(colName) + 1;
        for (int rowIndex = 1; rowIndex <= rows().count(); rowIndex++) {
            Cell<T> cell = cell(colIndex, rowIndex);
            if (cell.getValue().equals(value)) return cell;
        }
        return null;
    }

    public MapArray<String, Cell<T>> matchCellsInColumn(int colIndex, String pattern) {
        return _rows.cellsToColumn(matches(getColumn(colIndex).values(), pattern));
    }

    public MapArray<String, Cell<T>> matchCellsInColumn(String colName, String pattern) {
        return _rows.cellsToColumn(matches(getColumn(colName).values(), pattern));
    }

    //Row filters
    public MapArray<String, Cell<T>> matchCellsInRow(int rowIndex, String pattern) {
        return _columns.cellsToRow(matches(getRow(rowIndex).values(), pattern));
    }

    public MapArray<String, Cell<T>> matchCellsInRow(String rowName, String pattern) {
        return _columns.cellsToRow(matches(getRow(rowName).values(), pattern));
    }

    public Cell<T> findCellInRow(int rowIndex, String value) {
        for (int colIndex = 1; colIndex <= columns().count(); colIndex++) {
            Cell<T> cell = cell(colIndex, rowIndex);
            if (cell.getValue().equals(value)) return cell;
        }
        return null;
    }

    public Cell<T> findCellInRow(String rowName, String value) {
        int rowIndex = asList(rows().headers()).indexOf(rowName) + 1;
        for (int colIndex = 1; colIndex <= columns().count(); colIndex++) {
            Cell<T> cell = cell(colIndex, rowIndex);
            if (cell.getValue().equals(value)) return cell;
        }
        return null;
    }

    public MapArray<String, Cell<T>> findColumnByRowValue(int rowIndex, String value) {
        Cell<T> columnCell = findCellInRow(rowIndex, value);
        return columnCell != null ? columns().getRow(columnCell.columnNum) : null;
    }

    public MapArray<String, Cell<T>> findColumnByRowValue(String rowName, String value) {
        Cell<T> columnCell = findCellInRow(rowName, value);
        return columnCell != null ? columns().getRow(columnCell.columnNum) : null;
    }

    public MapArray<String, Cell<T>> findRowByColumnValue(int colIndex, String value) {
        Cell<T> rowCell = findCellInColumn(colIndex, value);
        return rowCell != null ? rows().getColumn(rowCell.rowNum) : null;
    }

    public MapArray<String, Cell<T>> findRowByColumnValue(String colName, String value) {
        Cell<T> rowCell = findCellInColumn(colName, value);
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
    protected String getValueAction() {
        return "||X|" + print(columns().headers(), "|") + "||" + LineBreak +
                print(new ArrayList<>(select(rows().headers(),
                        rowName -> "||" + rowName + "||" +
                                print(new ArrayList<>(select(where(getCells(),
                                                cell -> cell.rowName.equals(rowName)),
                                        Cell::getValue)), "|") + "||")), LineBreak);
    }

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
                MapArray<ContextType, By> newContext = avatar.getContext().clone();
                newContext.add(Locator, getLocator());
                cell.setAvatar(new GetElementModule(fillByTemplateSilent(cell.getLocator(), rowIndex, colIndex), newContext));
            } else
                cell = (T) createChild(this, clazz, fillByTemplateSilent(_cellLocatorTemplate, rowIndex, colIndex));
        } catch (Exception ex) { asserter.exception("Can't init Cell"); return null; }
        if (cell == null)
            asserter.exception("Can't init Cell");
        return cell;
    }
}