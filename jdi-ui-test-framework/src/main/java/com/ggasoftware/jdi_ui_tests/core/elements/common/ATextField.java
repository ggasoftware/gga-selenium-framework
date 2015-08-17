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

import com.ggasoftware.jdi_ui_tests.core.elements.interfaces.common.ITextField;

/**
 * AText Field control implementation
 *
 * @author Alexeenko Yan
 * @author Shubin Konstantin
 * @author Zharov Alexandr
 */
public abstract class ATextField extends AText implements ITextField {
    // Actions
    protected abstract void inputAction(String text);
    protected abstract void clearAction();
    protected abstract void focusAction();

    // Methods
    public final void input(String text) {
        doJAction("Input text '" + text + "' in text field", () -> inputAction(text));
    }
    public final void sendKeys(String text) { input(text); }
    public final void newInput(String text) {
        doJAction("Input new text '" + text + "' in text field", () -> { clearAction(); inputAction(text); });
    }
    public final void clear() {
        doJAction("Clear text field", this::clearAction);
    }
    public final void focus() {
        doJAction("Focus on text field", this::focusAction);
    }
    public final void setValue(String value) { doJAction("Set value", () -> setValueRule(value, this::inputAction)); }

}
