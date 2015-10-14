package com.epam.hem.tests.pageObjects.sections.projects;

import com.epam.hem.tests.enums.ModelProperties;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.complex.TextList;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.composite.Section;
import org.openqa.selenium.support.FindBy;

/**
 * Created by Pavel_Shcherbakov1 on 2015-10-12
 */
public class ModelTileItem extends Section {

    @FindBy(css = "small")
    protected TextList<ModelProperties> textLabels;

    public String getProperty(String field, boolean onlyValue) {

        int index = 0;

        switch (field) {
            case "Name" : index = ModelProperties.Name.value; break;
            case "Type" : index = ModelProperties.Type.value; break;
            case "Status" : index = ModelProperties.Status.value; break;
            case "Creator" : index = ModelProperties.Creator.value; break;
            case "CreationDate" : index = ModelProperties.CreationDate.value; break;
        }

        String textLabel = textLabels.getText(index);

        if (onlyValue)
            return textLabel.substring(textLabel.indexOf(":") + 2);
        else
            return textLabel;
    }
}
