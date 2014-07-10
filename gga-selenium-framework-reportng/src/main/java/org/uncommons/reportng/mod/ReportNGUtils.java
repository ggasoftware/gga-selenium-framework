//=============================================================================
// Copyright 2006-2013 Daniel W. Dyer
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
//=============================================================================
package org.uncommons.reportng.mod;

import org.testng.*;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Utility class that provides various helper methods that can be invoked
 * from a Velocity template.
 *
 * @author Daniel Dyer
 *
 * @author azhukov
 * Modifications:
 * - add array object to renderArgument() method
 * - add expected failures count to report
 * - add working with test attibutes
 * - add log status to report
 * - add screenshort info to report
 * - add timestamp to report
 *
 */
public class ReportNGUtils extends org.uncommons.reportng.ReportNGUtils{
    private static final SuitComparator SUIT_COMPARATOR = new SuitComparator();

    @Override
    public String formatDuration(long millis) {
        long second = (millis / 1000) % 60;
        long minute = (millis / (1000 * 60)) % 60;
        long hour = (millis / (1000 * 60 * 60)) % 24;
        return String.format("%02d:%02d:%02d", hour, minute, second);
    }

    /**
     * Decorate the string representation of an argument to give some
     * hint as to its type (e.g. render Strings in double quotes).
     * @param argument The argument to render.
     * @return The string representation of the argument.
     */
    private String renderArgument(Object argument) {
        if (argument == null)
        {
            return "null";
        }
        else if (argument instanceof String)
        {
            return "\"" + argument + "\"";
        }
        else if (argument instanceof Character)
        {
            return "\'" + argument + "\'";
        }
        else if (argument instanceof String[]) {
            StringBuilder stringBuffer = new StringBuilder();
            stringBuffer.append("{");
            int length = ((String[]) argument).length;
            for (int i = 0; i < length; i++) {
                stringBuffer.append("\"").append(((String[]) argument)[i]).append("\"");
                if (i < length - 1) {
                    stringBuffer.append(", ");
                }
            }
            stringBuffer.append("}");
            return stringBuffer.toString();
        }
        else {
            return argument.toString();
        }
    }

    /**
     * Returns the Numbers of Expected Failures Count.
     * @param context ITestContext.
     * @return Numbers of Expected Failures Count.
     */
    public int getExpectedFailuresCount(ITestContext context) {
        return getExpectedFailuresCount(context.getPassedTests()) + getExpectedFailuresCount(context.getFailedTests());
    }

    private int getExpectedFailuresCount(IResultMap results) {
        int result = 0;
        for (ITestResult testResult : results.getAllResults()) {
            if (testResult.getAttribute("bug") != null) {
                result++;
            }
        }
        return result;
    }

    /**
     * Returns the Attribute text.
     * @param result ITestResult.
     * @param key Attribute key.
     * @return Attribute text.
     */
    public String getAttribute(ITestResult result, String key) {
        String attribute = (String) result.getAttribute(key);
        if (attribute == null) {
            return "";
        } else {
            return attribute;
        }
    }

    /**
     * Returns the Attribute text array.
     * @param result Test Result.
     * @param key Attribute key.
     * @return Attribute text array.
     */
    public String[] getAttributeArray(ITestResult result, String key) {
        String[] attribute = (String[]) result.getAttribute(key);
        if (attribute != null && attribute.length > 0) {
            return attribute;
        }
        return new String[]{};
    }

    /**
     * Returns the lenght of array.
     * @param array Array of string.
     * @return Lenght of array.
     */
    public int getStringArrayLength(String[] array) {
        if (array != null) {
            return array.length;
        }
        return 0;
    }


    public boolean isNotNull(String value) {
        return !(value == null || value.length() == 0 || value.equals(""));
    }

    /**
     * Returns the number of total test methods.
     * @param context ITestContext.
     * @return Number of total test methods.
     */
    public int getTotalTestMethods(ITestContext context) {
        int result;
        result = context.getAllTestMethods().length;
        return result;
    }

    public boolean hasGroups(ISuite suite) {
        return !suite.getMethodsByGroups().isEmpty();
    }

    /**
     * Returns the converted date by simple format.
     * @param date - Date object.
     * @return Converted date.
     */
    public String convertDate(Object date) {
        if (date == null) {
            return "00:00:00.000";
        } else if (date instanceof String) {
            Calendar calendar = new GregorianCalendar();
            calendar.setTimeInMillis(Long.valueOf((String)date));
            date = calendar.getTime();
        }
        return new SimpleDateFormat("HH:mm:ss.SSS").format(date);
    }

