package com.ggasoftware.jdi_ui_tests.core.drivers;

import com.ggasoftware.jdi_ui_tests.core.elements.interfaces.base.IBaseElement;
import com.ggasoftware.jdi_ui_tests.utils.common.Timer;

import java.util.List;

import static com.ggasoftware.jdi_ui_tests.settings.JDISettings.*;
import static java.lang.String.format;

/**
 * Created by Roman_Iovlev on 8/10/2015.
 */
public interface JLocationInfo<TDriver, TElement, TLocator, TContext> {
    boolean haveLocator();
    TDriver getDriver();
    TLocator getLocator();
    List<TContext> getContext();

    void init(IBaseElement<TDriver, TLocator> element);
    void init(TLocator locator, IBaseElement<TDriver, TLocator> element);
    void init(TLocator locator, List<TContext> context, IBaseElement<TDriver, TLocator> element);

    List<TElement> getElementsAction();
    String printElement();
    // void fillElementLocator(IBaseElement<TDriver, TLocator> element);

    default Timer timer() { return new Timer(timeouts.currentTimoutSec * 1000); }
    default TElement getElement() {
        logger.info("Get Web element: " + printElement());
        TElement element = timer().getResultByCondition(this::getElementAction, el -> el != null);
        logger.debug("One element found");
        return element;
    }

    default List<TElement> getElements() {
        logger.info("Get Web elements: " + printElement());
        List<TElement> elements = getElementsAction();
        logger.debug(format("Found %s elements", elements.size()));
        return elements;
    }

    default TElement getElementAction() {
        int timeout = timeouts.currentTimoutSec;
        List<TElement> result = getElementsAction();
        if (result == null) {
            asserter.exception(format(failedToFindElementMessage, printElement(), timeout));
            return null;
        }
        if (result.size() > 1) {
            asserter.exception(format(findToMuchElementsMessage, result.size(), printElement(), timeout));
            return null;
        }
        return result.get(0);
    }
    String failedToFindElementMessage = "Can't find element '%s' during %s seconds";
    String findToMuchElementsMessage = "Find %s elements instead of one for element '%s' during %s seconds";
}
