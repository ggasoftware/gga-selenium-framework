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
package com.ggasoftware.uitest.control.simple;

import com.ggasoftware.uitest.control.base.SetValue;
import com.ggasoftware.uitest.control.interfaces.IChecbox;
import org.openqa.selenium.By;

/**
 * Checkbox control implementation
 *
 * @author Alexeenko Yan
 * @author Belousov Andrey
 */
public class CheckBox extends SetValue implements IChecbox {
    public CheckBox() { }
    public CheckBox(By byLocator) { super(byLocator); }
    public CheckBox(String name, By byLocator) { super(name, byLocator); }

    protected void checkAction() throws Exception {
        if (!isCheckedAction())
            click();
    }
    protected void uncheckAction() throws Exception {
        if (isCheckedAction())
            click();
    }
    protected boolean isCheckedAction() throws Exception { return getWebElement().isSelected(); }
    @Override
    protected String getValueAction() throws Exception { return isChecked().toString(); }
    @Override
    protected void setValueAction(String value) throws Exception {
        if (value.toLowerCase().equals("true") || value.toLowerCase().equals("1"))
            check();
        if (value.toLowerCase().equals("false") || value.toLowerCase().equals("0"))
            uncheck();
    }
    protected void clickAction() throws Exception { getWebElement().click(); }

    public final void check() throws Exception {
        doJAction("Check Checkbox", this::checkAction);
    }
    public final void uncheck() throws Exception {
        doJAction("Uncheck Checkbox", this::uncheckAction);
    }
    public final Boolean isChecked() throws Exception {
        return doJActionResult("IsChecked",
                this::isCheckedAction,
                result -> "Checkbox is " + (result ? "checked" : "unchecked"));
    }
    public final void click() throws Exception {
        doJAction("Click on element", this::clickAction);
    }

}
