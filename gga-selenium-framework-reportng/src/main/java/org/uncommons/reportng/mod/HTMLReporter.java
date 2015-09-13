//=============================================================================
// Copyright 2006-2013 Daniel W. Dyer
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
//=============================================================================
package org.uncommons.reportng.mod;

import org.apache.velocity.VelocityContext;
import org.testng.*;
import org.testng.xml.XmlSuite;
import org.uncommons.reportng.ReportNGException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Enhanced HTML reporter for TestNG that uses Velocity templates to generate its
 * output.
 * @author Daniel Dyer
 *
 * @author azhukov
 * Modifications:
 * - use modification path
 * - add application version
 * - add test results html file with status, dependency and duration.
 * - add chronology html file
 * - add new resources to copy.
 */
public class HTMLReporter extends AbstractReporter
{
    private static final String FRAMES_PROPERTY = "org.uncommons.reportng.mod.frames";
    private static final String ONLY_FAILURES_PROPERTY = "org.uncommons.reportng.mod.failures-only";

    private static final String TEMPLATES_PATH = "org/uncommons/reportng/mod/templates/html/";
    private static final String INDEX_FILE = "index.html";
    private static final String SUITES_FILE = "suites.html";
    private static final String OVERVIEW_FILE = "overview.html";
    private static final String GROUPS_FILE = "groups.html";
    private static final String RESULTS_FILE = "results.html";
    private static final String RESULTEST_FILE = "test_result.html";
    private static final String CHRONOLOGY_FILE = "chronology.html";
    private static final String OUTPUT_FILE = "output.html";
    private static final String CUSTOM_STYLE_FILE = "custom.css";

    private static final String SUITE_KEY = "suite";
    private static final String SUITES_KEY = "suites";
    private static final String GROUPS_KEY = "groups";
    private static final String RESULT_KEY = "result";
    private static final String STATUS_KEY = "testResultStatus";
    private static final String DEPEND_KEY = "dependency";
    private static final String DURATION_KEY = "duration";
    private static final String START_KEY = "start";

    private static final String FAILED_CONFIG_KEY = "failedConfigurations";
    private static final String SKIPPED_CONFIG_KEY = "skippedConfigurations";
    private static final String FAILED_TESTS_KEY = "failedTests";
    private static final String SKIPPED_TESTS_KEY = "skippedTests";
    private static final String PASSED_TESTS_KEY = "passedTests";
    private static final String ONLY_FAILURES_KEY = "onlyReportFailures";
    private static final String ALL_TESTS_KEY = "allTests";
    private static final String METHODS_KEY = "methods";

    private static final SimpleDateFormat SDF = new SimpleDateFormat("HH:mm:ss");

    private static final String REPORT_DIRECTORY = "html";

    private static final Comparator<ITestNGMethod> METHOD_COMPARATOR = new TestMethodComparator();
    private static final Comparator<ITestResult> RESULT_COMPARATOR = new TestResultComparator();
    private static final Comparator<IClass> CLASS_COMPARATOR = new TestClassComparator();
    public static final String APPLICATION_VERSION = "applicationVersion";


    public HTMLReporter()
    {
        super(TEMPLATES_PATH);
    }

    
    /**
     * Generates a set of HTML files that contain data about the outcome of
     * the specified test suites.
     * @param suites Data about the test runs.
     * @param outputDirectoryName The directory in which to create the report.
     */
    public void generateReport(List<XmlSuite> xmlSuites,
                               List<ISuite> suites,
                               String outputDirectoryName)
    {
        removeEmptyDirectories(new File(outputDirectoryName));
        
        boolean useFrames = System.getProperty(FRAMES_PROPERTY, "true").equals("true");
        boolean onlyFailures = System.getProperty(ONLY_FAILURES_PROPERTY, "false").equals("true");

        File outputDirectory = new File(outputDirectoryName, REPORT_DIRECTORY);
        outputDirectory.mkdirs();

        try
        {
            if (useFrames)
            {
                createFrameset(outputDirectory);
            }
            createOverview(suites, outputDirectory, !useFrames, onlyFailures);
            createSuiteList(suites, outputDirectory, onlyFailures);
            createGroups(suites, outputDirectory);
            createResults(suites, outputDirectory, onlyFailures);
            createTestResults(suites, outputDirectory);
            createLog(outputDirectory, onlyFailures);
            copyResources(outputDirectory);
        }
        catch (Exception|AssertionError ex)
        {
            throw new ReportNGException("Failed generating HTML report.", ex);
        }
    }


