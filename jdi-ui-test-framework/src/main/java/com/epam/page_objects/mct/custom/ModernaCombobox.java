package com.epam.page_objects.mct.custom;

import com.epam.ui_test_framework.elements.complex.ComboBox;
import com.epam.ui_test_framework.elements.common.Input;
import org.openqa.selenium.By;

import static com.epam.page_objects.mct.NewExperimentTab.*;

/**
 * Created by Roman_Iovlev on 7/10/2015.
 */
public class ModernaCombobox<TEnum extends Enum> extends ComboBox<TEnum> {
    public ModernaCombobox() { super(); }
    public ModernaCombobox(By valueLocator) {
        super(valueLocator);
    }
    public ModernaCombobox(By valueLocator, By optionsNamesLocatorTemplate) {
        super(valueLocator, optionsNamesLocatorTemplate);
    }
    public ModernaCombobox(By valueLocator, By optionsNamesLocatorTemplate, By allOptionsNamesLocator) {
        super(valueLocator, optionsNamesLocatorTemplate, allOptionsNamesLocator);
    }
    @Override
    protected Input createInputAction(By valueLocator) {
        return new Input(valueLocator) {
            @Override
            protected void inputAction(String name) {
                super.inputAction(name);
                newExperimentTab.newExperimentHeader.click();
            }
        };
    }

}
