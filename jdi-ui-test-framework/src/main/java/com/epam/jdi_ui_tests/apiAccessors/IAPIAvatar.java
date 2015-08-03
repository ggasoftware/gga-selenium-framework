package com.epam.jdi_ui_tests.apiAccessors;

import java.util.List;

/**
 * Created by Roman_Iovlev on 6/10/2015.
 */
public interface IAPIAvatar<T extends IElementAvatar> {
    T getElement(String locator);
    List<T> getElements(String locator);
    void runApplication();
    void closeApplication();
}
