package com.epam.career.page_objects.entities;

import com.epam.career.page_objects.enums.JobCategories;

import static com.epam.career.page_objects.enums.JobCategories.QA;

/**
 * Created by Roman_Iovlev on 10/22/2015.
 */
public class JobSearchFilter {
    public String keywords = "QA";
    public JobCategories category = QA;
    //public String location;
}
