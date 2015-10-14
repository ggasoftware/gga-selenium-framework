package com.epam.hem.tests.pageObjects.sections.projects;

import com.epam.hem.tests.pageObjects.sections.base.HeadPanel;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.common.CheckBox;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.common.Label;
import org.openqa.selenium.support.FindBy;

/**
 * Created by Pavel_Shcherbakov1 on 2015-10-08
 */
public class ProjectsHeadPanel extends HeadPanel {

    @FindBy(css = "div[class*='panel-title']")
    private Label panelTitle;
    @FindBy(id = "myProjects")
    private CheckBox onlyMyProjectsCheckbox;

    public String getTitle() {
        return panelTitle.getText();
    }

    public void displayOnlyMyProjects() {
        onlyMyProjectsCheckbox.check();
    }

    public void displayAllProjects() {
        onlyMyProjectsCheckbox.uncheck();
    }

    public boolean isOnlyMyProjectsDisplayed() {
        return onlyMyProjectsCheckbox.isChecked();
    }
}
