package com.ggasoftware.jdiuitests.implementation.selenium.elements;

import com.ggasoftware.jdiuitests.core.utils.pairs.Pairs;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.apiInteract.ContextType;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.composite.Page;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.interfaces.base.IBaseElement;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.interfaces.base.IComposite;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.page_objects.annotations.Frame;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.page_objects.annotations.JFindBy;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.page_objects.annotations.JPage;
import com.ggasoftware.jdiuitests.core.utils.common.StringUtils;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.page_objects.annotations.AnnotationsUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

import java.lang.reflect.Field;

import static com.ggasoftware.jdiuitests.core.settings.JDIData.applicationVersion;
import static com.ggasoftware.jdiuitests.core.settings.JDISettings.exception;
import static com.ggasoftware.jdiuitests.core.utils.common.LinqUtils.foreach;
import static com.ggasoftware.jdiuitests.core.utils.common.ReflectionUtils.*;
import static com.ggasoftware.jdiuitests.core.utils.usefulUtils.TryCatchUtil.tryGetResult;
import static java.lang.String.format;

/**
 * Created by Roman_Iovlev on 6/10/2015.
 */
public abstract class CascadeInit implements IBaseElement {
    public static boolean firstInstance = true;

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
            AnnotationsUtil.fillPageFromAnnotaiton((Page) parent, parentType.getAnnotation(JPage.class), null);

