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
package com.ggasoftware.jdiuitest.core.interfaces.settings;

import com.ggasoftware.jdiuitest.core.interfaces.base.IElement;
import com.ggasoftware.jdiuitest.core.settings.HighlightSettings;

/**
 * Created by Roman_Iovlev on 9/3/2015.
 */
public interface IDriver {
    void registerDriver(String driverName);
    void setRunType(String runType);
    <T> T getDriver();
    boolean hasDrivers();
    String currentDriverName();
    <T> T getDriver(String name);
    void highlight(IElement element);
    void highlight(IElement element, HighlightSettings highlightSettings);
    String getDriverPath();
    void setDriverPath(String driverPath);
}
