package com.ggasoftware.uitest.control.complex.table;

import com.ggasoftware.uitest.control.base.Element;
import org.openqa.selenium.By;

/**
 * Created by 12345 on 25.10.2014.
 */

public class Cell<P, T extends ClickableText> extends Element<P>{
    public T element;
    public int columnNum;
    public int rowNum;
    public String columnName;
    public String rowName;

    public String getValue() { return element.getValue(); }
    public String getText() { return getValue(); }
    public void select() { element.click(); }
    public P click() { select(); return parent; }

    public Cell(T element, int columnNum, int rowNum, String colName, String rowName, By tableLocator) {
        this.element = element;
        element.setContext(tableLocator);
        this.columnNum = columnNum;
        this.rowNum = rowNum;
        columnName = colName;
        this.rowName = rowName;
    }

    public Cell<P, T> updateData(String colName, String rowName) {
        if ((columnName == null || columnName.equals("")) && !(colName == null || colName.equals("")))
            columnName = colName;
        if ((this.rowName == null || this.rowName.equals("")) && !(rowName == null || rowName.equals("")))
        this.rowName = rowName;
        return this;
    }
}
