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
package com.ggasoftware.jdi_ui_tests.elements.base;

import com.ggasoftware.jdi_ui_tests.elements.common.Text;
import com.ggasoftware.jdi_ui_tests.elements.interfaces.base.IClickable;
import com.ggasoftware.jdi_ui_tests.elements.interfaces.base.IHaveValue;
import com.ggasoftware.jdi_ui_tests.elements.interfaces.common.IText;
import org.openqa.selenium.By;

/**
 * Button control implementation
 *
 * @author Alexeenko Yan
 */
public class ClickableText extends Clickable implements IHaveValue, IClickable, IText {
    public ClickableText() { }
    public ClickableText(By byLocator) { super(byLocator); }

    protected Text text() { return new Text(getLocator()); }

    public final String getText() { return text().getText(); }
    public final String getValue() { return text().getValue(); }
    public final String waitText(String text) { return text().waitText(text); }
    public final String waitMatchText(String regEx) { return text().waitMatchText(regEx); }
}
