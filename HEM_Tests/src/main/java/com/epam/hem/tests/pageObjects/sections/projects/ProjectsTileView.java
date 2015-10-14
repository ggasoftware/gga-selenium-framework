package com.epam.hem.tests.pageObjects.sections.projects;

import com.epam.hem.tests.enums.ProjectPropertiesTileView;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.complex.ElementsGroup;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.complex.TextList;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.composite.Section;
import org.openqa.selenium.support.FindBy;
import java.util.ArrayList;

/**
 * Created by Pavel_Shcherbakov1 on 2015-10-08
 */
public class ProjectsTileView extends Section {

    @FindBy(css = "div[class='caption'] small[class*='title'] span")
    private TextList<ProjectPropertiesTileView> projectNames;
    @FindBy(xpath = "//span[text()='%s']/../../../div[@class='caption']")
    private ElementsGroup<Enum, ProjectTileItem> projectTileItems = new ElementsGroup<>(ProjectTileItem.class);

    public boolean isProjectDisplayed(String name) {
        return projectNames.getTextList().contains(name);
    }

    public boolean selectProjectByName(String name) {
        if (!projectNames.getLabels().contains(name))
            return false;
        getProjectTileItem(name).clickCenter();
        return true;
    }

    public void openEditForm(String name) {
        getProjectTileItem(name).openEditForm();
    }

    public void openCloneForm(String name) {
        getProjectTileItem(name).openCloneForm();
    }

    public ArrayList<String> getDisplayedProjectsOwners() {
        ArrayList<String> list = new ArrayList<>();
        for (String name : projectNames.getLabels())
            list.add(getProjectTileItem(name).getProperty("Owner", false));
        return list;
    }

    public ArrayList<String> getDisplayedProjectsNames() {
        return (ArrayList<String>) projectNames.getTextList();
    }

    public ProjectTileItem getProjectTileItem(String name) {
        return projectTileItems.get(name);
    }
}
