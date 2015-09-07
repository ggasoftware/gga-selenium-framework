package com.ggasoftware.jdi_ui_tests.core.asserter;

import com.ggasoftware.jdi_ui_tests.core.utils.common.Timer;
import com.ggasoftware.jdi_ui_tests.core.utils.linqInterfaces.JActionT;
import com.ggasoftware.jdi_ui_tests.core.utils.linqInterfaces.JFuncT;
import com.ggasoftware.jdi_ui_tests.core.utils.linqInterfaces.JFuncTEx;
import com.ggasoftware.jdi_ui_tests.core.utils.map.MapArray;

import java.util.Collection;

import static com.ggasoftware.jdi_ui_tests.core.asserter.DoScreen.*;
import static com.ggasoftware.jdi_ui_tests.core.logger.enums.LogInfoTypes.FRAMEWORK;
import static com.ggasoftware.jdi_ui_tests.core.settings.JDISettings.asserter;
import static com.ggasoftware.jdi_ui_tests.core.settings.JDISettings.logger;
import static com.ggasoftware.jdi_ui_tests.core.utils.common.LinqUtils.first;
import static com.ggasoftware.jdi_ui_tests.core.utils.common.LinqUtils.select;
import static com.ggasoftware.jdi_ui_tests.core.utils.common.LinqUtils.where;
import static com.ggasoftware.jdi_ui_tests.core.utils.common.PrintUtils.*;
import static com.ggasoftware.jdi_ui_tests.core.utils.common.ReflectionUtils.isInterface;
import static com.ggasoftware.jdi_ui_tests.core.utils.usefulUtils.ScreenshotMaker.doScreenshotGetMessage;
import static edu.emory.mathcs.backport.java.util.Arrays.asList;
import static java.lang.String.format;
import static java.lang.reflect.Array.get;
import static java.lang.reflect.Array.getLength;

/**
 * Created by Roman_Iovlev on 6/9/2015.
 */
public abstract class BaseChecker implements IAsserter, IChecker {
    public static long defaultWaitTimeout = 0;
    public static DoScreen defaultDoScreenType = NO_SCREEN;

    private JActionT<String> throwFail;
    public BaseChecker doScreenshot(DoScreen doScreenshot) { this.doScreenshot = doScreenshot; return this; }
    public BaseChecker doScreenshot() { return doScreenshot(DO_SCREEN_ALWAYS); }
    public BaseChecker setThrowFail(JActionT<String> throwFail) { this.throwFail = throwFail; return this; }
    public BaseChecker ignoreCase() { this.ignoreCase = true; return this; }
    public BaseChecker setWait(int timeoutSec) { this.timeout = timeoutSec*1000; return this; }

    private DoScreen doScreenshot = defaultDoScreenType;
    private String checkMessage = "";
    private boolean ignoreCase = false;
    private boolean isListCheck = false;
    private long timeout = defaultWaitTimeout;

    public BaseChecker() { }
    public BaseChecker(String checkMessage) {  this.checkMessage = getCheckMessage(checkMessage); }

    private String getCheckMessage(String checkMessage) {
        if (checkMessage == null || checkMessage.equals("")) return "";
        String firstWord = checkMessage.split(" ")[0];
        return  (!firstWord.toLowerCase().equals("check") || firstWord.toLowerCase().equals("verify"))
            ? "Check " + checkMessage
            : checkMessage;
    }

    private static String FOUND = "FOUND";
    private void assertAction(String defaultMessage, Boolean result, String failMessage) {
        assertAction(defaultMessage, () -> result ? FOUND : "Check failed", failMessage, false);
    }
    private void waitAction(String defaultMessage, JFuncT<Boolean> result, String failMessage) {
        assertAction(defaultMessage, () -> result.invoke() ? FOUND : "Check failed", failMessage, true);
    }
    private void assertAction(String defaultMessage, JFuncT<String> result, String failMessage, boolean wait) {
        if (!isListCheck && defaultMessage != null)
            logger.info(getBeforeMessage(defaultMessage));
        if (!isListCheck && doScreenshot == DO_SCREEN_ALWAYS)
            makeScreenshot();
        if (isListCheck && failMessage == null)
            failMessage = defaultMessage + " failed";
        String resultMessage = (wait)
                ? new Timer(timeout).getResultByCondition(result::invoke, r -> r != null && r.equals(FOUND))
                : result.invoke();
        if (resultMessage == null)
            resultMessage = result.invoke();
        if (!resultMessage.equals(FOUND)) {
            if (doScreenshot == SCREEN_ON_FAIL)
                makeScreenshot();
            throwFail.invoke(failMessage != null
                    ? failMessage
                    : resultMessage);
        }
    }

