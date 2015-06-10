package com.ggasoftware.uitest.control.interfaces;

import com.ggasoftware.uitest.utils.JDIAction;
import org.openqa.selenium.By;

/**
 * Created by Roman_Iovlev on 6/10/2015.
 */
public interface IBaseElement {
    @JDIAction
    String getName();
    @JDIAction
    By getByLocator();
}
