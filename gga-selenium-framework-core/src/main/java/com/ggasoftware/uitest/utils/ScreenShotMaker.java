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

import org.apache.commons.codec.binary.Base64;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriverException;
import ru.yandex.qatools.allure.annotations.Attachment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;

/**
 * Utility for take Screenshot.
 *
 * @author Zharov Alexandr
 * @author Zhukov Anatoliy
 */
public final class ScreenShotMaker {

    private static String path = "\\";
    private static String dir = "\\";
    private static boolean hasTake = true;

    private ScreenShotMaker() {
    }

    /**
     * Set ScreenShotMaker path
     *
     * @param sPath - to take screenshots
     */
    public static void setPath(String sPath) {
        ScreenShotMaker.dir = "." + sPath.substring(sPath.lastIndexOf("/")) + "/";
        ScreenShotMaker.path = sPath + "/";
    }

    /**
     * Set hasTake variable value
     *
     * @param value - to set hasTake variable
     */
    public static void hasTake(boolean value) {
        hasTake = value;
    }

    /**
     * Get hasTake variable value
     *
     * @return hasTake
     */
    public static boolean getHasScreenshot() {
        return hasTake;
    }

    private static boolean isDirectoryCorrect() {
        return !path.equals("\\");
    }

    /**
     * create screenshot on remote machine
     *
     * @param id - name output png file
     * @return full path
     */
    public static String takeScreenshotRemote(String id) {
        String sId = id;
        String base64Screenshot;
        if (hasTake) {
            if (isDirectoryCorrect()) {
                String name = String.format("%s.png", DateUtil.now(new SimpleDateFormat("HH_mm_ss-sss").toPattern()));
                try {
                    TakesScreenshot tsDriver;
                    tsDriver = (TakesScreenshot) WebDriverWrapper.getDriver();
                    base64Screenshot = tsDriver.getScreenshotAs(OutputType.BASE64);

                    byte[] decodedScreenshot = Base64.decodeBase64(base64Screenshot.getBytes());
                    File file = new File(ScreenShotMaker.path + name);
                    try (FileOutputStream fos = new FileOutputStream(file)) {
                        fos.write(decodedScreenshot);
                    }
                    if (TestBaseWebDriver.allure) {
                        saveScreenshot(decodedScreenshot);
                    }
                    if (TestBaseWebDriver.reportportal) {
                        ReporterNGExt.log4j(new ReportPortalMessage(file, id));
                    }
                } catch (WebDriverException | IOException e) {
                    sId += String.format("  [%s when making screenshot(webdriver: %s)]", e.toString(), WebDriverWrapper.getDriver());
                    ReporterNGExt.log4jError(String.format("%s when making screenshot(webdriver: %s; file: %s%s) ", e.toString(), WebDriverWrapper.getDriver(), ScreenShotMaker.path, name));
                }
                return dir + name + "|" + sId;
            }
            ReporterNGExt.log4jError(String.format("Incorrect directory screenshot directory: %s", path));
            return String.format("Incorrect directory screenshot directory: %s", path);
        } else {
            return id;
        }
    }

    @Attachment(value = "Page screenshot", type = "image/png")
    public static byte[] saveScreenshot(byte[] screenShot) {
        return screenShot;
    }
}
