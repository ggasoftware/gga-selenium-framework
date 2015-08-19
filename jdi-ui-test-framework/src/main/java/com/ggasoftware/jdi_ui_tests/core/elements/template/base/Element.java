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
package com.ggasoftware.jdi_ui_tests.core.elements.template.base;

import com.ggasoftware.jdi_ui_tests.core.elements.interfaces.base.IElement;
import com.ggasoftware.jdi_ui_tests.core.logger.LogSettings;
import com.ggasoftware.jdi_ui_tests.utils.common.Timer;
import com.ggasoftware.jdi_ui_tests.utils.linqInterfaces.*;
import org.openqa.selenium.By;

import static com.ggasoftware.jdi_ui_tests.core.logger.enums.LogInfoTypes.BUSINESS;
import static com.ggasoftware.jdi_ui_tests.core.logger.enums.LogLevels.DEBUG;
import static com.ggasoftware.jdi_ui_tests.core.settings.JDISettings.*;
import static java.lang.String.format;

/**
 * Base Element control implementation
 *
 * Created by Roman Iovlev
 */
public class Element extends BaseElement implements IElement {
    public Element() { super(); }
    public Element(By byLocator) { super(byLocator); }

    public void highlight();
    public final boolean waitAttribute(String attributeName, String value) {
        return doJActionResult(format("Get Attribute '%s'='%s'", attributeName, value),
                () -> wait(() -> getElement().getAttribute(attributeName, value));
    }
    public final void setAttribute(String attributeName, String value) {
        doJAction(format("Set Attribute '%s'='%s'", attributeName, value),
                () -> getElement().setAttribute(attributeName, value));
    }
    public final boolean waitVisible() {
        return doJActionResult("Wait while element displayed", () -> wait(getElement().isDisplayed()));
    }
    public final boolean waitDisplayed() { return waitVisible(); }
    public final boolean waitInvisible()  {
        setWaitTimeout(timeouts.retryMSec);
        boolean result = timer().wait(() -> {
            try { if (getElement().isDisplayed()) return false; }
            catch (Exception ignore) { }
            return false;
        });
        setWaitTimeout(timeouts.waitElementSec);
        return result;
    }
    public final boolean waitDisappear() { return waitInvisible(); }
    public final boolean waitVanished() { return waitInvisible(); }

    public static <T extends Element> T copy(T element, By newLocator) {
        try {
            T result = (T) element.getClass().newInstance();
            result.elementHandler = new ElementHandler(newLocator, element.elementHandler, result);
            return result;
        } catch (Exception ex) { asserter.exception("Can't copy element: " + element); return null; }
    }

    public final TElement getElement() {
        return doJActionResult("Get web element " + this.toString(), elementHandler::getElement, new LogSettings(DEBUG, BUSINESS));
    }
    public final Boolean wait(JFuncT<Boolean> resultFunc) {
        return wait(resultFunc, result -> result);
    }
    public final <T> T wait(JFuncT<T> resultFunc, JFuncTT<T, Boolean> condition) {
        return timer().getResultByCondition(resultFunc::invoke, condition::invoke);
    }
    public final Boolean wait(JFuncT<Boolean> resultFunc, int timeoutSec) {
        return wait(resultFunc, result -> result, timeoutSec);
    }
    public final <T> T wait(JFuncT<T> resultFunc, JFuncTT<T, Boolean> condition, int timeoutSec) {
        setWaitTimeout(timeoutSec);
        T result = new Timer(timeoutSec).getResultByCondition(resultFunc::invoke, condition::invoke);
        setWaitTimeout(timeouts.waitElementSec);
        return result;
    }
    public final Boolean wait(JFuncTT<TElement, Boolean> resultFunc) {
        return wait(resultFunc, result -> result);
    }
    public final <T> T wait(JFuncTT<TElement, T> resultFunc, JFuncTT<T, Boolean> condition) {
        return timer().getResultByCondition(() -> resultFunc.invoke(getElement()), condition::invoke);
    }
    public final Boolean wait(JFuncTT<TElement, Boolean> resultFunc, int timeoutSec) {
        return wait(resultFunc, result -> result, timeoutSec);
    }
    public final <T> T wait(JFuncTT<TElement, T> resultFunc, JFuncTT<T, Boolean> condition, int timeoutSec) {
        setWaitTimeout(timeoutSec);
        T result = new Timer(timeoutSec).getResultByCondition(() -> resultFunc.invoke(getElement()), condition::invoke);
        setWaitTimeout(timeouts.waitElementSec);
        return result;
    }

}
