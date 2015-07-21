package com.epam.page_objects2.mct;

import com.epam.page_objects2.entities.Experiment;
import com.epam.page_objects2.enums.*;
import com.epam.page_objects2.mct.custom.*;
import com.epam.ui_test_framework.elements.base.ElementsGroup;
import com.epam.ui_test_framework.elements.complex.Dropdown;
import com.epam.ui_test_framework.elements.composite.*;
import com.epam.ui_test_framework.elements.interfaces.complex.*;
import com.epam.ui_test_framework.elements.interfaces.common.*;
import com.epam.ui_test_framework.elements.common.*;
import org.openqa.selenium.By;

import org.openqa.selenium.support.FindBy;
import ru.yandex.qatools.allure.annotations.Step;

import static com.epam.page_objects2.enums.ExperimentButtons.*;
import static com.epam.page_objects2.enums.ExperimentInputs.*;
import static com.epam.page_objects2.enums.MCTTabs.*;
import static com.epam.page_objects2.mct.popup.Popups.*;
import static com.epam.ui_test_framework.utils.common.Timer.waitCondition;
import static com.epam.ui_test_framework.utils.settings.FrameworkSettings.seleniumFactory;
import static com.epam.ui_test_framework.utils.usefulUtils.TryCatchUtil.*;
import static com.epam.ui_test_framework.utils.common.Timer.sleep;
import static org.testng.Assert.*;

/**
 * Created by Maksim_Palchevskii on 6/5/2015.
 */

public class NewExperimentTab extends Form<Experiment> {
    public static NewExperimentTab newExperimentTab = InitElements(new NewExperimentTab());

    //Page elements
    @FindBy(css = ".worksheet-header")  public ILabel newExperimentHeader;
    private ITabs<MCTTabs> mctTabs = new ModernaTabs(
            By.xpath("//*[@data-role='tabstrip']//li[*[text()='%s']]"),
            By.xpath("//*[@data-role='tabstrip']//li/a"));
    private IInput name = new Input(By.cssSelector("input[name=name]")) {
            @Override
            protected void inputAction(String text) {
                ignoreException(() -> sleep(500));
                super.inputAction(text);
            }
        };
    private IComboBox project = new ModernaCombobox<>(
            By.xpath("//*[*[@class='k-input' and @placeholder='Select Project...']]/span"),
            By.xpath("//ul[@aria-hidden='false']/li[text()='%s']"));
    private IComboBox target = new ModernaCombobox<>(
            By.xpath("//*[@class='k-input' and @placeholder='Select Target...']"));
    private IDropDown<AssayTypes> assayType = new Dropdown<>(
            By.xpath("//*[@class='k-input' and text()='Select Assay Type...']"),
            By.xpath("//ul[@aria-hidden='false']/li[text()='%s']"));
    private IComboBox source = new ModernaCombobox<>(
            By.xpath("//*[@class='k-input' and @placeholder='Select Source...']"));
    private IGroup<ExperimentInputs, Input> expInputs = new ElementsGroup<>(By.cssSelector("input[name=%s]"), Input.class);
    @FindBy(css = "input[name=blot_images]")                 private IFileInput blotImage;
    @FindBy(css = "input[name=transfection_control_images]") private IFileInput transfection;
    @FindBy(css = "textarea[name=containersString]")         private ITextArea sampleIdentifiers;

    @FindBy(css = "input[name=platemap]")   private IFileInput platemap;
    @FindBy(css = "input[name=readout]")    private IFileInput readout;

    @FindBy(css = "input[name=support]")    private IFileInput attachments;

    @FindBy(xpath = "//*[contains(@data-bind, 'form.comment')")     private ITextArea commentInput;
    @FindBy(css = "select[name=transfectedBy]")                     private IDropDown transfectionBy;
    @FindBy(css = "select[name=transfectedDate]")                   private IDatePicker transfectedDate;
    @FindBy(css = "[data-bind='text: experiment.name']")            private ILabel savedExperimentLabel;

    private IGroup<ExperimentButtons, Button> expButtons = new ElementsGroup<>(By.xpath("//button[*[text()='%s']]"), Button.class);

    @FindBy(xpath = "//td/a[@class='btn-comment k-state-default']") private IButton commentExpBtn;

    public void prepeareNewExperiment() {
        if (!mctTabs.waitSelected(DEFINE_EXPERIMENT))
            mctTabs.select(DEFINE_EXPERIMENT);
        else
            seleniumFactory.getDriver().navigate().refresh();

        name.waitDisplayed();
    }

    // Create Experiment Save and Publish
    @Step
    public void createExperiment(Experiment experiment) {
        newSaveNextExperiment(experiment);
        checkExperimentSave(experiment);
        publishExperiment(experiment);
    }

    private void checkExperimentSave(Experiment experiment) {
        assertEquals(savedExperimentLabel.getText(), experiment.name, "Check Experiment name");
        switch (experiment.assayType) {
            case WESTERN_BLOT:
                assertTrue(waitCondition(() -> mctTabs.waitSelected(RESULTS)), "Results tab selected");
                break;
            default:
                assertTrue(waitCondition(() -> mctTabs.waitSelected(READOUT)), "Readout tab selected");
                openMCTTab(RESULTS);
                break;
        }
    }

    public void openMCTTab(MCTTabs mctTab) {
        mctTabs.select(mctTab);
    }


    // Create Experiment SaveNext and Publish
    @Step
    public void newSaveNextExperiment(Experiment experiment) {
        // Mandatory fields
        name.waitDisplayed();
        name.newInput(experiment.name);
        project.select(experiment.project);
        target.newInput(experiment.target);
        assayType.select(experiment.assayType);
        switch(experiment.assayType) {
            case AMPLEX_RED:
                amplexRedInfoPopup().submit(experiment);
                break;
            case LC_MS:
                lcmCInfoPopup().submit(experiment);
                break;
        }
        source.newInput(experiment.source);

        platemap.newInput(experiment.platemap);
        readout.newInput(experiment.readout);

        blotImage.newInput(experiment.blotImage);
        transfection.newInput(experiment.transfection);
        sampleIdentifiers.newInput(experiment.sampleIdentifiers);

        expInputs.get(CELL_LINE).newInput(experiment.cellLine);
        expInputs.get(CELL_COUNT).newInput(experiment.cellCount);
        expInputs.get(CELL_PASSAGE).newInput(experiment.cellPassage);
        commentInput.newInput(experiment.comment);
        transfectionBy.select(experiment.transfectedBy);
        transfectedDate.newInput(experiment.transfectedDate);

        expButtons.get(SAVE_NEXT).click();
    }

    // Create Experiment Publish
    @Step
    public void publishExperiment(Experiment experiment) {
        expButtons.get(PUBLISH).click();
        sleep(500);
        mctInfoPopup().ok();
        experiment.id = mctInfoPopup().getExperimentId();
        mctInfoPopup().ok();
    }

}
