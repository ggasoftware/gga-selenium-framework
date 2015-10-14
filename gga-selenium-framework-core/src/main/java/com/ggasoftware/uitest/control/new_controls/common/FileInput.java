package com.ggasoftware.uitest.control.new_controls.common;

import com.ggasoftware.uitest.control.Input;
import com.ggasoftware.uitest.control.interfaces.common.IFileInput;
import org.openqa.selenium.By;

/**
 * Created by Roman_Iovlev on 7/10/2015.
 */
public class FileInput extends Input implements IFileInput {
    public FileInput() {
        super();
    }

    public FileInput(By byLocator) {
        super(byLocator);
    }

}
