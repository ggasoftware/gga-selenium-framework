package com.ggasoftware.jdiuitest.web.robot;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

import static com.ggasoftware.jdiuitest.core.settings.JDISettings.exception;
import static com.ggasoftware.jdiuitest.core.utils.common.Timer.sleep;
import static java.awt.event.KeyEvent.*;

/**
 * Created by Roman_Iovlev on 9/7/2015.
 */
public class RobotF {
    public static RobotF robot = new RobotF();
    private Robot robotInstance;

    public RobotF() {
        try {
            robotInstance = new Robot();
        } catch (Exception ex) {
            throw exception("Can't instantiate Robot");
        }
    }

    public void pasteText(String text) {
        try {
            StringSelection stringSelection = new StringSelection(text);
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(stringSelection, (clipboard1, contents) -> {
            });
            sleep(1000);
            robotInstance.keyPress(VK_CONTROL);
            robotInstance.keyPress(VK_V);

            robotInstance.keyRelease(VK_CONTROL);
            robotInstance.keyPress(VK_ENTER);
            robotInstance.keyRelease(VK_ENTER);
        } catch (Exception ex) {
            throw exception("Robot Input exception");
        }
    }
}
