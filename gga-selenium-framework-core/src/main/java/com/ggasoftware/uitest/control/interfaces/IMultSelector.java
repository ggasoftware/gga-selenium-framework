package com.ggasoftware.uitest.control.interfaces;

import java.util.List;

/**
 * Created by Roman_Iovlev on 6/10/2015.
 */
public interface IMultSelector {
    void select(String... elementNames);
    void select(Enum... elementNames);
    void select(int... indexes);
    List<String> areSelected();
    List<String> areDeselected();
    boolean areSelected(String... elementNames);
    boolean areSelected(Enum... elementNames);
    boolean areSelected(int... indexes);
    void clear();
    void selectAll();
    void selectOnly(String... elementNames);
    void selectOnly(Enum... elementNames);
    void selectOnly(int... indexes);
    List<String> getAllValues();
    String getAllValuesAsText();

}