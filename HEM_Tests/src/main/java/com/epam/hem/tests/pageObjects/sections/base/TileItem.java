package com.epam.hem.tests.pageObjects.sections.base;

import com.epam.hem.tests.enums.ProjectProperties;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.composite.Section;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.interfaces.common.IButton;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.interfaces.complex.ITextList;
import org.openqa.selenium.support.FindBy;

/**
 * Created by Pavel_Shcherbakov1 on 2015-10-12
 */
public class TileItem extends Section {

    @FindBy(css = "small")
    protected ITextList<ProjectProperties> textLabels;
    @FindBy(css = "i[tooltip*='Edit']")
    private IButton editButton;
    @FindBy(css = "i[tooltip*='Clone']")
    private IButton cloneButton;

    public void openEditForm() {
        editButton.click();
    }

    public void openCloneForm() {
        cloneButton.click();
    }
}
