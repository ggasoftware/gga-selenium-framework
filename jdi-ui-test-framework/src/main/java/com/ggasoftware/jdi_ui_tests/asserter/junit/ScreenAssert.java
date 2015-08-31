package com.ggasoftware.jdi_ui_tests.asserter.junit;

import com.ggasoftware.jdi_ui_tests.asserter.BaseChecker;

import java.util.List;

import static com.ggasoftware.jdi_ui_tests.asserter.DoScreen.DO_SCREEN_ALWAYS;

/**
 * Created by Roman_Iovlev on 6/9/2015.
 */
public class ScreenAssert {
    private static BaseChecker getAssert() { return new Check().doScreenshot(DO_SCREEN_ALWAYS); }

    public static void areEquals(Object obj, Object obj2, String message) {
        getAssert().areEquals(obj, obj2, message);
    }
    public static void areEquals(Object obj, Object obj2) {
        getAssert().areEquals(obj, obj2);
    }
    public static void assertEquals(Object obj, Object obj2, String message) {
        areEquals(obj, obj2, message);
    }
    public static void assertEquals(Object obj, Object obj2) {
        areEquals(obj, obj2);
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
    public static void assertTrue(Boolean condition, String message) {
        isTrue(condition, message);
    }
    public static void assertTrue(Boolean condition) {
        isTrue(condition);
    }
    public static void isFalse(Boolean condition, String message) {
        getAssert().isFalse(condition, message);
    }
    public static void isFalse(Boolean condition) {
        getAssert().isFalse(condition);
    }
    public static void assertFalse(Boolean condition, String message) {
        isFalse(condition, message);
    }
    public static void assertFalse(Boolean condition) {
        isFalse(condition);
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
    public static void assertSame(Object obj, Object obj2, String message) {
        areSame(obj, obj2, message);
    }
    public static void assertSame(Object obj, Object obj2) {
        areSame(obj, obj2);
    }
    public static void areDifferent(Object obj, Object obj2, String message) {
        getAssert().areDifferent(obj, obj2, message);
    }
    public static void areDifferent(Object obj, Object obj2) {
        getAssert().areDifferent(obj, obj2);
    }
    public static void assertNotSame(Object obj, Object obj2, String message) {
        areDifferent(obj, obj2, message);
    }
    public static void assertNotSame(Object obj, Object obj2) {
        areDifferent(obj, obj2);
    }
    public static BaseChecker.ListChecker eachElementOf(List<Object> list) { return getAssert().eachElementOf(list); }
    public static BaseChecker.ListChecker eachElementOf(Object[] array) { return getAssert().eachElementOf(array); }
    public static BaseChecker.ListChecker assertEach(List<Object> list) { return eachElementOf(list); }
    public static BaseChecker.ListChecker assertEach(Object[] array) { return eachElementOf(array); }

}
