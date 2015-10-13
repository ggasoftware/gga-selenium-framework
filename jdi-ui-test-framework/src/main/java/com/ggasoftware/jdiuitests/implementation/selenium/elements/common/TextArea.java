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
package com.ggasoftware.jdiuitests.implementation.selenium.elements.common;

import com.ggasoftware.jdiuitests.implementation.selenium.elements.interfaces.common.ITextArea;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * Text Field control implementation
 *
 * @author Alexeenko Yan
 * @author Shubin Konstantin
 * @author Zharov Alexandr
 */
public class TextArea extends TextField implements ITextArea {
    public TextArea() { }
    public TextArea(By byLocator) { super(byLocator); }
    public TextArea(WebElement webElement) { super(webElement); }

    public final void inputLines(String... textLines) {
        actions.inputLines(this::clearAction, this::inputAction, textLines);
    }
    public final void addNewLine(String textLine) {
        actions.addNewLine(textLine, this::inputAction);
    }
    public final String[] getLines() {
        return actions.getLines(this::getTextAction);
    }
}
