package com.ggasoftware.jdiuitests.implementation.selenium.elements.composite;

import com.ggasoftware.jdiuitests.core.utils.common.LinqUtils;
import com.ggasoftware.jdiuitests.core.utils.common.ReflectionUtils;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.BaseElement;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.base.Clickable;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.interfaces.base.IClickable;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.interfaces.complex.IPagination;
import org.openqa.selenium.By;

import java.lang.reflect.Field;
import java.text.MessageFormat;
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
    private By nextLocator;
    private By previousLocator;
    private By firstLocator;
    private By lastLocator;

    public Pagination() {
        super();
    }

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
        Collection<Clickable> clickables = select(fields, f -> (Clickable) ReflectionUtils.getValueField(f, this));
        return LinqUtils.first(clickables, cl -> cl.getName().contains(getElementName(name.toLowerCase())));
    }

    private String cantChooseElementMsg(String actionName, String shortName, String action) {
        return MessageFormat.format("Can't choose {0} page for Element '{3}'. " +
                "Please specify locator for this action using constructor or add Clickable Element " +
                "on pageObject with name '{1}Link' or '{1}Button' or use locator template with parameter '{1}'" +
                "or override {2}() in class", actionName, shortName, action, toString());
    }

    protected Clickable nextAction() {
        String shortName = "next";
        if (nextLocator != null)
            return new Clickable(nextLocator);

        Clickable nextLink = getClickable(shortName);
        if (nextLink != null)
            return nextLink;

        if (getLocator() != null && getLocator().toString().contains("'%s'"))
            return new Clickable(fillByTemplate(getLocator(), shortName));
        throw exception(cantChooseElementMsg("Next", shortName, "nextAction"));
    }

    private Clickable previousAction() {
        String shortName = "prev";
        if (previousLocator != null)
            return new Clickable(previousLocator);

        Clickable prevLink = getClickable(shortName);
        if (prevLink != null)
            return prevLink;

        if (getLocator() != null && getLocator().toString().contains("'%s'"))
            return new Clickable(fillByTemplate(getLocator(), shortName));
        throw exception(cantChooseElementMsg("Previous", shortName, "previousAction"));
    }

    private Clickable firstAction() {
        String shortName = "first";
        if (firstLocator != null)
            return new Clickable(firstLocator);

        Clickable firstLink = getClickable(shortName);
        if (firstLink != null)
            return firstLink;

        if (getLocator() != null && getLocator().toString().contains("'%s'"))
            return new Clickable(fillByTemplate(getLocator(), shortName));
        throw exception(cantChooseElementMsg("First", shortName, "firstAction"));
    }

    private Clickable lastAction() {
        String shortName = "last";
        if (lastLocator != null)
            return new Clickable(lastLocator);

        Clickable lastLink = getClickable(shortName);
        if (lastLink != null)
            return lastLink;

        if (getLocator() != null && getLocator().toString().contains("'%s'"))
            return new Clickable(fillByTemplate(getLocator(), shortName));
        throw exception(cantChooseElementMsg("Last", shortName, "lastAction"));
    }

    private Clickable pageAction(int index) {
        String shortName = "page";
        if (getLocator() != null && getLocator().toString().contains("'%s'"))
            return new Clickable(fillByTemplate(getLocator(), index));

        Clickable pageLink = getClickable(shortName);
        if (pageLink != null)
            return pageLink;
        throw exception(cantChooseElementMsg(Integer.toString(index), shortName, "pageAction"));
    }
}
