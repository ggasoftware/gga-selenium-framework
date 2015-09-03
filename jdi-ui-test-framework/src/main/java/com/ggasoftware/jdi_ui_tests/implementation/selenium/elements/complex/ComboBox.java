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
package com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.complex;

import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.common.TextField;
import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.interfaces.complex.IComboBox;
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
        textFieldLocator = valueLocator;
    }
    public ComboBox(By selectorLocator, By optionsNamesLocatorTemplate) {
        super(selectorLocator, optionsNamesLocatorTemplate);
        textFieldLocator = selectorLocator;
    }
    public ComboBox(By selectorLocator, By optionsNamesLocatorTemplate, By valueLocator) {
        super(selectorLocator, optionsNamesLocatorTemplate);
        textFieldLocator = valueLocator;
    }
    public ComboBox(By selectorLocator, By optionsNamesLocatorTemplate, By valueLocator, By allOptionsNamesLocator) {
        super(selectorLocator, optionsNamesLocatorTemplate, allOptionsNamesLocator);
        textFieldLocator = valueLocator;
    }
    public By textFieldLocator;
    protected TextField textField() { return new TextField(textFieldLocator); }

    @Override
    protected void setValueAction(String value){ newInput(value);}
    @Override
    protected String getTextAction() { return textField().getText(); }
    protected void inputAction(String text) { textField().sendKeys(text); }
    protected void clearAction() { textField().clear(); }
    protected void focusAction() { textField().focus(); }

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
