package com.epam.hem.tests.pageObjects.sections.projects;

import com.epam.hem.tests.entities.Project;
import com.epam.hem.tests.enums.ModelProperties;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.complex.ElementsGroup;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.complex.TextList;
import org.openqa.selenium.support.FindBy;

/**
 * Created by Pavel_Shcherbakov1 on 2015-10-09
 */
public class AddProjectForm extends ProjectForm {

    @FindBy(css = "div[class='caption'] small[class*='title'] span")
    private TextList<ModelProperties> modelNames;
    @FindBy(xpath = "//span[text()='%s']/../../../div[@class='caption']")
    private ElementsGroup<Enum, ModelTileItem> modelTileItems = new ElementsGroup<>(ModelTileItem.class);

    public void addProject(Project project) {
        projectName.setValue(project.getName());
        projectNotes.setValue(project.getNotes());
        selectModelByName(project.getModel());
        saveButton.click();
    }

    private boolean selectModelByName(String name) {
        if (!modelNames.getLabels().contains(name))
            return false;
        modelTileItems.get(name).clickCenter();
        return true;
    }
}