package com.ggasoftware.jdi_ui_tests.utils.common;

import com.ggasoftware.jdi_ui_tests.utils.usefulUtils.TryCatchUtil;

import java.lang.reflect.Field;

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
}
