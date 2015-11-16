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
package com.ggasoftware.jdiuitest.web.selenium.elements.common;

import com.ggasoftware.jdiuitest.web.selenium.elements.base.Clickable;
import com.ggasoftware.jdiuitest.core.interfaces.common.IImage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * Image control implementation
 *
 * @author Alexeenko Yan
 */
public class Image extends Clickable implements IImage {
    public Image() {
    }

    public Image(By byLocator) {
        super(byLocator);
    }

    public Image(WebElement webElement) {
        super(webElement);
    }

    public String getSource() {
        return invoker.doJActionResult("Get image source for Element " + this,
                () -> getWebElement().getAttribute("src"));
    }

    public String getAlt() {
        return invoker.doJActionResult("Get image title for Element " + this,
                () -> getWebElement().getAttribute("alt"));
    }

}
