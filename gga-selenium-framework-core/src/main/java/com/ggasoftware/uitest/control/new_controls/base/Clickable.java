package com.ggasoftware.uitest.control.new_controls.base;

import com.ggasoftware.uitest.control.Element;
import com.ggasoftware.uitest.control.interfaces.base.IClickable;
import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;

import static java.lang.String.format;

/**
 * Created by Roman_Iovlev on 7/6/2015.
 */
public class Clickable<P> extends Element<P> implements IClickable<P> {
    public Clickable() {
    }

    public Clickable(By byLocator) {
        super(byLocator);
    }

    public Clickable(String name, String locator, P parentPanel) {
        super(name, locator, parentPanel);
    }

    protected void clickJSAction() {
        jsExecutor().executeScript("arguments[0].click();", getWebElement());
    }

    protected void clickActionM() {
        getWebElement().click();
    }

    public final P click() {
        doJAction("Click on element", this::clickActionM);
        return parent;
    }

    public void clickByXY(int x, int y) {
        doJAction(format("Click on element with coordinates (x,y) = (%s, %s)", x, y),
                () -> new Actions(getDriver())
                        .moveToElement(getWebElement(), x, y).click().build().perform());
    }
}
