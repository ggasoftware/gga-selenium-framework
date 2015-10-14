package com.ggasoftware.uitest.control.new_controls.common;

import com.ggasoftware.uitest.control.Input;
import com.ggasoftware.uitest.control.interfaces.common.IDatePicker;
import org.openqa.selenium.By;

/**
 * Created by Roman_Iovlev on 7/10/2015.
 */
public class DatePicker extends Input implements IDatePicker {
    public DatePicker() {
    }

    public DatePicker(By byLocator) {
        super(byLocator);
    }
}
