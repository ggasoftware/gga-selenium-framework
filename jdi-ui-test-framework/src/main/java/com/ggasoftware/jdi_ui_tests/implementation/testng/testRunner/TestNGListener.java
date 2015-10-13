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
package com.ggasoftware.jdi_ui_tests.implementation.testng.testRunner;

import com.ggasoftware.jdi_ui_tests.core.asserter.Verify;
import com.ggasoftware.jdi_ui_tests.core.logger.enums.LogInfoTypes;
import com.ggasoftware.jdi_ui_tests.core.settings.JDIData;
import com.ggasoftware.jdi_ui_tests.core.settings.JDISettings;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestResult;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.util.List;

import static org.testng.ITestResult.FAILURE;

public class TestNGListener implements IInvokedMethodListener {
    @Override
    public void beforeInvocation(IInvokedMethod iInvokedMethod, ITestResult iTestResult) {
        if (iInvokedMethod.isTestMethod()) {
            Method testMethod = iInvokedMethod.getTestMethod().getConstructorOrMethod().getMethod();
            if (testMethod.isAnnotationPresent(Test.class)) {
                JDIData.testName = testMethod.getName();
                JDISettings.logger.info("== Test '%s' started ==", JDIData.testName);
            }
        }
    }

    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult result) {
        if (method.isTestMethod()) {
            List<String> fails = Verify.getFails();
            if (fails.size() > 0) {
                for (String fail : fails)
                    JDISettings.logger.error(LogInfoTypes.BUSINESS, fail);
                result.setStatus(FAILURE);
            }
            JDISettings.logger.info("=== Test '%s' %s ===", JDIData.testName, getTestResult(result));
        }
    }
    private String getTestResult(ITestResult result) {
        switch (result.getStatus()) {
            case ITestResult.SUCCESS:
                return "passed";
            case ITestResult.SKIP:
                return "skipped";
            default:
                return "failed";
        }
    }


}
