package com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.page_objects.annotations;

import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.composite.CheckPageTypes;
import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.composite.Page;
import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.page_objects.annotations.functions.CancelButton;
import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.page_objects.annotations.functions.CloseButton;
import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.page_objects.annotations.functions.Functions;
import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.page_objects.annotations.functions.OkButton;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

import java.lang.reflect.Field;

import static com.ggasoftware.jdi_ui_tests.core.settings.JDISettings.domain;
import static com.ggasoftware.jdi_ui_tests.core.settings.JDISettings.hasDomain;
import static com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.composite.CheckPageTypes.*;
import static com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.composite.Page.getMatchFromDomain;
import static com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.composite.Page.getUrlFromUri;

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

    public static void fillPageFromAnnotaiton(Page element, JPage pageAnnotation, Class<?> parentClass){
        String url = pageAnnotation.url();
        if (!hasDomain() && parentClass != null && parentClass.isAnnotationPresent(JSite.class))
            domain = parentClass.getAnnotation(JSite.class).domain();
        url = (url.contains("://") || parentClass == null || !hasDomain())
                ? url
                : getUrlFromUri(url);
        String title = pageAnnotation.title();
        String urlTemplate = pageAnnotation.urlTemplate();
        if (urlTemplate != null && !urlTemplate.equals(""))
            urlTemplate = (urlTemplate.contains("://") || parentClass == null || !hasDomain())
                    ? urlTemplate
                    : getMatchFromDomain(urlTemplate);
        CheckPageTypes checkType = pageAnnotation.checkType();
        CheckPageTypes urlCheckType = pageAnnotation.urlCheckType();
        CheckPageTypes titleCheckType = pageAnnotation.titleCheckType();
        if (urlCheckType == NONE)
            urlCheckType = (checkType != NONE) ? checkType : EQUAL;
        if (titleCheckType == NONE)
            titleCheckType = (checkType != NONE) ? checkType : EQUAL;
        if (urlCheckType == MATCH || urlCheckType == CONTAIN && (urlTemplate == null || urlTemplate.equals("")))
            urlTemplate = url;
        element.updatePageData(url, title, urlCheckType, titleCheckType, urlTemplate);
    }

    private static String getUrlFromDomain(Object parent, String uri) {
        domain = parent.getClass().getAnnotation(JSite.class).domain();
        return getUrlFromUri(uri);
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

    private static boolean isCapital(char ch) { return 'A' < ch  && ch < 'Z'; }

    public static By getFrame(Frame frame) {
        if (frame == null) return null;
        if (!"".equals(frame.id()))
            return By.id(frame.id());
        if (!"".equals(frame.className()))
            return By.className(frame.className());
        if (!"".equals(frame.xpath()))
            return By.xpath(frame.xpath());
        if (!"".equals(frame.css()))
            return By.cssSelector(frame.css());
        if (!"".equals(frame.linkText()))
            return By.linkText(frame.linkText());
        if (!"".equals(frame.name()))
            return By.name(frame.name());
        if (!"".equals(frame.partialLinkText()))
            return By.partialLinkText(frame.partialLinkText());
        if (!"".equals(frame.tagName()))
            return By.tagName(frame.tagName());
        return null;
    }
    public static By getFindByLocator(FindBy locator) {
        if (locator == null) return null;
        if (!"".equals(locator.id()))
            return By.id(locator.id());
        if (!"".equals(locator.className()))
            return By.className(locator.className());
        if (!"".equals(locator.xpath()))
            return By.xpath(locator.xpath());
        if (!"".equals(locator.css()))
            return By.cssSelector(locator.css());
        if (!"".equals(locator.linkText()))
            return By.linkText(locator.linkText());
        if (!"".equals(locator.name()))
            return By.name(locator.name());
        if (!"".equals(locator.partialLinkText()))
            return By.partialLinkText(locator.partialLinkText());
        if (!"".equals(locator.tagName()))
            return By.tagName(locator.tagName());
        return null;
    }
    public static By getFindByLocator(JFindBy locator) {
        if (locator == null) return null;
        if (!"".equals(locator.id()))
            return By.id(locator.id());
        if (!"".equals(locator.className()))
            return By.className(locator.className());
        if (!"".equals(locator.xpath()))
            return By.xpath(locator.xpath());
        if (!"".equals(locator.css()))
            return By.cssSelector(locator.css());
        if (!"".equals(locator.linkText()))
            return By.linkText(locator.linkText());
        if (!"".equals(locator.name()))
            return By.name(locator.name());
        if (!"".equals(locator.partialLinkText()))
            return By.partialLinkText(locator.partialLinkText());
        if (!"".equals(locator.tagName()))
            return By.tagName(locator.tagName());
        return null;
    }
}
