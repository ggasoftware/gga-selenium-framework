package com.ggasoftware.jdi_ui_tests.core.elements.page_objects.annotations;

import com.ggasoftware.jdi_ui_tests.core.elements.composite.Page;
import com.ggasoftware.jdi_ui_tests.core.elements.page_objects.annotations.functions.*;
import com.ggasoftware.jdi_ui_tests.settings.JDISettings;

import java.lang.reflect.Field;

/**
 * Created by roman.i on 25.09.2014.
 */
public class AnnotationsUtil {
    public static <T> String getElementName(T clazz) {
        Class<T> cl = (Class<T>)clazz.getClass();
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

    public static void fillPageFromAnnotaiton(Page element, JPage pageAnnotation, Object parent){
        String url = pageAnnotation.url();
        if (!JDISettings.hasDomain() && parent.getClass().isAnnotationPresent(JSite.class))
            JDISettings.domain = parent.getClass().getAnnotation(JSite.class).domain();
        url = (url.contains("://") || parent == null || !JDISettings.hasDomain())
                ? url
                : JDISettings.domain.replaceAll("/*$", "") + "/" + url.replaceAll("^/*", "");
        String title = pageAnnotation.title();
        String urlMatcher = pageAnnotation.urlMatcher();
        String titleMatcher = pageAnnotation.titleMatcher();
        element.updatePageData(url, title, urlMatcher, titleMatcher);
    }

    private static String getUrlFromDomain(Object parent, String uri) {
        String domain = parent.getClass().getAnnotation(JSite.class).domain();
        return domain.replaceAll("/*$", "") + "/" + uri.replaceAll("^/*", "");
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
            result += (((isCapital(camel.charAt(i)) && !isCapital(camel.charAt(i + 1))) ? " " : "") + camel.charAt(i));
        return result + camel.charAt(camel.length() - 1);
    }

    private static boolean isCapital(char ch) { return 'A' < ch  && ch < 'Z'; }
}
