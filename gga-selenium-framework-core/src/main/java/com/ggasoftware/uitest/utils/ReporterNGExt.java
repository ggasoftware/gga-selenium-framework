/****************************************************************************
 * Copyright (C) 2014 GGA Software Services LLC
 *
 * This file may be distributed and/or modified under the terms of the
 * GNU General Public License version 3 as published by the Free Software
 * Foundation.
 *
 * This file is provided AS IS with NO WARRANTY OF ANY KIND, INCLUDING THE
 * WARRANTY OF DESIGN, MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see <http://www.gnu.org/licenses>.
 ***************************************************************************/
package com.ggasoftware.uitest.utils;

import com.ggasoftware.uitest.control.Element;
import com.ggasoftware.uitest.control.Elements;
import com.ggasoftware.uitest.panel.BasePanel;
import org.apache.commons.lang.ClassUtils;
import org.testng.Reporter;
import ru.yandex.qatools.allure.annotations.Step;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import static com.ggasoftware.uitest.utils.ReporterNG.logTechnical;
import static com.ggasoftware.uitest.utils.TestBaseWebDriver.allure;

/**
 * Utility for level log format.
 *
 * @author Alexeenko Yan
 * @author Belousov Andrey
 * @author Zharov Alexandr
 * @author Zhukov Anatoliy
 */
public class ReporterNGExt extends ReporterNG{

    public static final char BUSINESS_LEVEL = '2';
    public static final char COMPONENT_LEVEL = '1';
    public static final char TECHNICAL_LEVEL = '0';

    private static final String SDFP = new SimpleDateFormat("HH:mm:ss.SSS").toPattern();

    /**
     * Takes screenshot and adds link to business part of test LOG. Must only be used on Test level
     * @param name the name of screenshot. User alphanumeric and _
     */
    public static void logBusinessScreenshot(String name) {
        ReporterNGExt.logBusiness(ScreenShotMaker.takeScreenshotRemote(name));
    }

    /**
     * Takes screenshot and adds link to component part of test LOG. Must only be used on Component level
     * @param name the name of screenshot. User alphanumeric and _
     */
    public static void logComponentScreenshot(String name) {
        ReporterNGExt.logComponent(ScreenShotMaker.takeScreenshotRemote(name));
    }

    /**
     * Get element level for log.
     * @param element - element to get level
     * @return log level (ReporterNG.BUSINESS_LEVEL, ReporterNG.COMPONENT_LEVEL or ReporterNG.TECHNICAL_LEVEL)
     */
    //TODO Improve this method
    private static char getLogLevel(Object element){
        if (ClassUtils.getPackageName(element.getClass()).contains(".panel")) {
            return COMPONENT_LEVEL;
        } else if(ClassUtils.getAllSuperclasses(element.getClass()).contains(TestBaseWebDriver.class)){
            return BUSINESS_LEVEL;
        }
        return TECHNICAL_LEVEL;
    }

    /**
     * Log Report.
     *
     * @param logLevel (ReporterNG.BUSINESS_LEVEL, ReporterNG.COMPONENT_LEVEL or ReporterNG.TECHNICAL_LEVEL)
     * @param status - test status
     * @param message - log message text
     * @param takePassedScreenshot - Set True to take screenshot if assert passed
     */
    private static void logReport(char logLevel, String status, String message, boolean takePassedScreenshot) {
        if (status.equals(ReporterNG.FAILED)) {
            boolean hasOldValue = ScreenShotMaker.getHasScreenshot();
            ScreenShotMaker.hasTake(true);
            Reporter.log(String.format("%s %s~ %s", logLevel, DateUtil.now(SDFP), ScreenShotMaker.takeScreenshotRemote(String.format("%s: %s", message, status))));
            ScreenShotMaker.hasTake(hasOldValue);
        }else {
            if (takePassedScreenshot) {
                Reporter.log(String.format("%s %s~ %s", logLevel, DateUtil.now(SDFP), ScreenShotMaker.takeScreenshotRemote(String.format("%s: %s", message, status))));
            }
        }
    }

