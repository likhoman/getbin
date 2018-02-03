package com.github.likhoman.getbin.postProcessor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author Artyom Likhomanenko
 */
@Component
public class ScheduleBeenDefinitionPostProcessor implements BeanPostProcessor {

  private static final String ANNOTATION_DATA = "annotationData";
  private static final String ANNOTATIONS = "annotations";

  @Override
  public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
    Class<?> beanClass = bean.getClass();
    Method[] methods = beanClass.getMethods();
    for (Method method : methods) {
      if (method.isAnnotationPresent(Scheduled.class) && "download".equals(method.getName())) {

        Method methodFromClass = null;
        try {
          methodFromClass = Class.class.getDeclaredMethod(ANNOTATION_DATA, null);
        } catch (NoSuchMethodException e) {
          e.printStackTrace();
          return bean;
        }
        methodFromClass.setAccessible(true);
        Object annotationData = null;
        try {
          annotationData = method.invoke(beanClass);
        } catch (IllegalAccessException e) {
          e.printStackTrace();
          return bean;
        } catch (InvocationTargetException e) {
          e.printStackTrace();
          return bean;
        }

        Field annotations = null;
        try {
          annotations = annotationData.getClass().getDeclaredField(ANNOTATIONS);
        } catch (NoSuchFieldException e) {
          e.printStackTrace();
          return bean;
        }

        annotations.setAccessible(true);
        Map<Class<? extends Annotation>, Annotation> map = null;
        try {
          map = (Map<Class<? extends Annotation>, Annotation>) annotations.get(annotationData);
        } catch (IllegalAccessException e) {
          e.printStackTrace();
          return bean;
        }
        Scheduled newAnnotation = new DynamicSchedule();
        map.put(Scheduled.class, newAnnotation);

      }
    }

    return bean;
  }
}
