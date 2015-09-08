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
package com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.common;

import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.base.Clickable;
import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.interfaces.common.IImage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * Image control implementation
 *
 * @author Alexeenko Yan
 */
public class Image extends Clickable implements IImage {
    public Image() { }
    public Image(By byLocator) { super(byLocator); }
    public Image(WebElement webElement) { super(webElement); }

    public String getSource() {
        return invoker.doJActionResult("Get image source for webElement " + this,
                () -> getWebElement().getAttribute("src"));
    }
    public String getAlt() {
        return invoker.doJActionResult("Get image alt for webElement " + this,
                () -> getWebElement().getAttribute("alt"));
    }

}
