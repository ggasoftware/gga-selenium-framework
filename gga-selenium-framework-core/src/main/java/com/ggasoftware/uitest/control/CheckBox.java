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
package com.ggasoftware.uitest.control;

import com.ggasoftware.uitest.control.interfaces.common.ICheckBox;
import com.ggasoftware.uitest.control.new_controls.base.Clickable;
import org.openqa.selenium.By;

/**
 * Checkbox control implementation
 *
 * @author Alexeenko Yan
 * @author Belousov Andrey
 */
public class CheckBox<ParentPanel> extends Clickable<ParentPanel> implements ICheckBox<ParentPanel> {
    public CheckBox() { }
    public CheckBox(By valueLocator) { super(valueLocator); }
    //constructor

    /**
     * Initializes element with given locator. Locates own properties of the element by class name, takes given locator and tries
     * to initialize.
     *
     * @param name        - Checkbox name
     * @param locator     - start it with locator type "id=", "css=", "xpath=" and etc. Locator without type is assigned to xpath
     * @param parentPanel - Panel which contains current checkbox
     */
    public CheckBox(String name, String locator, ParentPanel parentPanel) {
        super(name, locator, parentPanel);
    }

    protected ParentPanel checkAction() {
        if (!isCheckedAction())
            clickAction();
        return parent;
    }
    protected ParentPanel uncheckAction() {
        if (isCheckedAction())
            clickAction();
        return parent;
    }
    protected boolean isCheckedAction() { return getWebElement().isSelected(); }
    protected String getValueAction() { return isChecked() + ""; }
    protected void setValueAction(String value) {
        if (value == null) return;
        if (value.toLowerCase().equals("true") || value.toLowerCase().equals("1"))
            check();
        if (value.toLowerCase().equals("false") || value.toLowerCase().equals("0"))
            uncheck();
    }

    public final ParentPanel check() {
        return doJActionResult("Check Checkbox", this::checkAction);
    }
    public final ParentPanel uncheck() {
        return doJActionResult("Uncheck Checkbox", this::uncheckAction);
    }
    public final boolean isChecked() {
        return doJActionResult("IsChecked",
                this::isCheckedAction,
                result -> "Checkbox is " + (result ? "checked" : "unchecked"));
    }

    public final String getValue() { return doJActionResult("Get value", this::getValueAction); }
    public final void setValue(String value) { doJAction("Set value", () -> setValueRule(value, this::setValueAction)); }
}
