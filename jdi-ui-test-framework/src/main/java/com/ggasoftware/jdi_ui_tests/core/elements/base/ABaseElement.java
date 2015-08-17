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
package com.ggasoftware.jdi_ui_tests.core.elements.base;

import com.ggasoftware.jdi_ui_tests.core.elements.interfaces.base.IElement;
import com.ggasoftware.jdi_ui_tests.utils.common.Timer;
import com.ggasoftware.jdi_ui_tests.utils.linqInterfaces.JFuncTT;

import static com.ggasoftware.jdi_ui_tests.core.settings.JDISettings.timeouts;
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
public abstract class ABaseElement extends ABase implements IElement {
    // Actions
    protected abstract boolean waitAttributeAction(String attributeName, String value);
    protected abstract void setAttributeAction(String attributeName, String value);
    protected abstract boolean waitDisplayedAction();
    protected abstract boolean isVanishedAction();

    // Methods
    public boolean waitAttribute(String attributeName, String value) {
        return doJActionResult(format("Get Attribute '%s'='%s'", attributeName, value),
                () -> waitAttributeAction(attributeName, value));
    }
    public void setAttribute(String attributeName, String value) {
        doJAction(format("Set Attribute '%s'='%s'", attributeName, value),
                () -> setAttributeAction(attributeName, value));
    }
    public boolean waitVisible() {
        return doJActionResult("Wait while element displayed", this::waitDisplayedAction);
    }
    public boolean waitInvisible()  {
        setWaitTimeout(timeouts.retryMSec);
        boolean result = timer().wait(this::isVanishedAction);
        setWaitTimeout(timeouts.waitElementSec);
        return result;
    }


    public Boolean wait(JFuncTT<ABaseElement, Boolean> resultFunc) {
        return wait(resultFunc, result -> result);
    }
    public <T> T wait(JFuncTT<ABaseElement, T> resultFunc, JFuncTT<T, Boolean> condition) {
        return timer().getResultByCondition(() -> resultFunc.invoke(this), condition::invoke);
    }
    public Boolean wait(JFuncTT<ABaseElement, Boolean> resultFunc, int timeoutSec) {
        return wait(resultFunc, result -> result, timeoutSec);
    }
    public <T> T wait(JFuncTT<ABaseElement, T> resultFunc, JFuncTT<T, Boolean> condition, int timeoutSec) {
        setWaitTimeout(timeoutSec);
        T result = new Timer(timeoutSec).getResultByCondition(() -> resultFunc.invoke(this), condition::invoke);
        setWaitTimeout(timeouts.waitElementSec);
        return result;
    }

}
