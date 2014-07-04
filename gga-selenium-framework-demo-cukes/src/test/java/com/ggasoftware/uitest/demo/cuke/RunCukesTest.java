package com.ggasoftware.uitest.demo.cuke;

import com.ggasoftware.uitest.cuke.CukeBuilder;
import org.testng.annotations.Factory;

import java.io.IOException;
import java.util.ArrayList;

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
