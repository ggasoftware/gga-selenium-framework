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
package com.ggasoftware.uitest.control.complex;

import com.ggasoftware.uitest.control.base.Clickable;
import com.ggasoftware.uitest.control.base.Element;
import com.ggasoftware.uitest.control.interfaces.IDropDown;
import com.ggasoftware.uitest.control.simple.Text;
import org.openqa.selenium.By;

/**
 * RadioButtons control implementation
 *
 * @author Alexeenko Yan
 */
public class Dropdown<TEnum extends Enum> extends Selector<TEnum> implements IDropDown<TEnum> {
    public Dropdown() { super(); }
    public Dropdown(By valueLocator) { this.valueLocator = valueLocator; }
    public Dropdown(By valueLocator, By optionsNamesLocatorTemplate) { super(optionsNamesLocatorTemplate); this.valueLocator = valueLocator;}
    public Dropdown(By valueLocator, By optionsNamesLocatorTemplate, By allOptionsNamesLocator) {
        super(optionsNamesLocatorTemplate, allOptionsNamesLocator); this.valueLocator = valueLocator;
    }

    private By valueLocator;

    @Override
    protected void selectAction(String name) { new Clickable(valueLocator).click(); select(name); }
    @Override
    protected void selectByIndexAction(int index) { new Clickable(valueLocator).click(); select(index); }
    @Override
    protected String getValueAction() { return new Text(valueLocator).getText(); }
    @Override
    protected boolean isSelectedAction(String value) { return getValueAction().equals(value); }
    protected String getTextAction() { return getValueAction(); }

    @Override
    public boolean waitDisplayed(int seconds) {
        return new Element(valueLocator).waitDisplayed(seconds);
    }
    @Override
    public boolean waitVanished(int seconds)  {
        return new Element(valueLocator).waitVanished();
    }

    public final String getText() { return getTextAction(); }
}
