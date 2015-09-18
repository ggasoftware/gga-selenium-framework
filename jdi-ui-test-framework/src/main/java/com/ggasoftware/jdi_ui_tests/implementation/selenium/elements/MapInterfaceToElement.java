package com.ggasoftware.jdi_ui_tests.implementation.selenium.elements;

import com.ggasoftware.jdi_ui_tests.core.utils.map.MapArray;

import static com.ggasoftware.jdi_ui_tests.core.settings.JDISettings.asserter;
import static com.ggasoftware.jdi_ui_tests.core.utils.common.StringUtils.LineBreak;

/**
 * Created by Roman_Iovlev on 8/17/2015.
 */
public class MapInterfaceToElement {
    private static MapArray<Class, Class> map = new MapArray<>();
    public static void updateInterfacesMap(Object[][] pairs) {
        try { map.addOrReplace(pairs);
        } catch (Throwable ex) { throw asserter.exception("Error in getInterfaceTypeMap" + LineBreak + ex.getMessage()); }
    }
    public static Class getClassFromInterface(Class clazz) {
        return map.get(clazz);
    }

}
