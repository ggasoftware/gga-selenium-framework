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
public class Rows<P, T extends ClickableText> extends TableLine<P, T> {
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

    public final MapArray<String, Cell<P, T>> getColumn(String colName) {
        try {
            return cellsToColumn(select(table.rows().headers(), rowName -> table.cell(colName, rowName)));
        }
        catch (Exception ex) { throwRowsException(colName, ex); return null; }
    }

    public MapArray<String, Cell<P, T>> cellsToColumn(Collection<Cell<P, T>> cells) throws Exception {
        return new MapArray<>(cells,
                cell -> table.rows().headers()[cell.rowNum - 1],
                cell -> cell);
    }

    public final MapArray<String, Cell<P, T>> getColumn(int colNum) {
        int rowsCount = -1;
        if (count > 0)
            rowsCount = count;
        else if (headers != null && (headers.length > 0))
            rowsCount = headers.length;
        if (rowsCount > 0 && rowsCount < colNum)
            asserter.exception(format("Can't Get Row '%s'. [num] > RowsCount(%s).", colNum, rowsCount));
        try {
            return new MapArray<>(table.rows().count(),
                    rowNum -> headers()[rowNum],
                    rowNum -> table.cell(colNum, rowNum));
        }
        catch (Exception ex) { throwRowsException(colNum + "", ex); return null; }
    }

    public MapArray<String, MapArray<String, Cell<P, T>>> get() throws Exception {
        MapArray<String, MapArray<String, Cell<P, T>>> rows = new MapArray<>();
        for(String rowName : headers()) {
            MapArray<String, Cell<P, T>> column = new MapArray<>();
            for (String columnName : table.columns().headers())
                column.add(columnName, table.cell(columnName, rowName));
            rows.add(rowName, column);
        }
        return rows;
    }
}
