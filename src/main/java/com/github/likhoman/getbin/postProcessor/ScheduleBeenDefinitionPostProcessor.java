package com.github.likhoman.getbin.postProcessor;

import com.github.likhoman.getbin.config.ScheduleConfig;
import org.azeckoski.reflectutils.ReflectUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

import static com.github.likhoman.getbin.postProcessor.ScheduleBeenDefinitionPostProcessor.ScheduleTimeType.*;
import static org.apache.logging.log4j.util.Strings.isNotEmpty;

/**
 * @author Artyom Likhomanenko
 * <p>
 * Get value from config and set to annptation value
 */
@Component
public class ScheduleBeenDefinitionPostProcessor implements BeanPostProcessor {

  private static final String ANNOTATION_DATA = "annotationData";
  private static final String ANNOTATIONS = "annotations";
  private static ScheduleConfig config;
  private static final ReflectUtils reflectUtils = ReflectUtils.getInstance();

  @Override
  public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
    if (bean instanceof ScheduleConfig) {
      Class<?> beanClass = bean.getClass();
      saveConfig(bean);

      Method[] methods = ReflectionUtils.getAllDeclaredMethods(beanClass);
      for (Method method : methods) {
        if (method.isAnnotationPresent(Scheduled.class) && "download".equals(method.getName())) {
          if (config == null) {
            throw new IllegalStateException("config not initialized");
          }
          Method annotationDataMethod = ReflectionUtils.findMethod(Class.class, ANNOTATION_DATA);
          ReflectionUtils.makeAccessible(annotationDataMethod);
          Object annotationData = ReflectionUtils.invokeMethod(annotationDataMethod, beanClass);

          Field annotations = ReflectionUtils.findField(annotationData.getClass(), ANNOTATIONS);
          ReflectionUtils.makeAccessible(annotations);
          try {
            @SuppressWarnings("unchecked cast") Map<Class<? extends Annotation>, Annotation> map = (Map<Class<? extends Annotation>, Annotation>) annotations.get(annotationData);
            Scheduled newAnnotation = new DynamicSchedule();
            initializeFieldInAnnotation(newAnnotation);
            map.put(Scheduled.class, newAnnotation);
          } catch (IllegalAccessException | IllegalArgumentException e) {
            throw new IllegalStateException("Can't get annotations.");
          }
        }
      }
      return bean;
    } else {
      return bean;
    }
  }

  private List<String> findFieldByConfigParameters() {
    Set<ScheduleTimeType> scheduleTimeTypes = getScheduleTimeType();
    List<String> fields = new ArrayList<>(scheduleTimeTypes.size());
    for (ScheduleTimeType scheduleTimeType : scheduleTimeTypes) {
      fields.add(scheduleTimeType.name());
    }
    return fields;
  }

  private Set<ScheduleTimeType> getScheduleTimeType() {
    Set<ScheduleTimeType> timeTypes = EnumSet.noneOf(ScheduleTimeType.class);
    if (isNotEmpty(config.getCron())) {
      timeTypes.add(cron);
    } else if (isNotEmpty(config.getZone())) {
      timeTypes.add(zone);
    } else if (isNotEmpty(config.getFixDelay())) {
      timeTypes.add(fixDelay);
    } else if (isNotEmpty(config.getFixRate())) {
      timeTypes.add(fixedRate);
    } else if (isNotEmpty(config.getInitialDelay())) {
      timeTypes.add(initialDelay);
    }
    return timeTypes;
  }

  private void saveConfig(Object bean) {
    Class<?> beanClass = bean.getClass();
    if (ScheduleConfig.class.isAssignableFrom(beanClass)) {
      config = ScheduleConfig.class.cast(bean);
    }
  }

  enum ScheduleTimeType {
    cron, zone, fixDelay, fixedRate, initialDelay;
  }

  private void initializeFieldInAnnotation(Annotation annotation) {
    for (String fieldName : findFieldByConfigParameters()) {
      Object fieldValue = reflectUtils.getFieldValue(config, fieldName);
      reflectUtils.setFieldValue(annotation, fieldName, fieldValue);
    }
  }
}
