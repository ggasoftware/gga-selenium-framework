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
import com.ggasoftware.uitest.utils.WebDriverWrapper;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
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

    private Select select() { return new Select(getWebElement()); }

    /**
     * Select by Index.
     *
     * @param index - option index
     * @return Parent Panel instance
     */
    public ParentPanel select(int index) {
        logAction(this, getParentClassName(), format("Select %s item", index));
        alwaysDoneAction(() -> select().selectByIndex(index));
        return this.parent;
    }

    /**
     * Select by option text.
     *
     * @param value - option text
     * @return Parent Panel instance
     */
    public ParentPanel select(String value) {
        logAction(this, getParentClassName(), "Select " + value);
        alwaysDoneAction(() -> select().selectByValue(value));
        return this.parent;
    }

    /**
     * Select by the visible option text(contains)
     *
     * @param item - visible option text(contains)
     * @return Parent Panel instance
     */
    public ParentPanel selectByTextContains(String item) {
        logAction(this, getParentClassName(), format("select by text contains: %s", item));
        Select select = select();
        int firstIndex = getResultAction(() -> firstIndex(
                select.getOptions(),
                option -> option.getText().contains(item)));
        if (firstIndex > -1) {
            select.selectByIndex(firstIndex);
            return super.parent; }
        throw new NoSuchElementException(format("Cannot find item contains this text '%s'", item));
    }

    /**
     * Get first selected option text.
     *
     * @return First Selected option.
     */
    public String getFirstSelectedItem() {
        logAction(this, getParentClassName(), "Get selected items");
        return getResultAction(() -> select().getFirstSelectedOption().getText());
    }

    /**
     * Get all selected options text.
     *
     * @return All Selected options.
     */
    public List<String> getSelectedItem() {
        logAction(this, getParentClassName(), "Get selected items");
        return getResultAction(() -> (List<String>)LinqUtils.select(
                select().getAllSelectedOptions(),
                WebElement::getText));
    }

    /**
     * Get all option text.
     *
     * @return List of all options.
     */
    public List<String> getItems() {
        logAction(this, getParentClassName(), "Get all items");
        return getResultAction(() -> (List<String>)LinqUtils.select(
                select().getOptions(),
                WebElement::getText));
    }

    /**
     * Wait until option is selected by value.
     *
     * @param value - option text
     * @return Parent Panel instance
     */
    public ParentPanel waitForItemAndSelect(final String value) {
        boolean isSelected;
        logAction(this, getParentClassName(), format("waitForItemAndSelect[%s]: %s", value, locator));
        long start = currentTimeMillis() / 1000;
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
            ReporterNGExt.logTechnical(format("waitForItemAndSelect: [ %s ] during: [ %d ] sec ", locator, currentTimeMillis() / 1000 - start));
            isSelected = false;
        }
        ReporterNGExt.logAssertTrue(ReporterNGExt.BUSINESS_LEVEL, isSelected, format("waitForItemAndSelect: select item %s of %s", value, name));
        return parent;
    }

}
