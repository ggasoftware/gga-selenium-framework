package com.ggasoftware.uitest.control.base;

import com.ggasoftware.uitest.control.interfaces.IClickable;
import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;

import static java.lang.String.format;

/**
 * Created by Roman_Iovlev on 7/6/2015.
 */
public class Clickable extends Element implements IClickable {
    public Clickable() { }
    public Clickable(By byLocator) { super(byLocator); }

    protected void clickAction() { getWebElement().click(); }
    public final void click() { doJAction("Click on element", this::clickAction); }

    public void clickByXY(int x, int y) {
        doJAction(format("Click on element with coordinates (x,y) = (%s, %s)", x, y),
            () -> new Actions(getDriver())
                .moveToElement(getWebElement(), x, y).click().build().perform());
    }

    public void clickJS() {
        doJAction("Click on element using JavaScript",
                () -> jsExecutor().executeScript("arguments[0].click();", getWebElement()));
    }
}
