package com.ggasoftware.jdi_ui_tests.elements.complex.table;

import com.ggasoftware.jdi_ui_tests.elements.base.SelectElement;
import com.ggasoftware.jdi_ui_tests.utils.map.MapArray;
import com.ggasoftware.jdi_ui_tests.settings.JDISettings;
import com.ggasoftware.jdi_ui_tests.utils.common.LinqUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.Collection;

import static com.ggasoftware.jdi_ui_tests.utils.common.LinqUtils.select;
import static java.lang.String.format;

/**
 * Created by 12345 on 26.10.2014.
 */
class Rows<T extends SelectElement> extends TableLine<T> {
    public Rows() {
        haveHeader = false;
        elementIndex = ElementIndexType.Nums;
    }

    protected String[] getHeadersAction() {
        return LinqUtils.select(table.getWebElement().findElements(By.xpath(".//tr/td[1]")), WebElement::getText)
                .toArray(new String[1]);
    }

    private void throwRowsException(String rowName, Exception ex) {
        JDISettings.asserter.exception(format("Can't Get Rows '%s'. Exception: %s", rowName, ex));
    }

    public final MapArray<String, ICell<T>> getColumn(String colName) {
        try { return cellsToColumn(LinqUtils.select(headers(), rowName -> table.cell(new Column(colName), new Row(rowName)))); }
        catch (Exception ex) { throwRowsException(colName, ex); return null; }
    }

    public MapArray<String, ICell<T>> cellsToColumn(Collection<ICell<T>> cells) {
        return JDISettings.asserter.silent(() -> new MapArray<String, ICell<T>>(cells,
                cell -> headers()[cell.rowNum() - 1],
                cell -> cell));
    }

    public final MapArray<String, ICell<T>> getColumn(int colNum) {
        int rowsCount = -1;
        if (count > 0)
            rowsCount = count;
        else if (headers != null && (headers.length > 0))
            rowsCount = headers.length;
        if (rowsCount > 0 && rowsCount < colNum)
            JDISettings.asserter.exception(format("Can't Get Row '%s'. [num] > RowsCount(%s).", colNum, rowsCount));
        try {
            return new MapArray<>(count(),
                    rowNum -> headers()[rowNum],
                    rowNum -> table.cell(new Column(colNum), new Row(rowNum)));
        }
        catch (Exception ex) { throwRowsException(colNum + "", ex); return null; }
    }

    public MapArray<String, MapArray<String, ICell<T>>> get() {
        MapArray<String, MapArray<String, ICell<T>>> rows = new MapArray<>();
        for(String rowName : headers()) {
            MapArray<String, ICell<T>> column = new MapArray<>();
            for (String columnName : table.columns().headers())
                column.add(columnName, table.cell(new Column(columnName), new Row(rowName)));
            rows.add(rowName, column);
        }
        return rows;
    }
}
