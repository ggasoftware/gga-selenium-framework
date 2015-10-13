package com.ggasoftware.jdiuitests.implementation.selenium.elements.complex.table;

/**
 * Created by Roman_Iovlev on 10/9/2015.
 */
public class TableSettings {
    public boolean columnHasHeaders = true;
    public boolean rowHasHeaders = false;
    public String[] columnHeaders;
    public String[] rowHeaders;
    public int columnsCount;
    public int rowsCount;

    public TableSettings() {}
    public TableSettings(boolean columnHasHeaders, boolean rowHasHeaders) {
        this.columnHasHeaders = columnHasHeaders;
        this.rowHasHeaders = rowHasHeaders;
    }
    public TableSettings(String[] columnHeaders, String[] rowHeaders) {
        this.columnHeaders = columnHeaders;
        this.rowHeaders = rowHeaders;
    }
    public TableSettings(int columnsCount, int rowsCount) {
        this.columnsCount = columnsCount;
        this.rowsCount = rowsCount;
    }
}
