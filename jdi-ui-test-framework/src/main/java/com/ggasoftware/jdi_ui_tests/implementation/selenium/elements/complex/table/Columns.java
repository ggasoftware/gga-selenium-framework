package com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.complex.table;

import com.ggasoftware.jdi_ui_tests.core.utils.map.MapArray;
import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.complex.table.interfaces.ICell;
import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.interfaces.common.IText;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.Collection;
import java.util.List;

import static com.ggasoftware.jdi_ui_tests.core.settings.JDISettings.asserter;
import static com.ggasoftware.jdi_ui_tests.core.utils.common.LinqUtils.index;
import static com.ggasoftware.jdi_ui_tests.core.utils.common.WebDriverByUtils.fillByTemplate;
import static java.lang.String.format;

/**
 * Created by 12345 on 26.10.2014.
 */
public class Columns extends TableLine {
    public Columns() {
        hasHeader = true;
        elementIndex = ElementIndexType.Nums;
    }

    protected By rowTemplate = By.xpath(".//tr/td[%s]");
    protected By columnsHeadersTemplate = By.xpath(".//th");
    protected List<WebElement> getHeadersAction() {
        return table.getWebElement().findElements(columnsHeadersTemplate);
    }
    protected List<WebElement> getRowAction(int rowNum) {
        return table.getWebElement().findElements(fillByTemplate(rowTemplate, rowNum));
    }

    private RuntimeException throwColsException(String colName, Exception ex) {
        return asserter.exception(format("Can't Get Column '%s'. Exception: %s", colName, ex));
    }
    public final MapArray<String, ICell> getRow(String rowName) {
        try {
            String[] headers = headers();
            List<WebElement> webRow = getRowAction(index(headers, rowName));
            return new MapArray<>(count(),
                    key -> headers[key],
                    value -> table.cell(webRow.get(value), new Column(headers[value]), new Row(rowName)));
        }
        catch (Exception ex) { throw throwColsException(rowName, ex); }
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
            throw asserter.exception(format("Can't Get Column '%s'. [num] > ColumnsCount(%s).", rowNum, count()));
        try {
            List<WebElement> webRow = getRowAction(rowNum);
            return new MapArray<>(count(),
                    key -> headers()[key],
                    value -> table.cell(webRow.get(value), new Column(value+1), new Row(rowNum)));
        }
        catch (Exception ex) { throw throwColsException(rowNum + "", ex); }
    }
    public final MapArray<String, String> getRowAsText(int rowNum) {
        return getRow(rowNum).toMapArray(IText::getText);
    }

    public MapArray<String, MapArray<String, ICell>> get() {
        return new MapArray<>(headers(), key -> key, value -> table.columns().getRow(value));
    }
}
