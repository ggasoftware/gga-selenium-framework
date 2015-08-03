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
package com.epam.jdi_ui_tests.elements.common;

import com.epam.jdi_ui_tests.elements.base.ClickableText;
import com.epam.jdi_ui_tests.elements.interfaces.common.IButton;
import org.openqa.selenium.By;

/**
 * Button control implementation
 *
 * @author Alexeenko Yan
 */
public class Button extends ClickableText implements IButton {
    public Button() { }
    public Button(By byLocator) { super(byLocator); }

    @Override
    protected Text text() { return new Text(getLocator()) {
        @Override
        protected String getTextAction() { return getWebElement().getAttribute("value"); }
        };
    }

}
