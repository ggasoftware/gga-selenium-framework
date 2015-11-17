/*
 * Copyright 2004-2016 EPAM Systems
 *
 * This file is part of JDI project.
 *
 * JDI is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * JDI is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; 
 * without even the implied warranty ofMERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. 
 * See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with JDI. If not, see <http://www.gnu.org/licenses/>.
 */
 
/**
 * *************************************************************************
 * Copyright (C) 2014 GGA Software Services LLC
 * <p>
 * This file may be distributed and/or modified under the terms of the
 * GNU General Public License version 3 as published by the Free Software
 * Foundation.
 * <p>
 * This file is provided AS IS with NO WARRANTY OF ANY KIND, INCLUDING THE
 * WARRANTY OF DESIGN, MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see <http://www.gnu.org/licenses>.
 * *************************************************************************
 */
package com.ggasoftware.jdiuitest.web.selenium.elements.base;

import com.ggasoftware.jdiuitest.core.annotations.JDIAction;
import com.ggasoftware.jdiuitest.core.interfaces.base.IElement;
import com.ggasoftware.jdiuitest.core.logger.base.LogSettings;
import com.ggasoftware.jdiuitest.core.logger.enums.LogInfoTypes;
import com.ggasoftware.jdiuitest.core.logger.enums.LogLevels;
import com.ggasoftware.jdiuitest.core.settings.HighlightSettings;
import com.ggasoftware.jdiuitest.core.settings.JDISettings;
import com.ggasoftware.jdiuitest.core.utils.common.Timer;
import com.ggasoftware.jdiuitest.core.utils.linqinterfaces.JFuncTT;
import com.ggasoftware.jdiuitest.web.selenium.elements.BaseElement;
import com.ggasoftware.jdiuitest.web.selenium.elements.WebSettings;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import static java.lang.String.format;

/**
 * Base Element control implementation
 *
 * @author Alexeenko Yan
 * @author Belin Yury
 * @author Belousov Andrey
 * @author Shubin Konstantin
 * @author Zharov Alexandr
 */
public class Element extends BaseElement implements IElement {
    private WebElement webElement;

    public Element() {
        super();
    }

    public Element(By byLocator) {
        super(byLocator);
    }

    public Element(WebElement webElement) {
        this.webElement = webElement;
    }

    public static <T extends Element> T copy(T element, By newLocator) {
        try {
            T result = (T) element.getClass().newInstance();
            result.setAvatar(newLocator, element.getAvatar());
            return result;
        } catch (Exception ex) {
            throw JDISettings.exception("Can't copy Element: " + element);
        }
    }

    /**
     * @returns Specified Selenium Element for this Element
     */
    @JDIAction
    public WebElement getWebElement() {
        return invoker.doJActionResult("Get web element",
                () -> webElement != null ? webElement : avatar.getElement(),
                new LogSettings(LogLevels.DEBUG, LogInfoTypes.BUSINESS));
    }

    public String getAttribute(String name) {
        return getWebElement().getAttribute(name);
    }

    public boolean waitAttribute(String name, String value) {
        return wait(el -> el.getAttribute(name).equals(value));
    }

    public void setAttribute(String attributeName, String value) {
        invoker.doJAction(format("Set Attribute '%s'='%s'", attributeName, value),
                () -> jsExecutor().executeScript(format("arguments[0].setAttribute('%s',arguments[1]);", attributeName),
                        getWebElement(), value));
    }

    protected boolean isDisplayedAction() {
        return actions.findImmediately(() -> getWebElement().isDisplayed(), false);
    }

    protected boolean waitDisplayedAction() {
        return wait(WebElement::isDisplayed);
    }

    public boolean isDisplayed() {
        return actions.isDisplayed(this::isDisplayedAction);
    }

    public boolean isHidden() {
        return actions.isDisplayed(() -> !isDisplayedAction());
    }

    public boolean waitDisplayed() {
        return actions.waitDisplayed(this::waitDisplayedAction);
    }

    public boolean waitVanished() {
        return actions.waitVanished(() -> timer().wait(() -> !isDisplayedAction()));
    }

    public WebElement getInvisibleElement() {
        avatar.searchAll();
        return getWebElement();
    }

