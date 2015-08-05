package com.ggasoftware.jdi_ui_tests.utils.usefulUtils;

import com.ggasoftware.jdi_ui_tests.settings.JDIData;
import com.ggasoftware.jdi_ui_tests.settings.JDISettings;
import com.ggasoftware.jdi_ui_tests.utils.common.StringUtils;
import com.ggasoftware.jdi_ui_tests.utils.common.Timer;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.io.IOException;

import static org.apache.commons.io.FileUtils.copyFile;
import static org.openqa.selenium.OutputType.FILE;

/**
 * Created by Roman_Iovlev on 7/21/2015.
 */
public class ScreenshotMaker {
    public String pathSuffix = "/.logs/images/";
    public ScreenshotMaker() {}
    public ScreenshotMaker(String pathSuffix) { this.pathSuffix = pathSuffix; }

    public static String takeScreen() throws IOException { return new ScreenshotMaker().takeScreenshot(); }
    public String takeScreenshot() throws IOException {
        String path = new File(".").getCanonicalPath() + getValidUrl(pathSuffix);
        String screensFilePath = getFileName(path + JDIData.testName + Timer.nowDate().replace(":", "-"));
        new File(screensFilePath).getParentFile().mkdirs();
        File screensFile = ((TakesScreenshot) JDISettings.driverFactory.getDriver()).getScreenshotAs(FILE);
        copyFile(screensFile, new File(screensFilePath));
        return screensFilePath;
    }

    private String getFileName(String fileName) {
        int num = 1;
        String newName = fileName;
        while (new File(newName + ".jpg").exists())
            newName = fileName + "_" + num ++;
        return newName + ".jpg";
    }

    public static String getValidUrl(String logPath)
    {
        if (logPath == null || logPath.equals(""))
            return "";
        String result = logPath.replace("/", "\\");
        if (result.charAt(1) != ':')
            if (result.substring(0, 3).equals("..\\"))
                result = result.substring(2);
        if (result.charAt(0) != '\\')
            result = "\\" + result;
        return (result.charAt(result.length() - 1) == '\\')
                ? result
                : result + "\\";
    }

    public static String doScreenshotGetMessage() {
            String screenshotPath = "";
            try { screenshotPath = takeScreen(); }
            catch (IOException ignore) { }
            return (screenshotPath.equals(""))
                    ? "Failed to do Screenshot"
                    : StringUtils.LineBreak + "Add screenshot to: " + screenshotPath;
    }
}
