package com.ggasoftware.jdi_ui_tests.core.asserter;

import java.util.LinkedList;
import java.util.List;

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
    public Verify(String checkMessage) { super(checkMessage); }
}
