package com.epam.jdi.uitests.gui.sikuli.elements.composite;

import com.epam.jdi.uitests.core.interfaces.complex.IPage;
import com.epam.jdi.uitests.gui.GuiSettings;
import com.epam.jdi.uitests.gui.sikuli.elements.BaseElement;
import com.epam.jdi.uitests.gui.sikuli.elements.CheckPageTypes;
import org.sikuli.script.Pattern;

import java.awt.*;

/**
 * Created by Natalia_Grebenshchik on 1/15/2016.
 */
public class Page extends BaseElement implements IPage {

    String filePath;
    Pattern pattern;
    Rectangle rectangle;
    int X, Y, W, H;
    double similarity;
    protected CheckPageTypes checkPage = CheckPageTypes.EQUAL;

    public Page(){};

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void checkOpened() {  }


    @Override
    public <T extends IPage> T open() {
        return null;
    }

    public void updatePageData(String fileLogoPath, CheckPageTypes checkPage,
                               Rectangle rectangle, int X, int Y, int W, int H, double similarity) {
        if (this.filePath == null)
            this.filePath = fileLogoPath;
        if (this.rectangle == null)
            this.rectangle = rectangle;
        if (this.X == 0)
            this.X = X;
        if (this.Y == 0)
            this.Y = Y;
        if (this.W == 0)
            this.W = W;
        if (this.H == 0)
            this.H = H;
        if (this.similarity == 0)
            this.similarity = similarity;
        if (this.filePath != null && this.similarity != 0.0)
            this.pattern = new Pattern(fileLogoPath).similar((float) this.similarity);
        this.checkPage = checkPage;
    }
}
