package com.moderna;

import com.epam.page_objects2.dataProviders.ExperimentsProviders;
import com.epam.page_objects2.entities.Experiment;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.lang.reflect.Method;

import static com.epam.page_objects2.enums.Preconditions.MCT_New_Experiment_Define_Experiment;
import static com.epam.page_objects2.mct.NewExperimentTab.newExperimentTab;
import static com.epam.page_objects2.mct.ViewExperimentsTab.viewExperimentsTab;
import static com.epam.page_objects2.mct.login.Portal.portal;
import static com.epam.ui_test_framework.utils.settings.FrameworkSettings.*;
import static java.lang.String.format;

/**
 * Created by Maksim_Palchevskii on 6/5/2015.
 */

public class MCT_QC_experiments2 extends InitTests {
    @BeforeMethod
    public void before(Method method) throws IOException {
        testName = method.getName();
        portal.isInState(MCT_New_Experiment_Define_Experiment);
    }

    @Test(dataProvider = "assayTypesExperiments", dataProviderClass = ExperimentsProviders.class)
    public void createSavePublish(Experiment experiment) {

        logger.test(format(" * Jira:  MCT-803, MCT-813, MCT-814, MCT-815, MCT-816, MCT-817\n" +
                "     Steps:\n" +
                "     1) Create new experiment with assayType '%s'" +
                "     2) Check that experiment created successfully"
                , experiment.assayType));
        newExperimentTab.createExperiment(experiment);
        viewExperimentsTab.checkPublication(experiment);
    }



}
