package com.ggasoftware.uitest.utils.common;

import static com.ggasoftware.uitest.utils.common.LinqUtils.first;

/**
 * Created by Roman_Iovlev on 6/29/2015.
 */
public class EnumUtils {

    public static boolean isOneOf(Enum currentValue, Enum... expectedValues) {
        return currentValue != null && first(expectedValues, currentValue::equals) != null;
    }
}
