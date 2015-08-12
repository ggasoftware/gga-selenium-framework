package com.ggasoftware.jdi_ui_tests.core.elements.interfaces.complex;

import com.ggasoftware.jdi_ui_tests.core.elements.interfaces.base.IComposite;
import com.ggasoftware.jdi_ui_tests.core.elements.interfaces.common.ITextField;
import com.ggasoftware.jdi_ui_tests.core.elements.page_objects.annotations.JDIAction;

import java.util.List;

/**
 * Created by Roman_Iovlev on 7/8/2015.
 */
public interface ISearch extends ITextField, IComposite {
    /** Input text in search and then select value from suggestions */
    @JDIAction
    void chooseSuggestion(String text, String selectValue);
    /** Input text in search and then select suggestions by index*/
    @JDIAction
    void chooseSuggestion(String text, int selectIndex);
    /** Input text in search field and press search button */
    @JDIAction
    void search(String text);
    /** Select all suggestions for text */
    @JDIAction
    List<String> getSuggesions(String text);
}
