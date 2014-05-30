package com.ggasoftware.uitest.demo;

import cucumber.api.CucumberOptions;
import cucumber.api.testng.AbstractTestNGCucumberTests;

/**
 * Class for run cucumber tests.
 *
 * @author Belousov Andrey
 */
@CucumberOptions(format = "html:target/cucumber")
public class RunCukesTest extends AbstractTestNGCucumberTests {
}
