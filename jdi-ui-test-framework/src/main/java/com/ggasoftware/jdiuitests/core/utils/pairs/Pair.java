package com.ggasoftware.jdiuitests.core.utils.pairs;

/**
 * Created by 12345 on 30.09.2014.
 */
public class Pair<TFirst, TSecond> {
    public TFirst key;
    public TSecond value;

    public Pair(TFirst value1, TSecond value2) {
        key = value1;
        value = value2;
    }

    public boolean equalTo(Pair<TFirst, TSecond> other) {
        return other != null && (this == other || key == other.key && value == other.value);
    }

    @Override
    public int hashCode() {
        return key.hashCode() ^ value.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null)
            return false;
        if (!(o instanceof PairString))
            return false;
        Pair<TFirst, TSecond> pairo = (Pair<TFirst, TSecond>) o;
        return this.key.equals(pairo.key) &&
                this.value.equals(pairo.value);
    }

    @Override
    public String toString() {
        return key + ":" + value;
    }
}
