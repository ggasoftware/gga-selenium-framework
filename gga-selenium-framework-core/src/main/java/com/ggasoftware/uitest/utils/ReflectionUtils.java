package com.ggasoftware.uitest.utils;

import java.lang.reflect.Field;
import java.util.List;

import static com.ggasoftware.uitest.utils.LinqUtils.*;

/**
 * Created by roman.i on 25.09.2014.
 */
public class ReflectionUtils {
    public static boolean isClass(Field field, Class<?> expected) throws Exception {
        return isClass(field.getType(), expected);
    }
    public static boolean isClass(Class<?> type, Class<?> expected) throws Exception {
        while (type != null && type != Object.class)
            if (type == expected) return true; else type = type.getSuperclass();
        return false;
    }
    public static boolean isInterface(Field field, Class<?> expected) throws Exception {
        return isInterface(field.getType(), expected);
    }
    public static boolean deepInterface(Class<?> type, Class<?> expected) throws Exception {
        Class<?>[] interfaces = type.getInterfaces();
        return interfaces.length != 0 && (first(interfaces, i -> i == expected) != null || first(interfaces, i -> deepInterface(i, expected)) != null);
    }
    public static boolean isInterface(Class<?> type, Class<?> expected) throws Exception {
        while (type != null && type != Object.class) {
            Class<?>[] interfaces = type.getInterfaces();
            if (interfaces.length != 0 && (first(interfaces, i -> i == expected) != null || first(interfaces, i -> deepInterface(i, expected)) != null))
                return true;
            type = type.getSuperclass();
        }
        return false;
    }

    public static List<Field> getFields(Object obj, Class<?> type) throws Exception {
        return (List<Field>) where(obj.getClass().getDeclaredFields(), field -> isClass(field, type) || isInterface(field, type));
    }

    public static Object getFieldValue(Field field, Object obj) throws IllegalAccessException {
        field.setAccessible(true);
        return field.get(obj);
    }
}
