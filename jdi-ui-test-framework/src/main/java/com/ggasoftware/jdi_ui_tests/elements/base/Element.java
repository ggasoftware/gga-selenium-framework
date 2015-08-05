/****************************************************************************
 * Copyright (C) 2014 GGA Software Services LLC
 *
 * This file may be distributed and/or modified under the terms of the
 * GNU General Public License version 3 as published by the Free Software
 * Foundation.
 *
 * This file is provided AS IS with NO WARRANTY OF ANY KIND, INCLUDING THE
 * WARRANTY OF DESIGN, MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see <http://www.gnu.org/licenses>.
 ***************************************************************************/
package com.ggasoftware.jdi_ui_tests.elements.base;

import com.ggasoftware.jdi_ui_tests.elements.interfaces.base.IElement;
import com.ggasoftware.jdi_ui_tests.elements.BaseElement;
import com.ggasoftware.jdi_ui_tests.logger.base.LogSettings;
import com.ggasoftware.jdi_ui_tests.utils.common.Timer;
import com.ggasoftware.jdi_ui_tests.settings.HighlightSettings;
import com.ggasoftware.jdi_ui_tests.utils.linqInterfaces.JFuncTT;
import com.ggasoftware.jdi_ui_tests.logger.enums.LogInfoTypes;
import com.ggasoftware.jdi_ui_tests.logger.enums.LogLevels;
import com.ggasoftware.jdi_ui_tests.settings.JDISettings;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.*;

import static com.ggasoftware.jdi_ui_tests.elements.page_objects.annotations.AnnotationsUtil.getElementName;
import static com.ggasoftware.jdi_ui_tests.elements.page_objects.annotations.AnnotationsUtil.getFindByLocator;
import static com.ggasoftware.jdi_ui_tests.utils.common.LinqUtils.first;
import static com.ggasoftware.jdi_ui_tests.utils.common.LinqUtils.select;
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
    public Element() { super(); }
    public Element(By byLocator) { super(byLocator); }

    public WebElement getWebElement() {
        return doJActionResult("Get web element " + this.toString(), avatar::getElement, new LogSettings(LogLevels.DEBUG, LogInfoTypes.BUSINESS));
    }

    public static <T extends Element> T copy(T element, By newLocator) {
        try {
            T result = (T) element.getClass().newInstance();
            result.setAvatar(newLocator, element.getAvatar());
            return result;
        } catch (Exception ex) { JDISettings.asserter.exception("Can't copy element: " + element); return null; }
    }

    public boolean waitAttribute(String name, String value) {
        return wait(el -> el.getAttribute(name).equals(value));
    }
    public void setAttribute(String attributeName, String value) {
        doJAction(format("Set Attribute '%s'='%s'", attributeName, value),
                () -> jsExecutor().executeScript(format("arguments[0].setAttribute('%s',arguments[1]);", attributeName),
                        getWebElement(), value));
    }
    public boolean waitDisplayed() {
        return wait(WebElement::isDisplayed);
    }

    public boolean waitVanished()  {
        setWaitTimeout(JDISettings.timeouts.retryMSec);
        boolean result = timer().wait(() -> {
                try { if (getWebElement().isDisplayed()) return false; }
                catch (Exception ignore) { }
                return false;
            });
        setWaitTimeout(JDISettings.timeouts.waitElementSec);
        return result;
    }

    @Override
    public Boolean wait(JFuncTT<WebElement, Boolean> resultFunc) {
        return wait(resultFunc, result -> result);
    }
    @Override
    public <T> T wait(JFuncTT<WebElement, T> resultFunc, JFuncTT<T, Boolean> condition) {
        return timer().getResultByCondition(() -> resultFunc.invoke(getWebElement()), condition::invoke);
    }

    @Override
    public Boolean wait(JFuncTT<WebElement, Boolean> resultFunc, int timeoutSec) {
        return wait(resultFunc, result -> result, timeoutSec);
    }
    @Override
    public <T> T wait(JFuncTT<WebElement, T> resultFunc, JFuncTT<T, Boolean> condition, int timeoutSec) {
        setWaitTimeout(timeoutSec);
        T result = new Timer(timeoutSec).getResultByCondition(() -> resultFunc.invoke(getWebElement()), condition::invoke);
        setWaitTimeout(JDISettings.timeouts.waitElementSec);
        return result;
    }

    public void highlight() { JDISettings.driverFactory.highlight(this); }
    public void highlight(HighlightSettings highlightSettings) {
        JDISettings.driverFactory.highlight(this, highlightSettings);
    }

    public void clickWithKeys(Keys... keys) {
        doJAction("Ctrl click on element",
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
        doJAction("Couble click on element", () -> {
            getWebElement().getSize(); //for scroll to object
            Actions builder = new Actions(getDriver());
            builder.doubleClick();
        });
    }
    public void rightClick() {
        doJAction("Right click on element", () -> {
            getWebElement().getSize(); //for scroll to object
            Actions builder = new Actions(getDriver());
            builder.contextClick(getWebElement()).perform();
        });
    }
    public void clickCenter() {
        doJAction("Click in Center of element", () -> {
            getWebElement().getSize(); //for scroll to object
            Actions builder = new Actions(getDriver());
            builder.click(getWebElement()).perform();
        });
    }
    public void mouseOver() {
        doJAction("Move mouse over element", () -> {
            getWebElement().getSize(); //for scroll to object
            Actions builder = new Actions(getDriver());
            builder.moveToElement(getWebElement()).build().perform();
        });
    }
    public void focus() {
        doJAction("Focus on element", () -> {
            Dimension size = getWebElement().getSize(); //for scroll to object
            new Actions(getDriver()).moveToElement(getWebElement(), size.width / 2, size.height / 2).build().perform();
        });
    }

    public void selectArea(int x1, int y1, int x2, int y2) {
        doJAction(format("Select area: from %d,%d;to %d,%d", x1, y1, x2, y2), () -> {
            WebElement element = getWebElement();
            new Actions(getDriver()).moveToElement(element, x1, y1).clickAndHold()
                    .moveToElement(element, x2, y2).release().build().perform();
        });
    }
    public void dragAndDropBy(int x, int y) {
        doJAction(format("Drag and drop element: (x,y)=(%s,%s)", x, y), () ->
            new Actions(getDriver()).dragAndDropBy(getWebElement(), x, y).build().perform());
    }

}
