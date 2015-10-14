package com.epam.hem.tests.utils;

import java.text.SimpleDateFormat;

/**
 * Created by Pavel_Shcherbakov1 on 2015-10-13
 */
public class Date {
    public static String getCurrentDate() {
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
        Date now = new Date();
        return sdfDate.format(now);
    }
}
