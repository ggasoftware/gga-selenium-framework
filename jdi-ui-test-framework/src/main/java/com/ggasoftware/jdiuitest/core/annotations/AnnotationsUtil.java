package com.ggasoftware.jdiuitest.core.annotations;

import com.ggasoftware.jdiuitest.core.annotations.functions.CancelButton;
import com.ggasoftware.jdiuitest.core.annotations.functions.CloseButton;
import com.ggasoftware.jdiuitest.core.annotations.functions.Functions;
import com.ggasoftware.jdiuitest.core.annotations.functions.OkButton;

import java.lang.reflect.Field;

/**
 * Created by roman.i on 25.09.2014.
 */
public class AnnotationsUtil {
    protected AnnotationsUtil() {}
    public static <T> String getElementName(T clazz) {
        Class<T> cl = (Class<T>) clazz.getClass();
        if (cl.isAnnotationPresent(Name.class)) {
            return cl.getAnnotation(Name.class).value();
        } else {
            return splitCamelCase(cl.getSimpleName());
        }
    }

    public static String getElementName(Field field) {
        if (field.isAnnotationPresent(Name.class)) {
            return field.getAnnotation(Name.class).value();
        } else {
            return splitCamelCase(field.getName());
        }
    }

    public static Functions getFunction(Field field) {
        if (field.isAnnotationPresent(OkButton.class))
            return Functions.OK_BUTTON;
        if (field.isAnnotationPresent(CloseButton.class))
            return Functions.CLOSE_BUTTON;
        if (field.isAnnotationPresent(CancelButton.class))
            return Functions.CANCEL_BUTTON;
        return Functions.NONE;
    }

    private static String splitCamelCase(String camel) {
        String result = (camel.charAt(0) + "").toUpperCase();
        for (int i = 1; i < camel.length() - 1; i++)
            result += ((isCapital(camel.charAt(i)) && !isCapital(camel.charAt(i - 1))) ? " " : "") + camel.charAt(i);
        return result + camel.charAt(camel.length() - 1);
    }

    private static boolean isCapital(char ch) {
        return 'A' < ch && ch < 'Z';
    }

}
