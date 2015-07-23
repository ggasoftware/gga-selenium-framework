package com.epam.ui_test_framework.elements.complex.table;

import com.epam.ui_test_framework.elements.interfaces.base.IClickableText;
import com.epam.ui_test_framework.utils.map.MapArray;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.Collection;

import static com.epam.ui_test_framework.utils.common.LinqUtils.select;
import static com.epam.ui_test_framework.settings.FrameworkSettings.asserter;
import static java.lang.String.format;

/**
 * Created by 12345 on 26.10.2014.
 */
public class Rows<T extends IClickableText> extends TableLine<T> {
    public Rows() {
        haveHeader = false;
        elementIndex = ElementIndexType.Nums;
    }

    protected String[] getHeadersAction() {
        return select(table.getWebElement().findElements(By.xpath(".//tr/td[1]")), WebElement::getText)
                .toArray(new String[1]);
    }

    private void throwRowsException(String rowName, Exception ex) {
        asserter.exception(format("Can't Get Rows '%s'. Exception: %s", rowName, ex));
    }

    public final MapArray<String, Cell<T>> getColumn(String colName) {
        try { return cellsToColumn(select(headers(), rowName -> table.cell(new Column(colName), new Row(rowName)))); }
        catch (Exception ex) { throwRowsException(colName, ex); return null; }
    }

    public MapArray<String, Cell<T>> cellsToColumn(Collection<Cell<T>> cells) {
        return asserter.silentException(() -> new MapArray<String, Cell<T>>(cells,
                cell -> headers()[cell.rowNum - 1],
                cell -> cell));
    }

    public final MapArray<String, Cell<T>> getColumn(int colNum) {
        int rowsCount = -1;
        if (count > 0)
            rowsCount = count;
        else if (headers != null && (headers.length > 0))
            rowsCount = headers.length;
        if (rowsCount > 0 && rowsCount < colNum)
            asserter.exception(format("Can't Get Row '%s'. [num] > RowsCount(%s).", colNum, rowsCount));
        try {
            return new MapArray<>(count(),
                    rowNum -> headers()[rowNum],
                    rowNum -> table.cell(new Column(colNum), new Row(rowNum)));
        }
        catch (Exception ex) { throwRowsException(colNum + "", ex); return null; }
    }

    public MapArray<String, MapArray<String, Cell<T>>> get() {
        MapArray<String, MapArray<String, Cell<T>>> rows = new MapArray<>();
        for(String rowName : headers()) {
            MapArray<String, Cell<T>> column = new MapArray<>();
            for (String columnName : table.columns().headers())
                column.add(columnName, table.cell(new Column(columnName), new Row(rowName)));
            rows.add(rowName, column);
        }
        return rows;
    }
}
