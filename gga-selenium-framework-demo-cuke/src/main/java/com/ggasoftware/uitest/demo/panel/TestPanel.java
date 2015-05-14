package com.ggasoftware.uitest.demo.panel;

import com.ggasoftware.uitest.control.Button;
import com.ggasoftware.uitest.control.Elements;
import com.ggasoftware.uitest.control.Image;
import com.ggasoftware.uitest.control.Input;
import com.ggasoftware.uitest.panel.BasePanel;

/**
 * Main panel
 *
 * @author Belousov Andrey
 */
public class TestPanel extends BasePanel<TestPanel> {

    private static TestPanel panel;

    public final Image<TestPanel> logo = new Image<>("logo", getProperty("logo.locator"), this);
    public final Input<TestPanel> textField = new Input<>("textField", getProperty("textField.locator"), this);
    public final Button<TestPanel> searchBtn = new Button<>("searchBtn", getProperty("searchBtn.locator"), this);
    public final Button<TestPanel> searchBtn2 = new Button<>("searchBtn2", getProperty("searchBtn2.locator"), this);
    public final Elements<TestPanel> resultsLinks = new Elements<>("resultsLinks", getProperty("resultsLinks.locator"), this);

    public static TestPanel get() {
        if (panel == null) {
            panel = new TestPanel();
        }
        return panel;
    }

}
