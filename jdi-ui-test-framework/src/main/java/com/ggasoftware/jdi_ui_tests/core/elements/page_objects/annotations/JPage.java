package com.ggasoftware.jdi_ui_tests.core.elements.page_objects.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Roman_Iovlev on 7/24/2015.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD})
public @interface JPage {
    String url() default "";
    String title() default "";
    String urlMatcher() default "";
    String titleMatcher() default "";
}
