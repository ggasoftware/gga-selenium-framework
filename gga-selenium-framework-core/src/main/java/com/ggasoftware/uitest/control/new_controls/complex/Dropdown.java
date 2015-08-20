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
package com.ggasoftware.uitest.control.new_controls.complex;

import com.ggasoftware.uitest.control.Element;
import com.ggasoftware.uitest.control.interfaces.base.IElement;
import com.ggasoftware.uitest.control.interfaces.complex.IDropDown;
import com.ggasoftware.uitest.control.new_controls.base.Clickable;
import com.ggasoftware.uitest.control.new_controls.common.Text;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import static com.ggasoftware.uitest.utils.Timer.getByCondition;
import static java.lang.String.format;

/**
 * RadioButtons control implementation
 *
 * @author Alexeenko Yan
 */
public class Dropdown<TEnum extends Enum, P> extends Selector<TEnum, P> implements IDropDown<TEnum, P> {
    public Dropdown() { super(); }
    public Dropdown(By selectLocator) { this.selectLocator = selectLocator; }
    public Dropdown(By selectLocator, By optionsNamesLocatorTemplate) { super(optionsNamesLocatorTemplate); this.selectLocator = selectLocator;}
    public Dropdown(By selectLocator, By optionsNamesLocatorTemplate, By allOptionsNamesLocator) {
        super(optionsNamesLocatorTemplate, allOptionsNamesLocator); this.selectLocator = selectLocator;
    }
    public Dropdown(String name, String locator, P parentPanel) {
        super(name, locator, parentPanel);
    }

    private By selectLocator;
    /**
     * Parent panel which contains current element
     */
    protected P parent;

    @Override
    protected void selectAction(String name) {
        if (getLocator() != null && getLocator().toString().contains("%s")) {
            new Clickable(selectLocator).click();
            super.selectAction(name);
        }
        else
            new Select(templateElement.getWebElement()).selectByValue(name);
    }
    @Override
    protected void selectByIndexAction(int index) { new Clickable(selectLocator).click(); super.selectByIndexAction(index); }
    @Override
    protected String getValueAction() { return new Text(selectLocator).getText(); }
    @Override
    protected boolean waitSelectedAction(String value) { return getValueAction().equals(value); }
    protected String getTextAction() { return getValueAction(); }

    @Override
    public boolean waitDisplayed(int seconds) {
        return new Element(selectLocator).waitDisplayed(seconds);
    }
    @Override
    public boolean waitVanished(int seconds)  {
        return new Element(selectLocator).waitVanished();
    }

    public final String getText() { return getTextAction(); }
    public final String waitText(String text) { return doJActionResult(format("Wait text contains '%s'", text),
            () -> getByCondition(this::getTextAction, t -> t.contains(text)));
    }
    public final String waitMatchText(String regEx) { return doJActionResult(format("Wait text match regex '%s'", regEx),
            () -> getByCondition(this::getTextAction, t -> t.matches(regEx)));
    }

    public P setAttributeJS(String attributeName, String value) {
        jsExecutor().executeScript("arguments[0].setAttribute(arguments[1], arguments[2])",
                getWebElement(), attributeName, value);
        return parent;
    }
    public boolean waitAttribute(String name, String value) {
        return doJActionResult(format("Wait attribute %s='%s'", name, value),
                () -> getWebElement().getAttribute(name).equals(value));
    }
    public WebElement getWebElement() { return new Element(selectLocator).getWebElement(); }
    public WebElement getWebElement(int timeouInSec) { return new Element(selectLocator).getWebElement(); }
    // Not relevant
    public IElement copy(By newLocator) { return this; }
}
