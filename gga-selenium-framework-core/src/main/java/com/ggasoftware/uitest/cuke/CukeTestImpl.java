package com.ggasoftware.uitest.cuke;

import com.ggasoftware.uitest.utils.ReporterNGExt;
import cucumber.api.CucumberOptions;
import cucumber.api.testng.TestNgReporter;
import cucumber.runtime.ClassFinder;
import cucumber.runtime.CucumberException;
import cucumber.runtime.RuntimeOptions;
import cucumber.runtime.RuntimeOptionsFactory;
import cucumber.runtime.io.MultiLoader;
import cucumber.runtime.io.ResourceLoader;
import cucumber.runtime.io.ResourceLoaderClassFinder;
import org.apache.commons.io.FilenameUtils;
import org.testng.ITest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Cucumber test implementation.
 *
 * @author Belousov Andrey
 */
public class CukeTestImpl implements ITest {

    private String feature;
    private String tags;
    private cucumber.runtime.Runtime runtime;

    public CukeTestImpl(String feature, String tags) {
        this.feature = feature;
        this.tags = tags;
        runtime = null;
    }

    @Override
    public String getTestName() {
        return FilenameUtils.getName(feature);
    }

    @Test(groups = "cucumber", description = "Runs Cucumber Features")
    public void CucumberRunner() throws Throwable {

        ClassLoader classLoader = getClass().getClassLoader();
        ResourceLoader resourceLoader = new MultiLoader(classLoader);

        RuntimeOptionsFactory roFactory = new RuntimeOptionsFactory(getClass(), new Class[]{CucumberOptions.class});

        RuntimeOptions ro = roFactory.create();

        ro.getGlue().clear();
        ro.getGlue().add("classpath:");

        ro.getFeaturePaths().clear();
        ro.getFeaturePaths().add(feature);

        if (!tags.isEmpty()) {
            ro.getFilters().add(tags);
        }

        TestNgReporter reporter = new TestNgReporter(System.out);
        ro.addFormatter(reporter);
        ClassFinder classFinder = new ResourceLoaderClassFinder(resourceLoader, classLoader);
        runtime = new cucumber.runtime.Runtime(resourceLoader, classFinder, classLoader, ro);

        try {
            ReporterNGExt.logBusiness(String.format("Cucumber --tags: %s", tags));
            runtime.run();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        runtime.printSummary();
        if (!runtime.getErrors().isEmpty()) {
            throw new CucumberException(runtime.getErrors().get(0));
        }

    }

}
