/**
 * *************************************************************************
 * Copyright (C) 2014 GGA Software Services LLC
 * <p>
 * This file may be distributed and/or modified under the terms of the
 * GNU General Public License version 3 as published by the Free Software
 * Foundation.
 * <p>
 * This file is provided AS IS with NO WARRANTY OF ANY KIND, INCLUDING THE
 * WARRANTY OF DESIGN, MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see <http://www.gnu.org/licenses>.
 * *************************************************************************
 */
package com.ggasoftware.uitest.utils;

import org.testng.IInvokedMethod;
import org.testng.ITestResult;

import static com.ggasoftware.uitest.utils.ReporterNGExt.logBusinessScreenshot;

/**
 * InvokedMethodListener with Screenshots.
 *
 * @author Zhukov Anatoliy
 */
public class WebDriverInvokedMethodListener extends InvokedMethodListener {
    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult result) {
        Throwable throwable = result.getThrowable();
        if (throwable != null) {
            TestBase.setFailed(throwable.getMessage());
        }
        super.afterInvocation(method, result);
        if (method.isTestMethod()) {
            if (!TestBase.getPassed()) {
                logBusinessScreenshot("Error Occurred!");
            } else {
                if (TestBaseWebDriver.takePassedScreenshot) {
                    logBusinessScreenshot("Test Passed");
                }
            }
        }
    }
}
