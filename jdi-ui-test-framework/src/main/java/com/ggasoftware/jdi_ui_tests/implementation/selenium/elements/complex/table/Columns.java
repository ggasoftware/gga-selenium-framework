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
public class Columns extends TableLine {
    public Columns() {
        hasHeader = true;
        elementIndex = ElementIndexType.Nums;
    }

    protected By columnsHeadersTemplate = By.xpath(".//th");
    protected By columnTemplate = By.xpath(".//tr/td[%s]");
    protected By columnNameTemplate = null;
    protected List<WebElement> getHeadersAction() {
        return table.getWebElement().findElements(columnsHeadersTemplate);
    }
    protected List<WebElement> getColumnAction(int colNum) {
        return table.getWebElement().findElements(fillByTemplate(columnTemplate, colNum));
    }
    protected List<WebElement> getColumnAction(String colName) {
        return (columnNameTemplate == null)
            ? getColumnAction(index(headers, colName) + 1)
            : table.getWebElement().findElements(fillByTemplate(columnNameTemplate, colName));
    }

    private RuntimeException throwColsException(String colName, String ex) {
        return asserter.exception("Can't Get Column '%s'. Exception: %s", colName, ex);
    }
    public final MapArray<String, ICell> getRow(String rowName) {
        try {
            String[] headers = headers();
            List<WebElement> webRow = table.rows().getRowAction(rowName);
            return new MapArray<>(count(),
                    key -> headers[key],
                    value -> table.cell(webRow.get(value), new Column(headers[value]), new Row(rowName)));
        }
        catch (Throwable ex) { throw throwColsException(rowName, ex.getMessage()); }
    }
    public List<String> getRowValue(String rowName) {
        try { return select(table.rows().getRowAction(rowName), WebElement::getText); }
        catch (Throwable ex) { throw throwColsException(rowName, ex.getMessage()); }
    }
    public final MapArray<String, String> getRowAsText(String rowName) {
        return getRow(rowName).toMapArray(IText::getText);
    }

    public MapArray<String, ICell> cellsToRow(Collection<ICell> cells) {
        return new MapArray<>(cells,
                cell -> headers()[cell.columnNum() - 1],
                cell -> cell);
    }

    public MapArray<String, ICell> getRow(int rowNum) {
        if (count() < 0 || count() < rowNum || rowNum <= 0)
            throw exception("Can't Get Column '%s'. [num] > ColumnsCount(%s).", rowNum, count());
        try {
            List<WebElement> webRow = table.rows().getRowAction(rowNum);
            return new MapArray<>(count(),
                    key -> headers()[key],
                    value -> table.cell(webRow.get(value), new Column(value+1), new Row(rowNum)));
        }
        catch (Throwable ex) { throw throwColsException(rowNum + "", ex.getMessage()); }
    }
    public List<String> getRowValue(int rowNum) {
        if (count() < 0 || count() < rowNum || rowNum <= 0)
            throw exception("Can't Get Column '%s'. [num] > ColumnsCount(%s).", rowNum, count());
        try { return select(table.rows().getRowAction(rowNum), WebElement::getText); }
        catch (Throwable ex) { throw throwColsException(rowNum + "", ex.getMessage()); }
    }
    public final MapArray<String, String> getRowAsText(int rowNum) {
        return getRow(rowNum).toMapArray(IText::getText);
    }

    public MapArray<String, MapArray<String, ICell>> get() {
        return new MapArray<>(headers(), key -> key, value -> table.rows().getColumn(value));
    }
}
