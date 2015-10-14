package com.epam.hem.tests.pageObjects.pages;

import com.epam.hem.tests.entities.Project;
import com.epam.hem.tests.pageObjects.sections.projects.*;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.composite.Page;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;

/**
 * Created by Pavel_Shcherbakov1 on 2015-10-08
 */
public class ProjectsPage extends Page {

    private final String panelBodySelector = "div[ui-view='projects'] div[class*='panel-body']";

    @FindBy(css = "div[ui-view='projects'] div[class*='panel-heading']")
    public ProjectsHeadPanel projectsHeadPanel;
    @FindBy(css = panelBodySelector)
    public ProjectsTileView projectsTileView;
    @FindBy(css = panelBodySelector)
    public ProjectsListView projectsListView;

    @FindBy(css = panelBodySelector)
    public AddProjectForm addProjectForm;
    @FindBy(css = panelBodySelector)
    public EditProjectForm editProjectForm;
    @FindBy(css = panelBodySelector)
    public CloneProjectForm cloneProjectForm;

    @FindBy(css = "div[class='col-md-3']")
    public ProjectInfoPanel projectInfoPanel;

    public void addProject(Project project) {
        projectsHeadPanel.openAddForm();
        addProjectForm.addProject(project);
    }

    public void editProject(String currentName, String newName, String newNotes) {
        if (projectsHeadPanel.isTileView())
            projectsTileView.openEditForm(currentName);
        else
            projectsListView.openEditForm(currentName);
        editProjectForm.saveProjectNameAndNotes(newName, newNotes);
    }

    public void cloneProject(String currentName, String newName, String newNotes) {
        if (projectsHeadPanel.isTileView())
            projectsTileView.openCloneForm(currentName);
        else
            projectsListView.openCloneForm(currentName);
        cloneProjectForm.saveProjectNameAndNotes(newName, newNotes);
    }

    public Boolean findProject(String name) {
        projectsHeadPanel.search(name);
        if (projectsHeadPanel.isTileView())
            return projectsTileView.isProjectDisplayed(name);
        else
            return projectsListView.isProjectDisplayed(name);
    }

    public boolean selectProjectByName(String name) {
        if (projectsHeadPanel.isTileView())
            return projectsTileView.selectProjectByName(name);
        else
            return projectsListView.selectProjectByName(name);
    }

    public void clearSearch() {
        projectsHeadPanel.search("");
    }

    public ArrayList<String> getDisplayedProjectsNames() {
        if (projectsHeadPanel.isTileView())
            return projectsTileView.getDisplayedProjectsNames();
        else
            return projectsListView.getDisplayedProjectsNames();
    }

    public ArrayList<String> getDisplayedProjectsOwners() {
        if (projectsHeadPanel.isTileView())
            return projectsTileView.getDisplayedProjectsOwners();
        else
            return projectsListView.getDisplayedProjectsOwners();
    }
}