    /**
     * Create the index file that sets up the frameset.
     * @param outputDirectory The target directory for the generated file(s).
     */
    private void createFrameset(File outputDirectory) throws IOException {
        VelocityContext context = createContext();
        generateFile(new File(outputDirectory, INDEX_FILE),
                     INDEX_FILE + TEMPLATE_EXTENSION,
                     context);
    }


    private void createOverview(List<ISuite> suites,
                                File outputDirectory,
                                boolean isIndex,
                                boolean onlyFailures) throws IOException {
        VelocityContext context = createContext();
        context.put(SUITES_KEY, suites);
        context.put(ONLY_FAILURES_KEY, onlyFailures);
        for (ISuite suite : suites) {
            String version="";
            for (ISuiteResult result : suite.getResults().values()) {
                version = getApplicationVersion(result.getTestContext().getPassedTests());
                if (version == null){
                    version = getApplicationVersion(result.getTestContext().getFailedTests());
                } else {break;}
                if (version == null){
                    version = getApplicationVersion(result.getTestContext().getSkippedTests());
                } else {break;}
            }
            if (version != null) {
                context.put(APPLICATION_VERSION, version);
                break;
            }
        }
        generateFile(new File(outputDirectory, isIndex ? INDEX_FILE : OVERVIEW_FILE),
                     OVERVIEW_FILE + TEMPLATE_EXTENSION,
                     context);
    }

    private String getApplicationVersion(IResultMap iResultMap) {
        for (ITestNGMethod iTestNGMethod : iResultMap.getAllMethods()) {
            for (ITestResult iTestResult : iResultMap.getResults(iTestNGMethod)){
                String version = (String) iTestResult.getAttribute(APPLICATION_VERSION);
                if (version != null){
                    return version;
                }
            }
        }
        return null;
    }

    /**
     * Create the navigation frame.
     * @param outputDirectory The target directory for the generated file(s).
     */
    private void createSuiteList(List<ISuite> suites,
                                 File outputDirectory,
                                 boolean onlyFailures) throws IOException {
        VelocityContext context = createContext();
        context.put(SUITES_KEY, suites);
        context.put(ONLY_FAILURES_KEY, onlyFailures);
        generateFile(new File(outputDirectory, SUITES_FILE),
                     SUITES_FILE + TEMPLATE_EXTENSION,
                     context);
    }


