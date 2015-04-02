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
import com.sun.jna.Structure;
import com.sun.jna.WString;

/**
 * Manage AutoIt instances.
 *
 * @author Belousov Andrey
 */
public interface UAutoItXLib extends Library
{
    abstract class LPPOINT extends Structure {
        public int X;
        public int Y;
    }

    void AU3_Init();

    int AU3_error();

    int AU3_AutoItSetOption(WString Option, int Value);

    void AU3_BlockInput(int Flag); // 1 = disable user input, 0 enable user input (to have auto it run without
    // interference)

    void AU3_CDTray(WString Drive, WString Action); // drive: ,"open" or "closed"

    void AU3_ClipGet(byte[] Clip, int BufSize);

    void AU3_ClipPut(WString Clip);

    int AU3_ControlClick(WString Title, WString Text, WString Control, WString Button, int NumClicks, int X, int Y);

    void AU3_ControlCommand(WString Title, WString Text, WString Control, WString Command, WString Extra, byte[] Result, int BufSize);

    void AU3_ControlListView(WString Title, WString Text, WString Control, WString Command, WString Extra1, WString Extra2, byte[] Result, int BufSize);

    int AU3_ControlDisable(WString Title, WString Text, WString Control);

    int AU3_ControlEnable(WString Title, WString Text, WString Control);

    int AU3_ControlFocus(WString Title, WString Text, WString Control);

    void AU3_ControlGetFocus(WString Title, WString Text, byte[] ControlWithFocus, int BufSize);

    void AU3_ControlGetHandle(WString Title, WString Text, WString Control, byte[] RetText, int BufSize);

    int AU3_ControlGetPosX(WString Title, WString Text, WString Control);

    int AU3_ControlGetPosY(WString Title, WString Text, WString Control);

    int AU3_ControlGetPosHeight(WString Title, WString Text, WString Control);

    int AU3_ControlGetPosWidth(WString Title, WString Text, WString Control);

    void AU3_ControlGetText(WString Title, WString Text, WString Control, byte[] ControlText, int BufSize);

    int AU3_ControlHide(WString Title, WString Text, WString Control);

    int AU3_ControlMove(WString Title, WString Text, WString Control, int X, int Y, int Width, int Height);

    int AU3_ControlSend(WString Title, WString Text, WString Control, WString SendText, int Mode);

    int AU3_ControlSetText(WString Title, WString Text, WString Control, WString ControlText);

    int AU3_ControlShow(WString Title, WString Text, WString Control);

    void AU3_DriveMapAdd(WString Device, WString Share, int Flags, WString User, WString Pwd, byte[] Result, int BufSize);

    int AU3_DriveMapDel(WString Device);

    void AU3_DriveMapGet(WString Device, byte[] Mapping, int BufSize);

    int AU3_IniDelete(WString Filename, WString Section, WString Key);

    void AU3_IniRead(WString Filename, WString Section, WString Key, WString Default, byte[] Value, int BufSize);

    int AU3_IniWrite(WString Filename, WString Section, WString Key, WString Value);

    int AU3_IsAdmin();

    int AU3_MouseClick(WString Button, int X, int Y, int Clicks, int Speed);

    int AU3_MouseClickDrag(WString Button, int X1, int Y1, int X2, int Y2, int Speed);

    void AU3_MouseDown(WString Button);

    int AU3_MouseGetCursor();

    int AU3_MouseGetPosX();

    int AU3_MouseGetPosY();

    int AU3_MouseMove(int X, int Y, int Speed);

    void AU3_MouseUp(WString Button);

    void AU3_MouseWheel(WString Direction, int Clicks);

    int AU3_Opt(WString Option, int Value);

    int AU3_PixelChecksum(int Left, int Top, int Right, int Bottom, int Step);

    int AU3_PixelGetColor(int X, int Y);

    void AU3_PixelSearch(int Left, int Top, int Right, int Bottom, int Col, int Var, int Step, LPPOINT pPointResult);

    int AU3_ProcessClose(WString Process);

    int AU3_ProcessExists(WString Process);

    int AU3_ProcessSetPriority(WString Process, int Priority);

    int AU3_ProcessWait(WString Process, int Timeout);

    int AU3_ProcessWaitClose(WString Process, int Timeout);

    int AU3_RegDeleteKey(WString Keyname);

    int AU3_RegDeleteVal(WString Keyname, WString Valuename);

    void AU3_RegEnumKey(WString Keyname, int Instance, byte[] Result, int BufSize);

    void AU3_RegEnumVal(WString Keyname, int Instance, byte[] Result, int BufSize);

    void AU3_RegRead(WString Keyname, WString Valuename, byte[] RetText, int BufSize);

    int AU3_RegWrite(WString Keyname, WString Valuename, WString Type, WString Value);

    int AU3_Run(WString Run, WString Dir, int ShowFlags);

    int AU3_RunAsSet(WString User, WString Domain, WString Password, int Options);

    int AU3_RunWait(WString Run, WString Dir, int ShowFlags);

    void AU3_Send(WString SendText, int Mode);

    int AU3_Shutdown(int Flags);

    void AU3_Sleep(int Milliseconds);

    void AU3_StatusbarGetText(WString Title, WString Text, int Part, byte[] StatusText, int BufSize);

    void AU3_ToolTip(WString Tip, int X, int Y);

    int AU3_WinActive(WString Title, WString Text);

    void AU3_WinActivate(WString Title, WString Text);

    int AU3_WinClose(WString Title, WString Text);

    int AU3_WinExists(WString Title, WString Text);

    int AU3_WinGetCaretPosX();

    int AU3_WinGetCaretPosY();

    void AU3_WinGetClassList(WString Title, WString Text, byte[] RetText, int BufSize);

    int AU3_WinGetClientSizeHeight(WString Title, WString Text);

    int AU3_WinGetClientSizeWidth(WString Title, WString Text);

    void AU3_WinGetHandle(WString Title, WString Text, byte[] RetText, int BufSize);

    int AU3_WinGetPosX(WString Title, WString Text);

    int AU3_WinGetPosY(WString Title, WString Text);

    int AU3_WinGetPosHeight(WString Title, WString Text);

    int AU3_WinGetPosWidth(WString Title, WString Text);

    void AU3_WinGetProcess(WString Title, WString Text, byte[] RetText, int BufSize);

    int AU3_WinGetState(WString Title, WString Text);

    void AU3_WinGetText(WString Title, WString Text, byte[] RetText, int BufSize);

    void AU3_WinGetTitle(WString Title, WString Text, byte[] RetText, int BufSize);

    int AU3_WinKill(WString Title, WString Text);

    int AU3_WinMenuSelectItem(WString Title, WString Text, WString Item1, WString Item2, WString Item3, WString Item4, WString Item5, WString Item6, WString Item7, WString Item8);

    void AU3_WinMinimizeAll();

    void AU3_WinMinimizeAllUndo();

    int AU3_WinMove(WString Title, WString Text, int X, int Y, int Width, int Height);

    int AU3_WinSetOnTop(WString Title, WString Text, int Flag);

    int AU3_WinSetState(WString Title, WString Text, int Flags);

    int AU3_WinSetTitle(WString Title, WString Text, WString NewTitle);

    int AU3_WinSetTrans(WString Title, WString Text, int Trans);

    int AU3_WinWait(WString Title, WString Text, int Timeout);

    int AU3_WinWaitActive(WString Title, WString Text, int Timeout);

    int AU3_WinWaitClose(WString Title, WString Text, int Timeout);

    int AU3_WinWaitNotActive(WString Title, WString Text, int Timeout);

}
