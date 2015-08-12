package com.ggasoftware.jdi_ui_tests.selenium.elements.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by 12345 on 07.11.2014.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD})
public @interface JFindBy {
    String id() default "";
    String name() default "";
    String className() default "";
    String css() default "";
    String tagName() default "";
    String linkText() default "";
    String partialLinkText() default "";
    String xpath() default "";

    String group();
}
