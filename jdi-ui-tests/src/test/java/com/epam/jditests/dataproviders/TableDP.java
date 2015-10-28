package com.epam.jditests.dataproviders;

import com.ggasoftware.jdiuitests.implementation.selenium.elements.common.Button;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.common.Image;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.common.Label;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.common.Link;
import org.testng.annotations.DataProvider;

import java.util.Arrays;
import java.util.Objects;

import static java.util.Arrays.asList;

/**
 * Created by Natalia_Grebenshchik on 10/8/2015.
 */
public class TableDP {

    @DataProvider(name = "hasHeadersSelector")
    public static Object[][] hasHeadersSelector(){
        return new Object [][]{
                {true, true,2,6,
                        asList("Now","Plans"),
                        asList("Drivers", "Test Runner", "Asserter", "Logger", "Reporter", "BDD/DSL"),"Selenium Custom"},
                {true, false,3,6,
                        asList("Type","Now","Plans"),
                        asList("1", "2", "3", "4", "5", "6"),"Drivers"},
                {false, false,3,6,
                        asList("1", "2", "3"),
                        asList("1", "2", "3", "4", "5", "6"),"Drivers"},
                {false, true,2,6,
                        asList("1", "2"),
                        asList("Drivers", "Test Runner", "Asserter", "Logger", "Reporter", "BDD/DSL"),"Selenium Custom"}};
    }

    @DataProvider(name = "setColumnsCount")
    public static Object[][] setColumnsCount(){
        return new Object [][]{
               {2,asList("Type","Now"),"Drivers"},
               {3,asList("Type","Now","Plans"),"Drivers"},
               {10,asList("Type","Now","Plans"),"Drivers"}};
    }

    @DataProvider(name = "setColumnsCountNegative")
    public static Object[][] setColumnsCountNegarive(){
        return new Object [][]{
               {-1,0,Arrays.asList(),""},
               {0,0,Arrays.asList(),""},
               {10,10,Arrays.asList("Type","Now","Plans"),"Drivers"}};
    }

    @DataProvider(name = "setRowsCount")
    public static Object[][] setRowsCount(){
        return new Object [][]{
               {2,asList("1","2"),"Drivers"},
               {6,asList("1","2","3","4","5","6"),"Drivers"},
               {10,asList("1","2","3","4","5","6","7","8","9","10"),"Drivers"}};
    }
    @DataProvider(name = "setRowsCountNegative")
    public static Object[][] setRowsCountNegative(){
        return new Object [][]{
               {-1,0,Arrays.asList(""),""},
               {0,0,Arrays.asList(),""},
               {2,2,Arrays.asList("1","2"),"Drivers"},
               {6,6,Arrays.asList("1","2","3","4","5","6"),"Drivers"},
               {10,10,Arrays.asList("1","2","3","4","5","6"),"Drivers"}};
    }

    @DataProvider(name = "setColumnHeaders")
    public static Object[][] setColumnHeaders(){
        return new Object[][]{
                {asList("h1"),asList("r1","r2"),"Drivers"},
                {asList("h1","h2","h3"),asList("r1","r2","r3","r4","r5","r6"),"Drivers"},
                {asList("h1","h2","h3","h4","h5","h6"),asList("r1","r2","r3","r4","r5","r6","r7","r8"),"Drivers"},
        };
    }

    @DataProvider(name = "setCellValue")
    public static Object[][] setCellValue(){
        return new Object[][]{
                {1,1, "Drivers"},
                {3,6,"Cucumber, Jbehave, Thucydides, SpecFlow"}
        };
    }

    @DataProvider(name="setCellPosition")
    public static Object[][] setCellPosition(){
        return new Object[][]{
                {"MSTest, NUnit, Epam",3,2},
                {"Custom",2,6}
        };
    }

    @DataProvider(name="setValueAndColumn")
    public static Object[][] setValueAndColumn(){
        return new Object[][]{
                {"MSTest, NUnit, Epam","Plans",3,2},
                {"MSTest, NUnit, Epam",3,3,2},
                {"Custom",2,2,6},
                {"Custom","Now",2,6}
        };
    }

    @DataProvider(name = "tableCellsClickableContent")
    public static Object[][] tableCellsClickableContent(){
        return new Object[][] {
                {3,2,Button.class,"([0-9]{2}:){2}[0-9]{2} Name: button was clicked"},
                {3,2,Link.class,"([0-9]{2}:){2}[0-9]{2} Name: date selected selected"},
                {3,2,Image.class,"([0-9]{2}:){2}[0-9]{2} Name: link ws clicked was clicked"},
                {3,2,Label.class,"([0-9]{2}:){2}[0-9]{2} Name: label ws clicked was clicked"}
        };
    }

    @DataProvider(name="cellIndexes")
    public static Object[][] cellIndexes(){
        return new Object[][]{
                {4,1},
                {-1,1},
                {0,1},
                {1,10},
                {1,-1},
                {1,0}
        };
    }

    @DataProvider(name="indexes")
    public static Object[][] indexes(){
        return new Object[][]{
                {-10},
                {0},
                {10}
        };
    }

}
