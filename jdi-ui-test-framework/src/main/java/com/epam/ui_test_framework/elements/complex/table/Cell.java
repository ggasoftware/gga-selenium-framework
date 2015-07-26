package com.epam.ui_test_framework.elements.complex.table;

import com.epam.ui_test_framework.elements.base.ClickableText;
import com.epam.ui_test_framework.elements.common.Text;
import com.epam.ui_test_framework.elements.interfaces.base.IClickable;
import com.epam.ui_test_framework.elements.interfaces.common.IText;

/**
 * Created by 12345 on 25.10.2014.
 */

public class Cell<T extends IClickable & IText> extends ClickableText implements IClickable, IText {
    private T element;
    public int columnNum;
    public int rowNum;
    public String columnName;
    public String rowName;

    @Override
    protected Text text() {
        return new Text(getLocator()) {
            @Override
            protected String getTextAction() {
                return element.getText();
    }   };
    }
    @Override
    protected void clickAction() { element.click(); }
    public void select() { click(); }
    public T get() { return element; }

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
