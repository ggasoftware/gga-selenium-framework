/**
 * *************************************************************************
 * Copyright (C) 2014 GGA Software Services LLC
 * <p>
 * This file may be distributed and/or modified under the terms of the
 * GNU General Public License version 3 as published by the Free Software
 * Foundation.
 * <p>
 * This file is provided AS IS with NO WARRANTY OF ANY KIND, INCLUDING THE
 * WARRANTY OF DESIGN, MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see <http://www.gnu.org/licenses>.
 * *************************************************************************
 */
package com.ggasoftware.uitest.utils;

import java.util.Enumeration;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.ResourceBundle;

public final class PropertyReader {

    private PropertyReader() {
    }

    /**
     * Get properties from file
     * @param properties - properties object
     * @param className - file with properties className.properties
     */
    public static void getProperties(Properties properties, String className) {
        try {
            ResourceBundle source = ResourceBundle.getBundle(className);
            if (source != null) {
                Enumeration<String> keys = source.getKeys();
                while (keys.hasMoreElements()) {
                    String key = keys.nextElement();
                    properties.put(key, source.getString(key));
                }
            }
        } catch (MissingResourceException e) {
            //in case no properties for class found, ignore
            //it's not exactly that class should have property file
        }
    }
}