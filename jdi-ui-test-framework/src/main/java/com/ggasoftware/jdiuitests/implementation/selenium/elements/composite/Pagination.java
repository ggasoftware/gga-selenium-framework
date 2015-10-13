package com.ggasoftware.jdiuitests.implementation.selenium.elements.composite;

import com.ggasoftware.jdiuitests.core.utils.common.LinqUtils;
import com.ggasoftware.jdiuitests.core.utils.common.ReflectionUtils;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.BaseElement;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.base.Clickable;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.interfaces.base.IClickable;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.interfaces.complex.IPagination;
import org.openqa.selenium.By;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;

import static com.ggasoftware.jdiuitests.core.settings.JDISettings.exception;
import static com.ggasoftware.jdiuitests.core.utils.common.LinqUtils.select;
import static com.ggasoftware.jdiuitests.core.utils.common.WebDriverByUtils.fillByTemplate;
import static com.ggasoftware.jdiuitests.implementation.selenium.elements.pageobjects.annotations.AnnotationsUtil.getElementName;
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
        invoker.doJAction("Choose Next page", () -> nextAction().click());
    }

    public void previous() {
        invoker.doJAction("Choose Previous page", () -> previousAction().click());
    }

    public void first() {
        invoker.doJAction("Choose First page", () -> firstAction().click());
    }

    public void last() {
        invoker.doJAction("Choose Last page", () -> lastAction().click());
    }

    public void selectPage(int index) {
        invoker.doJAction(format("Choose '%s' page", index), () -> pageAction(index).click());
    }

    private Clickable getClickable(String name) {
        List<Field> fields = ReflectionUtils.getFields(this, IClickable.class);
        Collection<Clickable> clickables = select(fields, f -> (Clickable) ReflectionUtils.getFieldValue(f, this));
        Clickable clickable = LinqUtils.first(clickables, cl -> cl.getName().contains(getElementName(name.toLowerCase())));
        if (clickable == null)
            throw exception("Can't find clickable Element '%s' for Element '%s'", name, toString());
        return clickable;
    }

    protected Clickable nextAction() {
        if (nextLocator != null)
            return new Clickable(nextLocator);

        Clickable nextLink = getClickable("next");
        if (nextLink != null)
            return nextLink;

        if (getLocator() != null && getLocator().toString().contains("'%s'"))
            return new Clickable(fillByTemplate(getLocator(), "next"));

        throw exception("Can't choose Next page for Element '%s'. " +
                "Please specify locator for this action using constructor or add Clickable Element " +
                "on pageObject with name 'nextLink' or 'nextButton' or use locator template with parameter 'next'" +
                "or override nextAction() in class", toString());
    }
    private Clickable previousAction() {
        if (previousLocator != null)
            return new Clickable(previousLocator);

        Clickable prevLink = getClickable("prev");
        if (prevLink != null)
            return prevLink;

        if (getLocator() != null && getLocator().toString().contains("'%s'"))
            return new Clickable(fillByTemplate(getLocator(), "prev"));

        throw exception("Can't choose Previous page for Element '%s'. " +
                "Please specify locator for this action using constructor or add Clickable Element " +
                "on pageObject with name 'prevLink' or 'prevButton' or use locator template with parameter 'prev'" +
                "or override previousAction() in class", toString());
    }
    private Clickable firstAction() {
        if (firstLocator != null)
            return new Clickable(firstLocator);

        Clickable firstLink = getClickable("first");
        if (firstLink != null)
            return firstLink;

        if (getLocator() != null && getLocator().toString().contains("'%s'"))
            return new Clickable(fillByTemplate(getLocator(), "first"));

        throw exception("Can't choose First page for Element '%s'. " +
                "Please specify locator for this action using constructor or add Clickable Element " +
                "on pageObject with name 'firstLink' or 'firstButton' or use locator template with parameter 'first'" +
                "or override firstAction() in class", toString());
    }
    private Clickable lastAction() {
        if (lastLocator != null)
            return new Clickable(lastLocator);

        Clickable lastLink = getClickable("last");
        if (lastLink != null)
            return lastLink;

        if (getLocator() != null && getLocator().toString().contains("'%s'"))
            return new Clickable(fillByTemplate(getLocator(), "last"));

        throw exception("Can't choose Last page for Element '%s'. " +
                "Please specify locator for this action using constructor or add Clickable Element " +
                "on pageObject with name 'lastLink' or 'lastButton' or use locator template with parameter 'last'" +
                "or override lastAction() in class", toString());
    }
    private Clickable pageAction(int index) {
        if (getLocator() != null && getLocator().toString().contains("'%s'"))
            return new Clickable(fillByTemplate(getLocator(), index));

        Clickable pageLink = getClickable("page");
        if (pageLink != null)
            return pageLink;

        throw exception("Can't choose page '%s' for Element '%s'. " +
                "Please specify locator for this action using constructor or add Clickable Element " +
                "on pageObject with name 'pageLink' or 'pageButton'" +
                "or override pageAction() in class", index, toString());
    }
}
