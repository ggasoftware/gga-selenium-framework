package com.ggasoftware.jdiuitest.core.asserter;

import com.ggasoftware.jdiuitest.core.utils.linqinterfaces.JActionT;

import java.util.LinkedList;
import java.util.List;

import static java.util.stream.Collectors.toCollection;

/**
 * Created by Roman_Iovlev on 6/9/2015.
 */
public class Verify extends BaseChecker {
    private static List<String> fails = new LinkedList<>();

    protected String doScreenshotGetMessage() { return ""; }

    public Verify() { }

    public Verify(String checkMessage) {
        super(checkMessage);
    }

    public static List<String> getFails() {
        List<String> result = fails.stream().collect(toCollection(LinkedList::new));
        fails.clear();
        return result;
    }

    @Override
    protected JActionT<String> throwFail() {
        return fails::add;
    }
}
