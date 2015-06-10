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
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import static java.lang.String.format;

/**
 * Text Field control implementation
 *
 * @author Alexeenko Yan
 * @author Shubin Konstantin
 * @author Zharov Alexandr
 */
public class Input<ParentPanel> extends Element<ParentPanel> {

    //constructors

    /**
     * Initializes element with given locator. Locates own properties of the element by class name, takes given locator and tries
     * to initialize.
     *
     * @param name        - Input name
     * @param locator     - start it with locator type "id=", "css=", "xpath=" and etc. Locator without type is assigned to xpath
     * @param parentPanel - Parent instance
     */
    public Input(String name, String locator, ParentPanel parentPanel) {
        super(name, locator, parentPanel);
    }

    /**
     * Gets the value of an input field
     *
     * @return the value of an input field
     */
    public String getValue() {
        return super.getAttribute("value");
    }


    /**
     * Type text to the Input field
     *
     * @param text - text for Input field
     * @return Parent instance
     */
    public ParentPanel setText(String text) {
        return doJAction(format("setText - %s", text), () -> {
            WebElement webEl = getWebElement();
            webEl.click();
            webEl.clear();
            webEl.click();
            super.sendKeys(text);
        });
    }

    /**
     * Type text to the Input field with secure log.
     *
     * @param text - text for Input field
     * @return Parent instance
     */
    public ParentPanel setTextSecure(String text) {
        return doJAction(format("setTextSecure - %s", text.replaceAll("[^']", "*")),
            () -> {
                WebElement webEl = getWebElement();
                webEl.click();
                webEl.clear();
                webEl.click();
                super.sendKeysSecure(text);
            });
    }

    /**
     * Clear the value from the Input field
     *
     * @return Parent instance
     */
    public ParentPanel clear() {
        return doJAction("Clear", () -> getWebElement().clear());
    }

    /**
     * Use this method to simulate typing into an element, which may set its value.
     *
     * @param keysToSend - CharSequence to send
     * @return Parent instance
     */
    public ParentPanel sendKeys(CharSequence... keysToSend) {
        return super.sendKeys(keysToSend);
    }

    /**
     * Use this method to simulate enter key press into an element.
     *
     * @return Parent instance
     */
    public ParentPanel pressEnter() {
        return super.sendKeys(Keys.ENTER);
    }

    /**
     * Use this method to simulate send keys to the element.
     *
     * @param sendKeys - e.g. sendKeys(Keys.ARROW_DOWN)
     * @return Parent instance
     */
    public ParentPanel sendKeys(Keys sendKeys) {
        return super.sendKeys(sendKeys);
    }

}
