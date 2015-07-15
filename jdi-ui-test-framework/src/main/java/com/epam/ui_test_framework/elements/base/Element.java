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
package com.epam.ui_test_framework.elements.base;

import com.epam.ui_test_framework.elements.interfaces.base.IElement;
import com.epam.ui_test_framework.elements.BaseElement;
import com.epam.ui_test_framework.elements.interfaces.simple.IButton;
import com.epam.ui_test_framework.elements.interfaces.simple.IText;
import com.epam.ui_test_framework.elements.common.Button;
import com.epam.ui_test_framework.elements.common.Text;
import com.epam.ui_test_framework.logger.LogSettings;
import com.epam.ui_test_framework.utils.common.Timer;
import com.epam.ui_test_framework.utils.settings.HighlightSettings;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.*;

import java.lang.reflect.Field;
import java.util.List;

import static com.epam.ui_test_framework.elements.page_objects.annotations.AnnotationsUtil.getElementName;
import static com.epam.ui_test_framework.logger.enums.LogInfoTypes.BUSINESS;
import static com.epam.ui_test_framework.logger.enums.LogLevels.DEBUG;
import static com.epam.ui_test_framework.utils.common.LinqUtils.first;
import static com.epam.ui_test_framework.utils.common.ReflectionUtils.getFieldValue;
import static com.epam.ui_test_framework.utils.common.ReflectionUtils.getFields;
import static com.epam.ui_test_framework.utils.settings.FrameworkSettings.asserter;
import static com.epam.ui_test_framework.utils.settings.FrameworkSettings.highlightSettings;
import static com.epam.ui_test_framework.utils.settings.FrameworkSettings.timeouts;
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
        return getWebElement(timeouts.waitElementSec);
    }

    public WebElement getWebElement(int timeouInSec) {
        timeouts.currentTimoutSec = timeouInSec;
        WebElement element = doJActionResult("Get web element " + this.toString(), avatar::getElement, new LogSettings(DEBUG, BUSINESS));
        timeouts.currentTimoutSec = timeouts.waitElementSec;
        return element;
    }

    public static <T extends IElement> T copy(T element, By newLocator) {
        try {
            T result = (T) element.getClass().newInstance();
            result.setAvatar(newLocator, element.getAvatar());
            return result;
        } catch (Exception ex) { asserter.exception("Can't copy element: " + element); return null; }
    }

    protected Button getButton(String funcName) {
        List<Field> fields = getFields(this, IButton.class);
        Field buttonField = first(fields, f -> ((BaseElement) getFieldValue(f, this)).function.toLowerCase().equals(funcName.toLowerCase()));
        Object button;
        if (buttonField == null || (button = getFieldValue(buttonField, this)) == null) {
            asserter.exception(format("Can't find button '%s' for form '%s'", funcName, toString()));
            return null;
        }
        return (Button) button;
    }

    protected Text getTextElement() {
        List<Field> fields = getFields(this, IText.class);
        Field buttonField = first(fields, f -> ((BaseElement) getFieldValue(f, this)).function.toLowerCase().equals("text"));
        Object textElement;
        if (buttonField == null || (textElement = getFieldValue(buttonField, this)) == null) {
            asserter.exception(format("Can't find button 'text' for form '%s'", toString()));
            return null;
        }
        return (Text) textElement;
    }

    public boolean isDisplayed() { return waitDisplayed(0); }
    public boolean waitDisplayed() { return waitDisplayed(timeouts.waitElementSec); }
    public boolean waitDisplayed(int seconds) {
        setWaitTimeout(seconds);
        boolean result = new Timer(seconds*1000).wait(() -> avatar.getElement().isDisplayed());
        setWaitTimeout(timeouts.waitElementSec);
        return result;
    }

    public boolean waitVanished() { return waitDisplayed(timeouts.waitElementSec); }
    public boolean waitVanished(int seconds)  {
        setWaitTimeout(timeouts.retryMSec);
        boolean result = new Timer(seconds*1000).wait(() -> !(avatar.getElement().isDisplayed()));
        setWaitTimeout(timeouts.waitElementSec);
        return result;
    }

    public static void highlight(IElement element) { highlight(element, highlightSettings); }
    public static void highlight(IElement element, HighlightSettings highlightSettings) {
        if (highlightSettings == null)
            highlightSettings = new HighlightSettings();
        String orig = element.getWebElement().getAttribute("style");
        element.setAttribute("style", format("border: 3px solid %s; background-color: %s;", highlightSettings.FrameColor,
                highlightSettings.BgColor));
        try { Thread.sleep(highlightSettings.TimeoutInSec * 1000); } catch (Exception ignore) {}
        element.setAttribute("style", orig);
    }
    public void setAttribute(String attributeName, String value) {
        jsExecutor().executeScript("arguments[0].setAttribute(arguments[1], arguments[2])",
                getWebElement(), attributeName, value);
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

    public String getAttribute(String attributeName) {
        return doJActionResult(format("Get Attribute '%s'", attributeName),
            () -> getWebElement().getAttribute(attributeName));
    }

    public void setAttributeJS(String attributeName, String value) {
        doJAction(format("Set Attribute '%s'='%s'", attributeName, value),
                () -> jsExecutor().executeScript(format("arguments[0].setAttribute('%s',arguments[1]);", attributeName),
                        getWebElement(), value));
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
