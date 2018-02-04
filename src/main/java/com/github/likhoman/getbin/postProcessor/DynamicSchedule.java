package com.github.likhoman.getbin.postProcessor;

import org.springframework.scheduling.annotation.Scheduled;

import java.lang.annotation.Annotation;

/**
 * @author Artyom Likhomanenko
 */
@SuppressWarnings({"ClassExplicitlyAnnotation", "UnusedDeclaration"})
public class DynamicSchedule implements Scheduled {

  private String cron;
  private String zone;
  private long fixDelay = -1;
  private String fixedDelayString;
  private long fixedRate = -1;
  private String fixedRateString;
  private long initialDelay = -1;
  private String initialDelayString;

  @Override
  public String cron() {
    return cron;
  }

  @Override
  public String zone() {
    return zone;
  }

  @Override
  public long fixedDelay() {
    return fixDelay;
  }

  @Override
  public String fixedDelayString() {
    return fixedDelayString;
  }

  @Override
  public long fixedRate() {
    return fixedRate;
  }

  @Override
  public String fixedRateString() {
    return fixedRateString;
  }

  @Override
  public long initialDelay() {
    return initialDelay;
  }

  @Override
  public String initialDelayString() {
    return initialDelayString;
  }

  @Override
  public Class<? extends Annotation> annotationType() {
    return DynamicSchedule.class;
  }
}
