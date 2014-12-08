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
import com.ggasoftware.uitest.utils.Sleeper;
import com.sun.jna.Native;
import com.sun.jna.WString;

/**
 * Manage AutoIt instances.
 *
 * @author Belousov Andrey
 */
public class UAutoItX {
    public int MouseMove(int x, int y, int s) {
        ReporterNGExt.logAction("", "", String.format("AutoIt - MouseMove: x=%d y=%d s=%d", x, y, s));
        return lib().AU3_MouseMove(x, y, s);
    }

    public int MouseClick(String but, int x, int y) {
        ReporterNGExt.logAction("", "", String.format("AutoIt - MouseClick: button [%s] x=%d y=%d", but, x, y));
        return lib().AU3_MouseClick(but, x, y, 1, -1);
    }

    public int MouseDbClick(String but, int x, int y) {
        ReporterNGExt.logAction("", "", String.format("AutoIt - MouseDbClick: button [%s] x=%d y=%d", but, x, y));
        return lib().AU3_MouseClick(but, x, y, 2, -1);
    }

    public int MouseClickDrag(String but, int x1, int y1, int x2, int y2, int s) {
        ReporterNGExt.logAction("", "", String.format("AutoIt - MouseClickDrag: button [%s] x1=%d y1=%d x2=%d y2=%d s=%d", but, x1, y1, x2, y2, s));
        return lib().AU3_MouseClickDrag(but, x1, y1, x2, y2, s);
    }

    public int WinWait(String title, String text, int timeout) {
        ReporterNGExt.logAction("", "", String.format("AutoIt - WinWait: title [%s] text [%s] timeout=%d", title, text, timeout));
        return lib().AU3_WinWait(title, text, timeout);
    }

    public int WinActive(String title, String text) {
        ReporterNGExt.logAction("", "", String.format("AutoIt - WinActive: title [%s] text [%s]", title, text));
        return lib().AU3_WinActive(title, text);
    }

    public void WinActivate(String title, String text) {
        ReporterNGExt.logAction("", "", String.format("AutoIt - WinActivate: title [%s] text [%s]", title, text));
        lib().AU3_WinActivate(title, text);
    }

    public void Send(String key) {
        ReporterNGExt.logAction("", "", String.format("AutoIt - Send: key [%s]", key));
        lib().AU3_Send(key, 0);
        Sleeper.sleepTight(200);
    }

    public UAutoItX ControlSend(String Title, String Text, String Control, String SendText, int Mode) {
        ReporterNGExt.logAction("", "", String.format("AutoIt - ControlSend: Title [%s] Text [%s] Control [%s] SendText [%s] Mode [%d]", Title, Text, Control, SendText, Mode));
        lib().AU3_ControlSend(Title, Text, Control, SendText, Mode);
        Sleeper.sleepTight(200);
        return this;
    }

    public int WinExists(String title, String text) {
        ReporterNGExt.logAction("", "", String.format("AutoIt - WinExists: title [%s] text [%s]", title, text));
        return lib().AU3_WinExists(title, text);
    }


    public int Error() {
        return lib().AU3_error();
    }

    public int AutoItSetOption(String Option, int Value) {
        return lib().AU3_AutoItSetOption(Option, Value);
    }

    public void BlockInput(int Flag) {
        lib().AU3_BlockInput(Flag);
    }

    public void CDTray(String Drive, String Action) {
        lib().AU3_CDTray(Drive, Action);
    }

    public void ClipGet(byte[] Clip, int BufSize) {
        lib().AU3_ClipGet(Clip, BufSize);
    }

    public void ClipPut(String Clip) {
        lib().AU3_ClipPut(Clip);
    }

    public int ControlClick(String Title, String Text, String Control, String Button, int NumClicks, int X, int Y) {
        return lib().AU3_ControlClick(Title, Text, Control, Button, NumClicks, X, Y);
    }

    public void ControlCommand(String Title, String Text, String Control, String Command, String Extra, byte[] Result, int BufSize) {
        lib().AU3_ControlCommand(Title, Text, Control, Command, Extra, Result, BufSize);
    }

