package com.ggasoftware.jdi_ui_tests.core.elements.base;

import com.ggasoftware.jdi_ui_tests.core.elements.interfaces.base.IElement;
import com.ggasoftware.jdi_ui_tests.core.elements.interfaces.base.IVisible;
import com.ggasoftware.jdi_ui_tests.core.elements.template.base.BaseElement;
import com.ggasoftware.jdi_ui_tests.utils.common.EnumUtils;

import java.util.List;
import java.util.stream.Collectors;

import static com.ggasoftware.jdi_ui_tests.core.settings.JDISettings.asserter;
import static com.ggasoftware.jdi_ui_tests.utils.common.EnumUtils.getEnumValue;
import static com.ggasoftware.jdi_ui_tests.utils.common.LinqUtils.select;
import static java.lang.String.format;

/**
 * Created by Roman_Iovlev on 7/3/2015.
 */
public abstract class TemplatesList<TType extends IElement, TEnum extends Enum> extends BaseElement implements IVisible {

    // Methods
    public void setListOfElements(List<String> elementsNames) { this.elementsNames = elementsNames; }
    public void setListOfElements(TEnum enumMember) { this.elementsNames =
            (List<String>) select(enumMember.getClass().getEnumConstants(), EnumUtils::getEnumValue); }
    protected List<String> elementsNames;
    private TType templateElement;

    public TType getElement(String name) {
        return copyFromTemplate(templateElement, name);
    }
    public TType getElement(TEnum enumName) {
        return getElement(getEnumValue(enumName));
    }

    protected List<TType> getElementsListAction() {
        try { return elementsNames.stream().map(this::getElement).collect(Collectors.toList());
        } catch (Exception ex) { throw asserter.exception(ex.getMessage()); }
    }
    public final List<TType> getElementsList() {
        if (elementsNames == null || elementsNames.size() == 0)
            throw asserter.exception(format("Please specify elements names for list element '%s'", toString()));
        return doJActionResult("Get elements", this::getElementsListAction);
    }
    public int count() {
        return getElementsList().size();
    }
}
