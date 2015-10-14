package com.epam.hem.tests.pageObjects.sections.base;

import com.ggasoftware.jdiuitests.implementation.selenium.elements.composite.Section;
import org.openqa.selenium.support.FindBy;

import java.util.List;

/**
 * Created by Pavel_Shcherbakov1 on 2015-10-12
 */
public class TileView extends Section {

    @FindBy(css = "div[class*='caption']")
    private List<TileItem> tileItems;
}
