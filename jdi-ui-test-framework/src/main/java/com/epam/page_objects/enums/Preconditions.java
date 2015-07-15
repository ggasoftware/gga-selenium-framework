package com.epam.page_objects.enums;

import com.epam.ui_test_framework.utils.common.Timer;
import com.epam.ui_test_framework.utils.linqInterfaces.JAction;
import com.epam.ui_test_framework.utils.linqInterfaces.JFuncT;

import static com.epam.page_objects.mct.NewExperimentTab.newExperimentTab;
import static com.epam.page_objects.mct.login.Portal.modernaUrl;
import static com.epam.ui_test_framework.utils.settings.FrameworkSettings.seleniumFactory;

/**
 * Created by 12345 on 03.06.2015.
 */
public enum Preconditions {
    CHEMISTRY("chemistry/#smth"),
    INVENTORY("inventory"),
    MCT("mct"),
    MCT_New_Experiment_Define_Experiment(() -> false,
            () -> {
                if (!checkUrl("mct/#NewExperiment//DefineExperiment"))
                    openUri("mct/#NewExperiment//DefineExperiment");
                newExperimentTab.prepeareNewExperiment();
            });

    public JFuncT<Boolean> checkAction;
    public JAction moveToAction;

    Preconditions(JFuncT<Boolean> checkAction, JAction moveToAction) {
        this.checkAction = checkAction;
        this.moveToAction = moveToAction;
    }
    Preconditions(String uri) {
        this(() -> checkUrl(uri), () -> openUri(uri));
    }
    private static boolean checkUrl(String uri) {
        return seleniumFactory.getDriver().getCurrentUrl().contains(uri);
    }
    private static void openUri(String uri) {
        String url = modernaUrl + uri;
        Timer timer = new Timer(1000, 100);
        seleniumFactory.getDriver().navigate().to(url);
        if (!timer.timeoutPassed())
            seleniumFactory.getDriver().navigate().to(url);
    }
}
