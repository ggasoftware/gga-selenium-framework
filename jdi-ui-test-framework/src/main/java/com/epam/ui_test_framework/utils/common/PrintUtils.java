package com.epam.ui_test_framework.utils.common;

import com.epam.ui_test_framework.elements.page_objects.annotations.Complex;
import com.epam.ui_test_framework.utils.map.MapArray;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static com.epam.ui_test_framework.utils.common.LinqUtils.select;
import static com.epam.ui_test_framework.utils.common.ReflectionUtils.getFieldValue;
import static com.epam.ui_test_framework.utils.common.ReflectionUtils.getFields;
import static com.epam.ui_test_framework.utils.common.ReflectionUtils.isClass;
import static java.lang.Integer.parseInt;
import static java.lang.String.format;
import static java.lang.String.join;
import static java.util.Arrays.asList;

/**
 * Created by roman.i on 30.09.2014.
 */
public class PrintUtils {
    public static String print(Iterable<String> list) { return print(list, ", ", "%s"); }
    public static String print(Iterable<String> list, String separator) { return print(list, separator, "%s"); }
    public static <T extends Enum> String printEnum(List<T> enums) {
        return (enums != null) ? join(", ", select(enums, el -> format("%s", el))) : "";
    }
    public static String print(Iterable<String> list, String separator, String format) {
        return (list != null) ? join(separator, select(list, el -> format(format, el))) : "";
    }
    public static String print(String[] list) { return print(list, ", ", "%s"); }
    public static String print(String[] list, String separator) { return print(list, separator, "%s"); }
    public static String print(String[] list, String separator, String format)  {
        return print(asList(list), separator, format);
    }
    public static String print(int[] list) { return print(list, ", ", "%s"); }
    public static String print(int[] list, String separator) { return print(list, separator, "%s"); }
    public static String print(int[] list, String separator, String format)  {
        List<String> result = new ArrayList<>();
        for (int i : list) result.add(i+"");
        return print(result, separator, format);
    }
    public static String print(boolean[] list) { return print(list, ", ", "%s"); }
    public static String print(boolean[] list, String separator) { return print(list, separator, "%s"); }
    public static String print(boolean[] list, String separator, String format)  {
        List<String> result = new ArrayList<>();
        for (boolean i : list) result.add(i+"");
        return print(result, separator, format);
    }

    public static String printFields(Object obj) { return printFields(obj, "; "); }
    public static String printFields(Object obj, String separator) {
        String className = obj.getClass().getSimpleName();
        String params = print(select(getFields(obj, String.class),
            field -> format("%s: '%s'", field.getName(), getFieldValue(field, obj))), separator, "%s");
        return format("%s(%s)", className, params);
    }

    private static String printObject(Object obj) {
        List<String> result = new ArrayList<>();
        for (Field field : getFields(obj, Object.class)) {
            Object value = getFieldValue(field, obj);
            String strValue = null;
            if (value == null)
                strValue = "#NULL#";
            else if (isClass(value.getClass(), String.class))
                strValue = (String) value;
            else if (isClass(value.getClass(), Enum.class))
                strValue = value.toString();
            else if (field.isAnnotationPresent(Complex.class))
                strValue = "#(#" + printObject(value) + "#)#";
            if (strValue != null)
                result.add(format("%s#:#%s", field.getName(), strValue));
        }
        return print(result, "#;#", "%s");
    }
    public static MapArray<String, String> objToSetValue(Object obj) {
        return (obj == null)
            ? new MapArray<>()
            : parseObjectAsString(printObject(obj));
    }
    public static String processValue(String input, List<String> values) {
        if (input.equals("#NULL#"))
            return null;
        if (input.matches("#VAL\\d*"))
            return values.get(parseInt(input.substring(4)) - 1);
        return input;
    }
    public static MapArray<String, String> parseObjectAsString(String obj) {
        if (obj == null) return null;
        MapArray<String, String> result = new MapArray<>();
        List<String> values = new ArrayList<>();
        int i = 1;
        while (obj.indexOf("#(#") > 0) {
            values.add(obj.substring(obj.indexOf("#(#") + 3, obj.indexOf("#)#")));
            obj = obj.replaceAll("#\\(#.*#\\)#", "#VAL" + i++);
        }
        String[] fields = obj.split("#;#");
        for(String field : fields) {
            String[] splitedField = field.split("#:#");
            if (splitedField.length == 2)
                result.add(splitedField[0], processValue(splitedField[1], values));
        }
        return result;
    }
}
