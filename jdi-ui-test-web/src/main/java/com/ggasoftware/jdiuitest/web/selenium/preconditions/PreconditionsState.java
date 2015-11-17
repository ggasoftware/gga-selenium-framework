/*
 * Copyright 2004-2016 EPAM Systems
 *
 * This file is part of JDI project.
 *
 * JDI is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * JDI is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; 
 * without even the implied warranty ofMERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. 
 * See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with JDI. If not, see <http://www.gnu.org/licenses/>.
 */
 
package com.ggasoftware.jdiuitest.web.selenium.preconditions;

import java.lang.reflect.Method;

import static com.ggasoftware.jdiuitest.core.settings.JDIData.testName;
import static com.ggasoftware.jdiuitest.core.settings.JDISettings.asserter;
import static com.ggasoftware.jdiuitest.core.settings.JDISettings.logger;
import static java.lang.String.format;

/**
 * Created by Roman_Iovlev on 10/27/2015.
 */
public class PreconditionsState {
    public static boolean alwaysMoveToCondition;

    public static void isInState(IPreconditions condition, Method method) {
        try {
            logger.test("Move to condition: " + condition);
            testName = method.getName();
            if (alwaysMoveToCondition && condition.checkAction().invoke())
                return;
            condition.moveToAction().invoke();
            asserter.isTrue(condition.checkAction()::invoke);
            logger.test(condition + " condition achieved");
        } catch (Exception ex) {
            throw asserter.exception(format("Can't reach state: %s. Exception: %s", condition, ex.getMessage()));
        }
    }

}