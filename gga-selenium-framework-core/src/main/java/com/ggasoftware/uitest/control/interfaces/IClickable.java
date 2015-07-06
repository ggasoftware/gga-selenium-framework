package com.ggasoftware.uitest.control.interfaces;

/**
 * Created by Roman_Iovlev on 6/10/2015.
 */
public interface IClickable extends IElement {
    void click() throws Exception;
    void clickByXY(int x, int y) throws Exception;
    void clickJS() throws Exception;

}
