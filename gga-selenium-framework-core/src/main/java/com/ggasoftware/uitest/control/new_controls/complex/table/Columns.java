package com.ggasoftware.uitest.control.new_controls.complex.table;

import com.ggasoftware.uitest.control.base.map.MapArray;
import com.ggasoftware.uitest.control.interfaces.base.IClickableText;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.Collection;

import static com.ggasoftware.uitest.control.base.asserter.testNG.Assert.exception;
import static com.ggasoftware.uitest.utils.LinqUtils.select;
import static java.lang.String.format;

/**
 * Created by 12345 on 26.10.2014.
 */
public class Columns<T extends IClickableText, P> extends TableLine<T, P> {
    public Columns() {
        haveHeader = true;
        elementIndex = ElementIndexType.Nums;
    }

    protected String[] getHeadersAction() {
        return select(table.getWebElement().findElements(By.xpath(".//th")), WebElement::getText)
                .toArray(new String[1]);
    }

    private RuntimeException throwColsException(String colName, String ex) {
        return exception(format("Can't Get Column '%s'. Exception: %s", colName, ex));
    }

    public final MapArray<String, Cell<T, P>> getRow(String rowName) {
        try {
            return cellsToRow(select(headers(), colName -> table.cell(new Column(colName), new Row(rowName))));
        } catch (Exception | AssertionError ex) {
            throw throwColsException(rowName, ex.getMessage());
        }
    }

    public MapArray<String, Cell<T, P>> cellsToRow(Collection<Cell<T, P>> cells) {
        return new MapArray<>(cells,
                cell -> headers()[cell.columnNum - 1],
                cell -> cell);
    }

    public MapArray<String, Cell<T, P>> getRow(int rowNum) {
        int colsCount = -1;
        if (count > 0)
            colsCount = count;
        else if (headers != null && (headers.length > 0))
            colsCount = headers.length;
        if (colsCount > 0 && colsCount < rowNum)
            throw exception(format("Can't Get Column '%s'. [num] > ColumnsCount(%s).", rowNum, colsCount));
        try {
            return new MapArray<>(count(),
                    colNum -> headers()[colNum],
                    colNum -> table.cell(new Column(colNum), new Row(rowNum)));
        } catch (Exception | AssertionError ex) {
            throw throwColsException(rowNum + "", ex.getMessage());
        }
    }

    public MapArray<String, MapArray<String, Cell<T, P>>> get() {
        MapArray<String, MapArray<String, Cell<T, P>>> cols = new MapArray<>();
        for (String columnName : headers()) {
            MapArray<String, Cell<T, P>> row = new MapArray<>();
            for (String rowName : table.rows().headers())
                row.add(rowName, table.cell(new Column(columnName), new Row(rowName)));
            cols.add(columnName, row);
        }
        return cols;
    }
}