    /**
     * Generate a results file for each test in each suite.
     * @param outputDirectory The target directory for the generated file(s).
     */
    private void createResults(List<ISuite> suites,
                               File outputDirectory,
                               boolean onlyShowFailures) throws IOException {
        int index = 1;
        for (ISuite suite : suites)
        {
            int index2 = 1;
            for (ISuiteResult result : suite.getResults().values())
            {
                boolean failuresExist = result.getTestContext().getFailedTests().size() > 0
                                        || result.getTestContext().getFailedConfigurations().size() > 0;
                if (!onlyShowFailures || failuresExist)
                {
                    VelocityContext context = createContext();
                    context.put(RESULT_KEY, result);
                    context.put(FAILED_CONFIG_KEY, sortByTestClass(result.getTestContext().getFailedConfigurations()));
                    context.put(SKIPPED_CONFIG_KEY, sortByTestClass(result.getTestContext().getSkippedConfigurations()));
                    context.put(FAILED_TESTS_KEY, sortByTestClass(result.getTestContext().getFailedTests()));
                    context.put(SKIPPED_TESTS_KEY, sortByTestClass(result.getTestContext().getSkippedTests()));
                    context.put(PASSED_TESTS_KEY, sortByTestClass(result.getTestContext().getPassedTests()));
                    IResultMap iResultMap = result.getTestContext().getPassedTests();
                    IResultMap iResultMapFailed = result.getTestContext().getFailedTests();
                    IResultMap iResultMapSkipped = result.getTestContext().getSkippedTests();
                    for (ITestNGMethod iTestNGMethod : iResultMapFailed.getAllMethods()) {
                        for (ITestResult iTestResult : iResultMapFailed.getResults(iTestNGMethod)) {
                            iResultMap.addResult(iTestResult, iTestNGMethod);
                        }
                    }
                    for (ITestNGMethod iTestNGMethod : iResultMapSkipped.getAllMethods()) {
                        for (ITestResult iTestResult : iResultMapSkipped.getResults(iTestNGMethod)) {
                            iResultMap.addResult(iTestResult, iTestNGMethod);
                        }
                    }
                    context.put(ALL_TESTS_KEY, sortByTestClass(iResultMap));
                    String fileName = String.format("suite%d_test%d_%s", index, index2, RESULTS_FILE);
                    generateFile(new File(outputDirectory, fileName),
                                 RESULTS_FILE + TEMPLATE_EXTENSION,
                                 context);
                }
                ++index2;
            }
            ++index;
        }
    }

    public void sortTests(List<ITestResult> collection) {
        Collections.sort(collection, RESULT_COMPARATOR);
    }

    public void createTestResults(List<ISuite> suites, File outputDirectory) throws IOException {
        for (ISuite suite : suites) {
            for (ISuiteResult result : suite.getResults().values()) {
                HashMap<String, String> testsResult = new HashMap<>();
                IResultMap results = result.getTestContext().getFailedConfigurations();
                for (ITestResult testResult : results.getAllResults()) {
                    VelocityContext context = createContext();
                    long duration = testResult.getEndMillis() - testResult.getStartMillis();
                    Date date = new Date(testResult.getStartMillis());
                    context.put(RESULT_KEY, testResult);
                    context.put(STATUS_KEY, 1);
                    context.put(DURATION_KEY, duration);
                    context.put(START_KEY, SDF.format(date));
                    String name = (String) testResult.getAttribute("name");
                    String fileName;
                    if (name != null) {
                        fileName = String.format("test_%s_%s.html", testResult.getName(), name);
                    } else {
                        fileName = String.format("test_%s.html", testResult.getName());
                    }
                    testsResult.put(testResult.getName(), fileName);
                    generateFile(new File(outputDirectory, fileName),
                            RESULTEST_FILE + TEMPLATE_EXTENSION,
                            context);
                }

                results = result.getTestContext().getFailedTests();
                for (ITestResult testResult : results.getAllResults()) {
                    VelocityContext context = createContext();
                    String dependencyFile = testsResult.get(testResult.getName());
                    Date date = new Date(testResult.getStartMillis());
                    context.put(RESULT_KEY, testResult);
                    context.put(DEPEND_KEY, dependencyFile);
                    context.put(STATUS_KEY, 2);
                    context.put(START_KEY, SDF.format(date));
                    String name = (String) testResult.getAttribute("name");
                    String fileName;
                    if (name != null) {
                        fileName = String.format("test_%s_%s.html", testResult.getName(), name);
                    } else {
                        fileName = String.format("test_%s.html", testResult.getName());
                    }
                    generateFile(new File(outputDirectory, fileName),
                            RESULTEST_FILE + TEMPLATE_EXTENSION,
                            context);

                }
                results = result.getTestContext().getSkippedTests();
                for (ITestResult testResult : results.getAllResults()) {
                    VelocityContext context = createContext();
                    Date date = new Date(testResult.getStartMillis());
                    context.put(RESULT_KEY, testResult);
                    context.put(STATUS_KEY, 3);
                    context.put(START_KEY, SDF.format(date));
                    String name = (String) testResult.getAttribute("name");
                    String fileName;
                    if (name != null) {
                        fileName = String.format("test_%s_%s.html", testResult.getName(), name);
                    } else {
                        fileName = String.format("test_%s.html", testResult.getName());
                    }
                    generateFile(new File(outputDirectory, fileName),
                            RESULTEST_FILE + TEMPLATE_EXTENSION,
                            context);
                }
                results = result.getTestContext().getPassedTests();
                for (ITestResult testResult : results.getAllResults()) {
                    VelocityContext context = createContext();
                    long duration = testResult.getEndMillis() - testResult.getStartMillis();
                    Date date = new Date(testResult.getStartMillis());
                    context.put(RESULT_KEY, testResult);
                    int status = 4;
                    if (testResult.getStatus() == 2){
                        status = 2;
                    }
                    context.put(STATUS_KEY, status);
                    context.put(DURATION_KEY, duration);
                    context.put(START_KEY, SDF.format(date));
                    String name = (String) testResult.getAttribute("name");
                    String fileName;
                    if (name != null) {
                        fileName = String.format("test_%s_%s.html", testResult.getName(), name);
                    } else {
                        fileName = String.format("test_%s.html", testResult.getName());
                    }

                    generateFile(new File(outputDirectory, fileName),
                            RESULTEST_FILE + TEMPLATE_EXTENSION,
                            context);
                }
            }
        }
    }

