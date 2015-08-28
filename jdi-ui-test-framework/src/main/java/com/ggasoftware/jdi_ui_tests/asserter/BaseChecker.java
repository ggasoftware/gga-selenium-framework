package com.ggasoftware.jdi_ui_tests.asserter;

import com.ggasoftware.jdi_ui_tests.utils.linqInterfaces.JAction;
import com.ggasoftware.jdi_ui_tests.utils.linqInterfaces.JActionTR;
import com.ggasoftware.jdi_ui_tests.utils.linqInterfaces.JFuncT;
import com.ggasoftware.jdi_ui_tests.utils.linqInterfaces.JFuncTR;

import java.util.Collection;

import static com.ggasoftware.jdi_ui_tests.asserter.DoScreen.DO_SCREEN;
import static com.ggasoftware.jdi_ui_tests.asserter.DoScreen.NO_SCREEN;
import static com.ggasoftware.jdi_ui_tests.logger.enums.LogInfoTypes.FRAMEWORK;
import static com.ggasoftware.jdi_ui_tests.settings.JDISettings.logger;
import static com.ggasoftware.jdi_ui_tests.utils.common.LinqUtils.first;
import static com.ggasoftware.jdi_ui_tests.utils.common.LinqUtils.select;
import static com.ggasoftware.jdi_ui_tests.utils.common.PrintUtils.print;
import static com.ggasoftware.jdi_ui_tests.utils.common.PrintUtils.printObjectAsArray;
import static com.ggasoftware.jdi_ui_tests.utils.common.ReflectionUtils.isInterface;
import static com.ggasoftware.jdi_ui_tests.utils.usefulUtils.ScreenshotMaker.doScreenshotGetMessage;
import static java.lang.String.format;
import static java.lang.reflect.Array.get;
import static java.lang.reflect.Array.getLength;

/**
 * Created by Roman_Iovlev on 6/9/2015.
 */
public abstract class BaseChecker implements IAsserter {
    private JActionTR<String> throwFail;
    public BaseChecker setFailMethod(JActionTR<String> throwFail) { this.throwFail = throwFail; return this; }

    private DoScreen doScreenshot = NO_SCREEN;
    private String checkMessage;

    public BaseChecker() { }
    public BaseChecker(DoScreen doScreenshot) { this(null, doScreenshot); }
    public BaseChecker(String checkMessage) { this(checkMessage, NO_SCREEN); }
    public BaseChecker(String checkMessage, DoScreen doScreenshot) {
        this.checkMessage = getCheckMessage(checkMessage);
        this.doScreenshot = doScreenshot;
    }

    private String getCheckMessage(String checkMessage) {
        String firstWord = checkMessage.split(" ")[0];
        return  (!firstWord.toLowerCase().equals("check") || firstWord.toLowerCase().equals("verify"))
            ? "Check that " + checkMessage
            : checkMessage;
    }

    private void assertAction(String defaultMessage, Boolean result, String failMessage) {
        assertAction(defaultMessage, () -> result ? null : "BaseChecker failed", failMessage);
    }
    private void assertAction(String defaultMessage, JFuncTR<String> result, String failMessage) {
        if (defaultMessage != null)
            logger.info(getBeforeMessage(defaultMessage));
        if (doScreenshot == DO_SCREEN) {
            String screenMessage = doScreenshotGetMessage();
            logger.info("Create screenshot in: ", screenMessage);
        }
        String resultMessage = result.invoke();
        if (resultMessage != null)
            throwFail.invoke(failMessage != null ? failMessage : resultMessage);
    }
    private String getBeforeMessage(String defaultMessage) {
        return  (checkMessage != null && !checkMessage.equals(""))
            ? checkMessage
            : defaultMessage;
    }

    // For Framework
    public Exception exception(String failMessage) {
        logger.error(FRAMEWORK, failMessage);
        assertAction(null, false, failMessage);
        return new Exception(failMessage);
    }
    public void silent(JAction action) {
        try { action.invoke();
        } catch (Exception ex) { exception(ex.getMessage()); }
    }
    public <TResult> TResult silent(JFuncT<TResult> func) {
        try { return func.invoke();
        } catch (Exception ex) { exception(ex.getMessage()); }
        return null;
    }

    // Asserts
    public void areEquals(Object obj, Object obj2, String failMessage) {
        assertAction(format("BaseChecker that '%s' equals to '%s'", obj, obj2), obj.equals(obj2), failMessage);
    }
    public void areEquals(Object obj, Object obj2) { areEquals(obj, obj2, null); }

    public void matches(String str, String regEx, String failMessage) {
        assertAction(format("BaseChecker that '%s' matches to regEx '%s", str, regEx), str.matches(regEx), failMessage);
    }
    public void matches(String str, String regEx) { matches(str, regEx, null);
    }