    private void makeScreenshot() {
        String screenMessage = doScreenshotGetMessage();
        logger.info("Create screenshot in: " + screenMessage);
    }

    private String getBeforeMessage(String defaultMessage) {
        return  (checkMessage != null && !checkMessage.equals(""))
            ? checkMessage
            : defaultMessage;
    }

    // For Framework
    public RuntimeException exception(String failMessage) {
        logger.error(FRAMEWORK, failMessage);
        assertAction(null, false, failMessage);
        return new RuntimeException(failMessage);
    }
    public <TResult> TResult silent(JFuncTEx<TResult> func) {
        try { return func.invoke();
        } catch (Exception ex) { throw asserter.exception(ex.getMessage()); }
    }

    // Asserts
    public <T> void areEquals(T actual, T expected, String failMessage) {
        boolean result = (ignoreCase && actual.getClass() == String.class)
            ? ((String)actual).toLowerCase().equals(((String)expected).toLowerCase())
            : actual.equals(expected);
        assertAction(format("Check that '%s' equals to '%s'", actual, expected), result, failMessage);
    }
    public <T> void areEquals(T actual, T expected) {
        areEquals(actual, expected, null);
    }
    public void matches(String actual, String regEx, String failMessage) {
        boolean result = (ignoreCase && actual.getClass() == String.class)
                ? actual.toLowerCase().matches(regEx.toLowerCase())
                : actual.matches(regEx);
        assertAction(format("Check that '%s' matches to regEx '%s", actual, regEx), result, failMessage);
    }
    public void matches(String actual, String regEx) {
        matches(actual, regEx, null);
    }

    public void contains(String actual, String expected, String failMessage) {
        boolean result = (ignoreCase && actual.getClass() == String.class)
                ? actual.toLowerCase().contains(expected.toLowerCase())
                : actual.contains(expected);
        assertAction(format("Check that '%s' contains '%s'", actual, expected), result, failMessage);
    }
    public void contains(String actual, String expected) { contains(actual, expected, null);
    }

    public void isTrue(Boolean condition, String failMessage) {
        assertAction(format("Check that condition '%s' is True", condition), condition, failMessage);
    }
    public void isTrue(Boolean condition) {
        isTrue(condition, null);
    }