    public void createChronology(List<ISuite> suites, File outputDirectory) throws IOException {
        int index = 1;
        for (ISuite suite : suites) {
            List<IInvokedMethod> methods = suite.getAllInvokedMethods();
            if (!methods.isEmpty()) {
                VelocityContext context = createContext();
                context.put(SUITE_KEY, suite);
                context.put(METHODS_KEY, methods);
                String fileName = String.format("suite%d_%s", index, CHRONOLOGY_FILE);
                generateFile(new File(outputDirectory, fileName),
                        CHRONOLOGY_FILE + TEMPLATE_EXTENSION,
                        context);
            }
            ++index;
        }
    }


    /**
     * Group test methods by class and sort alphabetically.
     */ 
    private SortedMap<IClass, List<ITestResult>> sortByTestClass(IResultMap results)
    {
        SortedMap<IClass, List<ITestResult>> sortedResults = new TreeMap<>(CLASS_COMPARATOR);
        for (ITestResult result : results.getAllResults())
        {
            List<ITestResult> resultsForClass = sortedResults.get(result.getTestClass());
            if (resultsForClass == null)
            {
                resultsForClass = new ArrayList<>();
                sortedResults.put(result.getTestClass(), resultsForClass);
            }
            int index = Collections.binarySearch(resultsForClass, result, RESULT_COMPARATOR);
            if (index < 0)
            {
                index = Math.abs(index + 1);
            }
            resultsForClass.add(index, result);
        }
        return sortedResults;
    }



    /**
     * Generate a groups list for each suite.
     * @param outputDirectory The target directory for the generated file(s).
     */
    private void createGroups(List<ISuite> suites,
                              File outputDirectory) throws IOException {
        int index = 1;
        for (ISuite suite : suites)
        {
            SortedMap<String, SortedSet<ITestNGMethod>> groups = sortGroups(suite.getMethodsByGroups());
            if (!groups.isEmpty())
            {
                VelocityContext context = createContext();
                context.put(SUITE_KEY, suite);
                context.put(GROUPS_KEY, groups);
                String fileName = String.format("suite%d_%s", index, GROUPS_FILE);
                generateFile(new File(outputDirectory, fileName),
                             GROUPS_FILE + TEMPLATE_EXTENSION,
                             context);                
            }
            ++index;
        }
    }


