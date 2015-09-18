package com.ggasoftware.uitest.utils;

import com.ggasoftware.uitest.utils.linqInterfaces.*;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.ggasoftware.uitest.utils.WebDriverWrapper.TIMEOUT;
import static java.lang.System.currentTimeMillis;

/**
 * Created by 12345 on 28.09.2014.
 */
public class Timer {
    private long _timeoutInMSec =  TIMEOUT * 1000;
    private long _retryTimeoutInMSec = 100;
    private final Long start = currentTimeMillis();
    public static String nowTime() { return nowTime("HH:mm:ss.SSS"); }
    public static String nowTimeShort() { return nowTime("mm:ss.SSS"); }
    public static String nowDate() { return nowTime("yyyy-MM-dd HH:mm:ss"); }
    public static String nowTime(String timeFormat) {
        return new SimpleDateFormat(timeFormat).format(new Date());
    }
    public static String nowMSecs() { return currentTimeMillis()+""; }
    public static void sleep(long mSec) {
        try { Thread.sleep(mSec);
        } catch (InterruptedException ignore) { }
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

    public boolean wait(JFuncTEx<Boolean> waitCase) {
        while (!timeoutPassed())
            try {
                if (waitCase.invoke() != null)
                    return true;
                sleep(_retryTimeoutInMSec);
            } catch (Exception|AssertionError ignore) { }
        return false;
    }

    public <T> T getResult(JFuncTEx<T> getFunc) {
        return getResultByCondition(getFunc, result -> true);
    }
    public <T> T getResultByCondition(JFuncTEx<T> getFunc, JFuncTT<T, Boolean> conditionFunc) {
        while (!timeoutPassed()) {
            try {
                T result = getFunc.invoke();
                if (result != null && conditionFunc.invoke(result))
                    return result;
            } catch (Exception|AssertionError ignore) { }
            sleep(_retryTimeoutInMSec);
        }
        return null;
    }
    public static <T> T getByCondition(JFuncTEx<T> getFunc, JFuncTT<T, Boolean> conditionFunc) {
        return new Timer().getResultByCondition(getFunc, conditionFunc);
    }

    public static <T> T getResultAction(JFuncTEx<T> getFunc) {
        return new Timer().getResultByCondition(getFunc, result -> true);
    }
    public static boolean alwaysDoneAction(JAction action) {
        return new Timer().wait(() -> {
            action.invoke();
            return true;
        });
    }

    public static boolean waitCondition(JFuncTEx<Boolean> condition) {
        return new Timer().wait(condition);
    }

}
