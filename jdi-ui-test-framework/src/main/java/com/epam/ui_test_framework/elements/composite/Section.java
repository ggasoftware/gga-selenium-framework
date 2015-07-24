package com.epam.ui_test_framework.elements.composite;

import com.epam.ui_test_framework.elements.base.Element;
import com.epam.ui_test_framework.elements.interfaces.base.IComposite;
import org.openqa.selenium.By;

/**
 * Created by Roman_Iovlev on 7/10/2015.
 */
public class Section extends Element implements IComposite {
    public Section() { super(); }
    public Section(By byLocator) { super(byLocator); }
}
