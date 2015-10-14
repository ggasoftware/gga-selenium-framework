package com.ggasoftware.jdiuitests.implementation.selenium.elements.common;

import com.ggasoftware.jdiuitests.implementation.selenium.elements.interfaces.common.IFileInput;
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

}