    /**
     * Generate a groups list for each suite.
     * @param outputDirectory The target directory for the generated file(s).
     */
    private void createLog(File outputDirectory, boolean onlyFailures) throws IOException {
        if (!Reporter.getOutput().isEmpty())
        {
            VelocityContext context = createContext();
            context.put(ONLY_FAILURES_KEY, onlyFailures);
            generateFile(new File(outputDirectory, OUTPUT_FILE),
                         OUTPUT_FILE + TEMPLATE_EXTENSION,
                         context);
        }
    }


    /**
     * Sorts groups alphabetically and also sorts methods within groups alphabetically
     * (class name first, then method name).  Also eliminates duplicate entries.
     */
    private SortedMap<String, SortedSet<ITestNGMethod>> sortGroups(Map<String, Collection<ITestNGMethod>> groups)
    {
        SortedMap<String, SortedSet<ITestNGMethod>> sortedGroups = new TreeMap<>();
        for (Map.Entry<String, Collection<ITestNGMethod>> entry : groups.entrySet())
        {
            SortedSet<ITestNGMethod> methods = new TreeSet<>(METHOD_COMPARATOR);
            methods.addAll(entry.getValue());
            sortedGroups.put(entry.getKey(), methods);
        }
        return sortedGroups;
    }


    /**
     * Reads the CSS and JavaScript files from the JAR file and writes them to
     * the output directory.
     * @param outputDirectory Where to put the resources.
     * @throws IOException If the resources can't be read or written.
     */
    private void copyResources(File outputDirectory) throws IOException
    {
        copyClasspathResource(outputDirectory, "reportng.css", "reportng.css");
        copyClasspathResource(outputDirectory, "reportng.js", "reportng.js");
        copyClasspathResource(outputDirectory, "sorttable.js", "sorttable.js");
        copyClasspathResource(outputDirectory, "jquery-1.4.3.min.js", "jquery-1.4.3.min.js");
        copyClasspathResource(outputDirectory, "jquery.fancybox-1.3.4.css", "jquery.fancybox-1.3.4.css");
        copyClasspathResource(outputDirectory, "jquery.fancybox-1.3.4.pack.js", "jquery.fancybox-1.3.4.pack.js");
        copyImage(outputDirectory, "blank.gif", "blank.gif");
        copyImage(outputDirectory, "fancybox-x.png", "fancybox-x.png");
        copyImage(outputDirectory, "fancybox-y.png", "fancybox-y.png");
        copyImage(outputDirectory, "fancy_close.png", "fancy_close.png");
        copyImage(outputDirectory, "fancy_nav_left.png", "fancy_nav_left.png");
        copyImage(outputDirectory, "fancy_nav_right.png", "fancy_nav_right.png");
        copyImage(outputDirectory, "fancybox.png", "fancybox.png");
        copyImage(outputDirectory, "timeStampOn.png", "timeStampOn.png");
        copyImage(outputDirectory, "timeStampOff.png", "timeStampOff.png");
        copyImage(outputDirectory, "showPassedOn.png", "showPassedOn.png");
        copyImage(outputDirectory, "showPassedOff.png", "showPassedOff.png");

        // If there is a custom stylesheet, copy that.
        File customStylesheet = META.getStylesheetPath();

        if (customStylesheet != null)
        {
            if (customStylesheet.exists())
            {
                copyFile(outputDirectory, customStylesheet, CUSTOM_STYLE_FILE);
            }
            else
            {
                // If not found, try to read the file as a resource on the classpath
                // useful when reportng is called by a jarred up library
                InputStream stream = ClassLoader.getSystemClassLoader().getResourceAsStream(customStylesheet.getPath());
                if (stream != null)
                {
                    copyStream(outputDirectory, stream, CUSTOM_STYLE_FILE);
                }
            }
        }
    }
}
