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
package com.ggasoftware.jdi_ui_tests.elements.complex;

import com.ggasoftware.jdi_ui_tests.elements.base.Element;
import com.ggasoftware.jdi_ui_tests.elements.base.SetValue;
import com.ggasoftware.jdi_ui_tests.elements.common.Button;
import com.ggasoftware.jdi_ui_tests.elements.interfaces.complex.IDropDown;
import com.ggasoftware.jdi_ui_tests.utils.linqInterfaces.JFuncTT;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

/**
 * RadioButtons control implementation
 *
 * @author Alexeenko Yan
 */
public class Dropdown<TEnum extends Enum> extends Selector<TEnum> implements IDropDown<TEnum> {
    public Dropdown() { super(); }
    public Dropdown(By selectLocator) { this.selectLocator = selectLocator; }
    public Dropdown(By selectLocator, By optionsNamesLocatorTemplate) { super(optionsNamesLocatorTemplate); this.selectLocator = selectLocator;}
    public Dropdown(By selectLocator, By optionsNamesLocatorTemplate, By allOptionsNamesLocator) {
        super(optionsNamesLocatorTemplate, allOptionsNamesLocator); this.selectLocator = selectLocator;
    }
    private By selectLocator;
    private Button field() { return new Button(selectLocator); }

    @Override
    protected void selectAction(String name) {
        if (getLocator() != null) {
            field().click();
            super.selectAction(name);
        }
        else
            new Select(new Element(selectLocator).getWebElement()).selectByValue(name);
    }
    @Override
    protected void selectByIndexAction(int index) {
        if (getLocator() != null) {
            field().click();
            super.selectByIndexAction(index);
        }
        else
            new Select(new Element(selectLocator).getWebElement()).selectByIndex(index);
    }
    @Override
    protected SetValue setValue() { return  new SetValue(() -> field().getText(), super.setValue()); }
    @Override
    protected boolean waitSelectedAction(String value) { return field().waitText(value).equals(value); }

    @Override
    public boolean waitDisplayed() {  return field().waitDisplayed(); }
    @Override
    public boolean waitVanished()  { return field().waitVanished(); }

    @Override
    public Boolean wait(JFuncTT<WebElement, Boolean> resultFunc) {
        return field().wait(resultFunc);
    }
    @Override
    public <T> T wait(JFuncTT<WebElement, T> resultFunc, JFuncTT<T, Boolean> condition) {
        return field().wait(resultFunc, condition);
    }
    @Override
    public Boolean wait(JFuncTT<WebElement, Boolean> resultFunc, int timeoutSec) {
        return field().wait(resultFunc, timeoutSec);
    }
    @Override
    public <T> T wait(JFuncTT<WebElement, T> resultFunc, JFuncTT<T, Boolean> condition, int timeoutSec) {
        return field().wait(resultFunc, condition, timeoutSec);
    }
    
    public final String getText() { return field().getText(); }
    public final String waitText(String text) { return field().waitText(text); }
    public final String waitMatchText(String regEx) { return field().waitText(regEx); }

    public void setAttribute(String attributeName, String value) {
        field().setAttribute(attributeName, value);
    }
    public WebElement getWebElement() { return field().getWebElement(); }

    public boolean waitAttribute(String name, String value) {
        return field().waitAttribute(name, value);
    }

}
