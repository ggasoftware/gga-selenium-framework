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

import com.ggasoftware.uitest.utils.ReporterNGExt;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

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


    /**
     * Select by the visible option text
     *
     * @param sItem - visible option text
     * @return Parent Panel instance
     */
    public ParentPanel selectByText(String sItem) {
        ReporterNGExt.logAction(this, getParentClassName(), String.format("Set Value (selectByVisibleText): %s", sItem));
        Select select = new Select(getWebElement());
        select.selectByVisibleText(sItem);
        return super.parent;
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
        ReporterNGExt.logAction(this, getParentClassName(), String.format("Set Value (selectByValue): %s", value));
        Select select = new Select(getWebElement());
        select.selectByValue(value);
        return super.parent;
    }

    /**
     * Select the option at the given index
     *
     * @param index - index The option at this index will be selected
     * @return Parent Panel instance
     */
    public ParentPanel selectByIndex(int index) {
        ReporterNGExt.logAction(this, getParentClassName(), String.format("Set Value by Index: %d", index));
        Select select = new Select(getWebElement());
        select.selectByIndex(index);
        return super.parent;
    }

    /**
     * Select by the visible option text(contains)
     *
     * @param sItem - visible option text(contains)
     * @return Parent Panel instance
     */
    public ParentPanel selectByTextContains(String sItem) {
        ReporterNGExt.logAction(this, getParentClassName(), String.format("Set Value contains: %s", sItem));
        Select select = new Select(getWebElement());

        List<WebElement> options = select.getOptions();
        for (int i = 0; i < options.size(); i++) {
            if (options.get(i).getText().contains(sItem)) {
                select.selectByIndex(i);
                return super.parent;
            }
        }
        throw new NoSuchElementException(String.format("Cannot find item contains this text '%s'", sItem));
    }

    /**
     * Gets count of options in DropBox
     *
     * @return count of options in DropBox
     */
    public int getOptionsCount() {
        ReporterNGExt.logAction(this, getParentClassName(), "Get count of all options");
        Select select = new Select(getWebElement());
        return (select.getOptions().size());
    }

    /**
     * Gets all options
     *
     * @return All options belonging to this select tag
     */
    public String[] getAllOptions() {
        ReporterNGExt.logAction(this, getParentClassName(), "Get all options");
        Select select = new Select(getWebElement());

        List<WebElement> allOptionsWE;
        allOptionsWE = select.getOptions();

        String[] allOptions = new String[allOptionsWE.size()];
        for (int i = 0; i < allOptionsWE.size(); i++) {
            allOptions[i] = allOptionsWE.get(i).getText();
        }
        return allOptions;
    }

    /**
     * Gets first selected option
     *
     * @return The first selected option in this select tag(or the currently selected option in a
     * normal select)
     */
    public String getFirstSelectedOption() {
        ReporterNGExt.logAction(this, getParentClassName(), "Get First Selected Option");
        Select select = new Select(getWebElement());
        return select.getFirstSelectedOption().getText();
    }

    /**
     * Gets All selected options
     *
     * @return All selected options belonging to this select tag
     */
    public String[] getAllSelectedOptions() {
        ReporterNGExt.logAction(this, getParentClassName(), "Get All selected options");
        Select select = new Select(getWebElement());

        List<WebElement> allSelectedOptionsWE;
        allSelectedOptionsWE = select.getAllSelectedOptions();

        String[] allSelectedOptions = new String[allSelectedOptionsWE.size()];
        for (int i = 0; i < allSelectedOptionsWE.size(); i++) {
            allSelectedOptions[i] = allSelectedOptionsWE.get(i).getText();
        }
        return allSelectedOptions;
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
        ReporterNGExt.logAction(this, getParentClassName(), String.format("Deselect value%s", sItem));
        Select select = new Select(getWebElement());
        select.deselectByVisibleText(sItem);
        return super.parent;
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
        ReporterNGExt.logAction(this, getParentClassName(), "Deselect All values");
        Select select = new Select(getWebElement());
        select.deselectAll();
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
        Select select = new Select(getWebElement());
        return select.isMultiple();
    }

    /**
     * Check if value present into Dropbox
     *
     * @param value - checked value
     * @return true if value exists
     */
    public boolean isOptionExist(String value) {
        String[] allOptions = getAllOptions();
        for (String option : allOptions) {
            if (option.equals(value)) {
                return true;
            }
        }
        return false;
    }

}
