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
package com.ggasoftware.uitest.control;

import com.ggasoftware.uitest.control.interfaces.common.IImage;
import com.ggasoftware.uitest.control.new_controls.base.Clickable;
import org.openqa.selenium.By;

import static java.lang.String.format;

/**
 * Image control implementation
 *
 * @author Alexeenko Yan
 */
public class Image<ParentPanel> extends Clickable<ParentPanel> implements IImage<ParentPanel> {
    public Image() { }
    public Image(By valueLocator) { super(valueLocator); }
    //constructor

    /**
     * Initializes element with given locator. Locates own properties of the element by class name, takes given locator and tries
     * to initialize.
     *
     * @param name        - Image name
     * @param locator     - start it with locator type "id=", "css=", "xpath=" and etc. Locator without type is assigned to xpath
     * @param parentPanel - Parent instance
     */
    public Image(String name, String locator, ParentPanel parentPanel) {
        super(name, locator, parentPanel);
    }

    public String getSource() {
        return doJActionResult("Get image source for element " + this,
                () -> getWebElement().getAttribute("src"));
    }
    public String getAlt() {
        return doJActionResult("Get image alt for element " + this,
                () -> getWebElement().getAttribute("alt"));
    }
}
