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

import com.ggasoftware.uitest.utils.LinqUtils;
import com.ggasoftware.uitest.utils.ReporterNGExt;
import com.ggasoftware.uitest.utils.Timer;
import com.ggasoftware.uitest.utils.WebDriverWrapper;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.ArrayList;
import java.util.List;

import static com.ggasoftware.uitest.utils.LinqUtils.foreach;
import static com.ggasoftware.uitest.utils.PrintUtils.print;
import static com.ggasoftware.uitest.utils.ReporterNG.logAssertTrue;
import static com.ggasoftware.uitest.utils.ReporterNG.logTechnical;
import static com.ggasoftware.uitest.utils.ReporterNGExt.logAction;
import static com.ggasoftware.uitest.utils.Timer.alwaysDoneAction;
import static java.lang.String.format;

/**
 * Select control implementation
 *
 * @author Alexeenko Yan
 * @author Belousov Andrey
 */
public class Select<ParentPanel> extends Element<ParentPanel> {
    public Select() { }
    public Select(By valueLocator) { super(valueLocator); }
    //constructors

    /**
     * !!! Use Selector instead
     * Initializes element with given locator. Locates own properties of the element by class name, takes given locator and tries
     * to initialize.
     *
     * @param name        - Button Name
     * @param locator     - start it with locator type "id=", "css=", "xpath=" and etc. Locator without type is assigned to xpath
     * @param parentPanel - Panel which contains current button
     */
    @Deprecated
    public Select(String name, String locator, ParentPanel parentPanel) {
        super(name, locator, parentPanel);
    }

    private org.openqa.selenium.support.ui.Select select() {
        return new org.openqa.selenium.support.ui.Select(getWebElement()); }
    /**
     * Deselect all items.
     *
     * @return Parent instance
     */
    public ParentPanel deselect() {
        doJAction("Deselect all items", () -> select().deselectAll());
        return this.parent;
    }

    /**
     * Select item by index.
     *
     * @param index of item
     * @return Parent instance
     */
    public ParentPanel select(int index) {
        doJAction(format("Select %d item", index), () -> {
            select().deselectAll();
            select().selectByIndex(index);
        });
        return this.parent;
    }

    /**
     * Select item by value.
     *
     * @param value of item
     * @return Parent instance
     */
    public ParentPanel select(String value) {
        doJAction(format("Select %s", value), () -> {
            select().deselectAll();
            select().selectByValue(value);
        });
        return this.parent;
    }

    /**
     * Select items by value.
     *
     * @param values of items
     * @return Parent instance
     */
    public ParentPanel select(String[] values) {
        doJAction("Select values " + print(values), () -> {
            select().deselectAll();
            for (String value : values)
                select().selectByValue(value);
        });
        return this.parent;
    }

    /**
     * Select items by indexes.
     *
     * @param ids - indexes of items
     * @return Parent instance
     */
    public ParentPanel select(int[] ids) {
        doJAction("Select values " + print(ids), () -> {
            select().deselectAll();
            for (int id : ids)
                select().selectByIndex(id);
        });
        return this.parent;
    }

    /**
     * Get list of selected items.
     *
     * @return Parent instance
     */
    public List<String> getSelectedItems() {
        return doJActionResult("Get selected items", () ->
            (List<String>) LinqUtils.select(
                select().getAllSelectedOptions(),
                WebElement::getText));
    }

    /**
     * Get list of items.
     *
     * @return Parent instance
     */
    public List<String> getItems() {
        return doJActionResult("Get all items", () -> (List<String>) LinqUtils.select(
                select().getOptions(),
                WebElement::getText));
    }

    /**
     * !!! Use just select()
     * Wait until item is selected by value.
     *
     * @param value - item text
     * @return Parent Panel instance
     */
    @Deprecated
    public ParentPanel waitForItemAndSelect(final String value) {
        select(value);
        return parent;
    }

}
