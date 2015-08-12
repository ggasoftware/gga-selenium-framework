package com.ggasoftware.jdi_ui_tests.selenium.elements.base;

import com.ggasoftware.jdi_ui_tests.core.drivers.JElement;
import com.ggasoftware.jdi_ui_tests.core.elements.base.Element;
import com.ggasoftware.jdi_ui_tests.core.elements.interfaces.base.*;
import com.ggasoftware.jdi_ui_tests.settings.HighlightSettings;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static com.ggasoftware.jdi_ui_tests.settings.JDISettings.*;
import static com.ggasoftware.jdi_ui_tests.utils.common.ReflectionUtils.isClass;
import static java.lang.String.format;


/**
 * Created by Roman_Iovlev on 6/10/2015.
 */
public class SlmElement implements JElement<WebDriver, WebElement, By> {
    private WebElement element;
    public SlmElement(WebElement element) {
        this.element = element;
    }
    public void click() { element.click(); }
    public String getText() { return element.getText(); }
    public void clear() { element.clear(); }
    public void setText(String text) { element.sendKeys(text); }
    public String getAttribute(String attrName) { return element.getAttribute(attrName); }
    public boolean isDisplayed() { return element.isDisplayed(); }

    public void processDemoMode(IBaseElement<WebDriver, By> element) {
        if (isDemoMode)
            if (isClass(element.getClass(), Element.class))
                highlight((IElement<WebElement>)element, highlightSettings);
    }

    public void highlight(IElement<WebElement> element) { highlight(element, highlightSettings); }
    public void highlight(IElement<WebElement> element, HighlightSettings highlightSettings) {
        if (highlightSettings == null)
            highlightSettings = new HighlightSettings();
        String orig = element.getElement().getAttribute("style");
        element.setAttribute("style", format("border: 3px solid %s; background-color: %s;", highlightSettings.FrameColor,
                highlightSettings.BgColor));
        try { Thread.sleep(highlightSettings.TimeoutInSec * 1000); } catch (Exception ignore) {}
        element.setAttribute("style", orig);
    }
}
