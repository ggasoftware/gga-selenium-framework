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
        return lib().AU3_MouseClick(new WString(but), x, y, 1, -1);
    }

    public int MouseDbClick(String but, int x, int y) {
        ReporterNGExt.logAction("", "", String.format("AutoIt - MouseDbClick: button [%s] x=%d y=%d", but, x, y));
        return lib().AU3_MouseClick(new WString(but), x, y, 2, -1);
    }

    public int MouseClickDrag(String but, int x1, int y1, int x2, int y2, int s) {
        ReporterNGExt.logAction("", "", String.format("AutoIt - MouseClickDrag: button [%s] x1=%d y1=%d x2=%d y2=%d s=%d", but, x1, y1, x2, y2, s));
        return lib().AU3_MouseClickDrag(new WString(but), x1, y1, x2, y2, s);
    }

    public int WinWait(String title, String text, int timeout) {
        ReporterNGExt.logAction("", "", String.format("AutoIt - WinWait: title [%s] text [%s] timeout=%d", title, text, timeout));
        return lib().AU3_WinWait(new WString(title), new WString(text), timeout);
    }

    public int WinActive(String title, String text) {
        ReporterNGExt.logAction("", "", String.format("AutoIt - WinActive: title [%s] text [%s]", title, text));
        return lib().AU3_WinActive(new WString(title), new WString(text));
    }

    public void WinActivate(String title, String text) {
        ReporterNGExt.logAction("", "", String.format("AutoIt - WinActivate: title [%s] text [%s]", title, text));
        lib().AU3_WinActivate(new WString(title), new WString(text));
    }

    public void Send(String key) {
        ReporterNGExt.logAction("", "", String.format("AutoIt - Send: key [%s]", key));
        lib().AU3_Send(new WString(key), 0);
        Sleeper.sleepTight(200);
    }

    public UAutoItX ControlSend(String Title, String Text, String Control, String SendText, int Mode) {
        ReporterNGExt.logAction("", "", String.format("AutoIt - ControlSend: Title [%s] Text [%s] Control [%s] SendText [%s] Mode [%d]", Title, Text, Control, SendText, Mode));
        lib().AU3_ControlSend(new WString(Title), new WString(Text), new WString(Control), new WString(SendText), Mode);
        Sleeper.sleepTight(200);
        return this;
    }

    public int WinExists(String title, String text) {
        ReporterNGExt.logAction("", "", String.format("AutoIt - WinExists: title [%s] text [%s]", title, text));
        return lib().AU3_WinExists(new WString(title), new WString(text));
    }


    public int Error() {
        return lib().AU3_error();
    }

    public int AutoItSetOption(String Option, int Value) {
        return lib().AU3_AutoItSetOption(new WString(Option), Value);
    }

    public void BlockInput(int Flag) {
        lib().AU3_BlockInput(Flag);
    }

    public void CDTray(String Drive, String Action) {
        lib().AU3_CDTray(new WString(Drive), new WString(Action));
    }

    public void ClipGet(byte[] Clip, int BufSize) {
        lib().AU3_ClipGet(Clip, BufSize);
    }

    public void ClipPut(String Clip) {
        lib().AU3_ClipPut(new WString(Clip));
    }

    public int ControlClick(String Title, String Text, String Control, String Button, int NumClicks, int X, int Y) {
        return lib().AU3_ControlClick(new WString(Title), new WString(Text), new WString(Control), new WString(Button), NumClicks, X, Y);
    }

    public void ControlCommand(String Title, String Text, String Control, String Command, String Extra, byte[] Result, int BufSize) {
        lib().AU3_ControlCommand(new WString(Title), new WString(Text), new WString(Control), new WString(Command), new WString(Extra), Result, BufSize);
    }

    public void ControlListView(String Title, String Text, String Control, String Command, String Extra1, String Extra2, byte[] Result, int BufSize) {
        lib().AU3_ControlListView(new WString(Title), new WString(Text), new WString(Control), new WString(Command), new WString(Extra1), new WString(Extra2), Result, BufSize);
    }

    public int ControlDisable(String Title, String Text, String Control) {
        return lib().AU3_ControlDisable(new WString(Title), new WString(Text), new WString(Control));
    }

    public int ControlEnable(String Title, String Text, String Control) {
        return lib().AU3_ControlEnable(new WString(Title), new WString(Text), new WString(Control));
    }

    public int ControlFocus(String Title, String Text, String Control) {
        return lib().AU3_ControlFocus(new WString(Title), new WString(Text), new WString(Control));
    }

    public void ControlGetFocus(String Title, String Text, byte[] ControlWithFocus, int BufSize) {
        lib().AU3_ControlGetFocus(new WString(Title), new WString(Text), ControlWithFocus, BufSize);
    }

    public void ControlGetHandle(String Title, String Text, String Control, byte[] RetText, int BufSize) {
        lib().AU3_ControlGetHandle(new WString(Title), new WString(Text), new WString(Control), RetText, BufSize);
    }

    public int ControlGetPosX(String Title, String Text, String Control) {
        return lib().AU3_ControlGetPosX(new WString(Title), new WString(Text), new WString(Control));
    }

    public int ControlGetPosY(String Title, String Text, String Control) {
        return lib().AU3_ControlGetPosY(new WString(Title), new WString(Text), new WString(Control));
    }

    public int ControlGetPosHeight(String Title, String Text, String Control) {
        return lib().AU3_ControlGetPosHeight(new WString(Title), new WString(Text), new WString(Control));
    }

    public int ControlGetPosWidth(String Title, String Text, String Control) {
        return lib().AU3_ControlGetPosWidth(new WString(Title), new WString(Text), new WString(Control));
    }

    public void ControlGetText(String Title, String Text, String Control, byte[] ControlText, int BufSize) {
        lib().AU3_ControlGetText(new WString(Title), new WString(Text), new WString(Control), ControlText, BufSize);
    }

    public int ControlHide(String Title, String Text, String Control) {
        return lib().AU3_ControlHide(new WString(Title), new WString(Text), new WString(Control));
    }

    public int ControlMove(String Title, String Text, String Control, int X, int Y, int Width, int Height) {
        return lib().AU3_ControlMove(new WString(Title), new WString(Text), new WString(Control), X, Y, Width, Height);
    }

    public int ControlSetText(String Title, String Text, String Control, String ControlText) {
        return lib().AU3_ControlSetText(new WString(Title), new WString(Text), new WString(Control), new WString(ControlText));
    }

    public int ControlShow(String Title, String Text, String Control) {
        return lib().AU3_ControlShow(new WString(Title), new WString(Text), new WString(Control));
    }

    public void DriveMapAdd(String Device, String Share, int Flags, String User, String Pwd, byte[] Result, int BufSize) {
        lib().AU3_DriveMapAdd(new WString(Device), new WString(Share), Flags, new WString(User), new WString(Pwd), Result, BufSize);
    }

    public int DriveMapDel(String Device) {
        return lib().AU3_DriveMapDel(new WString(Device));
    }

    public void DriveMapGet(String Device, byte[] Mapping, int BufSize) {
        lib().AU3_DriveMapGet(new WString(Device), Mapping, BufSize);
    }

    public int IniDelete(String Filename, String Section, String Key) {
        return lib().AU3_IniDelete(new WString(Filename), new WString(Section), new WString(Key));
    }

    public void IniRead(String Filename, String Section, String Key, String Default, byte[] Value, int BufSize) {
        lib().AU3_IniRead(new WString(Filename), new WString(Section), new WString(Key), new WString(Default), Value, BufSize);
    }

    public int IniWrite(String Filename, String Section, String Key, String Value) {
        return lib().AU3_IniWrite(new WString(Filename), new WString(Section), new WString(Key), new WString(Value));
    }

    public int IsAdmin() {
        return lib().AU3_IsAdmin();
    }

    public int MouseClick(String Button, int X, int Y, int Clicks, int Speed) {
        return lib().AU3_MouseClick(new WString(Button), X, Y, Clicks, Speed);
    }

    public void MouseDown(String Button) {
        lib().AU3_MouseDown(new WString(Button));
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
        lib().AU3_MouseUp(new WString(Button));
    }

    public void MouseWheel(String Direction, int Clicks) {
        lib().AU3_MouseWheel(new WString(Direction), Clicks);
    }

    public int Opt(String Option, int Value) {
        return lib().AU3_Opt(new WString(Option), Value);
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
        return lib().AU3_ProcessClose(new WString(Process));
    }

    public int ProcessExists(String Process) {
        return lib().AU3_ProcessExists(new WString(Process));
    }

    public int ProcessSetPriority(String Process, int Priority) {
        return lib().AU3_ProcessSetPriority(new WString(Process), Priority);
    }

    public int ProcessWait(String Process, int Timeout) {
        return lib().AU3_ProcessWait(new WString(Process), Timeout);
    }

    public int ProcessWaitClose(String Process, int Timeout) {
        return lib().AU3_ProcessWaitClose(new WString(Process), Timeout);
    }

    public int RegDeleteKey(String Keyname) {
        return lib().AU3_RegDeleteKey(new WString(Keyname));
    }

    public int RegDeleteVal(String Keyname, String Valuename) {
        return lib().AU3_RegDeleteVal(new WString(Keyname), new WString(Valuename));
    }

    public void RegEnumKey(String Keyname, int Instance, byte[] Result, int BufSize) {
        lib().AU3_RegEnumKey(new WString(Keyname), Instance, Result, BufSize);
    }

    public void RegEnumVal(String Keyname, int Instance, byte[] Result, int BufSize) {
        lib().AU3_RegEnumVal(new WString(Keyname), Instance, Result, BufSize);
    }

    public void RegRead(String Keyname, String Valuename, byte[] RetText, int BufSize) {
        lib().AU3_RegRead(new WString(Keyname), new WString(Valuename), RetText, BufSize);
    }

    public int RegWrite(String Keyname, String Valuename, String Type, String Value) {
        return lib().AU3_RegWrite(new WString(Keyname), new WString(Valuename), new WString(Type), new WString(Value));
    }

    public int Run(String Run, String Dir, int ShowFlags) {
        return lib().AU3_Run(new WString(Run), new WString(Dir), ShowFlags);
    }

    public int RunAsSet(String User, String Domain, String Password, int Options) {
        return lib().AU3_RunAsSet(new WString(User), new WString(Domain), new WString(Password), Options);
    }

    public int RunWait(String Run, String Dir, int ShowFlags) {
        return lib().AU3_RunWait(new WString(Run), new WString(Dir), ShowFlags);
    }

    public void Send(String SendText, int Mode) {
        lib().AU3_Send(new WString(SendText), Mode);
    }

    public int Shutdown(int Flags) {
        return lib().AU3_Shutdown(Flags);
    }

    public void Sleep(int Milliseconds) {
        lib().AU3_Sleep(Milliseconds);
    }

    public void StatusbarGetText(String Title, String Text, int Part, byte[] StatusText, int BufSize) {
        lib().AU3_StatusbarGetText(new WString(Title), new WString(Text), Part, StatusText, BufSize);
    }

    public void ToolTip(String Tip, int X, int Y) {
        lib().AU3_ToolTip(new WString(Tip), X, Y);
    }

    public int WinClose(String Title, String Text) {
        return lib().AU3_WinClose(new WString(Title), new WString(Text));
    }

    public int WinGetCaretPosX() {
        return lib().AU3_WinGetCaretPosX();
    }

    public int WinGetCaretPosY() {
        return lib().AU3_WinGetCaretPosY();
    }

    public void WinGetClassList(String Title, String Text, byte[] RetText, int BufSize) {
        lib().AU3_WinGetClassList(new WString(Title), new WString(Text), RetText, BufSize);
    }

    public int WinGetClientSizeHeight(String Title, String Text) {
        return lib().AU3_WinGetClientSizeHeight(new WString(Title), new WString(Text));
    }

    public int WinGetClientSizeWidth(String Title, String Text) {
        return lib().AU3_WinGetClientSizeWidth(new WString(Title), new WString(Text));
    }

    public void WinGetHandle(String Title, String Text, byte[] RetText, int BufSize) {
        lib().AU3_WinGetHandle(new WString(Title), new WString(Text), RetText, BufSize);
    }

    public int WinGetPosX(String Title, String Text) {
        return lib().AU3_WinGetPosX(new WString(Title), new WString(Text));
    }

    public int WinGetPosY(String Title, String Text) {
        return lib().AU3_WinGetPosY(new WString(Title), new WString(Text));
    }

    public int WinGetPosHeight(String Title, String Text) {
        return lib().AU3_WinGetPosHeight(new WString(Title), new WString(Text));
    }

    public int WinGetPosWidth(String Title, String Text) {
        return lib().AU3_WinGetPosWidth(new WString(Title), new WString(Text));
    }

    public void WinGetProcess(String Title, String Text, byte[] RetText, int BufSize) {
        lib().AU3_WinGetProcess(new WString(Title), new WString(Text), RetText, BufSize);
    }

    public int WinGetState(String Title, String Text) {
        return lib().AU3_WinGetState(new WString(Title), new WString(Text));
    }

    public void WinGetText(String Title, String Text, byte[] RetText, int BufSize) {
        lib().AU3_WinGetText(new WString(Title), new WString(Text), RetText, BufSize);
    }

    public void WinGetTitle(String Title, String Text, byte[] RetText, int BufSize) {
        lib().AU3_WinGetTitle(new WString(Title), new WString(Text), RetText, BufSize);
    }

    public int WinKill(String Title, String Text) {
        return lib().AU3_WinKill(new WString(Title), new WString(Text));
    }

    public int WinMenuSelectItem(String Title, String Text, String Item1, String Item2, String Item3, String Item4, String Item5, String Item6, String Item7, String Item8) {
        return lib().AU3_WinMenuSelectItem(new WString(Title), new WString(Text), new WString(Item1), new WString(Item2), new WString(Item3), new WString(Item4), new WString(Item5), new WString(Item6), new WString(Item7), new WString(Item8));
    }

    public void WinMinimizeAll() {
        lib().AU3_WinMinimizeAll();
    }

    public void WinMinimizeAllUndo() {
        lib().AU3_WinMinimizeAllUndo();
    }

    public int WinMove(String Title, String Text, int X, int Y, int Width, int Height) {
        return lib().AU3_WinMove(new WString(Title), new WString(Text), X, Y, Width, Height);
    }

    public int WinSetOnTop(String Title, String Text, int Flag) {
        return lib().AU3_WinSetOnTop(new WString(Title), new WString(Text), Flag);
    }

    public int WinSetState(String Title, String Text, int Flags) {
        return lib().AU3_WinSetState(new WString(Title), new WString(Text), Flags);
    }

    public int WinSetTitle(String Title, String Text, String NewTitle) {
        return lib().AU3_WinSetTitle(new WString(Title), new WString(Text), new WString(NewTitle));
    }

    public int WinSetTrans(String Title, String Text, int Trans) {
        return lib().AU3_WinSetTrans(new WString(Title), new WString(Text), Trans);
    }

    public int WinWaitActive(String Title, String Text, int Timeout) {
        return lib().AU3_WinWaitActive(new WString(Title), new WString(Text), Timeout);
    }

    public int WinWaitClose(String Title, String Text, int Timeout) {
        return lib().AU3_WinWaitClose(new WString(Title), new WString(Text), Timeout);
    }

    public int WinWaitNotActive(String Title, String Text, int Timeout) {
        return lib().AU3_WinWaitNotActive(new WString(Title), new WString(Text), Timeout);
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
