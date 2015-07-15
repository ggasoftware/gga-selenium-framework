package com.epam.page_objects.enums;

/**
 * Created by Roman_Iovlev on 7/8/2015.
 */
public enum ExperimentButtons {
    NEW_EXPERIMENT("New Experiment"),
    SAVE("Save"),
    SAVE_NEXT("Save & Next"),
    SAVE_PUBLISH("Save & Publish"),
    PUBLISH("Publish");

    private String value;
    ExperimentButtons(String value) { this.value = value; }

    @Override
    public String toString() {
        return value;
    }
}
