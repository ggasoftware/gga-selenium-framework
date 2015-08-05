package com.ggasoftware.jdi_ui_tests.elements.complex.table;

import com.ggasoftware.jdi_ui_tests.settings.JDISettings;
import com.ggasoftware.jdi_ui_tests.utils.linqInterfaces.JFuncTT;

/**
 * Created by Roman_Iovlev on 7/17/2015.
 */
abstract class RowColumn {
    private int num;
    private String name;
    public boolean haveName() { return name != null; }
    public int getNum() { return num; }
    public String getName() { return name; }

    public <T> T get(JFuncTT<RowColumn, T> action) {
        return JDISettings.asserter.silent(() -> action.invoke(this));
    }
    public <T> T get(JFuncTT<String, T> nameAction, JFuncTT<Integer, T> numAction) {
        return JDISettings.asserter.silent(() -> haveName()
                        ? nameAction.invoke(name)
                        : numAction.invoke(num)
        );
    }

    RowColumn(int num) { this.num = num; }
    RowColumn(String name) { this.name = name; }

}
