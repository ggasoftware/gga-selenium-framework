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

import org.testng.IInvokedMethod;
import org.testng.ITestResult;

/**
 * InvokedMethodListener with Screenshots.
 *
 * @author Zhukov Anatoliy
 */
public class WebDriverInvokedMethodListener extends InvokedMethodListener {
    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult result) {
        Throwable throwable = result.getThrowable();
        if (throwable != null){
            TestBase.setFailed(throwable.getMessage());
        }
        super.afterInvocation(method, result);
        if (method.isTestMethod()) {
            if (!TestBase.getPassed()) {
                boolean hasOldValue = ScreenShotMaker.getHasScreenshot();
                ScreenShotMaker.hasTake(true);
                ReporterNGExt.logBusinessScreenshot("Error Occured!");
                ScreenShotMaker.hasTake(hasOldValue);
            }
            else {
                if (TestBaseWebDriver.takePassedScreenshot) {
                    ReporterNGExt.logBusinessScreenshot("Test Passed");
                }
            }
        }
    }
}
