package com.ggasoftware.jdiuitests.implementation.selenium.driver;

import com.ggasoftware.jdiuitests.core.apiaccessor.JElement;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;

import java.util.List;


/**
 * Created by Roman_Iovlev on 6/10/2015.
 */
public class WebElementAvatar implements JElement, WebElement {
    private WebElement element;

    public WebElementAvatar(WebElement element) {
        this.element = element;
    }

    public void click() {
        element.click();
    }

    public void submit() {
        element.submit();
    }

    public void sendKeys(CharSequence... charSequences) {
        element.sendKeys(charSequences);
    }

    public void clear() {

    }

    public String getTagName() {
        return null;
    }

    public String getText() {
        return null;
    }

    public void setText(String text) {

    }

    public List<WebElement> findElements(By by) {
        return null;
    }

    public WebElement findElement(By by) {
        return null;
    }

    public void clear(String text) {

    }

    public String getAttribute(String attrNamr) {
        return null;
    }

    public boolean isSelected() {
        return false;
    }

    public boolean isEnabled() {
        return false;
    }

    public boolean isDisplayed() {
        return false;
    }

    public Point getLocation() {
        return null;
    }

    public Dimension getSize() {
        return null;
    }

    public String getCssValue(String s) {
        return null;
    }
}
