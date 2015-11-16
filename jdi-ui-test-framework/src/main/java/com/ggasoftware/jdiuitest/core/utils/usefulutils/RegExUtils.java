package com.ggasoftware.jdiuitest.core.utils.usefulutils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.compile;

/**
 * Created by Roman_Iovlev on 9/8/2015.
 */
public class RegExUtils {
    public static List<String> matches(String str, String regEx) {
        List<String> result = new ArrayList<>();
        Pattern pattern = compile(regEx);
        Matcher matcher = pattern.matcher(str);
        while (matcher.find())
            result.add(matcher.group());
        return result;
    }
}
