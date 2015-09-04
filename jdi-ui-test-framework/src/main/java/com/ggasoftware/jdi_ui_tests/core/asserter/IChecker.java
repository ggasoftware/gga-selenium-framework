package com.ggasoftware.jdi_ui_tests.core.asserter;

import com.ggasoftware.jdi_ui_tests.core.utils.linqInterfaces.JFuncT;

import java.util.Collection;

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
    <T> void arrayEquals(T actual, T expected);

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
