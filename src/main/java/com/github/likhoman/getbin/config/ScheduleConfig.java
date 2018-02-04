package com.github.likhoman.getbin.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author Artyom Likhomanenko
 */

@Configuration
@Getter
@Setter
public class ScheduleConfig extends Config {

  @Value("${schedule.cron:#{null}}")
  private String cron;
  @Value("${schedule.zone:#{null}}")
  private String zone;
  @Value("${schedule.fixDelay:#{null}}")
  private String fixDelay;
  @Value("${schedule.fixRate:#{null}}")
  private String fixRate;
  @Value("${schedule.initialDelay:#{null}}")
  private String initialDelay;
}
