package com.ggasoftware.jdi_ui_tests.core.utils.map;


import com.ggasoftware.jdi_ui_tests.core.utils.common.LinqUtils;
import com.ggasoftware.jdi_ui_tests.core.utils.linqInterfaces.JActionTT;
import com.ggasoftware.jdi_ui_tests.core.utils.linqInterfaces.JFuncTT;
import com.ggasoftware.jdi_ui_tests.core.utils.linqInterfaces.JFuncTTT;
import com.ggasoftware.jdi_ui_tests.core.utils.pairs.Pair;

import java.util.*;
import java.util.stream.Collectors;

import static com.ggasoftware.jdi_ui_tests.core.utils.common.PrintUtils.print;
import static com.ggasoftware.jdi_ui_tests.core.utils.usefulUtils.TryCatchUtil.throwRuntimeException;
import static java.util.stream.Collectors.toList;

/**
 * Created by Roman_Iovlev on 6/3/2015.
 */
public class MapArray<K, V> implements Collection<Pair<K,V>>, Cloneable {
    public List<Pair<K,V>> pairs;

    public MapArray() { pairs = new ArrayList<>(); }
    public MapArray(K key, V value) {
        this();
        add(key, value);
    }
    public <T> MapArray(Collection<T> collection, JFuncTT<T, K> key, JFuncTT<T, V> value) {
        this();
        for (T t : collection)
            add(key.invoke(t), value.invoke(t));
    }
    public MapArray(Collection<K> collection, JFuncTT<K, V> value) {
        this();
        for (K k : collection)
            add(k, value.invoke(k));
    }
    public <T> MapArray(T[] array, JFuncTT<T, K> key, JFuncTT<T, V> value) {
        this();
        for (T t : array)
            add(key.invoke(t), value.invoke(t));
    }
    public MapArray(K[] array, JFuncTT<K, V> value) {
        this();
        for (K k : array)
            add(k, value.invoke(k));
    }
    public MapArray(int count, JFuncTT<Integer, K> key, JFuncTT<Integer, V> value) {
        this();
        for (int i = 0; i < count; i++)
            add(key.invoke(i), value.invoke(i));
    }

    public MapArray(MapArray<K, V> mapArray) {
        this();
        addAll(mapArray.stream().collect(toList()));
    }
    public MapArray(Object[][] objects) {
        this();
        add(objects);
    }

    public static <T> MapArray<Integer, T> toMapArray(Collection<T> collection) {
        MapArray<Integer, T> mapArray = new MapArray<>();
        int i = 0;
        for (T t : collection)
            mapArray.add(i++, t);
        return mapArray;
    }
    public static <T> MapArray<Integer, T> toMapArray(T[] array) {
        Set<T> mySet = new HashSet<>();
        Collections.addAll(mySet, array);
        return toMapArray(mySet);
    }
    public <KResult, VResult> MapArray<KResult, VResult> toMapArray(
            JFuncTTT<K, V, KResult> key, JFuncTTT<K, V, VResult> value) {
        MapArray<KResult, VResult> result = new MapArray<>();
        for (Pair<K,V> pair : pairs)
            result.add(key.invoke(pair.key, pair.value), value.invoke(pair.key, pair.value));
        return result;
    }
    public <VResult> MapArray<K, VResult> toMapArray(
            JFuncTT<V, VResult> value) {
        MapArray<K, VResult> result = new MapArray<>();
        for(Pair<K,V> pair : pairs)
            result.add(pair.key, value.invoke(pair.value));
        return result;
    }
    public boolean add(K key, V value) {
        if (haveKey(key)) return false;
        pairs.add(new Pair<>(key, value));
        return true;
    }
    public void add(Object[][] pairs) {
        for (Object[] pair : pairs)
            if (pair.length == 2)
                add((K) pair[0], (V) pair[1]);
    }
    public void addOrReplace(K key, V value) {
        if (haveKey(key))
            removeByKey(key);
        add(key, value);
    }

    public void addOrReplace(Object[][] pairs) {
            for (Object[] pair : pairs)
                if (pair.length == 2)
                    addOrReplace((K) pair[0], (V) pair[1]);
    }
    private boolean haveKey(K key) {
        return keys().contains(key);
    }

