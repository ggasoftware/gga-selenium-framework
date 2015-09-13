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
public class Rows<T extends IClickableText, P> extends TableLine<T, P> {
    public Rows() {
        haveHeader = false;
        elementIndex = ElementIndexType.Nums;
    }

    protected String[] getHeadersAction() {
        return select(table.getWebElement().findElements(By.xpath(".//tr/td[1]")), WebElement::getText)
                .toArray(new String[1]);
    }

    private RuntimeException throwRowsException(String rowName, String ex) {
        return exception(format("Can't Get Rows '%s'. Exception: %s", rowName, ex));
    }

    public final MapArray<String, Cell<T,P>> getColumn(String colName) {
        try { return cellsToColumn(select(headers(), rowName -> table.cell(new Column(colName), new Row(rowName)))); }
        catch (Exception|AssertionError ex) { throw throwRowsException(colName, ex.getMessage()); }
    }

    public MapArray<String, Cell<T,P>> cellsToColumn(Collection<Cell<T,P>> cells) {
        return new MapArray<>(cells,
                cell -> headers()[cell.rowNum - 1],
                cell -> cell);
    }

    public final MapArray<String, Cell<T,P>> getColumn(int colNum) {
        int rowsCount = -1;
        if (count > 0)
            rowsCount = count;
        else if (headers != null && (headers.length > 0))
            rowsCount = headers.length;
        if (rowsCount > 0 && rowsCount < colNum)
            throw exception(format("Can't Get Row '%s'. [num] > RowsCount(%s).", colNum, rowsCount));
        try {
            return new MapArray<>(count(),
                    rowNum -> headers()[rowNum],
                    rowNum -> table.cell(new Column(colNum), new Row(rowNum)));
        }
        catch (Exception|AssertionError ex) { throw throwRowsException(colNum + "", ex.getMessage()); }
    }

    public MapArray<String, MapArray<String, Cell<T,P>>> get() {
        MapArray<String, MapArray<String, Cell<T,P>>> rows = new MapArray<>();
        for(String rowName : headers()) {
            MapArray<String, Cell<T,P>> column = new MapArray<>();
            for (String columnName : table.columns().headers())
                column.add(columnName, table.cell(new Column(columnName), new Row(rowName)));
            rows.add(rowName, column);
        }
        return rows;
    }
}
