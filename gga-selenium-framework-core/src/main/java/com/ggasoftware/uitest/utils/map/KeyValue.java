package com.ggasoftware.uitest.utils.map;

/**
 * Created by Roman_Iovlev on 6/3/2015.
 */
public class KeyValue <K, V> {
    public K key;
    public V value;

    public KeyValue(K key, V value) {
        this.key = key;
        this.value = value;
    }
}
