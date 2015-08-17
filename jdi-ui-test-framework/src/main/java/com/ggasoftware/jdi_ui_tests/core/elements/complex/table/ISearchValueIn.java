package com.ggasoftware.jdi_ui_tests.core.elements.complex.table;

/**
 * Created by Roman_Iovlev on 8/13/2015.
 */
public interface ISearchValueIn {
    boolean inColumn(String name);
    boolean inRow(String name);
    boolean inColumn(int index);
    boolean inRow(int index);
}
