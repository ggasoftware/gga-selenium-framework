package com.ggasoftware.uitest.control.complex.table;

import com.ggasoftware.uitest.control.interfaces.IClickableText;
import com.ggasoftware.uitest.control.simple.Text;

/**
 * Created by 12345 on 25.10.2014.
 */

public class Cell<T extends IClickableText> extends Text implements IClickableText {
    public T element;
    public int columnNum;
    public int rowNum;
    public String columnName;
    public String rowName;

    @Override
    protected String getTextAction() { return element.getText(); }
    public void select() { element.click(); }
    public void click() { element.click(); }

    public Cell(T element, int columnNum, int rowNum, String colName, String rowName) {
        this.element = element;
        this.columnNum = columnNum;
        this.rowNum = rowNum;
        this.columnName = colName;
        this.rowName = rowName;
    }

    public Cell<T> updateData(String colName, String rowName) {
        if ((columnName == null || columnName.equals("")) && !(colName == null || colName.equals("")))
            columnName = colName;
        if ((this.rowName == null || this.rowName.equals("")) && !(rowName == null || rowName.equals("")))
        this.rowName = rowName;
        return this;
    }
}
