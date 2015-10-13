package com.ggasoftware.jdiuitests.implementation.selenium.elements.pageobjects.annotations.objects;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by roman.i on 06.10.2014.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD})
public @interface Table {
    String root() default "";
    String header() default "";
    String cell() default "";
    String row() default "";
    String column() default "";
}
