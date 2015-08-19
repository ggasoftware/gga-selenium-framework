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
package com.ggasoftware.jdi_ui_tests.core.elements.template.base;

import com.ggasoftware.jdi_ui_tests.core.elements.interfaces.base.IClickable;
import com.ggasoftware.jdi_ui_tests.core.elements.interfaces.base.IHasValue;
import com.ggasoftware.jdi_ui_tests.core.elements.interfaces.common.IText;

import static com.ggasoftware.jdi_ui_tests.utils.common.Timer.getByCondition;
import static java.lang.String.format;

/**
 * Button control implementation
 *
 * @author Alexeenko Yan
 */
public class ClickableText extends Clickable implements IHasValue, IClickable, IText {
    // Actions
    protected String getTextAction();
    protected String getValueAction() { return getTextAction(); }

    // Methods
    public final String getText() {
        return doJActionResult("Get text", this::getTextAction);
    }
    public final String waitText(String text) {
        return doJActionResult(format("Wait text contains '%s'", text),
                () -> getByCondition(this::getTextAction, t -> t.contains(text)));
    }
    public final String waitMatchText(String regEx) {
        return doJActionResult(format("Wait text match regex '%s'", regEx),
                () -> getByCondition(this::getTextAction, t -> t.matches(regEx)));
    }
    public final String getValue() { return doJActionResult("Get value", this::getValueAction); }

}
