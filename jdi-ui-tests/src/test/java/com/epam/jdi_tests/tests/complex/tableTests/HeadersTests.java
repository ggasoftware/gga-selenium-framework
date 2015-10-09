package com.epam.jdi_tests.tests.complex.tableTests;

import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.base.SelectElement;
import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.complex.table.Table;
import com.ggasoftware.jdi_ui_tests.implementation.testng.asserter.Assert;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Natalia_Grebenshchik on 10/5/2015.
 */
public class HeadersTests extends InitTableTests{
    //TODO failed
    /*
    @Test
    public void verifyTableHeaders(){
        String [] expectedColumnHeaders = (String[]) Arrays.asList("Type", "Now", "Plans").toArray();
        String [] actualColumnHeaders = support().headers();

        Assert.isTrue(Arrays.equals(expectedColumnHeaders, actualColumnHeaders),
                String.format("Expected list of headers is %s, but was %s", Arrays.toString(expectedColumnHeaders), Arrays.toString(actualColumnHeaders)));
    }
    */
    @Test
    public void verifyColumnsHeader (){
        List<String> expectedColumnHeaders, actualColumnHeaders;

        expectedColumnHeaders = Arrays.asList("Type", "Now", "Plans");
        actualColumnHeaders = support().header().select((key, value) -> key);

        Assert.areEquals(expectedColumnHeaders, actualColumnHeaders,
                String.format("Expected list of headers is %s, but was %s", expectedColumnHeaders, expectedColumnHeaders));

    }

    @Test
    public void verifyHeaderAsSelectedElement(){
        String headerName = "Now";

        SelectElement selectElement = support().header(headerName);

        Assert.areEquals(selectElement.getValue(), headerName,
                String.format("Expected '%s' header name, but was '%s'", headerName, selectElement.getValue()));
        Assert.isTrue(!selectElement.isSelected(), "Element expectde to be not selected< but was selected");
    }
    //TODO failed
    /*
    @Test
    public void verifyColumnsHeadersAsTest(){
        String [] expectedColumnHeaders = new String[] {"Type", "Now", "Plans"};
        String [] actualColumnHeaders = support().columns().headers();

        Assert.isTrue(Arrays.equals(expectedColumnHeaders, actualColumnHeaders),
                String.format("Expected list of headers is %s, but was %s", Arrays.toString(expectedColumnHeaders), Arrays.toString(actualColumnHeaders)));
    }

    @Test
    public void verifyRowsHeadersAsTest(){
        String [] expectedColumnHeaders = (String[]) Arrays.asList("1", "2", "3", "4", "5", "6").toArray();
        String [] actualColumnHeaders = support().rows().headers();

        Assert.isTrue(Arrays.equals(expectedColumnHeaders, actualColumnHeaders),
                String.format("Expected list of headers is %s, but was %s", Arrays.toString(expectedColumnHeaders), Arrays.toString(actualColumnHeaders)));
    }
*/
    @Test
    public void setColumnHeaders(){
        List<String> newHeaders = Arrays.asList("h1","h2","h3");
        ((Table) support()).setColumnHeaders((String[]) newHeaders.toArray());
        List<String> actualHeaders = Arrays.asList(support().headers());

        Assert.areEquals(newHeaders, actualHeaders,
                String.format("Wrong columns headers returned, expected %s, but was %s", newHeaders, actualHeaders));
    }
    @Test
    public void setRowHeaders(){
        List<String> newHeaders = Arrays.asList("h1","h2","h3","h4","h5", "h6");
        ((Table) support()).setRowHeaders((String[]) newHeaders.toArray());
        List<String> actualHeaders = Arrays.asList(support().rows().headers());

        Assert.areEquals(newHeaders, actualHeaders,
                String.format("Wrong rows headers returned, expected %s, but was %s", newHeaders, actualHeaders));
    }
}
