/****************************************************************************
 * Copyright (C) 2014 GGA Software Services LLC
 *
 * This file may be distributed and/or modified under the terms of the
 * GNU General Public License version 3 as published by the Free Software
 * Foundation.
 *
 * This file is provided AS IS with NO WARRANTY OF ANY KIND, INCLUDING THE
 * WARRANTY OF DESIGN, MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see <http://www.gnu.org/licenses>.
 ***************************************************************************/
package com.ggasoftware.uitest.control;

import com.ggasoftware.uitest.control.interfaces.complex.IComboBox;
import com.ggasoftware.uitest.control.new_controls.base.SelectElement;
import com.ggasoftware.uitest.control.new_controls.complex.Dropdown;
import com.ggasoftware.uitest.utils.LinqUtils;
import com.ggasoftware.uitest.utils.ReporterNGExt;
import com.ggasoftware.uitest.utils.WebDriverWrapper;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

import static com.ggasoftware.uitest.utils.LinqUtils.firstIndex;
import static com.ggasoftware.uitest.utils.ReporterNGExt.logAction;
import static com.ggasoftware.uitest.utils.Timer.alwaysDoneAction;
import static com.ggasoftware.uitest.utils.Timer.getResultAction;
import static java.lang.String.format;
import static java.lang.System.currentTimeMillis;

/**
 * ComboBox control implementation
 *
 * @author Alexeenko Yan
 */
public class ComboBox<ParentPanel, TEnum extends Enum> extends Dropdown<TEnum, ParentPanel> implements IComboBox<TEnum, ParentPanel> {

    public ComboBox() { }
    public ComboBox(By valueLocator) {
        super(valueLocator);
        input = createInputAction(valueLocator);
    }
    public ComboBox(By selectorLocator, By optionsNamesLocatorTemplate) {
        super(selectorLocator, optionsNamesLocatorTemplate);
        input = createInputAction(selectorLocator);
    }
    public ComboBox(By selectorLocator, By optionsNamesLocatorTemplate, By valueLocator) {
        super(selectorLocator, optionsNamesLocatorTemplate);
        input = createInputAction(valueLocator);
    }
    public ComboBox(By selectorLocator, By optionsNamesLocatorTemplate, By valueLocator, By allOptionsNamesLocator) {
        super(selectorLocator, optionsNamesLocatorTemplate, allOptionsNamesLocator);
        input = createInputAction(valueLocator);
    }
    private Input input;
    private SelectElement selector;

    protected Input createInputAction(By valueLocator) { return new Input(valueLocator); }

    protected void inputAction(String text) { input.input(text); }
    protected void clearAction() { input.clear(); }
    protected void focusAction() { input.focus(); }

    @Override
    protected void setValueAction(String value) { newInput(value); }
    @Override
    protected String getValueAction() {
        return input.getText();
    }
    public final void input(String text) { input.input(text); }
    public final void newInput(String text) { input.newInput(text); }
    public final ParentPanel clear() { input.clear(); return parent; }
    public final ParentPanel focus() { input.focus(); return parent; }

    //constructors

    /**
     * Initializes element with given locator. Locates own properties of the element by class name, takes given locator and tries
     * to initialize.
     *
     * @param name        - Button Name
     * @param locator     - start it with locator type "id=", "css=", "xpath=" and etc. Locator without type is assigned to xpath
     * @param parentPanel - Panel which contains current button
     */
    public ComboBox(String name, String locator, ParentPanel parentPanel) {
        super(name, locator, parentPanel);
    }

    private Select select() { return new Select(getWebElement()); }

    /**
     * !!! Better do not use. Instead use just select(String name)
     * Select by the visible option text(contains)
     *
     * @param item - visible option text(contains)
     * @return Parent Panel instance
     */
    @Deprecated
    public ParentPanel selectByTextContains(String item) {
        return doJActionResult("select by text contains: %s", () -> {
            int firstIndex = getResultAction(() -> firstIndex(
                    select().getOptions(),
                    option -> option.getText().contains(item)));
            if (firstIndex > -1) {
                select().selectByIndex(firstIndex);
                return super.parent;
            }
            throw new NoSuchElementException(format("Cannot find item contains this text '%s'", item));
        });
    }

    /**
     * Get first selected option text.
     *
     * @return First Selected option.
     */
    public String getFirstSelectedItem() {
        return doJActionResult("Get selected items", () ->
                select().getFirstSelectedOption().getText());
    }

    /**
     * Get all selected options text.
     *
     * @return All Selected options.
     */
    public List<String> getSelectedItem() {
        return doJActionResult("Get selected items",  () -> (List<String>) LinqUtils.select(
                select().getAllSelectedOptions(),
                WebElement::getText));
    }

    /**
     * !!! Use getOptions() instead
     * Get all option text.
     *
     * @return List of all options.
     */
    @Deprecated
    public List<String> getItems() {
        return doJActionResult("Get all items",  () -> (List<String>) LinqUtils.select(
                select().getOptions(),
                WebElement::getText));
    }

    /**
     * !!! Use select(String value) instead
     * Wait until option is selected by value.
     *
     * @param value - option text
     * @return Parent Panel instance
     */
    @Deprecated
    public ParentPanel waitForItemAndSelect(final String value) {
        select(value);
        return parent;
    }

}
