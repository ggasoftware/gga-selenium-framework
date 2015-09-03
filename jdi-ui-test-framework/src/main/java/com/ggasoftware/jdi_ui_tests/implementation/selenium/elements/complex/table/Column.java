package com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.complex.table;

/**
 * Created by Roman_Iovlev on 7/17/2015.
 */
public class Column extends RowColumn {
    public static Column column(int num) { return new Column(num);}
    public static Column column(String name) { return new Column(name);}

    public Column(int num) { super(num); }
    public Column(String name) { super(name); }
}
