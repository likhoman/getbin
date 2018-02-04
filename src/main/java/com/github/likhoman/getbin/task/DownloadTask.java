package com.github.likhoman.getbin.task;

import org.slf4j.Logger;
import org.springframework.context.annotation.DependsOn;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author Artyom Likhomanenko
 */

@Component
public class DownloadTask {
  private static final Logger logger = getLogger(DownloadTask.class);

  @Scheduled(cron = "")
  @DependsOn("config")
  public void download() {
    System.out.println("test schedule");
  }

}
