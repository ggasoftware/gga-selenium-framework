package com.epam.ui_test_framework.utils.pairs;

/**
 * Created by 12345 on 30.09.2014.
 */
public class Pair<TValue1, TValue2> {
    public TValue1 key;
    public TValue2 value;

    public Pair(TValue1 value1, TValue2 value2)
    {
        key = value1;
        value = value2;
    }

    public boolean equals(Pair<TValue1, TValue2> other) {
        return other != null && (this == other || key == other.key && value == other.value);
    }

    @Override
    public int hashCode() { return key.hashCode() ^ value.hashCode(); }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (!(o instanceof PairString)) return false;
        Pair<TValue1, TValue2> pairo = (Pair<TValue1, TValue2>) o;
        return this.key.equals(pairo.key) &&
                this.value.equals(pairo.value);
    }
}
