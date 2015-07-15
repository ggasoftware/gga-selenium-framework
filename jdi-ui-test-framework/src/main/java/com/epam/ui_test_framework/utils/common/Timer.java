package com.epam.ui_test_framework.utils.common;

import com.epam.ui_test_framework.utils.linqInterfaces.JAction;
import com.epam.ui_test_framework.utils.linqInterfaces.JFuncT;
import com.epam.ui_test_framework.utils.linqInterfaces.JFuncTT;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.epam.ui_test_framework.utils.settings.FrameworkSettings.timeouts;
import static com.epam.ui_test_framework.utils.usefulUtils.TryCatchUtil.ignoreException;
import static com.epam.ui_test_framework.utils.usefulUtils.TryCatchUtil.tryGetResult;
import static java.lang.System.currentTimeMillis;
import static java.lang.Thread.sleep;

/**
 * Created by 12345 on 28.09.2014.
 */
public class Timer {
    private long _timeoutInMSec = timeouts.waitElementSec * 1000;
    private long _retryTimeoutInMSec = timeouts.retryMSec;
    private final Long start = currentTimeMillis();
    public static String nowTime() { return nowTime("HH:mm:ss.SSS"); }
    public static String nowTimeShort() { return nowTime("mm:ss.SSS"); }
    public static String nowDate() { return nowTime("yyyy-MM-dd HH:mm:ss"); }
    public static String nowTime(String timeFormat) {
        return new SimpleDateFormat(timeFormat).format(new Date());
    }
    public static String nowMSecs() { return currentTimeMillis()+""; }
    public static void sleep(long mSec) {
        ignoreException(() -> Thread.sleep(mSec));
    }

    public Timer() { }
    public Timer(long timeoutInMSec, long retryTimeoutInMSec) {
        this();
        _timeoutInMSec = timeoutInMSec;
        _retryTimeoutInMSec = retryTimeoutInMSec;
    }
    public Timer(long timeoutInMSec) {
        this();
        _timeoutInMSec = timeoutInMSec;
    }
    public Timer setTimeout(long timeoutInMSec) { _timeoutInMSec = timeoutInMSec; return this; }
    public Timer setRetryTimeout(long retryTimeoutInMSec) { _retryTimeoutInMSec = retryTimeoutInMSec; return this; }

    public Long timePassedInMSec() {
        Long now = currentTimeMillis();
        return now - start;
    }

    public boolean timeoutPassed() {
        return timePassedInMSec() >  _timeoutInMSec;
    }

    public boolean wait(JFuncT<Boolean> waitCase) {
        while (!timeoutPassed())
            try {
                if (tryGetResult(waitCase) != null)
                    return true;
                sleep(_retryTimeoutInMSec);
            } catch (Exception ignore) { }
        return false;
    }

    public <T> T getResult(JFuncT<T> getFunc) {
        return getResultByCondition(getFunc, result -> true);
    }
    public <T> T getResultByCondition(JFuncT<T> getFunc, JFuncTT<T, Boolean> conditionFunc) {
        while (!timeoutPassed()) {
            try {
                T result = tryGetResult(getFunc);
                if (result != null && conditionFunc.invoke(result))
                    return result;
            } catch (Exception ignore) { }
            ignoreException(() -> sleep(_retryTimeoutInMSec));
        }
        return null;
    }
    public static <T> T getByCondition(JFuncT<T> getFunc, JFuncTT<T, Boolean> conditionFunc) {
        return new Timer().getResultByCondition(getFunc, conditionFunc);
    }

    public static <T> T getResultAction(JFuncT<T> getFunc) {
        return new Timer().getResultByCondition(getFunc, result -> true);
    }
    public static boolean alwaysDoneAction(JAction action) {
        return new Timer().wait(() -> {
            action.invoke();
            return true;
        });
    }

    public static boolean waitCondition(JFuncT<Boolean> condition) {
        return new Timer().wait(condition);
    }

}
