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
package com.ggasoftware.uitest.control.new_controls.common;

import org.openqa.selenium.By;

/**
 * Link control implementation
 *
 * @author Roman Iovlev
 */
public class Link<P> extends ClickableText<P> {
    public Link() { }
    public Link(By byLocator) { super(byLocator); }

    protected String getReferenceAction() { return getWebElement().getAttribute("href"); }
    public final String getReference() {
        return doJActionResult("Get Reference", this::getReferenceAction, href -> "Get href of link '" + href + "'");
    }

}
