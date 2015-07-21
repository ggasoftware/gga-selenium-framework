package com.epam.page_objects2.enums;

/**
 * Created by Roman_Iovlev on 7/8/2015.
 */
public enum ExperimentInputs {
    CELL_LINE("cellType_input"),
    DATE("date"),
    CELL_COUNT("cellCountInMillions"),
    CELL_PASSAGE("cellPassage"),
    TRANSFECTED_DATE("transfectedDate");

    private String value;
    ExperimentInputs(String value) { this.value = value; }

    @Override
    public String toString() {
        return value;
    }
}
