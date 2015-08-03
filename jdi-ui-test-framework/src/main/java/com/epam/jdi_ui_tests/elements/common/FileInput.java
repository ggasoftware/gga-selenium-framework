package com.epam.jdi_ui_tests.elements.common;

import com.epam.jdi_ui_tests.elements.interfaces.common.IFileInput;
import org.openqa.selenium.By;

/**
 * Created by Roman_Iovlev on 7/10/2015.
 */
public class FileInput extends TextField implements IFileInput {
    public FileInput() { super(); }
    public FileInput(By byLocator) { super(byLocator); }

}
