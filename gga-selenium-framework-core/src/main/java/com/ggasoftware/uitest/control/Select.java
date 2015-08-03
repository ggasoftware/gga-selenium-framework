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
import com.ggasoftware.uitest.utils.WebDriverWrapper;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

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

    /**
     * Deselect all items.
     *
     * @return Parent instance
     */
    public ParentPanel deselect() {
        ReporterNGExt.logAction(this, getParentClassName(), "Deselect all items");
        new org.openqa.selenium.support.ui.Select(getWebElement()).deselectAll();
        return this.parent;
    }

    /**
     * Select item by index.
     *
     * @param index of item
     * @return Parent instance
     */
    public ParentPanel select(int index) {
        ReporterNGExt.logAction(this, getParentClassName(), String.format("Select %d item", index));
        org.openqa.selenium.support.ui.Select select = new org.openqa.selenium.support.ui.Select(getWebElement());
        select.deselectAll();
        select.selectByIndex(index);
        return this.parent;
    }

    /**
     * Select item by value.
     *
     * @param value of item
     * @return Parent instance
     */
    public ParentPanel select(String value) {
        ReporterNGExt.logAction(this, getParentClassName(), String.format("Select %s", value));
        org.openqa.selenium.support.ui.Select select = new org.openqa.selenium.support.ui.Select(getWebElement());
        select.deselectAll();
        select.selectByValue(value);
        return this.parent;
    }

    /**
     * Select items by value.
     *
     * @param values of items
     * @return Parent instance
     */
    public ParentPanel select(String[] values) {
        org.openqa.selenium.support.ui.Select select = new org.openqa.selenium.support.ui.Select(getWebElement());
        select.deselectAll();
        for (String value : values) {
            ReporterNGExt.logAction(this, getParentClassName(), String.format("Select %s", value));
            select.selectByValue(value);
        }
        return this.parent;
    }

    /**
     * Select items by indexes.
     *
     * @param ids - indexes of items
     * @return Parent instance
     */
    public ParentPanel select(int[] ids) {
        org.openqa.selenium.support.ui.Select select = new org.openqa.selenium.support.ui.Select(getWebElement());
        select.deselectAll();
        for (int id : ids) {
            ReporterNGExt.logAction(this, getParentClassName(), String.format("Select %d item", id));
            select.selectByIndex(id);
        }
        return this.parent;
    }

    /**
     * Get list of selected items.
     *
     * @return Parent instance
     */
    public List<String> getSelectedItems() {
        ReporterNGExt.logAction(this, getParentClassName(), "Get selected items");
        org.openqa.selenium.support.ui.Select select = new org.openqa.selenium.support.ui.Select(getWebElement());
        List<WebElement> elements = select.getAllSelectedOptions();
        List<String> items = new ArrayList<>();
        for (WebElement element : elements) {
            items.add(element.getText());
        }
        return items;
    }

    /**
     * Get list of items.
     *
     * @return Parent instance
     */
    public List<String> getItems() {
        ReporterNGExt.logAction(this, getParentClassName(), "Get all items");
        org.openqa.selenium.support.ui.Select select = new org.openqa.selenium.support.ui.Select(getWebElement());
        List<WebElement> elements = select.getOptions();
        List<String> items = new ArrayList<>();
        for (WebElement element : elements) {
            items.add(element.getText());
        }
        return items;
    }

    /**
     * Wait until item is selected by value.
     *
     * @param value - item text
     * @return Parent Panel instance
     */
    public ParentPanel waitForItemAndSelect(final String value) {
        boolean isSelected;
        ReporterNGExt.logAction(this, getParentClassName(), String.format("waitForItemAndSelect[%s]: %s", value, locator));
        long start = System.currentTimeMillis() / 1000;
        WebDriverWait wait = (WebDriverWait) new WebDriverWait(WebDriverWrapper.getDriver(), WebDriverWrapper.TIMEOUT)
                .ignoring(StaleElementReferenceException.class);
        try {
            isSelected = wait.until(
                    new ExpectedCondition<Boolean>() {
                        @Override
                        public Boolean apply(WebDriver driver) {
                            try {
                                org.openqa.selenium.support.ui.Select select = new org.openqa.selenium.support.ui.Select(getWebElement());
                                select.selectByValue(value);
                                return true;
                            } catch (Exception e) {
                                return false;
                            }
                        }
                    }
            );
        }catch (TimeoutException e) {
            ReporterNGExt.logTechnical(String.format("waitForItemAndSelect: [ %s ] during: [ %d ] sec ", locator, System.currentTimeMillis() / 1000 - start));
            isSelected = false;
        }
        ReporterNGExt.logAssertTrue(ReporterNGExt.BUSINESS_LEVEL, isSelected, String.format("waitForItemAndSelect: select item %s of %s", value, name));
        return parent;
    }

}
