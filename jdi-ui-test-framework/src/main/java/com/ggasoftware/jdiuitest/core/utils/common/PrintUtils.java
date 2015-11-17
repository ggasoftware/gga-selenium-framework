/*
 * Copyright 2004-2016 EPAM Systems
 *
 * This file is part of JDI project.
 *
 * JDI is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * JDI is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; 
 * without even the implied warranty ofMERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. 
 * See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with JDI. If not, see <http://www.gnu.org/licenses/>.
 */
package com.ggasoftware.jdiuitest.core.utils.common;

import com.ggasoftware.jdiuitest.core.annotations.Complex;
import com.ggasoftware.jdiuitest.core.utils.map.MapArray;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static com.ggasoftware.jdiuitest.core.annotations.AnnotationsUtil.getElementName;
import static com.ggasoftware.jdiuitest.core.utils.common.LinqUtils.select;
import static com.ggasoftware.jdiuitest.core.utils.common.ReflectionUtils.*;
import static java.lang.Integer.parseInt;
import static java.lang.String.format;
import static java.lang.reflect.Array.get;
import static java.lang.reflect.Array.getLength;
import static java.util.Arrays.asList;

/**
 * Created by roman.i on 30.09.2014.
 */
public class PrintUtils {
    private PrintUtils() { }
    public static String print(Iterable<String> list) {
        return print(list, ", ", "%s");
    }

    public static String print(Iterable<String> list, String separator) {
        return print(list, separator, "%s");
    }

    public static <T extends Enum> String printEnum(List<T> enums) {
        return (enums != null) ? String.join(", ", LinqUtils.select(enums, el -> String.format("%s", el))) : "";
    }

    public static String print(Iterable<String> list, String separator, String format) {
        return (list != null) ? String.join(separator, LinqUtils.select(list, el -> String.format(format, el))) : "";
    }

    public static String print(String[] list) {
        return print(list, ", ", "%s");
    }

    public static String print(String[] list, String separator) {
        return print(list, separator, "%s");
    }

    public static String print(String[] list, String separator, String format) {
        return print(asList(list), separator, format);
    }

    public static String print(int[] list) {
        return print(list, ", ", "%s");
    }

    public static String print(int[] list, String separator) {
        return print(list, separator, "%s");
    }

    public static String print(int[] list, String separator, String format) {
        List<String> result = new ArrayList<>();
        for (int i : list)
            result.add(Integer.toString(i));
        return print(result, separator, format);
    }

    public static String print(boolean[] list) {
        return print(list, ", ", "%s");
    }

    public static String print(boolean[] list, String separator) {
        return print(list, separator, "%s");
    }

    public static String print(boolean[] list, String separator, String format) {
        List<String> result = new ArrayList<>();
        for (boolean i : list)
            result.add(Boolean.toString(i));
        return print(result, separator, format);
    }

    public static String printFields(Object obj) {
        return printFields(obj, "; ");
    }

    public static String printFields(Object obj, String separator) {
        String className = obj.getClass().getSimpleName();
        String params = print(select(getFields(obj, String.class),
                field -> format("%s: '%s'", field.getName(), getValueField(field, obj))), separator, "%s");
        return format("%s(%s)", className, params);
    }

    private static String printObject(Object obj) {
        List<String> result = new ArrayList<>();
        for (Field field : getFields(obj, Object.class)) {
            Object value = getValueField(field, obj);
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
                result.add(String.format("%s#:#%s", getElementName(field), strValue));
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

    public static MapArray<String, String> parseObjectAsString(String string) {
        if (string == null)
            return null;
        MapArray<String, String> result = new MapArray<>();
        List<String> values = new ArrayList<>();
        int i = 1;
        String str = string;
        while (string.indexOf("#(#") > 0) {
            values.add(string.substring(string.indexOf("#(#") + 3, string.indexOf("#)#")));
            str = string.replaceAll("#\\(#.*#\\)#", "#VAL" + i++);
        }
        String[] fields = str.split("#;#");
        for (String field : fields) {
            String[] splitField = field.split("#:#");
            if (splitField.length == 2)
                result.add(splitField[0], processValue(splitField[1], values));
        }
        return result;
    }

    public static String printObjectAsArray(Object array) {
        List<String> elements = new ArrayList<>();
        for (int i = 0; i <= getLength(array); i++)
            elements.add(get(array, i).toString());
        return print(elements);
    }
}
