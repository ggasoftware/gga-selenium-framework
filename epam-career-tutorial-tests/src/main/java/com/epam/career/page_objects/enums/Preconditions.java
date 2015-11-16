package com.epam.career.page_objects.enums;

import com.ggasoftware.jdiuitest.core.utils.linqinterfaces.JAction;
import com.ggasoftware.jdiuitest.core.utils.linqinterfaces.JFuncT;
import com.ggasoftware.jdiuitest.web.selenium.preconditions.IPreconditions;

import static com.ggasoftware.jdiuitest.web.selenium.preconditions.IPreconditions.*;
import static com.ggasoftware.jdiuitest.web.selenium.preconditions.PreconditionsState.alwaysMoveToCondition;

/**
 * Created by 12345 on 03.06.2015.
 */
public enum Preconditions implements IPreconditions {
    HOME_PAGE("");

    Preconditions(JFuncT<Boolean> checkAction, JAction moveToAction) {
        this.checkAction = checkAction;
        this.moveToAction = moveToAction;
        alwaysMoveToCondition = true;
    }

    Preconditions(String uri) {
        this(() -> checkUrl(uri), () -> openUri(uri));
    }

    public JFuncT<Boolean> checkAction;
    public JAction moveToAction;
    public JFuncT<Boolean> checkAction() { return checkAction; }
    public JAction moveToAction() { return moveToAction; }

}
