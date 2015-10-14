package com.epam.hem.tests.pageObjects.sections.projects;

import com.epam.hem.tests.enums.ProjectPropertiesListView;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.complex.ElementsGroup;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.complex.TextList;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.composite.Section;
import org.openqa.selenium.support.FindBy;
import java.util.ArrayList;

/**
 * Created by Pavel_Shcherbakov1 on 2015-10-12
 */
public class ProjectsListView extends Section {

    @FindBy(css = "div[on='fmView'] div[role='row']:not([class*='header']) div:nth-child(1) div")
    private TextList<ProjectPropertiesListView> projectNames;
    @FindBy(xpath = "//div[@on='fmView']//div[@role='row' and not(contains(@class, 'header'))]//div[1]//div[.='%s']/../..")
    private ElementsGroup<Enum, ProjectListItem> projectListItems = new ElementsGroup<>(ProjectListItem.class);

    public boolean isProjectDisplayed(String name) {
        return projectNames.getTextList().contains(name);
    }

    public boolean selectProjectByName(String name) {
        if (!projectNames.getLabels().contains(name))
            return false;
        getProjectListItem(name).clickCenter();
        return true;
    }

    public void openEditForm(String name) {
        getProjectListItem(name).openEditForm();
    }

    public void openCloneForm(String name) {
        getProjectListItem(name).openCloneForm();
    }

    public ArrayList<String> getDisplayedProjectsOwners() {
        ArrayList<String> list = new ArrayList<>();
        for (String name : projectNames.getLabels())
            list.add(getProjectListItem(name).getProperty("Owner"));
        return list;
    }

    public ArrayList<String> getDisplayedProjectsNames() {
        return (ArrayList<String>) projectNames.getTextList();
    }

    public ProjectListItem getProjectListItem(String name) {
        return projectListItems.get(name);
    }
}
