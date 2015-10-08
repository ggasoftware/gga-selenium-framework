package com.epam.jdi_tests.dataproviders;

import org.testng.annotations.DataProvider;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Objects;

/**
 * Created by Natalia_Grebenshchik on 10/8/2015.
 */
public class TableDP {

    @DataProvider(name = "hasHeadersSelector")
    public static Object [][] hasHeadersSelector(){
        return new Object [][]{
               {true,true,6,2,Arrays.asList("Drivers","Test Runner","Asserter","Logger","Reporter","BDD/DSL"),Arrays.asList("Type","Now","Plans"),"Selenium Custom"},
               {true,false,6,3,Arrays.asList("Drivers","Test Runner","Asserter","Logger","Reporter","BDD/DSL"),Arrays.asList("1","2","3"),"Now"},
               {false,false,7,3,Arrays.asList("1","2","3","4","5","6"),Arrays.asList("1","2","3"),"Type"},
               {false,true,7,2,Arrays.asList("1","2","3","4","5","6"),Arrays.asList("Type","Now","Plans"),"Drivers"}};
    }
}
