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
package com.ggasoftware.uitest.control.complex;

import com.ggasoftware.uitest.control.interfaces.IComboBox;
import com.ggasoftware.uitest.control.simple.Input;
import org.openqa.selenium.*;

/**
 * ComboBox control implementation
 *
 * @author Alexeenko Yan
 */
public class ComboBox<TEnum extends Enum> extends Dropdown<TEnum> implements IComboBox<TEnum> {
    public ComboBox() { super(); }
    public ComboBox(By valueLocator) {
        super(valueLocator);
        input = new Input(valueLocator);
    }
    public ComboBox(By valueLocator, By optionsNamesLocatorTemplate) {
        super(valueLocator, optionsNamesLocatorTemplate);
        input = new Input(valueLocator);
    }
    public ComboBox(By valueLocator, By optionsNamesLocatorTemplate, By allOptionsNamesLocator) {
        super(valueLocator, optionsNamesLocatorTemplate, allOptionsNamesLocator);
        input = new Input(valueLocator);
    }
    private Input input;

    protected void inputAction(String text) { input.input(text); }
    protected void clearAction() { input.clear(); }
    protected void focusAction() { input.focus(); }
    @Override
    protected void setValueAction(String value) {
        if (value == null) return;
        newInput(value);
    }

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
