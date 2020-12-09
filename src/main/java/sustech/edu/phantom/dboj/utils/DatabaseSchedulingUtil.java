package sustech.edu.phantom.dboj.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import sustech.edu.phantom.dboj.service.SchedulingService;

/**
 * 这个类是springboot定时任务
 */
@Component
@Slf4j
public class DatabaseSchedulingUtil {
    @Autowired
    SchedulingService schedulingService;

    @Scheduled(cron = "* */30 * * * ?")
    public void updateAssignmentInfo() {
        log.info("Updating the assignment and problem table every 30 minutes.");
        schedulingService.updateAssignmentInfo();
    }
}
