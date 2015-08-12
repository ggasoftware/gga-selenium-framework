package com.ggasoftware.jdi_ui_tests.core.elements.complex.table;

import com.ggasoftware.jdi_ui_tests.core.elements.base.SelectElement;
import com.ggasoftware.jdi_ui_tests.core.elements.common.Text;
import com.ggasoftware.jdi_ui_tests.core.elements.interfaces.base.ISelect;

/**
 * Created by 12345 on 25.10.2014.
 */
/**
 * Cell have some info about its position:<br>
 * ColumnName, RowName, ColumnIndex, RowIndex<br>
 * You can do some actions with Cell:<br>
 * Click, Select, getText, waitText, waitMatchText<br>
 * Also you can use get() method to get element of specified for table Type and do any possible action with it<br>
 * */
class Cell<T extends SelectElement> extends SelectElement implements ISelect, ICell<T> {
    private T element;
    private int columnNum;
    public int columnNum() { return columnNum; }
    private int rowNum;
    public int rowNum() { return rowNum; }
    private String columnName;
    public String columnName() { return columnName; }
    private String rowName;
    public String rowName() { return rowName; }

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
