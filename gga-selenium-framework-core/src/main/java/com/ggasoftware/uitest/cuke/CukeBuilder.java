package com.ggasoftware.uitest.cuke;

import com.ggasoftware.uitest.utils.ReporterNGExt;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Cucumber builder.
 *
 * @author Belousov Andrey
 */
public class CukeBuilder {

    /**
     * Get feature files.
     *
     * @param featurePath - features files root path.
     * @return List of feature files
     */
    private static List<String> getFeatures(final File featurePath) {
        List<String> features = new ArrayList<>();

        if (featurePath.isDirectory()) {
            File[] list = featurePath.listFiles();
            if (list != null) {
                for (File file : list) {
                    features.addAll(getFeatures(new File(file.getAbsolutePath())));
                }
            }
        } else {
            if (featurePath.getName().endsWith(".feature")) {
                features.add(featurePath.getPath());
            }
        }
        return features;
    }

    /**
     * Run cucumber features.
     *
     * @return List of features tests
     */
    public Object[] Run() {
        return Run("");
    }

    /**
     * Run cucumber features.
     *
     * @param tags - cucumber tags options.
     * @return List of features tests
     */
    public Object[] Run(String tags) {

        ReporterNGExt.logBusiness(String.format("Run Cucumber features with tags: %s", tags));
        CukeTestImpl test;

        List<Object> tests = new ArrayList<>();

        List<String> features = getFeatures(new File("src/test/resources/features"));
        for (String feature : features) {

            test = new CukeTestImpl(feature, tags);
            tests.add(test);
        }

        return tests.toArray();
    }

}
