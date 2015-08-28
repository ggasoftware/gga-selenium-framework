package com.ggasoftware.jdi_ui_tests.asserter.junit;

import com.ggasoftware.jdi_ui_tests.asserter.BaseChecker;

import java.util.Collection;

import static com.ggasoftware.jdi_ui_tests.asserter.DoScreen.DO_SCREEN;

/**
 * Created by Roman_Iovlev on 6/9/2015.
 */
public class ScreenAssert {
    private static BaseChecker getAssert() { return new Check(DO_SCREEN); }
    public static void areEquals(Object obj, Object obj2, String message) {
        getAssert().areEquals(obj, obj2, message);
    }
    public static void areEquals(Object obj, Object obj2) {
        getAssert().areEquals(obj, obj2);
    }
    public static void matches(String str, String regEx, String message) {
        getAssert().matches(str, regEx, message);
    }
    public static void matches(String str, String regEx) {
        getAssert().matches(str, regEx);
    }
    public static void contains(String str, String str2, String message) {
        getAssert().contains(str, str2, message);
    }
    public static void contains(String str, String str2) {
        getAssert().contains(str, str2);
    }
    public static void isTrue(Boolean condition, String message) {
        getAssert().isTrue(condition, message);
    }
    public static void isTrue(Boolean condition) {
        getAssert().isTrue(condition);
    }

    public static void isFalse(Boolean condition, String message) {
        getAssert().isFalse(condition, message);
    }
    public static void isFalse(Boolean condition) {
        getAssert().isFalse(condition);
    }
    public static void isEmpty(Object obj, String message) {
        getAssert().isEmpty(obj, message);
    }
    public static void isEmpty(Object obj) {
        getAssert().isEmpty(obj);
    }
    public static void isNotEmpty(Object obj, String message) {
        getAssert().isNotEmpty(obj, message);
    }
    public static void isNotEmpty(Object obj) {
        getAssert().isNotEmpty(obj);
    }
    public static void areSame(Object obj, Object obj2, String message) {
        getAssert().areSame(obj, obj2, message);
    }
    public static void areSame(Object obj, Object obj2) {
        getAssert().areSame(obj, obj2);
    }
    public static void areDifferent(Object obj, Object obj2, String message) {
        getAssert().areDifferent(obj, obj2, message);
    }
    public static void areDifferent(Object obj, Object obj2) {
        getAssert().areDifferent(obj, obj2);
    }

    public <T> void listContains(Collection<T> collection, T actual, String message) {
        getAssert().listContains(collection, actual, message);
    }
    public <T> void listContains(Collection<T> collection, T actual) {
        getAssert().listContains(collection, actual);
    }

    public <T> void listEquals(Collection<T> collection, Collection<T> collection2, String message) {
        getAssert().listEquals(collection, collection2, message);
    }
    public <T> void listEquals(Collection<T> collection, Collection<T> collection2) {
        getAssert().listEquals(collection, collection2);
    }
    public static void arrayEquals(Object array, Object array2, String message) {
        getAssert().arrayEquals(array, array2, message);
    }
    public static void arrayEquals(Object array, Object array2) {
        getAssert().arrayEquals(array, array2);
    }
    public static void arrayContains(Object array, Object actual, String message) {
        getAssert().arrayContains(array, actual, message);
    }
    public static void arrayContains(Object array, Object actual) {
        getAssert().arrayContains(array, actual);
    }
}
