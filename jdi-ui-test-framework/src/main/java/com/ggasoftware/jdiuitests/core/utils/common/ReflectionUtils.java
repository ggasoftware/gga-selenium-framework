package com.ggasoftware.jdiuitests.core.utils.common;

import com.ggasoftware.jdiuitests.implementation.selenium.elements.BaseElement;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.String.format;
import static java.lang.reflect.Modifier.isStatic;

/**
 * Created by roman.i on 25.09.2014.
 */
public class ReflectionUtils {
    public static boolean isClass(Field field, Class<?> expected) {
        return isClass(field.getType(), expected);
    }
    public static boolean isClass(Class<?> type, Class<?> expected)  {
        if (expected == Object.class) return true;
        while (type != null && type != Object.class)
            if (type == expected) return true;
            else type = type.getSuperclass();
        return false;
    }
    public static boolean isInterface(Field field, Class<?> expected)  {
        return isInterface(field.getType(), expected);
    }
    public static boolean deepInterface(Class<?> type, Class<?> expected)  {
        Class<?>[] interfaces = type.getInterfaces();
        return interfaces.length != 0 && (LinqUtils.first(interfaces, i -> i == expected) != null || LinqUtils.first(interfaces, i -> deepInterface(i, expected)) != null);
    }
    public static boolean isInterface(Class<?> type, Class<?> expected)  {
        while (type != null && type != Object.class) {
            Class<?>[] interfaces = type.getInterfaces();
            if (interfaces.length != 0 && (LinqUtils.first(interfaces, i -> i == expected) != null || LinqUtils.first(interfaces, i -> deepInterface(i, expected)) != null))
                return true;
            type = type.getSuperclass();
        }
        return false;
    }

    private static List<Field> deepGetFields(Class<?> clazz) {
        List<Field> result = new ArrayList<>();
        if (clazz != BaseElement.class)
            result.addAll(deepGetFields(clazz.getSuperclass()));
        result.addAll(Arrays.asList(clazz.getDeclaredFields()));
        return result;
    }

    public static List<Field> deepGetFields(Object obj, Class<?> type)  {
        return LinqUtils.where(deepGetFields(obj.getClass()), field -> !isStatic(field.getModifiers()) && (isClass(field, type) || isInterface(field, type)));
    }
    public static List<Field> getFields(Object obj, Class<?> type)  {
        return LinqUtils.where(obj.getClass().getDeclaredFields(), field -> !isStatic(field.getModifiers()) && (isClass(field, type) || isInterface(field, type)));
    }

    public static List<Field> getStaticFields(Class<?> parent, Class<?> type)  {
        return LinqUtils.where(parent.getDeclaredFields(), field -> isStatic(field.getModifiers()) && (isClass(field, type) || isInterface(field, type)));
    }
    public static <T> T getFirstField(Object obj, Class<T> type)  {
        return (T) getFieldValue(LinqUtils.first(obj.getClass().getDeclaredFields(), field -> isClass(field, type) || isInterface(field, type)), obj);
    }

    public static Object getFieldValue(Field field, Object obj) {
        field.setAccessible(true);
        try { return field.get(obj); }
        catch (Exception ex) {
            throw new RuntimeException(format("Can't get field '%s' value", field.getName())); }
    }
}
