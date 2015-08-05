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

import com.ggasoftware.jdi_ui_tests.elements.interfaces.complex.ICheckList;
import org.openqa.selenium.By;

/**
 * Select control implementation
 *
 * @author Alexeenko Yan
 * @author Belousov Andrey
 */
public class CheckList<TEnum extends Enum> extends MultiSelector<TEnum> implements ICheckList<TEnum> {
    public CheckList() { super(); }
    public CheckList(By optionsNamesLocator) { super(optionsNamesLocator); }
    public CheckList(By optionsNamesLocator, By allOptionsNamesLocator) {
        super(optionsNamesLocator, allOptionsNamesLocator);
    }
}
