package com.epam.hem.tests.smoke;

import com.epam.hem.tests.InitTests;
import com.epam.hem.tests.entities.Project;
import com.epam.hem.tests.entities.User;
import com.epam.hem.tests.utils.Date;
import com.ggasoftware.jdiuitests.implementation.testng.asserter.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import static com.epam.hem.tests.pageObjects.HemSite.*;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import static com.ggasoftware.jdiuitests.core.settings.JDISettings.logger;

/**
 * Created by Pavel_Shcherbakov1 on 2015-10-08
 */
public class ProjectSmoke extends InitTests {

    private Project projectForTileView = new Project("projectTileprojectList1444736733883", "notes", "Pneumococcal Vaccination CEA Model");
    private Project projectForListView = new Project("projectList" + System.currentTimeMillis(), "notes", "Pneumococcal Vaccination CEA Model");

    @BeforeMethod
    public void before(Method method) throws IOException {
        homePage.isOpened();
    }

    @Test
    public void addProject() {
        logger.test("Smoke / Project / Add project");

        // tile view
        projectsPage.addProject(projectForTileView);
        Assert.isTrue(projectsPage.findProject(projectForTileView.getName()));
        projectsPage.selectProjectByName(projectForTileView.getName());

        Assert.areEquals(projectsPage.projectInfoPanel.getDetail("Name", false), "Name: " + projectForTileView.getName());
        Assert.areEquals(projectsPage.projectInfoPanel.getDetail("ModelName", false), "Model Name: " + projectForTileView.getModel());
        Assert.areEquals(projectsPage.projectInfoPanel.getNotes(), projectForTileView.getNotes());

        projectsPage.clearSearch();

        projectsPage.projectsHeadPanel.setListView();

        // list view
        projectsPage.addProject(projectForListView);
        Assert.isTrue(projectsPage.findProject(projectForListView.getName()));
        projectsPage.selectProjectByName(projectForListView.getName());

        Assert.areEquals(projectsPage.projectInfoPanel.getDetail("Name", false), "Name: " + projectForListView.getName());
        Assert.areEquals(projectsPage.projectInfoPanel.getDetail("ModelName", false), "Model Name: " + projectForListView.getModel());
        Assert.areEquals(projectsPage.projectInfoPanel.getNotes(), projectForListView.getNotes());

        projectsPage.clearSearch();

        projectsPage.projectsHeadPanel.setTileView();
    }

    @Test//(dependsOnMethods = "addProject")
    public void editProject() {
        logger.test("Smoke / Project / Edit project");

        // tile view
        projectsPage.editProject(projectForTileView.getName(), projectForTileView.getName() + "_edited", projectForTileView.getNotes() + "_edited");

        Assert.isFalse(projectsPage.findProject(projectForTileView.getName()));
        projectForTileView.setName(projectForTileView.getName() + "_edited");
        projectForTileView.setNotes(projectForTileView.getNotes() + "_edited");
        Assert.isTrue(projectsPage.findProject(projectForTileView.getName()));

        projectsPage.selectProjectByName(projectForTileView.getName());
        Assert.areEquals(projectsPage.projectInfoPanel.getDetail("Name", false), "Name: " + projectForTileView.getName());
        Assert.areEquals(projectsPage.projectInfoPanel.getNotes(), projectForTileView.getNotes());

        projectsPage.clearSearch();

        projectsPage.projectsHeadPanel.setListView();

        // list view
        projectsPage.editProject(projectForListView.getName(), projectForListView.getName() + "_edited", projectForListView.getNotes() + "_edited");

        Assert.isFalse(projectsPage.findProject(projectForListView.getName()));
        projectForListView.setName(projectForListView.getName() + "_edited");
        projectForListView.setNotes(projectForListView.getNotes() + "_edited");
        Assert.isTrue(projectsPage.findProject(projectForListView.getName()));

        projectsPage.selectProjectByName(projectForListView.getName());
        Assert.areEquals(projectsPage.projectInfoPanel.getDetail("Name", false), "Name: " + projectForListView.getName());
        Assert.areEquals(projectsPage.projectInfoPanel.getNotes(), projectForListView.getNotes());

        projectsPage.clearSearch();

        projectsPage.projectsHeadPanel.setTileView();
    }

