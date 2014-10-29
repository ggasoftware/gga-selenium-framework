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
package com.ggasoftware.uitest.autoit;

import com.sun.jna.Library;

/**
 * Manage AutoIt instances.
 *
 * @author Belousov Andrey
 */
public interface UAutoItXLib extends Library
{
    int AU3_WinWait(String title, String text, int timeout);
    int AU3_MouseMove(int x, int y, int s);
    int AU3_MouseClick(String button, int x, int y, int clicks, int speed);
    int AU3_MouseClickDrag(String button, int x1, int y1, int x2, int y2, int speed);
    int AU3_WinActive(String title, String text);
    int AU3_WinActivate(String title, String text);
    int AU3_WinExists(String title, String text);

    int AU3_Send(String key, int mode);

}
