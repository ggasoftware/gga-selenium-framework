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

import com.ggasoftware.uitest.control.simple.Element;
import com.ggasoftware.uitest.utils.common.LinqUtils;
import org.openqa.selenium.WebElement;

import java.util.List;

import static java.lang.String.format;
import static jdk.nashorn.internal.objects.Global.print;

/**
 * Select control implementation
 *
 * @author Alexeenko Yan
 * @author Belousov Andrey
 */
public class Select<ParentPanel> extends Element<ParentPanel> {

    //constructors

    /**
     * Initializes element with given locator. Locates own properties of the element by class name, takes given locator and tries
     * to initialize.
     *
     * @param name        - Button Name
     * @param locator     - start it with locator type "id=", "css=", "xpath=" and etc. Locator without type is assigned to xpath
     * @param parentPanel - Panel which contains current button
     */
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
        return doJAction("Deselect all items",() -> select().deselectAll());
    }

    /**
     * Select item by index.
     *
     * @param index of item
     * @return Parent instance
     */
    public ParentPanel select(int index) {
        return doJAction(format("Select %d item", index), () -> {
            org.openqa.selenium.support.ui.Select select = select();
            select.deselectAll();
            select.selectByIndex(index);
        });
    }

    /**
     * Select item by value.
     *
     * @param value of item
     * @return Parent instance
     */
    public ParentPanel select(String value) {
        return doJAction(format("Select %s", value), () -> {
            org.openqa.selenium.support.ui.Select select = select();
            select.deselectAll();
            select.selectByValue(value);
        });
    }

    /**
     * Select items by value.
     *
     * @param values of items
     * @return Parent instance
     */
    public ParentPanel select(String[] values) {
        org.openqa.selenium.support.ui.Select select = select();
        return doJAction("Select^ " + print(values), () -> {
            select.deselectAll();
            for (String value : values) {
                logAction(format("Select %s", value));
                select.selectByValue(value);
            }
        });
    }

    /**
     * Select items by indexes.
     *
     * @param ids - indexes of items
     * @return Parent instance
     */
    public ParentPanel select(int[] ids) {
        org.openqa.selenium.support.ui.Select select = select();
        return doJAction("Select^ " + print(ids), () -> {
            select.deselectAll();
            for (int id : ids) {
                logAction(format("Select %d item", id));
                select.selectByIndex(id);
            }
        });
    }

    /**
     * Get list of selected items.
     *
     * @return Parent instance
     */
    public List<String> getSelectedItems() {
        return doJActionResult("Get selected items",
                () -> (List<String>) LinqUtils.select(
                select().getAllSelectedOptions(),
                WebElement::getText));
    }

    /**
     * Get list of items.
     *
     * @return Parent instance
     */
    public List<String> getItems() {
        return doJActionResult("Get all items",
                () -> (List<String>) LinqUtils.select(
                select().getOptions(),
                WebElement::getText));
    }
}
