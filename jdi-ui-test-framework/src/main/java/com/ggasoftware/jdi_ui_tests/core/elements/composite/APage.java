package com.ggasoftware.jdi_ui_tests.core.elements.composite;

import com.ggasoftware.jdi_ui_tests.core.elements.base.ABase;
import com.ggasoftware.jdi_ui_tests.core.elements.interfaces.complex.IPage;
import com.ggasoftware.jdi_ui_tests.core.elements.page_objects.annotations.JDIAction;

import java.lang.reflect.Field;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * Created by Roman_Iovlev on 7/17/2015.
 */
public abstract class APage extends ABase implements IPage {
    public abstract void fillPageFromAnnotation(Field field, Object parent);
    public abstract void fillPageFromAnnotation();

    private String title;
    private String titleMatcher;
    public abstract String getTitle();

    public StringCheckType title() { return new StringCheckType(title, titleMatcher, getTitle()); }

    public class StringCheckType {
        private String string;
        private String matcher;
        private String actualValue;

        public StringCheckType(String string, String matcher, String actualValue) {
            this.string = string;
            this.matcher = (matcher != null) ? matcher : string;
            this.actualValue = actualValue;
        }

        /** Check that current page actualValue equals to expected url/title */
        @JDIAction
        public void check() { assertEquals(actualValue, string); }
        /** Check that current page actualValue matches to expected url/title-matcher */
        @JDIAction
        public void match() { assertTrue(actualValue.matches(matcher)); }
        /** Check that current page actualValue contains expected url/title-matcher */
        @JDIAction
        public void contains() { assertTrue(actualValue.contains(matcher)); }
    }
}
