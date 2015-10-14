/**
 * *************************************************************************
 * Copyright (C) 2014 GGA Software Services LLC
 * <p>
 * This file may be distributed and/or modified under the terms of the
 * GNU General Public License version 3 as published by the Free Software
 * Foundation.
 * <p>
 * This file is provided AS IS with NO WARRANTY OF ANY KIND, INCLUDING THE
 * WARRANTY OF DESIGN, MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see <http://www.gnu.org/licenses>.
 * *************************************************************************
 */
package com.ggasoftware.jdiuitests.implementation.selenium.elements.complex;

import com.ggasoftware.jdiuitests.implementation.selenium.elements.GetElementType;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.common.TextField;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.interfaces.complex.IComboBox;
import org.openqa.selenium.By;

/**
 * ComboBox control implementation
 *
 * @author Alexeenko Yan
 */
public class ComboBox<TEnum extends Enum> extends Dropdown<TEnum> implements IComboBox<TEnum> {
    private GetElementType textField = new GetElementType();

    public ComboBox() {
        super();
    }

    public ComboBox(By valueLocator) {
        super(valueLocator);
        textField = new GetElementType(valueLocator);
    }

    public ComboBox(By selectorLocator, By optionsNamesLocatorTemplate) {
        super(selectorLocator, optionsNamesLocatorTemplate);
        textField = new GetElementType(selectorLocator);
    }

    public ComboBox(By selectorLocator, By optionsNamesLocatorTemplate, By valueLocator) {
        super(selectorLocator, optionsNamesLocatorTemplate);
        textField = new GetElementType(valueLocator);
    }

    public ComboBox(By selectorLocator, By optionsNamesLocatorTemplate, By valueLocator, By allOptionsNamesLocator) {
        super(selectorLocator, optionsNamesLocatorTemplate, allOptionsNamesLocator);
        textField = new GetElementType(valueLocator);
    }

    protected TextField textField() {
        return textField.get(new TextField(), getAvatar());
    }

    @Override
    protected void setValueAction(String value) {
        newInput(value);
    }

    @Override
    protected String getTextAction() {
        return textField().getText();
    }

    protected void inputAction(String text) {
        textField().sendKeys(text);
    }

    protected void clearAction() {
        textField().clear();
    }

    protected void focusAction() {
        textField().focus();
    }

    public final void input(String text) {
        actions.input(text, this::inputAction);
    }

    public void sendKeys(String text) {
        input(text);
    }

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
