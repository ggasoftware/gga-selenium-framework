package com.epam.jdi_tests.dataproviders;

import org.testng.annotations.DataProvider;

import java.util.Arrays;

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
    @DataProvider(name = "setColumnsCount")
    public static Object [][] setColumnsCount(){
        return new Object [][]{
               {-1,0,Arrays.asList(),""},
               {0,0,Arrays.asList(),""},
               {2,2,Arrays.asList("Type","Now"),"Drivers"},
               {3,3,Arrays.asList("Type","Now","Plans"),"Drivers"},
               {10,10,Arrays.asList("Type","Now","Plans","4"),"Drivers"}};
    }
    @DataProvider(name = "setRowsCount")
    public static Object [][] setRowsCount(){
        return new Object [][]{
               {-1,0,Arrays.asList(""),""},
               {0,0,Arrays.asList(),""},
               {2,2,Arrays.asList("1","2"),"Drivers"},
               {6,6,Arrays.asList("1","2","3","4","5","6"),"Drivers"},
               {10,10,Arrays.asList("1","2","3","4","5","6","7","8","9","10"),"Drivers"}};
    }
    @DataProvider(name = "setColumnHeaders")
    public  static  Object [][] setColumnHeaders(){
        return new Object[][]{
                {Arrays.asList(""),""},
                {Arrays.asList("h1"),"Drivers"},
                {Arrays.asList("h1","h2","h3"),"Drivers"},
                {Arrays.asList("h1","h2","h3","h4","h5","h6"),"Drivers"},
        };
    }
}
