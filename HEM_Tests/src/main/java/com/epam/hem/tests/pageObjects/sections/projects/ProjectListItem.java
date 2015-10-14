package com.epam.hem.tests.pageObjects.sections.projects;

import com.epam.hem.tests.enums.ProjectPropertiesListView;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.common.Button;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.complex.TextList;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.composite.Section;
import org.openqa.selenium.support.FindBy;

/**
 * Created by Pavel_Shcherbakov1 on 2015-10-08
 */
public class ProjectListItem extends Section {

    @FindBy(css = "div div")
    public TextList<ProjectPropertiesListView> textLabels;
    @FindBy(css = "i[tooltip*='Edit']")
    private Button editButton;
    @FindBy(css = "i[tooltip*='Clone']")
    private Button cloneButton;

    public void openEditForm() {
        editButton.click();
    }

    public void openCloneForm() {
        cloneButton.click();
    }

    public String getProperty(String field) {

        int index = 0;

        switch (field) {
            case "Name" : index = ProjectPropertiesListView.Name.value; break;
            case "CreationDate" : index = ProjectPropertiesListView.Creator.value; break;
            case "CreationUser" : index = ProjectPropertiesListView.Owner.value; break;
            case "OwnerUser" : index = ProjectPropertiesListView.CreationDate.value; break;
        }

        return textLabels.getText(index);
    }
}
