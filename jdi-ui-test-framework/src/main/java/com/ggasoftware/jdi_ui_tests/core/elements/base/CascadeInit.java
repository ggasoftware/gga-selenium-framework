package com.ggasoftware.jdi_ui_tests.core.elements.base;

import com.ggasoftware.jdi_ui_tests.core.elements.interfaces.base.*;
import com.ggasoftware.jdi_ui_tests.core.elements.template.base.BaseElement;
import com.ggasoftware.jdi_ui_tests.core.elements.template.composite.Page;

import java.lang.reflect.Field;

import static com.ggasoftware.jdi_ui_tests.core.elements.page_objects.annotations.AnnotationsUtil.*;
import static com.ggasoftware.jdi_ui_tests.core.elements.template.base.BaseElement.createFreeInstance;
import static com.ggasoftware.jdi_ui_tests.core.elements.base.MapInterfaceToElement.getClassFromInterface;
import static com.ggasoftware.jdi_ui_tests.core.settings.JDISettings.asserter;
import static com.ggasoftware.jdi_ui_tests.utils.common.LinqUtils.foreach;
import static com.ggasoftware.jdi_ui_tests.utils.common.ReflectionUtils.*;
import static com.ggasoftware.jdi_ui_tests.utils.common.StringUtils.LineBreak;
import static com.ggasoftware.jdi_ui_tests.utils.usefulUtils.TryCatchUtil.tryGetResult;
import static java.lang.String.format;

/**
 * Created by Roman_Iovlev on 6/10/2015.
 */
public class CascadeInit  {
    private static boolean firstInstance = true;
    public static void InitElements(Object parent) {
        if (parent.getClass().getName().contains("$")) return;
        Class<?> parentType = parent.getClass();

        Object parentInstance = (firstInstance)
            ? getParentInstance(parentType)
            : parent;
        initSubElements(parent, parentInstance);

        if (isClass(parentType, Page.class))
            ((Page) parentInstance).fillPageFromAnnotation();

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

    private static void setElement(Object parent, Object parentInstance, Field field) {
        try {
            Class<?> type = field.getType();
            BaseElement instance;
            if (isClass(type, Page.class)) {
                instance = (BaseElement) getFieldValue(field, parentInstance);
                if (instance == null)
                    instance = (BaseElement) type.newInstance();
                ((Page) instance).fillPageFromAnnotation(field, parent);
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
            throw asserter.exception(format("Error in setElement for field '%s' with parent '%s'",
                    field.getName(), parent.getClass().getSimpleName()) + LineBreak + ex.getMessage()); }
    }

    private static BaseElement createChildFromField(Object parentInstance, Field field, Class<?> type) {
        BaseElement instance = (BaseElement) getFieldValue(field, parentInstance);
        if (instance == null)
            try {
                if (type.isInterface())
                    type = getClassFromInterface(type);
                instance = (BaseElement) type.newInstance();
            } catch (Exception ex) {
                throw asserter.exception(format("Can't create child for parent '%s' with type '%s'",
                    parentInstance.getClass().getSimpleName(), field.getType().getSimpleName()));
            }
        instance.setLocatorFromField(field, parentInstance, type);
        return instance;
    }

}