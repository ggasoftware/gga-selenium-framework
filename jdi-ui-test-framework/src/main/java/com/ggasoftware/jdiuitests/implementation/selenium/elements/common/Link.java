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
import com.ggasoftware.jdiuitests.implementation.selenium.elements.interfaces.common.ILink;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static com.ggasoftware.jdiuitests.core.utils.common.Timer.getByCondition;
import static java.lang.String.format;

/**
 * Link control implementation
 *
 * @author Roman Iovlev
 */
public class Link extends ClickableText implements ILink {
    public Link() {
    }

    public Link(By byLocator) {
        super(byLocator);
    }

    public Link(WebElement webElement) {
        super(webElement);
    }

    protected String getReferenceAction() {
        return getWebElement().getAttribute("href");
    }

    public final String getReference() {
        return invoker.doJActionResult("Get link reference", this::getReferenceAction, href -> "Get href of link '" + href + "'");
    }

    public final String waitReference(String text) {
        return invoker.doJActionResult(format("Wait link contains '%s'", text),
                () -> getByCondition(this::getReferenceAction, t -> t.contains(text)));
    }

    public final String waitMatchReference(String regEx) {
        return invoker.doJActionResult(format("Wait link match regex '%s'", regEx),
                () -> getByCondition(this::getReferenceAction, t -> t.matches(regEx)));
    }

    protected String getTooltipAction() {
        return getWebElement().getAttribute("title");
    }

    public final String getTooltip() {
        return invoker.doJActionResult("Get link tooltip", this::getTooltipAction, href -> "Get link tooltip '" + href + "'");
    }
}
