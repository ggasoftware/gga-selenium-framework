package com.epam.ui_test_framework.elements.interfaces.complex;

import com.epam.ui_test_framework.elements.interfaces.base.*;
import com.epam.ui_test_framework.elements.interfaces.common.ITextfield;
import com.epam.ui_test_framework.elements.page_objects.annotations.JDIAction;

import java.util.List;

/**
 * Created by Roman_Iovlev on 7/8/2015.
 */
public interface ISearch extends ITextfield, IComposite {
    /** Input text in search and then select value from suggestions */
    @JDIAction
    void chooseSuggestion(String text, String selectValue);
    /** Input text in search and then select suggestions by index*/
    @JDIAction
    void chooseSuggestion(String text, int selectIndex);
    /** Select element with name (use index) from list */
    @JDIAction
    void search(String text);
    @JDIAction
    List<String> getSuggesions(String text);
}
