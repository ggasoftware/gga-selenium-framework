package com.ggasoftware.jdiuitests.implementation.selenium.elements;

import com.ggasoftware.jdiuitests.core.utils.map.MapArray;

import static com.ggasoftware.jdiuitests.core.settings.JDISettings.exception;
import static com.ggasoftware.jdiuitests.core.utils.common.StringUtils.LineBreak;

/**
 * Created by Roman_Iovlev on 8/17/2015.
 */
public class MapInterfaceToElement {
    private static MapArray<Class, Class> map = new MapArray<>();

    public static void updateInterfacesMap(Object[][] pairs) {
        try {
            map.addOrReplace(pairs);
        } catch (Exception ex) {
            throw exception("Error in getInterfaceTypeMap" + LineBreak + ex.getMessage());
        }
    }

    public static Class getClassFromInterface(Class clazz) {
        return map.get(clazz);
    }

}
