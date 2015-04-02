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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Properties;

/**
 * Basic  Test class in order to implement soft assertion
 */
public abstract class TestBase {

    private static boolean passed = true;

    private final Properties properties = new Properties();

    public TestBase() {
        PropertyReader.getProperties(properties, this.getClass().getName());
    }

    public enum ATTRIBUTES {
        ATTRIBUTE_BUG("bug"),
        ATTRIBUTE_NAME("name"),
        ATTRIBUTE_FAILED_MESSAGE("failedMessage"),
        ATTRIBUTE_APPLICATION_VERSION("applicationVersion");
        ATTRIBUTES(String sValue) {
            this.value = sValue;
        }

        @Override
        public String toString() {
            return this.value;
        }

        public static String[] getValues() {
            ATTRIBUTES[] values = values();
            String[] result = new String[values.length];
            for (int i = 0; i < values.length; i++) {
                result[i] = values[i].toString();
            }
            return result;
        }
        private final String value;
    }

    /**
     * Set failed attribute to test results and failed message to log.
     *
     * @param message Failed message.
     */
    public static void setFailed(String message) {
        passed = false;

        ArrayList<String> failedMessage = new ArrayList<>();
        String[] attribute = (String[]) ReporterNG.getAttribute(ATTRIBUTES.ATTRIBUTE_FAILED_MESSAGE.toString());
        if (attribute != null) {
            Collections.addAll(failedMessage, attribute);
        }
        failedMessage.add(message);
        ReporterNG.setAttribute(ATTRIBUTES.ATTRIBUTE_FAILED_MESSAGE.toString(), failedMessage.toArray(new String[failedMessage.size()]));
    }

    /**
     * Set application version attribute to test results.
     *
     * @param version Application version.
     */
    public static void setApplicationVersion(String version){
        ReporterNG.LOG.info("Set version: " + version);
        ReporterNG.setAttribute(ATTRIBUTES.ATTRIBUTE_APPLICATION_VERSION.toString(), version);
    }

    /**
     * Set test passed.
     */
    public static void setPassed() {
        passed = true;
    }

    /**
     * Check test passed or not.
     *
     * @return  True if test passed.
     */
    public static boolean getPassed() {
        return passed;
    }

    //
    // Assertions
    //

    /**
     * Compares two Objects as strings. In case of inequality marks test as FAILED. Execution continuous.
     *
     * @param value         value
     * @param expectedValue expected value
     * @param message       the message to add in case of inequality
     */
    protected void assertEquals(Object value, Object expectedValue, String message) {
        ReporterNG.logAssertEquals(ReporterNG.BUSINESS_LEVEL, value, expectedValue, message);
    }

    protected void assertNotEquals(Object value, Object notExpectedValue, String message) {
        ReporterNG.logAssertNotEquals(ReporterNG.BUSINESS_LEVEL, value, notExpectedValue, message);
    }

    protected void assertEquals(String[] value, String[] expectedValue, String message) {
        ReporterNG.logAssertEquals(ReporterNG.BUSINESS_LEVEL, value, expectedValue, message);
    }

    /**
     * Checks whether logical expression is TRUE. In case of logical expression is FALSE marks test as FAILED. Execution continuous.
     *
     * @param what    logical expression that should be TRUE
     * @param message the message to add in case of logical expression is FALSE
     */
    protected void assertTrue(boolean what, String message) {
        ReporterNG.logAssertTrue(ReporterNG.BUSINESS_LEVEL, what, message);
    }

    /**
     * Checks whether logical expression is FALSE. In case of logical expression is TRUE marks test as FAILED. Execution continuous.
     *
     * @param what    logical expression that should be FALSE
     * @param message the message to add in case of logical expression is TRUE
     */
    protected void assertFalse(boolean what, String message) {
        ReporterNG.logAssertFalse(ReporterNG.BUSINESS_LEVEL, what, message);
    }

    /**
     * Checks whether one string contains another. In case of check failure marks test as FAILED. Execution continuous.
     *
     * @param toSearchIn   the string to search in
     * @param whatToSearch the string to search
     * @param message      the message to add in case of check failure
     */
    protected void assertContains(String toSearchIn, String whatToSearch, String message) {
        ReporterNG.logAssertContains(ReporterNG.BUSINESS_LEVEL, toSearchIn, whatToSearch, message);
    }

    /**
     * Checks whether Object is NULL. In case if Object is NOT NULL marks test as FAILED. Execution continuous.
     *
     * @param what    Object to check for NULL
     * @param message the message to add in case of check failure
     */
    protected void assertNull(Object what, String message) {
        ReporterNG.logAssertNull(ReporterNG.BUSINESS_LEVEL, what, message);
    }

    protected void assertNotNull(Object what, String message) {
        ReporterNG.logAssertNotNull(ReporterNG.BUSINESS_LEVEL, what, message);
    }

    protected void assertEmpty(String what, String message) {
        ReporterNG.logAssertEmpty(ReporterNG.BUSINESS_LEVEL, what, message);
    }

    protected void assertNotIntersect(String[] firstArray, String[] secondArray, String message) {
        ReporterNG.logAssertNotIntersect(ReporterNG.BUSINESS_LEVEL, firstArray, secondArray, message);
    }

    protected void assertMatch(String value, String regExp, String message) {
        ReporterNG.logAssertMatch(ReporterNG.BUSINESS_LEVEL, value, regExp, message);
    }

    /**
     * Adds message to business part of test LOG. Must only be used on Test level
     *
     * @param message the message to add
     */
    protected void logBusiness(String message) {
        ReporterNG.logBusiness(message);
    }

    protected String getTestProperty(String key){
        return properties.getProperty(key);
    }

    protected String[] getArrayProperty(String key) {
        return this.getTestProperty(key).split("; ");
    }
}
