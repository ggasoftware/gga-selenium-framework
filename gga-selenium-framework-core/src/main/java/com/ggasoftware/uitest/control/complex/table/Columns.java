package com.ggasoftware.uitest.control.complex.table;

import com.ggasoftware.uitest.utils.map.MapArray;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.Collection;

import static com.ggasoftware.uitest.utils.common.LinqUtils.select;
import static com.ggasoftware.uitest.utils.settings.FrameworkSettings.asserter;
import static java.lang.String.format;

/**
 * Created by 12345 on 26.10.2014.
 */
public class Columns<P, T extends ClickableText> extends TableLine<P, T> {
    public Columns() {
        haveHeader = true;
        elementIndex = ElementIndexType.Nums;
    }

    protected String[] getHeadersAction() {
        return select(table.getWebElement().findElements(By.xpath(".//th")), WebElement::getText)
                .toArray(new String[1]);
    }

    private void throwColsException(String colName, Exception ex) {
        asserter.exception(format("Can't Get Column '%s'. Exception: %s", colName, ex));
    }

    public final MapArray<String, Cell<P, T>> getRow(String rowName) {
        try {
            return cellsToRow(select(table.columns().headers(), colName -> table.cell(colName, rowName)));
        }
        catch (Exception ex) { throwColsException(rowName, ex); return null; }
    }

    public MapArray<String, Cell<P, T>> cellsToRow(Collection<Cell<P, T>> cells) {
        return asserter.silentException(() -> new MapArray<String, Cell<P, T>>(cells,
                cell -> table.columns().headers()[cell.columnNum - 1],
                cell -> cell));
    }

    public MapArray<String, Cell<P, T>> getRow(int rowNum) {
        int colsCount = -1;
        if (count > 0)
        colsCount = count;
        else if (headers != null && (headers.length > 0))
        colsCount = headers.length;
        if (colsCount > 0 && colsCount < rowNum)
            asserter.exception(format("Can't Get Column '%s'. [num] > ColumnsCount(%s).", rowNum, colsCount));
        try {
            return new MapArray<>(table.columns().count(),
                    colNum -> headers()[colNum],
                    colNum -> table.cell(colNum, rowNum));
        }
        catch (Exception ex) { throwColsException(rowNum + "", ex); return null; }
    }

    public MapArray<String, MapArray<String, Cell<P, T>>> get() {
        MapArray<String, MapArray<String, Cell<P, T>>> cols = new MapArray<>();
        for(String columnName : headers()) {
            MapArray<String, Cell<P, T>> row = new MapArray<>();
            for (String rowName : table.rows().headers())
                row.add(rowName, table.cell(columnName, rowName));
            cols.add(columnName, row);
        }
        return cols;
    }
}
