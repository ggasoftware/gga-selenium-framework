package com.epam.page_objects2.mct.custom;

import com.epam.ui_test_framework.elements.common.FileInput;
import org.openqa.selenium.By;

import java.io.File;

import static com.epam.ui_test_framework.utils.usefulUtils.TryCatchUtil.tryGetResult;
import static java.lang.Thread.sleep;
import static java.net.URLDecoder.decode;

/**
 * Created by Roman_Iovlev on 7/10/2015.
 */
public class ModernaFileInput extends FileInput {
    public ModernaFileInput() { avatar.localElementSearchCriteria = el -> true; }
    public ModernaFileInput(By byLocator) { super(byLocator); avatar.localElementSearchCriteria = el -> true; }

    private String workingDir = tryGetResult(() -> decode(new File(
            new File(".").getCanonicalPath()) + "\\jdi-ui-test-framework\\src\\main\\resources\\", "UTF-8"));

    @Override
    protected void inputAction(String text) { super.inputAction(workingDir + text); }
    @Override
    protected void clearAction() { }
}
