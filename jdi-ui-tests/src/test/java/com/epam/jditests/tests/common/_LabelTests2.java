package com.epam.jditests.tests.common;

import com.epam.jditests.InitTests;
import org.testng.annotations.Factory;

import static com.epam.jditests.enums.Preconditions.METALS_AND_COLORS_PAGE;
import static com.epam.jditests.pageobjects.EpamJDISite.metalsColorsPage;

/**
 * Created by Roman_Iovlev on 10/13/2015.
 */
public class _LabelTests2 extends InitTests {
    @Factory
    public Object[] buttonTests() {
        return new Object[] {
            new _TextTests2(() -> metalsColorsPage.calculateButton, METALS_AND_COLORS_PAGE, "CALCULATE")
        };
    }
}
