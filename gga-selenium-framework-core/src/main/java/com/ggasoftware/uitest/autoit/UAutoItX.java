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

import com.ggasoftware.uitest.utils.ReporterNGExt;
import com.sun.jna.Native;
import org.openqa.selenium.browserlaunchers.Sleeper;

/**
 * Manage AutoIt instances.
 *
 * @author Belousov Andrey
 */
public class UAutoItX {
    public UAutoItX MouseMove(int x, int y, int s) {
        ReporterNGExt.logAction("", "", String.format("AutoIt - MouseMove: x=%d y=%d s=%d", x, y, s));
        lib().AU3_MouseMove(x, y, s);
        return this;
    }

    public UAutoItX MouseClick(String but, int x, int y) {
        ReporterNGExt.logAction("", "", String.format("AutoIt - MouseClick: button [%s] x=%d y=%d", but, x, y));
        lib().AU3_MouseClick(but, x, y, 1, -1);
        return this;
    }

    public UAutoItX MouseDbClick(String but, int x, int y) {
        ReporterNGExt.logAction("", "", String.format("AutoIt - MouseDbClick: button [%s] x=%d y=%d", but, x, y));
        lib().AU3_MouseClick(but, x, y, 2, -1);
        return this;
    }

    public UAutoItX MouseClickDrag(String but, int x1, int y1, int x2, int y2, int s) {
        ReporterNGExt.logAction("", "", String.format("AutoIt - MouseClickDrag: button [%s] x1=%d y1=%d x2=%d y2=%d s=%d", but, x1, y1, x2, y2, s));
        lib().AU3_MouseClickDrag(but, x1, y1, x2, y2, s);
        return this;
    }

    public int WinWait(String title, String text, int timeout) {
        ReporterNGExt.logAction("", "", String.format("AutoIt - WinWait: title [%s] text [%s] timeout=%d", title, text, timeout));
        lib().AU3_WinWait(title, text, timeout);
        return 0;
    }

    public int WinActive(String title, String text) {
        ReporterNGExt.logAction("", "", String.format("AutoIt - WinActive: title [%s] text [%s]", title, text));
        lib().AU3_WinActive(title, text);
        return 0;
    }

    public UAutoItX WinActivate(String title, String text) {
        ReporterNGExt.logAction("", "", String.format("AutoIt - WinActivate: title [%s] text [%s]", title, text));
        lib().AU3_WinActivate(title, text);
        return this;
    }

    public UAutoItX Send(String key) {
        ReporterNGExt.logAction("", "", String.format("AutoIt - Send: key [%s]", key));
        // arg 1 - the sequence of keys to send
        // arg 2 -  how keys is processed, 0 by default
        lib().AU3_Send(key, 0);
        Sleeper.sleepTight(200);
        return this;
    }

    public int WinExists(String title, String text) {
        ReporterNGExt.logAction("", "", String.format("AutoIt - WinExists: title [%s] text [%s]", title, text));
        return lib().AU3_WinExists(title, text);
    }

    private static UAutoItXLib instance;

    private static UAutoItXLib lib () {
        if (instance == null) {
            String dllpath, dllname;
            if (System.getProperty("sun.arch.data.model").equals("64")){
                dllname = "AutoItX3_x64.dll";
            }else{
                dllname = "AutoItX3.dll";
            }
            if (System.getProperty("AutoItX3.dll.path")!=null){
                dllpath = String.format("%s\\%s", System.getProperty("AutoItX3.dll.path"), dllname);
            }else {
                dllpath = dllname;
            }
            instance = (UAutoItXLib)Native.loadLibrary(dllpath, UAutoItXLib.class);
        }
        return instance;
    }
}
