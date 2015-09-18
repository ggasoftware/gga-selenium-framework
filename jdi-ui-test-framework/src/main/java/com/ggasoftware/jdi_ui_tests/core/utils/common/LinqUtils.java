package com.ggasoftware.jdi_ui_tests.core.utils.common;


import com.ggasoftware.jdi_ui_tests.core.utils.linqInterfaces.JActionT;
import com.ggasoftware.jdi_ui_tests.core.utils.linqInterfaces.JFuncTT;
import com.ggasoftware.jdi_ui_tests.core.utils.map.MapArray;
import com.ggasoftware.jdi_ui_tests.core.utils.pairs.Pair;

import java.util.*;

import static java.util.Arrays.asList;

/**
 * Created by roman.i on 30.09.2014.
 */
public class LinqUtils {
    public static <T> List<T> copyList(Iterable<T> list) {
        List<T> result = new ArrayList<>();
        for (T el : list)
            result.add(el);
        return result;
    }
    public static <T, T1> List<T1> select(Iterable<T> list, JFuncTT<T, T1> func) {
        if (list == null)
            throw new RuntimeException("Can't do select. Collection is Null");
        try {
            List<T1> result = new ArrayList<>();
            for (T el : list)
                result.add(func.invoke(el));
            return result;
        } catch (Throwable ex) { throw new RuntimeException("Can't do select. Exception: " + ex.getMessage()); }
    }
    public static <T, T1> List<T1> select(T[] array, JFuncTT<T, T1> func){
        return select(asList(array), func);
    }
    public static <T, T1, T2> List<T2> selectMap(Map<T, T1> map, JFuncTT<Map.Entry<T, T1>, T2> func) {
        if (map == null)
            throw new RuntimeException("Can't do selectMap. Collection is Null");
        try {
            List<T2> result = new ArrayList<>();
            for(Map.Entry<T, T1> el : map.entrySet())
                result.add(func.invoke(el));
            return result;
        } catch (Throwable ex) { throw new RuntimeException("Can't do select. Exception: " + ex.getMessage()); }
    }
    public static <T, T1, T2> Map<T, T2> select(Map<T, T1> map, JFuncTT<T1, T2> func) {
        if (map == null)
            throw new RuntimeException("Can't do select. Collection is Null");
        try {
            Map<T, T2> result = new HashMap<>();
            for(Map.Entry<T, T1> el : map.entrySet())
                result.put(el.getKey(), func.invoke(el.getValue()));
            return result;
        } catch (Throwable ex) { throw new RuntimeException("Can't do select. Exception: " + ex.getMessage()); }
    }

    public static <T> List<T> where(Iterable<T> list, JFuncTT<T, Boolean> func) {
        if (list == null)
            throw new RuntimeException("Can't do where. Collection is Null");
        try {
            List<T> result = new ArrayList<>();
            for(T el : list)
                if (func.invoke(el))
                result.add(el);
            return result;
        } catch (Throwable ex) { throw new RuntimeException("Can't do where. Exception: " + ex.getMessage()); }
    }
    public static <T> List<T> where(T[] list, JFuncTT<T, Boolean> func) {
        return where(asList(list), func);
    }
    public static <T, T1> Map<T, T1> where(Map<T, T1> map, JFuncTT<Map.Entry<T, T1>, Boolean> func) {
        if (map == null)
            throw new RuntimeException("Can't do where. Collection is Null");
        try {
            Map<T, T1> result = new HashMap<>();
            for(Map.Entry<T, T1> el : map.entrySet())
                if (func.invoke(el))
                    result.put(el.getKey(), el.getValue());
            return result;
        } catch (Throwable ex) { throw new RuntimeException("Can't do where. Exception: " + ex.getMessage()); }
    }

