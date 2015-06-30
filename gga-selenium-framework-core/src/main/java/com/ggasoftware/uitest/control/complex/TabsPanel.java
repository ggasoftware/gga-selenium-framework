package com.ggasoftware.uitest.control.complex;

import com.ggasoftware.uitest.control.complex.table.ClickableText;
import org.openqa.selenium.By;

import static com.ggasoftware.uitest.utils.common.LinqUtils.first;

/**
 * Created by 12345 on 04.06.2015.
 */
public class TabsPanel<T extends Enum> extends Selector<T> {
    public TabsPanel() { }
    public TabsPanel(String name, By byLocator) { super(name, byLocator); }

    @Override
    protected String isSelectedAction() {
        ClickableText firstElement = first(getAllElementsAction(), cl -> cl.getWebElement().getAttribute("class").equals("k-state-active"));
        return (firstElement != null) ? firstElement.getName() : null;
    }

}
