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
package com.ggasoftware.uitest.control.new_controls.common;

import com.ggasoftware.uitest.control.Input;
import com.ggasoftware.uitest.control.interfaces.common.ITextArea;
import org.openqa.selenium.By;

/**
 * Text Field control implementation
 *
 * @author Alexeenko Yan
 * @author Shubin Konstantin
 * @author Zharov Alexandr
 */
public class TextArea<P> extends Input<P> implements ITextArea<P> {
    public TextArea() {
    }

    public TextArea(By byLocator) {
        super(byLocator);
    }

    @Override
    public String getTextAction() {
        return getWebElement().getText();
    }

    public final String[] getLines() {
        return doJActionResult("Get text as lines", () -> getTextAction().split("\\n"));
    }
}