    @Test(dependsOnMethods = "editProject")
    public void cloneProject() {
        logger.test("Smoke / Project / Clone project");

        // tile view
        projectsPage.cloneProject(projectForTileView.getName(), projectForTileView.getName() + "_cloned", projectForTileView.getNotes() + "_cloned");

        Assert.isTrue(projectsPage.findProject(projectForTileView.getName()));
        projectForTileView.setName(projectForTileView.getName() + "_cloned");
        projectForTileView.setNotes(projectForTileView.getNotes() + "_cloned");
        Assert.isTrue(projectsPage.findProject(projectForTileView.getName()));

        projectsPage.selectProjectByName(projectForTileView.getName());
        Assert.areEquals(projectsPage.projectInfoPanel.getDetail("Name", false), "Name: " + projectForTileView.getName());
        Assert.areEquals(projectsPage.projectInfoPanel.getNotes(), projectForTileView.getNotes());

        projectsPage.clearSearch();

        projectsPage.projectsHeadPanel.setListView();

        // list view
        projectsPage.cloneProject(projectForListView.getName(), projectForListView.getName() + "_cloned", projectForListView.getNotes() + "_cloned");

        Assert.isTrue(projectsPage.findProject(projectForListView.getName()));
        projectForListView.setName(projectForListView.getName() + "_cloned");
        projectForListView.setNotes(projectForListView.getNotes() + "_cloned");
        Assert.isTrue(projectsPage.findProject(projectForListView.getName()));

        projectsPage.selectProjectByName(projectForListView.getName());
        Assert.areEquals(projectsPage.projectInfoPanel.getDetail("Name", false), "Name: " + projectForListView.getName());
        Assert.areEquals(projectsPage.projectInfoPanel.getNotes(), projectForListView.getNotes());

        projectsPage.clearSearch();

        projectsPage.projectsHeadPanel.setTileView();
    }

    @Test(dependsOnMethods = "cloneProject")
    public void search() {
        logger.test("Smoke / Project / Search");

        // tile view
        Assert.isTrue(projectsPage.findProject(projectForTileView.getName()));
        ArrayList<String> displayedProjectsNames = projectsPage.getDisplayedProjectsNames();
        for (String name : displayedProjectsNames)
            Assert.contains(name, projectForTileView.getName());
        projectsPage.clearSearch();

        projectsPage.projectsHeadPanel.setListView();

        // list view
        Assert.isTrue(projectsPage.findProject(projectForListView.getName()));
        displayedProjectsNames = projectsPage.getDisplayedProjectsNames();
        for (String name : displayedProjectsNames)
            Assert.contains(name, projectForListView.getName());
        projectsPage.clearSearch();

        projectsPage.projectsHeadPanel.setTileView();
    }

