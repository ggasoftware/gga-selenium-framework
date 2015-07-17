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

import com.epam.ui_test_framework.elements.base.Clickable;
import com.epam.ui_test_framework.elements.interfaces.base.IClickableText;
import com.epam.ui_test_framework.elements.interfaces.base.IHaveValue;
import org.openqa.selenium.By;

import static com.epam.ui_test_framework.utils.common.Timer.getByCondition;
import static java.lang.String.format;

/**
 * Button control implementation
 *
 * @author Alexeenko Yan
 */
public class ClickableText extends Clickable implements IClickableText, IHaveValue {
    public ClickableText() { }
    public ClickableText(By byLocator) { super(byLocator); }

    protected String getTextAction() { return getWebElement().getText(); }
    protected String getValueAction() { return getTextAction(); }

    public final String getText() { return doJActionResult("Get text", this::getTextAction); }
    public final String getValue() { return doJActionResult("Get value", this::getTextAction); }
    public final String waitText(String text) { return doJActionResult(format("Wait text contains '%s'", text),
            () -> getByCondition(this::getTextAction, t -> t.contains(text)));
    }
    public final String waitMatchText(String regEx) { return doJActionResult(format("Wait text match regex '%s'", regEx),
            () -> getByCondition(this::getTextAction, t -> t.matches(regEx)));
    }
}
