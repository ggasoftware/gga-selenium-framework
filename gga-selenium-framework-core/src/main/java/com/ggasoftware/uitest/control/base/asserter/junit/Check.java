package com.ggasoftware.uitest.control.base.asserter.junit;

import com.ggasoftware.uitest.control.base.asserter.BaseChecker;
import org.junit.Assert;

/**
 * Created by Roman_Iovlev on 6/9/2015.
 */
public class Check extends BaseChecker {
    public Check() {
        this("");
    }

    public Check(String checkMessage) {
        super(checkMessage);
        setThrowFail(Assert::fail);
    }
}
