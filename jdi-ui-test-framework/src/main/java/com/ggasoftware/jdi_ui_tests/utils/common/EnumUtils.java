package com.ggasoftware.jdi_ui_tests.utils.common;

import com.ggasoftware.jdi_ui_tests.utils.usefulUtils.TryCatchUtil;

import java.lang.reflect.Field;
import java.util.List;

import static edu.emory.mathcs.backport.java.util.Arrays.asList;

/**
 * Created by Roman_Iovlev on 7/8/2015.
 */
public class EnumUtils {
    public static String getEnumValue(Enum enumWithValue) {
        Field field;
        try { field = enumWithValue.getClass().getField("value");
            if (field.getType() != String.class)
                throw new Exception("Can't get Value from enum");
        } catch (Exception ex) { return enumWithValue.toString(); }
        return TryCatchUtil.tryGetResult(() -> (String) field.get(enumWithValue));
    }
    public static <T extends Enum> List<T> getAllEnumValues(T enumValue) {
        return (List<T>) asList(enumValue.getDeclaringClass().getEnumConstants());
    }
}