    public static <T> void foreach(Iterable<T> list, JActionT<T> action) {
        if (list == null)
            throw new RuntimeException("Can't do foreach. Collection is Null");
        try {
            for(T el : list)
                action.invoke(el);
        } catch (Throwable ex) { throw new RuntimeException("Can't do foreach. Exception: " + ex.getMessage()); }
    }
    public static <T> void foreach(T[] list, JActionT<T> action) {
        foreach(asList(list), action);
    }
    public static <T, T1> void foreach(Map<T, T1> map, JActionT<Map.Entry<T, T1>> action) {
        if (map == null)
            throw new RuntimeException("Can't do foreach. Collection is Null");
        try {
            for (Map.Entry<T, T1> entry : map.entrySet())
                action.invoke(entry);
        } catch (Throwable ex) { throw new RuntimeException("Can't do foreach. Exception: " + ex.getMessage()); }
    }
    public static <T> T first(Iterable<T> list) {
        if (list == null)
            throw new RuntimeException("Can't do first. Collection is Null");
        for(T el : list)
            return el;
        return null;
    }
    public static <T> T first(T[] list) {
        return first(asList(list));
    }
    public static <T, T1> T1 first(Map<T, T1> map) {
        if (map == null)
            throw new RuntimeException("Can't do first. Collection is Null");
        for (Map.Entry<T, T1> el : map.entrySet())
            return el.getValue();
        return null;
    }
    public static <T> T first(Iterable<T> list, JFuncTT<T, Boolean> func) {
        if (list == null)
            throw new RuntimeException("Can't do first. Collection is Null");
        try {
            for(T el : list)
                if (func.invoke(el))
                    return el;
        } catch (Throwable ex) { throw new RuntimeException("Can't do first. Exception: " + ex.getMessage()); }
        return null;
    }
    public static <T> boolean any(Iterable<T> list, JFuncTT<T, Boolean> func) {
        return first(list, func) == null;
    }
    public static <T> int firstIndex(List<T> list, JFuncTT<T, Boolean> func)  {
        if (list == null)
            throw new RuntimeException("Can't do firstIndex. Collection is Null");
        try {
            for(int i = 0; i< list.size(); i++)
                if (func.invoke(list.get(i)))
                    return i;
        } catch (Throwable ex) { throw new RuntimeException("Can't do firstIndex. Exception: " + ex.getMessage()); }
        return -1;
    }
    public static <T> int firstIndex(T[] array, JFuncTT<T, Boolean> func) {
        if (array == null)
            throw new RuntimeException("Can't do firstIndex. Collection is Null");
        try {
            for(int i = 0; i< array.length; i++)
                if (func.invoke(array[i]))
                    return i;
        } catch (Throwable ex) { throw new RuntimeException("Can't do firstIndex. Exception: " + ex.getMessage()); }
        return -1;
    }
    public static <T> T first(T[] list, JFuncTT<T, Boolean> func) {
        return first(asList(list), func);
    }
    public static <T, T1> T1 first(Map<T, T1> map, JFuncTT<T, Boolean> func) {
        if (map == null)
            throw new RuntimeException("Can't do first. Collection is Null");
        try {
            for (Map.Entry<T, T1> el : map.entrySet())
                if (func.invoke(el.getKey()))
                    return el.getValue();
        } catch (Throwable ex) { throw new RuntimeException("Can't do first. Exception: " + ex.getMessage()); }
        return null;
    }
    public static <K, V> V first(MapArray<K, V> map, JFuncTT<K, Boolean> func) {
        if (map == null)
            throw new RuntimeException("Can't do first. Collection is Null");
        try {
            for (Pair<K, V> pair : map.pairs)
                if (func.invoke(pair.key))
                    return pair.value;
        } catch (Throwable ex) { throw new RuntimeException("Can't do first. Exception: " + ex.getMessage()); }
        return null;
    }
    public static <T> T last(Iterable<T> list) {
        if (list == null)
            throw new RuntimeException("Can't do last. Collection is Null");
        T result = null;
        for(T el : list)
            result = el;
        return result;
    }
    public static <T> T last(T[] list) {
        return last(asList(list));
    }
    public static <T> T last(Iterable<T> list, JFuncTT<T, Boolean> func) {
        if (list == null)
            throw new RuntimeException("Can't do last. Collection is Null");
        T result = null;
        try {
            for(T el : list)
                if (func.invoke(el))
                    result = el;
        } catch (Throwable ex) { throw new RuntimeException("Can't do last. Exception: " + ex.getMessage()); }
        return result;
    }
    public static <T> T last(T[] list, JFuncTT<T, Boolean> func) {
        return last(asList(list), func);
    }

    public static String[] toStringArray(Collection<String> collection) {
        if (collection == null)
            throw new RuntimeException("Can't do toStringArray. Collection is Null");
        return collection.toArray(new String[collection.size()]);
    }
    public static Integer[] toIntArray(Collection<Integer> collection) {
        if (collection == null)
            throw new RuntimeException("Can't do toIntArray. Collection is Null");
        return collection.toArray(new Integer[collection.size()]);
    }
    public static int index(String[] array, String value) {
        if (array == null)
            throw new RuntimeException("Can't do index. Collection is Null");
        for (int i = 0; i < array.length; i++)
            if (array[i].equals(value))
                return i;
        return -1;
    }
}
