package com.ggasoftware.jdiuitests.implementation.selenium.elements.page_objects.annotations;

import com.ggasoftware.jdiuitests.implementation.selenium.elements.composite.CheckPageTypes;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static com.ggasoftware.jdiuitests.implementation.selenium.elements.composite.CheckPageTypes.*;

/**
 * Created by Roman_Iovlev on 7/24/2015.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD})
public @interface JPage {
    String url() default "";
    String title() default "";
    String urlTemplate() default "";
    CheckPageTypes checkType() default NONE;
    CheckPageTypes urlCheckType() default NONE;
    CheckPageTypes titleCheckType() default NONE;

}
