package com.ggasoftware.uitest.control.new_controls.complex.table;

import com.ggasoftware.uitest.control.interfaces.base.IClickableText;
import com.ggasoftware.uitest.control.new_controls.common.ClickableText;

/**
 * Created by 12345 on 25.10.2014.
 */

public class Cell<T extends IClickableText, P> extends ClickableText<P> implements IClickableText<P> {
    public int columnNum;
    public int rowNum;
    public String columnName;
    public String rowName;
    private T element;

    public Cell(T element, int columnNum, int rowNum, String colName, String rowName) {
        this.element = element;
        this.columnNum = columnNum;
        this.rowNum = rowNum;
        this.columnName = colName;
        this.rowName = rowName;
    }

    @Override
    protected String getTextAction() {
        return element.getText();
    }

    @Override
    protected void clickActionM() {
        element.click();
    }

    public void select() {
        click();
    }

    public T get() {
        return element;
    }

    public Cell<T, P> updateData(String colName, String rowName) {
        if ((columnName == null || columnName.equals("")) && !(colName == null || colName.equals("")))
            columnName = colName;
        if ((this.rowName == null || this.rowName.equals("")) && !(rowName == null || rowName.equals("")))
            this.rowName = rowName;
        return this;
    }
}
