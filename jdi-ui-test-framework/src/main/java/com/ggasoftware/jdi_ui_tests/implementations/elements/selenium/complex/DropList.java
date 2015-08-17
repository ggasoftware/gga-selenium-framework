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
package com.ggasoftware.jdi_ui_tests.implementations.elements.selenium.complex;

import com.ggasoftware.jdi_ui_tests.core.elements.interfaces.complex.IDropList;
import com.ggasoftware.jdi_ui_tests.implementations.elements.selenium.base.WebBaseElement;
import com.ggasoftware.jdi_ui_tests.implementations.elements.selenium.base.SetValue;
import com.ggasoftware.jdi_ui_tests.implementations.elements.selenium.common.Button;
import com.ggasoftware.jdi_ui_tests.utils.linqInterfaces.JFuncTT;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

/**
 * Select control implementation
 *
 * @author Alexeenko Yan
 * @author Belousov Andrey
 */
public class DropList<TEnum extends Enum> extends MultiSelector<TEnum> implements IDropList<TEnum> {
    public DropList() { super(); }
    public DropList(By valueLocator) { this.valueLocator = valueLocator; }
    public DropList(By valueLocator, By optionsNamesLocator) { super(optionsNamesLocator); this.valueLocator = valueLocator;}
    public DropList(By valueLocator, By optionsNamesLocator, By allOptionsNamesLocator) {
        super(optionsNamesLocator, allOptionsNamesLocator); this.valueLocator = valueLocator;
    }

    private By valueLocator;
    private Button field() { return new Button(valueLocator); }

    @Override
    protected void selectListAction(String... names) {
        if (getLocator() != null) {
            field().click();
            super.selectListAction(names);
        }
        else
            for(String name : names)
                new Select(new WebBaseElement(valueLocator).getWebElement()).selectByValue(name);
    }
    @Override
    protected void selectListAction(int... indexes) {
        if (getLocator() != null) {
            field().click();
            super.selectListAction(indexes);
        }
        else
            for(int index : indexes)
                new Select(new WebBaseElement(valueLocator).getWebElement()).selectByIndex(index);
    }
    @Override
    protected boolean waitSelectedAction(String value) { return field().getText().equals(value); }
    @Override
    protected SetValue setValue() { return new SetValue(() -> field().getText(), super.setValue()); }

    @Override
    public boolean waitVisible() {  return field().waitVisible(); }
    @Override
    public boolean waitInvisible()  { return field().waitInvisible(); }

    public Boolean wait(JFuncTT<WebElement, Boolean> resultFunc) {
        return field().wait(resultFunc);
    }
    public <T> T wait(JFuncTT<WebElement, T> resultFunc, JFuncTT<T, Boolean> condition) {
        return field().wait(resultFunc, condition);
    }
    public Boolean wait(JFuncTT<WebElement, Boolean> resultFunc, int timeoutSec) {
        return field().wait(resultFunc, timeoutSec);
    }
    public <T> T wait(JFuncTT<WebElement, T> resultFunc, JFuncTT<T, Boolean> condition, int timeoutSec) {
        return field().wait(resultFunc, condition, timeoutSec);
    }
    public void setAttribute(String attributeName, String value) {
        field().setAttribute(attributeName, value);
    }
    public final String waitText(String text) { return field().waitText(text); }
    public final String waitMatchText(String regEx) { return field().waitText(regEx); }
    public WebElement getWebElement() { return field().getWebElement(); }
    public final String getText() { return field().getText(); }
    public boolean waitAttribute(String name, String value) {
        return field().waitAttribute(name, value);
    }
}
