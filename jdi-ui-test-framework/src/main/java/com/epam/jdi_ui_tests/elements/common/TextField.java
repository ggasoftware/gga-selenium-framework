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
package com.epam.jdi_ui_tests.elements.common;

import com.epam.jdi_ui_tests.elements.base.SetValue;
import com.epam.jdi_ui_tests.elements.interfaces.common.ITextField;
import org.openqa.selenium.By;

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

    protected SetValue setValue() { return new SetValue(this::newInput, this::getTextAction); }
    @Override
    protected String getTextAction() { return getWebElement().getAttribute("value"); }
    protected void inputAction(String text) { getWebElement().sendKeys(text); }
    protected void clearAction() { getWebElement().clear(); }
    protected void focusAction() { getWebElement().click(); }

    public final void setValue(String value) { setValue().setValue(value); }

    public final void input(String text) {
        doJAction("Input text '" + text + "' in text field",
                () -> setValueRule(text, this::inputAction));
    }

    public final void sendKeys(String text) { input(text); }
    public final void newInput(String text) {
        setValueRule(text, t -> {
            clear();
            input(t);
        });
    }
    public final void clear() {
        doJAction("Clear text field", this::clearAction);
    }
    public final void focus() {
        doJAction("Focus on text field", this::focusAction);
    }

}
