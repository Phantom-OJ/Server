package sustech.edu.phantom.dboj.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import sustech.edu.phantom.dboj.service.SchedulingService;
import sustech.edu.phantom.dboj.service.StatService;

import java.util.concurrent.TimeUnit;

/**
 * 这个类是springboot定时任务
 */
@Component
@Slf4j
public class DatabaseSchedulingUtil {
    @Autowired
    SchedulingService schedulingService;

    @Autowired
    StatService statService;

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @Scheduled(cron = "0 0/30 * * * ?")
    public void updateAssignmentInfo() {
        log.info("Updating the assignment and problem table every 30 minutes.");
        schedulingService.updateAssignmentInfo();
    }

    @Scheduled(cron = "1 0 0 * * ?")
    public void getHomeStat() {
        try {

        } catch (RuntimeException e) {

        }
        log.info("Getting home statistics every day!!!!!");
        redisTemplate.opsForValue().set("Home statistics", "", 1, TimeUnit.DAYS);
    }
}
