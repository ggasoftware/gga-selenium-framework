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

import com.ggasoftware.uitest.control.base.Element;
import com.ggasoftware.uitest.control.interfaces.IInput;
import org.openqa.selenium.By;

/**
 * Text Field control implementation
 *
 * @author Alexeenko Yan
 * @author Shubin Konstantin
 * @author Zharov Alexandr
 */
public class Input extends Element implements IInput {
    public Input() { }
    public Input(By byLocator) { super(byLocator); }

    protected void setValueAction(String value) {
        if (value == null) return;
        newInput(value);
    }
    protected String getValueAction() { return getWebElement().getAttribute("value"); }
    protected String getTextAction() { return getValueAction(); }
    protected void inputAction(String text) { getWebElement().sendKeys(text); }
    protected void clearAction() { getWebElement().clear(); }
    protected void focusAction() { getWebElement().click(); }

    public final void setValue(String value) { doJAction("Set value", () -> setValueAction(value)); }
    public final String getValue() { return doJActionResult("Get value", this::getValueAction); }
    public final String getText() { return doJActionResult("Get text", this::getTextAction); }

    public final void input(String text) {
        doJAction("Input text '" + text + "' in text field", () -> inputAction(text));
    }
    public final void newInput(String text) {
        doJAction("New input text '" + text + "' in text field", () -> {
            clearAction();
            inputAction(text);
        });
    }
    public final void clear() {
        doJAction("Clear text field", this::clearAction);
    }
    public final void focus() {
        doJAction("Focus on text field", this::focusAction);
    }

}
