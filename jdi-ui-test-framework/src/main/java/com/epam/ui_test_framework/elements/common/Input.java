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
package com.epam.ui_test_framework.elements.common;

import com.epam.ui_test_framework.elements.base.Element;
import com.epam.ui_test_framework.elements.interfaces.common.IInput;
import org.openqa.selenium.By;

import static com.epam.ui_test_framework.utils.common.Timer.getByCondition;
import static com.epam.ui_test_framework.utils.settings.FrameworkSettings.asserter;

/**
 * Text Field control implementation
 *
 * @author Alexeenko Yan
 * @author Shubin Konstantin
 * @author Zharov Alexandr
 */
public class Input extends Element implements IInput {
    public Input() { super(); }
    public Input(By byLocator) { super(byLocator); }

    protected void setValueAction(String value) {
        if (value == null) return;
        newInput(value);
    }
    protected String getValueAction() { return getWebElement().getAttribute("value"); }
    protected String getTextAction() { return getValueAction(); }
    protected void inputAction(String text) { getWebElement().sendKeys(text); }
    protected void inputAction(Object obj) { inputAction(obj.toString()); }
    protected void clearAction() { getWebElement().clear(); }
    protected void focusAction() { getWebElement().click(); }

    public final void setValue(String value) { doJAction("Set value", () -> setValueRule.invoke(value, this::setValueAction)); }
    public final String getValue() { return doJActionResult("Get value", this::getValueAction); }
    public final String getText() { return doJActionResult("Get text", this::getTextAction); }
    public final String waitText(String text) { return doJActionResult("Wait text",
            () -> getByCondition(this::getTextAction, t -> t.contains(text)));
    }
    public final String waitTextByRegEx(String regEx) { return doJActionResult("Wait text",
            () -> getByCondition(this::getTextAction, t -> t.matches(regEx)));
    }
    public final void input(String text) {
        doJAction("Input text '" + text + "' in text field",
                () -> setValueRule.invoke(text, this::inputAction));
    }
    public final void newInput(String text) {
        asserter.silentException(() -> setValueRule.invoke(text, t -> {
            clear();
            input(t);
        }));
    }
    public final void input(Object obj) {
        doJAction("Input text '" + obj + "' in text field",
            () -> inputAction(obj));
    }
    public final void newInput(Object obj) {
        doJAction("Input text '" + obj + "' in text field",
                () -> {
                    clearAction(); inputAction(obj); });
    }
    public final void clear() {
        doJAction("Clear text field", this::clearAction);
    }
    public final void focus() {
        doJAction("Focus on text field", this::focusAction);
    }

}
