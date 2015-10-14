package com.epam.hem.tests.pageObjects.sections.base;

import com.ggasoftware.jdiuitests.implementation.selenium.elements.common.Button;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.common.TextField;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.composite.Section;
import org.openqa.selenium.support.FindBy;

/**
 * Created by Pavel_Shcherbakov1 on 2015-10-12
 */
public class HeadPanel extends Section {

    @FindBy(css = "*[class='fa fa-plus']")
    private Button addButton;
    @FindBy(css = "input[class*='search-input']")
    private TextField searchField;
    @FindBy(css = "button[ng-class*='th']")
    private Button tileViewButton;
    @FindBy(css = "button[ng-class*='list']")
    private Button listViewButton;

    public void openAddForm() {
        addButton.click();
    }

    public void search(String query) {
        searchField.setValue(query);
    }

    public void setTileView() {
        tileViewButton.click();
    }

    public void setListView() {
        listViewButton.click();
    }

    public boolean isTileView() {
        return tileViewButton.getAttribute("class").contains("active");
    }

    public boolean isListView() {
        return listViewButton.getAttribute("class").contains("active");
    }
}
