package com.ggasoftware.jdiuitest.web.selenium.elements.pageobjects.annotations;

import com.ggasoftware.jdiuitest.web.selenium.elements.composite.CheckPageTypes;

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

    String urlTemplate() default "";

    CheckPageTypes checkType() default CheckPageTypes.NONE;

    CheckPageTypes urlCheckType() default CheckPageTypes.NONE;

    CheckPageTypes titleCheckType() default CheckPageTypes.NONE;

}
