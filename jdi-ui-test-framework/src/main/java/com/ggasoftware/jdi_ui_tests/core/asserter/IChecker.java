package com.ggasoftware.jdi_ui_tests.core.asserter;

import java.util.Collection;

/**
 * Created by Roman_Iovlev on 8/28/2015.
 */
public interface IChecker {
    <T> void areEquals(T obj, T obj2, String failMessage);
    <T> void areEquals(T obj, T obj2);
    void matches(String str, String regEx, String failMessage);
    void matches(String str, String regEx);
    void contains(String str, String str2, String failMessage);
    void contains(String str, String str2);
    void isTrue(Boolean condition, String failMessage);
    void isTrue(Boolean condition);
    void isFalse(Boolean condition, String failMessage);
    void isFalse(Boolean condition);
    void isEmpty(Object obj, String failMessage);
    void isEmpty(Object obj);
    void isNotEmpty(Object obj, String failMessage);
    void isNotEmpty(Object obj);
    <T> void areSame(T obj, T obj2, String failMessage);
    <T> void areSame(T obj, T obj2);
    <T> void areDifferent(T obj, T obj2, String failMessage);
    <T> void areDifferent(T obj, T obj2);
    <T> void listEquals(Collection<T> collection, Collection<T> collection2, String failMessage);
    <T> void listEquals(Collection<T> collection, Collection<T> collection2);
    <T> void arrayEquals(T array, T array2, String failMessage);
    <T> void arrayEquals(T array, T array2);
    <T> BaseChecker.ListChecker eachElementOf(Collection<T> list);
    <T> BaseChecker.ListChecker eachElementOf(T[] array);
}
