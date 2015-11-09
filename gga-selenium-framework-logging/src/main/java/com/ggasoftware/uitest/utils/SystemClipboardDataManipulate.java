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
package com.ggasoftware.uitest.utils;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;

public final class SystemClipboardDataManipulate {

    private SystemClipboardDataManipulate() {
    }

    /**
     * When you do a cut or copy of text in the operating system, the text is
     * stored in the clipboard.
     *
     * The following method returns the content that is currently in the
     * clipboard.
     */
    public static String getClipboardData() {
        String clipboardText;
        try {
            Transferable trans = Toolkit.getDefaultToolkit().getSystemClipboard()
                    .getContents(null);
            if (trans != null
                    && trans.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                clipboardText = (String) trans
                        .getTransferData(DataFlavor.stringFlavor);
                return clipboardText;
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    /**
     * This method will set any parameter string to the system's clipboard.
     */
    public static void setClipboardData(String string) {
        StringSelection stringSelection = new StringSelection(string);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(
                stringSelection, null);
    }
}
