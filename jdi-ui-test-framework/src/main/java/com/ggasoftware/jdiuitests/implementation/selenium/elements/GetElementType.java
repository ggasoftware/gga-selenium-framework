package com.ggasoftware.jdiuitests.implementation.selenium.elements;

import com.ggasoftware.jdiuitests.implementation.selenium.elements.apiInteract.GetElementModule;
import org.openqa.selenium.By;

/**
 * Created by Roman_Iovlev on 9/28/2015.
 */
public class GetElementType {
    private By locator;
    private By allLabelsLocator;

    public GetElementType() {
        this(null);
    }

    public GetElementType(By locator) {
        this.locator = locator;
    }

    public <T extends BaseElement> T get(T element, GetElementModule avatar) {
        return locator == null
                ? null
                : (T) element.setAvatar(locator, avatar);
    }
}
