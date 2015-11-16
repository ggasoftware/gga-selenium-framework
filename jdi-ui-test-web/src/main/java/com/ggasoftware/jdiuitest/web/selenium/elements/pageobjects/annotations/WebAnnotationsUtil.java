package com.ggasoftware.jdiuitest.web.selenium.elements.pageobjects.annotations;

import com.ggasoftware.jdiuitest.core.annotations.AnnotationsUtil;
import com.ggasoftware.jdiuitest.web.selenium.elements.composite.CheckPageTypes;
import com.ggasoftware.jdiuitest.web.selenium.elements.composite.WebPage;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

import static com.ggasoftware.jdiuitest.core.settings.JDISettings.domain;
import static com.ggasoftware.jdiuitest.core.settings.JDISettings.hasDomain;
import static com.ggasoftware.jdiuitest.web.selenium.elements.composite.CheckPageTypes.*;
import static com.ggasoftware.jdiuitest.web.selenium.elements.composite.WebPage.getMatchFromDomain;
import static com.ggasoftware.jdiuitest.web.selenium.elements.composite.WebPage.getUrlFromUri;

/**
 * Created by roman.i on 25.09.2014.
 */
public class WebAnnotationsUtil extends AnnotationsUtil {

    public static void fillPageFromAnnotaiton(WebPage element, JPage pageAnnotation, Class<?> parentClass) {
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