    @Test(dependsOnMethods = "search")
    public void projectDetails() {
        logger.test("Smoke / Project / ProjectSmoke details");

        // tile view
        Assert.isTrue(projectsPage.findProject(projectForTileView.getName()));
        projectsPage.selectProjectByName(projectForTileView.getName());

        Assert.areEquals(projectsPage.projectsTileView.getProjectTileItem(projectForTileView.getName()).getProperty("Name", true), projectForTileView.getName());
        Assert.areEquals(projectsPage.projectsTileView.getProjectTileItem(projectForTileView.getName()).getProperty("Creator", true), User.DEFAULT_USER.userName);
        Assert.areEquals(projectsPage.projectsTileView.getProjectTileItem(projectForTileView.getName()).getProperty("Owner", true), User.DEFAULT_USER.userName);
        Assert.areEquals(projectsPage.projectsTileView.getProjectTileItem(projectForTileView.getName()).getProperty("CreationDate", true), Date.getCurrentDate());

        Assert.areEquals("Name: " + projectForTileView.getName(), projectsPage.projectInfoPanel.getDetail("Name", false));
        Assert.areEquals("Creator: " + User.DEFAULT_USER.userName, projectsPage.projectInfoPanel.getDetail("Creator", false));
        Assert.areEquals("Owner: " + User.DEFAULT_USER.userName, projectsPage.projectInfoPanel.getDetail("Owner", false));
        Assert.contains("Creation Date: " + Date.getCurrentDate(), projectsPage.projectInfoPanel.getDetail("CreationDate", false));
        Assert.contains("Modified Date: " + Date.getCurrentDate(), projectsPage.projectInfoPanel.getDetail("ModifiedDate", false));
        Assert.areEquals("Model Name: " + projectForTileView.getModel(), projectsPage.projectInfoPanel.getDetail("ModelName", false));

        projectsPage.clearSearch();

        projectsPage.projectsHeadPanel.setListView();

        // list view
        Assert.isTrue(projectsPage.findProject(projectForListView.getName()));
        projectsPage.selectProjectByName(projectForListView.getName());

        Assert.areEquals(projectsPage.projectsListView.getProjectListItem(projectForListView.getName()).getProperty("Name"), projectForListView.getName());
        Assert.areEquals(projectsPage.projectsListView.getProjectListItem(projectForListView.getName()).getProperty("Creator"), User.DEFAULT_USER.userName);
        Assert.areEquals(projectsPage.projectsListView.getProjectListItem(projectForListView.getName()).getProperty("Owner"), User.DEFAULT_USER.userName);
        Assert.areEquals(projectsPage.projectsListView.getProjectListItem(projectForListView.getName()).getProperty("CreationDate"), Date.getCurrentDate());

        Assert.areEquals("Name: " + projectForListView.getName(), projectsPage.projectInfoPanel.getDetail("Name", false));
        Assert.areEquals("Creator: " + User.DEFAULT_USER.userName, projectsPage.projectInfoPanel.getDetail("Creator", false));
        Assert.areEquals("Owner: " + User.DEFAULT_USER.userName, projectsPage.projectInfoPanel.getDetail("Owner", false));
        Assert.contains("Creation Date: " + Date.getCurrentDate(), projectsPage.projectInfoPanel.getDetail("CreationDate", false));
        Assert.contains("Modified Date: " + Date.getCurrentDate(), projectsPage.projectInfoPanel.getDetail("ModifiedDate", false));
        Assert.areEquals("Model Name: " + projectForListView.getModel(), projectsPage.projectInfoPanel.getDetail("ModelName", false));

        projectsPage.clearSearch();

        projectsPage.projectsHeadPanel.setTileView();
    }

    @Test(dependsOnMethods = "projectDetails")
    public void onlyMyProjectCheckbox() {

        // tile view
        logger.test("Smoke / Project / Only my projects checkbox");
        projectsPage.projectsHeadPanel.displayOnlyMyProjects();
        ArrayList<String> displayedProjectsOwners = projectsPage.getDisplayedProjectsOwners();
        for (String owner : displayedProjectsOwners)
            Assert.areEquals(owner, User.DEFAULT_USER.userName);
        projectsPage.projectsHeadPanel.displayAllProjects();

        projectsPage.projectsHeadPanel.setListView();

        // list view
        projectsPage.projectsHeadPanel.displayOnlyMyProjects();
        displayedProjectsOwners = projectsPage.getDisplayedProjectsOwners();
        for (String owner : displayedProjectsOwners)
            Assert.areEquals(owner, User.DEFAULT_USER.userName);
        projectsPage.projectsHeadPanel.displayAllProjects();

        projectsPage.projectsHeadPanel.setTileView();
    }
}
