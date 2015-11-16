package com.epam.career.page_objects.site.pages;

import com.epam.career.page_objects.enums.JobListHeaders;
import com.epam.career.page_objects.site.sections.JobFilter;
import com.ggasoftware.jdiuitest.core.utils.map.MapArray;
import com.ggasoftware.jdiuitest.web.selenium.elements.complex.table.Table;
import com.ggasoftware.jdiuitest.web.selenium.elements.complex.table.interfaces.ICell;
import com.ggasoftware.jdiuitest.web.selenium.elements.complex.table.interfaces.ITable;
import com.ggasoftware.jdiuitest.web.selenium.elements.composite.WebPage;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

import static com.epam.career.page_objects.enums.JobListHeaders.APPLY;
import static com.epam.career.page_objects.enums.JobListHeaders.JOB_NAME;
import static com.ggasoftware.jdiuitest.web.selenium.elements.complex.table.Column.column;

/**
 * Created by Roman_Iovlev on 10/22/2015.
 */
public class JobListingPage extends WebPage {
    @FindBy(className = "career-list-header")
    public JobFilter jobFilter;

    @FindBy(className = "search-result-list")
    public ITable jobsList = new Table(null,
            By.xpath(".//li[%s]//div"),
            By.xpath(".//li//div[%s]"))
            .setColumnHeaders(JobListHeaders.class);

    public void getJobRowByName(String jobName) {
        MapArray<String, ICell> row = jobsList.row(jobName, column(JOB_NAME.toString()));
        row.get(APPLY.toString()).select();
    }
}
