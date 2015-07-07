package com.ggasoftware.uitest.utils.usefulUtils;

import org.openqa.selenium.os.CommandLine;
import org.openqa.selenium.os.WindowsUtils;

import static com.ggasoftware.uitest.utils.TryCatchUtil.tryGetResult;
import static com.ggasoftware.uitest.utils.common.LinqUtils.*;

/**
 * Created by 12345 on 26.01.2015.
 */
public class WebDriverUtils {
    public static void killAllRunWebDrivers() {
        try {
            String pid = getPid();
            while (pid != null) {
                killPID(pid);
                pid = getPid();
            }
        } catch(Exception ignore) { }
    }

    private static String getPid() {
        return first(where(tryGetResult(WindowsUtils::procMap), el -> el.getKey() != null
                && (el.getKey().contains("firefox") && el.getKey().contains("-foreground"))
                | el.getKey().contains("chromedriver")
                | el.getKey().contains("IEDriverServer")));
    }

    private static void killPID(String processID) {
        executeCommand("taskkill", "/f", "/t", "/pid", processID);
    }

    private static void executeCommand(String commandName, String... args) {
        CommandLine cmd = new CommandLine(commandName, args);
        cmd.execute();
    }
}
