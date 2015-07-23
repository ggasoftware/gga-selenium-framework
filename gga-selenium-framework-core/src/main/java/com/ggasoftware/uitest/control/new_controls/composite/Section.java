package com.ggasoftware.uitest.control.new_controls.composite;

import com.ggasoftware.uitest.control.Element;
import com.ggasoftware.uitest.control.interfaces.base.IComposite;
import org.openqa.selenium.By;

/**
 * Created by Roman_Iovlev on 7/10/2015.
 */
public class Section extends Element implements IComposite {
    public Section() { super(); }
    public Section(By byLocator) { super(byLocator); }
}
