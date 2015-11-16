package com.ggasoftware.jdiuitest.web.selenium.elements;

import com.ggasoftware.jdiuitest.core.utils.map.MapArray;

import static com.ggasoftware.jdiuitest.core.settings.JDISettings.exception;
import static com.ggasoftware.jdiuitest.core.utils.common.StringUtils.LINE_BREAK;

/**
 * Created by Roman_Iovlev on 8/17/2015.
 */
public class MapInterfaceToElement {
    private static MapArray<Class, Class> map = new MapArray<>();

    public static void updateInterfacesMap(Object[][] pairs) {
        try {
            map.addOrReplace(pairs);
        } catch (Exception ex) {
            throw exception("Error in getInterfaceTypeMap" + LINE_BREAK + ex.getMessage());
        }
    }

    public static Class getClassFromInterface(Class clazz) {
        return map.get(clazz);
    }

}
