package com.uccwal.naratracer.TracerScheduler;

import com.uccwal.naratracer.Service.TracerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class TracerScheduler {

    private static final Logger logger = LoggerFactory.getLogger(TracerScheduler.class);

    private final TracerService tracerService;

    @Autowired
    public TracerScheduler(TracerService tracerService) {
        this.tracerService = tracerService;
    }

    // 3시간마다 크롤링 수행 (cron 표현식 사용)
    //@Scheduled(cron = "0 0 */3 * * *")
    @Scheduled(fixedRate = 3000) // 10초마다
    public void performTracer() {
        logger.info("Crawling Job Started...");
        //tracerService.tracerAndPrintData();
        tracerService.tracerAndSaveData();
        logger.info("Crawling Job Completed!");
    }
}