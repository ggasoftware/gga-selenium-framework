package com.ggasoftware.jdiuitests.implementation.junit.asserter;

import com.ggasoftware.jdiuitests.core.asserter.BaseChecker;
import com.ggasoftware.jdiuitests.core.asserter.DoScreen;
import com.ggasoftware.jdiuitests.core.utils.linqInterfaces.JFuncT;
import com.ggasoftware.jdiuitests.core.utils.map.MapArray;

import java.util.Collection;
import java.util.List;

import static com.ggasoftware.jdiuitests.core.asserter.DoScreen.NO_SCREEN;
import static com.ggasoftware.jdiuitests.core.asserter.DoScreen.SCREEN_ON_FAIL;

/**
 * Created by Roman_Iovlev on 6/9/2015.
 */
public class Assert {
    private static DoScreen doScreen = SCREEN_ON_FAIL;
    private static BaseChecker getAssert() { return new Check().doScreenshot(doScreen); }
    public static void noScreenOnFail() { doScreen = NO_SCREEN; }

    public static BaseChecker doScreenOnFail() { return new Check().doScreenshot(SCREEN_ON_FAIL); }
    public static BaseChecker ignoreCase() { return getAssert().ignoreCase(); }

    public static RuntimeException exception(String msg, Object... args) { return getAssert().exception(msg, args); }
    public static <T> void areEquals(T actual, T expected, String failMessage) { getAssert().areEquals(actual, expected, failMessage); }
    public static <T> void areEquals(T actual, T expected) { getAssert().areEquals(actual, expected); }
    public static <T> void assertEquals(T actual, T expected, String failMessage) { getAssert().areEquals(actual, expected, failMessage); }
    public static <T> void assertEquals(T actual, T expected) { getAssert().areEquals(actual, expected); }
    public static void matches(String actual, String regEx, String failMessage) { getAssert().matches(actual, regEx, failMessage); }
    public static void matches(String actual, String regEx) { getAssert().matches(actual, regEx); }
    public static void contains(String actual, String expected, String failMessage) {
        getAssert().contains(actual, expected, failMessage);
    }
    public static void contains(String actual, String expected) {
        getAssert().contains(actual, expected);
    }
    public static void assertContains(String actual, String expected, String failMessage) {
        getAssert().contains(actual, expected, failMessage);
    }
    public static void assertContains(String actual, String expected) {
        getAssert().contains(actual, expected);
    }
    public static void isTrue(Boolean condition, String failMessage) {
        getAssert().isTrue(condition, failMessage);
    }
    public static void isTrue(Boolean condition) {
        getAssert().isTrue(condition);
    }
    public static void assertTrue(Boolean condition, String failMessage) {
        isTrue(condition, failMessage);
    }
    public static void assertTrue(Boolean condition) {
        isTrue(condition);
    }
    public static void isFalse(Boolean condition, String failMessage) {
        getAssert().isFalse(condition, failMessage);
    }
    public static void isFalse(Boolean condition) {
        getAssert().isFalse(condition);
    }
    public static void assertFalse(Boolean condition, String failMessage) {
        isFalse(condition, failMessage);
    }
    public static void assertFalse(Boolean condition) {
        isFalse(condition);
    }
    public static void isEmpty(Object obj, String failMessage) {
        getAssert().isEmpty(obj, failMessage);
    }
    public static void isEmpty(Object obj) {
        getAssert().isEmpty(obj);
    }
    public static void isNotEmpty(Object obj, String failMessage) {
        getAssert().isNotEmpty(obj, failMessage);
    }
    public static void isNotEmpty(Object obj) {
        getAssert().isNotEmpty(obj);
    }
    public static <T> void areSame(T actual, T expected, String failMessage) {
        getAssert().areSame(actual, expected, failMessage);
    }
    public static <T> void areSame(T actual, T expected) {
        getAssert().areSame(actual, expected);
    }
    public static <T> void assertSame(T actual, T expected, String failMessage) {
        getAssert().areSame(actual, expected, failMessage);
    }
    public static <T> void assertSame(T actual, T expected) {
        getAssert().areSame(actual, expected);
    }
    public static <T> void areDifferent(T actual, T expected, String failMessage) { getAssert().areDifferent(actual, expected, failMessage); }
    public static <T> void areDifferent(T actual, T expected) {
        getAssert().areDifferent(actual, expected);
    }
    public static <T> void assertNotSame(T actual, T expected, String failMessage) { getAssert().areDifferent(actual, expected, failMessage); }
    public static <T> void assertNotSame(T actual, T expected) {
        getAssert().areDifferent(actual, expected);
    }

