package com.ggasoftware.jdiuitests.core.asserter;

import com.ggasoftware.jdiuitests.core.utils.linqInterfaces.JFuncT;
import com.ggasoftware.jdiuitests.core.utils.map.MapArray;

import java.util.Collection;
import java.util.List;

import static com.ggasoftware.jdiuitests.core.utils.common.LinqUtils.toIntArray;

/**
 * Created by Roman_Iovlev on 8/28/2015.
 */
public interface IChecker {
    <T> void areEquals(T actual, T expected, String failMessage);
    <T> void areEquals(T actual, T expected);
    void matches(String actual, String regEx, String failMessage);
    void matches(String actual, String regEx);
    void contains(String actual, String expected, String failMessage);
    void contains(String actual, String expected);
    void isTrue(Boolean condition, String failMessage);
    void isTrue(Boolean condition);
    void isFalse(Boolean condition, String failMessage);
    void isFalse(Boolean condition);
    void isEmpty(Object obj, String failMessage);
    void isEmpty(Object obj);
    void isNotEmpty(Object obj, String failMessage);
    void isNotEmpty(Object obj);
    <T> void areSame(T actual, T expected, String failMessage);
    <T> void areSame(T actual, T expected);
    <T> void areDifferent(T actual, T expected, String failMessage);
    <T> void areDifferent(T actual, T expected);
    <T> void listEquals(Collection<T> actual, Collection<T> expected, String failMessage);
    <T> void listEquals(Collection<T> actual, Collection<T> expected);
    <T> void arrayEquals(T actual, T expected, String failMessage);
    default <T> void arrayEquals(T actual, T expected) { arrayEquals(actual, expected, null); }
    <T> void entityIncludeMap(MapArray<String, String> actual, T entity, String failMessage);
    default <T> void entityIncludeMap(MapArray<String, String> actual, T entity) {
        entityEqualsToMap(actual, entity, null); }
    <T> void entityEqualsToMap(MapArray<String, String> actual, T entity, String failMessage);
    default <T> void entityEqualsToMap(MapArray<String, String> actual, T entity) {
        entityEqualsToMap(actual, entity, null); }
    void isSortedByAsc(int[] array, String failMessage);
    default void isSortedByAsc(int[] array) { isSortedByAsc(array, null); }
    default void isSortedByAsc(List<Integer> array, String failMessage) { isSortedByAsc(toIntArray(array), failMessage); }
    default void isSortedByAsc(List<Integer> array) { isSortedByAsc(toIntArray(array)); }

    void isSortedByDesc(int[] array, String failMessage);
    default void isSortedByDesc(int[] array) { isSortedByDesc(array, null); }
    default void isSortedByDesc(List<Integer> array, String failMessage) { isSortedByDesc(toIntArray(array), failMessage); }
    default void isSortedByDesc(List<Integer> array) { isSortedByDesc(toIntArray(array)); }

    <T> void areEquals(JFuncT<T> actual, T expected, String failMessage);
    <T> void areEquals(JFuncT<T> actual, T expected);
    void matches(JFuncT<String> actual, String regEx, String failMessage);
    void matches(JFuncT<String> actual, String regEx);
    void contains(JFuncT<String> actual, String expected, String failMessage);
    void contains(JFuncT<String> actual, String expected);
    void isTrue(JFuncT<Boolean> condition, String failMessage);
    void isTrue(JFuncT<Boolean> condition);
    void isFalse(JFuncT<Boolean> condition, String failMessage);
    void isFalse(JFuncT<Boolean> condition);
    void isEmpty(JFuncT<Object> obj, String failMessage);
    void isEmpty(JFuncT<Object>  obj);
    void isNotEmpty(JFuncT<Object>  obj, String failMessage);
    void isNotEmpty(JFuncT<Object>  obj);
    <T> void areSame(JFuncT<T> actual, T expected, String failMessage);
    <T> void areSame(JFuncT<T> actual, T expected);
    <T> void areDifferent(JFuncT<T> actual, T expected, String failMessage);
    <T> void areDifferent(JFuncT<T> actual, T expected);
    <T> void listEquals(JFuncT<Collection<T>> actual, Collection<T> expected, String failMessage);
    <T> void listEquals(JFuncT<Collection<T>> actual, Collection<T> expected);
    <T> void arrayEquals(JFuncT<T> actual, T expected, String failMessage);
    <T> void arrayEquals(JFuncT<T> actual, T expected);

    <T> BaseChecker.ListChecker eachElementOf(Collection<T> list);
    <T> BaseChecker.ListChecker eachElementOf(T[] array);
}
