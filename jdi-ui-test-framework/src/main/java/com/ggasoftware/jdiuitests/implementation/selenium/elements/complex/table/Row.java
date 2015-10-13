package com.ggasoftware.jdiuitests.implementation.selenium.elements.complex.table;

/**
 * Created by Roman_Iovlev on 7/17/2015.
 */
public class Row extends RowColumn {
    public static Row row(int num) { return new Row(num);}
    public static Row row(String name) { return new Row(name);}

    public Row(int num) { super(num); }
    public Row(String name) { super(name); }
}
