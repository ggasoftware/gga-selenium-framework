package com.epam.ui_test_framework.elements.composite;

import com.epam.ui_test_framework.elements.BaseElement;
import com.epam.ui_test_framework.elements.base.Clickable;
import com.epam.ui_test_framework.elements.interfaces.base.IClickable;
import com.epam.ui_test_framework.elements.interfaces.complex.IPagination;
import com.epam.ui_test_framework.utils.common.LinqUtils;
import org.openqa.selenium.By;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;

import static com.epam.ui_test_framework.elements.page_objects.annotations.AnnotationsUtil.getElementName;
import static com.epam.ui_test_framework.settings.FrameworkSettings.*;
import static com.epam.ui_test_framework.utils.common.LinqUtils.select;
import static com.epam.ui_test_framework.utils.common.ReflectionUtils.*;
import static com.epam.ui_test_framework.utils.common.WebDriverByUtils.fillByTemplate;
import static com.epam.ui_test_framework.utils.usefulUtils.TryCatchUtil.tryGetResult;
import static java.lang.String.format;

/**
 * Created by Roman_Iovlev on 7/29/2015.
 */
public class Pagination extends BaseElement implements IPagination {
    public Pagination() { super(); }
    public Pagination(By byLocator) {
        super(byLocator);
    }
    public Pagination(By nextLocator, By previousLocator) {
        this(null, nextLocator, previousLocator, null, null);
    }
    public Pagination(By pageTemplateLocator, By nextLocator, By previousLocator) {
        this(pageTemplateLocator, nextLocator, previousLocator, null, null);
    }
    public Pagination(By pageTemplateLocator, By nextLocator, By previousLocator,
                      By firstLocator, By lastLocator) {
        super(pageTemplateLocator);
        this.nextLocator = nextLocator;
        this.previousLocator = previousLocator;
        this.firstLocator = firstLocator;
        this.lastLocator = lastLocator;
    }

    private By nextLocator;
    private By previousLocator;
    private By firstLocator;
    private By lastLocator;

    public void next() {
        doJAction("Choose Next page", () -> nextAction().click());
    }

    public void previous() {
        doJAction("Choose Previous page", () -> previousAction().click());
    }

    public void first() {
        doJAction("Choose First page", () -> firstAction().click());
    }

    public void last() {
        doJAction("Choose Last page", () -> lastAction().click());
    }

    public void selectPage(int index) {
        doJAction(format("Choose '%s' page", index), () -> pageAction(index).click());
    }

    private Clickable getClickable(String name) {
        List<Field> fields = getFields(this, IClickable.class);
        Collection<Clickable> clickables = select(fields, f -> (Clickable) getFieldValue(f, this));
        Clickable clickable = LinqUtils.first(clickables, cl -> cl.getName().contains(getElementName(name.toLowerCase())));
        if (clickable == null) {
            asserter.exception(format("Can't find clickable element '%s' for element '%s'", name, toString()));
            return null;
        }
        return clickable;
    }

    protected Clickable nextAction() {
        if (nextLocator != null)
            return new Clickable(nextLocator);

        Clickable nextLink = getClickable("next");
        if (nextLink != null)
            return nextLink;

        if (getLocator() != null && getLocator().toString().contains("'%s'"))
            return new Clickable(tryGetResult(() -> fillByTemplate(getLocator(), "next")));

        asserter.exception(format("Can't choose Next page for element '%s'. " +
                "Please specify locator for this action using constructor or add Clickable element " +
                "on pageObject with name 'nextLink' or 'nextButton' or use locator template with parameter 'next'" +
                "or override nextAction() in class", toString()));
        return null;
    }
    private Clickable previousAction() {
        if (previousLocator != null)
            return new Clickable(previousLocator);

        Clickable prevLink = getClickable("prev");
        if (prevLink != null)
            return prevLink;

        if (getLocator() != null && getLocator().toString().contains("'%s'"))
            return new Clickable(tryGetResult(() -> fillByTemplate(getLocator(), "prev")));

        asserter.exception(format("Can't choose Previous page for element '%s'. " +
                "Please specify locator for this action using constructor or add Clickable element " +
                "on pageObject with name 'prevLink' or 'prevButton' or use locator template with parameter 'prev'" +
                "or override previousAction() in class", toString()));
        return null;
    }
    private Clickable firstAction() {
        if (firstLocator != null)
            return new Clickable(firstLocator);

        Clickable firstLink = getClickable("first");
        if (firstLink != null)
            return firstLink;

        if (getLocator() != null && getLocator().toString().contains("'%s'"))
            return new Clickable(tryGetResult(() -> fillByTemplate(getLocator(), "first")));

        asserter.exception(format("Can't choose First page for element '%s'. " +
                "Please specify locator for this action using constructor or add Clickable element " +
                "on pageObject with name 'firstLink' or 'firstButton' or use locator template with parameter 'first'" +
                "or override firstAction() in class", toString()));
        return null;
    }
    private Clickable lastAction() {
        if (lastLocator != null)
            return new Clickable(lastLocator);

        Clickable lastLink = getClickable("last");
        if (lastLink != null)
            return lastLink;

        if (getLocator() != null && getLocator().toString().contains("'%s'"))
            return new Clickable(tryGetResult(() -> fillByTemplate(getLocator(), "last")));

        asserter.exception(format("Can't choose Last page for element '%s'. " +
                "Please specify locator for this action using constructor or add Clickable element " +
                "on pageObject with name 'lastLink' or 'lastButton' or use locator template with parameter 'last'" +
                "or override lastAction() in class", toString()));
        return null;
    }
    private Clickable pageAction(int index) {
        if (getLocator() != null && getLocator().toString().contains("'%s'"))
            return new Clickable(tryGetResult(() -> fillByTemplate(getLocator(), index)));

        Clickable pageLink = getClickable("page");
        if (pageLink != null)
            return pageLink;

        asserter.exception(format("Can't choose page '%s' for element '%s'. " +
                "Please specify locator for this action using constructor or add Clickable element " +
                "on pageObject with name 'pageLink' or 'pageButton'" +
                "or override pageAction() in class", index, toString()));
        return null;
    }
}
