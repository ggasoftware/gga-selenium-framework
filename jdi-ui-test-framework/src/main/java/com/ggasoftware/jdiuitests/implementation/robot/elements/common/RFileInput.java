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
package com.ggasoftware.jdiuitests.implementation.robot.elements.common;

import com.ggasoftware.jdiuitests.implementation.selenium.elements.common.FileInput;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.interfaces.common.ITextField;
import org.openqa.selenium.By;

import static com.ggasoftware.jdiuitests.implementation.robot.elements.common.RobotF.robot;

/**
 * Text Field control implementation
 *
 * @author Alexeenko Yan
 * @author Shubin Konstantin
 * @author Zharov Alexandr
 */
public class RFileInput extends FileInput implements ITextField {
    public RFileInput() { super(); }
    public RFileInput(By byLocator) { super(byLocator); }

    @Override
    protected void inputAction(String text) {
        getWebElement().click();
        robot.pasteText(text);
    }

}
