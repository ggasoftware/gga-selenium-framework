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
import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.common.Button;
import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.interfaces.complex.IDropList;
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
    public By valueLocator;
    protected Button field() { return new Button(valueLocator); }

    protected boolean isExpanded() { return false; }
    @Override
    protected void beforeAction() { if (getLocator() == null || !isExpanded()) getWebElement().click(); }

    @Override
    protected void selectListAction(String... names) {
        if (getLocator() != null) {
            beforeAction();
            super.selectListAction(names);
        }
        else
            for(String name : names)
                new Select(new Element(valueLocator).getWebElement()).selectByValue(name);
    }
    @Override
    protected void selectListAction(int... indexes) {
        if (getLocator() != null) {
            beforeAction();
            super.selectListAction(indexes);
        }
        else
            for(int index : indexes)
                new Select(new Element(valueLocator).getWebElement()).selectByIndex(index);
    }
    @Override
    protected boolean waitSelectedAction(String value) { return getTextAction().equals(value); }
    @Override
    protected String getValueAction() { return getTextAction(); }
    protected String getTextAction() { return getWebElement().getAttribute("value"); }
    @Override
    public boolean waitDisplayed() {  return field().waitDisplayed(); }
    @Override
    public boolean waitVanished()  { return field().waitVanished(); }

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

    public final String getText() { return actions.getText(this::getTextAction); }
    public final String waitText(String text) { return actions.waitText(text, this::getTextAction); }
    public final String waitMatchText(String regEx) { return actions.waitMatchText(regEx, this::getTextAction); }

    public WebElement getWebElement() { return field().getWebElement(); }
    public boolean waitAttribute(String name, String value) {
        return field().waitAttribute(name, value);
    }
}