    /**
     * Log Match Assertion.
     *
     * @param logLevel (ReporterNG.BUSINESS_LEVEL, ReporterNG.COMPONENT_LEVEL or ReporterNG.TECHNICAL_LEVEL)
     * @param value - analyzed text
     * @param regExp - regular expression
     * @param message - log message text
     * @param takePassedScreenshot - Set True to take screenshot if assert passed
     */
    public static void logAssertMatch(char logLevel, String value, String regExp, String message, boolean takePassedScreenshot) {
        String status = logAssertMatch(logLevel, value, regExp, message);
        logReport(logLevel, status, message, takePassedScreenshot);
    }

    /**
     * Log Not Intersect Assertion.
     *
     * @param logLevel (ReporterNG.BUSINESS_LEVEL, ReporterNG.COMPONENT_LEVEL or ReporterNG.TECHNICAL_LEVEL)
     * @param firstArray - first text array 
     * @param secondArray - second text array
     * @param message - log message text
     * @param takePassedScreenshot - Set True to take screenshot if assert passed
     */
    public static void logAssertNotIntersect(char logLevel, String[] firstArray, String[] secondArray, String message, boolean takePassedScreenshot) {
        String status = logAssertNotIntersect(logLevel, firstArray, secondArray, message);
        logReport(logLevel, status, message, takePassedScreenshot);
    }

    /**
     * Log Equals Assertion.
     *
     * @param logLevel (ReporterNG.BUSINESS_LEVEL, ReporterNG.COMPONENT_LEVEL or ReporterNG.TECHNICAL_LEVEL)
     * @param value - actual object
     * @param expectedValue - expected object
     * @param message - log message text
     * @param takePassedScreenshot - Set True to take screenshot if assert passed
     */
    public static void logAssertEquals(char logLevel, Object value, Object expectedValue, String message, boolean takePassedScreenshot) {
        String status = logAssertEquals(logLevel, value, expectedValue, message);
        logReport(logLevel, status, message, takePassedScreenshot);
    }

    /**
     * Log Not Equals Assertion.
     *
     * @param logLevel (ReporterNG.BUSINESS_LEVEL, ReporterNG.COMPONENT_LEVEL or ReporterNG.TECHNICAL_LEVEL)
     * @param value - actual object
     * @param notExpectedValue - not expected object
     * @param message - log message text
     * @param takePassedScreenshot - Set True to take screenshot if assert passed
     */
    public static void logAssertNotEquals(char logLevel, Object value, Object notExpectedValue, String message, boolean takePassedScreenshot) {
        String status = logAssertNotEquals(logLevel, value, notExpectedValue, message);
        logReport(logLevel, status, message, takePassedScreenshot);
    }

    /**
     * Log Array List of String Equals Assertion.
     *
     * @param logLevel (ReporterNG.BUSINESS_LEVEL, ReporterNG.COMPONENT_LEVEL or ReporterNG.TECHNICAL_LEVEL)
     * @param value - actual Array List of String
     * @param expectedValue - expected Array List of String
     * @param message - log message text
     * @param takePassedScreenshot - Set True to take screenshot if assert passed
     */
    public static void logAssertArrayListEquals(char logLevel, ArrayList<String> value, ArrayList<String> expectedValue, String message, boolean takePassedScreenshot) {
        String status = logAssertArrayListEquals(logLevel, value, expectedValue, message);
        logReport(logLevel, status, message, takePassedScreenshot);
    }

    /**
     * Log Array Equals Assertion.
     *
     * @param logLevel (ReporterNG.BUSINESS_LEVEL, ReporterNG.COMPONENT_LEVEL or ReporterNG.TECHNICAL_LEVEL)
     * @param value - actual text array
     * @param expectedValue - expected text array
     * @param message - log message text
     * @param takePassedScreenshot - Set True to take screenshot if assert passed
     */
    public static void logAssertEquals(char logLevel, String[] value, String[] expectedValue, String message, boolean takePassedScreenshot) {
        String status = logAssertEquals(logLevel, value, expectedValue, message);
        logReport(logLevel, status, message, takePassedScreenshot);
    }

