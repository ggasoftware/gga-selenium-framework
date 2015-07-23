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

import com.ggasoftware.uitest.control.interfaces.complex.IDropList;
import com.ggasoftware.uitest.control.new_controls.complex.MultiSelector;
import com.ggasoftware.uitest.utils.LinqUtils;
import com.ggasoftware.uitest.utils.ReporterNGExt;
import com.ggasoftware.uitest.utils.Timer;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

import static com.ggasoftware.uitest.utils.LinqUtils.first;
import static com.ggasoftware.uitest.utils.Timer.alwaysDoneAction;
import static com.ggasoftware.uitest.utils.Timer.getResultAction;

/**
 * DropBox control implementation
 *
 * @author Alexeenko Yan
 * @author Belousov Andrey
 */
public class DropBox<ParentPanel, TEnum extends Enum> extends MultiSelector<TEnum, ParentPanel>
        implements IDropList<TEnum> {
    public DropBox() { }
    public DropBox(By valueLocator) { super(valueLocator); }
    //constructor

    /**
     * Initializes element with given locator. Locates own properties of the element by class name, takes given locator and tries
     * to initialize.
     *
     * @param name        - DropBox name
     * @param locator     - start it with locator type "id=", "css=", "xpath=" and etc. Locator without type is assigned to xpath
     * @param parentPanel - Panel which contains current dropbox
     */
    public DropBox(String name, String locator, ParentPanel parentPanel) {
        super(name, locator, parentPanel);
    }

    private Select select() { return new Select(getWebElement()); }

    /**
     * !!! Use select(Strinh... names) instead
     * Select by the visible option text
     *
     * @param sItem - visible option text
     * @return Parent Panel instance
     */
    @Deprecated
    public ParentPanel selectByText(String sItem) {
        ReporterNGExt.logAction(this, getParentClassName(), String.format("Set Value (selectByVisibleText): %s", sItem));
        alwaysDoneAction(() -> select().selectByVisibleText(sItem));
        return super.parent;
    }

    /**
     * Select all options that have a value matching the argument. That is, when given "foo" this
     * would select an option like:
     *
     * &lt;option value="foo"&gt;Bar&lt;/option&gt;
     *
     * @param value The value to match against
     * @return Parent Panel instance
     */
    public ParentPanel selectByValue(String value) {
        ReporterNGExt.logAction(this, getParentClassName(), String.format("Set Value (selectByValue): %s", value));
        alwaysDoneAction(() -> select().selectByValue(value));
        return super.parent;
    }

    /**
     * !!! Use select(int... indexes) instead
     * Select the option at the given index
     *
     * @param index - index The option at this index will be selected
     * @return Parent Panel instance
     */
    @Deprecated
    public ParentPanel selectByIndex(int index) {
        ReporterNGExt.logAction(this, getParentClassName(), String.format("Set Value by Index: %d", index));
        alwaysDoneAction(() -> select().selectByIndex(index));
        return super.parent;
    }

    /**
     * Better do not use this one. Use select(String... names) instead
     * Select by the visible option text(contains)
     *
     * @param sItem - visible option text(contains)
     * @return Parent Panel instance
     */
    @Deprecated
    public ParentPanel selectByTextContains(String sItem) {
        ReporterNGExt.logAction(this, getParentClassName(), String.format("Set Value contains: %s", sItem));
        Select select = select();
        int firstIndex = getResultAction(() -> LinqUtils.firstIndex(
                select.getOptions(),
                option -> option.getText().contains(sItem)));
        if (firstIndex > -1) {
            select.selectByIndex(firstIndex);
            return super.parent;
        }
        throw new NoSuchElementException(String.format("Cannot find item contains this text '%s'", sItem));
    }

    /**
     * !!! Use count() instead
     * Gets count of options in DropBox
     *
     * @return count of options in DropBox
     */
    @Deprecated
    public int getOptionsCount() {
        ReporterNGExt.logAction(this, getParentClassName(), "Get count of all options");
        return select().getOptions().size();
    }

    /**
     * !!! Use getOptions() instead
     * Gets all options
     *
     * @return All options belonging to this select tag
     */
    @Deprecated
    public String[] getAllOptions() {
        ReporterNGExt.logAction(this, getParentClassName(), "Get all options");
        return getResultAction(() -> (String[])LinqUtils.select(
                select().getOptions(),
                WebElement::getText).toArray());
    }

    /**
     * Gets first selected option
     *
     * @return The first selected option in this select tag(or the currently selected option in a
     * normal select)
     */
    public String getFirstSelectedOption() {
        ReporterNGExt.logAction(this, getParentClassName(), "Get First Selected Option");
        return select().getFirstSelectedOption().getText();
    }

    /**
     * !!! Use areSelected() instead
     * Gets All selected options
     *
     * @return All selected options belonging to this select tag
     */
    @Deprecated
    public String[] getAllSelectedOptions() {
        ReporterNGExt.logAction(this, getParentClassName(), "Get All selected options");
        return getResultAction(() -> (String[])LinqUtils.select(
                    select().getAllSelectedOptions(),
                    WebElement::getText).toArray());
    }


    /**
     * !!! Use uncheck(String... names) instead
     * Undo selection by option text of Select
     *
     * (That is Deselect all options that display text matching the argument)
     *
     * @param sItem - visible option text
     * @return Parent Panel instance
     */
    @Deprecated
    public ParentPanel deSelectByText(String sItem) {
        ReporterNGExt.logAction(this, getParentClassName(), String.format("Deselect value%s", sItem));
        alwaysDoneAction(() -> select().deselectByVisibleText(sItem));
        return super.parent;
    }

    /**
     * !!! use clear() instead
     * Undo the selection for all options. This is only valid when the SELECT supports multiple selections.
     *
     * Check if the Select can be multiple selected
     * boolean isMultiple = select.isMultiple();
     *
     * @return Parent Panel instance
     */
    @Deprecated
    public ParentPanel deselectAll() {
        ReporterNGExt.logAction(this, getParentClassName(), "Deselect All values");
        alwaysDoneAction(() -> select().deselectAll());
        return super.parent;
    }

    /**
     * Check if the Select can be multiple selected
     *
     * @return Whether this select element support selecting multiple options at the same time? This
     * is done by checking the value of the "multiple" attribute.
     */
    public boolean isMultiple() {
        ReporterNGExt.logAction(this, getParentClassName(), "isMultiple");
        return select().isMultiple();
    }

    /**
     * Check if value present into Dropbox
     *
     * @param value - checked value
     * @return true if value exists
     */
    public boolean isOptionExist(String value) {
        return first(getAllOptions(),
                option -> option.equals(value)) != null;
    }

}
