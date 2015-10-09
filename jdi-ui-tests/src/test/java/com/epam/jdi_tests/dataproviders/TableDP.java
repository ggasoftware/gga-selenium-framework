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
