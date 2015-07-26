package com.epam.ui_test_framework.elements.complex.table;

import com.epam.ui_test_framework.utils.linqInterfaces.*;

import static com.epam.ui_test_framework.settings.FrameworkSettings.asserter;

/**
 * Created by Roman_Iovlev on 7/17/2015.
 */
public abstract class RowColumn {
    private int num;
    private String name;
    public boolean haveName() { return name != null; }
    public int getNum() { return num; }
    public String getName() { return name; }

    public <T> T get(JFuncTT<RowColumn, T> action) {
        return asserter.silent(() -> action.invoke(this));
    }
    public <T> T get(JFuncTT<String, T> nameAction, JFuncTT<Integer, T> numAction) {
        return asserter.silent(() -> haveName()
                        ? nameAction.invoke(name)
                        : numAction.invoke(num)
        );
    }

    RowColumn(int num) { this.num = num; }
    RowColumn(String name) { this.name = name; }

}
