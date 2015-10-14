package com.epam.hem.tests.pageObjects.sections.projects;

import com.epam.hem.tests.enums.ProjectPropertiesTileView;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.common.Button;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.complex.TextList;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.composite.Section;
import org.openqa.selenium.support.FindBy;

/**
 * Created by Pavel_Shcherbakov1 on 2015-10-08
 */
public class ProjectTileItem extends Section {

    @FindBy(css = "small")
    protected TextList<ProjectPropertiesTileView> textLabels;
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

    public String getProperty(String field, boolean onlyValue) {

        int index = 0;

        switch (field) {
            case "Name" : index = ProjectPropertiesTileView.Name.value; break;
            case "CreationDate" : index = ProjectPropertiesTileView.CreationDate.value; break;
            case "CreationUser" : index = ProjectPropertiesTileView.CreationUser.value; break;
            case "OwnerUser" : index = ProjectPropertiesTileView.OwnerUser.value; break;
        }

        String textLabel = textLabels.getText(index);

        if (onlyValue)
            return textLabel.substring(textLabel.indexOf(":") + 2);
        else
            return textLabel;
    }
}
