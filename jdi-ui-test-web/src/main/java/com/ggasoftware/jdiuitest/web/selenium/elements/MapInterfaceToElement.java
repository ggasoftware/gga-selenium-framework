/*
 * Copyright 2004-2016 EPAM Systems
 *
 * This file is part of JDI project.
 *
 * JDI is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * JDI is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; 
 * without even the implied warranty ofMERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. 
 * See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with JDI. If not, see <http://www.gnu.org/licenses/>.
 */
 
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