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
import com.sun.jna.platform.win32.WinUser;

/**
 * Manage AutoIt instances.
 *
 * @author Belousov Andrey
 */
public interface UAutoItXLib extends Library
{
    public static abstract class LPPOINT extends Structure {
        public int X;
        public int Y;
    }

    public void AU3_Init();

    public int AU3_error();

    public int AU3_AutoItSetOption(String Option, int Value);

    public void AU3_BlockInput(int Flag); // 1 = disable user input, 0 enable user input (to have auto it run without
    // interference)

    public void AU3_CDTray(String Drive, String Action); // drive: ,"open" or "closed"

    public void AU3_ClipGet(byte[] Clip, int BufSize);

    public void AU3_ClipPut(String Clip);

    public int AU3_ControlClick(String Title, String Text, String Control, String Button, int NumClicks, int X, int Y);

    public void AU3_ControlCommand(String Title, String Text, String Control, String Command, String Extra, byte[] Result, int BufSize);

    public void AU3_ControlListView(String Title, String Text, String Control, String Command, String Extra1, String Extra2, byte[] Result, int BufSize);

    public int AU3_ControlDisable(String Title, String Text, String Control);

    public int AU3_ControlEnable(String Title, String Text, String Control);

    public int AU3_ControlFocus(String Title, String Text, String Control);

    public void AU3_ControlGetFocus(String Title, String Text, byte[] ControlWithFocus, int BufSize);

    public void AU3_ControlGetHandle(String Title, String Text, String Control, byte[] RetText, int BufSize);

    public int AU3_ControlGetPosX(String Title, String Text, String Control);

    public int AU3_ControlGetPosY(String Title, String Text, String Control);

    public int AU3_ControlGetPosHeight(String Title, String Text, String Control);

    public int AU3_ControlGetPosWidth(String Title, String Text, String Control);

    public void AU3_ControlGetText(String Title, String Text, String Control, byte[] ControlText, int BufSize);

    public int AU3_ControlHide(String Title, String Text, String Control);

    public int AU3_ControlMove(String Title, String Text, String Control, int X, int Y, int Width, int Height);

    public int AU3_ControlSend(String Title, String Text, String Control, String SendText, int Mode);

    public int AU3_ControlSetText(String Title, String Text, String Control, String ControlText);

    public int AU3_ControlShow(String Title, String Text, String Control);

    public void AU3_DriveMapAdd(String Device, String Share, int Flags, String User, String Pwd, byte[] Result, int BufSize);

    public int AU3_DriveMapDel(String Device);

    public void AU3_DriveMapGet(String Device, byte[] Mapping, int BufSize);

    public int AU3_IniDelete(String Filename, String Section, String Key);

    public void AU3_IniRead(String Filename, String Section, String Key, String Default, byte[] Value, int BufSize);

    public int AU3_IniWrite(String Filename, String Section, String Key, String Value);

    public int AU3_IsAdmin();

    public int AU3_MouseClick(String Button, int X, int Y, int Clicks, int Speed);

    public int AU3_MouseClickDrag(String Button, int X1, int Y1, int X2, int Y2, int Speed);

    public void AU3_MouseDown(String Button);

    public int AU3_MouseGetCursor();

    public int AU3_MouseGetPosX();

    public int AU3_MouseGetPosY();

    public int AU3_MouseMove(int X, int Y, int Speed);

    public void AU3_MouseUp(String Button);

    public void AU3_MouseWheel(String Direction, int Clicks);

    public int AU3_Opt(String Option, int Value);

    public int AU3_PixelChecksum(int Left, int Top, int Right, int Bottom, int Step);

    public int AU3_PixelGetColor(int X, int Y);

    public void AU3_PixelSearch(int Left, int Top, int Right, int Bottom, int Col, int Var, int Step, LPPOINT pPointResult);

    public int AU3_ProcessClose(String Process);

    public int AU3_ProcessExists(String Process);

    public int AU3_ProcessSetPriority(String Process, int Priority);

    public int AU3_ProcessWait(String Process, int Timeout);

    public int AU3_ProcessWaitClose(String Process, int Timeout);

    public int AU3_RegDeleteKey(String Keyname);

    public int AU3_RegDeleteVal(String Keyname, String Valuename);

    public void AU3_RegEnumKey(String Keyname, int Instance, byte[] Result, int BufSize);

    public void AU3_RegEnumVal(String Keyname, int Instance, byte[] Result, int BufSize);

    public void AU3_RegRead(String Keyname, String Valuename, byte[] RetText, int BufSize);

    public int AU3_RegWrite(String Keyname, String Valuename, String Type, String Value);

    public int AU3_Run(String Run, String Dir, int ShowFlags);

    public int AU3_RunAsSet(String User, String Domain, String Password, int Options);

    public int AU3_RunWait(String Run, String Dir, int ShowFlags);

    public void AU3_Send(String SendText, int Mode);

    public int AU3_Shutdown(int Flags);

    public void AU3_Sleep(int Milliseconds);

    public void AU3_StatusbarGetText(String Title, String Text, int Part, byte[] StatusText, int BufSize);

    public void AU3_ToolTip(String Tip, int X, int Y);

    public int AU3_WinActive(String Title, String Text);

    public void AU3_WinActivate(String Title, String Text);

    public int AU3_WinClose(String Title, String Text);

    public int AU3_WinExists(String Title, String Text);

    public int AU3_WinGetCaretPosX();

    public int AU3_WinGetCaretPosY();

    public void AU3_WinGetClassList(String Title, String Text, byte[] RetText, int BufSize);

    public int AU3_WinGetClientSizeHeight(String Title, String Text);

    public int AU3_WinGetClientSizeWidth(String Title, String Text);

    public void AU3_WinGetHandle(String Title, String Text, byte[] RetText, int BufSize);

    public int AU3_WinGetPosX(String Title, String Text);

    public int AU3_WinGetPosY(String Title, String Text);

    public int AU3_WinGetPosHeight(String Title, String Text);

    public int AU3_WinGetPosWidth(String Title, String Text);

    public void AU3_WinGetProcess(String Title, String Text, byte[] RetText, int BufSize);

    public int AU3_WinGetState(String Title, String Text);

    public void AU3_WinGetText(String Title, String Text, byte[] RetText, int BufSize);

    public void AU3_WinGetTitle(String Title, String Text, byte[] RetText, int BufSize);

    public int AU3_WinKill(String Title, String Text);

    public int AU3_WinMenuSelectItem(String Title, String Text, String Item1, String Item2, String Item3, String Item4, String Item5, String Item6, String Item7, String Item8);

    public void AU3_WinMinimizeAll();

    public void AU3_WinMinimizeAllUndo();

    public int AU3_WinMove(String Title, String Text, int X, int Y, int Width, int Height);

    public int AU3_WinSetOnTop(String Title, String Text, int Flag);

    public int AU3_WinSetState(String Title, String Text, int Flags);

    public int AU3_WinSetTitle(String Title, String Text, String NewTitle);

    public int AU3_WinSetTrans(String Title, String Text, int Trans);

    public int AU3_WinWait(String Title, String Text, int Timeout);

    public int AU3_WinWaitActive(String Title, String Text, int Timeout);

    public int AU3_WinWaitClose(String Title, String Text, int Timeout);

    public int AU3_WinWaitNotActive(String Title, String Text, int Timeout);

}
