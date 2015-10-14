package com.epam.hem.tests.enums;

/**
 * Created by Pavel_Shcherbakov1 on 2015-10-09
 */
public enum ModelProperties {
    Name(0), Type(1), Status(2), Creator(3), CreationDate(4);

    public int value;
    ModelProperties(int value) { this.value = value; }
}
