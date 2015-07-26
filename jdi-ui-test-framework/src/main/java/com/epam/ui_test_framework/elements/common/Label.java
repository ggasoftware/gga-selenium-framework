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
import com.epam.ui_test_framework.elements.interfaces.common.ILabel;
import org.openqa.selenium.By;

/**
 * Text Label control implementation
 *
 * @author Alexeenko Yan
 */
public class Label extends ClickableText implements ILabel {
    public Label() { }
    public Label(By byLocator) { super(byLocator); }

}
