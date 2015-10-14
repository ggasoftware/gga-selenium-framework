package com.epam.hem.tests.enums;

/**
 * Created by Pavel_Shcherbakov1 on 2015-10-09
 */
public enum ProjectDetails {
    Name(0), Creator(1), Owner(2), CreationDate(3), ModifiedDate(4), ModelName(5), ScenariosNumber(6), AnalysesNumber(7);

    public int value;
    ProjectDetails(int value) { this.value = value; }
}
