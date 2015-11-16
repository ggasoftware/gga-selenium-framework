package com.epam.jditests.tests.common;

import com.epam.jditests.InitTests;
import com.epam.jditests.enums.Preconditions;
import com.epam.jditests.pageobjects.EpamJDISite;
import com.ggasoftware.jdiuitest.core.interfaces.common.IFileInput;
import com.ggasoftware.jdiuitest.web.robot.RFileInput;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

import static com.epam.jditests.pageobjects.EpamJDISite.isInState;
import static com.epam.jditests.tests.complex.CommonActionsData.*;

public class FileInputTests extends InitTests {
    private Preconditions _onPage = null;

    public FileInputTests() {
        _onPage = Preconditions.DATES_PAGE_FILLED;
    }

    public RFileInput input() {
        return EpamJDISite.dates.rImageInput;
    }

    public IFileInput robotInput() {
        return EpamJDISite.dates.imageInput;
    }

    @BeforeMethod
    public void before(final Method method) {
        isInState(_onPage, method);
    }

    // INPUT
    @Test
    public void inputTest() {
        input().input(getFPath());
        checkAction("FileUpload: file \"" + getFName() + "\" has been uploaded");
    }

    @Test
    public void sendKeysTest() {
        input().sendKeys(getFPath());
        checkAction("FileUpload: file \"" + getFName() + "\" has been uploaded");
    }

    @Test
    public void newInputTest() throws Exception {
        input().newInput(getFPath());
        checkAction("FileUpload: file \"" + getFName() + "\" has been uploaded");
    }
    // !INPUT

}
