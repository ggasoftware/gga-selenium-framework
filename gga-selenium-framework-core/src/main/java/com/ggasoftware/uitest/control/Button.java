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
package com.ggasoftware.uitest.control;

import com.ggasoftware.uitest.control.interfaces.common.IButton;
import com.ggasoftware.uitest.control.new_controls.common.ClickableText;
import org.openqa.selenium.By;

/**
 * Button control implementation
 *
 * @author Alexeenko Yan
 */
public class Button<ParentPanel> extends ClickableText<ParentPanel> implements IButton<ParentPanel> {
    public Button() {
    }

    public Button(By valueLocator) {
        super(valueLocator);
    }

    //constructors

    /**
     * Initializes element with given locator. Locates own properties of the element by class name, takes given locator and tries
     * to initialize.
     *
     * @param name        - Button Name
     * @param locator     - start it with locator type "id=", "css=", "xpath=" and etc. Locator without type is assigned to xpath
     * @param parentPanel - Panel which contains current button
     */
    public Button(String name, String locator, ParentPanel parentPanel) {
        super(name, locator, parentPanel);
    }

}
