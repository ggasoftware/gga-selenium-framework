package com.ggasoftware.jdi_ui_tests.core.elements.template.base;

import com.ggasoftware.jdi_ui_tests.core.elements.interfaces.base.IBaseElement;
import com.ggasoftware.jdi_ui_tests.core.settings.DriverFactory;
import com.ggasoftware.jdi_ui_tests.utils.common.Timer;

import static com.ggasoftware.jdi_ui_tests.core.settings.DriverFactory.getCurrentDriverName;
import static com.ggasoftware.jdi_ui_tests.core.settings.JDISettings.*;
import static com.ggasoftware.jdi_ui_tests.utils.usefulUtils.TryCatchUtil.tryGetResult;
import static java.lang.String.format;

/**
 * Created by Roman_Iovlev on 8/17/2015.
 */
public class ElementHandler {
    public GetElementModule(IBaseElement element) {
        this.element = element;
        driverName = getCurrentDriverName();
    }
    public GetElementModule(TLocator locator, IBaseElement element) {
        this(element);
        this.locator = locator;
    }

    private List<TElement> searchElements();
    private List<TElement> searchElements(TLocator locator);
    private Boolean defaultSearchCriteria(TElement element) { return element.isDisplayed(); }

    private TLocator locator;
    public TLocator getLocator() { return locator; }
    public boolean hasLocator() { return locator != null; }
    private String driverName = "";
    private IBaseElement element;

    public TDriver getDriver() { return tryGetResult(() -> (TDriver) DriverFactory.getDriver(driverName)); }
    public TElement getElement() {
        logger.info("Get Web element: " + element);
        TElement element = Timer.getByCondition(this::getElementAction, el -> el != null);
        logger.debug("One element found");
        return element;
    }

    public List<TElement> getElements() {
        logger.info("Get Web elements: " + element);
        List<TElement> elements = getElementsAction();
        logger.debug(format("Found %s elements", elements.size()));
        return elements;
    }

    private List<TElement> getElementsAction() {
        List<TElement> result = timeouts.timer().getResultByCondition(
                searchElements(locator),
                els -> where(els, getSearchCriteria()::invoke).size() > 0);
        timeouts.dropTimeouts();
        return result;
    }
    private JFuncTT<TElement, Boolean> localElementSearchCriteria = null;
    private JFuncTT<TElement, Boolean> getSearchCriteria() {
        return localElementSearchCriteria != null ? localElementSearchCriteria : this::defaultSearchCriteria;
    }
    public void setLocalElementSearchCriteria(JFuncTT<TElement, Boolean> criteria) {
        localElementSearchCriteria = criteria;
    }

    private TElement getElementAction() {
        int timeout = timeouts.currentTimoutSec;
        List<TElement> result = getElementsAction();
        if (result == null) {
            asserter.exception(format(failedToFindElementMessage, element, timeout));
            return null;
        }
        if (result.size() > 1) {
            asserter.exception(format(findToMuchElementsMessage, result.size(), element, timeout));
            return null;
        }
        return result.get(0);
    }

    private static final String failedToFindElementMessage = "Can't find element '%s' during %s seconds";
    private static final String findToMuchElementsMessage = "Find %s elements instead of one for element '%s' during %s seconds";

    @Override
    public String toString() { return format("Locator: '%s'", byLocator); }
}