        if (firstInstanceCreated)
            firstInstance = true;
    }

    private static Object getParentInstance(Class<?> parentType) {
        firstInstance = false;
        BaseElement.createFreeInstance = true;
        Object parentInstance = tryGetResult(parentType::newInstance);
        BaseElement.createFreeInstance = false;
        return parentInstance;
    }
    private static void initSubElements(Object parent, Object parentInstance) {
        foreach(deepGetFields(parent, IBaseElement.class),
                field -> setElement(parent, parentInstance, field));
    }

    public static void InitPages(Class<?> parentType) {
        if (parentType.getName().contains("$")) return;
        boolean firstInstanceCreated = false;

        if (firstInstance)
            firstInstanceCreated = true;
        initPages(parentType);
        if (firstInstanceCreated)
            firstInstance = true;
    }

    private static void initPages(Class<?> parentType) {
        foreach(getStaticFields(parentType, BaseElement.class),
                field -> setElement(parentType, field));
    }

    public static void setElement(Class<?> parentType, Field field) {
        try {
            Class<?> type = field.getType();
            BaseElement instance;
            if (isClass(type, Page.class)) {
                instance = (BaseElement) getFieldValue(field, null);
                if (instance == null)
                    instance = (BaseElement) type.newInstance();
                fillPage(instance, field, parentType);
            }
            else {
                instance = createChildFromFieldStatic(parentType, field, type);
                instance.function = AnnotationsUtil.getFunction(field);
            }
            instance.setName(field);
            if (instance.getClass().getSimpleName().equals(""))
                instance.setTypeName(type.getSimpleName());
            instance.setParentName(parentType.getClass().getSimpleName());
            field.set(null, instance);
            if (isInterface(field, IComposite.class))
                InitElements(instance);
        } catch (Exception ex) {
            throw exception("Error in setElement for field '%s' with parent '%s'", field.getName(), parentType.getClass().getSimpleName() + StringUtils.LineBreak + ex.getMessage()); }
    }

    public static void setElement(Object parent, Object parentInstance, Field field) {
        try {
            Class<?> type = field.getType();
            parentInstance = (parentInstance != null) ? parentInstance : parent;
            BaseElement instance;
            if (isClass(type, Page.class)) {
                instance = (BaseElement) getFieldValue(field, parentInstance);
                if (instance == null)
                    instance = (BaseElement) type.newInstance();
                fillPage(instance, field, parent != null ? parent.getClass() : null);
            }
            else {
                instance = createChildFromField(parentInstance, field, type);
                instance.function = AnnotationsUtil.getFunction(field);
            }
            instance.setName(field);
            if (instance.getClass().getSimpleName().equals(""))
                instance.setTypeName(type.getSimpleName());
            instance.setParentName(parent.getClass().getSimpleName());
            field.set(parent, instance);
            if (isInterface(field, IComposite.class))
                InitElements(instance);
        } catch (Exception ex) {
            throw exception("Error in setElement for field '%s' with parent '%s'", field.getName(), parent.getClass().getSimpleName() + StringUtils.LineBreak + ex.getMessage()); }
    }

    private static void fillPage(BaseElement instance, Field field, Class<?> parentType) {
        if (field.isAnnotationPresent(JPage.class))
            AnnotationsUtil.fillPageFromAnnotaiton((Page) instance, field.getAnnotation(JPage.class), parentType);
    }
    private static BaseElement createChildFromFieldStatic(Class<?> parentClass, Field field, Class<?> type) {
        BaseElement instance = (BaseElement) getFieldValue(field, null);
        if (instance == null)
            try { instance = getElementInstance(type, field.getName(), getNewLocator(field)); }
            catch (Exception ex) { throw exception(
                    format("Can't create child for parent '%s' with type '%s'",
                            parentClass.getSimpleName(), field.getType().getSimpleName())); }
        else if (instance.getLocator() == null)
            instance.avatar.byLocator = getNewLocator(field);
        instance.avatar.context = new Pairs<>();
        if (type != null) {
            By frameBy = AnnotationsUtil.getFrame(type.getDeclaredAnnotation(Frame.class));
            if (frameBy != null)
                instance.avatar.context.add(ContextType.Frame, frameBy);
        }
        return instance;
    }
    private static BaseElement createChildFromField(Object parentInstance, Field field, Class<?> type) {
        BaseElement instance = (BaseElement) getFieldValue(field, parentInstance);
        if (instance == null)
            try { instance = getElementInstance(type, field.getName(), getNewLocator(field)); }
            catch (Exception ex) { throw exception(
                    format("Can't create child for parent '%s' with type '%s'",
                            parentInstance.getClass().getSimpleName(), field.getType().getSimpleName())); }
        else if (instance.getLocator() == null)
            instance.avatar.byLocator = getNewLocator(field);
        instance.avatar.context = (isBaseElement(parentInstance))
                ? ((BaseElement) parentInstance).avatar.context.copy()
                : new Pairs<>();
        if (type != null) {
            By frameBy = AnnotationsUtil.getFrame(type.getDeclaredAnnotation(Frame.class));
            if (frameBy != null)
                instance.avatar.context.add(ContextType.Frame, frameBy);
        }
        if (isBaseElement(parentInstance)) {
            By parentLocator = ((BaseElement) parentInstance).getLocator();
            if (parentLocator != null)
                instance.avatar.context.add(ContextType.Locator, parentLocator);
        }
        return instance;
    }

    private static boolean isBaseElement(Object obj) {
        return isClass(obj.getClass(), BaseElement.class);
    }
    private static BaseElement getElementInstance(Class<?> type, String fieldName, By newLocator) {
        try {
            if (!type.isInterface()) {
                BaseElement instance = (BaseElement) type.newInstance();
                instance.avatar.byLocator = newLocator;
                return instance;
            }
            Class classType = MapInterfaceToElement.getClassFromInterface(type);
            if (classType != null)
                return (BaseElement) classType.getDeclaredConstructor(By.class).newInstance(newLocator);
            throw exception("Unknown interface: " + type +
                    ". Add relation interface -> class in VIElement.InterfaceTypeMap");
        } catch (Exception ex) {
            throw exception("Error in getElementInstance for field '%s' with type '%s'", fieldName, type.getSimpleName() +
                    StringUtils.LineBreak + ex.getMessage()); }
    }

    private static By getNewLocator(Field field) {
        try {
            By byLocator = null;
            String locatorGroup = applicationVersion;
            if (locatorGroup != null) {
                JFindBy jFindBy = field.getAnnotation(JFindBy.class);
                if (jFindBy != null && locatorGroup.equals(jFindBy.group()))
                    byLocator = AnnotationsUtil.getFindByLocator(jFindBy);
            }
            return (byLocator != null)
                    ? byLocator
                    : AnnotationsUtil.getFindByLocator(field.getAnnotation(FindBy.class));
        } catch (Exception ex) {
            throw exception("Error in get locator for type '%s'", field.getType().getName() +
                    StringUtils.LineBreak + ex.getMessage()); }
    }

}