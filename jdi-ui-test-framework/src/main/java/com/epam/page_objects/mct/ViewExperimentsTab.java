package com.epam.page_objects.mct;

import com.epam.page_objects.entities.Experiment;
import com.epam.page_objects.enums.MCTTabs;
import com.epam.ui_test_framework.elements.complex.table.Table;
import com.epam.ui_test_framework.elements.interfaces.base.IElement;
import com.epam.ui_test_framework.elements.interfaces.complex.ITabs;
import com.epam.ui_test_framework.elements.interfaces.simple.IButton;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;
import ru.yandex.qatools.allure.annotations.Step;

import static com.epam.page_objects.enums.MCTTabs.*;
import static com.epam.ui_test_framework.elements.BaseElement.InitElements;
import static com.epam.ui_test_framework.utils.usefulUtils.TryCatchUtil.ignoreException;
import static java.lang.Thread.sleep;
import static org.testng.Assert.assertTrue;

/**
 * Created by Maksim_Palchevskii on 6/5/2015.
 */
public class ViewExperimentsTab {
    public static ViewExperimentsTab viewExperimentsTab = InitElements(new ViewExperimentsTab());

    public Table grid = new Table(By.xpath("//div[@data-role=\"experimentgrid\"]"));
    @FindBy(xpath = "//a[@class='k-link' and contains(text(), '%s')]")
    private ITabs<MCTTabs> mctTabs;
    @FindBy(xpath = "//span[@class='k-link' and contains(text(), 'View Experiment')]")
    private IButton viewExpButton;
    @FindBy(xpath = "//span[@data-bind='text: showAllButtonName']")
    private IButton browseAllExperimentsButton;
    @FindBy(xpath = "//tbody[@role='rowgroup']")
    private IElement gridState;

    @Step
    public void checkPublication(Experiment experiment) {
        viewExpButton.click();
        assertTrue(mctTabs.waitSelected(SEARCH_EXPERIMENTS));
        mctTabs.select(SEARCH_RESULTS);
        assertTrue(grid.isValueInColumn("Experiment Name", experiment.name));
    }
}