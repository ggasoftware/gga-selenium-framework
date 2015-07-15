package com.epam.page_objects.mct.popup;

import org.openqa.selenium.support.FindBy;

import static com.epam.ui_test_framework.elements.BaseElement.InitElements;

/**
 * Created by Roman_Iovlev on 7/14/2015.
 */
public class Popups {
    public static Popups popups = InitElements(new Popups());

    @FindBy(css ="[data-role=confirmationdialog]")
    private MCTInfo mctInfo;
    public static MCTInfo mctInfoPopup() {return popups.mctInfo; }

    private LCMCInfoPopup lcmLCMCInfo;
    public static LCMCInfoPopup lcmCInfoPopup() {return popups.lcmLCMCInfo; }

    private AmplexRedInfoPopup amplexRedInfo;
    public static AmplexRedInfoPopup amplexRedInfoPopup() {return popups.amplexRedInfo; }
}
