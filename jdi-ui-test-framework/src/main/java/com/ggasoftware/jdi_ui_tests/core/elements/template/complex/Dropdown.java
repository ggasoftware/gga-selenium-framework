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
package com.ggasoftware.jdi_ui_tests.core.elements.template.complex;

import com.ggasoftware.jdi_ui_tests.core.elements.interfaces.complex.IDropDown;

import static com.ggasoftware.jdi_ui_tests.core.settings.JDISettings.timeouts;
import static com.ggasoftware.jdi_ui_tests.utils.common.Timer.getByCondition;
import static java.lang.String.format;

/**
 * RadioButtons control implementation
 *
 * @author Alexeenko Yan
 */
public class Dropdown<TEnum extends Enum> extends Selector<TEnum> implements IDropDown<TEnum> {
    // Actions
    protected void clickAction();
    protected String getTextAction();
    protected boolean waitAttributeAction(String attributeName, String value);
    protected void setAttributeAction(String attributeName, String value);
    protected boolean waitDisplayedAction();
    protected boolean isVanishedAction();
    @Override
    protected String getValueAction() { return getTextAction(); }
    @Override
    protected void selectAction(String name) { clickAction(); super.selectAction(name); }
    @Override
    protected void selectByIndexAction(int index) { clickAction(); super.selectByIndexAction(index); }
    @Override
    protected boolean waitSelectedAction(String value) { return waitText(value).equals(value); }

    // Methods
    public final String getText() {
        return doJActionResult("Get text", this::getTextAction);
    }
    public final String waitText(String text) {
        return doJActionResult(format("Wait text contains '%s'", text),
                () -> getByCondition(this::getTextAction, t -> t.contains(text)));
    }
    public final String waitMatchText(String regEx) {
        return doJActionResult(format("Wait text match regex '%s'", regEx),
                () -> getByCondition(this::getTextAction, t -> t.matches(regEx)));
    }
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

}
