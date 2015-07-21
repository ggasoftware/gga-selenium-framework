package com.epam.page_objects.mct;

import com.epam.page_objects.entities.Experiment;
import com.epam.page_objects.enums.AssayTypes;
import com.epam.page_objects.enums.MCTTabs;
import com.epam.page_objects.mct.custom.ModernaTabs;
import com.epam.page_objects.mct.popup.AmplexRedInfoPopup;
import com.epam.page_objects.mct.popup.LCMCInfoPopup;
import com.epam.ui_test_framework.elements.complex.Dropdown;
import com.epam.ui_test_framework.elements.composite.Form;
import com.epam.ui_test_framework.elements.interfaces.common.*;
import com.epam.ui_test_framework.elements.interfaces.complex.*;
import com.epam.ui_test_framework.elements.page_objects.annotations.functions.SubmitButton;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;
import ru.yandex.qatools.allure.annotations.Step;

import static com.epam.page_objects.enums.MCTTabs.*;
import static com.epam.page_objects.mct.popup.Popups.mctInfoPopup;
import static com.epam.ui_test_framework.utils.common.Timer.sleep;
import static com.epam.ui_test_framework.utils.common.Timer.waitCondition;
import static com.epam.ui_test_framework.utils.settings.FrameworkSettings.seleniumFactory;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

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

    @FindBy(css = "input[name=name]")           private IInput name;

    @FindBy(xpath = "//*[@class='k-input' and @placeholder='Select Project...']")
    private IComboBox project;
    @FindBy(xpath = "//*[@class='k-input' and @placeholder='Select Target...']")
    private IComboBox target;
    private IDropDown<AssayTypes> assayType = new Dropdown<>(
            By.xpath("//*[@class='k-input' and text()='Select Assay Type...']"),
            By.xpath("//ul[@aria-hidden='false']/li[text()='%s']"));
    private AmplexRedInfoPopup amplexRedInfo;
    private LCMCInfoPopup lcmcInfo;
    @FindBy(xpath = "//*[@class='k-input' and @placeholder='Select Source...']")
    private IComboBox source;
    @FindBy(css = "input[name=cellType_input]")         private IInput cellLine;
    @FindBy(css = "input[name=cellCountInMillions]")    private IInput cellCount;
    @FindBy(css = "input[name=transfectedDate]")        private IDatePicker transfectedDate;
    @FindBy(css = "select[name=transfectedBy]")         private IDropDown transfectionBy;

    @FindBy(xpath = "//*[contains(@data-bind, 'form.comment')")     private ITextArea commentInput;
    @FindBy(css = "[data-bind='text: experiment.name']")            private ILabel savedExperimentLabel;

    @FindBy(css = "input[name=blot_images]")                 private IFileInput blotImage;
    @FindBy(css = "input[name=transfection_control_images]") private IFileInput transfection;
    @FindBy(css = "textarea[name=containersString]")         private ITextArea sampleIdentifiers;

    @FindBy(css = "input[name=platemap]")   private IFileInput platemap;
    @FindBy(css = "input[name=readout]")    private IFileInput readout;

    @FindBy(css = "input[name=support]")    private IFileInput attachments;

    @FindBy(xpath = "//button[*[text()='New Experiment']]")     private IButton newExperimentButton;
    @FindBy(xpath = "//button[*[text()='Save']]")               private IButton saveButton;
    @FindBy(xpath = "//button[*[text()='Save & Publish']]")     private IButton savePublishButton;
    @FindBy(xpath = "//button[*[text()='Publish']]")            private IButton publishButton;
    @SubmitButton
    @FindBy(xpath = "//button[*[text()='Save & Next']]")        private IButton saveNextButton;

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
        submit(experiment);
        checkExperimentSave(experiment);
        publishExperiment(experiment);
    }

    public void openMCTTab(MCTTabs mctTab) {
        mctTabs.select(mctTab);
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

    // Create Experiment Publish
    @Step
    public void publishExperiment(Experiment experiment) {
        publishButton.click();
        sleep(500);
        mctInfoPopup().ok();
        experiment.id = mctInfoPopup().getExperimentId();
        mctInfoPopup().ok();
    }
}
