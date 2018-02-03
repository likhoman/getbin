package com.github.likhoman.getbin.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

/**
 * @author Artyom Likhomanenko
 */
@Configuration
@PropertySource("file:{app.home}/conf/getbin.properties")
@Data
public class Config {

  @Value("${url}")
  private String url;

  @Value("${schedule.time}")
  private String scheduleTime;

  @Bean
  public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
    return new PropertySourcesPlaceholderConfigurer();
  }
}
