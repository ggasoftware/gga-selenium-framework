package com.epam.hem.tests.pageObjects.pages;

import com.epam.hem.tests.pageObjects.sections.common.CommonPanel;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.composite.Page;
import org.openqa.selenium.support.FindBy;

/**
 * Created by Pavel_Shcherbakov1 on 2015-10-08
 */
public class HomePage extends Page {

    @FindBy(css = "div[ng-if='showNavigationTabs()']")
    public CommonPanel commonPanel;
}
