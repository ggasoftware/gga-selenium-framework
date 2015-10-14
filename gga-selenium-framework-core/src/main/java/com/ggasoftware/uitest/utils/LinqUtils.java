package com.ggasoftware.uitest.utils;


import com.ggasoftware.uitest.control.base.map.MapArray;
import com.ggasoftware.uitest.control.base.pairs.Pair;
import com.ggasoftware.uitest.utils.linqInterfaces.JActionT;
import com.ggasoftware.uitest.utils.linqInterfaces.JFuncTT;

import java.util.*;

import static java.util.Arrays.asList;

/**
 * Created by roman.i on 30.09.2014.
 */
public class LinqUtils {
    public static <T, T1> Collection<T1> select(Iterable<T> list, JFuncTT<T, T1> func) {
        try {
            List<T1> result = new ArrayList<>();
            for (T el : list)
                result.add(func.invoke(el));
            return result;
        } catch (Exception | AssertionError ignore) {
            return new ArrayList<>();
        }
    }

    public static <T, T1> Collection<T1> select(T[] list, JFuncTT<T, T1> func) {
        return select(asList(list), func);
    }

    public static <T, T1, T2> Collection<T2> selectMap(Map<T, T1> map, JFuncTT<Map.Entry<T, T1>, T2> func) {
        try {
            List<T2> result = new ArrayList<>();
            for (Map.Entry<T, T1> el : map.entrySet())
                result.add(func.invoke(el));
            return result;
        } catch (Exception | AssertionError ignore) {
            return new ArrayList<>();
        }
    }

    public static <T, T1, T2> Map<T, T2> select(Map<T, T1> map, JFuncTT<T1, T2> func) {
        try {
            Map<T, T2> result = new HashMap<>();
            for (Map.Entry<T, T1> el : map.entrySet())
                result.put(el.getKey(), func.invoke(el.getValue()));
            return result;
        } catch (Exception | AssertionError ignore) {
            return new HashMap<>();
        }
    }

    public static <T> Collection<T> where(Iterable<T> list, JFuncTT<T, Boolean> func) {
        try {
            List<T> result = new ArrayList<>();
            for (T el : list)
                if (func.invoke(el))
                    result.add(el);
            return result;
        } catch (Exception | AssertionError ignore) {
            return new ArrayList<>();
        }
    }

    public static <T> Collection<T> where(T[] list, JFuncTT<T, Boolean> func) {
        return where(asList(list), func);
    }

    public static <T, T1> Map<T, T1> where(Map<T, T1> map, JFuncTT<Map.Entry<T, T1>, Boolean> func) {
        try {
            Map<T, T1> result = new HashMap<>();
            for (Map.Entry<T, T1> el : map.entrySet())
                if (func.invoke(el))
                    result.put(el.getKey(), el.getValue());
            return result;
        } catch (Exception | AssertionError ignore) {
            return new HashMap<>();
        }
    }

    public static <T> void foreach(Iterable<T> list, JActionT<T> action) {
        for (T el : list)
            action.invoke(el);
    }

    public static <T> void foreach(T[] list, JActionT<T> action) {
        foreach(asList(list), action);
    }

    public static <T, T1> void foreach(Map<T, T1> map, JActionT<Map.Entry<T, T1>> action) {
        try {
            for (Map.Entry<T, T1> entry : map.entrySet())
                action.invoke(entry);
        } catch (Exception | AssertionError ignore) {
        }
    }

    public static <T> T first(Iterable<T> list) {
        for (T el : list)
            return el;
        return null;
    }

    public static <T> T first(T[] list) {
        return first(asList(list));
    }

    public static <T, T1> T1 first(Map<T, T1> map) {
        for (Map.Entry<T, T1> el : map.entrySet())
            return el.getValue();
        return null;
    }

    public static <T> T first(Iterable<T> list, JFuncTT<T, Boolean> func) {
        try {
            for (T el : list)
                if (func.invoke(el))
                    return el;
        } catch (Exception | AssertionError ignore) {
        }
        return null;
    }

    public static <T> int firstIndex(List<T> list, JFuncTT<T, Boolean> func) {
        try {
            for (int i = 0; i < list.size(); i++)
                if (func.invoke(list.get(i)))
                    return i;
        } catch (Exception | AssertionError ignore) {
        }
        return -1;
    }

    public static <T> int firstIndex(T[] array, JFuncTT<T, Boolean> func) {
        try {
            for (int i = 0; i < array.length; i++)
                if (func.invoke(array[i]))
                    return i;
        } catch (Exception | AssertionError ignore) {
        }
        return -1;
    }

    public static <T> T first(T[] list, JFuncTT<T, Boolean> func) {
        return first(asList(list), func);
    }

    public static <T, T1> T1 first(Map<T, T1> map, JFuncTT<T, Boolean> func) {
        try {
            for (Map.Entry<T, T1> el : map.entrySet())
                if (func.invoke(el.getKey()))
                    return el.getValue();
        } catch (Exception | AssertionError ignore) {
        }
        return null;
    }

    public static <K, V> V first(MapArray<K, V> map, JFuncTT<K, Boolean> func) {
        try {
            for (Pair<K, V> pair : map.pairs)
                if (func.invoke(pair.key))
                    return pair.value;
        } catch (Exception | AssertionError ignore) {
        }
        return null;
    }

    public static <T> T last(Iterable<T> list) {
        T result = null;
        for (T el : list)
            result = el;
        return result;
    }

    public static <T> T last(T[] list) {
        return last(asList(list));
    }

    public static <T> T last(Iterable<T> list, JFuncTT<T, Boolean> func) {
        T result = null;
        try {
            for (T el : list)
                if (func.invoke(el))
                    result = el;
        } catch (Exception | AssertionError ignore) {
        }
        return result;
    }

    public static <T> T last(T[] list, JFuncTT<T, Boolean> func) {
        return last(asList(list), func);
    }

    public static String[] toStringArray(Collection<String> collection) {
        return collection.toArray(new String[collection.size()]);
    }
}
