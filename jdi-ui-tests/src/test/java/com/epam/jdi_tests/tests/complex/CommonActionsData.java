package com.epam.jdi_tests.tests.complex;

import com.ggasoftware.jdi_ui_tests.core.utils.common.Timer;
import com.ggasoftware.jdi_ui_tests.core.utils.linqInterfaces.JAction;
import com.ggasoftware.jdi_ui_tests.core.utils.linqInterfaces.JFuncT;
import ru.yandex.qatools.allure.annotations.Step;

import static com.epam.jdi_tests.page_objects.EpamJDISite.actionsLog;
import static com.epam.jdi_tests.page_objects.EpamJDISite.metalsColorsPage;
import static com.ggasoftware.jdi_ui_tests.implementation.testng.asserter.Assert.*;

/**
 * Created by Roman_Iovlev on 9/18/2015.
 */
public class CommonActionsData {
    public static void checkActionThrowError(JAction action, String msg) {
        try { action.invoke();
        } catch (Exception|AssertionError ex) {
            assertContains(ex.getMessage(), msg);
            return;
        }
        throw exception("Exception not thrown");
    }
    public static final String noElementsMessage = "No elements selected. Override getSelectedAction or place locator to <select> tag";

    public static final long waitTimeOut = 1000;
    private static Timer timer;
    public static long getTimePassed() { return timer.timePassedInMSec(); }
    public static void runParallel(JAction action) {
        new Thread() {
            @Override
            public void run() {
                timer = new Timer();
                Timer.sleep(waitTimeOut);
                action.invoke();
            }
        }.run();
    }
    @Step
    public static void checkAction(String text) {
        assertContains(actionsLog::getFirstText, text);
    }
    @Step
    public static void checText(JFuncT<String> func, String text) {
        areEquals(func.invoke(), text);
    }
    @Step
    public static void checkCalculate(String text) {
        assertContains(metalsColorsPage.calculateText::getText, text);
    }
}
