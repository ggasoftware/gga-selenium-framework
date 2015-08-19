package com.ggasoftware.jdi_ui_tests.core.elements.template.composite;

import com.ggasoftware.jdi_ui_tests.core.elements.template.base.BaseElement;
import com.ggasoftware.jdi_ui_tests.core.elements.template.base.Clickable;
import com.ggasoftware.jdi_ui_tests.core.elements.interfaces.base.IClickable;
import com.ggasoftware.jdi_ui_tests.core.elements.interfaces.complex.IPagination;
import com.ggasoftware.jdi_ui_tests.utils.common.LinqUtils;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;

import static com.ggasoftware.jdi_ui_tests.core.elements.template.composite.Pagination.LinkTypes.*;
import static com.ggasoftware.jdi_ui_tests.core.elements.page_objects.annotations.AnnotationsUtil.getElementName;
import static com.ggasoftware.jdi_ui_tests.core.settings.JDISettings.asserter;
import static com.ggasoftware.jdi_ui_tests.utils.common.LinqUtils.select;
import static com.ggasoftware.jdi_ui_tests.utils.common.ReflectionUtils.getFieldValue;
import static com.ggasoftware.jdi_ui_tests.utils.common.ReflectionUtils.getFields;
import static java.text.MessageFormat.format;

/**
 * Created by Roman_Iovlev on 7/29/2015.
 */
public class Pagination extends BaseElement implements IPagination {
    // Actions
    enum LinkTypes { NEXT, PREV, FIRST, LAST, PAGE }
    protected Clickable tryGetClickableByLocatorAction(LinkTypes linkType);
    protected Clickable tryGetClickableFromTemplateAction(LinkTypes linkType);

    // Methods
    public void next() { doJAction("Choose Next page", () -> getClickable(NEXT).click()); }
    public void previous() { doJAction("Choose Previous page", () -> getClickable(PREV).click()); }
    public void first() { doJAction("Choose First page", () -> getClickable(FIRST).click()); }
    public void last() { doJAction("Choose Last page", () -> getClickable(LAST).click()); }

    public void selectPage(int index) {
        doJAction(String.format("Choose '%s' page", index),
                () -> copyFromTemplate(getClickable(PAGE), Integer.toString(index)).click());
    }

    private Clickable tryGetClickableByFieldName(LinkTypes linkType) {
        List<Field> fields = getFields(this, IClickable.class);
        Collection<Clickable> clickables = select(fields, f -> (Clickable) getFieldValue(f, this));
        return LinqUtils.first(clickables, cl -> cl.getName().toLowerCase().contains(getElementName(linkType.toString().toLowerCase())));
    }

    private Clickable getClickable(LinkTypes linkType) {
        Clickable clickable = tryGetClickableByLocatorAction(linkType);
        if (clickable == null) {
            clickable = tryGetClickableByFieldName(linkType);
            if (clickable == null && printLocator().contains("'%s'") )
                clickable = tryGetClickableFromTemplateAction(linkType);
            if (clickable == null)
                throw asserter.exception(format("Can't choose {0} page for element '{1}'. " +
                    "Please specify locator for this action using constructor or add Clickable element " +
                    "on pageObject with name '{0}Link' or '{0}Button' or use locator template with parameter '{0}'",
                    linkType.toString().toLowerCase(), toString()));
        }
        return clickable;
    }
}
