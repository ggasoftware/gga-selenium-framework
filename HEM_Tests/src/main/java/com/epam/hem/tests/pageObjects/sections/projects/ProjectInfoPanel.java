package com.epam.hem.tests.pageObjects.sections.projects;

import com.epam.hem.tests.enums.ProjectDetails;
import com.epam.hem.tests.pageObjects.sections.base.InfoPanel;

/**
 * Created by Pavel_Shcherbakov1 on 2015-10-08
 */
public class ProjectInfoPanel extends InfoPanel {

    public String getDetail(String field, boolean onlyValue) {
        openDetailsTab();
        int index = 0;

        switch (field) {
            case "Name" : index = ProjectDetails.Name.value; break;
            case "Creator" : index = ProjectDetails.Creator.value; break;
            case "Owner" : index = ProjectDetails.Owner.value; break;
            case "CreationDate" : index = ProjectDetails.CreationDate.value; break;
            case "ModifiedDate" : index = ProjectDetails.ModifiedDate.value; break;
            case "ModelName" : index = ProjectDetails.ModelName.value; break;
            case "ScenariosNumber" : index = ProjectDetails.ScenariosNumber.value; break;
            case "AnalysesNumber" : index = ProjectDetails.AnalysesNumber.value; break;
        }

        String textLabel = textLabels.getText(index);

        if (onlyValue)
            return textLabel.substring(textLabel.indexOf(":") + 2);
        else
            return textLabel;
    }

    public String getNotes() {
        openNotesTab();
        return textLabels.getText(0);
    }
}
