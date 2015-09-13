package com.ggasoftware.uitest.utils;

import java.lang.reflect.Field;

import static com.ggasoftware.uitest.control.base.usefulUtils.TryCatchUtil.tryGetResult;

/**
 * Created by Roman_Iovlev on 7/8/2015.
 */
public class EnumUtils {
    public static String getEnumValue(Enum enumWithValue) {
        Field field;
        try { field = enumWithValue.getClass().getField("value");
            if (field.getType() != String.class)
                throw new Exception("Can't get Value from enum");
        } catch (Exception|AssertionError ex) { return enumWithValue.toString(); }
        return tryGetResult(() -> (String) field.get(enumWithValue));
    }
}
