package com.ggasoftware.jdiuitest.web.selenium.elements.common;

import com.ggasoftware.jdiuitest.core.interfaces.common.IDatePicker;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * Created by Roman_Iovlev on 7/10/2015.
 */
public class DatePicker extends TextField implements IDatePicker {
    public DatePicker() {
    }

    public DatePicker(By byLocator) {
        super(byLocator);
    }

    public DatePicker(WebElement webElement) {
        super(webElement);
    }
}
