package com.github.likhoman.getbin.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

/**
 * @author Artyom Likhomanenko
 */
@Configuration
@PropertySource("file:${app.home}/conf/getbin.properties")
@Getter
@Setter
public class Config {
  @Value("${url:#{null}}")
  private String url;

  @Bean("config")
  public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
    return new PropertySourcesPlaceholderConfigurer();
  }
}
