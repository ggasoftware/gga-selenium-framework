package com.moderna;

import com.epam.page_objects.dataProviders.ExperimentsProviders;
import com.epam.page_objects.entities.Experiment;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.lang.reflect.Method;

import static com.epam.page_objects.enums.Preconditions.MCT_New_Experiment_Define_Experiment;
import static com.epam.page_objects.mct.NewExperimentTab.newExperimentTab;
import static com.epam.page_objects.mct.ViewExperimentsTab.viewExperimentsTab;
import static com.epam.page_objects.mct.login.Portal.portal;
import static com.epam.ui_test_framework.utils.settings.FrameworkSettings.logger;
import static com.epam.ui_test_framework.utils.settings.FrameworkSettings.testName;
import static java.lang.String.format;

/**
 * Created by Maksim_Palchevskii on 6/5/2015.
 */

public class MCT_QC_experiments extends InitTests {
    @BeforeMethod
    public void before(Method method) throws IOException {
        testName = method.getName();
        portal.isInState(MCT_New_Experiment_Define_Experiment);
    }

    @Test(dataProvider = "assayTypesExperiments", dataProviderClass = ExperimentsProviders.class)
    public void createSavePublish(Experiment experiment) {
        logger.test(format(
                "     Steps:\n" +
                "     1) Create new experiment with assayType '%s'" +
                "     2) Check that experiment created successfully"
                , experiment.assayType));
        newExperimentTab.createExperiment(experiment);
        viewExperimentsTab.checkPublication(experiment);
    }

}
