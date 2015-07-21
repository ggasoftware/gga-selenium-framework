package com.epam.page_objects.dataProviders;

import com.epam.page_objects.entities.Experiment;
import org.testng.annotations.DataProvider;

import static com.epam.page_objects.enums.AssayTypes.*;

/**
 * Created by Nataliia_Garkusha on 05-Jun-15.
 */
public class ExperimentsProviders {

    @DataProvider(name = "assayTypesExperiments")
    public static Object[][] assayTypesExperiments() {
        return new Object[][] {
                {new Experiment(ELISA) },
                {new Experiment(AMPLEX_RED) },
                {new Experiment(FLUORESCENCE) },
                {new Experiment(LUMINESCENCE) },
                /*{new Experiment(LC_MS)
                    .setBarCode("BarCode")},*/
                {new Experiment(WESTERN_BLOT) }
        };
    }
}
