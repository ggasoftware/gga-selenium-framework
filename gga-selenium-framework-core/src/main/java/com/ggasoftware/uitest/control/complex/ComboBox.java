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

import com.ggasoftware.uitest.control.simple.Input;
import com.ggasoftware.uitest.utils.common.LinqUtils;
import com.ggasoftware.uitest.utils.WebDriverWrapper;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

import static com.ggasoftware.uitest.utils.common.LinqUtils.firstIndex;
import static com.ggasoftware.uitest.utils.ReporterNG.logAssertTrue;
import static com.ggasoftware.uitest.utils.ReporterNG.logTechnical;
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
        return doJAction(format("Select %s item", index),
                () -> select().selectByIndex(index));
    }

    /**
     * Select by option text.
     *
     * @param value - option text
     * @return Parent Panel instance
     */
    public ParentPanel select(String value) {
        return doJAction("Select " + value,
            () -> select().selectByValue(value));
    }

    /**
     * Select by the visible option text(contains)
     *
     * @param item - visible option text(contains)
     * @return Parent Panel instance
     */
    public ParentPanel selectByTextContains(String item) {
        return doJAction(format("select by text contains: %s", item), () -> {
            Select select = select();
            int firstIndex = firstIndex(
                    select.getOptions(),
                    option -> option.getText().contains(item));
            if (firstIndex == -1)
                throw new NoSuchElementException(format("Cannot find item contains this text '%s'", item));
            select.selectByIndex(firstIndex);
            });
    }

    /**
     * Get first selected option text.
     *
     * @return First Selected option.
     */
    public String getFirstSelectedItem() {
        return doJActionResult("Get selected items",
                () -> select().getFirstSelectedOption().getText());
    }

    /**
     * Get all selected options text.
     *
     * @return All Selected options.
     */
    public List<String> getSelectedItem() {
        return doJActionResult("Get selected items", () ->
                (List<String>) LinqUtils.select(
                        select().getAllSelectedOptions(),
                        WebElement::getText));
    }

    /**
     * Get all option text.
     *
     * @return List of all options.
     */
    public List<String> getItems() {
        return doJActionResult("Get all items",
                () -> (List<String>) LinqUtils.select(
                        select().getOptions(),
                        WebElement::getText));
    }

}
