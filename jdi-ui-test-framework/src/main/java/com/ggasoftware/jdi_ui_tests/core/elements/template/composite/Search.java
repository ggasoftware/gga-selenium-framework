package com.ggasoftware.jdi_ui_tests.core.elements.template.composite;

import com.ggasoftware.jdi_ui_tests.core.elements.template.common.TextField;
import com.ggasoftware.jdi_ui_tests.core.elements.interfaces.base.*;
import com.ggasoftware.jdi_ui_tests.core.elements.interfaces.common.IButton;
import com.ggasoftware.jdi_ui_tests.core.elements.interfaces.complex.ISearch;

import java.lang.reflect.Field;
import java.util.List;

import static com.ggasoftware.jdi_ui_tests.core.settings.JDISettings.*;
import static com.ggasoftware.jdi_ui_tests.utils.common.LinqUtils.where;
import static com.ggasoftware.jdi_ui_tests.utils.common.ReflectionUtils.*;
import static java.lang.String.format;

/**
 * Created by Roman_Iovlev on 7/29/2015.
 */
public class Search extends TextField implements ISearch {
    protected IClickable select;
    protected ISelector suggestions;

    public void chooseSuggestion(String text, String selectValue) {
        logger.info(format("Search for text '%s' and choose suggestion '%s", text, selectValue));
        input(text);
        getElement(selectValue).click();
    }

    public void chooseSuggestion(String text, int selectIndex) {
        logger.info(format("Search for text '%s' and choose suggestion '%s", text, selectIndex));
        input(text);
        getSuggestions().select(selectIndex);
    }

    public void search(String text) {
        logger.info(format("Search text '%s'", text));
        newInput(text);
        getSearchButton().click();
    }

    public List<String> getSuggesions(String text) {
        logger.info(format("Get all suggestions for input '%s'", text));
        return getSuggestions().getOptions();
    }

    private ISelector getSuggestions() {
        tryGetSuggestionsElement();
        if (suggestions == null)
            throw asserter.exception("Suggestions list locator not specified for search. Use accordance constructor");
        return suggestions;
    }

    private IClickable getElement(String name) {
        tryGetSelectElement();
        if (select == null)
            throw asserter.exception("Select locator not specified for search. Use accordance constructor");
        return copyFromTemplate(select, name);
    }

    private IButton getSearchButton() {
        List<Field> fields = getFields(this, IButton.class);
        if (fields.size() != 1)
            throw asserter.exception((fields.size() == 0)
                ? format("Can't find any Buttons on form '%s'.", toString())
                : format("Form '%s' have more than 1 Button. Use submit(entity, buttonName) for this case instead", toString()));
        return (IButton) getFieldValue(fields.get(0), this);
    }
    private void tryGetSuggestionsElement() {
        if (suggestions != null) return;
        List<Field> fields = getFields(this, ISelector.class);
        if (fields.size() != 1)
            throw asserter.exception((fields.size() == 0)
                    ? format("Can't find no one TextList element for suggestions on form '%s'.", toString())
                    : format("Form '%s' have more than 1 TextList elements for Suggestion", toString()));
        suggestions = (ISelector) getFieldValue(fields.get(0), this);
    }

    private void tryGetSelectElement() {
        if (select != null) return;
        List<Field> fields = (List<Field>) where(getFields(this, IClickable.class),
                f -> ((IClickable) getFieldValue(f, this)).printLocator().contains("%s"));
        if (fields.size() != 1)
            throw asserter.exception((fields.size() == 0)
                    ? format("Can't find no one Template Clickable element for Select on form '%s'.", toString()) +
                        "Add element IClickable with locator contains '%s'"
                    : format("Form '%s' have more than 1 Template Clickable elements for Select", toString()));
        select = (IClickable) getFieldValue(fields.get(0), this);
    }
}
