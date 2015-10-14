package com.epam.hem.tests.enums;

/**
 * Created by Pavel_Shcherbakov1 on 2015-10-09
 */
public enum ProjectPropertiesListView {
    Name(0), Creator(1), Owner(2), CreationDate(3);

    public int value;
    ProjectPropertiesListView(int value) { this.value = value; }
}
