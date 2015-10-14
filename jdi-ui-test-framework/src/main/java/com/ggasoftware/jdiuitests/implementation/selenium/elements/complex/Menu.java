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
package com.ggasoftware.jdiuitests.implementation.selenium.elements.complex;

import com.ggasoftware.jdiuitests.implementation.selenium.elements.interfaces.complex.IMenu;
import org.openqa.selenium.By;

/**
 * RadioButtons control implementation
 *
 * @author Alexeenko Yan
 */
public class Menu<TEnum extends Enum> extends Selector<TEnum> implements IMenu<TEnum> {
    public Menu() {
        super();
    }

    public Menu(By optionsNamesLocatorTemplate) {
        super(optionsNamesLocatorTemplate);
    }

    public Menu(By optionsNamesLocatorTemplate, By allOptionsNamesLocator) {
        super(optionsNamesLocatorTemplate, allOptionsNamesLocator);
    }

}
