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

import org.openqa.selenium.Alert;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static com.ggasoftware.uitest.utils.WebDriverWrapper.TIMEOUT;

/**
 * Alerts control
 *
 * @author Belousov Andrey
 */
public class Alerts {

    private Alerts(){}

    /**
     * Get alert.
     *
     * @param timeoutSec to wait until alert is exists.
     * @return Alert
     */
    public static Alert getAlert(int timeoutSec) {
        WebDriverWait wait = new WebDriverWait(WebDriverWrapper.getDriver(), timeoutSec);
        wait.until(ExpectedConditions.alertIsPresent());
        return WebDriverWrapper.getDriver().switchTo().alert();
    }

    /**
     * Get alert.
     *
     * @return Alert
     */
    public static Alert getAlert() {
        return getAlert(TIMEOUT);
    }

    /**
     * Get alert text.
     *
     * @return Alert text.
     */
    public static String getAlertText() {
        return (String) ReporterNGExt.logGetter("Alerts", "", "text", getAlert().getText());
    }

    /**
     * Accept alert.
     *
     */
    public static void acceptAlert() {
        Alert alert = getAlert();
        ReporterNGExt.logAction("Alerts", "", String.format("Accept Alert: %s", alert.getText()));
        alert.accept();
    }

    /**
     * Dismiss alert.
     */
    public static void dismissAlert() {
        Alert alert = getAlert();
        ReporterNGExt.logAction("Alerts", "", String.format("Dismiss Alert: %s", alert.getText()));
        alert.dismiss();
    }

    /**
     * Find alert from web page.
     *
     * @param timeoutSec to wait until alert is exists.
     * @return true if alert is presents at web page, otherwise false
     */
    public static boolean findAlert(int timeoutSec) {
        WebDriverWait wait = new WebDriverWait(WebDriverWrapper.getDriver(), timeoutSec);
        try {
            wait.until(ExpectedConditions.alertIsPresent());
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    /**
     * Find alert from web page.
     *
     * @return true if alert is presents on the web page, otherwise false
     */
    public static boolean findAlert() {
        return findAlert(TIMEOUT);
    }

    /**
     * Check that alert appeared at web page.
     *
     * @param timeoutSec to wait until alert is exists.
     */
    public static void waitForAlert(int timeoutSec) {
        ReporterNGExt.logAction("Alerts", "", "waitForAlert");
        long start = System.currentTimeMillis() / 1000;
        boolean exists = findAlert(timeoutSec);
        ReporterNGExt.logTechnical(String.format("waitForAlert: during: [ %d ] sec ", System.currentTimeMillis() / 1000 - start));
        ReporterNGExt.logAssertTrue(ReporterNGExt.BUSINESS_LEVEL, exists, String.format("waitForAlert: alert should be exists"));
    }
    /**
     * Check that alert appeared at web page.
     */
    public static void waitForAlert() {
        waitForAlert(TIMEOUT);
    }

    /**
     * Confirm alert by JS.
     */
    public static void jConfirmAlerts() {
        if (findAlert(0)){
            WebDriverWrapper.executeScript("window.confirm = function(msg) { return true; }");
        }
    }

    /**
     * Wait for alert exists and accept.
     *
     * @param timeoutSec to wait until alert is exists.
     */
    public static void findAndAcceptAlert(int timeoutSec){
        if (findAlert(timeoutSec)){
            acceptAlert();
        }
    }
    /**
     * Wait for alert exists and accept.
     */
    public static void findAndAcceptAlert(){
        findAndAcceptAlert(TIMEOUT);
    }
    /**
     * Wait for alert exists and dissmis.
     *
     * @param timeoutSec to wait until alert is exists.
     */
    public static void findAndDismissAlert(int timeoutSec){
        if (findAlert(timeoutSec)){
            dismissAlert();
        }
    }
    /**
     * Wait for alert exists and dissmis.
     */
    public static void findAndDismissAlert(){
        findAndDismissAlert(TIMEOUT);
    }


}
