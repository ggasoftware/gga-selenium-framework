package com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.complex.table;

import com.ggasoftware.jdi_ui_tests.core.utils.map.MapArray;
import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.complex.table.interfaces.ICell;
import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.interfaces.common.IText;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.Collection;
import java.util.List;

import static com.ggasoftware.jdi_ui_tests.core.settings.JDISettings.asserter;
import static com.ggasoftware.jdi_ui_tests.core.settings.JDISettings.exception;
import static com.ggasoftware.jdi_ui_tests.core.utils.common.LinqUtils.index;
import static com.ggasoftware.jdi_ui_tests.core.utils.common.LinqUtils.select;
import static com.ggasoftware.jdi_ui_tests.core.utils.common.WebDriverByUtils.fillByTemplate;

/**
 * Created by 12345 on 26.10.2014.
 */
public class Rows extends TableLine {
    public Rows() {
        hasHeader = false;
        elementIndex = ElementIndexType.Nums;
    }

    protected By rowsHeadersLocator = By.xpath(".//tr/td[1]");
    protected By defaultRowTemplate = By.xpath(".//tr[%s]/td");
    protected By rowTemplate = null;
    protected List<WebElement> getHeadersAction() {
        return table.getWebElement().findElements(rowsHeadersLocator);
    }
    protected List<WebElement> getRowAction(int rowNum) {
        return table.getWebElement().findElements(fillByTemplate(defaultRowTemplate, rowNum));
    }
    protected List<WebElement> getRowAction(String rowName) {
        return (rowTemplate == null)
                ? getRowAction(index(headers(), rowName) + 1)
                : table.getWebElement().findElements(fillByTemplate(rowTemplate, rowName));
    }

    private RuntimeException throwRowsException(String rowName, String ex) {
        return asserter.exception("Can't Get Rows for column '%s'. Exception: %s", rowName, ex);
    }
    public final MapArray<String, ICell> getColumn(String colName) {
        try {
            String[] headers = headers();
            List<WebElement> webColumn = table.columns().getColumnAction(colName);
            return new MapArray<>(count(),
                    key -> headers[key],
                    value -> table.cell(webColumn.get(value), new Column(colName), new Row(headers[value])));
        }
        catch (Throwable ex) { throw throwRowsException(colName, ex.getMessage()); }
    }
    public final List<String> getColumnValue(String colName) {
        try { return select(table.columns().getColumnAction(colName), WebElement::getText); }
        catch (Throwable ex) { throw throwRowsException(colName, ex.getMessage()); }
    }
    public final MapArray<String, String> getColumnAsText(String colName) {
        return getColumn(colName).toMapArray(IText::getText);
    }

    public MapArray<String, ICell> cellsToColumn(Collection<ICell> cells) {
        return new MapArray<>(cells,
                cell -> headers()[cell.rowNum() - 1],
                cell -> cell);
    }

    public final MapArray<String, ICell> getColumn(int colNum) {
        if (count() < 0 || table.columns().count() < colNum || colNum <= 0)
            throw exception("Can't Get Row '%s'. [num] > RowsCount(%s).", colNum, count());
        try {
            List<WebElement> webColumn = table.columns().getColumnAction(colNum);
            return new MapArray<>(count(),
                    key -> headers()[key],
                    value -> table.cell(webColumn.get(value), new Column(colNum), new Row(value + 1)));
        }
        catch (Throwable ex) { throw throwRowsException(colNum + "", ex.getMessage()); }
    }
    public final List<String> getColumnValue(int colNum) {
        if (count() < 0 || count() < colNum || colNum <= 0)
            throw exception("Can't Get Row '%s'. [num] > RowsCount(%s).", colNum, count());
        try { return select(table.columns().getColumnAction(colNum), WebElement::getText); }
        catch (Throwable ex) { throw throwRowsException(colNum + "", ex.getMessage()); }
    }
    public final MapArray<String, String> getColumnAsText(int colNum) {
        return getColumn(colNum).toMapArray(IText::getText);
    }

    public MapArray<String, MapArray<String, ICell>> get() {
        return new MapArray<>(headers(), key -> key, value -> table.columns().getRow(value));
    }

}