    public static <T> void listEquals(Collection<T> actual, Collection<T> expected, String failMessage) { getAssert().listEquals(actual, expected, failMessage); }
    public static <T> void listEquals(Collection<T> actual, Collection<T> expected) { getAssert().listEquals(actual, expected); }
    public static <T> void arrayEquals(T actual, T expected, String failMessage) { getAssert().arrayEquals(actual, expected, failMessage); }
    public static <T> void arrayEquals(T actual, T expected) { getAssert().arrayEquals(actual, expected); }

    public static <T> void entityIncludeMap(MapArray<String, String> actual, T entity, String failMessage) { getAssert().entityIncludeMap(actual, entity, failMessage); }
    public static <T> void entityIncludeMap(MapArray<String, String> actual, T entity) { getAssert().entityIncludeMap(actual, entity); }
    public static <T> void entityEqualsToMap(MapArray<String, String> actual, T entity, String failMessage) { getAssert().entityEqualsToMap(actual, entity, failMessage); }
    public static <T> void entityEqualsToMap(MapArray<String, String> actual, T entity) { getAssert().entityEqualsToMap(actual, entity); }
    public static void isSortedByAsc(int[] array, String failMessage) { getAssert().isSortedByAsc(array, failMessage); }
    public static void isSortedByAsc(int[] array) { getAssert().isSortedByAsc(array); }
    public static void isSortedByDesc(int[] array, String failMessage) { getAssert().isSortedByDesc(array, failMessage); }
    public static void isSortedByDesc(int[] array) { getAssert().isSortedByDesc(array); }
    public static void isSortedByAsc(List<Integer> array, String failMessage) { getAssert().isSortedByAsc(array, failMessage); }
    public static void isSortedByAsc(List<Integer> array) { getAssert().isSortedByAsc(array); }
    public static void isSortedByDesc(List<Integer> array, String failMessage) { getAssert().isSortedByDesc(array, failMessage); }
    public static void isSortedByDesc(List<Integer> array) { getAssert().isSortedByDesc(array); }

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
    public static void assertContains(JFuncT<String> actual, String expected, String failMessage) { getAssert().contains(actual, expected, failMessage); }
    public static void assertContains(JFuncT<String> actual, String expected) { getAssert().contains(actual, expected); }
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
    public static <T> void assertSame(JFuncT<T> actual, T expected, String failMessage) { getAssert().areSame(actual, expected, failMessage); }
    public static <T> void assertSame(JFuncT<T> actual, T expected) { getAssert().areSame(actual, expected); }
    public static <T> void areDifferent(JFuncT<T> actual, T expected, String failMessage) { getAssert().areDifferent(actual, expected, failMessage); }
    public static <T> void areDifferent(JFuncT<T> actual, T expected) { getAssert().areDifferent(actual, expected); }
    public static void assertNotSame(JFuncT<Object> obj, Object obj2, String failMessage) { areDifferent(obj, obj2, failMessage); }
    public static void assertNotSame(JFuncT<Object> obj, Object obj2) {
        areDifferent(obj, obj2);
    }

    public static <T> void listEquals(JFuncT<Collection<T>> actual, Collection<T> expected, String failMessage) { getAssert().listEquals(actual, expected, failMessage); }
    public static <T> void listEquals(JFuncT<Collection<T>> actual, Collection<T> expected) { getAssert().listEquals(actual, expected); }
    public static <T> void arrayEquals(JFuncT<T> actual, T expected, String failMessage) { getAssert().arrayEquals(actual, expected, failMessage); }
    public static <T> void arrayEquals(JFuncT<T> actual, T expected) { getAssert().arrayEquals(actual, expected); }
}
