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
package com.ggasoftware.jdiuitest.core.utils.common;

import com.ggasoftware.jdiuitest.core.utils.linqinterfaces.JActionT;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyReader {
    private static String propertiesPath = "test.properties";
    private static volatile Properties properties;
    private static InputStream inputStream;

    PropertyReader() {
    }

    private static Properties loadProperties() throws IOException {
        if (properties == null) {
            properties = new Properties();
            if (propertiesPath.charAt(0) != '/')
                propertiesPath = "/" + propertiesPath;
            try {
                inputStream = PropertyReader.class.getResourceAsStream(propertiesPath);
                if (inputStream != null)
                    properties.load(inputStream);
            } catch (Exception ex) {
                if (inputStream != null) inputStream.close();
            }
        }
        return properties;
    }

    public static Properties getProperties(String path) throws IOException {
        propertiesPath = path;
        return loadProperties();
    }

    public static Properties getProperties() throws IOException {
        return getProperties(propertiesPath);
    }

    public static String getProperty(String propertyName) throws IOException {
        return loadProperties().getProperty(propertyName);
    }

    public static void fillAction(JActionT<String> action, String name) {
        Object prop = properties.get(name);
        if (prop != null && !prop.equals(""))
            action.invoke(prop.toString());
    }

}