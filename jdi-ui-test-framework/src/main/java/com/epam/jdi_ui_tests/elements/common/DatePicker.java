package com.epam.jdi_ui_tests.elements.common;

import com.epam.jdi_ui_tests.elements.interfaces.common.IDatePicker;
import org.openqa.selenium.By;

/**
 * Created by Roman_Iovlev on 7/10/2015.
 */
public class DatePicker extends TextField implements IDatePicker {
    public DatePicker() { }
    public DatePicker(By byLocator) { super(byLocator); }
}
