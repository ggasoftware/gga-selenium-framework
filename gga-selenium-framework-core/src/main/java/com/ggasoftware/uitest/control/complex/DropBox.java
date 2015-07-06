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
package com.ggasoftware.uitest.control.complex;

import com.ggasoftware.uitest.control.base.Element;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import static com.ggasoftware.uitest.utils.common.LinqUtils.*;
import static java.lang.String.format;

/**
 * DropBox control implementation
 *
 * @author Alexeenko Yan
 * @author Belousov Andrey
 */
public class DropBox<ParentPanel> extends Element<ParentPanel> {

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

    private Select selector() { return new Select(getWebElement()); }

    /**
     * Select by the visible option text
     *
     * @param sItem - visible option text
     * @return Parent Panel instance
     */
    public ParentPanel selectByText(String sItem) {
        return doJAction(format("Set Value (selectByVisibleText): %s", sItem),
            () -> selector().selectByVisibleText(sItem));
    }

    /**
     * Select all options that have a value matching the argument. That is, when given "foo" this
     * would select an option like:
     * <p/>
     * &lt;option value="foo"&gt;Bar&lt;/option&gt;
     *
     * @param value The value to match against
     * @return Parent Panel instance
     */
    public ParentPanel selectByValue(String value) {
        return doJAction(format("Set Value (selectByValue): %s", value),
            () -> selector().selectByValue(value));
    }

    /**
     * Select the option at the given index
     *
     * @param index - index The option at this index will be selected
     * @return Parent Panel instance
     */
    public ParentPanel selectByIndex(int index) {
        return doJAction(format("Set Value by Index: %d", index),
            () -> selector().selectByIndex(index));
    }

    /**
     * Select by the visible option text(contains)
     *
     * @param sItem - visible option text(contains)
     * @return Parent Panel instance
     */
    public ParentPanel selectByTextContains(String sItem) {
        return doJAction(format("Set Value contains: %s", sItem), () -> {
            Select select = selector();
            int firstIndex = firstIndex(select.getOptions(),
                    option -> option.getText().contains(sItem));
            if (firstIndex == -1)
                throw new NoSuchElementException(format("Cannot find item contains this text '%s'", sItem));
            select.selectByIndex(firstIndex);
        });
    }

    /**
     * Gets count of options in DropBox
     *
     * @return count of options in DropBox
     */
    public int getOptionsCount() {
        return doJActionResult("Get count of all options",
                () -> selector().getOptions().size());
    }

    /**
     * Gets all options
     *
     * @return All options belonging to this select tag
     */
    public String[] getAllOptions() {
        return doJActionResult("Get all options",
                () -> toStringArray(select(
                        selector().getOptions(),
                        WebElement::getText)));
    }

    /**
     * Gets first selected option
     *
     * @return The first selected option in this select tag(or the currently selected option in a
     * normal select)
     */
    public String getFirstSelectedOption() {
        return doJActionResult("Get First Selected Option",
            () -> selector().getFirstSelectedOption().getText());
    }

    /**
     * Gets All selected options
     *
     * @return All selected options belonging to this select tag
     */
    public String[] getAllSelectedOptions() {
        return doJActionResult("Get All selected options",
            () -> toStringArray(select(
                selector().getAllSelectedOptions(),
                WebElement::getText)));
    }


    /**
     * Undo selection by option text of Select
     * <p/>
     * (That is Deselect all options that display text matching the argument)
     *
     * @param sItem - visible option text
     * @return Parent Panel instance
     */
    public ParentPanel deSelectByText(String sItem) {
        return doJAction(format("Deselect value%s", sItem),
            () -> selector().deselectByVisibleText(sItem));
    }

    /**
     * Undo the selection for all options. This is only valid when the SELECT supports multiple selections.
     * <p/>
     * Check if the Select can be multiple selected
     * boolean isMultiple = select.isMultiple();
     *
     * @return Parent Panel instance
     */
    public ParentPanel deselectAll() {
        return doJAction("Deselect All values",
            () -> selector().deselectAll());
    }

    /**
     * Check if the Select can be multiple selected
     *
     * @return Whether this select element support selecting multiple options at the same time? This
     * is done by checking the value of the "multiple" attribute.
     */
    public boolean isMultiple() {
        return doJActionResult("isMultiple",
                () -> selector().isMultiple());
    }

    /**
     * Check if value present into Dropbox
     *
     * @param value - checked value
     * @return true if value exists
     */
    public boolean isOptionExist(String value) {
        return doJActionResult("Is option Exist",
            () -> first(getAllOptions(),
                option -> option.equals(value)) != null);
    }

}