    public void ControlListView(String Title, String Text, String Control, String Command, String Extra1, String Extra2, byte[] Result, int BufSize) {
        lib().AU3_ControlListView(Title, Text, Control, Command, Extra1, Extra2, Result, BufSize);
    }

    public int ControlDisable(String Title, String Text, String Control) {
        return lib().AU3_ControlDisable(Title, Text, Control);
    }

    public int ControlEnable(String Title, String Text, String Control) {
        return lib().AU3_ControlEnable(Title, Text, Control);
    }

    public int ControlFocus(String Title, String Text, String Control) {
        return lib().AU3_ControlFocus(Title, Text, Control);
    }

    public void ControlGetFocus(String Title, String Text, byte[] ControlWithFocus, int BufSize) {
        lib().AU3_ControlGetFocus(Title, Text, ControlWithFocus, BufSize);
    }

    public void ControlGetHandle(String Title, String Text, String Control, byte[] RetText, int BufSize) {
        lib().AU3_ControlGetHandle(Title, Text, Control, RetText, BufSize);
    }

    public int ControlGetPosX(String Title, String Text, String Control) {
        return lib().AU3_ControlGetPosX(Title, Text, Control);
    }

    public int ControlGetPosY(String Title, String Text, String Control) {
        return lib().AU3_ControlGetPosY(Title, Text, Control);
    }

    public int ControlGetPosHeight(String Title, String Text, String Control) {
        return lib().AU3_ControlGetPosHeight(Title, Text, Control);
    }

    public int ControlGetPosWidth(String Title, String Text, String Control) {
        return lib().AU3_ControlGetPosWidth(Title, Text, Control);
    }

    public void ControlGetText(String Title, String Text, String Control, byte[] ControlText, int BufSize) {
        lib().AU3_ControlGetText(Title, Text, Control, ControlText, BufSize);
    }

    public int ControlHide(String Title, String Text, String Control) {
        return lib().AU3_ControlHide(Title, Text, Control);
    }

    public int ControlMove(String Title, String Text, String Control, int X, int Y, int Width, int Height) {
        return lib().AU3_ControlMove(Title, Text, Control, X, Y, Width, Height);
    }

    public int ControlSetText(String Title, String Text, String Control, String ControlText) {
        return lib().AU3_ControlSetText(Title, Text, Control, ControlText);
    }

    public int ControlShow(String Title, String Text, String Control) {
        return lib().AU3_ControlShow(Title, Text, Control);
    }

    public void DriveMapAdd(String Device, String Share, int Flags, String User, String Pwd, byte[] Result, int BufSize) {
        lib().AU3_DriveMapAdd(Device, Share, Flags, User, Pwd, Result, BufSize);
    }

    public int DriveMapDel(String Device) {
        return lib().AU3_DriveMapDel(Device);
    }

    public void DriveMapGet(String Device, byte[] Mapping, int BufSize) {
        lib().AU3_DriveMapGet(Device, Mapping, BufSize);
    }

    public int IniDelete(String Filename, String Section, String Key) {
        return lib().AU3_IniDelete(Filename, Section, Key);
    }

    public void IniRead(String Filename, String Section, String Key, String Default, byte[] Value, int BufSize) {
        lib().AU3_IniRead(Filename, Section, Key, Default, Value, BufSize);
    }

    public int IniWrite(String Filename, String Section, String Key, String Value) {
        return lib().AU3_IniWrite(Filename, Section, Key, Value);
    }

    public int IsAdmin() {
        return lib().AU3_IsAdmin();
    }

    public int MouseClick(String Button, int X, int Y, int Clicks, int Speed) {
        return lib().AU3_MouseClick(Button, X, Y, Clicks, Speed);
    }

    public void MouseDown(String Button) {
        lib().AU3_MouseDown(Button);
    }

    public int MouseGetCursor() {
        return lib().AU3_MouseGetCursor();
    }

    public int MouseGetPosX() {
        return lib().AU3_MouseGetPosX();
    }

    public int MouseGetPosY() {
        return lib().AU3_MouseGetPosY();
    }

