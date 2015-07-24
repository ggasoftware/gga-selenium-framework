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
import com.epam.ui_test_framework.elements.interfaces.complex.IDropList;
import com.epam.ui_test_framework.elements.common.Text;
import org.openqa.selenium.By;

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

    @Override
    protected void selectListAction(String... names) { new Clickable(valueLocator).click(); super.selectListAction(names); }
    @Override
    protected void selectListAction(int... indexes) { new Clickable(valueLocator).click(); super.selectListAction(indexes); }
    @Override
    protected boolean waitSelectedAction(String value) { return getValueAction().equals(value); }
    @Override
    protected String getValueAction() { return new Text(valueLocator).getText(); }
    @Override
    public boolean waitDisplayed(int seconds) {
        return new Element(valueLocator).waitDisplayed(seconds);
    }
    @Override
    public boolean waitVanished(int seconds)  {
        return new Element(valueLocator).waitVanished();
    }
}
