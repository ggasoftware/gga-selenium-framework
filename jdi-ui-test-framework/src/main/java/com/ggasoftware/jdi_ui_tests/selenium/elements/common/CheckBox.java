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
package com.ggasoftware.jdi_ui_tests.selenium.elements.common;

import com.ggasoftware.jdi_ui_tests.core.elements.interfaces.common.ICheckBox;
import com.ggasoftware.jdi_ui_tests.selenium.elements.base.Clickable;
import com.ggasoftware.jdi_ui_tests.selenium.elements.base.SetValue;
import org.openqa.selenium.By;

/**
 * Checkbox control implementation
 *
 * @author Alexeenko Yan
 * @author Belousov Andrey
 */
public class CheckBox extends Clickable implements ICheckBox {
    public CheckBox() { }
    public CheckBox(By byLocator) { super(byLocator); }

    protected SetValue setValue() { return new SetValue(
        value -> {
            switch (value.toLowerCase()) {
                case "true": case "1": case "check":
                    check(); break;
                case "false": case "0": case "uncheck":
                    uncheck(); break;
        } },
        () -> isChecked()+"");
    }

    protected void checkAction() {
        if (!isCheckedAction())
            clickAction();
    }
    protected void uncheckAction() {
        if (isCheckedAction())
            clickAction();
    }
    protected boolean isCheckedAction() { return getWebElement().isSelected(); }

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

    public final String getValue() { return setValue().getValue(); }
    public final void setValue(String value) { setValue().setValue(value); }
}
