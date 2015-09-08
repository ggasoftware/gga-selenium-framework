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

import com.ggasoftware.jdi_ui_tests.core.utils.linqInterfaces.JFuncTT;
import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.base.Element;
import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.interfaces.complex.IDropDown;
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

    public By selectLocator;
    protected Element element() { return new Element(selectLocator); }

    @Override
    protected void selectAction(String name) {
        if (getLocator() != null) {
            getWebElement().click();
            super.selectAction(name);
        }
        else
            new Select(new Element(selectLocator).getWebElement()).selectByValue(name);
    }
    @Override
    protected void selectByIndexAction(int index) {
        if (getLocator() != null) {
            getWebElement().click();
            super.selectByIndexAction(index);
        }
        else
            new Select(new Element(selectLocator).getWebElement()).selectByIndex(index);
    }
    @Override
    protected String getValueAction() { return getTextAction(); }
    @Override
    protected boolean waitSelectedAction(String value) { return waitText(value).equals(value); }

    @Override
    public boolean waitDisplayed() {  return element().waitDisplayed(); }
    @Override
    public boolean waitVanished()  { return element().waitVanished(); }

    @Override
    public Boolean wait(JFuncTT<WebElement, Boolean> resultFunc) {
        return element().wait(resultFunc);
    }
    @Override
    public <T> T wait(JFuncTT<WebElement, T> resultFunc, JFuncTT<T, Boolean> condition) {
        return element().wait(resultFunc, condition);
    }
    @Override
    public Boolean wait(JFuncTT<WebElement, Boolean> resultFunc, int timeoutSec) {
        return element().wait(resultFunc, timeoutSec);
    }
    @Override
    public <T> T wait(JFuncTT<WebElement, T> resultFunc, JFuncTT<T, Boolean> condition, int timeoutSec) {
        return element().wait(resultFunc, condition, timeoutSec);
    }
    protected String getTextAction() { return getWebElement().getAttribute("value"); }
    public final String getText() { return actions.getText(this::getTextAction); }
    public final String waitText(String text) {
        return actions.waitText(text, this::getTextAction);
    }
    public final String waitMatchText(String regEx) {
        return actions.waitMatchText(regEx, this::getTextAction);
    }

    public void setAttribute(String attributeName, String value) {
        element().setAttribute(attributeName, value);
    }
    public WebElement getWebElement() { return element().getWebElement(); }

    public boolean waitAttribute(String name, String value) {
        return element().waitAttribute(name, value);
    }

}
