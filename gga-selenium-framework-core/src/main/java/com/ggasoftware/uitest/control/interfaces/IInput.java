package com.ggasoftware.uitest.control.interfaces;

/**
 * Created by Roman_Iovlev on 7/6/2015.
 */
public interface IInput extends ISetValue {
    void input(String text) throws Exception;
    void newInput(String text) throws Exception;
    String getText() throws Exception;
    void clear() throws Exception;
    void focus() throws Exception;
}