    public boolean addFirst(K key, V value) {
        if (haveKey(key)) return false;
        List<Pair<K,V>> result = new ArrayList<>();
        result.add(new Pair<>(key, value));
        result.addAll(pairs);
        pairs = result;
        return true;
    }
    public V get(K key) {
        Pair<K, V> first = null;
        try { first = LinqUtils.first(pairs, pair -> pair.key.equals(key));
        } catch (Exception ignore) {}
        return (first != null) ? first.value : null;
    }
    public Pair<K,V> get(int index) {
        if (index < 0) index = pairs.size() + index;
        if (index < 0) return null;
        return (pairs.size() > index)
                ? pairs.get(index)
                : null;
    }
    public Pair<K,V> getFromEnd(int index) {
        return get(size() - index - 1);
    }
    public K key(int index) {
        return get(index).key;
    }
    public V value(int index) {
        return get(index).value;
    }
    public Collection<K> keys() {
        return LinqUtils.select(pairs, pair -> pair.key);
    }
    public Collection<V> values() {
        return LinqUtils.select(pairs, pair -> pair.value);
    }
    public Collection<V> values(JFuncTT<V, Boolean> condition) {
        return LinqUtils.where(values(), condition);
    }

    public int size() { return pairs.size(); }
    public int count() {
        return size();
    }
    public boolean isEmpty() { return size() == 0; }
    public boolean any() { return size() > 0; }

    public Pair<K,V> first() {
        return get(0);
    }
    public Pair<K,V> last() {
        return getFromEnd(0);
    }

    public MapArray<K,V> revert() {
        List<Pair<K,V>> result = new ArrayList<>();
        for(int i = size() - 1; i >= 0; i--)
            result.add(get(i));
        return this;
    }

    public boolean contains(Object o) {
        return values().contains(o);
    }

    public Iterator<Pair<K, V>> iterator() {
        return pairs.iterator();
    }

    public Object[] toArray() {
        return pairs.toArray();
    }

    public <T> T[] toArray(T[] a) {
        return pairs.toArray(a);
    }

    public boolean add(Pair<K, V> kv) {
        return pairs.add(kv);
    }

    public boolean remove(Object o) {
        boolean isRemoved = false;
        for (Pair<K, V> kv : pairs)
            if (kv.equals(o)) {
                pairs.remove(kv);
                isRemoved = true;
            }
        return isRemoved;
    }

    public void removeByKey(K key) {
        pairs.remove(LinqUtils.firstIndex(pairs, pair -> pair.key.equals(key)));
    }
    public void removeAllValues(V value) {
        LinqUtils.where(pairs, p -> p.value.equals(value)).forEach(pairs::remove);
    }
    public boolean containsAll(Collection<?> c) {
        for(Object o : c)
            if (!contains(o))
                return false;
        return true;
    }

    public boolean addAll(Collection<? extends Pair<K, V>> c) {
        for(Pair<K, V> pair : c)
            add(pair);
        return true;
    }

    public boolean removeAll(Collection<?> c) {
        for(Object o : c)
            if (!remove(o))
                return false;
        return true;
    }

    public boolean retainAll(Collection<?> c) {
        for(Pair pair : pairs)
            if (!c.contains(pair))
                if (!remove(pair))
                    return false;
        return true;
    }

    public void clear() {
        pairs.clear();
    }

    @Override
    public String toString() {
        return print(LinqUtils.select(pairs, pair -> pair.key + ":" + pair.value));
    }

    @Override
    public MapArray<K, V> clone() {
        return new MapArray<>(this);
    }
    public MapArray<K, V> copy() { return clone(); }

    public <T1> List<T1> select(JFuncTTT<K,V, T1> func) {
        try {
            return pairs.stream()
                    .map(pair -> func.invoke(pair.key, pair.value))
                    .collect(Collectors.toList());
        } catch (Exception ignore) { throwRuntimeException(ignore); return new ArrayList<>(); }
    }

    public MapArray<K, V> where(JFuncTTT<K, V, Boolean> func) {
        try {
            MapArray<K, V> result = new MapArray<>();
            for(Pair<K,V> pair : pairs)
                if (func.invoke(pair.key, pair.value))
                    result.add(pair);
            return result;
        } catch (Exception ignore) { throwRuntimeException(ignore); return null; }
    }
    public V first(JFuncTTT<K,V, Boolean> func) {
        try {
            for(Pair<K,V> pair : pairs)
                if (func.invoke(pair.key, pair.value))
                    return pair.value;
            return null;
        } catch (Exception ignore) { throwRuntimeException(ignore); return null; }
    }
    public void foreach(JActionTT<K, V> action) {
        try {
            for(Pair<K,V> pair : pairs)
                action.invoke(pair.key, pair.value);
        } catch (Exception ignore) { throwRuntimeException(ignore); }
    }
    public <R> List<R> selectMany(JFuncTTT<K, V, List<R>> func) {
        try {
            List<R> result = new ArrayList<>();
            for(Pair<K,V> pair : pairs)
                result.addAll(func.invoke(pair.key, pair.value));
            return result;
        } catch (Exception ignore) { throwRuntimeException(ignore); return null;}
    }

}
