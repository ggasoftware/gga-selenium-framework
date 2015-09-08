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

import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.interfaces.common.ITextField;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * Text Field control implementation
 *
 * @author Alexeenko Yan
 * @author Shubin Konstantin
 * @author Zharov Alexandr
 */
public class TextField extends Text implements ITextField {
    public TextField() { super(); }
    public TextField(By byLocator) { super(byLocator); }
    public TextField(WebElement webElement) { super(webElement); }

    @Override
    protected String getTextAction() { return getWebElement().getAttribute("value"); }
    protected void setValueAction(String value) { newInput(value); }
    protected void inputAction(String text) { getWebElement().sendKeys(text); }
    protected void clearAction() { getWebElement().clear(); }
    protected void focusAction() { getWebElement().click(); }

    public final void setValue(String value) { actions.setValue(value, this::setValueAction); }

    public final void input(String text) {
        actions.input(text, this::inputAction);
    }

    public void sendKeys(String text) { input(text); }
    public void newInput(String text) {
        clear();
        input(text);
    }
    public final void clear() {
        actions.clear(this::clearAction);
    }
    public final void focus() {
        actions.focus(this::focusAction);
    }

}
