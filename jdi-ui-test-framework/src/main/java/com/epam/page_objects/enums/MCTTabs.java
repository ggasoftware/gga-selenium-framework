package com.epam.page_objects.enums;

/**
 * Created by Maksim_Palchevskii on 6/10/2015.
 */
public enum  MCTTabs {
    DEFINE_EXPERIMENT("Define Experiment"),
    READOUT("Readout"),
    PLATEMAP("Platemap"),
    CALIBRATION("Calibration"),
    RESULTS("Results"),

    SEARCH_EXPERIMENTS("Search Experiments"),
    SEARCH_RESULTS("Search Results");

    private String value;
    MCTTabs(String value) { this.value = value; }

    @Override
    public String toString() {
        return value;
    }
}
