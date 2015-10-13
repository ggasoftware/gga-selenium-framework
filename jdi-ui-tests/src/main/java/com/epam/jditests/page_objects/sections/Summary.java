package com.epam.jditests.page_objects.sections;

import com.epam.jditests.enums.Odds;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.complex.*;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.composite.Section;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.interfaces.common.IButton;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Created by Roman_Iovlev on 9/15/2015.
 */
public class Summary extends Section {
    @FindBy(css="#odds-selector p")
    public Selector<Odds> odds;
    @FindBy(css="#odds-selector p")
    public Selector<Odds> oddsWithSelected = new Selector<Odds>() {
        @Override
        protected boolean isSelectedAction(WebElement el) {
            return el.findElement(By.tagName("input")).getAttribute("checked") != null;
        }
    };
    @FindBy(css="#odds-selector p")
    public RadioButtons<Odds> oddsR;
    @FindBy(css="#odds-selector p")
    public RadioButtons<Odds> oddsRWithSelected = new RadioButtons<Odds>() {
        @Override
        protected boolean isSelectedAction(WebElement el) {
            return el.findElement(By.tagName("input")).getAttribute("checked") != null;
        }
    };
    @FindBy(css="#even-selector p")
    public Selector even;
    @FindBy(id="calculate-button")
    public IButton calculate;

}
