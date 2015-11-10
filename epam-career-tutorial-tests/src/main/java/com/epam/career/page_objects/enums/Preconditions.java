package com.epam.career.page_objects.enums;

import com.ggasoftware.jdiuitests.core.utils.linqinterfaces.JAction;
import com.ggasoftware.jdiuitests.core.utils.linqinterfaces.JFuncT;
import com.ggasoftware.jdiuitests.core.utils.preconditions.IPreconditions;

/**
 * Created by 12345 on 03.06.2015.
 */
public enum Preconditions implements IPreconditions {
    HOME_PAGE("");

    private JFuncT<Boolean> checkAction;
    private JAction moveToAction;

    Preconditions(JFuncT<Boolean> checkAction, JAction moveToAction) {
        this.checkAction = checkAction;
        this.moveToAction = moveToAction;
    }

    Preconditions(String uri) {
        this.checkAction = () -> checkUrl(uri);
        this.moveToAction = () -> openUri(uri);
    }

    public JFuncT<Boolean> checkAction() {
        return checkAction;
    }

    public JAction moveToAction() {
        return moveToAction;
    }

}
