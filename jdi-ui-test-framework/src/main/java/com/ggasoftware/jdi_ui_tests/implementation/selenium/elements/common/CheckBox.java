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
package com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.common;

import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.base.Clickable;
import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.interfaces.common.ICheckBox;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * Checkbox control implementation
 *
 * @author Alexeenko Yan
 * @author Belousov Andrey
 */
public class CheckBox extends Clickable implements ICheckBox {
    public CheckBox() { }
    public CheckBox(By byLocator) { super(byLocator); }
    public CheckBox(WebElement webElement) { super(webElement); }

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
    protected boolean isCheckedAction() { return getWebElement().isSelected(); }

    public final void check() {
        actions.check(this::checkAction);
    }
    public final void uncheck() {
        actions.uncheck(this::uncheckAction);
    }
    public final boolean isChecked() {
        return actions.isChecked(this::isCheckedAction);
    }

    public final String getValue() { return actions.getValue(this::getValueAction); }
    public final void setValue(String value) { actions.setValue(value, this::setValueAction); }
}
