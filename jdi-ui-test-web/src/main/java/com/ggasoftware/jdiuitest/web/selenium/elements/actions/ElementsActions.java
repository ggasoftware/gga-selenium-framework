/*
 * Copyright 2004-2016 EPAM Systems
 *
 * This file is part of JDI project.
 *
 * JDI is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * JDI is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; 
 * without even the implied warranty ofMERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. 
 * See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with JDI. If not, see <http://www.gnu.org/licenses/>.
 */
 
package com.ggasoftware.jdiuitest.web.selenium.elements.actions;

import com.ggasoftware.jdiuitest.core.utils.common.LinqUtils;
import com.ggasoftware.jdiuitest.core.utils.common.PrintUtils;
import com.ggasoftware.jdiuitest.core.utils.linqinterfaces.JAction;
import com.ggasoftware.jdiuitest.core.utils.linqinterfaces.JActionT;
import com.ggasoftware.jdiuitest.core.utils.linqinterfaces.JFuncT;
import com.ggasoftware.jdiuitest.core.utils.linqinterfaces.JFuncTT;
import com.ggasoftware.jdiuitest.web.selenium.elements.BaseElement;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

import static com.ggasoftware.jdiuitest.core.utils.common.Timer.getByCondition;
import static java.lang.String.format;
import static java.lang.String.join;

/**
 * Created by Roman_Iovlev on 9/3/2015.
 */
public class ElementsActions {
    private BaseElement element;

    public ElementsActions(BaseElement element) {
        this.element = element;
    }

    public ActionInvoker invoker() {
        return element.invoker;
    }

    // Element Actions
    public boolean isDisplayed(JFuncT<Boolean> isDisplayed) {
        return invoker().doJActionResult("Is element displayed", isDisplayed);
    }

    public boolean isHidden(JFuncT<Boolean> isHidden) {
        return invoker().doJActionResult("Is element hidden", isHidden);
    }

    public boolean waitDisplayed(JFuncT<Boolean> isDisplayed) {
        return invoker().doJActionResult("Wait element displayed", isDisplayed);
    }

    public boolean waitVanished(JFuncT<Boolean> isVanished) {
        return invoker().doJActionResult("Wait element vanished", isVanished);
    }

    // Value Actions
    public String getValue(JFuncT<String> getValueFunc) {
        return invoker().doJActionResult("Get value", getValueFunc);
    }

    public void setValue(String value, JActionT<String> setValueAction) {
        invoker().doJAction("Get value", () -> setValueAction.invoke(value));
    }

    // Click Action
    public void click(JAction clickAction) {
        invoker().doJAction("Click on Element", clickAction);
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
    public void select(String name, JActionT<String> selectAction) {
        invoker().doJAction(format("Select '%s'", name), () -> selectAction.invoke(name));
    }

    public void select(int index, JActionT<Integer> selectByIndexAction) {
        invoker().doJAction(format("Select '%s'", index), () -> selectByIndexAction.invoke(index));
    }

    public boolean isSelected(String name, JFuncTT<String, Boolean> isSelectedAction) {
        return invoker().doJActionResult(format("Wait is '%s' selected", name), () -> isSelectedAction.invoke(name));
    }

    public String getSelected(JFuncT<String> isSelectedAction) {
        return invoker().doJActionResult("Get Selected element name", isSelectedAction::invoke);
    }

    public int getSelectedIndex(JFuncT<Integer> isSelectedAction) {
        return invoker().doJActionResult("Get Selected element index", isSelectedAction::invoke);
    }

    //MultiSelector
    public void select(JActionT<String[]> selectListAction, String... names) {
        invoker().doJAction(String.format("Select '%s'", PrintUtils.print(names)), () -> selectListAction.invoke(names));
    }

    public void select(JActionT<int[]> selectListAction, int[] indexes) {
        List<String> listIndexes = new ArrayList<>();
        for (int i : indexes)
            listIndexes.add(Integer.toString(i));
        invoker().doJAction(String.format("Select '%s'", PrintUtils.print(listIndexes)), () -> selectListAction.invoke(indexes));
    }

    public List<String> areSelected(JFuncT<List<String>> getNames, JFuncTT<String, Boolean> waitSelectedAction) {
        return invoker().doJActionResult("Are selected", () ->
                LinqUtils.where(getNames.invoke(), waitSelectedAction));
    }

    public boolean waitSelected(JFuncTT<String, Boolean> waitSelectedAction, String... names) {
        return invoker().doJActionResult(String.format("Are deselected '%s'", PrintUtils.print(names)), () -> {
            for (String name : names)
                if (!waitSelectedAction.invoke(name))
                    return false;
            return true;
        });
    }

    public List<String> areDeselected(JFuncT<List<String>> getNames, JFuncTT<String, Boolean> waitSelectedAction) {
        return invoker().doJActionResult("Are deselected", () ->
                LinqUtils.where(getNames.invoke(), name -> !waitSelectedAction.invoke(name)));
    }

    public boolean waitDeselected(JFuncTT<String, Boolean> waitSelectedAction, String... names) {
        return invoker().doJActionResult(String.format("Are deselected '%s'", PrintUtils.print(names)), () -> {
            for (String name : names)
                if (waitSelectedAction.invoke(name))
                    return false;
            return true;
        });
    }

    public <T> T findImmediately(JFuncT<T> func, T ifError) {
        element.setWaitTimeout(0);
        JFuncTT<WebElement, Boolean> temp = element.avatar.localElementSearchCriteria;
        element.avatar.localElementSearchCriteria = el -> true;
        T result;
        try {
            result = func.invoke();
        } catch (Exception | Error ex) {
            result = ifError;
        }
        element.avatar.localElementSearchCriteria = temp;
        element.restoreWaitTimeout();
        return result;
    }
}
