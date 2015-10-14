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
package com.ggasoftware.uitest.control.new_controls.complex;

import com.ggasoftware.uitest.control.Element;
import com.ggasoftware.uitest.control.interfaces.complex.IDropList;
import com.ggasoftware.uitest.control.new_controls.base.Clickable;
import com.ggasoftware.uitest.control.new_controls.common.Text;
import org.openqa.selenium.By;

/**
 * Select control implementation
 *
 * @author Alexeenko Yan
 * @author Belousov Andrey
 */
public class DropList<TEnum extends Enum, P> extends MultiSelector<TEnum, P> implements IDropList<TEnum> {
    private By valueLocator;

    public DropList() {
        super();
    }

    public DropList(By valueLocator) {
        this.valueLocator = valueLocator;
    }

    public DropList(By valueLocator, By optionsNamesLocator) {
        super(optionsNamesLocator);
        this.valueLocator = valueLocator;
    }

    public DropList(By valueLocator, By optionsNamesLocator, By allOptionsNamesLocator) {
        super(optionsNamesLocator, allOptionsNamesLocator);
        this.valueLocator = valueLocator;
    }

    @Override
    protected void selectListAction(String... names) {
        new Clickable(valueLocator).click();
        super.selectListAction(names);
    }

    @Override
    protected void selectListAction(int... indexes) {
        new Clickable(valueLocator).click();
        super.selectListAction(indexes);
    }

    @Override
    protected boolean waitSelectedAction(String value) {
        return getValueAction().equals(value);
    }

    @Override
    protected String getValueAction() {
        return new Text(valueLocator).getText();
    }

    @Override
    public boolean waitDisplayed(int seconds) {
        return new Element(valueLocator).waitDisplayed(seconds);
    }

    @Override
    public boolean waitVanished(int seconds) {
        return new Element(valueLocator).waitVanished();
    }
}
