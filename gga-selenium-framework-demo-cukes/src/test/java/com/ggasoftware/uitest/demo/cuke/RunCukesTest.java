package com.ggasoftware.uitest.demo.cuke;

import com.ggasoftware.uitest.cuke.CukeBuilder;
import com.ggasoftware.uitest.utils.ReporterNGExt;
import cucumber.api.Scenario;
import cucumber.api.java.Before;
import org.testng.annotations.Factory;

import java.io.IOException;

/**
 * Class for run cucumber features scenarios.
 *
 * @author Belousov Andrey
 */
public class RunCukesTest {

    @Factory
    public Object[] Run_Cukes() throws IOException {
        return new CukeBuilder().Run("@demo");
    }

}
