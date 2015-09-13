package com.ggasoftware.jdi_ui_tests.core.utils.common;

import com.ggasoftware.jdi_ui_tests.core.utils.linqInterfaces.JFuncTT;
import com.ggasoftware.jdi_ui_tests.core.utils.usefulUtils.TryCatchUtil;
import org.openqa.selenium.By;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.ggasoftware.jdi_ui_tests.core.utils.common.PrintUtils.print;
import static java.lang.String.format;

/**
 * Created by roman.i on 30.09.2014.
 */
public class WebDriverByUtils {

    public static JFuncTT<String, By> getByFunc(By by) {
        return LinqUtils.first(getMapByTypes(), key -> by.toString().contains(key));
    }
    private static String getBadLocatorMsg(String byLocator, Object... args) {
        return "Bad locator template '" + byLocator + "'. Args: " + print(LinqUtils.select(args, Object::toString), ", ", "'%s'") + ".";
    }

    public static By fillByTemplate(By by, Object... args) {
        String byLocator = getByLocator(by);
        try { byLocator = format(byLocator, args); }
        catch(Exception ex) {
            throw new RuntimeException(getBadLocatorMsg(byLocator, args)); }
        return getByFunc(by).invoke(byLocator);
    }
    public static By fillByMsgTemplate(By by, Object... args) {
        String byLocator = getByLocator(by);
        try { byLocator = MessageFormat.format(byLocator, args); }
        catch(Exception ex) {
            throw new RuntimeException(getBadLocatorMsg(byLocator, args)); }
        return getByFunc(by).invoke(byLocator);
    }
    public static By copyBy(By by) {
        String byLocator = getByLocator(by);
        return TryCatchUtil.tryGetResult(() -> getByFunc(by).invoke(byLocator));
    }


    public static String getByLocator(By by) {
        String byAsString = by.toString();
        int index = byAsString.indexOf(": ") + 2;
        return byAsString.substring(index);
    }

    public static String getByName(By by) {
        Matcher m = Pattern.compile("By\\.(?<locator>.*):.*").matcher("By.cssSelector: .authorization-form");
        if (m.find())
            return m.group("locator");
        throw new RuntimeException("Can't get By name for: " + by);
    }

    private static Map<String, JFuncTT<String, By>> getMapByTypes() {
        Map<String, JFuncTT<String, By>> map = new HashMap<>();
        map.put("By.cssSelector", By::cssSelector);
        map.put("By.className", By::className);
        map.put("By.id", By::id);
        map.put("By.linkText", By::linkText);
        map.put("By.name", By::name);
        map.put("By.partialLinkText", By::partialLinkText);
        map.put("By.tagName", By::tagName);
        map.put("By.xpath", By::xpath);
        return map;
    }
}
