package com.ggasoftware.jdiuitests.core.utils.common;

import com.ggasoftware.jdiuitests.core.utils.linqInterfaces.JActionT;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyReader {
	public static String propertiesPath;
	private static Properties properties;

	private static Properties loadProperties() throws IOException {
		if (properties == null) {
			properties = new Properties();
			if (propertiesPath.charAt(0) != '/')
				propertiesPath = "/" + propertiesPath;
			InputStream inputStream = PropertyReader.class.getResourceAsStream(propertiesPath);
			if (inputStream != null)
				properties.load(inputStream);
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
			action.invoke(prop+"");
	}

}
