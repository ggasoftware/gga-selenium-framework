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
import org.testng.IInvokedMethodListener;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

public class InvokedMethodListener implements IInvokedMethodListener {

    private int id = 0;

    @Override
    public void beforeInvocation(IInvokedMethod iInvokedMethod, ITestResult iTestResult) {
        if (iInvokedMethod.isTestMethod()) {
            Reporter.setCurrentTestResult(iTestResult);
            removeAttributes(iTestResult);
            Method testMethod = iInvokedMethod.getTestMethod().getConstructorOrMethod().getMethod();
            if (testMethod.isAnnotationPresent(Test.class)) {
                String message = "Test ";
                message += testMethod.getName() + " started.";
                ReporterNG.logBusiness(message);
                ReporterNG.setAttribute(TestBase.ATTRIBUTES.ATTRIBUTE_NAME.toString(), "_" + id++);
            }
            ReporterNG.LOG.info("Processing Test " + iInvokedMethod.toString());
        }
        TestBase.setPassed();
    }

    protected void removeAttributes(ITestResult iTestResult) {
        for (String attribute : TestBase.ATTRIBUTES.getValues()) {
            if (iTestResult.getAttribute(attribute) != null) {
                iTestResult.removeAttribute(attribute);
            }
        }


    }

    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult result) {
        if (method.isTestMethod() && !TestBase.getPassed()) {
            result.setStatus(ITestResult.FAILURE);
        }
        Reporter.setCurrentTestResult(result);
    }
}
