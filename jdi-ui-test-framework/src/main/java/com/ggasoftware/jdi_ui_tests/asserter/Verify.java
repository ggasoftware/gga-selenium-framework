package com.ggasoftware.jdi_ui_tests.asserter;

import java.util.LinkedList;
import java.util.List;

import static com.ggasoftware.jdi_ui_tests.asserter.DoScreen.NO_SCREEN;
import static java.util.stream.Collectors.toCollection;

/**
 * Created by Roman_Iovlev on 6/9/2015.
 */
public class Verify extends BaseChecker {
    private List<String> fails = new LinkedList<>();
    public List<String> getFails() {
        List<String> result = fails.stream().collect(toCollection(LinkedList::new));
        fails.clear();
        return result;
    }

    public Verify() { }
    public Verify(DoScreen doScreenshot) { this(null, doScreenshot); }
    public Verify(String checkMessage) { this(checkMessage, NO_SCREEN); }
    public Verify(String checkMessage, DoScreen doScreenshot) {
        super(checkMessage, doScreenshot);
        setFailMethod(fails::add);
    }
}
