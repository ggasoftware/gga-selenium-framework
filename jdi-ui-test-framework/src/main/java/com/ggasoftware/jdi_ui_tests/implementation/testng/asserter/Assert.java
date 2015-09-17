package com.ggasoftware.jdi_ui_tests.implementation.testng.asserter;

import com.ggasoftware.jdi_ui_tests.core.asserter.BaseChecker;
import com.ggasoftware.jdi_ui_tests.core.utils.linqInterfaces.JFuncT;

import java.util.Collection;
import java.util.List;

import static com.ggasoftware.jdi_ui_tests.core.asserter.DoScreen.NO_SCREEN;
import static com.ggasoftware.jdi_ui_tests.core.asserter.DoScreen.SCREEN_ON_FAIL;

/**
 * Created by Roman_Iovlev on 6/9/2015.
 */
public class Assert  {
    private static BaseChecker getAssert() { return new Check().doScreenshot(NO_SCREEN); }
    
    public static BaseChecker doScreenOnFail() { return new Check().doScreenshot(SCREEN_ON_FAIL); }
    public static BaseChecker ignoreCase() { return getAssert().ignoreCase(); }

    public static void areEquals(Object obj, Object obj2, String message) { getAssert().areEquals(obj, obj2, message); }
    public static void areEquals(Object obj, Object obj2) { getAssert().areEquals(obj, obj2); }
    public static void assertEquals(Object obj, Object obj2, String message) { areEquals(obj, obj2, message); }
    public static void assertEquals(Object obj, Object obj2) { areEquals(obj, obj2); }
    public static void matches(String str, String regEx, String message) { getAssert().matches(str, regEx, message); }
    public static void matches(String str, String regEx) { getAssert().matches(str, regEx); }
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
    public static void areDifferent(Object obj, Object obj2, String message) { getAssert().areDifferent(obj, obj2, message); }
    public static void areDifferent(Object obj, Object obj2) {
        getAssert().areDifferent(obj, obj2);
    }
    public static void assertNotSame(Object obj, Object obj2, String message) {
        areDifferent(obj, obj2, message);
    }
    public static void assertNotSame(Object obj, Object obj2) {
        areDifferent(obj, obj2);
    }

    public static <T> void listEquals(Collection<T> actual, Collection<T> expected, String failMessage) { getAssert().listEquals(actual, expected, failMessage); }
    public static <T> void listEquals(Collection<T> actual, Collection<T> expected) { getAssert().listEquals(actual, expected); }
    public static <T> void arrayEquals(T actual, T expected, String failMessage) { getAssert().arrayEquals(actual, expected, failMessage); }
    public static <T> void arrayEquals(T actual, T expected) { getAssert().arrayEquals(actual, expected); }
    
    public static BaseChecker.ListChecker eachElementOf(List<Object> list) { return getAssert().eachElementOf(list); }
    public static BaseChecker.ListChecker eachElementOf(Object[] array) { return getAssert().eachElementOf(array); }
    public static BaseChecker.ListChecker assertEach(List<Object> list) { return eachElementOf(list); }
    public static BaseChecker.ListChecker assertEach(Object[] array) { return eachElementOf(array); }

    public static <T> void areEquals(JFuncT<T> actual, T expected, String failMessage) { getAssert().areEquals(actual, expected, failMessage); }
    public static <T> void areEquals(JFuncT<T> actual, T expected) { getAssert().areEquals(actual, expected); }
    public static void matches(JFuncT<String> actual, String regEx, String failMessage) { getAssert().matches(actual, regEx, failMessage); }
    public static void matches(JFuncT<String> actual, String regEx) { getAssert().matches(actual, regEx); }
    public static void contains(JFuncT<String> actual, String expected, String failMessage) { getAssert().contains(actual, expected, failMessage); }
    public static void contains(JFuncT<String> actual, String expected) { getAssert().contains(actual, expected); }
    public static void isTrue(JFuncT<Boolean> condition, String failMessage) { getAssert().isTrue(condition, failMessage); }
    public static void isTrue(JFuncT<Boolean> condition) { getAssert().isTrue(condition); }
    public static void isFalse(JFuncT<Boolean> condition, String failMessage) { getAssert().isFalse(condition, failMessage); }
    public static void isFalse(JFuncT<Boolean> condition) { getAssert().isFalse(condition); }
    public static void isEmpty(JFuncT<Object> obj, String failMessage) { getAssert().isEmpty(obj, failMessage); }
    public static void isEmpty(JFuncT<Object> obj) { getAssert().isEmpty(obj); }
    public static void isNotEmpty(JFuncT<Object> obj, String failMessage) { getAssert().isNotEmpty(obj, failMessage); }
    public static void isNotEmpty(JFuncT<Object> obj) { getAssert().isNotEmpty(obj); }
    public static <T> void areSame(JFuncT<T> actual, T expected, String failMessage) { getAssert().areSame(actual, expected, failMessage); }
    public static <T> void areSame(JFuncT<T> actual, T expected) { getAssert().areSame(actual, expected); }
    public static <T> void areDifferent(JFuncT<T> actual, T expected, String failMessage) { getAssert().areDifferent(actual, expected, failMessage); }
    public static <T> void areDifferent(JFuncT<T> actual, T expected) { getAssert().areDifferent(actual, expected); }
    public static void assertNotSame(JFuncT<Object> obj, Object obj2, String message) { areDifferent(obj, obj2, message); }
    public static void assertNotSame(JFuncT<Object> obj, Object obj2) {
        areDifferent(obj, obj2);
    }

    public static <T> void listEquals(JFuncT<Collection<T>> actual, Collection<T> expected, String failMessage) { getAssert().listEquals(actual, expected, failMessage); }
    public static <T> void listEquals(JFuncT<Collection<T>> actual, Collection<T> expected) { getAssert().listEquals(actual, expected); }
    public static <T> void arrayEquals(JFuncT<T> actual, T expected, String failMessage) { getAssert().arrayEquals(actual, expected, failMessage); }
    public static <T> void arrayEquals(JFuncT<T> actual, T expected) { getAssert().arrayEquals(actual, expected); }
}
