package com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.composite;

import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.base.Clickable;
import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.common.TextField;
import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.complex.TextList;
import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.interfaces.common.IButton;
import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.interfaces.common.ITextField;
import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.interfaces.complex.ISearch;
import org.openqa.selenium.By;

import java.lang.reflect.Field;
import java.util.List;

import static com.ggasoftware.jdi_ui_tests.core.settings.JDISettings.exception;
import static com.ggasoftware.jdi_ui_tests.core.utils.common.ReflectionUtils.getFieldValue;
import static com.ggasoftware.jdi_ui_tests.core.utils.common.ReflectionUtils.getFields;
import static com.ggasoftware.jdi_ui_tests.core.utils.common.WebDriverByUtils.fillByTemplate;
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

    protected Clickable select;
    protected TextList<Enum> suggestions;

    protected void findAction(String text) {
        getSearchField().newInput(text);
        getSearchButton().click();
    }
    protected void chooseSuggestionAction(String text, String selectValue) {
        getSearchField().input(text);
        getElement(selectValue).click();
    }
    protected void chooseSuggestionAction(String text, int selectIndex) {
        getSearchField().input(text);
        getSuggestions().getElement(selectIndex).click();
    }
    protected List<String> getSuggesionsAction(String text) {
        getSearchField().input(text);
        return getSuggestions().getLabels();
    }

    public final void find(String text) {
        invoker.doJAction(format("Search text '%s'", text), () -> findAction(text));
    }
    public final void chooseSuggestion(String text, String selectValue) {
        invoker.doJAction(format("Search for text '%s' and choose suggestion '%s'", text, selectValue),
                () -> chooseSuggestionAction(text, selectValue));
    }
    public final void chooseSuggestion(String text, int selectIndex) {
        invoker.doJAction(format("Search for text '%s' and choose suggestion '%s'", text, selectIndex),
                () -> chooseSuggestionAction(text, selectIndex));
    }
    public final List<String> getSuggesions(String text) {
        return invoker.doJActionResult(format("Get all suggestions for input '%s'", text),
                () -> getSuggesionsAction(text));
    }

    private TextList<Enum> getSuggestions() {
        if (suggestions != null)
            return suggestions;
        else
            throw exception("Suggestions list locator not specified for search. Use accordance constructor");
    }

    private Clickable getElement(String name) {
        if (select != null)
            return copy(select, fillByTemplate(getLocator(), name));
        else
            throw exception("Select locator not specified for search. Use accordance constructor");
    }
    private ITextField getSearchField() {
        List<Field> fields = getFields(this, ITextField.class);
        switch (fields.size()) {
            case 0:
                throw exception("Can't find any buttons on form '%s.", toString());
            case 1:
                return (ITextField) getFieldValue(fields.get(0), this);
            default:
                throw exception("Form '%s' have more than 1 button. Use submit(entity, buttonName) for this case instead", toString());
        }
    }
    private IButton getSearchButton() {
        List<Field> fields = getFields(this, IButton.class);
        switch (fields.size()) {
            case 0:
                throw exception("Can't find any buttons on form '%s.", toString());
            case 1:
                return (IButton) getFieldValue(fields.get(0), this);
            default:
                throw exception("Form '%s' have more than 1 button. Use submit(entity, buttonName) for this case instead", toString());
        }
    }
}
