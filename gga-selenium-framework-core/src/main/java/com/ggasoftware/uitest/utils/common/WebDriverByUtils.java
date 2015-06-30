package com.ggasoftware.uitest.utils.common;

import com.ggasoftware.uitest.utils.linqInterfaces.JFuncTT;
import org.openqa.selenium.By;

import java.util.HashMap;
import java.util.Map;

import static com.ggasoftware.uitest.utils.common.LinqUtils.*;
import static com.ggasoftware.uitest.utils.common.PrintUtils.print;
import static java.lang.String.format;

/**
 * Created by roman.i on 30.09.2014.
 */
public class WebDriverByUtils {

    public static JFuncTT<String, By> getByFunc(By by) throws Exception {
        return first(getMapByTypes(), key -> by.toString().contains(key));
    }
    private static String getBadLocatorMsg(String byLocator, Object... args) throws Exception {
        return "Bad locator template '" + byLocator + "'. Args: " + print(select(args, Object::toString), ", ", "'%s'") + ".";
    }

    public static By fillByTemplate(By by, Object... args) throws Exception {
        String byLocator = getByLocator(by);
        try { byLocator = format(getByLocator(by), args); }
        catch(Exception ex) {
            throw new Exception(getBadLocatorMsg(byLocator, args)); }
        return getByFunc(by).invoke(byLocator);
    }
    public static By copyBy(By by) throws Exception {
        String byLocator = getByLocator(by);
        return getByFunc(by).invoke(byLocator);
    }


    public static String getByLocator(By by) {
        String byAsString = by.toString();
        int index = byAsString.indexOf(": ") + 2;
        return byAsString.substring(index);
    }

    private static Map<String, JFuncTT<String, By>> getMapByTypes() {
        Map<String, JFuncTT<String, By>> map = new HashMap<>();
        map.put("By.selector:", By::cssSelector);
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
