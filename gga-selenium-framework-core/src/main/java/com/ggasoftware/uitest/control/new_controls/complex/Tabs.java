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

import com.ggasoftware.uitest.control.interfaces.complex.ITabs;
import org.openqa.selenium.By;

/**
 * RadioButtons control implementation
 *
 * @author Alexeenko Yan
 */
public class Tabs<TEnum extends Enum, P> extends Selector<TEnum, P> implements ITabs<TEnum> {
    public Tabs() { super(); }
    public Tabs(By optionsNamesLocatorTemplate) { super(optionsNamesLocatorTemplate); }
    public Tabs(By optionsNamesLocatorTemplate, By allOptionsNamesLocator) {
        super(optionsNamesLocatorTemplate, allOptionsNamesLocator);
    }

}
