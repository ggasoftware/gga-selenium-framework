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
package com.epam.ui_test_framework.elements.common;

import com.epam.ui_test_framework.elements.base.ClickableText;
import com.epam.ui_test_framework.elements.interfaces.common.ILink;
import org.openqa.selenium.By;

import static com.epam.ui_test_framework.utils.common.Timer.getByCondition;
import static java.lang.String.format;

/**
 * Link control implementation
 *
 * @author Roman Iovlev
 */
public class Link extends ClickableText implements ILink {
    public Link() { }
    public Link(By byLocator) { super(byLocator); }

    protected String getReferenceAction() { return getWebElement().getAttribute("href"); }
    public final String getReference() {
        return doJActionResult("Get Reference", this::getReferenceAction, href -> "Get href of link '" + href + "'");
    }
    public final String waitReference(String text) {
        return doJActionResult(format("Wait link contains '%s'", text),
                () -> getByCondition(this::getReferenceAction, t -> t.contains(text)));
    }
    public final String waitMatchReference(String regEx) {
        return doJActionResult(format("Wait link match regex '%s'", regEx),
                () -> getByCondition(this::getReferenceAction, t -> t.matches(regEx)));
    }
}