    /**
     * Returns the list of log levels.
     *
     * @param s String from log
     * @return list of log levels
     */
    public List<String> getLogLevel(String s) {
        List<String> result = new ArrayList<>(2);
        result.add(String.valueOf(s.charAt(0)));
        result.add(s.substring(1));
        return result;
    }

    /**
     * Returns the test output list after refactoring with log levels.
     *
     * @param result Test Result
     * @return Refactored test output list.
     */
    public List<String> getRefactoredTestOutput(ITestResult result) {
        List<String> tmp = Reporter.getOutput(result);
        LinkedList<String> res = new LinkedList<>();
        int level = 100;
        int statusLevel = 100;
        //boolean status_flag = false;
        char status = '*';
        for (int i = tmp.size() - 1; i >= 0; i--) {
            String logString = tmp.get(i);
            if (logString.substring(0, 1).equals("<")) {
                logString=logString.replace("<div \"featureFile\">", "2 ");
                logString=logString.replace("<div \"feature\">", "2 ");
                logString=logString.replace("<div \"scenarioOutline\">", "2 ");
                logString=logString.replace("<div \"scenario\">", "2 ");
                logString=logString.replace("<div \"result\">", "2 ");
                logString=logString.replace("</div>", "");
            }
            int currentLevel = Integer.parseInt(logString.substring(0, 1));
            logString = logString.substring(1);
            char currentStatus = logString.charAt(0);
            logString = logString.substring(1);
            if ((status == '1' || status == '0' || status == '2') && currentLevel > statusLevel &&
                    (currentStatus != '2')) {
                logString = status + logString;
                statusLevel++;
            } else {
                logString = currentStatus + logString;
            }

            if ((currentStatus == '1' || currentStatus == '2' || currentStatus == '0') && status != '2') {
                status = currentStatus;
                statusLevel = currentLevel;
            }
            if (currentLevel == 2) {
                status = '*';
            }

            if (currentLevel > level) {
                if (currentLevel > level + 1) {
                    res.addFirst("+" + (currentLevel - 1) + " Lower level information");
                }
                res.addFirst("+" + currentLevel + logString);
            } else {
                res.addFirst(currentLevel + logString);
            }

            level = currentLevel;

        }
        return res;
    }

    /**
     * Returns the number of log status.
     *
     * @param s String from log.
     * @return Number of log status.
     */
    public int getLogStatus(String s) {
        char value = s.charAt(0);
        if (value == '0') {
            return 0;
        } else if (value == '1') {
            return 1;
        } else if (value == '2') {
            return 2;
        } else {
            return 9;
        }
    }

    public boolean isScreenshot(String s) {
        return (s.contains("png") || s.contains("jpg") || s.contains("jpeg"));
    }

    /**
     * Returns the list after checking screenshort info.
     *
     * @param s String from log.
     * @return List of log string.
     */
    public List<String> getScreenshotInfo(String s) {
        List<String> result = new ArrayList<>();
        int separator = s.indexOf("|");
        if (separator == -1) {
            result.add(s);
            result.add(s);
        } else {
            String path = s.substring(0, separator);
            String info = s.substring(separator + 1, s.length());
            if (path.contains("_scrolled")) {
                result.add(path.replace("_scrolled", ""));
            }
            result.add(path);
            result.add(info);
        }
        return result;
    }

    public boolean expandable(String s) {
        char value = s.charAt(0);
        return value == '+';
    }

    /**
     * Returns the timestamp for the time at which the suite finished executing.
     * This is determined by finding the latest end time for each of the individual
     * tests in the suite.
     *
     * @param s String from log
     * @return List of the timestamp.
     */
    public List<String> getTimeStamp(String s) {
        List<String> result = new ArrayList<>();
        int separator = s.indexOf("~");
        if (separator == -1) {
            result.add("0");
            result.add(s);
        } else {
            result.add(s.substring(0, separator));
            result.add(s.substring(separator + 1, s.length()));
        }
        return result;
    }

    /**
     * Remove Double Quotes.
     *
     * @param s String from log.
     * @return Modified string.
     */
    public String removeDoubleQuotes(String s) {
        return s.replaceAll("\"", "'");
    }

    /**
     * Remove sorted suit result map.
     *
     * @param unsortMap Suit result map.
     * @return Sorted map.
     */
    public static Map<String, ISuiteResult> sortByComparator(Map<String, ISuiteResult> unsortMap) {

        LinkedList list = new LinkedList<>(unsortMap.entrySet());

        //sort list based on comparator
        Collections.sort(list, SUIT_COMPARATOR);

        //put sorted list into map again
        unsortMap = new LinkedHashMap<>();
        for (Object aList : list) {
            Map.Entry entry = (Map.Entry) aList;
            unsortMap.put((String) entry.getKey(), (ISuiteResult) entry.getValue());
        }
        return unsortMap;
    }

}


