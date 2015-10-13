package com.ggasoftware.jdiuitests.core.apiAccessors;

import java.util.List;

/**
 * Created by Roman_Iovlev on 6/10/2015.
 */
public interface JDriver<T extends JElement> {
    T getElement(String locator);
    List<T> getElements(String locator);
    void runApplication();
    void closeApplication();
}
