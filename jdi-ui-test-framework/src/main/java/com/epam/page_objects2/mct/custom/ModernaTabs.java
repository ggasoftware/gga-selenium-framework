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
package com.epam.page_objects2.mct.custom;

import com.epam.ui_test_framework.elements.complex.Tabs;
import org.openqa.selenium.By;

/**
 * RadioButtons control implementation
 *
 * @author Alexeenko Yan
 */
public class ModernaTabs<TEnum extends Enum> extends Tabs<TEnum> {
    public ModernaTabs() { super(); }
    public ModernaTabs(By optionsNamesLocatorTemplate) { super(optionsNamesLocatorTemplate); }
    public ModernaTabs(By optionsNamesLocatorTemplate, By allOptionsNamesLocator) {
        super(optionsNamesLocatorTemplate, allOptionsNamesLocator);
    }

    @Override
    protected boolean waitSelectedAction(String value) {
        return getWebElement(value).getAttribute("class").contains("k-state-active");
    }

}
