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
import com.ggasoftware.jdiuitest.core.utils.map.MapArray;
import com.ggasoftware.jdiuitest.web.selenium.elements.complex.table.interfaces.ICell;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.Collection;
import java.util.List;

import static com.ggasoftware.jdiuitest.core.settings.JDISettings.exception;
import static com.ggasoftware.jdiuitest.core.utils.common.LinqUtils.listCopy;
import static com.ggasoftware.jdiuitest.core.utils.common.LinqUtils.select;

/**
 * Created by 12345 on 26.10.2014.
 */
public class Columns extends TableLine {
    public Columns() {
        hasHeader = true;
        elementIndex = ElementIndexType.Nums;
        headersLocator = By.xpath(".//th");
        defaultTemplate = By.xpath(".//tr/td[%s]");
    }

    protected List<WebElement> getHeadersAction() {
        List<WebElement> headers = table.getWebElement().findElements(headersLocator);
        return (table.rows().hasHeader) ? listCopy(headers, 1, WebElement.class) : headers;
    }

    private RuntimeException throwColsException(String colName, String ex) {
        return exception("Can't Get Column '%s'. Exception: %s", colName, ex);
    }

    protected List<WebElement> getFirstLine() {
        return table.rows().getLineAction(1);
    }

    public final MapArray<String, ICell> getRow(String rowName) {
        try {
            String[] headers = headers();
            List<WebElement> webRow = table.rows().getLineAction(rowName);
            return new MapArray<>(count(),
                    key -> headers[key],
                    value -> table.cell(webRow.get(value), new Column(headers[value]), new Row(rowName)));
        } catch (Exception | Error ex) {
            throw throwColsException(rowName, ex.getMessage());
        }
    }

    public List<String> getRowValue(String rowName) {
        try {
            return select(table.rows().getLineAction(rowName), WebElement::getText);
        } catch (Exception | Error ex) {
            throw throwColsException(rowName, ex.getMessage());
        }
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
        if (count() < 0 || table.rows().count() < rowNum || rowNum <= 0)
            throw exception("Can't Get Column '%s'. [num] > ColumnsCount(%s).", rowNum, count());
        try {
            List<WebElement> webRow = table.rows().getLineAction(rowNum);
            return new MapArray<>(count(),
                    key -> headers()[key],
                    value -> table.cell(webRow.get(value), new Column(value + 1), new Row(rowNum)));
        } catch (Exception | Error ex) {
            throw throwColsException(rowNum + "", ex.getMessage());
        }
    }

    public List<String> getRowValue(int rowNum) {
        if (count() < 0 || count() < rowNum || rowNum <= 0)
            throw exception("Can't Get Column '%s'. [num] > ColumnsCount(%s).", rowNum, count());
        try {
            return select(table.rows().getLineAction(rowNum), WebElement::getText);
        } catch (Exception | Error ex) {
            throw throwColsException(rowNum + "", ex.getMessage());
        }
    }

    public final MapArray<String, String> getRowAsText(int rowNum) {
        return getRow(rowNum).toMapArray(IText::getText);
    }

    public MapArray<String, MapArray<String, ICell>> get() {
        return new MapArray<>(headers(), key -> key, value -> table.rows().getColumn(value));
    }
}
