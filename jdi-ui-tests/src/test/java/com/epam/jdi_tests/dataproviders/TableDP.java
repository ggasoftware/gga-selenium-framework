package com.epam.jdi_tests.dataproviders;

import org.testng.annotations.DataProvider;

import static com.epam.jdi_tests.page_objects.EpamJDISite.supportPage;
import static java.util.Arrays.asList;

/**
 * Created by Natalia_Grebenshchik on 10/8/2015.
 */
public class TableDP {

    @DataProvider(name = "hasHeadersSelector")
    public static Object [][] hasHeadersSelector(){
        return new Object [][]{
                {supportPage.tableWithBothHeaders,6,2,
                        asList("Drivers", "Test Runner", "Asserter", "Logger", "Reporter", "BDD/DSL"),
                        asList("Now", "Plans")},
                {supportPage.tableWithRowsHeader,6,2,
                        asList("Drivers", "Test Runner", "Asserter", "Logger", "Reporter", "BDD/DSL"),
                        asList("1", "2")},
                {supportPage.tableWithoutHeaders,6,3,
                        asList("1", "2", "3", "4", "5", "6"),
                        asList("1", "2", "3")},
                {supportPage.tableWithColumnHeader,6,3,
                        asList("1", "2", "3", "4", "5", "6"),
                        asList("Type", "Now", "Plans")}};
    }
}
