package sustech.edu.phantom.dboj.utils;

import org.springframework.scheduling.annotation.Scheduled;

/**
 * 这个类是springboot定时任务
 */
public class DatabaseSchedulingUtil {
    /**
     * 每隔三十分钟更新一遍assignment表
     */
    @Scheduled(cron = "0 */30 * * * ?")
    public void updateAssignmentInfo(){

    }
}
