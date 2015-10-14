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
package com.ggasoftware.jdiuitests.implementation.selenium.elements.common;

import com.ggasoftware.jdiuitests.implementation.selenium.elements.base.ClickableText;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.interfaces.common.IButton;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * Button control implementation
 *
 * @author Alexeenko Yan
 */
public class Button extends ClickableText implements IButton {
    public Button() {
    }

    public Button(By byLocator) {
        super(byLocator);
    }

    public Button(WebElement webElement) {
        super(webElement);
    }

    @Override
    protected String getTextAction() {
        return getWebElement().getAttribute("value");
    }

}