    public void MouseUp(String Button) {
        lib().AU3_MouseUp(Button);
    }

    public void MouseWheel(String Direction, int Clicks) {
        lib().AU3_MouseWheel(Direction, Clicks);
    }

    public int Opt(String Option, int Value) {
        return lib().AU3_Opt(Option, Value);
    }

    public int PixelChecksum(int Left, int Top, int Right, int Bottom, int Step) {
        return lib().AU3_PixelChecksum(Left, Top, Right, Bottom, Step);
    }

    public int PixelGetColor(int X, int Y) {
        return lib().AU3_PixelGetColor(X, Y);
    }

    public void PixelSearch(int Left, int Top, int Right, int Bottom, int Col, int Var, int Step, UAutoItXLib.LPPOINT pPointResult) {
        lib().AU3_PixelSearch(Left, Top, Right, Bottom, Col, Var, Step, pPointResult);
    }

    public int ProcessClose(String Process) {
        return lib().AU3_ProcessClose(Process);
    }

    public int ProcessExists(String Process) {
        return lib().AU3_ProcessExists(Process);
    }

    public int ProcessSetPriority(String Process, int Priority) {
        return lib().AU3_ProcessSetPriority(Process, Priority);
    }

    public int ProcessWait(String Process, int Timeout) {
        return lib().AU3_ProcessWait(Process, Timeout);
    }

    public int ProcessWaitClose(String Process, int Timeout) {
        return lib().AU3_ProcessWaitClose(Process, Timeout);
    }

    public int RegDeleteKey(String Keyname) {
        return lib().AU3_RegDeleteKey(Keyname);
    }

    public int RegDeleteVal(String Keyname, String Valuename) {
        return lib().AU3_RegDeleteVal(Keyname, Valuename);
    }

    public void RegEnumKey(String Keyname, int Instance, byte[] Result, int BufSize) {
        lib().AU3_RegEnumKey(Keyname, Instance, Result, BufSize);
    }

    public void RegEnumVal(String Keyname, int Instance, byte[] Result, int BufSize) {
        lib().AU3_RegEnumVal(Keyname, Instance, Result, BufSize);
    }

    public void RegRead(String Keyname, String Valuename, byte[] RetText, int BufSize) {
        lib().AU3_RegRead(Keyname, Valuename, RetText, BufSize);
    }

    public int RegWrite(String Keyname, String Valuename, String Type, String Value) {
        return lib().AU3_RegWrite(Keyname, Valuename, Type, Value);
    }

    public int Run(String Run, String Dir, int ShowFlags) {
        return lib().AU3_Run(Run, Dir, ShowFlags);
    }

    public int RunAsSet(String User, String Domain, String Password, int Options) {
        return lib().AU3_RunAsSet(User, Domain, Password, Options);
    }

    public int RunWait(String Run, String Dir, int ShowFlags) {
        return lib().AU3_RunWait(Run, Dir, ShowFlags);
    }

    public void Send(String SendText, int Mode) {
        lib().AU3_Send(SendText, Mode);
    }

    public int Shutdown(int Flags) {
        return lib().AU3_Shutdown(Flags);
    }

    public void Sleep(int Milliseconds) {
        lib().AU3_Sleep(Milliseconds);
    }

    public void StatusbarGetText(String Title, String Text, int Part, byte[] StatusText, int BufSize) {
        lib().AU3_StatusbarGetText(Title, Text, Part, StatusText, BufSize);
    }

    public void ToolTip(String Tip, int X, int Y) {
        lib().AU3_ToolTip(Tip, X, Y);
    }

    public int WinClose(String Title, String Text) {
        return lib().AU3_WinClose(Title, Text);
    }

    public int WinGetCaretPosX() {
        return lib().AU3_WinGetCaretPosX();
    }

    public int WinGetCaretPosY() {
        return lib().AU3_WinGetCaretPosY();
    }

    public void WinGetClassList(String Title, String Text, byte[] RetText, int BufSize) {
        lib().AU3_WinGetClassList(Title, Text, RetText, BufSize);
    }

