package com.epam.page_objects.mct.robot;


/**
 * Created by Fail on 01.06.2015.
 */

import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.event.KeyEvent;
import java.io.IOException;

import static com.epam.ui_test_framework.utils.settings.FrameworkSettings.asserter;
import static java.lang.Thread.sleep;

public class RobotTest implements ClipboardOwner {

    public static RobotTest robot;
    private Robot robotInstance;

    public RobotTest()
    {
        try { this.robotInstance = new Robot(); }
        catch (Exception ex) { asserter.exception(ex.getMessage()); }
        robotInstance.delay(50);
        robotInstance.setAutoDelay(2);
        robotInstance.setAutoWaitForIdle(true);
    }


    public void bufferPaste (String text) {
        try {
            StringSelection stringSelection = new StringSelection(text);
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(stringSelection, this);
            delay(1000);
            robotInstance.keyPress(KeyEvent.VK_CONTROL);
            robotInstance.keyPress(KeyEvent.VK_V);
            robotInstance.keyRelease(KeyEvent.VK_CONTROL);
            roboType(KeyEvent.VK_ENTER);
        } catch (Exception ex) {
            asserter.exception("Robot Input exception");
        }
    }


    public void robotBufferPaste (String text) {
        StringSelection stringSelection = new StringSelection(text);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, this);
        delay(3000);
        robotInstance.keyPress(KeyEvent.VK_CONTROL);
        robotInstance.keyPress(KeyEvent.VK_V);
        robotInstance.keyRelease(KeyEvent.VK_CONTROL);
        delay(500);
        robotInstance.keyPress(KeyEvent.VK_ENTER);
        delay(2000);
    }

    public void setBuffer (String text) {
        StringSelection stringSelection = new StringSelection(text);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, this);
    }

    public String getClipboardContents() {
        String result = "";
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        //odd: the Object param of getContents is not currently used
        Transferable contents = clipboard.getContents(null);
        boolean hasTransferableText =
                (contents != null) &&
                        contents.isDataFlavorSupported(DataFlavor.stringFlavor)
                ;
        if (hasTransferableText) {
            try {
                result = (String)contents.getTransferData(DataFlavor.stringFlavor);
            }
            catch (UnsupportedFlavorException | IOException ex){
                System.out.println(ex);
                ex.printStackTrace();
            }
        }
        return result;
}

    private void delay(Integer millis) {
        try {
            Thread.sleep(millis);
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    public void type(CharSequence characters)
    {
        int length = characters.length();
        for (int i = 0; i < length; i++)
        {
            char character = characters.charAt(i);
            type(character);
        }
    }

    public void inputType(CharSequence characters)
    {
        try {
            sleep(500);
            int length = characters.length();
            for (int i = 0; i < length; i++) {
                char character = characters.charAt(i);
                type(character);
            }
            roboType(KeyEvent.VK_ENTER);
        }
        catch (Exception ex) { asserter.exception("Robot Input exception"); }
    }

    public void type(char character)
    {
        switch (character) {
            case 'a':  roboType(KeyEvent.VK_A); break;
            case 'b':  roboType(KeyEvent.VK_B); break;
            case 'c':  roboType(KeyEvent.VK_C); break;
            case 'd':  roboType(KeyEvent.VK_D); break;
            case 'e':  roboType(KeyEvent.VK_E); break;
            case 'f':  roboType(KeyEvent.VK_F); break;
            case 'g':  roboType(KeyEvent.VK_G); break;
            case 'h':  roboType(KeyEvent.VK_H); break;
            case 'i':  roboType(KeyEvent.VK_I); break;
            case 'j':  roboType(KeyEvent.VK_J); break;
            case 'k':  roboType(KeyEvent.VK_K); break;
            case 'l':  roboType(KeyEvent.VK_L); break;
            case 'm':  roboType(KeyEvent.VK_M); break;
            case 'n':  roboType(KeyEvent.VK_N); break;
            case 'o':  roboType(KeyEvent.VK_O); break;
            case 'p':  roboType(KeyEvent.VK_P); break;
            case 'q':  roboType(KeyEvent.VK_Q); break;
            case 'r':  roboType(KeyEvent.VK_R); break;
            case 's':  roboType(KeyEvent.VK_S); break;
            case 't':  roboType(KeyEvent.VK_T); break;
            case 'u':  roboType(KeyEvent.VK_U); break;
            case 'v':  roboType(KeyEvent.VK_V); break;
            case 'w':  roboType(KeyEvent.VK_W); break;
            case 'x':  roboType(KeyEvent.VK_X); break;
            case 'y':  roboType(KeyEvent.VK_Y); break;
            case 'z':  roboType(KeyEvent.VK_Z); break;
            case 'A':  roboType(KeyEvent.VK_SHIFT, KeyEvent.VK_A); break;
            case 'B':  roboType(KeyEvent.VK_SHIFT, KeyEvent.VK_B); break;
            case 'C':  roboType(KeyEvent.VK_SHIFT, KeyEvent.VK_C); break;
            case 'D':  roboType(KeyEvent.VK_SHIFT, KeyEvent.VK_D); break;
            case 'E':  roboType(KeyEvent.VK_SHIFT, KeyEvent.VK_E); break;
            case 'F':  roboType(KeyEvent.VK_SHIFT, KeyEvent.VK_F); break;
            case 'G':  roboType(KeyEvent.VK_SHIFT, KeyEvent.VK_G); break;
            case 'H':  roboType(KeyEvent.VK_SHIFT, KeyEvent.VK_H); break;
            case 'I':  roboType(KeyEvent.VK_SHIFT, KeyEvent.VK_I); break;
            case 'J':  roboType(KeyEvent.VK_SHIFT, KeyEvent.VK_J); break;
            case 'K':  roboType(KeyEvent.VK_SHIFT, KeyEvent.VK_K); break;
            case 'L':  roboType(KeyEvent.VK_SHIFT, KeyEvent.VK_L); break;
            case 'M':  roboType(KeyEvent.VK_SHIFT, KeyEvent.VK_M); break;
            case 'N':  roboType(KeyEvent.VK_SHIFT, KeyEvent.VK_N); break;
            case 'O':  roboType(KeyEvent.VK_SHIFT, KeyEvent.VK_O); break;
            case 'P':  roboType(KeyEvent.VK_SHIFT, KeyEvent.VK_P); break;
            case 'Q':  roboType(KeyEvent.VK_SHIFT, KeyEvent.VK_Q); break;
            case 'R':  roboType(KeyEvent.VK_SHIFT, KeyEvent.VK_R); break;
            case 'S':  roboType(KeyEvent.VK_SHIFT, KeyEvent.VK_S); break;
            case 'T':  roboType(KeyEvent.VK_SHIFT, KeyEvent.VK_T); break;
            case 'U':  roboType(KeyEvent.VK_SHIFT, KeyEvent.VK_U); break;
            case 'V':  roboType(KeyEvent.VK_SHIFT, KeyEvent.VK_V); break;
            case 'W':  roboType(KeyEvent.VK_SHIFT, KeyEvent.VK_W); break;
            case 'X':  roboType(KeyEvent.VK_SHIFT, KeyEvent.VK_X); break;
            case 'Y':  roboType(KeyEvent.VK_SHIFT, KeyEvent.VK_Y); break;
            case 'Z':  roboType(KeyEvent.VK_SHIFT, KeyEvent.VK_Z); break;
            case '`':  roboType(KeyEvent.VK_BACK_QUOTE); break;
            case '0':  roboType(KeyEvent.VK_0); break;
            case '1':  roboType(KeyEvent.VK_1); break;
            case '2':  roboType(KeyEvent.VK_2); break;
            case '3':  roboType(KeyEvent.VK_3); break;
            case '4':  roboType(KeyEvent.VK_4); break;
            case '5':  roboType(KeyEvent.VK_5); break;
            case '6':  roboType(KeyEvent.VK_6); break;
            case '7':  roboType(KeyEvent.VK_7); break;
            case '8':  roboType(KeyEvent.VK_8); break;
            case '9':  roboType(KeyEvent.VK_9); break;
            case '-':  roboType(KeyEvent.VK_MINUS); break;
            case '=':  roboType(KeyEvent.VK_EQUALS); break;
            case '~':  roboType(KeyEvent.VK_SHIFT, KeyEvent.VK_BACK_QUOTE); break;
            case '!':  roboType(KeyEvent.VK_SHIFT, KeyEvent.VK_1); break;
            case '@':  roboType(KeyEvent.VK_SHIFT, KeyEvent.VK_2); break;
            case '#':  roboType(KeyEvent.VK_SHIFT, KeyEvent.VK_3); break;
            case '$':  roboType(KeyEvent.VK_SHIFT, KeyEvent.VK_4); break;
            case '%':  roboType(KeyEvent.VK_SHIFT, KeyEvent.VK_5); break;
            case '^':  roboType(KeyEvent.VK_SHIFT, KeyEvent.VK_6); break;
            case '&':  roboType(KeyEvent.VK_SHIFT, KeyEvent.VK_7); break;
            case '*':  roboType(KeyEvent.VK_SHIFT, KeyEvent.VK_8); break;
            case '(':  roboType(KeyEvent.VK_SHIFT, KeyEvent.VK_9); break;
            case ')':  roboType(KeyEvent.VK_SHIFT, KeyEvent.VK_0); break;
            case '_':  roboType(KeyEvent.VK_SHIFT, KeyEvent.VK_MINUS); break;
            case '+':  roboType(KeyEvent.VK_SHIFT, KeyEvent.VK_EQUALS); break;
            case '\t': roboType(KeyEvent.VK_TAB); break;
            case '\n': roboType(KeyEvent.VK_ENTER); break;
            case '[':  roboType(KeyEvent.VK_OPEN_BRACKET); break;
            case ']':  roboType(KeyEvent.VK_CLOSE_BRACKET); break;
            case '\\': roboType(KeyEvent.VK_BACK_SLASH); break;
            case '{':  roboType(KeyEvent.VK_SHIFT, KeyEvent. VK_OPEN_BRACKET); break;
            case '}':  roboType(KeyEvent.VK_SHIFT, KeyEvent. VK_CLOSE_BRACKET); break;
            case '|':  roboType(KeyEvent.VK_SHIFT, KeyEvent.VK_BACK_SLASH);
                break;
            case ';':  roboType(KeyEvent.VK_SEMICOLON); break;
            case ':': robotInstance.keyPress(KeyEvent.VK_SHIFT); robotInstance.keyPress(KeyEvent.VK_SEMICOLON); robotInstance.keyRelease(KeyEvent.VK_SHIFT); break;
            case '\'': roboType(KeyEvent.VK_QUOTE); break;
            case '"':  roboType(KeyEvent.VK_SHIFT, KeyEvent.VK_QUOTE); break;
            case ',':  roboType(KeyEvent.VK_COMMA); break;
            case '<':  roboType(KeyEvent.VK_SHIFT, KeyEvent.VK_COMMA); break;
            case '.':  roboType(KeyEvent.VK_PERIOD); break;
            case '>':  roboType(KeyEvent.VK_SHIFT, KeyEvent.VK_PERIOD); break;
            case '/':  roboType(KeyEvent.VK_SLASH); break;
            case '?':  roboType(KeyEvent.VK_SHIFT, KeyEvent.VK_SLASH); break;
            case ' ':  roboType(KeyEvent.VK_SPACE); break;
            default:
                throw new IllegalArgumentException("Cannot type character " + character);
        }
    }

    private void roboType(int... keyCodes)
    {
        type(keyCodes, 0, keyCodes.length);
    }
    private void type(int[] keyCodes, int offset, int length)
    {
        if (length == 0)
            return;

        robotInstance.keyPress(keyCodes[offset]);
        type(keyCodes, offset + 1, length - 1);
        robotInstance.keyRelease(keyCodes[offset]);
    }

    @Override
    public void lostOwnership(Clipboard clipboard, Transferable contents) {

    }
}