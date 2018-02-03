package com.github.likhoman.getbin.task;

import com.github.likhoman.getbin.config.Config;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author Artyom Likhomanenko
 */

@Component
public class DownloadTask {
  private static final Logger logger = getLogger(DownloadTask.class);

  private Config config;

  @Autowired
  public DownloadTask(Config config) {
    this.config = config;
  }

  @Scheduled(cron = "")
  public void download() {
    config.getScheduleTime();
  }

}
