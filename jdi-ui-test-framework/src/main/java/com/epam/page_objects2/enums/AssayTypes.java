package com.epam.page_objects2.enums;

/**
 * Created by Nataliia_Garkusha on 05-Jun-15.
 */
public enum AssayTypes {
    AMPLEX_RED("Amplex Red"),
    ELISA("ELISA"),
    NULL("Select Assay Type..."),
    FLUORESCENCE("Fluorescence"),
    LC_MS("LC-MS"),
    LUMINESCENCE("Luminescence"),
    WESTERN_BLOT("Western Blot");

    private String value;
    AssayTypes(String value) { this.value = value; }

    @Override
    public String toString() {
        return value;
    }
}
