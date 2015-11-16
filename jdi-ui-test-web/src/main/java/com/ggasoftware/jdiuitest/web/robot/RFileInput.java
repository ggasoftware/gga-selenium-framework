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
package com.ggasoftware.jdiuitest.web.robot;

import com.ggasoftware.jdiuitest.web.selenium.elements.common.FileInput;
import com.ggasoftware.jdiuitest.core.interfaces.common.ITextField;
import org.openqa.selenium.By;

import static com.ggasoftware.jdiuitest.web.robot.RobotF.robot;

/**
 * Text Field control implementation
 *
 * @author Alexeenko Yan
 * @author Shubin Konstantin
 * @author Zharov Alexandr
 */
public class RFileInput extends FileInput implements ITextField {
    public RFileInput() {
        super();
    }

    public RFileInput(By byLocator) {
        super(byLocator);
    }

    @Override
    protected void inputAction(String text) {
        getWebElement().click();
        robot.pasteText(text);
    }

}
