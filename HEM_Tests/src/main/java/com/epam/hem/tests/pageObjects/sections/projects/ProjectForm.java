package com.epam.hem.tests.pageObjects.sections.projects;

import com.epam.hem.tests.entities.Project;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.common.Button;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.common.TextField;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.composite.Form;
import org.openqa.selenium.support.FindBy;

/**
 * Created by Pavel_Shcherbakov1 on 2015-10-12
 */
public class ProjectForm extends Form<Project> {

    @FindBy(id = "projectName")
    protected TextField projectName;
    @FindBy(id = "projectNotes")
    protected TextField projectNotes;
    @FindBy(xpath = "//button[.='Save']")
    protected Button saveButton;
    @FindBy(xpath = "//button[.='Cancel']")
    protected Button cancelButton;

    public void saveProjectNameAndNotes(String name, String notes) {
        projectName.setValue(name);
        projectNotes.setValue(notes);
        saveButton.click();
    }

}
