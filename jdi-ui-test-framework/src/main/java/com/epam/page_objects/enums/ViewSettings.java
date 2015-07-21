package com.epam.page_objects.enums;

/**
 * Created by Roman_Iovlev on 7/8/2015.
 */
public enum ViewSettings {
    DATA_HEATMAP("heatmap"),
    SAMPLES("sample"),
    DILUTIONS("dilution"),
    REPLICATE("replica");

    private String value;
    ViewSettings(String value) { this.value = value; }

    @Override
    public String toString() {
        return value;
    }
}
