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
package com.ggasoftware.jdi_ui_tests.elements.base;

import com.ggasoftware.jdi_ui_tests.elements.BaseElement;
import com.ggasoftware.jdi_ui_tests.elements.interfaces.base.ISetValue;
import com.ggasoftware.jdi_ui_tests.utils.linqInterfaces.JActionT;
import com.ggasoftware.jdi_ui_tests.utils.linqInterfaces.JFuncT;

import static com.ggasoftware.jdi_ui_tests.settings.JDISettings.asserter;
import static java.lang.String.format;

/**
 * Checkbox control implementation
 *
 * @author Alexeenko Yan
 * @author Belousov Andrey
 */
public class SetValue extends HaveValue implements ISetValue {
    private JActionT<String> setValueAction;
    public SetValue(JActionT<String> setValueAction, JFuncT<String> getValueFunc) {
        super(getValueFunc);
        this.setValueAction = setValueAction;
    }
    public SetValue(JActionT<String> setValueAction, HaveValue haveValue) {
        super(haveValue::getValue);
        this.setValueAction = setValueAction;
    }
    public SetValue(JFuncT<String> getValueFunc, SetValue setValue) {
        super(getValueFunc);
        this.setValueAction = setValue::setValue;
    }
    public SetValue(JActionT<String> setValueAction, BaseElement element) {
        super(element);
        this.setValueAction = setValueAction;
    }
    public SetValue(JFuncT<String> getValueFunc, BaseElement element) {
        super(getValueFunc);
        this.setValueAction = value ->
            asserter.exception(format("Set value action not implemented for '%s'", element));
    }
    public final void setValue(String value) { doJAction("Set value", () -> setValueRule(value, setValueAction::invoke)); }
}