    public void contains(String str, String str2, String failMessage) {
        assertAction(format("BaseChecker that '%s' not contains '%s'", str, str2), str.contains(str2), failMessage);
    }
    public void contains(String str, String str2) { contains(str, str2, null);
    }

    public void isTrue(Boolean condition, String failMessage) {
        assertAction(format("BaseChecker that condition '%s' is True", condition), condition, failMessage);
    }
    public void isTrue(Boolean condition) { isTrue(condition, null); }

    public void isFalse(Boolean condition, String failMessage) {
        assertAction(format("BaseChecker that condition '%s' is False", condition), !condition, failMessage);
    }
    public void isFalse(Boolean condition) { isFalse(condition, null); }

    private boolean isObjEmpty(Object obj) {
        if (obj == null) return true;
        if (obj instanceof String)
            return obj.toString().equals("");
        if (isInterface(obj.getClass(), Collection.class))
            return ((Collection) obj).size() == 0;
        if (obj.getClass().isArray())
            return getLength(obj) == 0;
        return false;
    }
    public void isEmpty(Object obj, String failMessage) {
        assertAction("BaseChecker that Object is empty", isObjEmpty(obj), failMessage);
    }

    public void isEmpty(Object obj) { isEmpty(obj, null);
    }

    public void isNotEmpty(Object obj, String failMessage) {
        assertAction("BaseChecker that Object is NOT empty", !isObjEmpty(obj), failMessage);
    }

    public void isNotEmpty(Object obj) { isNotEmpty(obj, null);
    }

    public void areSame(Object obj, Object obj2, String failMessage) {
        assertAction("BaseChecker that Objects are the same", obj == obj2, failMessage);
    }

    public void areSame(Object obj, Object obj2) { areSame(obj, obj2, null);
    }

    public void areDifferent(Object obj, Object obj2, String failMessage) {
        assertAction("BaseChecker that Objects are different", obj != obj2, failMessage);
    }

    public void areDifferent(Object obj, Object obj2) { areDifferent(obj, obj2, null);
    }

    public <T> void listContains(Collection<T> collection, T actual, String failMessage) {
        assertAction(format("BaseChecker that list contains element '%s'", actual),
                () -> collection != null && collection.size() > 0
                        ? null
                        : "listContains failed because Collection is null or empty",
                failMessage);
        assertAction(null, () ->
                !collection.contains(actual)
                        ? format("Collection '%s' not contains element '%s'", print(select(collection, Object::toString)), actual)
                        : null
                , failMessage);
    }
    public <T> void listContains(Collection<T> collection, T actual) {
        listContains(collection, actual, null);
    }

    public <T> void listEquals(Collection<T> collection, Collection<T> collection2, String failMessage) {
        assertAction("BaseChecker that Collections are equal",
            () -> collection != null && collection2 != null && collection.size() == collection2.size()
                ? null
                : "listEquals failed because one of the Collections is null or empty",
            failMessage);
        assertAction(null, () -> {
            T notEqualElement = first(collection, el -> !collection2.contains(el));
            return (notEqualElement != null)
                    ? format("Collections '%s' and '%s' not equals at element '%s'",
                    print(select(collection, Object::toString)), print(select(collection2, Object::toString)), notEqualElement)
                    : null;
        }, failMessage);
    }
    public <T> void listEquals(Collection<T> collection, Collection<T> collection2) {
        listEquals(collection, collection2, null);
    }
    public void arrayEquals(Object array, Object array2, String failMessage) {
        assertAction("BaseChecker that Collections are equal",
                () -> array != null && array2 != null && array.getClass().isArray() && array2.getClass().isArray()
                        && getLength(array) == getLength(array2)
                        ? null
                        : "arrayEquals failed because one of the Objects is not Array or empty",
                failMessage);
        assertAction(null, () -> {
            for (int i = 0; i <= getLength(array); i++)
                if (!get(array, i).equals(get(array2, i)))
                    return format("Arrays not equals at index '%s'. '%s' != '%s'. Arrays: '%s' and '%s'",
                            i, get(array, i), get(array2, i), printObjectAsArray(array), printObjectAsArray(array2));
            return null;
        }, failMessage);
    }

    public void arrayEquals(Object array, Object array2) {
        arrayEquals(array, array2, null);
    }
    public void arrayContains(Object array, Object actual, String failMessage) {
        assertAction("BaseChecker that Collections are equal",
            () -> array != null && array.getClass().isArray() && getLength(array) > 0
                ? null
                : "arrayContains failed because Object is not Array or empty",
            failMessage);
        assertAction(null, () -> {
            for (int i = 0; i <= getLength(array); i++)
                if (get(array, i).equals(actual))
                    return null;
            return format("Array '%s' not contains element '%s'",
                    printObjectAsArray(array), actual);
        }, failMessage);
    }
    public void arrayContains(Object array, Object actual) {
        arrayContains(array, actual, null);
    }
}
