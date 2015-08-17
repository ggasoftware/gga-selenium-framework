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
package com.ggasoftware.jdi_ui_tests.core.elements.common;

import com.ggasoftware.jdi_ui_tests.core.elements.base.AClickable;
import com.ggasoftware.jdi_ui_tests.core.elements.interfaces.common.IImage;

/**
 * Image control implementation
 *
 * @author Alexeenko Yan
 */
public abstract class AImage extends AClickable implements IImage {
    // Actions
    protected abstract String getSourceAction();
    protected abstract String getAltAction();

    // Methods
    public String getSource() {
        return doJActionResult("Get image source for element " + this, this::getSourceAction);
    }
    public String getAlt() {
        return doJActionResult("Get image alt for element " + this,
                this::getAltAction);
    }

}
