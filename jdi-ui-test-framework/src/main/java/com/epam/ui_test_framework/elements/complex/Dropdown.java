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

import com.epam.ui_test_framework.elements.base.Clickable;
import com.epam.ui_test_framework.elements.base.Element;
import com.epam.ui_test_framework.elements.interfaces.base.IElement;
import com.epam.ui_test_framework.elements.interfaces.complex.IDropDown;
import com.epam.ui_test_framework.elements.common.Text;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static com.epam.ui_test_framework.utils.common.Timer.getByCondition;
import static java.lang.String.format;

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

    @Override
    protected void selectAction(String name) { new Clickable(selectLocator).click(); super.selectAction(name); }
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

    public void setAttribute(String attributeName, String value) {
        jsExecutor().executeScript("arguments[0].setAttribute(arguments[1], arguments[2])",
                getWebElement(), attributeName, value);
    }

    public String getAttribute(String attributeName) {
        return doJActionResult(format("Get Attribute '%s'", attributeName),
                () -> getWebElement().getAttribute(attributeName));
    }
    public WebElement getWebElement() { return new Element(selectLocator).getWebElement(); }
    public WebElement getWebElement(int timeouInSec) { return new Element(selectLocator).getWebElement(); }
    @Deprecated    // Not relevant
    public IElement copy(By newLocator) { return this; }
}
