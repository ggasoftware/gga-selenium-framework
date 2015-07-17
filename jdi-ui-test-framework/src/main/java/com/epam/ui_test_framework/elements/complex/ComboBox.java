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
package com.epam.ui_test_framework.elements.complex;

import com.epam.ui_test_framework.elements.base.SelectElement;
import com.epam.ui_test_framework.elements.common.Input;
import com.epam.ui_test_framework.elements.interfaces.complex.IComboBox;
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
        input = createInputAction(valueLocator);
    }
    public ComboBox(By selectorLocator, By optionsNamesLocatorTemplate) {
        super(selectorLocator, optionsNamesLocatorTemplate);
        input = createInputAction(selectorLocator);
    }
    public ComboBox(By selectorLocator, By optionsNamesLocatorTemplate, By valueLocator) {
        super(selectorLocator, optionsNamesLocatorTemplate);
        input = createInputAction(valueLocator);
    }
    public ComboBox(By selectorLocator, By optionsNamesLocatorTemplate, By valueLocator, By allOptionsNamesLocator) {
        super(selectorLocator, optionsNamesLocatorTemplate, allOptionsNamesLocator);
        input = createInputAction(valueLocator);
    }
    private Input input;
    private SelectElement selector;

    protected Input createInputAction(By valueLocator) { return new Input(valueLocator); }
/*
    protected void inputAction(String text) { input.input(text); }
    protected void inputAction(Object obj) { input.input(obj); }
    protected void clearAction() { input.clear(); }
    protected void focusAction() { input.focus(); }*/
    @Override
    protected void setValueAction(String value) { newInput(value); }
    @Override
    protected String getValueAction() {
        return input.getText();
    }
    public final void input(String text) { input.input(text); }
    public final void newInput(String text) { input.newInput(text); }
    public final void clear() { input.clear(); }
    public final void focus() { input.focus(); }

}
