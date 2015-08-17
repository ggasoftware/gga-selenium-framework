package com.ggasoftware.jdi_ui_tests.implementations.elements.selenium.composite;

import com.ggasoftware.jdi_ui_tests.core.elements.interfaces.common.IButton;
import com.ggasoftware.jdi_ui_tests.core.elements.interfaces.complex.ISearch;
import com.ggasoftware.jdi_ui_tests.implementations.elements.selenium.base.Clickable;
import com.ggasoftware.jdi_ui_tests.implementations.elements.selenium.common.Button;
import com.ggasoftware.jdi_ui_tests.implementations.elements.selenium.common.TextField;
import com.ggasoftware.jdi_ui_tests.implementations.elements.selenium.complex.TextList;
import org.openqa.selenium.By;

import java.lang.reflect.Field;
import java.util.List;

import static com.ggasoftware.jdi_ui_tests.core.settings.JDISettings.asserter;
import static com.ggasoftware.jdi_ui_tests.core.settings.JDISettings.logger;
import static com.ggasoftware.jdi_ui_tests.utils.common.ReflectionUtils.getFieldValue;
import static com.ggasoftware.jdi_ui_tests.utils.common.ReflectionUtils.getFields;
import static java.lang.String.format;

/**
 * Created by Roman_Iovlev on 7/29/2015.
 */
public class Search extends TextField implements ISearch {
    public Search() { super(); }
    public Search(By byLocator) { super(byLocator); }
    public Search(By byLocator, By selectLocator) {
        this(byLocator, selectLocator, null);
    }
    public Search(By byLocator, By selectLocator, By suggestionsListLocator) {
        super(byLocator);
        this.select = new Clickable(selectLocator);
        this.suggestions = new TextList(suggestionsListLocator);
    }

    private Clickable select;
    private TextList suggestions;

    public void chooseSuggestion(String text, String selectValue) {
        logger.info(format("Search for text '%s' and choose suggestion '%s", text, selectValue));
        input(text);
        getElement(selectValue).click();
    }

    public void chooseSuggestion(String text, int selectIndex) {
        logger.info(format("Search for text '%s' and choose suggestion '%s", text, selectIndex));
        input(text);
        getSuggestions().getElement(selectIndex).click();
    }

    public void search(String text) {
        logger.info(format("Search text '%s'", text));
        newInput(text);
        getSearchButton().click();
    }

    public List<String> getSuggesions(String text) {
        logger.info(format("Get all suggestions for input '%s'", text));
        return getSuggestions().getLabels();
    }

    private TextList getSuggestions() {
        if (suggestions == null)
            throw asserter.exception("Suggestions list locator not specified for search. Use accordance constructor");
        return suggestions;
    }

    private Clickable getElement(String name) {
        if (select != null)
            return copy(select, fillByTemplateSilent(getLocator(), name));
        else
            throw asserter.exception("Select locator not specified for search. Use accordance constructor");
        return null;
    }
    private Button getSearchButton() {
        List<Field> fields = getFields(this, IButton.class);
        if (fields.size() != 1)
            throw asserter.exception((fields.size() == 0)
                ? format("Can't find any buttons on form '%s.", toString())
                : format("Form '%s' have more than 1 button. Use submit(entity, buttonName) for this case instead", toString()));
        return (Button) getFieldValue(fields.get(0), this);
    }
}