    public void isFalse(Boolean condition, String failMessage) {
        assertAction(format("Check that condition '%s' is False", condition), !condition, failMessage);
    }
    public void isFalse(Boolean condition) {
        isFalse(condition, null);
    }

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
        assertAction("Check that Object is empty", isObjEmpty(obj), failMessage);
    }

    public void isEmpty(Object obj) { isEmpty(obj, null);
    }

    public void isNotEmpty(Object obj, String failMessage) {
        assertAction("Check that Object is NOT empty", !isObjEmpty(obj), failMessage);
    }

    public void isNotEmpty(Object obj) {
        isNotEmpty(obj, null);
    }

    public <T> void areSame(T actual, T expected, String failMessage) {
        assertAction("Check that Objects are the same", actual == expected, failMessage);
    }

    public <T> void areSame(T actual, T expected) {
        areSame(actual, expected, null);
    }

    public <T> void areDifferent(T actual, T expected, String failMessage) {
        assertAction("Check that Objects are different", actual != expected, failMessage);
    }

    public <T> void areDifferent(T actual, T expected) {
        areDifferent(actual, expected, null);
    }

    private  <T> void collectionEquals(Collection<T> actual, Collection<T> expected, String failMessage, boolean equalSize) {
        assertAction("Check that Collections are equal",
                () -> actual != null && expected != null && (!equalSize || (actual.size() == expected.size()))
                        ? FOUND
                        : "listEquals failed because one of the Collections is null or empty",
                failMessage, false);
        assertAction(null, () -> {
            T notEqualElement = first(expected, el -> el != null && !actual.contains(el));
            return (notEqualElement != null)
                    ? format("Collections '%s' and '%s' not equals at webElement '%s'",
                    print(select(actual, Object::toString)), print(select(expected, Object::toString)), notEqualElement)
                    : FOUND;
        }, failMessage, false);
    }

    public  <T> void listEquals(Collection<T> actual, Collection<T> expected, String failMessage) {
        collectionEquals(actual, expected, failMessage, true);
    }
    public <T> void listEquals(Collection<T> actual, Collection<T> expected) {
        listEquals(actual, expected, null);
    }
    public <T> void mapEqualsEntity(MapArray<String, String> map, T entity, String failMessage) {
        collectionEquals(map.pairs, where(objToSetValue(entity).pairs, el -> el.value != null), failMessage, false);
    }
    public <T> void mapEqualsEntity(MapArray<String, String> map, T entity) {
        mapEqualsEntity(map, entity, null);
    }
    public <T> void arrayEquals(T actual, T expected, String failMessage) {
        assertAction("Check that Collections are equal",
                () -> actual != null && expected != null && actual.getClass().isArray() && expected.getClass().isArray()
                        && getLength(actual) == getLength(expected)
                        ? FOUND
                        : "arrayEquals failed because one of the Objects is not Array or empty",
                failMessage, false);
        assertAction(null, () -> {
            for (int i = 0; i <= getLength(actual); i++)
                if (!get(actual, i).equals(get(expected, i)))
                    return format("Arrays not equals at index '%s'. '%s' != '%s'. Arrays: '%s' and '%s'",
                            i, get(actual, i), get(expected, i), printObjectAsArray(actual), printObjectAsArray(expected));
            return FOUND;
        }, failMessage, false);
    }
    public <T> void arrayEquals(T actual, T expected) {
        arrayEquals(actual, expected, null);
    }

    // ListProcessor
    public <T> ListChecker eachElementOf(Collection<T> list) { return new ListChecker<>(list); }
    public <T> ListChecker eachElementOf(T[] array) { return new ListChecker<>(asList(array)); }

    public class ListChecker<T> {
        Collection<T> list;
        private ListChecker(Collection<T> list) {
            this.list = list;
        }

        private void beforeListCheck(String defaultMessage, String expected, String failMessage) {
            assertAction(format(defaultMessage, print(select(list, Object::toString)), expected),
                () -> list != null && list.size() > 0
                        ? FOUND
                        : "list check failed because list is null or empty",
                failMessage, false);
            isListCheck = true;
        }

        public void areEquals(Object expected, String failMessage) {
            beforeListCheck("Check that each item of list '%s' equals to '%s'", expected.toString(), failMessage);
            for (Object el : list)
                BaseChecker.this.areEquals(el, expected, failMessage);
        }
        public void areEquals(Object expected) { areEquals(expected, null); }
        public void matches(String regEx, String failMessage) {
            beforeListCheck("Check that each item of list '%s' matches to regEx '%s'", regEx, failMessage);
            for (Object el : list)
                BaseChecker.this.matches((String) el, regEx, failMessage);
        }
        public void matches(String regEx) { matches(regEx, null); }

        public void contains(String expected, String failMessage) {
            beforeListCheck("Check that each item of list '%s' contains '%s'", expected, failMessage);
            for (Object el : list)
                BaseChecker.this.contains((String)el, expected, failMessage);
        }
        public void contains(String expected) { contains(expected, null); }

        public void areSame(Object expected, String failMessage) {
            beforeListCheck("Check that all items of list '%s' are same with '%s'", expected.toString(), failMessage);
            for (Object el : list)
                BaseChecker.this.areSame(el, expected, failMessage);
        }

        public void areSame(Object actual, Object expected) {
            areSame(expected, null);
        }

        public void areDifferent(Object expected, String failMessage) {
            beforeListCheck("Check that all items of list '%s' are different with '%s'", expected.toString(), failMessage);
            for (Object el : list)
                BaseChecker.this.areDifferent(el, expected, failMessage);
        }

        public void areDifferent(Object actual, Object expected) {
            areDifferent(expected, null);
        }

    }