    /**
     * Modify Text Array to String
     *
     * @param sa - Text Array
     * @return String of Text Array
     */
    public static String stringArrayToString(String[] sa) {
        StringBuilder sb = new StringBuilder("{");
        for (String aSa : sa) {
            sb.append(" ")
                    .append("\"")
                    .append(aSa)
                    .append("\"");
        }
        sb.append(" }");
        return sb.toString();
    }

    /**
     * Log True Assertion.
     *
     * @param logLevel (ReporterNG.BUSINESS_LEVEL, ReporterNG.COMPONENT_LEVEL or ReporterNG.TECHNICAL_LEVEL)
     * @param what - expression
     * @param message - log message text
     * @param takePassedScreenshot - Set True to take screenshot if assert passed
     */
    public static void logAssertTrue(char logLevel, boolean what, String message, boolean takePassedScreenshot) {
        String status = logAssertTrue(logLevel, what, message);
        logReport(logLevel, status, message, takePassedScreenshot);
    }

    /**
     * Log False Assertion.
     *
     * @param logLevel (ReporterNG.BUSINESS_LEVEL, ReporterNG.COMPONENT_LEVEL or ReporterNG.TECHNICAL_LEVEL)
     * @param what - expression
     * @param message - log message text
     * @param takePassedScreenshot - Set True to take screenshot if assert passed
     */
    public static void logAssertFalse(char logLevel, boolean what, String message, boolean takePassedScreenshot) {
        String status = logAssertFalse(logLevel, what, message);
        logReport(logLevel, status, message, takePassedScreenshot);
    }

    /**
     * Log Empty Assertion.
     *
     * @param logLevel (ReporterNG.BUSINESS_LEVEL, ReporterNG.COMPONENT_LEVEL or ReporterNG.TECHNICAL_LEVEL)
     * @param what - analyzed text
     * @param message - log message text
     * @param takePassedScreenshot - Set True to take screenshot if assert passed
     */
    public static void logAssertEmpty(char logLevel, String what, String message, boolean takePassedScreenshot) {
        String status = logAssertEmpty(logLevel, what, message);
        logReport(logLevel, status, message, takePassedScreenshot);
    }

    /**
     * Log Text Contains Assertion.
     *
     * @param logLevel (ReporterNG.BUSINESS_LEVEL, ReporterNG.COMPONENT_LEVEL or ReporterNG.TECHNICAL_LEVEL)
     * @param toSearchIn - text search in
     * @param whatToSearch - text to search
     * @param message - log message text
     * @param takePassedScreenshot - Set True to take screenshot if assert passed
     */
    public static void logAssertContains(char logLevel, String toSearchIn, String whatToSearch, String message, boolean takePassedScreenshot) {
        String status = logAssertContains(logLevel, toSearchIn, whatToSearch, message);
        logReport(logLevel, status, message, takePassedScreenshot);
    }

    /**
     * Log Text Not Contains Assertion.
     *
     * @param logLevel (ReporterNG.BUSINESS_LEVEL, ReporterNG.COMPONENT_LEVEL or ReporterNG.TECHNICAL_LEVEL)
     * @param toSearchIn - text search in
     * @param whatToSearch - text to search
     * @param message - log message text
     * @param takePassedScreenshot - Set True to take screenshot if assert passed
     */
    public static void logAssertNotContains(char logLevel, String toSearchIn, String whatToSearch, String message, boolean takePassedScreenshot) {
        String status = logAssertNotContains(logLevel, toSearchIn, whatToSearch, message);
        logReport(logLevel, status, message, takePassedScreenshot);
    }

    /**
     * Log Null Object Assertion.
     *
     * @param logLevel (ReporterNG.BUSINESS_LEVEL, ReporterNG.COMPONENT_LEVEL or ReporterNG.TECHNICAL_LEVEL)
     * @param what - analyzed object
     * @param message - log message text
     * @param takePassedScreenshot - Set True to take screenshot if assert passed
     */
    public static void logAssertNull(char logLevel, Object what, String message, boolean takePassedScreenshot) {
        String status = logAssertNull(logLevel, what, message);
        logReport(logLevel, status, message, takePassedScreenshot);
    }

