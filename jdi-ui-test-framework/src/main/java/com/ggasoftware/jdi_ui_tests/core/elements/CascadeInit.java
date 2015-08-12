package com.ggasoftware.jdi_ui_tests.core.elements;

import com.ggasoftware.jdi_ui_tests.core.elements.composite.Page;
import com.ggasoftware.jdi_ui_tests.core.elements.interfaces.base.IBaseElement;
import com.ggasoftware.jdi_ui_tests.core.elements.interfaces.base.IComposite;
import com.ggasoftware.jdi_ui_tests.core.elements.page_objects.annotations.JPage;
import com.ggasoftware.jdi_ui_tests.selenium.elements.base.ByContext;

import java.lang.reflect.Field;
import java.util.ArrayList;

import static com.ggasoftware.jdi_ui_tests.core.elements.BaseElement.createFreeInstance;
import static com.ggasoftware.jdi_ui_tests.core.elements.BaseElement.getClassFromInterface;
import static com.ggasoftware.jdi_ui_tests.core.elements.page_objects.annotations.AnnotationsUtil.*;
import static com.ggasoftware.jdi_ui_tests.settings.JDISettings.asserter;
import static com.ggasoftware.jdi_ui_tests.utils.common.LinqUtils.foreach;
import static com.ggasoftware.jdi_ui_tests.utils.common.ReflectionUtils.*;
import static com.ggasoftware.jdi_ui_tests.utils.common.StringUtils.LineBreak;
import static com.ggasoftware.jdi_ui_tests.utils.usefulUtils.TryCatchUtil.tryGetResult;
import static java.lang.String.format;

/**
 * Created by Roman_Iovlev on 6/10/2015.
 */
abstract class CascadeInit implements IBaseElement {
    private static boolean firstInstance = true;
    public static void InitElements(Object parent) {
        if (parent.getClass().getName().contains("$")) return;
        Object parentInstance = null;
        Class<?> parentType = parent.getClass();
        boolean firstInstanceCreated = false;

        if (firstInstance) {
            parentInstance = getParentInstance(parentType);
            firstInstanceCreated = true;
        }

        initSubElements(parent, parentInstance);

        if (isClass(parentType, Page.class) && parentType.isAnnotationPresent(JPage.class))
            fillPageFromAnnotaiton((Page) parent, parentType.getAnnotation(JPage.class), null);

        if (firstInstanceCreated)
            firstInstance = true;
    }

    private static Object getParentInstance(Class<?> parentType) {
        firstInstance = false;
        createFreeInstance = true;
        Object parentInstance = tryGetResult(parentType::newInstance);
        createFreeInstance = false;
        return parentInstance;
    }
    private static void initSubElements(Object parent, Object parentInstance) {
        asserter.silent(() -> foreach(getFields(parent, IBaseElement.class),
                f -> setElement(parent, parentInstance, f)));
    }

    public static void setElement(Object parent, Object parentInstance, Field field) throws Exception {
        try {
            Class<?> type = field.getType();
            parentInstance = (parentInstance != null) ? parentInstance : parent;
            BaseElement instance;
            if (isClass(type, Page.class)) {
                instance = (BaseElement) getFieldValue(field, parentInstance);
                if (instance == null)
                    instance = (BaseElement) type.newInstance();
                fillPage(instance, field, parent);
            }
            else {
                instance = createChildFromField(parentInstance, field, type);
                instance.function = getFunction(field);
            }
            instance.setName(getElementName(field));
            if (instance.getClass().getSimpleName().equals(""))
                instance.setTypeName(type.getSimpleName());
            instance.setParentName(parent.getClass().getSimpleName());
            field.set(parent, instance);
            if (isInterface(field, IComposite.class))
                InitElements(instance);
        } catch (Exception ex) {
            throw asserter.exception(format("Error in setElement for field '%s' with parent '%s'", field.getName(), parent.getClass().getSimpleName()) + LineBreak + ex.getMessage()); }
    }

    private static void fillPage(BaseElement instance, Field field, Object parent) throws Exception {
        if (field.isAnnotationPresent(JPage.class))
            fillPageFromAnnotaiton((Page) instance, field.getAnnotation(JPage.class), parent);
    }
    private static BaseElement createChildFromField(Object parentInstance, Field field, Class<?> type) {
        BaseElement instance = (BaseElement) getFieldValue(field, parentInstance);
        if (instance == null)
            try { instance = getElementInstance(type, field); }
            catch (Exception ex) { asserter.exception(
                    format("Can't create child for parent '%s' with type '%s'",
                            parentInstance.getClass().getSimpleName(), field.getType().getSimpleName())); return null; }
        else if (instance.getLocator() == null)
            instance.locationInfo.setLocatorFromField(field);
        instance.locationInfo.setContext((isBaseElement(parentInstance))
                ? ((BaseElement) parentInstance).locationInfo.getContext()
                : new ArrayList<ByContext>());
        instance.updateContextParent(parentInstance);
        return instance;
    }

    private static BaseElement getElementInstance(Class<?> type, Field field) throws Exception {
        String fieldName = field.getName();
        try {
            if (type.isInterface())
                type = getClassFromInterface(type);
            if (type == null)
                throw asserter.exception("Unknown interface: " + type +
                        ". Add relation interface -> class in VIElement.InterfaceTypeMap");
            BaseElement instance = (BaseElement) type.newInstance();
            instance.locationInfo.setLocatorFromField(field);
            return instance;
        } catch (Exception ex) {
            throw asserter.exception(format("Error in getElementInstance for field '%s' with type '%s'", fieldName, type.getSimpleName()) +
                    LineBreak + ex.getMessage()); }
    }

    private static boolean isBaseElement(Object obj) {
        return isClass(obj.getClass(), BaseElement.class);
    }

}