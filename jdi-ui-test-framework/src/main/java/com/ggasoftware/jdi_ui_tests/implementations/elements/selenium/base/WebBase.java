package com.ggasoftware.jdi_ui_tests.implementations.elements.selenium.base;

import com.ggasoftware.jdi_ui_tests.core.elements.base.ABase;
import com.ggasoftware.jdi_ui_tests.core.elements.interfaces.base.IBaseElement;
import com.ggasoftware.jdi_ui_tests.implementations.elements.selenium.annotations.Frame;
import com.ggasoftware.jdi_ui_tests.implementations.elements.selenium.annotations.JFindBy;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static com.ggasoftware.jdi_ui_tests.core.elements.base.CascadeInit.InitElements;
import static com.ggasoftware.jdi_ui_tests.core.elements.base.MapInterfaceToElement.updateInterfacesMap;
import static com.ggasoftware.jdi_ui_tests.core.settings.JDIData.applicationVersion;
import static com.ggasoftware.jdi_ui_tests.core.settings.JDISettings.asserter;
import static com.ggasoftware.jdi_ui_tests.implementations.elements.selenium.annotations.SlmAnnotationsUtil.getFindByLocator;
import static com.ggasoftware.jdi_ui_tests.implementations.elements.selenium.annotations.SlmAnnotationsUtil.getFrame;
import static com.ggasoftware.jdi_ui_tests.implementations.elements.selenium.base.ContextType.Frame;
import static com.ggasoftware.jdi_ui_tests.utils.common.ReflectionUtils.isClass;
import static com.ggasoftware.jdi_ui_tests.utils.common.StringUtils.LineBreak;
import static java.lang.String.format;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

/**
 * Created by Roman_Iovlev on 6/10/2015.
 */
public class WebBase extends ABase {
    protected WebElementsHandler elementHandler;
    public WebDriver getDriver() { return elementHandler.getDriver(); }
    protected JavascriptExecutor jsExecutor() { return (JavascriptExecutor) getDriver(); }
    public By getLocator() { return elementHandler.getLocator(); }

    public WebBase() { this(null); }
    public WebBase(By byLocator) {
        elementHandler = new WebElementsHandler(byLocator, this);
        if (!createFreeInstance)
            InitElements(this);
        updateInterfacesMap(new Object[][]{
              /*  {IElement.class, AElement.class},
                {IButton.class, AButton.class},
                {IClickable.class, AClickable.class},
                {IComboBox.class, AComboBox.class},
                {ILink.class, ALink.class},
                {ISelector.class, ASelector.class},
                {IText.class, AText.class},
                {ITextArea.class, ATextArea.class},
                {ITextField.class, ATextField.class},
                {ILabel.class, Label.class},
                {IDropDown.class, ADropdown.class},
                {IDropList.class, ADropList.class},
                {IGroup.class, ElementsGroup.class},
                {ITable.class, Table.class},
                {ICheckBox.class, ACheckBox.class},
                {IRadioButtons.class, ARadioButtons.class},
                {ICheckList.class, ACheckList.class},
                {ITextList.class, ATextList.class},
                {ITabs.class, ATabs.class},
                {IMenu.class, AMenu.class},
                {IFileInput.class, FileInput.class},
                {IDatePicker.class, ADatePicker.class},*/
        });
    }

    protected void setTimeoutAction(long mSeconds) {
        getDriver().manage().timeouts().implicitlyWait(mSeconds, MILLISECONDS);
    }

    protected void setLocatorFromField(Field field, Object parent, Class<?> type) {
        elementHandler = new WebElementsHandler(getNewLocator(field), getContext(parent, type), this);
    }

    public <T extends IBaseElement> T copyFromTemplate(T element, String name) {
        try {
            WebBase baseElement;
            if (isClass(element.getClass(), WebBase.class))
                baseElement = (WebBase) element;
            else
                throw asserter.exception("Can't copy element. It should inherit WebBaseElement");
            WebBase result = baseElement.getClass().newInstance();
            result.elementHandler = baseElement.elementHandler.getFilledTemplate(name, result);
            return (T) result;
        } catch (Exception ex) { throw asserter.exception("Can't copy element: " + element); }
    }

    public String printLocator() {
        return elementHandler.getLocator()+"";
    }

    private static List<ByContext> getContext(Object parent, Class<?> type) {
        List<ByContext> result = new ArrayList<>();
        if (isBaseElement(parent)) {
            result = ((WebBase) parent).elementHandler.getContextCopy();
            By parentLocator = ((WebBase) parent).elementHandler.getLocator();
            result.add(new ByContext(parentLocator));
        }
        if (type != null) {
            By frameBy = getFrame(type.getDeclaredAnnotation(Frame.class));
            if (frameBy != null)
                result.add(new ByContext(Frame, frameBy));
        }
        return result;
    }

    private static boolean isBaseElement(Object obj) { return isClass(obj.getClass(), ABase.class); }

    private static By getNewLocator(Field field) {
        try {
            By byLocator = null;
            String locatorGroup = applicationVersion;
            if (locatorGroup != null) {
                JFindBy jFindBy = field.getAnnotation(JFindBy.class);
                if (jFindBy != null && locatorGroup.equals(jFindBy.group()))
                    byLocator = getFindByLocator(jFindBy);
            }
            return (byLocator != null)
                    ? byLocator
                    : getFindByLocator(field.getAnnotation(FindBy.class));
        } catch (Exception ex) {
            asserter.exception(format("Error in get locator for type '%s'", field.getType().getName()) +
                    LineBreak + ex.getMessage()); return null; }
    }

    @Override
    public String toString() {
        return format("Name: '%s', Type: '%s' In: '%s', %s",
                getName(), getTypeName(), getParentName(), elementHandler);
    }
}