    /**
     * Log Not Null Object Assertion.
     *
     * @param logLevel (ReporterNG.BUSINESS_LEVEL, ReporterNG.COMPONENT_LEVEL or ReporterNG.TECHNICAL_LEVEL)
     * @param what - analyzed object
     * @param message - log message text
     * @param takePassedScreenshot - Set True to take screenshot if assert passed
     */
    public static void logAssertNotNull(char logLevel, Object what, String message, boolean takePassedScreenshot) {
        String status = logAssertNotNull(logLevel, what, message);
        logReport(logLevel, status, message, takePassedScreenshot);
    }

    /**
     * Log getting parameter value from Element.
     *
     * @param element - element to get value
     * @param parentClassName - parent class name of element
     * @param parameterName - name of parameter to get value
     * @param value - object to get value
     * @return object to get value
     */
    public static Object logGetter(Object element, String parentClassName, String parameterName, Object value) {
        String className = getClassName(element, parentClassName);
        logGetter(getLogLevel(element), className, parameterName, value);
        return value;
    }

    private static String getClassName(Object element, String parentClassName) {
        if (element == null) {
            return "";
        }
        Class elClass = element.getClass();
        if (BasePanel.class.isAssignableFrom(elClass)) {
            return element.getClass().getSimpleName();
        } else {
            String name = "";
            String className = parentClassName;
            if (Element.class.isAssignableFrom(elClass)){
                name = ((Element) element).getName();
            }else if (Elements.class.isAssignableFrom(elClass)){
                name = ((Elements) element).getName();
            }
            if (name.length() > 0) {
                className += name.contains(" ") ? ".'" + name + "'" : "." + name;
            }
            if (className.length() <=0){
                return element.getClass().getSimpleName();
            }else {
                return (element.getClass().getSimpleName() + " ") + className;
            }
        }

    }

    /**
     * Log setting parameter value to Element.
     *
     * @param element - element to set value
     * @param parentClassName - parent class name of element
     * @param parameterName - name of parameter to set value
     * @param value - object to set value
     * @return object to set value
     */
    public static Object logSetter(Object element, String parentClassName, String parameterName, Object value) {
        String className = getClassName(element, parentClassName);
        logSetter(getLogLevel(element), className, parameterName, value);
        return value;
    }

    /**
     * Log action.
     *
     * @param element - element to perform action
     * @param parentClassName - parent class name of element
     * @param actionName - name of action
     */
    public static void logAction(Object element, String parentClassName, String actionName) {
        String className = getClassName(element, parentClassName);
        logAction(getLogLevel(element), className, actionName);
        if (allure) {
            action(className, actionName);
        }
    }
    @Step
    public static void action(String element, String actionName) {
        logTechnical("Allure action step");
    }

    /**
     * Log and take screenshot at TECHNICAL_LEVEL.
     *
     * @param str - log message text
     */
    public static void logTechnicalScreenshot(String str) {
        boolean hasOldValue = ScreenShotMaker.getHasScreenshot();
        ScreenShotMaker.hasTake(true);
        ReporterNGExt.logTechnical(ScreenShotMaker.takeScreenshotRemote(str));
        ScreenShotMaker.hasTake(hasOldValue);
    }

    /**
     * Log failed message and take screenshot.
     *
     * @param logLevel - log level (ReporterNG.BUSINESS_LEVEL, ReporterNG.COMPONENT_LEVEL or ReporterNG.TECHNICAL_LEVEL)
     * @param message - log message text
     */
    public static void logFailedScreenshot(char logLevel, String message) {
        logFailed(logLevel, message);
        boolean hasOldValue = ScreenShotMaker.getHasScreenshot();
        ScreenShotMaker.hasTake(true);
        Reporter.log(String.format("%s %s~ %s", logLevel, DateUtil.now(SDFP), ScreenShotMaker.takeScreenshotRemote(String.format("%s: Failed", message))));
        ScreenShotMaker.hasTake(hasOldValue);
    }
    

}
