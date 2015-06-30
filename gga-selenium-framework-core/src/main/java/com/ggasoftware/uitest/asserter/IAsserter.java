package com.ggasoftware.uitest.asserter;

import com.ggasoftware.uitest.utils.linqInterfaces.JAction;

/**
 * Created by Roman_Iovlev on 6/9/2015.
 */
public interface IAsserter {
    Exception exception(String message);
    void silentException(JAction action);
}
