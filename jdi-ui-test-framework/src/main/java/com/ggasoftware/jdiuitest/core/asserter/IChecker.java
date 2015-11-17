/*
 * Copyright 2004-2016 EPAM Systems
 *
 * This file is part of JDI project.
 *
 * JDI is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * JDI is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; 
 * without even the implied warranty ofMERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. 
 * See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with JDI. If not, see <http://www.gnu.org/licenses/>.
 */
package com.ggasoftware.jdiuitest.core.asserter;

import com.ggasoftware.jdiuitest.core.utils.common.LinqUtils;
import com.ggasoftware.jdiuitest.core.utils.linqinterfaces.JFuncT;
import com.ggasoftware.jdiuitest.core.utils.map.MapArray;

import java.util.Collection;
import java.util.List;

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

    default <T> void arrayEquals(T actual, T expected) {
        arrayEquals(actual, expected, null);
    }

    <T> void entityIncludeMap(MapArray<String, String> actual, T entity, String failMessage);

    default <T> void entityIncludeMap(MapArray<String, String> actual, T entity) {
        entityEqualsToMap(actual, entity, null);
    }

    <T> void entityEqualsToMap(MapArray<String, String> actual, T entity, String failMessage);

    default <T> void entityEqualsToMap(MapArray<String, String> actual, T entity) {
        entityEqualsToMap(actual, entity, null);
    }

    void isSortedByAsc(int[] array, String failMessage);

    default void isSortedByAsc(int[] array) {
        isSortedByAsc(array, null);
    }

    default void isSortedByAsc(List<Integer> array, String failMessage) {
        isSortedByAsc(LinqUtils.toIntArray(array), failMessage);
    }

    default void isSortedByAsc(List<Integer> array) {
        isSortedByAsc(LinqUtils.toIntArray(array));
    }

    void isSortedByDesc(int[] array, String failMessage);

    default void isSortedByDesc(int[] array) {
        isSortedByDesc(array, null);
    }

    default void isSortedByDesc(List<Integer> array, String failMessage) {
        isSortedByDesc(LinqUtils.toIntArray(array), failMessage);
    }

    default void isSortedByDesc(List<Integer> array) {
        isSortedByDesc(LinqUtils.toIntArray(array));
    }

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

    void isEmpty(JFuncT<Object> obj);

    void isNotEmpty(JFuncT<Object> obj, String failMessage);

    void isNotEmpty(JFuncT<Object> obj);

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
