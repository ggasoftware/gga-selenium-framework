package com.ggasoftware.jdiuitest.core.utils.common;

import com.ggasoftware.jdiuitest.core.settings.JDISettings;
import com.ggasoftware.jdiuitest.core.utils.linqinterfaces.JAction;
import com.ggasoftware.jdiuitest.core.utils.linqinterfaces.JFuncTEx;
import com.ggasoftware.jdiuitest.core.utils.linqinterfaces.JFuncTT;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.ggasoftware.jdiuitest.core.utils.usefulutils.TryCatchUtil.throwRuntimeException;
import static java.lang.System.currentTimeMillis;

/**
 * Created by 12345 on 28.09.2014.
 */
public class Timer {
    private final Long start = currentTimeMillis();
    private long timeoutInMSec = JDISettings.timeouts.waitElementSec * 1000L;
    private long retryTimeoutInMSec = JDISettings.timeouts.retryMSec;

    public Timer() {
    }

    public Timer(long timeoutInMSec, long retryTimeoutInMSec) {
        this();
        this.timeoutInMSec = timeoutInMSec;
        this.retryTimeoutInMSec = retryTimeoutInMSec;
    }

    public Timer(long timeoutInMSec) {
        this();
        this.timeoutInMSec = timeoutInMSec;
    }

    public static String nowTime() {
        return nowTime("HH:mm:ss.SSS");
    }

    public static String nowTimeShort() {
        return nowTime("mm:ss.SSS");
    }

    public static String nowDate() {
        return nowTime("yyyy-MM-dd HH:mm:ss");
    }

    public static String nowTime(String timeFormat) {
        return new SimpleDateFormat(timeFormat).format(new Date());
    }

    public static String nowMSecs() {
        return Long.toString(currentTimeMillis());
    }

    public static void sleep(long mSec) {
        try {
            Thread.sleep(mSec);
        } catch (InterruptedException ignore) {
        }
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

    public Timer setTimeout(long timeoutInMSec) {
        this.timeoutInMSec = timeoutInMSec;
        return this;
    }

    public Timer setRetryTimeout(long retryTimeoutInMSec) {
        this.retryTimeoutInMSec = retryTimeoutInMSec;
        return this;
    }

    public Long timePassedInMSec() {
        Long now = currentTimeMillis();
        return now - start;
    }

    public boolean timeoutPassed() {
        return timePassedInMSec() > timeoutInMSec;
    }

    public boolean wait(JFuncTEx<Boolean> waitCase) {
        while (!timeoutPassed())
            try {
                if (waitCase.invoke())
                    return true;
                sleep(retryTimeoutInMSec);
            } catch (Exception | Error ignore) {
                throwRuntimeException(ignore);
            }
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
            } catch (Exception | Error ignore) {
                throwRuntimeException(ignore);
            }
            sleep(retryTimeoutInMSec);
        }
        return null;
    }

}
