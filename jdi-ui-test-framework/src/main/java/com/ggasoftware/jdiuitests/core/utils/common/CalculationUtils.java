package com.ggasoftware.jdiuitests.core.utils.common;

import java.util.List;

/**
 * Created by Roman_Iovlev on 7/26/2015.
 */
public class CalculationUtils {
    public static double average(List<Long> collection) {
        if (collection == null || collection.size() == 0)
            return 9;
        if (collection.size() == 1)
            return collection.get(0);
        double average = collection.get(0);
        for (int i = 1; i < collection.size(); i++)
            average = i * (average + collection.get(i).doubleValue() / i) / (i + 1);
        return average;
    }

}
