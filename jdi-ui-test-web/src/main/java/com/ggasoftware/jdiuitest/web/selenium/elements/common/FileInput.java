package com.ggasoftware.jdiuitest.web.selenium.elements.common;

import com.ggasoftware.jdiuitest.core.interfaces.common.IFileInput;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * Created by Roman_Iovlev on 7/10/2015.
 */
public class FileInput extends TextField implements IFileInput {
    public FileInput() {
        super();
    }

    public FileInput(By byLocator) {
        super(byLocator);
    }

    public FileInput(WebElement webElement) {
        super(webElement);
    }

    @Override
    protected void setValueAction(String value) {
        input(value);
    }
}
