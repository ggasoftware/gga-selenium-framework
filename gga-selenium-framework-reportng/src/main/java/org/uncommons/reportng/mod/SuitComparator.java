package org.uncommons.reportng.mod;

import org.testng.ISuiteResult;

import java.util.Comparator;
import java.util.Map;

/**
 * Comparator for sorting TestNG test suit.
 *
 * @author azhukov
 */
public class SuitComparator implements Comparator {

    @Override
    public int compare(Object o1, Object o2) {
        return ((ISuiteResult) ((Map.Entry) (o1)).getValue()).getTestContext().getStartDate()
	           .compareTo(((ISuiteResult) ((Map.Entry) (o2)).getValue()).getTestContext().getStartDate());
    }
}