/*
    public <T> ListWaitChecker eachElementOf(JFuncT<Collection<T>> list) { return new ListWaitChecker<>(list); }
    public <T> ListWaitChecker eachElementOfArray(JFuncT<T[]> array) {
        JFuncT<Collection<T>> list = () -> asList(array.invoke());
        return new ListWaitChecker<>(list);
    }

    public class ListWaitChecker<T> {
        JFuncT<Collection<T>> list;
        private ListWaitChecker(JFuncT<Collection<T>> list) {
            this.list = list;
        }

        private void beforeListCheck(String defaultMessage, String expected, String failMessage) {
            assertAction(format(defaultMessage, print(select(list, Object::toString)), expected),
                    () -> list != null && list.size() > 0
                            ? FOUND
                            : "list check failed because list is null or empty",
                    failMessage);
            isListCheck = true;
        }

        public void areEquals(Object expected, String failMessage) {
            beforeListCheck("Check that each item of list '%s' equals to '%s'", expected.toString(), failMessage);
            for (Object el : list)
                BaseChecker.this.areEquals(el, expected, failMessage);
        }
        public void areEquals(Object expected) { areEquals(expected, null); }
        public void matches(String regEx, String failMessage) {
            beforeListCheck("Check that each item of list '%s' matches to regEx '%s'", regEx, failMessage);
            for (Object el : list)
                BaseChecker.this.matches((String) el, regEx, failMessage);
        }
        public void matches(String regEx) { matches(regEx, null); }

        public void contains(String expected, String failMessage) {
            beforeListCheck("Check that each item of list '%s' contains '%s'", expected, failMessage);
            for (Object el : list)
                BaseChecker.this.contains((String)el, expected, failMessage);
        }
        public void contains(String expected) { contains(expected, null); }

        public void areSame(Object expected, String failMessage) {
            beforeListCheck("Check that all items of list '%s' are same with '%s'", expected.toString(), failMessage);
            for (Object el : list)
                BaseChecker.this.areSame(el, expected, failMessage);
        }

        public void areSame(Object actual, Object expected) {
            areSame(expected, null);
        }

        public void areDifferent(Object expected, String failMessage) {
            beforeListCheck("Check that all items of list '%s' are different with '%s'", expected.toString(), failMessage);
            for (Object el : list)
                BaseChecker.this.areDifferent(el, expected, failMessage);
        }

        public void areDifferent(Object actual, Object expected) {
            areDifferent(expected, null);
        }

    }*/

    // Asserts Wait
    public <T> void areEquals(JFuncT<T> actual, T expected, String failMessage) {
        JFuncT<Boolean> resultAction = (ignoreCase && expected.getClass() == String.class)
            ? () -> actual.invoke().equals(expected)
            : () -> ((String)actual.invoke()).toLowerCase().equals(((String)expected).toLowerCase());
        waitAction(format("Check that '%s' equals to '%s'", actual, expected), resultAction, failMessage);
    }
    public <T> void areEquals(JFuncT<T> actual, T expected) {
        areEquals(actual, expected, null);
    }
    public void matches(JFuncT<String> actual, String regEx, String failMessage) {
        JFuncT<Boolean> resultAction = (ignoreCase && regEx.getClass() == String.class)
                ? () -> actual.invoke().matches(regEx)
                : () -> actual.invoke().toLowerCase().matches(regEx.toLowerCase());
        waitAction(format("Check that '%s' matches to regEx '%s", actual, regEx), resultAction, failMessage);
    }
    public void matches(JFuncT<String> actual, String regEx) {
        matches(actual, regEx, null);
    }

    public void contains(JFuncT<String> actual, String expected, String failMessage) {
        JFuncT<Boolean> resultAction = (ignoreCase && expected.getClass() == String.class)
                ? () -> actual.invoke().contains(expected)
                : () -> actual.invoke().toLowerCase().contains(expected.toLowerCase());
        waitAction(format("Check that '%s' contains '%s'", actual, expected), resultAction, failMessage);
    }
    public void contains(JFuncT<String> actual, String expected) {
        contains(actual, expected, null);
    }

    public void isTrue(JFuncT<Boolean> condition, String failMessage) {
        waitAction(format("Check that condition '%s' is True", condition), condition, failMessage);
    }
    public void isTrue(JFuncT<Boolean> condition) {
        isTrue(condition, null);
    }

    public void isFalse(JFuncT<Boolean> condition, String failMessage) {
        waitAction(format("Check that condition '%s' is False", condition), () -> !condition.invoke(), failMessage);
    }
    public void isFalse(JFuncT<Boolean> condition) {
        isFalse(condition, null);
    }

    public void isEmpty(JFuncT<Object> obj, String failMessage) {
        waitAction("Check that Object is empty", () -> isObjEmpty(obj), failMessage);
    }

    public void isEmpty(JFuncT<Object> obj) {
        isEmpty(obj, null);
    }

    public void isNotEmpty(JFuncT<Object> obj, String failMessage) {
        waitAction("Check that Object is NOT empty", () -> !isObjEmpty(obj), failMessage);
    }

    public void isNotEmpty(JFuncT<Object> obj) {
        isNotEmpty(obj, null);
    }

    public <T> void areSame(JFuncT<T> actual, T expected, String failMessage) {
        waitAction("Check that Objects are the same", () -> actual == expected, failMessage);
    }

    public <T> void areSame(JFuncT<T> actual, T expected) {
        areSame(actual, expected, null);
    }

    public <T> void areDifferent(JFuncT<T> actual, T expected, String failMessage) {
        waitAction("Check that Objects are different", () -> actual != expected, failMessage);
    }

    public <T> void areDifferent(JFuncT<T> actual, T expected) {
        areDifferent(actual, expected, null);
    }
    public <T> void collectionEquals(JFuncT<Collection<T>> actual, Collection<T> expected, String failMessage, boolean equalSize) {
        assertAction("Check that Collections are equal",
                () -> actual.invoke() != null && expected != null && (!equalSize || (actual.invoke().size() == expected.size()))
                        ? FOUND
                        : "listEquals failed because one of the Collections is null or empty",
                failMessage, true);
        assertAction(null, () -> {
            T notEqualElement = first(actual.invoke(), el -> !expected.contains(el));
            return (notEqualElement != null)
                    ? format("Collections '%s' and '%s' not equals at webElement '%s'",
                    print(select(actual.invoke(), Object::toString)), print(select(expected, Object::toString)), notEqualElement)
                    : FOUND;
        }, failMessage, true);
    }
    public <T> void listEquals(JFuncT<Collection<T>> actual, Collection<T> expected, String failMessage) {
        collectionEquals(actual, expected, failMessage, true);
    }
    public <T> void listEquals(JFuncT<Collection<T>> actual, Collection<T> expected) {
        listEquals(actual, expected, null);
    }
    public <T> void mapEqualsEntity(JFuncT<MapArray<String, String>> map, T entity, String failMessage) {
        collectionEquals(() -> map.invoke().pairs, objToSetValue(entity).pairs, failMessage, false);
    }
    public <T> void mapEqualsEntity(JFuncT<MapArray<String, String>> map, T entity) {
        mapEqualsEntity(map, entity, null);
    }
    public <T> void arrayEquals(JFuncT<T> actual, T expected, String failMessage) {
        assertAction("Check that Collections are equal",
                () -> actual.invoke() != null && expected != null && actual.invoke().getClass().isArray() && expected.getClass().isArray()
                        && getLength(actual.invoke()) == getLength(expected)
                        ? FOUND
                        : "arrayEquals failed because one of the Objects is not Array or empty",
                failMessage, true);
        assertAction(null, () -> {
            for (int i = 0; i <= getLength(actual.invoke()); i++)
                if (!get(actual.invoke(), i).equals(get(expected, i)))
                    return format("Arrays not equals at index '%s'. '%s' != '%s'. Arrays: '%s' and '%s'",
                            i, get(actual.invoke(), i), get(expected, i), printObjectAsArray(actual), printObjectAsArray(expected));
            return FOUND;
        }, failMessage, true);
    }
    public <T> void arrayEquals(JFuncT<T> actual, T expected) {
        arrayEquals(actual, expected, null);
    }
}