    public int WinGetClientSizeHeight(String Title, String Text) {
        return lib().AU3_WinGetClientSizeHeight(Title, Text);
    }

    public int WinGetClientSizeWidth(String Title, String Text) {
        return lib().AU3_WinGetClientSizeWidth(Title, Text);
    }

    public void WinGetHandle(String Title, String Text, byte[] RetText, int BufSize) {
        lib().AU3_WinGetHandle(Title, Text, RetText, BufSize);
    }

    public int WinGetPosX(String Title, String Text) {
        return lib().AU3_WinGetPosX(Title, Text);
    }

    public int WinGetPosY(String Title, String Text) {
        return lib().AU3_WinGetPosY(Title, Text);
    }

    public int WinGetPosHeight(String Title, String Text) {
        return lib().AU3_WinGetPosHeight(Title, Text);
    }

    public int WinGetPosWidth(String Title, String Text) {
        return lib().AU3_WinGetPosWidth(Title, Text);
    }

    public void WinGetProcess(String Title, String Text, byte[] RetText, int BufSize) {
        lib().AU3_WinGetProcess(Title, Text, RetText, BufSize);
    }

    public int WinGetState(String Title, String Text) {
        return lib().AU3_WinGetState(Title, Text);
    }

    public void WinGetText(String Title, String Text, byte[] RetText, int BufSize) {
        lib().AU3_WinGetText(Title, Text, RetText, BufSize);
    }

    public void WinGetTitle(String Title, String Text, byte[] RetText, int BufSize) {
        lib().AU3_WinGetTitle(Title, Text, RetText, BufSize);
    }

    public int WinKill(String Title, String Text) {
        return lib().AU3_WinKill(Title, Text);
    }

    public int WinMenuSelectItem(String Title, String Text, String Item1, String Item2, String Item3, String Item4, String Item5, String Item6, String Item7, String Item8) {
        return lib().AU3_WinMenuSelectItem(Title, Text, Item1, Item2, Item3, Item4, Item5, Item6, Item7, Item8);
    }

    public void WinMinimizeAll() {
        lib().AU3_WinMinimizeAll();
    }

    public void WinMinimizeAllUndo() {
        lib().AU3_WinMinimizeAllUndo();
    }

    public int WinMove(String Title, String Text, int X, int Y, int Width, int Height) {
        return lib().AU3_WinMove(Title, Text, X, Y, Width, Height);
    }

    public int WinSetOnTop(String Title, String Text, int Flag) {
        return lib().AU3_WinSetOnTop(Title, Text, Flag);
    }

    public int WinSetState(String Title, String Text, int Flags) {
        return lib().AU3_WinSetState(Title, Text, Flags);
    }

    public int WinSetTitle(String Title, String Text, String NewTitle) {
        return lib().AU3_WinSetTitle(Title, Text, NewTitle);
    }

    public int WinSetTrans(String Title, String Text, int Trans) {
        return lib().AU3_WinSetTrans(Title, Text, Trans);
    }

    public int WinWaitActive(String Title, String Text, int Timeout) {
        return lib().AU3_WinWaitActive(Title, Text, Timeout);
    }

    public int WinWaitClose(String Title, String Text, int Timeout) {
        return lib().AU3_WinWaitClose(Title, Text, Timeout);
    }

    public int WinWaitNotActive(String Title, String Text, int Timeout) {
        return lib().AU3_WinWaitNotActive(Title, Text, Timeout);
    }


    private static UAutoItXLib instance;

    private static UAutoItXLib lib() {
        if (instance == null) {
            String dllpath, dllname;
            if (System.getProperty("sun.arch.data.model").equals("64")) {
                dllname = "AutoItX3_x64.dll";
            } else {
                dllname = "AutoItX3.dll";
            }
            if (System.getProperty("AutoItX3.dll.path") != null) {
                dllpath = String.format("%s\\%s", System.getProperty("AutoItX3.dll.path"), dllname);
            } else {
                dllpath = dllname;
            }
            instance = (UAutoItXLib) Native.loadLibrary(dllpath, UAutoItXLib.class);
        }
        return instance;
    }
}