    /**
     * @param resultFunc Specify expected function result
     * @return Waits while condition with WebElement happens during specified timeout and returns result using resultFunc
     */
    @JDIAction
    public Boolean wait(JFuncTT<WebElement, Boolean> resultFunc) {
        return wait(resultFunc, result -> result);
    }

    /**
     * @param resultFunc Specify expected function result
     * @param condition  Specify expected function condition
     * @return Waits while condition with WebElement happens and returns result using resultFunc
     */
    @JDIAction
    public <T> T wait(JFuncTT<WebElement, T> resultFunc, JFuncTT<T, Boolean> condition) {
        return timer().getResultByCondition(() -> resultFunc.invoke(getWebElement()), condition::invoke);
    }

    /**
     * @param resultFunc Specify expected function result
     * @param timeoutSec Specify timeout
     * @return Waits while condition with WebElement happens during specified timeout and returns wait result
     */
    @JDIAction
    public Boolean wait(JFuncTT<WebElement, Boolean> resultFunc, int timeoutSec) {
        return wait(resultFunc, result -> result, timeoutSec);
    }
    /**
     * @param resultFunc Specify expected function result
     * @param timeoutSec Specify timeout
     * @param condition  Specify expected function condition
     * @return Waits while condition with WebElement and returns wait result
     */
    @JDIAction
    public <T> T wait(JFuncTT<WebElement, T> resultFunc, JFuncTT<T, Boolean> condition, int timeoutSec) {
        setWaitTimeout(timeoutSec);
        T result = new Timer(timeoutSec).getResultByCondition(() -> resultFunc.invoke(getWebElement()), condition::invoke);
        restoreWaitTimeout();
        return result;
    }

    public void highlight() {
        WebSettings.driverFactory.highlight(this);
    }

    public void highlight(HighlightSettings highlightSettings) {
        WebSettings.driverFactory.highlight(this, highlightSettings);
    }

    public void clickWithKeys(Keys... keys) {
        invoker.doJAction("Ctrl click on Element",
                () -> {
                    Actions action = new Actions(getDriver());
                    for (Keys key : keys)
                        action = action.keyDown(key);
                    action = action.moveToElement(getWebElement()).click();
                    for (Keys key : keys)
                        action = action.keyUp(key);
                    action.perform();
                });
    }

    public void doubleClick() {
        invoker.doJAction("Double click on Element", () -> {
            getWebElement().getSize(); //for scroll to object
            Actions builder = new Actions(getDriver());
            builder.doubleClick(getWebElement()).perform();
        });
    }

    public void rightClick() {
        invoker.doJAction("Right click on Element", () -> {
            getWebElement().getSize(); //for scroll to object
            Actions builder = new Actions(getDriver());
            builder.contextClick(getWebElement()).perform();
        });
    }

    public void clickCenter() {
        invoker.doJAction("Click in Center of Element", () -> {
            getWebElement().getSize(); //for scroll to object
            Actions builder = new Actions(getDriver());
            builder.click(getWebElement()).perform();
        });
    }

    public void mouseOver() {
        invoker.doJAction("Move mouse over Element", () -> {
            getWebElement().getSize(); //for scroll to object
            Actions builder = new Actions(getDriver());
            builder.moveToElement(getWebElement()).build().perform();
        });
    }

    public void focus() {
        invoker.doJAction("Focus on Element", () -> {
            Dimension size = getWebElement().getSize(); //for scroll to object
            new Actions(getDriver()).moveToElement(getWebElement(), size.width / 2, size.height / 2).build().perform();
        });
    }

    public void selectArea(int x1, int y1, int x2, int y2) {
        invoker.doJAction(format("Select area: from %d,%d;to %d,%d", x1, y1, x2, y2), () -> {
            WebElement element = getWebElement();
            new Actions(getDriver()).moveToElement(element, x1, y1).clickAndHold()
                    .moveToElement(element, x2, y2).release().build().perform();
        });
    }

    public void dragAndDropBy(int x, int y) {
        invoker.doJAction(format("Drag and drop Element: (x,y)=(%s,%s)", x, y), () ->
                new Actions(getDriver()).dragAndDropBy(getWebElement(), x, y).build().perform());
    }

}
