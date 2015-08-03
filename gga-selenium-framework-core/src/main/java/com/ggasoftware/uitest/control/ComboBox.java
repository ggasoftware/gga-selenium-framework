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
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

/**
 * ComboBox control implementation
 *
 * @author Alexeenko Yan
 */
public class ComboBox<ParentPanel> extends Input<ParentPanel> {

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

    /**
     * Select by Index.
     *
     * @param index - option index
     * @return Parent Panel instance
     */
    public ParentPanel select(int index) {
        ReporterNGExt.logAction(this, getParentClassName(), String.format("Select %s item", index));
        Select select = new Select(getWebElement());
        select.selectByIndex(index);
        return this.parent;
    }

    /**
     * Select by option text.
     *
     * @param value - option text
     * @return Parent Panel instance
     */
    public ParentPanel select(String value) {
        ReporterNGExt.logAction(this, getParentClassName(), "Select " + value);
        Select select = new Select(getWebElement());
        select.selectByValue(value);
        return this.parent;
    }

    /**
     * Select by the visible option text(contains)
     *
     * @param item - visible option text(contains)
     * @return Parent Panel instance
     */
    public ParentPanel selectByTextContains(String item) {
        ReporterNGExt.logAction(this, getParentClassName(), String.format("select by text contains: %s", item));
        org.openqa.selenium.support.ui.Select select = new org.openqa.selenium.support.ui.Select(getWebElement());

        List<WebElement> options = select.getOptions();
        for (int i = 0; i < options.size(); i++) {
            if (options.get(i).getText().contains(item)) {
                select.selectByIndex(i);
                return super.parent;
            }
        }
        throw new NoSuchElementException(String.format("Cannot find item contains this text '%s'", item));
    }

    /**
     * Get first selected option text.
     *
     * @return First Selected option.
     */
    public String getFirstSelectedItem() {
        ReporterNGExt.logAction(this, getParentClassName(), "Get selected items");
        org.openqa.selenium.support.ui.Select select = new org.openqa.selenium.support.ui.Select(getWebElement());
        return select.getFirstSelectedOption().getText();
    }

    /**
     * Get all selected options text.
     *
     * @return All Selected options.
     */
    public List<String> getSelectedItem() {
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
     * Get all option text.
     *
     * @return List of all options.
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
     * Wait until option is selected by value.
     *
     * @param value - option text
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
                                Select select = new Select(getWebElement());
                                select.selectByValue(value);
                                return true;
                            } catch (Exception e) {
                                return false;
                            }
                        }
                    }
            );
        }catch (TimeoutException e){
            ReporterNGExt.logTechnical(String.format("waitForItemAndSelect: [ %s ] during: [ %d ] sec ", locator, System.currentTimeMillis() / 1000 - start));
            isSelected = false;
        }
        ReporterNGExt.logAssertTrue(ReporterNGExt.BUSINESS_LEVEL, isSelected, String.format("waitForItemAndSelect: select item %s of %s", value, name));
        return parent;
    }

}
