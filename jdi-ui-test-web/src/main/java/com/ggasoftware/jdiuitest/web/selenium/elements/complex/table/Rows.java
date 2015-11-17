/*
 * Copyright 2004-2016 EPAM Systems
 *
 * This file is part of JDI project.
 *
 * JDI is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * JDI is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; 
 * without even the implied warranty ofMERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. 
 * See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with JDI. If not, see <http://www.gnu.org/licenses/>.
 */
 
package com.ggasoftware.jdiuitest.web.selenium.elements.complex.table;

import com.ggasoftware.jdiuitest.core.interfaces.common.IText;
import com.ggasoftware.jdiuitest.core.utils.common.LinqUtils;
import com.ggasoftware.jdiuitest.core.utils.map.MapArray;
import com.ggasoftware.jdiuitest.web.selenium.elements.complex.table.interfaces.ICell;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.Collection;
import java.util.List;

import static com.ggasoftware.jdiuitest.core.settings.JDISettings.exception;

/**
 * Created by 12345 on 26.10.2014.
 */
public class Rows extends TableLine {
    public Rows() {
        hasHeader = false;
        elementIndex = ElementIndexType.Nums;
        headersLocator = By.xpath(".//tr/td[1]");
        defaultTemplate = By.xpath(".//tr[%s]/td");
    }

    protected List<WebElement> getHeadersAction() {
        return table.getWebElement().findElements(headersLocator);
    }

    private RuntimeException throwRowsException(String rowName, String ex) {
        return exception("Can't Get Rows for column '%s'. Exception: %s", rowName, ex);
    }

    protected List<WebElement> getFirstLine() {
        return table.columns().getLineAction(1);
    }

    public final MapArray<String, ICell> getColumn(String colName) {
        try {
            String[] headers = headers();
            List<WebElement> webColumn = table.columns().getLineAction(colName);
            return new MapArray<>(count(),
                    key -> headers[key],
                    value -> table.cell(webColumn.get(value), new Column(colName), new Row(headers[value])));
        } catch (Exception | Error ex) {
            throw throwRowsException(colName, ex.getMessage());
        }
    }

    public final List<String> getColumnValue(String colName) {
        try {
            return LinqUtils.select(table.columns().getLineAction(colName), WebElement::getText);
        } catch (Exception | Error ex) {
            throw throwRowsException(colName, ex.getMessage());
        }
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
            List<WebElement> webColumn = table.columns().getLineAction(colNum);
            return new MapArray<>(count(),
                    key -> headers()[key],
                    value -> table.cell(webColumn.get(value), new Column(colNum), new Row(value + 1)));
        } catch (Exception | Error ex) {
            throw throwRowsException(colNum + "", ex.getMessage());
        }
    }

    public final List<String> getColumnValue(int colNum) {
        if (count() < 0 || count() < colNum || colNum <= 0)
            throw exception("Can't Get Row '%s'. [num] > RowsCount(%s).", colNum, count());
        try {
            return LinqUtils.select(table.columns().getLineAction(colNum), WebElement::getText);
        } catch (Exception | Error ex) {
            throw throwRowsException(colNum + "", ex.getMessage());
        }
    }

    public final MapArray<String, String> getColumnAsText(int colNum) {
        return getColumn(colNum).toMapArray(IText::getText);
    }

    public MapArray<String, MapArray<String, ICell>> get() {
        return new MapArray<>(headers(), key -> key, value -> table.columns().getRow(value));
    }

}
