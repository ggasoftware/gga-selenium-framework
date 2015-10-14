package com.epam.hem.tests.pageObjects.sections.base;

import com.epam.hem.tests.enums.ProjectDetails;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.common.Button;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.complex.TextList;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.composite.Section;
import org.openqa.selenium.support.FindBy;

/**
 * Created by Pavel_Shcherbakov1 on 2015-10-12
 */
public class InfoPanel extends Section {

    @FindBy(css = "ul[class*='side-panel-tabs'] li[ng-class*='details'] a")
    private Button detailsTab;
    @FindBy(css = "ul[class*='side-panel-tabs'] li[ng-class*='notes'] a")
    private Button notesTab;
    @FindBy(css = "small")
    public TextList<ProjectDetails> textLabels;

    protected void openDetailsTab() {
        detailsTab.click();
    }

    protected void openNotesTab() {
        notesTab.click();
    }
}
