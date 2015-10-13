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
package com.ggasoftware.jdiuitests.implementation.selenium.elements.complex;

import com.ggasoftware.jdiuitests.core.utils.linqInterfaces.JFuncTT;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.GetElementType;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.base.Clickable;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.base.Element;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.interfaces.complex.IDropList;
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
    public DropList(By valueLocator) { super(valueLocator); }
    public DropList(By valueLocator, By optionsNamesLocator) { this(valueLocator, optionsNamesLocator, null);}
    public DropList(By valueLocator, By optionsNamesLocator, By allOptionsNamesLocator) {
        super(optionsNamesLocator, allOptionsNamesLocator); this.button = new GetElementType(valueLocator);
    }

    private GetElementType button = new GetElementType();
    protected Clickable button() { return button.get(new Clickable(), getAvatar()); }
    protected void expandAction(String name) { if (!isDisplayedAction(name)) button().click(); }
    protected void expandAction(int index) { if (!isDisplayedAction(index)) button().click(); }

    @Override
    protected void selectListAction(String... names) {
        if (names == null || names.length == 0)
            return;
        if (button() != null) {
            expandAction(names[0]);
            super.selectListAction(names);
        }
        else
            for(String name : names)
                new Select(getWebElement()).selectByVisibleText(name);
    }
    @Override
    protected void selectListAction(int... indexes) {
        if (indexes == null || indexes.length == 0)
            return;
        if (button() != null) {
            expandAction(indexes[0]);
            super.selectListAction(indexes);
        }
        else
            for(int index : indexes)
                new Select(getWebElement()).selectByIndex(index);
    }
    @Override
    protected void clearAction() {
        if (button() != null)
            expandAction(1);
        super.clearAction();
    }
    @Override
    protected String getValueAction() { return getTextAction(); }
    protected String getTextAction() { return getWebElement().getAttribute("value"); }
    @Override
    public boolean waitDisplayed() {  return button().waitDisplayed(); }
    @Override
    public boolean waitVanished()  { return button().waitVanished(); }

    public Boolean wait(JFuncTT<WebElement, Boolean> resultFunc) {
        return button().wait(resultFunc);
    }
    public <T> T wait(JFuncTT<WebElement, T> resultFunc, JFuncTT<T, Boolean> condition) {
        return button().wait(resultFunc, condition);
    }
    public Boolean wait(JFuncTT<WebElement, Boolean> resultFunc, int timeoutSec) {
        return button().wait(resultFunc, timeoutSec);
    }
    public <T> T wait(JFuncTT<WebElement, T> resultFunc, JFuncTT<T, Boolean> condition, int timeoutSec) {
        return button().wait(resultFunc, condition, timeoutSec);
    }
    public void setAttribute(String attributeName, String value) {
        button().setAttribute(attributeName, value);
    }

    public final String getText() { return actions.getText(this::getTextAction); }
    public final String waitText(String text) { return actions.waitText(text, this::getTextAction); }
    public final String waitMatchText(String regEx) { return actions.waitMatchText(regEx, this::getTextAction); }

    public WebElement getWebElement() { return new Element(getLocator()).getWebElement(); }

    public String getAttribute(String name) {
        return button().getAttribute(name);
    }

    public boolean waitAttribute(String name, String value) {
        return button().waitAttribute(name, value);
    }
}
