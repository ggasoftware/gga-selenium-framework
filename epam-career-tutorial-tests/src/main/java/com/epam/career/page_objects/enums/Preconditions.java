package com.epam.career.page_objects.enums;

import com.ggasoftware.jdiuitests.core.utils.linqInterfaces.JAction;
import com.ggasoftware.jdiuitests.core.utils.linqInterfaces.JFuncT;
import com.ggasoftware.jdiuitests.core.utils.preconditions.IPreconditions;

/**
 * Created by 12345 on 03.06.2015.
 */
public enum Preconditions implements IPreconditions {
    HOME_PAGE("");

    private JFuncT<Boolean> checkAction;
    public JFuncT<Boolean> checkAction() { return checkAction; }
    private JAction moveToAction;
    public JAction moveToAction() { return moveToAction; }

    Preconditions(JFuncT<Boolean> checkAction, JAction moveToAction) {
        this.checkAction = checkAction;
        this.moveToAction = moveToAction;
    }
    Preconditions(String uri) {
        this.checkAction = () -> checkUrl(uri);
        this.moveToAction = () -> openUri(uri);
    }

}
