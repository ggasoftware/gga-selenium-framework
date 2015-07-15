package com.epam.ui_test_framework.elements.page_objects.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by roman.i on 06.10.2014.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD})
public @interface Frame {
    public String id() default "";
    public String name() default "";
    public String className() default "";
    public String css() default "";
    public String tagName() default "";
    public String linkText() default "";
    public String partialLinkText() default "";
    public String xpath() default "";
}
