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
package com.ggasoftware.jdi_ui_tests.implementations.elements.selenium.base;

import com.ggasoftware.jdi_ui_tests.core.elements.base.ABaseElement;
import com.ggasoftware.jdi_ui_tests.core.elements.interfaces.base.IBaseElement;
import com.ggasoftware.jdi_ui_tests.core.elements.interfaces.base.IElement;
import com.ggasoftware.jdi_ui_tests.core.logger.LogSettings;
import com.ggasoftware.jdi_ui_tests.core.settings.HighlightSettings;
import com.ggasoftware.jdi_ui_tests.utils.common.Timer;
import com.ggasoftware.jdi_ui_tests.utils.linqInterfaces.JFuncTT;
import org.openqa.selenium.WebElement;

import java.lang.reflect.Field;

import static com.ggasoftware.jdi_ui_tests.core.logger.enums.LogInfoTypes.*;
import static com.ggasoftware.jdi_ui_tests.core.logger.enums.LogLevels.*;
import static com.ggasoftware.jdi_ui_tests.core.settings.JDISettings.timeouts;

/**
 * Base Element control implementation
 *
 * @author Roman Iovlev
 */
public class WebBaseElement extends WebBase implements IElement {
    protected boolean waitAttributeAction(String attributeName, String value) {
        return false;
    }

    protected void setAttributeAction(String attributeName, String value) {

    }

    protected boolean waitDisplayedAction() {
        return false;
    }

    protected boolean isVanishedAction() {
        return false;
    }

    protected void setTimeoutAction(long mSeconds) {

    }

    protected void setLocatorFromField(Field field, Object parent, Class<?> type) {

    }

    public <T extends IBaseElement> T copyFromTemplate(T element, String name) {
        return null;
    }

    public void highlight() {

    }

    public void highlight(HighlightSettings highlightSettings) {

    }

    public String printLocator() {
        return null;
    }

    public boolean waitDisplayed() {
        return false;
    }

    public boolean waitDisappear() {
        return false;
    }

    public boolean waitVanished() {
        return false;
    }

    public WebElement getWebElement() {
        return doJActionResult("Get web element " + this.toString(), ::getElement, new LogSettings(DEBUG, BUSINESS));
    }
    public Boolean waitWebElement(JFuncTT<WebElement, Boolean> resultFunc) {
        return waitWebElement(resultFunc, result -> result);
    }
    public <T> T waitWebElement(JFuncTT<WebElement, T> resultFunc, JFuncTT<T, Boolean> condition) {
        return timer().getResultByCondition(() -> resultFunc.invoke(getWebElement()), condition::invoke);
    }

    public Boolean waitWebElement(JFuncTT<WebElement, Boolean> resultFunc, int timeoutSec) {
        return waitWebElement(resultFunc, result -> result, timeoutSec);
    }
    public <T> T waitWebElement(JFuncTT<WebElement, T> resultFunc, JFuncTT<T, Boolean> condition, int timeoutSec) {
        setWaitTimeout(timeoutSec);
        T result = new Timer(timeoutSec).getResultByCondition(() -> resultFunc.invoke(getWebElement()), condition::invoke);
        setWaitTimeout(timeouts.waitElementSec);
        return result;
    }

}
