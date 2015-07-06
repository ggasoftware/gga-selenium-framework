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
import com.ggasoftware.uitest.control.interfaces.IInput;
import org.openqa.selenium.By;

/**
 * Text Field control implementation
 *
 * @author Alexeenko Yan
 * @author Shubin Konstantin
 * @author Zharov Alexandr
 */
public class Input extends SetValue implements IInput {
    public Input() { }
    public Input(By byLocator) { super(byLocator); }
    public Input(String name, By byLocator) { super(name, byLocator); }

    protected void inputAction(String text) throws Exception { getWebElement().sendKeys(text); }
    protected void clearAction() throws Exception { getWebElement().clear(); }
    protected void focusAction() throws Exception { getWebElement().click(); }

    @Override
    protected void setValueAction(String value) throws Exception {
        if (value == null) return;
        newInput(value);
    }

    public String getValueAction() throws Exception {
        return getWebElement().getAttribute("value");
    }
    public final String getText() throws Exception { return getValue(); }

    public final void input(String text) throws Exception {
        doJAction("Input text '" + text + "' in text field", () -> inputAction(text));
    }
    public final void newInput(String text) throws Exception {
        doJAction("New input text '" + text + "' in text field", () -> {
            clearAction();
            inputAction(text);
        });
    }
    public final void clear() throws Exception {
        doJAction("Clear text field", this::clearAction);
    }
    public final void focus() throws Exception {
        doJAction("Focus on text field", this::focusAction);
    }


}
