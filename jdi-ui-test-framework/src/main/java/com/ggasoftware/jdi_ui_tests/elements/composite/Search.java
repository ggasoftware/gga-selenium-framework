package com.ggasoftware.jdi_ui_tests.elements.composite;

import com.ggasoftware.jdi_ui_tests.elements.base.Clickable;
import com.ggasoftware.jdi_ui_tests.elements.common.TextField;
import com.ggasoftware.jdi_ui_tests.elements.complex.TextList;
import com.ggasoftware.jdi_ui_tests.elements.interfaces.common.IButton;
import com.ggasoftware.jdi_ui_tests.elements.interfaces.common.ITextField;
import com.ggasoftware.jdi_ui_tests.elements.interfaces.complex.ISearch;
import org.openqa.selenium.By;

import java.lang.reflect.Field;
import java.util.List;

import static com.ggasoftware.jdi_ui_tests.settings.JDISettings.asserter;
import static com.ggasoftware.jdi_ui_tests.settings.JDISettings.logger;
import static com.ggasoftware.jdi_ui_tests.utils.common.ReflectionUtils.getFieldValue;
import static com.ggasoftware.jdi_ui_tests.utils.common.ReflectionUtils.getFields;
import static com.ggasoftware.jdi_ui_tests.utils.common.WebDriverByUtils.fillByTemplateSilent;
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

    public void find(String text) {
        logger.info(format("Search text '%s'", text));
        getSearchField().newInput(text);
        getSearchButton().click();
    }
    public void chooseSuggestion(String text, String selectValue) {
        logger.info(format("Search for text '%s' and choose suggestion '%s", text, selectValue));
        getSearchField().input(text);
        getElement(selectValue).click();
    }

    public void chooseSuggestion(String text, int selectIndex) {
        logger.info(format("Search for text '%s' and choose suggestion '%s", text, selectIndex));
        getSearchField().input(text);
        getSuggestions().getElement(selectIndex).click();
    }

    public List<String> getSuggesions(String text) {
        logger.info(format("Get all suggestions for input '%s'", text));
        return getSuggestions().getLabels();
    }

    private TextList getSuggestions() {
        if (suggestions != null)
            return suggestions;
        else
            asserter.exception("Suggestions list locator not specified for search. Use accordance constructor");
        return null;
    }

    private Clickable getElement(String name) {
        if (select != null)
            return copy(select, fillByTemplateSilent(getLocator(), name));
        else
            asserter.exception("Select locator not specified for search. Use accordance constructor");
        return null;
    }
    private ITextField getSearchField() {
        List<Field> fields = getFields(this, ITextField.class);
        switch (fields.size()) {
            case 0:
                asserter.exception(format("Can't find any buttons on form '%s.", toString()));
                return null;
            case 1:
                return (ITextField) getFieldValue(fields.get(0), this);
            default:
                asserter.exception(format("Form '%s' have more than 1 button. Use submit(entity, buttonName) for this case instead", toString()));
                return null;
        }
    }
    private IButton getSearchButton() {
        List<Field> fields = getFields(this, IButton.class);
        switch (fields.size()) {
            case 0:
                asserter.exception(format("Can't find any buttons on form '%s.", toString()));
                return null;
            case 1:
                return (IButton) getFieldValue(fields.get(0), this);
            default:
                asserter.exception(format("Form '%s' have more than 1 button. Use submit(entity, buttonName) for this case instead", toString()));
                return null;
        }
    }
}
