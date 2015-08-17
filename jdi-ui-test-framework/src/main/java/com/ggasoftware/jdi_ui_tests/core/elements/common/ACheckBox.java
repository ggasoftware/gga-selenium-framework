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
package com.ggasoftware.jdi_ui_tests.core.elements.common;

import com.ggasoftware.jdi_ui_tests.core.elements.base.AClickable;
import com.ggasoftware.jdi_ui_tests.core.elements.interfaces.common.ICheckBox;

/**
 * Checkbox control implementation
 *
 * @author Alexeenko Yan
 * @author Belousov Andrey
 */
public abstract class ACheckBox extends AClickable implements ICheckBox {
    // Actions
    protected void setValueAction(String value) {
        switch (value.toLowerCase()) {
            case "true": case "1": case "check":
                check(); break;
            case "false": case "0": case "uncheck":
                uncheck(); break;
        }
    }
    protected String getValueAction() { return isChecked()+""; }
    protected void checkAction() {
        if (!isCheckedAction())
            clickAction();
    }
    protected void uncheckAction() {
        if (isCheckedAction())
            clickAction();
    }
    protected abstract boolean isCheckedAction();

    // Methods
    public final void check() {
        doJAction("Check Checkbox", this::checkAction);
    }
    public final void uncheck() {
        doJAction("Uncheck Checkbox", this::uncheckAction);
    }
    public final boolean isChecked() {
        return doJActionResult("IsChecked",
                this::isCheckedAction,
                result -> "Checkbox is " + (result ? "checked" : "unchecked"));
    }

    public final String getValue() { return doJActionResult("Get value", this::getValueAction); }
    public final void setValue(String value) { doJAction("Set value", () -> setValueRule(value, this::setValueAction)); }
}
