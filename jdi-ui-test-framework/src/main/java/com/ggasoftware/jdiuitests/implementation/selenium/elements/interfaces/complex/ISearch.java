package com.ggasoftware.jdiuitests.implementation.selenium.elements.interfaces.complex;

import com.ggasoftware.jdiuitests.implementation.selenium.elements.interfaces.base.IComposite;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.interfaces.common.ITextField;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.pageobjects.annotations.JDIAction;

import java.util.List;

/**
 * Created by Roman_Iovlev on 7/8/2015.
 */
public interface ISearch extends ITextField, IComposite {
    /**
     * @param text        Specify Text to search
     * @param selectValue Specify value to choose from suggested search result
     * @return Input text in search and then select value from suggestions
     */
    @JDIAction
    void chooseSuggestion(String text, String selectValue);

    /**
     * @param text        Specify Text to search
     * @param selectIndex Specify index to choose from suggested search result
     * @return Input text in search and then select suggestions by index
     */
    @JDIAction
    void chooseSuggestion(String text, int selectIndex);

    /**
     * @param text Specify Text to search
     * @return Input text in search field and press search button
     */
    @JDIAction
    void find(String text);

    /**
     * @param text Specify Text to search
     * @return Select all suggestions for text
     */
    @JDIAction
    List<String> getSuggesions(String text);
}
