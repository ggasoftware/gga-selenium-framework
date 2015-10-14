package com.epam.hem.tests.pageObjects.sections.common;

import com.ggasoftware.jdiuitests.implementation.selenium.elements.common.Button;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.composite.Section;
import org.openqa.selenium.support.FindBy;

/**
 * Created by Pavel_Shcherbakov1 on 2015-10-12
 */
public class CommonPanel extends Section {

    @FindBy(xpath = "//a[.='Projects']")
    private Button projectsButton;
    @FindBy(xpath = "//a[.='Scenario']")
    private Button scenarioButton;
    @FindBy(xpath = "//a[.='Analysis']")
    private Button analysisButton;
    @FindBy(xpath = "//a[.='Results']")
    private Button resultsButton;

    @FindBy(css = "button[value='Start Workflow']")
    private Button startWorkflowButton;
    @FindBy(css = "button[value='Previous']")
    private Button previousButton;
    @FindBy(css = "button[value='Next']")
    private Button nextButton;

    public void openProjects() {
        projectsButton.click();
    }

    public void openScenarios() {
        scenarioButton.click();
    }

    public void openAnalyses() {
        analysisButton.click();
    }

    public void openResults() {
        resultsButton.click();
    }

    public void startWorkflow() {
        startWorkflowButton.click();
    }

    public void previous() {
        previousButton.click();
    }

    public void next() {
        nextButton.click();
    }
}