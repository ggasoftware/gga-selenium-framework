package com.epam.jditests.tests.complex.tableTests;

import com.ggasoftware.jdiuitest.web.selenium.elements.base.SelectElement;
import com.ggasoftware.jdiuitest.web.testng.asserter.Assert;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Natalia_Grebenshchik on 10/5/2015.
 */
public class HeadersTests extends InitTableTests {

    @Test
    public void verifyTableHeaders() {
        String[] expectedColumnHeaders = new String[]{"Type", "Now", "Plans"};
        String[] actualColumnHeaders = support().headers();

        Assert.isTrue(Arrays.equals(expectedColumnHeaders, actualColumnHeaders),
                String.format("Expected list of headers is %s, but was %s", Arrays.toString(expectedColumnHeaders), Arrays.toString(actualColumnHeaders)));
    }

    @Test
    public void verifyColumnsHeader() {
        List<String> expectedColumnHeaders, actualColumnHeaders;

        expectedColumnHeaders = Arrays.asList("Type", "Now", "Plans");
        actualColumnHeaders = Arrays.asList(support().columns().headers());

        Assert.areEquals(expectedColumnHeaders, actualColumnHeaders,
                String.format("Expected list of headers is %s, but was %s", expectedColumnHeaders, expectedColumnHeaders));

    }

    @Test
    public void verifyHeaderAsSelectedElement() {
        String headerName = "Now";

        SelectElement selectElement = support().header(headerName);

        Assert.areEquals(selectElement.getValue(), headerName,
                String.format("Expected '%s' header name, but was '%s'", headerName, selectElement.getValue()));
        Assert.isTrue(!selectElement.isSelected(), "Element expectde to be not selected< but was selected");
    }

    @Test
    public void verifyColumnsHeadersAsTest() {
        String[] expectedColumnHeaders = new String[]{"Type", "Now", "Plans"};
        String[] actualColumnHeaders = support().columns().headers();

        Assert.isTrue(Arrays.equals(expectedColumnHeaders, actualColumnHeaders),
                String.format("Expected list of headers is %s, but was %s", Arrays.toString(expectedColumnHeaders), Arrays.toString(actualColumnHeaders)));
    }

    @Test
    public void verifyRowsHeadersAsTest() {
        String[] expectedColumnHeaders = new String[]{"1", "2", "3", "4", "5", "6"};
        String[] actualColumnHeaders = support().rows().headers();

        Assert.isTrue(Arrays.equals(expectedColumnHeaders, actualColumnHeaders),
                String.format("Expected list of headers is %s, but was %s", Arrays.toString(expectedColumnHeaders), Arrays.toString(actualColumnHeaders)));
    }

}
