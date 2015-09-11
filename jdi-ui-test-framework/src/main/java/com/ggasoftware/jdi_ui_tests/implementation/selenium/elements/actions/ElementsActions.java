package com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.actions;

import com.ggasoftware.jdi_ui_tests.core.utils.linqInterfaces.JAction;
import com.ggasoftware.jdi_ui_tests.core.utils.linqInterfaces.JActionT;
import com.ggasoftware.jdi_ui_tests.core.utils.linqInterfaces.JFuncT;
import com.ggasoftware.jdi_ui_tests.core.utils.linqInterfaces.JFuncTT;
import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.BaseElement;
import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.base.SelectElement;

import java.util.ArrayList;
import java.util.List;

import static com.ggasoftware.jdi_ui_tests.core.utils.common.LinqUtils.first;
import static com.ggasoftware.jdi_ui_tests.core.utils.common.LinqUtils.where;
import static com.ggasoftware.jdi_ui_tests.core.utils.common.PrintUtils.print;
import static com.ggasoftware.jdi_ui_tests.core.utils.common.Timer.getByCondition;
import static java.lang.String.format;
import static java.lang.String.join;

/**
 * Created by Roman_Iovlev on 9/3/2015.
 */
public class ElementsActions {
    public BaseElement element;
    public ActionInvoker invoker() { return element.invoker; }
    public ElementsActions(BaseElement element) { this.element = element; }

    // Value Actions
    public String getValue(JFuncT<String> getValueFunc) {
        return invoker().doJActionResult("Get value", getValueFunc);
    }
    public void setValue(String value, JActionT<String> setValueAction) {
        invoker().doJAction("Get value", () -> setValueAction.invoke(value));
    }

    // Click Action
    public void click(JAction clickAction) {
        invoker().doJAction("Click on webElement", clickAction);
    }

    // Text Actions
    public String getText(JFuncT<String> getTextAction) {
        return invoker().doJActionResult("Get text", getTextAction);
    }
    public String waitText(String text, JFuncT<String> getTextAction) {
        return invoker().doJActionResult(format("Wait text contains '%s'", text),
                () -> getByCondition(getTextAction::invoke, t -> t.contains(text)));
    }
    public String waitMatchText(String regEx, JFuncT<String> getTextAction) {
        return invoker().doJActionResult(format("Wait text match regex '%s'", regEx),
                () -> getByCondition(getTextAction::invoke, t -> t.matches(regEx)));
    }

    // Check/Select Actions
    public boolean isSelected(JFuncT<Boolean> isSelectedAction) {
        return invoker().doJActionResult("Is Selected", isSelectedAction);
    }
    public void check(JAction checkAction) {
        invoker().doJAction("BaseChecker Checkbox", checkAction);
    }
    public void uncheck(JAction uncheckAction) {
        invoker().doJAction("Uncheck Checkbox", uncheckAction);
    }
    public boolean isChecked(JFuncT<Boolean> isCheckedAction) {
        return invoker().doJActionResult("IsChecked",
                isCheckedAction,
                result -> "Checkbox is " + (result ? "checked" : "unchecked"));
    }

    // Input Actions
    public void inputLines(JAction clearAction, JActionT<String> inputAction, String... textLines) {
        invoker().doJAction("Input several lines of text in textarea",
                () -> {
                    clearAction.invoke();
                    inputAction.invoke(join("\n", textLines));
                });
    }
    public void addNewLine(String textLine, JActionT<String> inputAction) {
        invoker().doJAction("Add text from new line in textarea",
                () -> inputAction.invoke("\n" + textLine));
    }
    public String[] getLines(JFuncT<String> getTextAction) {
        return invoker().doJActionResult("Get text as lines", () -> getTextAction.invoke().split("\\n"));
    }
    public void input(String text, JActionT<String> inputAction) {
        invoker().doJAction("Input text '" + text + "' in text field",
                () -> inputAction.invoke(text));
    }
    public void clear(JAction clearAction) {
        invoker().doJAction("Clear text field", clearAction);
    }
    public void focus(JAction focusAction) {
        invoker().doJAction("Focus on text field", focusAction);
    }

    // Selector
    public void select(String name, JActionT<String> selectAction) { invoker().doJAction(format("Select '%s'", name), () -> selectAction.invoke(name)); }
    public void select(int index, JActionT<Integer> selectByIndexAction) {
        invoker().doJAction(format("Select '%s'", index), () -> selectByIndexAction.invoke(index)); }
    public String isSelectedList(JFuncT<List<SelectElement>> getElementsList) {
        return invoker().doJActionResult("Is Selected", () -> {
            List<SelectElement> elements = getElementsList.invoke();
            if (elements == null) return null;
            SelectElement selectedElement = first(elements, SelectElement::isSelected);
            return (selectedElement != null) ? selectedElement.getText() : null;
        });
    }
    public boolean waitSelected(String name, JFuncTT<String, Boolean> waitSelectedAction) {
        return invoker().doJActionResult(format("Wait is '%s' selected", name), () -> waitSelectedAction.invoke(name));
    }

    //MultiSelector
    public void select(JActionT<String[]> selectListAction, String... names) {
        invoker().doJAction(format("Select '%s'", print(names)), () -> selectListAction.invoke(names));
    }
    public void select(JActionT<int[]> selectListAction, int[] indexes) {
        List<String> listIndexes = new ArrayList<>();
        for (int i : indexes)
            listIndexes.add(i+"");
        invoker().doJAction(format("Select '%s'", print(listIndexes)), () -> selectListAction.invoke(indexes));
    }
    public List<String> areSelected(JFuncT<List<String>> getNames, JFuncTT<String, Boolean> waitSelectedAction) {
        return invoker().doJActionResult("Are selected", () ->
                where(getNames.invoke(), waitSelectedAction));
    }
    public boolean waitSelected(JFuncTT<String, Boolean> waitSelectedAction, String... names) {
        return invoker().doJActionResult(format("Are deselected '%s'", print(names)), () -> {
            for (String name : names)
                if (!waitSelectedAction.invoke(name))
                    return false;
            return true;
        });
    }
    public List<String> areDeselected(JFuncT<List<String>> getNames, JFuncTT<String, Boolean> waitSelectedAction) {
        return invoker().doJActionResult("Are deselected", () ->
                where(getNames.invoke(), name -> !waitSelectedAction.invoke(name)));
    }
    public boolean waitDeselected(JFuncTT<String, Boolean> waitSelectedAction, String... names) {
        return invoker().doJActionResult(format("Are deselected '%s'", print(names)), () -> {
            for (String name : names)
                if (waitSelectedAction.invoke(name))
                    return false;
            return true;
        });
    }
}
